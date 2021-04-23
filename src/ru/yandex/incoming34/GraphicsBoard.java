package ru.yandex.incoming34;

import java.awt.Button;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import ru.yandex.incoming34.GraphicsBoard.DevidedString;

public class GraphicsBoard extends JFrame {
	class DevidedString {
		
		private DevidedString(String columnPartOfString, String rowPartOfString) {
			super();
			this.rowPartOfString = rowPartOfString;
			this.columnPartOfString = columnPartOfString;
		}

		private final String rowPartOfString;

		public String getRowPartOfString() {
			return rowPartOfString;
		}

		private final String columnPartOfString;

		public String getColumnPartOfString() {
			return columnPartOfString;
		}

	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GraphicsBoard() throws HeadlessException {
		// super();
		setTitle(NAME_OF_GAME);
		System.out.println(Game.getOVERALL_SIZE());
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(300, 300, SIZE_OF_BOARD, SIZE_OF_BOARD);
		GridLayout grid = new GridLayout(Game.getOVERALL_SIZE(), Game.getOVERALL_SIZE());
		setLayout(grid);
		setResizable(false);
		buttons = new JButton[Game.getOVERALL_SIZE()][Game.getOVERALL_SIZE()];
		String string = String.valueOf(Game.EMPTY_SIGN);
		for (int r = 0; r < (Game.getOVERALL_SIZE()); r++) {
			String row = "R" + r;
			for (int c = 0; c < (Game.getOVERALL_SIZE()); c++) {
				String column = "C" + c;
				String command = column + row;
				JButton jButton = new JButton(string);
				jButton.setActionCommand(command);
				jButton.setName(command);
				buttons[c][r] = jButton;
				add(jButton);
				jButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent event) {
						handleUserCommand(event);
						Object src = event.getSource();
						((JButton) src).setText(String.valueOf(Game.CROSS_SIGN));
						
					}
				});

			}

		}
		setVisible(true);
	}

	private DevidedString devideString(String move) {
		int limit = move.indexOf("R");
		String firstString = move.substring(1, (limit));
		String secondString = move.substring(limit + 1);
		//System.out.println(firstString);
		//System.out.println(secondString);
		DevidedString dvdStr = new DevidedString(firstString, secondString);
		return dvdStr;

	}

	private Cell convertEventToCell(ActionEvent event) {
		String move = event.getActionCommand();
		//System.out.println("In actionevent: " + move);
		DevidedString devidedString = devideString(move);
		int tempX = Integer.parseInt(devidedString.getColumnPartOfString());
		int tempY = Integer.parseInt(devidedString.getRowPartOfString());
		//System.out.println("tempX: " + tempX);
		//System.out.println("tempY: " + tempY);
		Cell currentCell = new Cell(tempX, tempY);
		return currentCell;

	}

	private void handleUserCommand (ActionEvent event) {
		Cell oneCell = convertEventToCell(event);
		//Game.setLastFirstPlayerMove(oneCell);
		Game.LastMove.setLastMove(oneCell);
		//AtomicBoolean tempAtmBool = new AtomicBoolean(true);
		//Game.LastMove.setUserMadeMove(tempAtmBool);
		

	}

	private final String NAME_OF_GAME = "Tic Toc";
	private final int SIZE_OF_CELL = 60;
	private final int SIZE_OF_BOARD = SIZE_OF_CELL * Game.getOVERALL_SIZE();
	//private static AtomicBoolean userMadeMove = new AtomicBoolean(false);
	static JButton[][] buttons;

	

	/*public static void setUserMadeMove(AtomicBoolean userPressedKey) {
		GraphicsBoard.userMadeMove = userPressedKey;
	}

	public static AtomicBoolean getUserMadeMove() {
		return userMadeMove;
	} */

	public static void putTokenOnBoard(Cell anotherCell, char sign) {
		int tempX = anotherCell.getCoordinateX();
		int tempY = anotherCell.getCoordinateY();
		String text = String.valueOf(sign);
		buttons[tempX][tempY].setText(text);
		
	}

}
