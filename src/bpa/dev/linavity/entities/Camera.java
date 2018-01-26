package bpa.dev.linavity.entities;

import bpa.dev.linavity.Main;

public class Camera {

	//  X and Y position of the camera object in the level
	float x, y;
	int buffer;
	
	//Size of our camera
	int height;
	int width;
	
	// Default Constructor
	public Camera(float playerX, float playerY){
		this.x = playerX - 425;
		this.y = playerY - 720;
		this.height = Main.HEIGHT;
		this.width = Main.WIDTH;
		this.buffer = 50;
	}
	
	// Non-Default Constructor
	public Camera(float mobX, float mobY, int collisionRadius) {
		this.x = mobX - collisionRadius;
		this.y = mobY- collisionRadius;
		this.width = collisionRadius * 2;
		this.height = collisionRadius * 2;
	}
	
	//Updates the x and y of the camera 
	public void updateCameraPos(float x, float y){
		if(Main.util.getPlayer().getX() <= Main.util.getLevel().getLevelWidth() - 425 && Main.util.getPlayer().getX() >= 425)
			this.x = x - 425;

		if(Main.util.getPlayer().getY() <= Main.util.getLevel().getLevelHeight() - 99 && Main.util.getPlayer().getY() >= 600)
			this.y = y - 720;
	}
	
	public void outOfBoundsKill() {
		if(Main.util.getPlayer().getY() >= Main.util.getLevel().getLevelHeight() + 100 || Main.util.getPlayer().getY() < -50)
			Main.util.getPlayer().setHealth(Main.util.getPlayer().getHealth() - 1);
	}
	
	/* GETTERS */
	
	public float getX() {
		return x;
	}

	public float getY() {
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
	
	public void setX(float x) {
		this.x = x;
	}


	public void setY(float y) {
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
