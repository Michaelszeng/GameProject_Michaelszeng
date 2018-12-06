import java.awt.Graphics;
import java.util.ArrayList;

public class ObjectManager {
	ArrayList<Platform> platforms = new ArrayList<Platform>();
	
	void addPlatform(Platform p) {
		platforms.add(p);
	}
	
	void draw(Graphics g) {
		for (int i = 0; i < platforms.size(); i++) {
			platforms.get(i).draw(g);
		}
	}
	
	void update() {
		for (int i = 0; i < platforms.size(); i++) {
			platforms.get(i).update();
		}
	}
	
	public void manageEnemies() {
		
	}
}
