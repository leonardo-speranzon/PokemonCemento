package it.speranzon_galligioni.pokemoncemento.gameObject;

import android.content.Context;

import it.speranzon_galligioni.pokemoncemento.enums.ObstacleTypes;

public class Obstacle extends GameElement {

	/**
	 * Costruttore di ostacolo
	 * @param context context
	 * @param x coordinata X (rispetto alla mappa)
	 * @param y coordinata Y (rispetto alla mappa)
	 * @param height Altezza
	 * @param width Larhgezza
	 * @param type tipo di ostacolo
	 */
	public Obstacle(Context context, int x, int y, int height, int width, ObstacleTypes type) {
		super(context, x, y, height, width, type.drawableRes);
	}


}