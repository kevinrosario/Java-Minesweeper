import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class MyPanel extends JPanel {
	
	private static final long serialVersionUID = 3426940946811133635L;
	private static final int GRID_X = 25;
	private static final int GRID_Y = 25;
	private static final int INNER_CELL_SIZE = 70;
	private static final int TOTAL_COLUMNS = 9;
	private static final int TOTAL_ROWS = 9;
	public int x = -1;
	public int y = -1;
	public int mouseDownGridX = 0;
	public int mouseDownGridY = 0;
	private Random generator = new Random();
	JLabel title;
	JLabel unopenedCells;
	JLabel numOfUnopenedCells;
	
	public ArrayList<Integer[]> cellsNearMines = new ArrayList<Integer[]>();
	public Color[][] colorArray = new Color[TOTAL_COLUMNS][TOTAL_ROWS];
	public int[][] minesArray = new int[TOTAL_COLUMNS+1][];
	
	public MyPanel() {   //This is the constructor... this code runs first to initialize
		
		unopenedCells = new JLabel("Uncovered Cells: ");
		unopenedCells.setFont(new Font("TimesRoman", Font.PLAIN, 20));
		numOfUnopenedCells = new JLabel("71");
		numOfUnopenedCells.setFont(new Font("TimesRoman", Font.PLAIN, 20));
		add(unopenedCells);
		add(numOfUnopenedCells);

		if (INNER_CELL_SIZE + (new Random()).nextInt(1) < 1) {	//Use of "random" to prevent unwanted Eclipse warning
			throw new RuntimeException("INNER_CELL_SIZE must be positive!");
		}
		if (TOTAL_COLUMNS + (new Random()).nextInt(1) < 2) {	//Use of "random" to prevent unwanted Eclipse warning
			throw new RuntimeException("TOTAL_COLUMNS must be at least 2!");
		}
		if (TOTAL_ROWS + (new Random()).nextInt(1) < 3) {	//Use of "random" to prevent unwanted Eclipse warning
			throw new RuntimeException("TOTAL_ROWS must be at least 3!");
		}
		
		for (int x = 0; x < TOTAL_COLUMNS; x++) {   //The rest of the grid
			for (int y = 0; y < TOTAL_ROWS; y++) {
				colorArray[x][y] = new Color(0xFFFFFF);
				colorArray[x][y] = Color.WHITE;
			}
		}
		
		//
		for (int i=0; i< minesArray.length;i++) {
			minesArray[i] = new int[2];
			int[] coordinatesArray = minesArray[i]; //stores the array of the coordinates
			for (int j=0; j<minesArray[i].length; j++) {
				int randomNum= generator.nextInt(9);
				if (i>0 && j>0 && verifyCoordinates(minesArray, coordinatesArray[0], randomNum, i)) { //verifies that the number is not repeated on the second iteration
					randomNum = generator.nextInt(9);
					minesArray[i][j] = randomNum;
				}else {
					minesArray[i][j] = randomNum;
				}

			}
		}
	} 
	
	/**
	 * Test if the given X and Y coordinates are in the Coordinate Array
	 * @param minesArray to be verified
	 * @param xCoordinate X Coordinate
	 * @param yCoordinate Y Coordinate
	 * @param iteration Iteration variable of the outside for loop
	 * @return True if the given coordinates are found in the array.
	 */
	public boolean verifyCoordinates(int [][] minesArray, int xCoordinate, int yCoordinate, int iteration) {
		boolean verification = false;
		int[] verificationArray = new int[2];
		for (int i=0; i<iteration ;i++) {
			verificationArray = minesArray[i];
			if (verificationArray[0]==xCoordinate && verificationArray[1]== yCoordinate) { //verifies that coordinates given are in the array supplied
				verification= true;
			}
		} 
		return verification;
	}
	
	public boolean verifyCoordinates(int [][] minesArray, int xCoordinate, int yCoordinate) {
		boolean verification = false;
		int iteration = minesArray.length;
		verification = verifyCoordinates(minesArray, xCoordinate, yCoordinate, iteration);
		return verification;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		//Compute interior coordinates
		Insets myInsets = getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		int x2 = getWidth() - myInsets.right;
		int y2 = getHeight() - myInsets.bottom;
		int width = x2 - x1;
		int height = y2 - y1;
		//Paint the background
		g.setColor(new Color(96,156,225));
		g.fillRect(x1, y1, width + 1, height + 1);
		
		
		g.setColor(Color.BLACK);
		for (int y = 0; y <= TOTAL_ROWS ; y++) {
			g.drawLine(x1 + GRID_X, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)), x1 + GRID_X + ((INNER_CELL_SIZE + 1) * TOTAL_COLUMNS), y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)));
		}
		
		for (int x = 0; x <= TOTAL_COLUMNS; x++) {
			g.drawLine(x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)), y1 + GRID_Y, x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)), y1 + GRID_Y + ((INNER_CELL_SIZE + 1) * (TOTAL_ROWS)));
		}

		for (int x = 0; x < TOTAL_COLUMNS; x++) {
			for (int y = 0; y < TOTAL_ROWS; y++) {
				Color c = colorArray[x][y];
				g.setColor(c);
				g.fillRect(x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 1, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)) + 1, INNER_CELL_SIZE, INNER_CELL_SIZE);
			}
		}
		
		g.setColor(Color.WHITE); 
		g.setFont(new Font("TimesRoman", Font.PLAIN, GRID_X));
		for (int i=0; i<cellsNearMines.size(); i++) {
			Integer[] coordinatesAndMines = cellsNearMines.get(i);
			for (int j=0; j<coordinatesAndMines.length; j++) {
				if (coordinatesAndMines[2] >0) {
					g.drawString(Integer.toString(coordinatesAndMines[2]), x1+ (GRID_X*2) + (coordinatesAndMines[0]* (INNER_CELL_SIZE + 1)), y1 + (GRID_Y*3) + (coordinatesAndMines[1] * (INNER_CELL_SIZE+1)+1));
				}
			}
		}
	}

	// This method helps to find the adjacent boxes that don't have a mine.
	// It is partially implemented since the verify hasn't been discussed in class
	// Verify that the coordinates in the parameters are valid.
	// Also verifies if there are any mines around the x,y coordinate
	public void revealAdjacent(int x, int y){

		if((x<0) || (y<0) || (x>8) || (y>8)){
			return;
			} else if(isRevealed(x, y) && !(nearMine(x, y))){
			colorArray[x][y] = Color.GRAY;
			revealAdjacent(x, y+1);
			revealAdjacent(x, y-1);
			revealAdjacent(x+1, y);
			revealAdjacent(x-1, y);
			} else {return;}
		}
	
	public void showAllMines() {
		for (int i=0; i<minesArray.length; i++) {
			int [] mines = minesArray[i];
			colorArray[mines[0]][mines[1]]= Color.BLACK;
		}
	}
	
	/**
	 * Verifies if there is a near mine of coordinates X and Y
	 * @param x X Coordinate
	 * @param y Y Coordinate
	 * @return return true if there is a near mine. 
	 */
	public boolean nearMine(int x,int y) {
		boolean nearMine = false;
		int nearMines = 0;
		
		if(verifyCoordinates(minesArray, x+1, y) ||
				verifyCoordinates(minesArray, x-1, y) ||
				verifyCoordinates(minesArray, x, y+1) ||
				verifyCoordinates(minesArray, x, y-1) ||
				verifyCoordinates(minesArray, x+1, y+1) || 
				verifyCoordinates(minesArray, x+1, y-1) || 
				verifyCoordinates(minesArray, x-1, y-1) ||
				verifyCoordinates(minesArray, x-1, y+1)) {
			nearMine = true;
			colorArray[x][y] = Color.GRAY;			
		}
		
		
		if(verifyCoordinates(minesArray, x+1, y)) {nearMines +=1;}
		if(verifyCoordinates(minesArray, x-1, y)) {nearMines +=1;}
		if(verifyCoordinates(minesArray, x, y+1)) {nearMines +=1;}
		if(verifyCoordinates(minesArray, x, y-1)) {nearMines +=1;}
		if(verifyCoordinates(minesArray, x+1, y+1)) {nearMines +=1;}
		if(verifyCoordinates(minesArray, x-1, y+1)) {nearMines +=1;}
		if(verifyCoordinates(minesArray, x+1, y-1)) {nearMines +=1;}
		if(verifyCoordinates(minesArray, x-1, y-1)) {nearMines +=1;}
		
		if(nearMine == true) {
			Integer[] minesNearCells = {x,y, nearMines};
			cellsNearMines.add(minesNearCells);
		}
		
 		return nearMine;
	}	
	
	/**
	 * Verifies if the cell at coordinates X and Y is revealed (gray) or not (white)
	 * @param x X Coordinate
	 * @param y Y Coordinate
	 * @return Return True if not revealed (white) and False (gray)
	 */
	public boolean isRevealed(int x, int y) {
		boolean verification = true;
		if ((x<9 || x>=0 || y<9 || y >=0) && colorArray[x][y] == Color.GRAY) {
			verification = false;
		}
		return verification;
	}
	
	public int getGridX(int x, int y) {
		Insets myInsets = getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		x = x - x1 - GRID_X;
		y = y - y1 - GRID_Y;
		if (x < 0) {   //To the left of the grid
			return -1;
		}
		if (y < 0) {   //Above the grid
			return -1;
		}

		if ((x % (INNER_CELL_SIZE + 1) == 0) || (y % (INNER_CELL_SIZE + 1) == 0)) {   //Coordinate is at an edge; not inside a cell
			return -1;
		}
		x = x / (INNER_CELL_SIZE + 1);
		y = y / (INNER_CELL_SIZE + 1);
		if (x > 8) {
			return -1;
		}
		return x;
	}
	
	public int getGridY(int x, int y) {
		Insets myInsets = getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		x = x - x1 - GRID_X;
		y = y - y1 - GRID_Y;
		if (x < 0) {   //To the left of the grid
			return -1;
		}
		
		if (y < 0) {   //Above the grid
			return -1;
		}
		if ((x % (INNER_CELL_SIZE + 1) == 0) || (y % (INNER_CELL_SIZE + 1) == 0)) {   //Coordinate is at an edge; not inside a cell
			return -1;
		}
		
		x = x / (INNER_CELL_SIZE + 1);
		y = y / (INNER_CELL_SIZE + 1);
		if (y > 8) {
			return -1;
		}
		return y;
	}
	
	/**
	 * Checks whether the user has won or not.
	 * @return Return True if the user has won, false otherwise.
	 */
	public boolean checkGameStatus() {
		int counter = 0;
		for(int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if(colorArray[i][j] == Color.WHITE || colorArray[i][j] == Color.RED && verifyCoordinates(minesArray, i, j)) {	
					counter++;
				}
			}
		}

		this.numOfUnopenedCells.setText("" + (counter - 10));
		if (counter == 10) {
			return true;
		}
		return false;
	}
}