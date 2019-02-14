import java.awt.Color;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

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
	Font titleFont, normalFont, scoreFont, mediumFont, smallFont, boldNormalFont;
	Camera camera = new Camera();
	Jumper jumper = new Jumper(205, 600, 90, 90);
	StartPlatform startPlatform = new StartPlatform(Game.width/2-100, 550, 200, 10);
	ObjectManager objectManager;
	int score;
	public static BufferedImage characterImg;
	int r = 40;
	int gColor = 0;
	int b = 40;
	long colorTimer;
	int colorInterval = 0;
	int colorMultiplier = 1;
	int rFactor = 3;
	int gFactor = 2;
	int bFactor = 1;
	Random rand = new Random();
	boolean fastMode = true;
	int initialSpeed = 4;
	String mode = "Easy";
	
	public GamePanel() {
		timer = new Timer(1000 / 60, this);
		colorTimer = System.currentTimeMillis();
		titleFont = new Font("Ubuntu", Font.BOLD, 56);
		normalFont = new Font("Ubuntu", Font.PLAIN, 24);
		boldNormalFont = new Font("Ubuntu", Font.BOLD, 32);
		scoreFont = new Font("Ubuntu", Font.PLAIN, 18);
		mediumFont = new Font("Ubuntu", Font.PLAIN, 32);
		smallFont = new Font("Ubuntu", Font.PLAIN, 16);
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
			drawGameState(g, r, gColor, b);
		} else if (currentState == END_STATE) {
			drawEndState(g);
		}
	}
	
	void drawMenuState(Graphics g) {
		if (fastMode == true) {
			initialSpeed = 5;
			mode = "Hard";
		}
		else {
			initialSpeed = 4;
			mode = "Easy";
		}
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
		g.drawString("Press Space To See Instructions", (int) ((Game.width/2)-(enterInstructionsWidth/2)), (int) (Game.height*0.55));
		g.setFont(boldNormalFont);
		int modeWidth = g.getFontMetrics(boldNormalFont).stringWidth("Mode: hard");
		g.drawString("Mode: " + mode, (int) ((Game.width/2)-(modeWidth/2)), (int) (Game.height*0.625));
		g.setFont(smallFont);
		int changeModeWidth = g.getFontMetrics(smallFont).stringWidth("Press Up To Change Modes");
		g.drawString("Press Up To Change Modes", (int) ((Game.width/2)-(changeModeWidth/2)), (int) (Game.height*0.65));
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
		camera.speed = initialSpeed;
		r = 40;
		gColor = 0;
		b = 40;
		if (mode == "Hard") {
			fastMode = true;
		}
		else {
			fastMode = false;
		}
		/*jumper.y = 0;
		camera.y = 0;
		for (int i = 0; i < objectManager.platforms.size(); i++) {
			objectManager.platforms.remove(i);
		}*/
		}
	
	void drawInstructionsState(Graphics g) {
		g.setColor(new Color(95, 15, 55));
		g.fillRect(0,  0,  Game.width, Game.height);
		g.setColor(new Color(255, 255, 255));
		g.setFont(normalFont);
		int jumpWidth = g.getFontMetrics(normalFont).stringWidth("Press Space To Jump");
		g.drawString("Press Space To Jump", (int) ((Game.width/2)-(jumpWidth/2)), (int) (Game.height*0.375));
		int moveWidth = g.getFontMetrics(normalFont).stringWidth("Press ArrowKeys To Move Right/Left");
		g.drawString("Press ArrowKeys To Move Right/Left", (int) ((Game.width/2)-(moveWidth/2)), (int) (Game.height*0.4375));
		int down = g.getFontMetrics(normalFont).stringWidth("Press Down To Fall Fast");
		g.drawString("Press Down To Fall Fast", (int) ((Game.width/2)-(down/2)), (int) (Game.height*0.5));
		int hint = g.getFontMetrics(smallFont).stringWidth("hint: this may be useful in the beginning!");
		g.setFont(smallFont);
		g.drawString("hint: this may be useful in the beginning!", (int) ((Game.width/2)-(hint/2)), (int) (Game.height*0.525));
		int fast = g.getFontMetrics(boldNormalFont).stringWidth("ACT FAST!!!");
		g.setFont(boldNormalFont);
		g.drawString("ACT FAST!!!", (int) ((Game.width/2)-(fast/2)), (int) (Game.height*0.6));
		int returnWidth = g.getFontMetrics(normalFont).stringWidth("Press Space To Return To Menu");
		g.setFont(normalFont);
		g.drawString("Press Space To Return To Menu", (int) ((Game.width/2)-(returnWidth/2)), (int) (Game.height*0.9));
		
	}
	
	void drawGameState(Graphics g, int r, int gColor, int b) {
		objectManager.startPlatformTimer++;
		objectManager.platformTimer++;
		score = (jumper.y*-1)+600-camera.y/10;
		g.setColor(new Color(r, gColor, b));
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
	
	void updateGameState(int initialSpeed) {
		this.initialSpeed = initialSpeed;
		System.out.println(camera.speed);
		colorInterval+=1;
		if ((System.currentTimeMillis() - colorTimer > 6000) && (colorInterval%16 == 0)) {
			rFactor = rand.nextInt(4)+1;
			rFactor = rand.nextInt(4)+0;
			rFactor = rand.nextInt(3)-1;
			r = r + (3*colorMultiplier);
			gColor = gColor + (2*colorMultiplier);
			b = b + (1*colorMultiplier);
			if (r<5) {
				colorMultiplier = 1;
			}
			else if (r>210){
				colorMultiplier = -1;
			}
			if (gColor<5) {
				colorMultiplier = 1;
			}
			else if (gColor>210){
				colorMultiplier = -1;
			}
			if (b<5) {
				colorMultiplier = 1;
			}
			else if (b>210){
				colorMultiplier = -1;
			}
		}
		camera.update();
		jumper.update();
		objectManager.update();
		objectManager.managePlatforms();
		objectManager.eraseObjects(camera.y);
		objectManager.checkCollision(score);
		if (jumper.isAlive == false) {
			currentState = END_STATE;
		}
		if ((score>2500) && (camera.speed <= initialSpeed)) {
			camera.speed=initialSpeed+1;
		}
		/*
		if ((score>3000) && (camera.speed <= 4)) {
			camera.speed+=1;
		}
		*/
		if ((score>4000) && (camera.speed <= initialSpeed+1)) {
			camera.speed=initialSpeed+2;
		}
		if ((score>7500) && (camera.speed <= initialSpeed+2)) {
			camera.speed=initialSpeed+3;
		}
		if ((score>12000) && (camera.speed <= initialSpeed+3)) {
			camera.speed=initialSpeed+4;
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
			else if (e.getKeyCode() == KeyEvent.VK_UP) {
				if (mode == "Hard") {
					mode = "Easy";
					fastMode = false;
				}
				else {
					mode = "Hard";
					fastMode = true;
				}
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
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				jumper.gravity += 1;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			jumper.right = false;
		}
		else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			jumper.gravity = 1;
		}
		else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			jumper.left = false;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		repaint();
		if (currentState == GAME_STATE) {
			updateGameState(initialSpeed);
		}
	}

}
