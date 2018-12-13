import java.awt.Color;
import java.awt.Graphics;

public class Jumper extends GameObject{
	int speed;
	boolean up, down, right, left = false;
	int xVelocity = 5;
	int yVelocity = 0;
	int gravity = 1;
	int jumpPower = 20;
	int yLimit = 500;
	boolean canJump = false;
	
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
			x += xVelocity;
		}
		if (left) {
			x -= xVelocity;
		}
		
		yVelocity += gravity;
		y += yVelocity;
		
		if(y >= yLimit){
			y = yLimit;
			yVelocity = 0;
			canJump = true;
		}
	}
	
	void draw(Graphics g, int cameraY) {
		g.setColor(Color.WHITE);
		g.fillRect(x, y+cameraY, width, height);
		g.setColor(new Color(255, 178, 20));
		g.fillRect(width/2-250, y+height, 100, 10);
	}
}