package ru.yandex.incoming34;

public class ElementOfLine {

	public ElementOfLine(Token token, int coordinateX, int coordinateY) {
		this.token = token;
		// System.out.println("Recorded: " + token.typeOfToken);
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
	private final Token token;

	public Token getToken() {
		return token;
	}
}
