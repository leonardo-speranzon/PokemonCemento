package it.speranzon_galligioni.pokemoncemento;

import android.content.Context;

public class Trainer extends GameElement {
	private Direction lookDirection;

	private String name;

	public Trainer(Context context, int x, int y, Direction lookDirection, String name) {
		super(context, x, y, 1, 1, R.drawable.trainer);

		switch (lookDirection) {
			case DOWN:
				setRotation(180);
				break;
			case LEFT:
				setRotation(-90);
				break;
			case RIGHT:
				setRotation(90);
				break;

		}

		this.lookDirection = lookDirection;
		this.name = name;
	}


	public boolean checkView(Player player, int moveX, int moveY) {
		return (moveX + getX() + Math.min(lookDirection.getX() * GameCostants.TRAINER_DISTANCE_VIEW, 0) <= player.getX()
				&& moveX + getX() + Math.max((lookDirection.getX() * GameCostants.TRAINER_DISTANCE_VIEW), 0) + 1 >= player.getX() + 1

				&& moveY + getY() + Math.min(lookDirection.getY() * GameCostants.TRAINER_DISTANCE_VIEW, 0) <= player.getY()
				&& moveY + getY() + Math.max(lookDirection.getY() * GameCostants.TRAINER_DISTANCE_VIEW, 0) + 1 >= player.getY() + 1);
	}

	public String getName() {
		return name;
	}
}
