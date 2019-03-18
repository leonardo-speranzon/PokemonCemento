package it.speranzon_galligioni.pokemoncemento.enums;

public enum Direction {
	UP(0, -1, 0),
	DOWN(0, 1, 180),
	LEFT(-1, 0, -90),
	RIGHT(1, 0, 90),
	NONE(0, 0, 0);

	private int x, y, degrees;

	private Direction(int x, int y, int degrees) {
		this.x = x;
		this.y = y;
		this.degrees = degrees;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getDegrees() {
		return degrees;
	}
}
