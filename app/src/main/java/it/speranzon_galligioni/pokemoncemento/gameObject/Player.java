package it.speranzon_galligioni.pokemoncemento.gameObject;

import android.content.Context;

import it.speranzon_galligioni.pokemoncemento.R;

public class Player extends GameElement {
	private boolean blocked;
	public Player(Context context, int x, int y) {
		super(context, x, y, 1, 1, R.drawable.player_0);
	}

	/**
	 * blocca il player
	 */
	public void block(){blocked=true;}
	/**
	 * sblocca il player
	 */
	public void unlock(){blocked=false;}

	/**
	 * ritoran se il player è bloccato
	 * @return
	 */
	public boolean isBlocked(){return blocked;}
}
