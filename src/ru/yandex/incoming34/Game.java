package ru.yandex.incoming34;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Delayed;
import java.util.concurrent.atomic.AtomicBoolean;

import ru.yandex.incoming34.Token.TypeOfToken;

public class Game {
	int nulls = 0;

	public static class LastMove {
		private static Cell lastMoveCell;
		private static AtomicBoolean userMadeMove = new AtomicBoolean(false);

		public static Cell getLastMoveCell() {
			return lastMoveCell;
		}


		public static boolean getUserMadeMove() {
			if (userMadeMove.get() == false) {
				return false;
			} else {
				return true;
			}
			
		}

		public static void setLastMove(Cell cell) {
			userMadeMove.set(false);
			lastMoveCell = cell;
			userMadeMove.set(true);

		}

		public static void clearLastMove() {
			userMadeMove.set(false);
			lastMoveCell = null;
		}
	}

	private static final char SIGN_FOR_BEGINNING_OF_LINE = (char) 8594;
	static final char EMPTY_SIGN = (char) 9732;
	static final char CROSS_SIGN = 'X';
	static final char ZERO_SIGN = 'O';
	private static final boolean GRAPICS_MODE = true;
	private final String DELIMITER = " ";
	private static final int OVERALL_SIZE = 10;

	public static int getOVERALL_SIZE() {
		return OVERALL_SIZE;
	}

	private final int WINNING_LENGHT = 4;
	private final int QUANTITY_OF_POSSIBLE_MOVES = OVERALL_SIZE * OVERALL_SIZE;
	int movesLeft = QUANTITY_OF_POSSIBLE_MOVES;
	Token[][] gameBoard = new Token[OVERALL_SIZE][OVERALL_SIZE];
	/*
	 * private static Cell lastMove = null; public static Cell getLastMove() {
	 * return lastMove; }
	 * 
	 * public static void setLastMove(Cell lastMove) { Game.lastMove = lastMove; }
	 */

	// public static boolean userPressedButton;
	private static Cell lastFirstPlayerMove = null;

	public static Cell getLastFirstPlayerMove() {
		return lastFirstPlayerMove;
	}

	public static void setLastFirstPlayerMove(Cell lastFirstPlayerMove) {
		Game.lastFirstPlayerMove = lastFirstPlayerMove;
		System.out.println("lastFirstPlayerMove: " + lastFirstPlayerMove);
	}

	private static Cell lastSecondPlayerMove = null;
	private final String[] directions = { "NORTH", "NORTH_EAST", "EAST", "SOUTH_EAST", "SOUTH", "SOUTH_WEST", "WEST",
			"NORTH_WEST" };

	enum currentPlayer {
		FIRST_PLAYER, SECOND_PLAYER
	};

	private static currentPlayer activePlayer;

	public static currentPlayer getActivePlayer() {
		return activePlayer;
	}

	enum typeOfPlayer {
		HUMAN, COMPUTER
	};

	typeOfPlayer firstPlayer = typeOfPlayer.HUMAN;
	typeOfPlayer secondPlayer = typeOfPlayer.COMPUTER;

	boolean gameBoardCreated = false;
	Scanner scanner = new Scanner(System.in);

	public void gameProcess() {
		gameBoard = initiateBoard(gameBoard);
		showBoard(gameBoard);
		createGraphicsBoard();

		while (true) {
			activePlayer = currentPlayer.FIRST_PLAYER;
			chekForNextMove();
			// LastMove.clearLastMove();
			System.out.println("Turn of First player");
			if (firstPlayer == typeOfPlayer.HUMAN) {
				humanTurn();
			} else {
				aiTurn(gameBoard);
			}

			showBoard(gameBoard);
			// Line[] lineArray = createArrayOfLines(gameBoard);
			if (checkWinner(gameBoard, WINNING_LENGHT, TypeOfToken.CROSS)) {
				System.out.println("First player Won!!!");
				exitFromProgram();
			}

			activePlayer = currentPlayer.SECOND_PLAYER;
			chekForNextMove();
			LastMove.clearLastMove();
			System.out.println("Turn of Second player.");
			if (secondPlayer == typeOfPlayer.COMPUTER) {
				aiTurn(gameBoard);
			} else {
				humanTurn();
			}

			showBoard(gameBoard);
			if (checkWinner(gameBoard, WINNING_LENGHT, TypeOfToken.ZERO)) {
				System.out.println("Second player  Won!!!");
				exitFromProgram();
			}

		}
	}

	private void chekForNextMove() {
		if (movesLeft == 0) {
			System.out.println("There is no possible moves. Sorry...");
			exitFromProgram();
		}
	}

