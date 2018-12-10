
public class Camera {
	int y = 0;
	int speed;
	public Camera() {
		speed = 2;
	}
	
	void update() {
		y+=speed;
	}
	
	public int getY() {
		return y;
	}
}

