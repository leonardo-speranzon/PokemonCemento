package it.speranzon_galligioni.pokemoncemento.enums;

import it.speranzon_galligioni.pokemoncemento.R;

public enum ObstacleTypes {
	MOUNTAIN(R.drawable.obstacle_mountain),
	WALL(R.drawable.obstacle_wall_repeat);

	public final int drawableRes;

	/**
	 * @param res
	 */
	ObstacleTypes(int res) {
		drawableRes = res;
	}

	/**
	 * @return
	 */
	public int getDrawableRes() {
		return drawableRes;
	}

	/**
	 * @return
	 */
	public static ObstacleTypes getDefault() {
		return WALL;
	}
}