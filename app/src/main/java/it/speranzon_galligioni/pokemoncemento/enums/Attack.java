package it.speranzon_galligioni.pokemoncemento.enums;

public enum Attack {
	AZIONE(10),
	SCATTO(10),
	ATTACCO_RAPIDO(8),
	TERREMOTO(30),
	LANCIAFIAMME(20),
	BOTTA(15);

	private int damage;

	Attack(int damage) {
		this.damage = damage;
	}

	public int getDamage() {
		return damage;
	}
}
