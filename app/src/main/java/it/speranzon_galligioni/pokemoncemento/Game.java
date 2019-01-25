package it.speranzon_galligioni.pokemoncemento;

import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

public class Game {
    RelativeLayout map;
    List<Obstacle> obstacles;
    Player player;

    public Game(RelativeLayout map, int height, int width, Player player) {
        this(map, height, width, new ArrayList<Obstacle>(), player);
    }

    public Game(RelativeLayout map, int height, int width, List<Obstacle> obstacles, Player player) {
        this(map, height, width, 0, 0, new ArrayList<Obstacle>(), player);
    }

    public Game(RelativeLayout map, int height, int width, int playerInitX, int playerInitY, List<Obstacle> obstacles, Player player) {
        this.obstacles = new ArrayList<>(obstacles);
        this.player = player;
        this.map = map;
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) map.getLayoutParams();
        lp.height = GameCostants.BOX_SIZE * height;
        lp.width = GameCostants.BOX_SIZE * width;
        lp.topMargin = (int) (player.getY() - playerInitY) * GameCostants.BOX_SIZE;
        lp.leftMargin = (int) (player.getX() - playerInitX) * GameCostants.BOX_SIZE;
        lp.bottomMargin = ((RelativeLayout) map.getParent()).getHeight() - lp.height - lp.topMargin;
        lp.rightMargin = ((RelativeLayout) map.getParent()).getWidth() - lp.width - lp.leftMargin;
        //Log.d("PROVA","h: "+ mapHeight +", w: "+mapWidth+", lm: "+ lpC.leftMargin/GameCostants.BOX_SIZE +", rm: "+(mcWidth-mapWidth-lpM.leftMargin/GameCostants.BOX_SIZE)+", tm: "+ lpC.topMargin/GameCostants.BOX_SIZE +", bm: "+(mcHeight-mapHeight-lpM.topMargin/GameCostants.BOX_SIZE));
        map.setLayoutParams(lp);


        for (Obstacle obs : obstacles)
            map.addView(obs);
    }

    public boolean addObstacles(Obstacle obs) {
        map.addView(obs);
        obstacles.add(obs);
        return true;
    }

    public boolean checkCollisions(Direction direction) {
        int moveX = (int) (getX() + direction.getX());
        int moveY = (int) (getY() + direction.getY());
        for (Obstacle obs : obstacles)
            if (obs.checkCollision(player, moveX, moveY))
                return true;
        return false;
    }

    public boolean checkMapBounds(Direction direction) {
        int moveX = (int) (getX() + direction.getX());
        int moveY = (int) (getY() + direction.getY());
        //Log.d("BORDI","playerX: "+player.getX()+", playerY: "+player.getY()+", moveX: "+moveX+", moveY: "+moveY+", Direction: "+direction);
        return (moveX <= player.getX() && moveX + map.getWidth() >= player.getX() + 1
                && moveY <= player.getY() && moveY + map.getHeight() >= player.getY() + 1);
    }

    public void movePlayer(Direction direction) {
        map.setX(map.getX() + direction.getX() * GameCostants.BOX_SIZE);
        map.setY(map.getY() + direction.getY() * GameCostants.BOX_SIZE);
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
}
