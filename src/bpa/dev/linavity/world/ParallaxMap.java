package bpa.dev.linavity.world;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import bpa.dev.linavity.Main;

public class ParallaxMap {

	private Image backgroundLayer;
	
	private float x, speed;
	private boolean autoScroll;
	
	public ParallaxMap(String directory, float x, float speed, boolean autoScroll) throws SlickException {
		this.backgroundLayer = new Image(directory);
		this.x = x;
		this.speed = speed;
		this.autoScroll = autoScroll;
	}

	public void moveBackground() {
		//Check for sides of the image
		if(this.x < 0 || x > this.backgroundLayer.getWidth()) {
			//Check to see if the player is moving left
			if(Main.util.getPlayer().isMovingLeft()) {
				this.x = x + speed;
			}
			//Check to see if the player is moving right
			else if(Main.util.getPlayer().isMovingRight()) {
				this.x = x - speed;
			}
			//If not moving, just let it scroll
			else if (this.autoScroll)
				this.x = x + (speed/2);
		}
	}
	
	/* GETTERS */
	
	public Image getBackgroundLayer() {
		return backgroundLayer;
	}
	
	public float getX() {
		return x;
	}
	
	public float getSpeed() {
		return speed;
	}
	
	public boolean isAutoScroll() {
		return autoScroll;
	}
	
	/* SETTERS */
	
	public void setBackgroundLayer(Image backgroundLayer) {
		this.backgroundLayer = backgroundLayer;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
	public void setAutoScroll(boolean autoScroll) {
		this.autoScroll = autoScroll;
	}
	
}//end of class
