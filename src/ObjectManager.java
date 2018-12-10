import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

public class ObjectManager {
	ArrayList<Platform> platforms = new ArrayList<Platform>();
	long platformTimer = 0;
	int platformSpawnTime = 1000;
	int platformY = 0;
	int platformSpawnSpeed = 100;

	void addPlatform(Platform p) {
		platforms.add(p);
	}
	
	void draw(Graphics g, int cameraY) {
		for (int i = 0; i < platforms.size(); i++) {
			platforms.get(i).draw(g, cameraY);
		}
	}
	
	void update() {
		for (int i = 0; i < platforms.size(); i++) {
			platforms.get(i).update();
		}
	}
	
	public void managePlatforms() {
		if (System.currentTimeMillis() - platformTimer >= platformSpawnTime) {
			Random ran = new Random();
			int platformSize = ran.nextInt(45)+55;
			addPlatform(new Platform(new Random().nextInt(Game.width), platformY, platformSize, 10));
			platformY-=platformSpawnSpeed;
			platformSpawnSpeed = platformSpawnSpeed+15;
			platformTimer = System.currentTimeMillis();
		}
	}
}
