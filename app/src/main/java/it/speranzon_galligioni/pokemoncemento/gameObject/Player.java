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
	 * Blocca il giocatore sul posto
	 */
	public void block() {
		blocked = true;
	}

	/**
	 * Sblocca il giocatore
	 */
	public void unlock() {
		blocked = false;
	}

	/**
	 * Se il giocatore è bloccato
	 *
	 * @return true se il giocatore è bloccato
	 */
	public boolean isBlocked() {
		return blocked;
	}
}
