import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements KeyListener, ActionListener{
	Timer timer;
	final int MENU_STATE = 0;
	final int GAME_STATE = 1;
	final int END_STATE = 2;
	final int INSTRUCTIONS_STATE = 3;
	int currentState = MENU_STATE;
	Font titleFont, normalFont, scoreFont, mediumFont;
	Camera camera = new Camera();
	Jumper jumper = new Jumper(205, 600, 90, 90);
	StartPlatform startPlatform = new StartPlatform(Game.width/2-100, 550, 200, 10);
	ObjectManager objectManager;
	int score;
	public static BufferedImage characterImg;
	
	public GamePanel() {
		timer = new Timer(1000 / 60, this);
		titleFont = new Font("Ubuntu", Font.BOLD, 56);
		normalFont = new Font("Ubuntu", Font.PLAIN, 24);
		scoreFont = new Font("Ubuntu", Font.PLAIN, 18);
		mediumFont = new Font("Ubuntu", Font.PLAIN, 32);
		try {
            characterImg = ImageIO.read(this.getClass().getResourceAsStream("pc.png"));
    } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
    }
	}
	
	@Override

	public void paintComponent(Graphics g){
		if (currentState == MENU_STATE) {
			drawMenuState(g);
		} else if (currentState == INSTRUCTIONS_STATE) {
			drawInstructionsState(g);
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
		g.setFont(normalFont);
		int enterInstructionsWidth = g.getFontMetrics(normalFont).stringWidth("Press Space To See Instructions");
		g.drawString("Press Space To See Instructions", (int) ((Game.width/2)-(enterInstructionsWidth/2)), (int) (Game.height*0.6));
		objectManager = new ObjectManager(startPlatform, jumper);
		objectManager.platformSpawnSpeed = 175;
		jumper.isAlive = true;
		jumper.y = 600;
		jumper.x = 225;
		jumper.yLimit = 500;
		startPlatform.isAlive = true;
		startPlatform.x = Game.width/2-100;
		startPlatform.y = 550;
		score = (jumper.y*-1)+600-camera.y/10;
		camera.y = 0;
		/*jumper.y = 0;
		camera.y = 0;
		for (int i = 0; i < objectManager.platforms.size(); i++) {
			objectManager.platforms.remove(i);
		}*/
		}
	
	void drawInstructionsState(Graphics g) {
		g.setColor(new Color(95, 30, 95));
		g.fillRect(0,  0,  Game.width, Game.height);
		g.setColor(new Color(255, 255, 255));
		g.setFont(normalFont);
		int jumpWidth = g.getFontMetrics(normalFont).stringWidth("Press Space To Jump");
		g.drawString("Press Space To Jump", (int) ((Game.width/2)-(jumpWidth/2)), (int) (Game.height*0.375));
		int moveWidth = g.getFontMetrics(normalFont).stringWidth("Press ArrowKeys To Move Right/Left");
		g.drawString("Press ArrowKeys To Move Right/Left", (int) ((Game.width/2)-(moveWidth/2)), (int) (Game.height*0.4375));
		int points = g.getFontMetrics(normalFont).stringWidth("Don't Fall!");
		g.drawString("Don't Fall!", (int) ((Game.width/2)-(points/2)), (int) (Game.height*0.5));
		int returnWidth = g.getFontMetrics(normalFont).stringWidth("Press Space To Return To Menu");
		g.drawString("Press Space To Return To Menu", (int) ((Game.width/2)-(returnWidth/2)), (int) (Game.height*0.8));
		
	}
	
	void drawGameState(Graphics g) {
		objectManager.startPlatformTimer++;
		objectManager.platformTimer++;
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
		
		if (currentState == MENU_STATE) {
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				currentState = INSTRUCTIONS_STATE;
			}
		}
		
		else if (currentState == INSTRUCTIONS_STATE) {
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
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
