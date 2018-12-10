import java.awt.Color;
import java.awt.Graphics;

public class Jumper extends GameObject{
	int speed;
	boolean up, down, right, left = false;
	
	private int xVelocity = 5;
	private int yVelocity = 0;
	private int gravity = 1;
	private int jumpPower = 20;
	private int yLimit = 500;
	boolean canJump = false;
	
	public Jumper(int x, int y, int width, int height) {
		super(x, y, width, height);
		speed = 5;
	}
	
	void update() {
		super.update();
		if (up) {
			y -= speed;
		}
		if (down) {
			y += speed;
		}
		if (right) {
			x += speed;
		}
		if (left) {
			x -= speed;
		}
	}
	
	void draw(Graphics g, int cameraY) {
		g.setColor(Color.WHITE);
		g.fillRect(x, y+cameraY, width, height);
	}
}
