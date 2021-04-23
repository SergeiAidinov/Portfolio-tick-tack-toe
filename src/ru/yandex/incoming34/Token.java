package ru.yandex.incoming34;

public class Token {
	public Token(TypeOfToken typeOfToken) {
		this.typeOfToken = typeOfToken;
	}

	enum TypeOfToken {
		EMPTY, CROSS, ZERO
	}

	TypeOfToken typeOfToken;

	public TypeOfToken getTypeOfToken() {
		return typeOfToken;
	}

	public void setTypeOfToken(TypeOfToken typeOfToken) {
		this.typeOfToken = typeOfToken;
	}

	public void showSign() {
		switch (typeOfToken) {
		case CROSS:
			System.out.print(Game.CROSS_SIGN);
			break;
		case ZERO:
			System.out.print(Game.ZERO_SIGN);
			break;
		case EMPTY:
			System.out.print(Game.EMPTY_SIGN);
			break;

		default:
			break;
		}

	}

}
