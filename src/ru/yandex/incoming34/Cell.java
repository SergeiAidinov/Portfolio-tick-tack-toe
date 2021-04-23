package ru.yandex.incoming34;

public class Cell {
	public Cell(int coordinateX, int coordinateY) {
		super();
		this.coordinateX = coordinateX;
		this.coordinateY = coordinateY;
	}

	private final int coordinateX;

	public int getCoordinateX() {
		return coordinateX;
	}

	public int getCoordinateY() {
		return coordinateY;
	}

	private final int coordinateY;

}
