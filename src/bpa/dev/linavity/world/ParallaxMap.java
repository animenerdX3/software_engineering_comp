package bpa.dev.linavity.world;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import bpa.dev.linavity.Main;

public class ParallaxMap {

	private Image backgroundLayer;
	
	private float x, y, Xspeed, Yspeed;
	private boolean autoScroll;
	
	public ParallaxMap(String directory, float x, float y, float Xspeed, float Yspeed, boolean autoScroll) throws SlickException {
		this.backgroundLayer = new Image(directory);
		this.x = x;
		this.y = y;
		this.Xspeed = Xspeed;
		this.Yspeed = Yspeed;
		this.autoScroll = autoScroll;
	}

	public void moveBackground() {
			//If not moving, just let it scroll
			if (this.autoScroll) {
				this.x = x + Xspeed;
				this.y = y + Yspeed;
				if(this.x + this.Xspeed >= 0)
					this.Xspeed = -this.Xspeed;
				else if(this.x + this.Xspeed <= -(this.backgroundLayer.getWidth()- 900))
					this.Xspeed = -this.Xspeed;
			}
			else {
				if(Main.util.getPlayer().isMovingLeft()) {
					this.x = x + Xspeed;
					this.y = y + Yspeed;
				}
				//Check to see if the player is moving right
				else if(Main.util.getPlayer().isMovingRight()) {
					this.x = x - Xspeed;
					this.y = y - Yspeed;
				}
			}
			if(this.y < -(this.backgroundLayer.getHeight() - 900)){
				this.y = this.y - this.y;
			}
	}
	
	/* GETTERS */
	
	public Image getBackgroundLayer() {
		return backgroundLayer;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public float getXSpeed() {
		return Xspeed;
	}
	
	public float getYSpeed() {
		return Yspeed;
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
	
	public void setY(float y) {
		this.y = y;
	}
	
	public void setXSpeed(float Xspeed) {
		this.Xspeed = Xspeed;
	}
	
	public void setYSpeed(float Yspeed) {
		this.Xspeed = Yspeed;
	}
	
	public void setAutoScroll(boolean autoScroll) {
		this.autoScroll = autoScroll;
	}
	
}//end of class
