package bpa.dev.linavity.collectibles;

import org.newdawn.slick.SlickException;

import bpa.dev.linavity.Main;
import bpa.dev.linavity.gamestates.GameLevel;

public class Coin extends Item{
	
	public Coin(float x, float y) throws SlickException {
		super(x, y);
	}
	
	public Coin(float x, float y, String itemImage) throws SlickException {
		super(x, y, itemImage);
	}
	
	public Coin(float x, float y, float width, float height, String itemImage) throws SlickException {
		super(x, y, width, height, itemImage);
	}
	
	public void update() {
		super.update();//Call the parent's update method
		if(this.isCollected)//If the item is collected, call an event
			onCollision();
	}//end of update
	
	public void onCollision() {
		if(this.getItemImage().equalsIgnoreCase("coin_b"))
			Main.util.coinBGrabbed = true;
		else if(this.getItemImage().equalsIgnoreCase("coin_p"))
			Main.util.coinPGrabbed = true;
		else if(this.getItemImage().equalsIgnoreCase("coin_a"))
			Main.util.coinAGrabbed = true;
		this.isActive = false;//Destroy the item
		Main.util.getSFX(11).play(1f, Main.util.getSoundManager().getVolume());
		GameLevel.displayScore();
	}//end of onCollision

}//end of class
