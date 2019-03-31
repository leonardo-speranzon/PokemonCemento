package it.speranzon_galligioni.pokemoncemento.gameObject;

import android.content.Context;

import it.speranzon_galligioni.pokemoncemento.R;

public class Obstacle extends GameElement {

	/**
	 * Costruttore di ostacolo
	 *
	 * @param context context
	 * @param x       coordinata X (rispetto alla mappa)
	 * @param y       coordinata Y (rispetto alla mappa)
	 * @param height  Altezza
	 * @param width   Larhgezza
	 */
	public Obstacle(Context context, int x, int y, int height, int width) {
		super(context, x, y, height, width, R.drawable.obstacle_wall_repeat);
	}


}