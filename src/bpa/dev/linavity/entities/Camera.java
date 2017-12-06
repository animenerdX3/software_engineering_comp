package bpa.dev.linavity.entities;

public class Camera {

	//  X and Y position of the camera object in the level
	int x, y;
	int buffer;
	
	//Size of our camera
	int height = 900;
	int width = 900;

	//Creates the actual camera object
	public Camera(int x, int y){
		this.x = x - 425;
		this.y = y - 720;
		this.buffer = 50;
	}
	
	//Updates the x and y of the camera 
	public void updateCameraPos(int x, int y){
		this.x = x - 425;
		this.y = y - 720;
	}
	
	/* GETTERS */
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getBuffer() {
		return buffer;
	}
	
	/* SETTERS */
	
	public void setX(int x) {
		this.x = x;
	}


	public void setY(int y) {
		this.y = y;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public void setBuffer(int buffer) {
		this.buffer = buffer;
	}
	
}//end of class
