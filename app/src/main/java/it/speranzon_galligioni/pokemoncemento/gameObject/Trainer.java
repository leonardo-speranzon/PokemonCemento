package it.speranzon_galligioni.pokemoncemento.gameObject;

import android.content.Context;

import it.speranzon_galligioni.pokemoncemento.GameCostants;
import it.speranzon_galligioni.pokemoncemento.R;
import it.speranzon_galligioni.pokemoncemento.enums.Direction;

public class Trainer extends GameElement {
	private Direction lookDirection;

	private String name;
	private boolean disabled;

	/**
	 * @param context
	 * @param x
	 * @param y
	 * @param lookDirection
	 * @param name
	 */
	public Trainer(Context context, int x, int y, Direction lookDirection, String name) {
		super(context, x, y, 1, 1, R.drawable.trainer);

		switch (lookDirection) {
			case DOWN:
				setRotation(180);
				break;
			case LEFT:
				setRotation(-90);
				break;
			case RIGHT:
				setRotation(90);
				break;

		}

		this.lookDirection = lookDirection;
		this.name = name;
	}

	/**
	 * Controlla se il player viene visto dall'allenatore
	 *
	 * @param player giocatore da controllare
	 * @param moveX  posizione X
	 * @param moveY  posizione Y
	 * @return se è stato visto
	 */
	public boolean checkView(Player player, int moveX, int moveY) {
		return (moveX + getX() + Math.min(lookDirection.getX() * GameCostants.TRAINER_DISTANCE_VIEW, 0) <= player.getX()
				&& moveX + getX() + Math.max((lookDirection.getX() * GameCostants.TRAINER_DISTANCE_VIEW), 0) + 1 >= player.getX() + 1

				&& moveY + getY() + Math.min(lookDirection.getY() * GameCostants.TRAINER_DISTANCE_VIEW, 0) <= player.getY()
				&& moveY + getY() + Math.max(lookDirection.getY() * GameCostants.TRAINER_DISTANCE_VIEW, 0) + 1 >= player.getY() + 1);
	}

	/**
	 * ritorna il nome dell'allenatore
	 *
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * abilita l'allenatore
	 */
	public void enable() {
		disabled = false;
	}

	/**
	 * disabilita l'allenatore
	 */
	public void disable() {
		disabled = true;
	}

	/**
	 * ritorna se l'allenatore è disabilitato
	 *
	 * @return disabled
	 */
	public boolean isDisabled() {
		return disabled;
	}
}
