import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements KeyListener, ActionListener{
	Timer timer;
	final int MENU_STATE = 0;
	final int GAME_STATE = 1;
	final int END_STATE = 2;
	int currentState = MENU_STATE;
	Font titleFont, normalFont, scoreFont, mediumFont;
	Camera camera = new Camera();
	Jumper jumper = new Jumper(225, 600, 50, 50);
	StartPlatform startPlatform = new StartPlatform(Game.width/2-100, 550, 200, 10);
	ObjectManager objectManager = new ObjectManager(startPlatform, jumper);
	int score;
	
	public GamePanel() {
		timer = new Timer(1000 / 60, this);
		titleFont = new Font("Ubuntu", Font.BOLD, 56);
		normalFont = new Font("Ubuntu", Font.PLAIN, 24);
		scoreFont = new Font("Ubuntu", Font.PLAIN, 18);
		mediumFont = new Font("Ubuntu", Font.PLAIN, 32);
	}
	
	@Override

	public void paintComponent(Graphics g){
		if (currentState == MENU_STATE) {
			drawMenuState(g);
		} else if (currentState == GAME_STATE) {
			drawGameState(g);
		} else if (currentState == END_STATE) {
			drawEndState(g);
		}
	}
	
	void drawMenuState(Graphics g) {
		g.setColor(new Color(55, 0, 55));
		g.fillRect(0,  0,  Game.width, Game.height);
		g.setColor(new Color(255, 226, 193));
		g.setFont(titleFont);
		int titleWidth = g.getFontMetrics(titleFont).stringWidth("Jump!");
		g.drawString("Jump!", (int) ((Game.width/2)-(titleWidth/2)), (int) (Game.height*0.375));
		g.setFont(normalFont);
		int instructionsWidth = g.getFontMetrics(normalFont).stringWidth("Press Enter To Start");
		g.drawString("Press Enter To Start", (int) ((Game.width/2)-(instructionsWidth/2)), (int) (Game.height*0.5));
		/*jumper.y = 0;
		camera.y = 0;
		for (int i = 0; i < objectManager.platforms.size(); i++) {
			objectManager.platforms.remove(i);
		}*/
		}
	
	void drawGameState(Graphics g) {
		System.out.println(jumper.y);
		score = (jumper.y*-1)+600-camera.y/10;
		g.setColor(new Color(40, 0, 40));
		g.fillRect(0,  0,  Game.width, Game.height);
		jumper.draw(g, camera.y);
		objectManager.draw(g, camera.y);
		g.setColor(new Color(245, 245, 245));
		g.setFont(scoreFont);
		g.drawString("Score: " + score, (int) (Game.width*0.05), (int) (Game.height*0.045));
	}
	
	void drawEndState(Graphics g) {
		g.setColor(new Color(99, 4, 4));
		g.fillRect(0,  0,  Game.width, Game.height);
		g.setColor(new Color(255, 255, 220));
		g.setFont(titleFont);
		int titleWidth = g.getFontMetrics(titleFont).stringWidth("You Died!");
		g.drawString("You Died!", (int) ((Game.width/2)-(titleWidth/2)), (int) (Game.height*0.4));
		g.setFont(normalFont);
		int instructionsWidth = g.getFontMetrics(normalFont).stringWidth("Press Enter To Return to Menu");
		g.drawString("Press Enter To Return To Menu", (int) ((Game.width/2)-(instructionsWidth/2)), (int) (Game.height*0.675));
		g.setFont(mediumFont);
		int scoreWidth = g.getFontMetrics(mediumFont).stringWidth("Score: " + score);
		g.drawString("Score: " + score, (int) ((Game.width/2)-(scoreWidth/2)), (int) (Game.height*0.525));
	}
	
	void updateGameState() {
		camera.update();
		jumper.update();
		objectManager.update();
		objectManager.managePlatforms();
		objectManager.eraseObjects(camera.y);
		objectManager.checkCollision(score);
		if (jumper.isAlive == false) {
			currentState = END_STATE;
		}
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
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			if (currentState == MENU_STATE) {
				currentState = GAME_STATE;
			}
			else if (currentState == END_STATE) {
				currentState = MENU_STATE;
			}
		}
		
		if (currentState == GAME_STATE) {
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				jumper.right = true;
				jumper.xTimer = System.currentTimeMillis();
			} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				jumper.left = true;
			} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				jumper.jump();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			jumper.right = false;
			jumper.afterRight = true;
			/*
			int decel = 0;
			for (int i=0; i < 4; i++) {
				decel += jumper.xVelocity/4;
				jumper.x += decel;
			}
			*/
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			jumper.left = false;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		repaint();
		if (currentState == GAME_STATE) {
			updateGameState();
		}
	}

}
