package it.speranzon_galligioni.pokemoncemento.gameObject;

import android.content.Context;

import it.speranzon_galligioni.pokemoncemento.R;

public class Treasure extends GameElement {
	/**
	 * Costruttore di Treasure
	 *
	 * @param context context
	 * @param x       coordinata X
	 * @param y       coordinata Y
	 */
	public Treasure(Context context, int x, int y) {
		super(context, x, y, 1, 1, R.drawable.treasure);
	}
}
