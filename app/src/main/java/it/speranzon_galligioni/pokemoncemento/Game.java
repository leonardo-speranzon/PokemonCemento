package it.speranzon_galligioni.pokemoncemento;

import android.animation.Animator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.Log;
import android.view.ViewPropertyAnimator;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import it.speranzon_galligioni.pokemoncemento.enums.Direction;
import it.speranzon_galligioni.pokemoncemento.gameObject.Obstacle;
import it.speranzon_galligioni.pokemoncemento.gameObject.Player;
import it.speranzon_galligioni.pokemoncemento.gameObject.Trainer;

public class Game {
	private Context context;
	private RelativeLayout map;
	private List<Obstacle> obstacles;
	private List<Trainer> trainers;
	private Player player;
	private Direction currentDirection;
	private Handler handler;
	private TextController txtController;
	private boolean isMoving, mustMove, running;
	private Runnable onScontro;


	public Game(RelativeLayout map, int height, int width, int playerInitX, int playerInitY, List<Obstacle> obstacles, List<Trainer> trainers, Player player, TextController txtController,Runnable onScontro, Context context) {
		this.obstacles = new ArrayList<>(obstacles);
		this.trainers = new ArrayList<>(trainers);
		this.player = player;
		this.map = map;
		this.txtController = txtController;
		this.context = context;
		this.onScontro = onScontro;

		RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) map.getLayoutParams();
		lp.height = GameCostants.BOX_SIZE * height;
		lp.width = GameCostants.BOX_SIZE * width;
		Log.d("PROVA", "plY: " + player.getY() + ", plIY:" + playerInitY + ", top:" + (int) (player.getY() - playerInitY) * GameCostants.BOX_SIZE);
		lp.topMargin = (int) (player.getY() - playerInitY) * GameCostants.BOX_SIZE;
		lp.leftMargin = (int) (player.getX() - playerInitX) * GameCostants.BOX_SIZE;
		lp.bottomMargin = ((RelativeLayout) map.getParent()).getHeight() - lp.height - lp.topMargin;
		lp.rightMargin = ((RelativeLayout) map.getParent()).getWidth() - lp.width - lp.leftMargin;
		//Log.d("PROVA","h: "+ mapHeight +", w: "+mapWidth+", lm: "+ lpC.leftMargin/GameCostants.BOX_SIZE +", rm: "+(mcWidth-mapWidth-lpM.leftMargin/GameCostants.BOX_SIZE)+", tm: "+ lpC.topMargin/GameCostants.BOX_SIZE +", bm: "+(mcHeight-mapHeight-lpM.topMargin/GameCostants.BOX_SIZE));
		map.setLayoutParams(lp);

		handler = new Handler();
		currentDirection = Direction.UP;

