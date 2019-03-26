package it.speranzon_galligioni.pokemoncemento.enums;

import it.speranzon_galligioni.pokemoncemento.R;

public enum Pokemon {
	GRIGINOMON("Grigimon", 120, R.drawable.griginomon),
	CEMENTOKARP("Cementokarp", 55, R.drawable.cementokarp),
	POKETROTA("Poketrota", 65, R.drawable.poketrota),
	LOLLISEPT("LolliSept", 60, R.drawable.lollisept),
	ROBOANDROID("RoboAndroid", 70, R.drawable.roboandroid),
	SNEAKMON("Sneakmon", 65, R.drawable.sneakmon),
	POKETUX("PokeTux", 75, R.drawable.poketux),
	STEVECHOMP("Stevechomp", 70, R.drawable.stevechomp);

	private int hp, img;
	private String name;

	/**
	 * Costruttore di Pokemon
	 *
	 * @param name nome del pokemon
	 * @param hp   punti vita
	 * @param img  immagine del pokemon
	 */
	Pokemon(String name, int hp, int img) {
		this.hp = hp;
		this.img = img;
		this.name = name;
	}

	/**
	 * Ritorna i punti vita del ookemon
	 *
	 * @return punti vita
	 */
	public int getHp() {
		return hp;
	}

	/**
	 * Ritorna l'immagine del ookemon
	 *
	 * @return punti vita
	 */
	public int getImg() {
		return img;
	}

	/**
	 * Ritorna il nome del ookemon
	 *
	 * @return punti vita
	 */
	public String getName() {
		return name;
	}
}