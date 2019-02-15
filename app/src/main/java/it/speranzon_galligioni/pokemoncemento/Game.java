package it.speranzon_galligioni.pokemoncemento;

import android.animation.Animator;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class Game {
    private Context context;
    private RelativeLayout map;
    private List<Obstacle> obstacles;
    private List<Trainer> trainers;
    private Player player;
    private Direction currentDirection;
    private Handler handler;
    boolean isMoving, mustMove;

    public Game(RelativeLayout map, int height, int width, Player player,Context context) {
        this(map, height, width, new ArrayList<Obstacle>(),new ArrayList<Trainer>(), player,context);
    }

    public Game(RelativeLayout map, int height, int width, List<Obstacle> obstacles,List<Trainer> trainers, Player player,Context context) {
        this(map, height, width, 0, 0, new ArrayList<Obstacle>(),trainers, player,context);
    }

    public Game(RelativeLayout map, int height, int width, int playerInitX, int playerInitY, List<Obstacle> obstacles,List<Trainer> trainers, Player player,Context context) {
        this.obstacles = new ArrayList<>(obstacles);
        this.trainers = new ArrayList<>(trainers);
        this.player = player;
        this.map = map;
        this.context=context;
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) map.getLayoutParams();
        lp.height = GameCostants.BOX_SIZE * height;
        lp.width = GameCostants.BOX_SIZE * width;
        lp.topMargin = (int) (player.getY() - playerInitY) * GameCostants.BOX_SIZE;
        lp.leftMargin = (int) (player.getX() - playerInitX) * GameCostants.BOX_SIZE;
        lp.bottomMargin = ((RelativeLayout) map.getParent()).getHeight() - lp.height - lp.topMargin;
        lp.rightMargin = ((RelativeLayout) map.getParent()).getWidth() - lp.width - lp.leftMargin;
        //Log.d("PROVA","h: "+ mapHeight +", w: "+mapWidth+", lm: "+ lpC.leftMargin/GameCostants.BOX_SIZE +", rm: "+(mcWidth-mapWidth-lpM.leftMargin/GameCostants.BOX_SIZE)+", tm: "+ lpC.topMargin/GameCostants.BOX_SIZE +", bm: "+(mcHeight-mapHeight-lpM.topMargin/GameCostants.BOX_SIZE));
        map.setLayoutParams(lp);

        handler = new Handler();
        currentDirection = Direction.NONE;

        for (Obstacle obs : obstacles)
            map.addView(obs);
        for(Trainer t:trainers)
            map.addView(t);
    }

    /**
     * inizia lo spostamento del Player
     * @param d direzione dello spostamento
     */
    public void startMove(final Direction d) {

        if (currentDirection == Direction.NONE) {

            //Log.d("PROVA", "INIZIO : " + d.toString());
            mustMove = true;
            currentDirection = d;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (!move())
                        handler.postDelayed(this, 100);
                }
            }, 0);
        }
    }


    /**
     * ferma lo spostamento del Player
     * @param d direzione dello spostamento
     */
    public void stopMove(Direction d) {
        if (currentDirection == d) {
            //Log.d("PROVA", "FINE   : " + d.toString());
            mustMove = false;
            currentDirection = Direction.NONE;
        }
    }

    /**
     * Muove il Player se esso è possibile
     * @return ritorna falso se il movimento precedente non è ancora terinato
     *          !!QUANDO RITORNA TRUE NON VUOL DIRE CHE IL MOVIMENTO E' AVVENUTO!!
     */
    public boolean move() {
        //Log.d("PROVA", "mW: " + game.getWidth() / GameCostants.BOX_SIZE + "  mH: " + game.getHeight() / GameCostants.BOX_SIZE);
        //Log.d("PROVA","Bordi: "+!game.checkMapBounds(d) +"  Collisioni: "+game.checkCollisions(d));
        //Log.d("PROVA", "R-MOSSO: " + currentDirection.toString());

        //controlla se vi deve essere un movimento
        if (!mustMove)
            return true;
        //controlla che non si stia già muovendo
        if (isMoving)
            return false;

        //controlla che il Player non esca dalla mappa
        if (!checkMapBounds(currentDirection))
            return true;
        //controlla che il Player non entri in un ostacolo o allenatore
        if (checkCollisions(currentDirection))
            return true;
        //controlla che il Player non venga visto dagli altri allenatori
        if (checkTrainer(currentDirection)) {
            Log.d("PROVA","allenatoreeeeee");
        }
        //Log.d("PROVA", "MOSSO  : " + currentDirection.toString());
        movePlayer(currentDirection);
        return true;

    }

    /**
     * Aggiunge un ostacolo alla mappa
     * @param obs ostacolo
     * @return aggiunta avvenuta con successo
     */
    public boolean addObstacles(Obstacle obs) {
        //TODO: aggiungere un controllo sulla posizione degli ostacoli
        map.addView(obs);
        obstacles.add(obs);
        return true;
    }

    /**
     * controlla se nella voluta direzione lo spostamento è impedito da ostacoli
     * @param direction direzione voluta
     * @return collisione presente
     */
    public boolean checkCollisions(Direction direction) {
        int moveX = (int) (getX() + -direction.getX());
        int moveY = (int) (getY() + -direction.getY());
        for (Obstacle obs : obstacles)
            if (obs.checkCollision(player, moveX, moveY))
                return true;
        for (Trainer t : trainers)
            if (t.checkCollision(player, moveX, moveY))
                return true;
        return false;
    }

    public boolean checkTrainer(Direction direction){
        int moveX = (int) (getX() + -direction.getX());
        int moveY = (int) (getY() + -direction.getY());
        for(Trainer t:trainers)
            if(t.tihavistomannagiaallaputtana(player,moveX,moveY))
                return true;
        return false;
    }

    /**
     * controlla che nella voluta direzione il Player non esca dalla mappa
     * @param direction direzione voluta
     * @return movimento valido
     */
    public boolean checkMapBounds(Direction direction) {
        int moveX = (int) (getX() + -direction.getX());
        int moveY = (int) (getY() + -direction.getY());
        //Log.d("BORDI","playerX: "+player.getX()+", playerY: "+player.getY()+", moveX: "+moveX+", moveY: "+moveY+", Direction: "+direction);
        return (moveX <= player.getX() && moveX + map.getWidth() >= player.getX() + 1
                && moveY <= player.getY() && moveY + map.getHeight() >= player.getY() + 1);
    }

    /**
     * muove il Player nella voluta direzione(con animazione)
     * @param direction direzione del movimento
     */
    private void movePlayer(final Direction direction) {
        map.animate()
                .translationXBy(-direction.getX() * GameCostants.BOX_SIZE)
                .translationYBy(-direction.getY() * GameCostants.BOX_SIZE)
                .setDuration(500)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        isMoving = true;
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        isMoving = false;
                        move();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                    }
                })
                .start();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                player.setImageDrawable(context.getDrawable(R.drawable.man_3));
            }
        },125);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                player.setImageDrawable(context.getDrawable(R.drawable.man_2));
            }
        },375);
        //map.setX(map.getX() + direction.getX() * GameCostants.BOX_SIZE);
        //map.setY(map.getY() + direction.getY() * GameCostants.BOX_SIZE);
    }

    public float getX() {
        return map.getX() / GameCostants.BOX_SIZE;
    }

    public float getY() {
        return map.getY() / GameCostants.BOX_SIZE;
    }

    public void setX(int x) {
        map.setX(x * GameCostants.BOX_SIZE);
    }

    public void setY(int y) {
        map.setY(y * GameCostants.BOX_SIZE);
    }

    public int getWidth() {
        return map.getWidth();
    }

    public int getHeight() {
        return map.getHeight();
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }
}
