package it.speranzon_galligioni.pokemoncemento.enums;

import it.speranzon_galligioni.pokemoncemento.R;

public enum ObstacleTypes {
	MOUNTAIN(R.drawable.obstacle_mountain),
	WALL(R.drawable.obstacle_wall);

	public final int drawableRes;

	ObstacleTypes(int res) {
		drawableRes = res;
	}

	public int getDrawableRes() {
		return drawableRes;
	}

	public static ObstacleTypes getDefault() {
		return MOUNTAIN;
	}
}