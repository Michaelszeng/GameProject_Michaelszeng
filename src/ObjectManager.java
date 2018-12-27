import java.awt.Graphics;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ToolTipManager;

public class ObjectManager {
	ArrayList<Platform> platforms = new ArrayList<Platform>();
	StartPlatform startPlatform;
	Jumper jumper;
	long platformTimer = 0;
	int platformSpawnTime = 1000;
	int platformY = 0;
	int platformSpawnSpeed = 100;
	long startPlatformTimer = 0;
	int startPlatformMoveTime = 4250;
	
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
			startPlatform.draw(g, cameraY);
		}
	}
	
	void update() {
		if (startPlatform.isAlive != false) {
			for (int i = 0; i < platforms.size(); i++) {
				platforms.get(i).update();
			}
		}
		startPlatform.update();
	}
	
	public void managePlatforms() {
		if (System.currentTimeMillis() - platformTimer >= platformSpawnTime) {
			Random ran = new Random();
			int platformSize = ran.nextInt(45)+55;
			addPlatform(new Platform(new Random().nextInt(Game.width-platformSize), platformY, platformSize, 10));
			platformY-=platformSpawnSpeed;
			if (platformSpawnSpeed < 350) {
				platformSpawnSpeed = platformSpawnSpeed+15;
			}
			platformTimer = System.currentTimeMillis();
		}
	}
	
	void eraseObjects(int cameraY) {
		if (System.currentTimeMillis() - startPlatformTimer >= startPlatformMoveTime) {
			startPlatform.isAlive = false;
			startPlatform.x = 99999;
			startPlatformTimer = System.currentTimeMillis();
		}
		if (jumper.y*-1 <= cameraY-800) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jumper.isAlive = false;
		}
		for (int i = 0; i < platforms.size(); i++) {
			if (platforms.get(i).y >= 499) {
				platforms.get(i).isAlive = false;
			}
		}
	}
	
	void checkCollision(int score) {
		if (jumper.collisionBox.intersects(startPlatform.collisionBox)) {
			jumper.yLimit = startPlatform.y;
		}
		else {
			jumper.yLimit = 500;
		}
		for (Platform p : platforms) {
			if (jumper.collisionBox.intersects(p.collisionBox)) {
				jumper.yLimit = p.y;
			}
		}
	}
}
