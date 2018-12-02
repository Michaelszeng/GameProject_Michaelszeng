import java.awt.Dimension;

import javax.swing.JFrame;

public class Game {
	final static int width = 500;
	final static int height = 800;
	JFrame window;
	GamePanel gamePanel;
	
	public static void main(String[] args) {
		Game game = new Game();
		game.setUp();
		game.gamePanel.startGame();
	}
	
	public Game() {
		window = new JFrame();
		gamePanel = new GamePanel();
	}
	
	void setUp() {
		window.add(gamePanel);
		window.addKeyListener(gamePanel);
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.getContentPane().setPreferredSize(new Dimension(width, height));
		window.pack();
	}
}
