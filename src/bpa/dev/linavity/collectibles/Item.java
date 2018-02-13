package bpa.dev.linavity.collectibles;

import java.awt.Rectangle;
import java.util.Random;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import bpa.dev.linavity.Main;

public class Item {

	protected float x, y, width, height;
	protected Rectangle collisionBox;
	protected SpriteSheet itemSheet;
	protected Animation itemAni;
	private String itemImage;
	private Image thumb;
	protected boolean isCollected;
	protected boolean isActive;
	
	public Item(float x, float y) throws SlickException {
		this.x = x;
		this.y = y;
		this.width = 20;
		this.height = 40;
		this.itemImage = "gravitypack";
		this.collisionBox = new Rectangle((int)x,(int)y,(int)width,(int)height);
	    this.itemSheet = new SpriteSheet("res/items/"+itemImage+"/"+itemImage+"_animation.png",(int)this.width, (int)this.height); // declare a SpriteSheet and load it into java with its dimensions
	    this.itemAni = new Animation(this.itemSheet, new Random().nextInt(20) + 450); // declare a Animation, loading the SpriteSheet and inputing the Animation Speed
	    this.thumb = new Image("res/items/"+itemImage+"/"+itemImage+"_thumb.png");
	    this.isCollected = false;
	    this.isActive = true;
	}
	
	public Item(float x, float y, String itemImage) throws SlickException {
		this.x = x;
		this.y = y;
		this.width = 20;
		this.height = 40;
		this.itemImage = itemImage;
		this.collisionBox = new Rectangle((int)x,(int)y,(int)width,(int)height);
	    this.itemSheet = new SpriteSheet("res/items/"+itemImage+"/"+itemImage+"_animation.png",(int)this.width, (int)this.height); // declare a SpriteSheet and load it into java with its dimensions
	    this.itemAni = new Animation(this.itemSheet, new Random().nextInt(20) + 450); // declare a Animation, loading the SpriteSheet and inputing the Animation Speed
	    this.thumb = new Image("res/items/"+itemImage+"/"+itemImage+"_thumb.png");
	    this.isCollected = false;
	    this.isActive = true;
	}
	
	public Item(float x, float y, float width, float height, String itemImage) throws SlickException {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.itemImage = itemImage;
		this.collisionBox = new Rectangle((int)x,(int)y,(int)width,(int)height);
	    this.itemSheet = new SpriteSheet("res/items/"+itemImage+"/"+itemImage+"_animation.png",(int)this.width, (int)this.height); // declare a SpriteSheet and load it into java with its dimensions
	    this.itemAni = new Animation(this.itemSheet, new Random().nextInt(20) + 450); // declare a Animation, loading the SpriteSheet and inputing the Animation Speed
	    this.thumb = new Image("res/items/"+itemImage+"/"+itemImage+"_thumb.png");
	    this.isCollected = false;
	    this.isActive = true;
	}
	
	//Default Update method
	public void update() {
		checkItemCollision();
	}//end of update

	/**
	 * check to see if the item has collided with a mob
	 */
	private void checkItemCollision() {
		
		this.isCollected = false;
		
		//If the player is in the range of the item
		if(this.collisionBox.intersects(Main.util.getPlayer().getBoundingBox())) {
					this.isCollected = true;//The item is collected
					if(this instanceof GravPack)
						Main.util.getSFX(6).play(1f, Main.util.getSoundManager().getVolume());
					else
						Main.util.getSFX(12).play(1f, Main.util.getSoundManager().getVolume());

		}
		
	}//end of checkItemCollision
	
	/* GETTERS */
	
	/**
	 * 
	 * @return the item's x position
	 */
	public float getX() {
		return x;
	}

	/**
	 * 
	 * @return the item's y position
	 */
	public float getY() {
		return y;
	}

	/**
	 * 
	 * @return the item's width
	 */
	public float getWidth() {
		return width;
	}

	/**
	 * 
	 * @return the item's height
	 */
	public float getHeight() {
		return height;
	}

	/**
	 * 
	 * @return the item's collision box
	 */
	public Rectangle getCollisionBox() {
		return collisionBox;
	}

	/**
	 * 
	 * @return the item's spritesheet
	 */
	public SpriteSheet getItemSheet() {
		return itemSheet;
	}

	/**
	 * 
	 * @return the item's animation
	 */
	public Animation getItemAni() {
		return itemAni;
	}
	
	public Image getThumb(){
		return thumb;
	}
	
	/**
	 * 
	 * @return if true, the item is collected. if false, the item is not collected
	 */
	public boolean isCollected() {
		return isCollected;
	}

	/**
	 * 
	 * @return if true, the item is active. if false, the item is not active
	 */
	public boolean isActive() {
		return isActive;
	}
	
	/**
	 * 
	 * @return the current item image
	 */
	public String getItemImage() {
		return itemImage;
	}
	
	/* SETTERS */

	/**
	 * changes the item's x position
	 * @param x
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * changes the item's y position
	 * @param y
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * changes the item's width
	 * @param width
	 */
	public void setWidth(float width) {
		this.width = width;
	}

	/**
	 * changes the item's height
	 * @param height
	 */
	public void setHeight(float height) {
		this.height = height;
	}

	/**
	 * changes the collision box
	 * @param collisionBox
	 */
	public void setCollisionBox(Rectangle collisionBox) {
		this.collisionBox = collisionBox;
	}
	
	/**
	 * changes the item's spritesheet
	 * @param itemSheet
	 */
	public void setItemSheet(SpriteSheet itemSheet) {
		this.itemSheet = itemSheet;
	}

	/**
	 * changes the item's animation
	 * @param itemAni
	 */
	public void setItemAni(Animation itemAni) {
		this.itemAni = itemAni;
	}
	
	/**
	 * changes the item's collection boolean
	 * @param isCollected
	 */
	public void setCollected(boolean isCollected) {
		this.isCollected = isCollected;
	}
	
	/**
	 * changes the item's active boolean
	 * @param isActive
	 */
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	public String toString() {
		return this.x+","+this.y+","+this.width+","+this.height+","+this.itemImage;
	}
	
}//end of class