		for (Obstacle obs : obstacles)
			map.addView(obs);
		for (Trainer t : trainers)
			map.addView(t);
	}

	/**
	 * inizia lo spostamento del Player
	 *
	 * @param d direzione dello spostamento
	 */
	public void startMove(final Direction d) {

		if (!mustMove) {
			//Log.d("PROVA", "INIZIO : " + d.toString());
			mustMove = true;
			final boolean[] mustRotate = {player.getRotation() != d.getDegrees()};
			Log.d("PROVA",""+mustRotate[0]);
			currentDirection=d;

			handler.postDelayed(new Runnable() {
				@Override
				public void run() {

					switch (canMove()){
						case 1:
							if(mustRotate[0]){
								player.setRotation(currentDirection.getDegrees());
								mustRotate[0] =false;
								handler.postDelayed(this,200);
							}else
								movePlayer(currentDirection);
							break;
						case 0:
							handler.postDelayed(this, 50);
							break;
						case -1:
							if(mustRotate[0]){
								player.setRotation(currentDirection.getDegrees());
								mustRotate[0] =false;
							}
					}
				}
			}, 0);
		}
	}


	/**
	 * ferma lo spostamento del Player
	 *
	 */
	public void stopMove() {
			mustMove = false;
	}
	public void changeDirection(Direction d){
		currentDirection=d;
		mustMove=true;
		if(!isMoving && canMove()==1) {
				player.setRotation(currentDirection.getDegrees());
				movePlayer(currentDirection);

		}
	}

	/**
	 * Muove il Player se esso è possibile
	 *
	 * @return
	 * 		<0:no non può muoversi
	 * 		 0:in attesa
	 * 		+1:può muoversi
	 */
	public int canMove() {
		//Log.d("PROVA", "mW: " + game.getWidth() / GameCostants.BOX_SIZE + "  mH: " + game.getHeight() / GameCostants.BOX_SIZE);
		//Log.d("PROVA","Bordi: "+!game.checkMapBounds(d) +"  Collisioni: "+game.checkCollisions(d));
		//Log.d("PROVA", "R-MOSSO: " + currentDirection.toString());

		//controlla che non si stia già muovendo
		if (isMoving)
			return 0;


		//controlla se vi deve essere un movimento
		if (!mustMove) {
			return -1;
		}

		//controlla che il Player non esca dalla mappa
		if (!checkMapBounds(currentDirection))
			return -2;
		//controlla che il Player non entri in un ostacolo o allenatore
		if (checkCollisions(currentDirection))
			return -3;

		//controlla che il player non sia stato bloccato
		if(player.isBlocked())
			return -4;


		return 1;

	}

	/**
	 * Aggiunge un ostacolo alla mappa
	 *
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
	 *
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

	/**
	 * controlla che non il player non venga visto da allenatori
	 * @return se visto da allenatore
	 */
	public Trainer checkTrainer() {
		int moveX = (int) (getX());
		int moveY = (int) (getY());
		for (Trainer t : trainers)
			if (!t.isDisabled() && t.checkView(player, moveX, moveY))
				return t;
		return null;
	}

	/**
	 * controlla che nella voluta direzione il Player non esca dalla mappa
	 *
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
	 *
	 * @param direction direzione del movimento
	 */
	private void movePlayer(final Direction direction) {
		int animationDuration = running ? GameCostants.PLAYER_MOVEMENT_DURATION / 2 : GameCostants.PLAYER_MOVEMENT_DURATION;
		map.animate()
				.translationXBy(-direction.getX() * GameCostants.BOX_SIZE)
				.translationYBy(-direction.getY() * GameCostants.BOX_SIZE)
				.setDuration(animationDuration)
				.setListener(new Animator.AnimatorListener() {
					@Override
					public void onAnimationStart(Animator animation) {
						isMoving = true;
					}

					@Override
					public void onAnimationEnd(Animator animation) {

						//controlla che il Player non venga visto dagli altri allenatori
						final Trainer t;
						if ((t = checkTrainer()) != null) {
							stopMove();
							player.block();
							isMoving=false;

							float deltaX, deltaY = 0;
							ViewPropertyAnimator trainerAnimation = t.animate();
							if ((deltaX = player.getX() - (map.getX() / GameCostants.BOX_SIZE + t.getX())) != 0)
								trainerAnimation = trainerAnimation.xBy((deltaX - (deltaX / Math.abs(deltaX))) * GameCostants.BOX_SIZE).setDuration((long) ((Math.abs(deltaX) - 1) * GameCostants.PLAYER_MOVEMENT_DURATION));
							else if ((deltaY = player.getY() - (map.getY() / GameCostants.BOX_SIZE + t.getY())) != 0)
								trainerAnimation = trainerAnimation.yBy((deltaY - (deltaY / Math.abs(deltaY))) * GameCostants.BOX_SIZE).setDuration((long) ((Math.abs(deltaY) - 1) * GameCostants.PLAYER_MOVEMENT_DURATION));
							else
								Log.w("PROVA", "problema allenatore");
							deltaY = player.getY() - (map.getY() / GameCostants.BOX_SIZE + t.getY());
							Log.d("PROVA", "dX: " + (deltaX - (deltaX / Math.abs(deltaX))) + "  dY:" + (deltaY - (deltaY / Math.abs(deltaY))));
							trainerAnimation.setListener(new Animator.AnimatorListener() {
								@Override
								public void onAnimationStart(Animator animation) {

								}

								@Override
								public void onAnimationEnd(Animator animation) {
									txtController.toggleDialog(true);
									txtController.writeText(t, new Runnable() {
										@Override
										public void run() {
											//player.unlock();
											t.disable();
											onScontro.run();
											player.unlock();
										}
									});
								}

								@Override
								public void onAnimationCancel(Animator animation) {

								}

								@Override
								public void onAnimationRepeat(Animator animation) {

								}
							});

							//

						} else{
							isMoving = false;
							if(canMove()==1 ){
								if(direction!=currentDirection)
									player.setRotation(currentDirection.getDegrees());
								movePlayer(currentDirection);
							}
						}

					}

					@Override
					public void onAnimationCancel(Animator animation) {
					}

					@Override
					public void onAnimationRepeat(Animator animation) {
					}
				})
				.start();
		player.setImageDrawable(context.getDrawable(R.drawable.player_1));
		handler.postDelayed(() -> player.setImageDrawable(context.getDrawable(R.drawable.player_2)), animationDuration / 2);
		handler.postDelayed(() -> player.setImageDrawable(context.getDrawable(R.drawable.player_0)), animationDuration);
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

	/**
	 * ritoran la direzione corrente
	 * @return
	 */
	public Direction getCurrentDirection() {
		return currentDirection;
	}

	/**
	 * ritorna se sta correndo
	 * @return
	 */
	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}
}
