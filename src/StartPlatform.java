import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class StartPlatform extends GameObject{
	public StartPlatform(int x, int y, int width, int height) {
		super(x, y, width, height);
	}
	
	void update() {
		y-=2;
		super.update();
	}
	
	void draw(Graphics g, int CameraY) {
		g.setColor(new Color(155, 108, 10));
		g.fillRect(x, y+CameraY, width, height);
	}
}
