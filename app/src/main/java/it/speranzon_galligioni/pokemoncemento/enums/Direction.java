package it.speranzon_galligioni.pokemoncemento.enums;

public enum Direction {
	UP(0, -1, 0),
	DOWN(0, 1, 180),
	LEFT(-1, 0, -90),
	RIGHT(1, 0, 90),
	NONE(0, 0, 0);

	private int x, y, degrees;

	/**
	 * Costruttore di Direction
	 * @param x movimento X
	 * @param y movimento Y
	 * @param degrees gradi per la rotazione
	 */
	Direction(int x, int y, int degrees) {
		this.x = x;
		this.y = y;
		this.degrees = degrees;
	}

	/**
	 * Ritorna il movimento X
	 * @return x
	 */
	public int getX() {
		return x;
	}

	/**
	 * Ritorna il movimento Y
	 * @return y
	 */
	public int getY() {
		return y;
	}

	/**
	 * Ritorna i gradi per la rotazione
	 * @return gradi
	 */
	public int getDegrees() {
		return degrees;
	}
}
