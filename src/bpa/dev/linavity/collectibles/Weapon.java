package bpa.dev.linavity.collectibles;

import org.newdawn.slick.SlickException;

import bpa.dev.linavity.Main;
import bpa.dev.linavity.gamestates.GameLevel;

public class Weapon extends Item{
	
	public Weapon(float x, float y) throws SlickException {
		super(x, y);
	}
	
	public Weapon(float x, float y, String itemImage) throws SlickException {
		super(x, y, itemImage);
	}
	
	public Weapon(float x, float y, float width, float height, String itemImage) throws SlickException {
		super(x, y, width, height, itemImage);
	}
	
	public void update() {
		super.update();//Call the parent's update method
		if(this.isCollected)//If the item is collected, call an event
			onCollision();
	}//end of update
	
	public void onCollision() {
		this.isActive = false;//Destroy the item
		Main.util.getPlayer().setCanUseWeapon(true);
		GameLevel.tutorialScene.setTutorial(GameLevel.tutorialGUI[6]);
		GameLevel.tutorialScene.setActive(true);
		GameLevel.tutorialScene.setTimer(0);
	}//end of onCollision

}//end of class
