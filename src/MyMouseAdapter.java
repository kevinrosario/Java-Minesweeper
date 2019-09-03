import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MyMouseAdapter extends MouseAdapter {
	
	
	public void mousePressed(MouseEvent e) {
		Component c = e.getComponent();
		while (!(c instanceof JFrame)) {
			c = c.getParent();
			if (c == null) {
				return;
			}
		}
		
		JFrame myFrame = (JFrame) c;
		MyPanel myPanel = (MyPanel) myFrame.getContentPane().getComponent(0);
		Insets myInsets = myFrame.getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		switch (e.getButton()) {
			case 1: //left mouse button
				e.translatePoint(-x1, -y1);
				int x = e.getX();
				int y = e.getY();
				myPanel.x = x;
				myPanel.y = y;
				myPanel.mouseDownGridX = myPanel.getGridX(x, y);
				myPanel.mouseDownGridY = myPanel.getGridY(x, y);
				myPanel.repaint();
				break;
			case 3:		//Right mouse button
				e.translatePoint(-x1, -y1);
				x = e.getX();
				y = e.getY();
				myPanel.x = x;
				myPanel.y = y;
				myPanel.mouseDownGridX = myPanel.getGridX(x, y);
				myPanel.mouseDownGridY = myPanel.getGridY(x, y);
				myPanel.repaint();
				break;
			default:    //Some other button (2 = Middle mouse button, etc.)
				//Do nothing
				break;
		}
	}
	public void mouseReleased(MouseEvent e) {
		Component c = e.getComponent();
		while (!(c instanceof JFrame)) {
			c = c.getParent();
			if (c == null) {
				return;
			}
		}
		JFrame myFrame = (JFrame)c;
		MyPanel myPanel = (MyPanel) myFrame.getContentPane().getComponent(0);  //Can also loop among components to find MyPanel
		Insets myInsets = myFrame.getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		e.translatePoint(-x1, -y1);
		int x = e.getX();
		int y = e.getY();
		myPanel.x = x;
		myPanel.y = y;
		int gridX = myPanel.getGridX(x, y);
		int gridY = myPanel.getGridY(x, y);
		
		switch (e.getButton()) {
			case 1:		//Left mouse button
				if ((myPanel.mouseDownGridX == -1) || (myPanel.mouseDownGridY == -1)) {
					//Had pressed outside
					//Do nothing
				} else {
					if ((gridX == -1) || (gridY == -1)) {
						//Is releasing outside
						//Do nothing
					} else {
						if ((myPanel.mouseDownGridX != gridX) || (myPanel.mouseDownGridY != gridY)) {
							//Released the mouse button on a different cell where it was pressed
							//Do nothing
						} else {
							if (myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY] == Color.RED) {
								//do nothing
								} else {
									if (myPanel.verifyCoordinates(myPanel.minesArray, myPanel.mouseDownGridX, myPanel.mouseDownGridY)){
										//Released the mouse on a mine
										myPanel.showAllMines(); 
										myPanel.repaint();

										Object[] options = { "Exit Game", "Try Again" };
										new JOptionPane();
										int tryAgainLose = JOptionPane.showOptionDialog(null, "You have exploited a mine", "GAME OVER!", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[1]);
										if(tryAgainLose == 1) {
											Main.playAgain();	 
										} else { //Play Again
											System.exit(0);
											}
										} else {
											//Released the mouse button on the same cell where it was pressed
											myPanel.revealAdjacent(gridX, gridY);
								}
							}
						}
					}
				}
				myPanel.repaint();
				break;
			case 3:		//Right mouse button
				if ((myPanel.mouseDownGridX == -1) || (myPanel.mouseDownGridY == -1)) {
					//Had pressed outside
					//Do nothing
				} else {
					if ((gridX == -1) || (gridY == -1)) {
						//Is releasing outside
						//Do nothing
					} else {
						if ((myPanel.mouseDownGridX != gridX) || (myPanel.mouseDownGridY != gridY)) {
							//Released the mouse button on a different cell where it was pressed
							//Do nothing
						} else {
							if (myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY] == Color.GRAY) {
								//Do nothing
							} else {							// Check whether there was a flag in the clicked grid or not, if so the grid is changed back to uncovered (White).
								if (myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY] == Color.RED) {
									myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = Color.WHITE;
									} else { // If NOT, a flag is placed in the grid(is painted in red).
									myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = Color.RED;
								}
							}
						}
					}
				}		
				myPanel.repaint();
				break;
			default:    //Some other button (2 = Middle mouse button, etc.)
				//Do nothing
				break;
		}
		
		if (myPanel.checkGameStatus()) {
			Object[] options = { "Exit Game", "Play Again" };
			new JOptionPane();
			int tryAgainLose = JOptionPane.showOptionDialog(null, "WIN!!!", "Game Finished", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[1]);
			if(tryAgainLose == 1) { //Try Again
				Main.playAgain();	 
			} else { // Exit Game
				System.exit(0);
				}
		}
	}
}