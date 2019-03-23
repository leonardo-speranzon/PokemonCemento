package it.speranzon_galligioni.pokemoncemento.enums;

public enum Direction {
	UP(0, -1, 0),
	DOWN(0, 1, 180),
	LEFT(-1, 0, -90),
	RIGHT(1, 0, 90),
	NONE(0, 0, 0);

	private int x, y, degrees;

	/**
	 * @param x
	 * @param y
	 * @param degrees
	 */
	Direction(int x, int y, int degrees) {
		this.x = x;
		this.y = y;
		this.degrees = degrees;
	}

	/**
	 *
	 * @return
	 */
	public int getX() {
		return x;
	}

	/**
	 *
	 * @return
	 */
	public int getY() {
		return y;
	}

	/**
	 *
	 * @return
	 */
	public int getDegrees() {
		return degrees;
	}
}
