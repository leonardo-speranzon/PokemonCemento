package it.speranzon_galligioni.pokemoncemento.gameObject;

import android.content.Context;

import it.speranzon_galligioni.pokemoncemento.R;

public class Player extends GameElement {
	private boolean blocked;

	/**
	 * Costruttore di Player
	 *
	 * @param context Context
	 * @param x       coordinata X
	 * @param y       coordinata Y
	 */
	public Player(Context context, int x, int y) {
		super(context, x, y, 1, 1, R.drawable.player_0);
	}

	/**
	 * blocca il giocatore
	 */
	public void block() {
		blocked = true;
	}

	/**
	 * sblocca il giocatore
	 */
	public void unlock() {
		blocked = false;
	}

	/**
	 * ritoran se il giocatore è bloccato
	 *
	 * @return true se il giocatore è bloccato
	 */
	public boolean isBlocked() {
		return blocked;
	}
}
