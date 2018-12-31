import java.awt.Color;
import java.awt.Graphics;

public class Jumper extends GameObject{
	int speed;
	boolean up, down, right, left, afterRight = false;
	int xVelocity = 0;
	int yVelocity = 0;
	int gravity = 1;
	int xGravity = 1;
	int jumpPower = 28;
	int yLimit = 500;
	boolean canJump = false;
	long xTimer = 0;
	
	public Jumper(int x, int y, int width, int height) {
		super(x, y, width, height);
		speed = 5;
	}
	
	public void jump(){
		if(canJump){
			yVelocity -= jumpPower;
			canJump = false;
		}
	}
	
	void update() {
		super.update();
		if (right) {
			xVelocity=10;
		}
//		if (afterRight) {
//			if (xVelocity > 0 ) {
//				x += xVelocity;
//				xVelocity -= xGravity;
//			}
//		}
//		if (xVelocity <= 0) {
//			afterRight = false;
//		}
		if (left) {
			xVelocity=-10;
		}
		System.out.println("xv:"+xVelocity);
		x += xVelocity;
		yVelocity += gravity;
		y += yVelocity;

		xVelocity*=.9;
		if(y >= yLimit) {
			y = yLimit;
			yVelocity = 0;
			canJump = true;
		}
		
	}
	
	void draw(Graphics g, int cameraY) {
		g.setColor(Color.WHITE);
		g.fillRect(x, y+cameraY-52, width, height);
		g.setColor(new Color(255, 178, 20));
		g.fillRect(width/2-250, y+height, 100, 10);
	}
}
