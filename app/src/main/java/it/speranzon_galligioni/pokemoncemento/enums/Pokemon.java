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
	 * @param name
	 * @param hp
	 * @param img
	 */
	Pokemon(String name, int hp, int img) {
		this.hp = hp;
		this.img = img;
		this.name = name;
	}

	/**
	 *
	 * @return
	 */
	public int getHp() {
		return hp;
	}

	/**
	 *
	 * @return
	 */
	public int getImg() {
		return img;
	}

	/**
	 *
	 * @return
	 */
	public String getName() {
		return name;
	}
}