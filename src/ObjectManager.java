import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

public class ObjectManager {
	ArrayList<Platform> platforms = new ArrayList<Platform>();
	StartPlatform startPlatform;
	Jumper jumper;
	long platformTimer = 0;
	int platformSpawnTime = 1000;
	int platformY = 0;
	int platformSpawnSpeed = 100;
	long startPlatformTimer = 0;
	int startPlatformMoveTime = 3000;
	
	public ObjectManager(StartPlatform startPlatform, Jumper jumper) {
		this.startPlatform = startPlatform;
		this.jumper = jumper;
		startPlatformTimer = System.currentTimeMillis();
	}
	
	void addPlatform(Platform p) {
		platforms.add(p);
	}
	
	void draw(Graphics g, int cameraY) {
		for (int i = 0; i < platforms.size(); i++) {
			platforms.get(i).draw(g, cameraY);
		}
		if (startPlatform.isAlive != false) {
			startPlatform.draw(g);
		}
	}
	
	void update() {
		if (startPlatform.isAlive != false) {
			for (int i = 0; i < platforms.size(); i++) {
				platforms.get(i).update();
			}
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
	
	void eraseObjects() {
		if (System.currentTimeMillis() - startPlatformTimer >= startPlatformMoveTime) {
			startPlatform.isAlive = false;
			startPlatformTimer = System.currentTimeMillis();
		}
	}
	
	void checkCollision() {
		if ((jumper.x+jumper.width>startPlatform.x) && (jumper.x<startPlatform.x+startPlatform.width)) {
			if ((jumper.y+jumper.height <= startPlatform.y) && (jumper.y+jumper.height >= startPlatform.y-5)) {
				jumper.y = startPlatform.y;
			}
		}
	}
}
