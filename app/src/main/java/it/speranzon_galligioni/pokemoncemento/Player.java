package it.speranzon_galligioni.pokemoncemento;

import android.content.Context;

public class Player extends GameElement {
	private boolean blocked;
	public Player(Context context, int x, int y) {
		super(context, x, y, 1, 1, R.drawable.man_2);
	}
	public void block(){blocked=true;}
	public void unlock(){blocked=false;}
	public boolean isBlocked(){return blocked;}
}
