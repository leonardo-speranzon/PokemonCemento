package it.speranzon_galligioni.pokemoncemento;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

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

	public Game(RelativeLayout map, int height, int width, Player player, TextController txtController, Context context) {
		this(map, height, width, new ArrayList<Obstacle>(), new ArrayList<Trainer>(), player, txtController, context);
	}

	public Game(RelativeLayout map, int height, int width, List<Obstacle> obstacles, List<Trainer> trainers, Player player, TextController txtController, Context context) {
		this(map, height, width, 0, 0, new ArrayList<Obstacle>(), trainers, player, txtController, context);
	}

	public Game(RelativeLayout map, int height, int width, int playerInitX, int playerInitY, List<Obstacle> obstacles, List<Trainer> trainers, Player player, TextController txtController, Context context) {
		this.obstacles = new ArrayList<>(obstacles);
		this.trainers = new ArrayList<>(trainers);
		this.player = player;
		this.map = map;
		this.txtController = txtController;
		this.context = context;
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
		currentDirection = Direction.NONE;

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

		if (currentDirection == Direction.NONE) {

			//Log.d("PROVA", "INIZIO : " + d.toString());
			mustMove = true;
			currentDirection = d;
			handler.postDelayed(new Runnable() {
				@Override
				public void run() {
					if (!move())
						handler.postDelayed(this, 50);
				}
			}, 0);
		}
	}


	/**
	 * ferma lo spostamento del Player
	 *
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
	 *
	 * @return ritorna falso se il movimento precedente non è ancora terinato
	 * !!QUANDO RITORNA TRUE NON VUOL DIRE CHE IL MOVIMENTO E' AVVENUTO!!
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

		//controlla che il player non sia stato bloccato
		if(player.isBlocked())
			return true;

		//Log.d("PROVA", "MOSSO  : " + currentDirection.toString());
		movePlayer(currentDirection);

		return true;

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

	public Trainer checkTrainer(Direction direction) {
		int moveX = (int) (getX() + -direction.getX());
		int moveY = (int) (getY() + -direction.getY());
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
						if ((t = checkTrainer(currentDirection)) != null) {
							player.block();
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
											final View main=((Activity) context).findViewById(R.id.root);
											((Activity) context).setContentView(R.layout.activity_scontro);
											handler.postDelayed(new Runnable() {
												@Override
												public void run() {
													((Activity) context).setContentView(main);
													player.unlock();
												}
											},2000);
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

							//stopMove(getCurrentDirection());
							isMoving=false;
						} else{
							isMoving = false;
							move();
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
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				player.setImageDrawable(context.getDrawable(R.drawable.man_3));
			}
		}, animationDuration / 4);
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				player.setImageDrawable(context.getDrawable(R.drawable.man_2));
			}
		}, animationDuration / 4 * 3);
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

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}
}
