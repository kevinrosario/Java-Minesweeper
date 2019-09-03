import javax.swing.JFrame;

public class Main {


	static JFrame myFrame = new JFrame("Mine Sweeper");

	public static void main(String[] args) {

		myFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		myFrame.setLocation(400, 150);
		myFrame.setSize(700, 700);
		MyMouseAdapter myMouseAdapter = new MyMouseAdapter();
		myFrame.addMouseListener(myMouseAdapter);

		startGame();
	}
	
	static void startGame() {
		MyPanel myPanel = new MyPanel();
		myFrame.add(myPanel);
		myFrame.setVisible(true);
	}
	
	static void playAgain() {
		myFrame.getContentPane().removeAll();
		myFrame.getContentPane().repaint();
		startGame();	 

	}
}