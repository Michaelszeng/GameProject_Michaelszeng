import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements KeyListener, ActionListener{
	Timer timer;
	GameObject gameObject;
	
	public GamePanel() {
		timer = new Timer(1000 / 60, this);
		gameObject = new GameObject(245, 400, 10, 100);
	}
	
	@Override

	public void paintComponent(Graphics g){
		gameObject.draw(g);
	}
	
	void startGame() {
		timer.start();
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		repaint();
		gameObject.update();
	}

}
