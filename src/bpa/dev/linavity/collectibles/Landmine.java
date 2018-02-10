package bpa.dev.linavity.collectibles;

import org.newdawn.slick.SlickException;

import bpa.dev.linavity.Main;

public class Landmine extends Item{
	
	private int damage = 10;
	
	public Landmine(float x, float y) throws SlickException {
		super(x, y);
	}
	
	public Landmine(float x, float y, String itemImage) throws SlickException {
		super(x, y, itemImage);
	}
	
	public Landmine(float x, float y, float width, float height, String itemImage) throws SlickException {
		super(x, y, width, height, itemImage);
	}
	
	public void update() {
		super.update();//Call the parent's update method
		if(this.isCollected)//If the item is collected, call an event
			onCollision();
	}//end of update
	
	public void onCollision() {
		Main.util.getPlayer().setHealth(Main.util.getPlayer().getHealth() - this.damage);
		Main.util.getSFX(3).play(1f, Main.util.getSoundManager().getVolume());
		this.isActive = false;//Destroy the item
	}//end of onCollision

}//end of class
