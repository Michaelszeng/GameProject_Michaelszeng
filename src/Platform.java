import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Platform extends GameObject {
	
	public Platform(int x, int y, int width, int height) {
		super(x, y, width, height);
		Random gen = new Random();
		x = gen.nextInt(width);
	}
	
	void update() {
		super.update();
	}
	
	void draw(Graphics g, int cameraY) {
		g.setColor(new Color(255, 178, 20));
		g.fillRect(x, y+cameraY, width, height);
	}
	
	
}