	private void aiTurn(Token[][] gBrd) {
		System.out.println("In iaTurn()");
		System.out.print("Turn left: " + movesLeft + DELIMITER);
		LastMove.clearLastMove();
		Random random = new Random();
		Cell anotherCell = null;
		int tempX = 0;
		int tempY = 0;
		boolean aiMadeMove = false;
		while (!aiMadeMove) {
			tempX = random.nextInt(OVERALL_SIZE + 1);
			tempY = random.nextInt(OVERALL_SIZE + 1);
			anotherCell = new Cell(tempX, tempY);
			ReportOfUserMove reportOfUserMove = estimateMove(gBrd, anotherCell);
			aiMadeMove = reportOfUserMove.isMoveCorrect();
			if (aiMadeMove) {
				break;
			}
		}

		gBrd[tempX][tempY] = new Token(TypeOfToken.ZERO);
		if (GRAPICS_MODE) {
			GraphicsBoard.putTokenOnBoard(anotherCell, ZERO_SIGN);
		}
		LastMove.setLastMove(anotherCell);
		lastSecondPlayerMove = anotherCell;
		System.out.println("Artificial intellect made move: " + LastMove.getLastMoveCell().getCoordinateX() + DELIMITER
				+ LastMove.getLastMoveCell().getCoordinateY());
		movesLeft--;

	}

	private void exitFromProgram() {
		scanner.close();
		System.out.println("Goog bye!");
		System.exit(0);

	}

	private boolean checkWinner(Token[][] board, int winningLength, TypeOfToken typeOfToken) {
		boolean winner = false;
		for (int x = 0; x < board.length; x++) {
			for (int y = 0; y < board[0].length; y++) {
				for (int dir = 0; dir < directions.length; dir++) {
					Cell initialCell = new Cell(x, y);
					Line anotherLine = createLine(board, initialCell, directions[dir]);
					// anotherLine.showLine();
					// System.out.println();
					winner = isLineWinning(anotherLine, winningLength, typeOfToken);
					if (winner) {
						return true;
					}
				}
			}
		}

		return false;
	}

	private boolean isLineWinning(Line line, int winningLength, TypeOfToken typeOfToken) {
		// System.out.println("In isLineWinning");
		int matches = 0;
		ElementOfLine[] elements = line.getArrayOfElements();
		boolean winning = false;
		if (elements.length < winningLength) {
			return winning;
		}
		for (int i = 0; i < elements.length; i++) {
			// System.out.println("In isLineWinning " +
			// elements[i].getToken().getTypeOfToken());
			// System.out.println("To be compared with " + typeOfToken);
			if (elements[i].getToken().getTypeOfToken() == (typeOfToken)) {
				matches++;
				// System.out.println("MATCH " + matches);
			} else {
				break;
			}
		}
		if (matches >= winningLength) {
			winning = true;
		}
		return winning;
	}

	private Line createLine(Token[][] array, Cell initialCell, String direction) {
		Increments increments = calculateIncrements(direction);
		ArrayList<ElementOfLine> tempList = new ArrayList<>();
		int tempX = initialCell.getCoordinateX();
		int tempY = initialCell.getCoordinateY();
		// System.out.print("Beginning of line :" + tempX + " "+ tempY + " "+ direction
		// + " ");

		Cell nextCell = initialCell;
		// System.out.println("Creating line: " + direction);
		while (doesCellBelongToArray(array, nextCell)) {
			ElementOfLine element = new ElementOfLine(array[tempX][tempY], tempX, tempY);
			tempList.add(element);
			tempX = tempX + increments.getIncrementX();
			tempY = tempY + increments.getIncrementY();
			nextCell = new Cell(tempX, tempY);
		}
		Line line = new Line(tempList);
		// line.showLine();
		// System.out.println();
		return line;
	}

	private Increments calculateIncrements(String currentDirection) {
		int incremX = 0;
		int incremY = 0;
		switch (currentDirection) {
		case "NORTH":
			incremX = 0;
			incremY = -1;
			break;

		case "NORTH_EAST":
			incremX = 1;
			incremY = -1;
			break;

		case "EAST":
			incremX = 1;
			incremY = 0;
			break;

		case "SOUTH_EAST":
			incremX = 1;
			incremY = 1;
			break;
		case "SOUTH":
			incremX = 0;
			incremY = 1;
			break;

		case "SOUTH_WEST":
			incremX = -1;
			incremY = 1;
			break;

		case "WEST":
			incremX = -1;
			incremY = 0;
			break;

		case "NORTH_WEST":
			incremX = -1;
			incremY = -1;
			break;
		default:
			incremX = 2;
			incremY = -2;
		}

		Increments increments = new Increments(incremX, incremY);
		return increments;
	}

