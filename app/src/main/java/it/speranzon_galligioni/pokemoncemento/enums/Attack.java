package it.speranzon_galligioni.pokemoncemento.enums;

public enum Attack {
	AZIONE(10),
	SCATTO(10),
	ATTACCO_RAPIDO(8),
	TERREMOTO(30),
	LANCIAFIAMME(20),
	BOTTA(15),
	IPERRAGGIO(30),
	FENDIFOGLIA(10),
	SPACCAROCCIA(10),
	STRIDIO(8),
	VOLO(15),
	COSTAAPPLAUSO(90),
	YEAHBOYY(12),
	BOOMERANG(13),
	SCATTO_FULMINEO(7),
	MAZZATA(15),
	SIXTY_NINE_OCLOCK(19),
	DOPPIA_BOTTA(25),
	ANDROID_MISSING_R(35),
	ELICOTTERO(12),
	DUB(5),
	LUMEN(10);

	private int damage;

	/**
	 * Costruttore di Attack
	 * @param damage danno
	 */
	Attack(int damage) {
		this.damage = damage;
	}

	/**
	 * Ritoran il danno inflitto dalla mossa
	 * @return danno
	 */
	public int getDamage() {
		return damage;
	}
}
