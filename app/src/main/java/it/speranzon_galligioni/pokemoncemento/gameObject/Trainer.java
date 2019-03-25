package it.speranzon_galligioni.pokemoncemento.gameObject;

import android.content.Context;

import it.speranzon_galligioni.pokemoncemento.GameCostants;
import it.speranzon_galligioni.pokemoncemento.enums.Direction;
import it.speranzon_galligioni.pokemoncemento.enums.Gender;

public class Trainer extends GameElement {
	private Direction lookDirection;
	private Gender gender;

	private String name;
	private boolean disabled;

	/**
	 * Costruttore di Trainer
	 * @param context Context
	 * @param x posizione X
	 * @param y posizione Y
	 * @param lookDirection direzione in cui l'allenatore staguardando
	 * @param name nome dell'allenatore
	 * @param gender gener dell'allenatore
	 */
	public Trainer(Context context, int x, int y, Direction lookDirection, String name, Gender gender) {
		super(context, x, y, 1, 1, gender.getImg0());

		setRotation(lookDirection.getDegrees());

		this.lookDirection = lookDirection;
		this.name = name;
		this.gender = gender;
	}

	/**
	 * Controlla se il giocatore viene visto dall'allenatore
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
	 * ritorna la direzione in cui guarda l'allenatore
	 *
	 * @return
	 */
	public Direction getDirection() {
		return lookDirection;
	}
	/**
	 * ritorna il genere dell'allenatore
	 *
	 * @return
	 */
	public Gender getGender() {
		return gender;
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