	private void humanTurn() {
		System.out.println("In humanTurn()");
		boolean userMadeMove = false;
		Cell currentCell = null;
		while (!userMadeMove) {
			LastMove.clearLastMove();
			System.out.print("Turn left: " + movesLeft + DELIMITER);
			if (GRAPICS_MODE) {
				currentCell = receiveMoveFromGraficsBoard();
			} else {
				currentCell = askKeyBoard();
			}
			if (currentCell == null) { // Это только для отладки!!!
				nulls++;
				System.out.println("NULL NULL NULL NULL NULL " + nulls);
				continue; // Уже не вызывается, т.к. отладил работу AtomicBoolean!!!
			}
			ReportOfUserMove reportOfUserMove = estimateMove(gameBoard, currentCell);
			if (reportOfUserMove.isMoveCorrect()) {
				userMadeMove = true;
				int tempX = LastMove.getLastMoveCell().getCoordinateX();
				int tempY = LastMove.getLastMoveCell().getCoordinateY();
				Token oneToken = new Token(TypeOfToken.CROSS);
				gameBoard[tempX][tempY] = oneToken;
				movesLeft--;

			} else {
				System.out.println(reportOfUserMove.getMessage());

			}
		}

	}

	private Cell receiveMoveFromGraficsBoard() {
		System.out.println("in receiveMoveFromGraficsBoard()");
		Cell tempCell = null;

		while (LastMove.getUserMadeMove() == false) {
			//System.out.println("In while: " + LastMove.getUserMadeMove());
		}
		tempCell = LastMove.getLastMoveCell();
		return tempCell;

	}

	private void doNothing() {
		// System.out.println("*****************");
		return;

	}

	private ReportOfUserMove estimateMove(Token[][] array, Cell oneCell) {
		ReportOfUserMove oneReport;
		if (!doesCellBelongToArray(array, oneCell)) {
			oneReport = new ReportOfUserMove(false, "Selected cell does not belong to gameboard!");
		} else {
			int tempX = oneCell.getCoordinateX();
			int tempY = oneCell.getCoordinateY();
			if (array[tempX][tempY].getTypeOfToken() != TypeOfToken.EMPTY) {
				oneReport = new ReportOfUserMove(false, "Selected cell is already engaged!");
			} else {
				oneReport = new ReportOfUserMove(true, null);
			}
		}

		return oneReport;
	}

	private boolean doesCellBelongToArray(Token[][] array, Cell elementOfLine) {
		boolean belongs = true;
		int tempX = elementOfLine.getCoordinateX();
		int tempY = elementOfLine.getCoordinateY();
		if ((tempX < 0) || (tempY < 0))
			belongs = false;
		if ((tempX > array.length - 1) || (tempY > array[0].length - 1))
			belongs = false;
		return belongs;

	}

	private Cell askKeyBoard() {
		Cell keyBoardCell = null;
		boolean formatControllPassed = false;
		System.out.print(" Enter your move: ");
		while (!formatControllPassed) {
			if (scanner.hasNext()) {
				if (scanner.hasNextInt()) {
					int x = scanner.nextInt();
					int y = scanner.nextInt();
					keyBoardCell = new Cell(x, y);
					formatControllPassed = true;
				} else {
					System.out.println("Format of your answer is incorrect!");
					scanner.next();
				}
			}
		}
		LastMove.setLastMove(keyBoardCell);
		return keyBoardCell;

	}

	private Token[][] initiateBoard(Token[][] brd) {
		Token[][] initializedBoard = new Token[brd.length][brd[0].length];
		Token emptyToken = new Token(TypeOfToken.EMPTY);
		for (int y = 0; y < brd[0].length; y++) {
			for (int x = 0; x < brd.length; x++) {
				initializedBoard[x][y] = emptyToken;
			}

		}
		return initializedBoard;
	}

	private void showBoard(Token[][] gameBoard) {
		if (!GRAPICS_MODE) {
			System.out.print("   ");
			for (int i = 0; i < gameBoard.length; i++) {
				System.out.print(DELIMITER + i + DELIMITER);
				if (i > 8) {
					System.out.print("...");
					break;
				}
			}
			System.out.println();
			for (int y = 0; y < gameBoard[0].length; y++) {
				System.out.printf("%2d", y);
				System.out.print(SIGN_FOR_BEGINNING_OF_LINE);
				for (int x = 0; x < gameBoard.length; x++) {
					Cell cell = new Cell(x, y);

					System.out.print(DELIMITER + showToken(gameBoard, cell) + DELIMITER);
				}
				System.out.println();
			}

		} else {

			return;
		}
	}

	private void createGraphicsBoard() {
		if (gameBoardCreated) {
			// System.out.println("Already exists!!!");
			return;
		}
		GraphicsBoard grapicsBoard = new GraphicsBoard();
		gameBoardCreated = true;
		// grapicsBoard.createGameBoard();

	}

	public char showToken(Token[][] board, Cell tempCell) {
		char response;
		int coordinateX = tempCell.getCoordinateX();
		int coordinadeY = tempCell.getCoordinateY();
		Token tempToken = board[coordinateX][coordinadeY];
		TypeOfToken currentType = tempToken.getTypeOfToken();
		switch (currentType) {
		case EMPTY:
			response = EMPTY_SIGN;
			break;
		case CROSS:
			response = CROSS_SIGN;
			break;
		case ZERO:
			response = ZERO_SIGN;
			break;

		default:
			response = EMPTY_SIGN;
			break;
		}
		return response;
	}

}
