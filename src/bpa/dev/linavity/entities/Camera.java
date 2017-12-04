package bpa.dev.linavity.entities;

public class Camera {

	//  X and Y position of the camera object in the level
	int x, y;
	
	int height = 896;
	int width = 896;

	//Creates the actual camera object
	public Camera(int x, int y){
		this.x = x - 423;
		this.y = y - 718;
	}
	
	//Updates the x and y of the camera 
	public void updateCameraPos(int x, int y){
		this.x = x - 423;
		this.y = y - 718;
	}
	
	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	
	
	
	
	
	
	
	
	
	
}
