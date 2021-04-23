package ru.yandex.incoming34;

public class ReportOfUserMove {
	public ReportOfUserMove(boolean isMoveCorrect, String message) {
		super();
		this.isMoveCorrect = isMoveCorrect;
		this.message = message;
	}

	private final boolean isMoveCorrect;

	public boolean isMoveCorrect() {
		return isMoveCorrect;
	}

	public String getMessage() {
		return message;
	}

	private final String message;
}
