package bpa.dev.linavity.collectibles;

import org.newdawn.slick.SlickException;

import bpa.dev.linavity.Main;

public class HealthPack extends Item{
	
	public HealthPack(float x, float y) throws SlickException {
		super(x, y);
	}
	
	public HealthPack(float x, float y, String itemImage) throws SlickException {
		super(x, y, itemImage);
	}
	
	public HealthPack(float x, float y, float width, float height, String itemImage) throws SlickException {
		super(x, y, width, height, itemImage);
	}
	
	public void update() {
		super.update();//Call the parent's update method
		if(this.isCollected)//If the item is collected, call an event
			onCollision();
	}//end of update
	
	public void onCollision() {
		this.isActive = false;//Destroy the item
		Main.util.getInventory().addToInventory(this);
	}//end of onCollision

}//end of class
