package bpa.dev.linavity.entities;

import bpa.dev.linavity.Main;

public class Camera {

	//  X and Y position of the camera object in the level
	float x, y;
	private int buffer;
	
	//Size of our camera
	private int width, height;
	
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
	
	/**
	 * Updates the x and y of the camera 
	 * @param x
	 * @param y
	 */
	public void updateCameraPos(float x, float y){
		//If the player's x is greater than 425 (our X buffer) and less than the width - 425 (our X buffer), update our camera's X position
		if(Main.util.getPlayer().getX() <= Main.util.getLevel().getLevelWidth() - 425 && Main.util.getPlayer().getX() >= 425)
			this.x = x - 425;
		
		//If the player's y is greater than 600 and less than the height - 99 (our Y buffer), update our camera's Y position
		if(Main.util.getPlayer().getY() <= Main.util.getLevel().getLevelHeight() - 99 && Main.util.getPlayer().getY() >= 600)
			this.y = y - 720;
	}//end of updateCameraPos
	
	/**
	 * Kill the player if they are outside of the map
	 */
	public void outOfBoundsKill() {
		if(Main.util.getPlayer().getY() >= Main.util.getLevel().getLevelHeight() + 100 || Main.util.getPlayer().getY() < -50)
			Main.util.getPlayer().setHealth(Main.util.getPlayer().getHealth() - 1);
	}//end of outOfBoundsKill
	
	/* GETTERS */
	
	/**
	 * 
	 * @return our camera's x position
	 */
	public float getX() {
		return x;
	}

	/**
	 * 
	 * @return our camera's y position
	 */
	public float getY() {
		return y;
	}
	
	/**
	 * 
	 * @return our camera's width
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * 
	 * @return our camera's height
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * 
	 * @return our camera's general buffer
	 */
	public int getBuffer() {
		return buffer;
	}
	
	/* SETTERS */
	
	/**
	 * changes our camera's x position
	 * @param x
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * changes our camera's y position
	 * @param y
	 */
	public void setY(float y) {
		this.y = y;
	}
	
	/**
	 * changes our camera's width
	 * @param width
	 */
	public void setWidth(int width) {
		this.width = width;
	}
	
	/**
	 * changes our camera's height
	 * @param height
	 */
	public void setHeight(int height) {
		this.height = height;
	}
	
	/**
	 * changes our camera's general buffer
	 * @param buffer
	 */
	public void setBuffer(int buffer) {
		this.buffer = buffer;
	}
	
}//end of class
