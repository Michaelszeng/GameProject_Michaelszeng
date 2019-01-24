
public class Camera {
	int y = 0;
	int speed;
	int cameraTimer = 0;
	public Camera() {
		speed = 5;
	}
	
	void update() {
		cameraTimer+=1;
		y+=speed;
	}
	
	public int getY() {
		return y;
	}
}

