package it.speranzon_galligioni.pokemoncemento.enums;

import it.speranzon_galligioni.pokemoncemento.R;

public enum ObstacleTypes {
	MOUNTAIN(R.drawable.obstacle_mountain),
	WALL(R.drawable.obstacle_wall_repeat);

	public final int drawableRes;

	/**
	 * Costruttore di ObstacleTypes
	 *
	 * @param res risorsa
	 */
	ObstacleTypes(int res) {
		drawableRes = res;
	}

	/**
	 * Ritorna la risorsa
	 *
	 * @return la risorsa
	 */
	public int getDrawableRes() {
		return drawableRes;
	}

	/**
	 * Ritorna ObstacleTypes di default
	 *
	 * @return ObstacleTypes di default
	 */
	public static ObstacleTypes getDefault() {
		return WALL;
	}
}