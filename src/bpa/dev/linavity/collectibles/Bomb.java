package bpa.dev.linavity.collectibles;

import org.newdawn.slick.SlickException;

public class Bomb extends Item{
	
	public Bomb(float x, float y) throws SlickException {
		super(x, y);
	}
	
	public Bomb(float x, float y, String itemImage) throws SlickException {
		super(x, y, itemImage);
	}
	
	public Bomb(float x, float y, float width, float height, String itemImage) throws SlickException {
		super(x, y, width, height, itemImage);
	}
	
	public void update() {
		super.update();//Call the parent's update method
		if(this.isCollected)//If the item is collected, call an event
			onCollision();
	}//end of update
	
	public void onCollision() {
		this.isActive = false;//Destroy the item
	}//end of onCollision

}//end of class
