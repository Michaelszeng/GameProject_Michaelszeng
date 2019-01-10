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
		super(x, y, width, height-15);
		speed = 5;
		collisionBox.width-=40;
		collisionBox.y+=10;
		
	}
	
	public void jump(){
		if(canJump){
			yVelocity -= jumpPower;
			canJump = false;
		}
	}
	
	void update() {
		super.update();
		collisionBox.width-=40;
		collisionBox.y+=10;
		if (right) {
			xVelocity=10;
		}
		//failed attempts of decel
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
		x += xVelocity;
		yVelocity += gravity;
		y += yVelocity;

		xVelocity*=.9;
		if(y >= yLimit-16) {
			y = yLimit-16;
			yVelocity = 0;
			canJump = true;
		}
		
	}
	
	void draw(Graphics g, int cameraY) {
		g.drawImage(GamePanel.characterImg, x-20, y+cameraY-51, width, height, null);
		g.setColor(Color.RED);
		//uncomment below to see jumper's collision box
		//g.drawRect(collisionBox.x, collisionBox.y+cameraY, collisionBox.width, collisionBox.height);
	}
}
