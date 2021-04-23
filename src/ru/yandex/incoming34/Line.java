package ru.yandex.incoming34;

import java.util.ArrayList;

public class Line { // Auxiliary class. Represents a line, which was selected by methods of other
					// class.
					// Contains only Cells (without Tokens)
	private ElementOfLine[] arrayOfElements;

	public ElementOfLine[] getArrayOfElements() {
		return arrayOfElements;
	}

	public Line(ArrayList<ElementOfLine> ongoingLine) {
		arrayOfElements = new ElementOfLine[ongoingLine.size()];
		arrayOfElements = ongoingLine.toArray(arrayOfElements);
	}

	public ElementOfLine[] getCellsOfLine() {
		return arrayOfElements;
	}

	public int getLengthOfLine() {
		return arrayOfElements.length;
	}

	public void showLine() {
		for (int i = 0; i < arrayOfElements.length; i++) {
			Token tempToken = (arrayOfElements[i].getToken());
			tempToken.showSign();
		}

	}
}
