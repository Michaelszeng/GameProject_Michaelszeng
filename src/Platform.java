import java.awt.Color;
import java.awt.Graphics;

public class Platform extends GameObject {
	
	public Platform(int x, int y, int width, int height) {
		super(x, y, width, height);
	}
	
	void update() {
		super.update();
		y += 3;
	}
	
	void draw(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(x, y, width, height);
	}
	
}
