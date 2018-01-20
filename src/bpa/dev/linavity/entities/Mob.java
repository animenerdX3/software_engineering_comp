package bpa.dev.linavity.entities;

import java.awt.Rectangle;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import bpa.dev.linavity.GameObject;
import bpa.dev.linavity.Main;
import bpa.dev.linavity.entities.tiles.Tile;
import bpa.dev.linavity.events.Message;
import bpa.dev.linavity.physics.Gravity;
import bpa.dev.linavity.utils.ErrorLog;
import bpa.dev.linavity.utils.Utils;
import bpa.dev.linavity.world.Level;

public abstract class Mob extends GameObject{
	
	// Dimension Variables
		// The x and y coordinates of the mob in the world
		protected float x, y;
		private float futureX1, futureX2, futureY1, futureY2;
		// The width and height of the mob
		protected int width, height;
	
	// Collision Variables
		// The radius around the mob that we check for collisions
		private int collisionRadius;
		// On which side(s) is the mob colliding
		protected boolean collideUp;
		protected boolean collideDown;
		private boolean collideLeft;
		private boolean collideRight;
	
	// Character Variables
		protected Image mobImage = null;
		protected GravityPack gravPack = new GravityPack(0);
		
	// Physics Variables
		// Momentum in the y direction
		protected float yMomentum;
		// Momentum in the x direction
		protected float xMomentum;
		// Momentum added by jumping
		protected float jumpMomentum;
		// Momentum added by gravity pack
		protected float gravPackMomentum;
		// Maximum Number of Possible Jumps:
		protected int maxJumps;
		// Current number of jumps
		protected int jumps;
		// The power that the jump controls
		protected float jumpPower;
		// The speed at which the mob runs
		protected float runSpeed;
		protected float walkSpeed;
		protected int accessDelta;
		
	//Mob Game Stats
		protected double health, damage;
		protected boolean isAlive;
		
		protected boolean canJump;
		protected boolean isMovingLeft;
		protected boolean isMovingRight;
		
		protected Rectangle boundingBox = new Rectangle();
	
	//Default constructor
	public Mob() 
			throws SlickException{
		
		// Collision variables
		this.collisionRadius = 100;
		this.collideUp = false;
		this.collideDown = false;
		this.collideLeft = false;
		this.collideRight = false;
		
		// Character Variables
		this.jumps = 0;
		this.isAlive = true;
		//this.boundingBox = new Rectangle((int) this.x, (int) this.y, (int) this.width, (int) this.height);
		
//		// Character Variables
//		this.setMobImage(new Image("res/sprites/player/player_0.png")); //By default, our Mob has the player skin
//		this.health = 100;
//		this.gravPack = new GravityPack();
//		
//		// Dimension Variables
//		this.width = this.getMobImage().getWidth() - 2;
//		this.height = this.getMobImage().getHeight() - 2;
//		
//		
//		
//		// Position
//		this.x = 100;
//		this.y = 100;
//		
//		// Physic's Variables
//		this.yMomentum = 0;
//		this.xMomentum = 0;
//		this.jumpMomentum = 0;
//		this.jumpPower = -14;
//		
//		this.walkSpeed = 2;
//		this.runSpeed = 4;
//		
//		this.isAlive = true;
//		this.canJump = true;
		
		
	}
	
	//Non-Default constructor
//	public Mob(String textureDirectory, int x, int y) 
//			throws SlickException{
//		// Character Variables
//		this.setMobImage(new Image(textureDirectory)); //By default, our Mob has the player skin
//		
//		this.x = x;
//		this.y = y;
//		
//		// Enemy Stats
//		this.walkSpeed = 1;
//		this.runSpeed = 2;
//				
//		// Dimension Variables
//		this.width = this.getMobImage().getWidth();
//		this.height = this.getMobImage().getHeight();
//		this.maxJumps = 1;
//		this.isAlive = true;
//		this.canJump = true;  
//		
//		this.boundingBox = new Rectangle((int) this.x, (int) this.y, (int) this.width, (int) this.height);
//	}
	
	/*
	 * Make updates to the mob's stats, position, and collision detection
	 */
	public void updateMob(int delta) {
		
		// Update the future position of the mob, to help with collision detection
		updateFuturePosition();
		
		// Check for collisions with tiles
		checkMobCollisions(Main.util.getEvents(), new Camera(this.x, this.y, this.collisionRadius));
		checkMobCollisions(Main.util.getLevel(), new Camera(this.x, this.y, this.collisionRadius));
		
		// According to the inputs, update the mobs position in the game world
		updateMobPos();
		
		// Update all mob stats
		updateMobStats(delta);
	}
	
	/*
	 * upates the x1,x2,y1,y2 future positions of the mob to help detect collision
	 */
	private void updateFuturePosition() {
		this.futureX1 = this.x + this.xMomentum;
		this.futureX2 = this.x + this.width + this.xMomentum;
		this.futureY1 = this.y + this.yMomentum;
		this.futureY2 = this.y + this.height + this.yMomentum;
	}
	
	/*
	 * Update the mobs x and y positions
	 */
	protected void updateMobPos() {
		this.x = updateX();
		this.y = updateY();
	}
	
	/*
	 * Update the mobs x position based on it's x momentum
	 */
	private float updateX() {
		return this.x += this.xMomentum;
	}
	
	/*
	 * Update the mobs y position based on it's y momentum
	 */
	private float updateY() {
		return this.y += this.yMomentum;
	}
	
	/*
	 * Update mob stats based on game events
	 * 
	 *  Might not need this depending on how we handle messages
	 * 
	 */
	private void updateMobStats(int delta) {
		this.accessDelta = delta;
	}
	
	public void checkMobCollisions(Level level, Camera cam) {
		
		Tile[][] screenTiles = level.getScreenTiles(cam); // Load in the visible part of the level
		
		this.collideLeft = false;
		this.collideRight = false;
		this.collideUp = false;
		this.collideDown = false;
		
		for(int r = 0; r < screenTiles.length; r++) { // Run through each row
			for(int c = 0; c < screenTiles[0].length; c++) { // Run through each column
				if(screenTiles[r][c] != null)  // If the tile exists
					if(!screenTiles[r][c].isPassable()) // And the tile is not passable, check to see if it collides with the player
						checkTileCollision(screenTiles[r][c]); // Is the mob colliding with this tile?
			}
		}
		
	}
	
	private void checkTileCollision(Tile tile) {
			
			System.out.println("TILE X - "+tile.getX()+", TILE Y - "+tile.getY());
			System.out.println("COLLISION BOX X - "+tile.getCollisionBox().getX()+", COLLISION BOX Y - "+tile.getCollisionBox().getY());
		
			//Moving Left - Check Collision
			if(onRight(tile)) { //seeing if i am directly to the right of the tile we collided into
				if(leftCollide(tile.getX(), tile.getX() + tile.getWidth(), tile.getY(), tile.getY() + tile.getHeight())) {
					this.x = (tile.getX() + tile.getWidth()) + 1;
					this.collideLeft = true;
					this.isMovingLeft = false;
					this.xMomentum = 0;
					updateFuturePosition();
					checkTileCollision(tile);
					checkDynamicTiles(tile);
				} 
			}
			
			//Moving Right - Check Collision
			else if(onLeft(tile)){
				if(rightCollide(tile.getX(), tile.getX() + tile.getWidth(), tile.getY(), tile.getY() + tile.getHeight())) {
					this.x = tile.getX() - ((this.width) + 1);
					this.collideRight = true;
					this.isMovingRight = false;
					this.xMomentum = 0;
					updateFuturePosition();
					checkTileCollision(tile);
					checkDynamicTiles(tile);
				}
			}

			//Moving Down - Check Collision
			else if(onTop(tile)){
				if(downCollide(tile.getX(), tile.getX() + tile.getWidth(), tile.getY(), tile.getY() + tile.getHeight())){
					this.y = tile.getY() - (this.getHeight() + 1);
					this.collideDown = true;
					this.yMomentum = 0;
					updateFuturePosition();
					checkTileCollision(tile);
					checkDynamicTiles(tile);
				}
			}

			//Moving Up - Check Collision
			else if(onBottom(tile)){
				if(upCollide(tile.getX(), tile.getX() + tile.getWidth(), tile.getY(), tile.getY() + tile.getHeight())) {
					this.y = (tile.getY() + tile.getHeight()) + 1;
					this.collideUp = true;
					this.yMomentum = 0;
					updateFuturePosition();
					checkTileCollision(tile);
					checkDynamicTiles(tile);
				}
			}
	}
	
	private void checkDynamicTiles(Tile tile) {
		if(tile.getId() == 5) {
			Main.util.getMessageHandler().addMessage(new Message(this, tile, Message.gravPadRecharge, 0.5f));
			try {
				tile.setTexture(new Image("res/tiles/dynamic/antigravity/antigravity_on.png"));
			} catch (SlickException e) {
				ErrorLog.logError(e);
			}
		}
	}
	
	// Check to see if the mob is on the right side of a tile
	private boolean onRight(Tile tile) {
		return this.x > (tile.getX() + tile.getWidth());
	}
	
	// Check to see if the mob is on the right side of a tile
	private boolean onLeft(Tile tile) {
		return (this.x + this.width) < tile.getX();
	}
	
	// Check to see if the mob is on the right side of a tile
	private boolean onBottom(Tile tile) {
		return this.y > (tile.getY() + tile.getHeight());
	}
	
	// Check to see if the mob is on the right side of a tile
	private boolean onTop(Tile tile) {
		return (this.y + this.height) < tile.getY();
	}
	
	// Check to see whether or not the mob is colliding with an object (up)
	private boolean upCollide(float objX1, float objX2, float objY1, float objY2) {
		return (this.futureY1 <= objY2) && inRangeX(objX1, objX2);
	}
	// Check to see whether or not the mob is colliding with an object (down)
	private boolean downCollide(float objX1, float objX2, float objY1, float objY2) {
		return (this.futureY2 >= objY1) && inRangeX(objX1, objX2);
	}
	// Check to see whether or not the mob is colliding with an object (left)
	private boolean leftCollide(float objX1, float objX2, float objY1, float objY2) {
		return (this.futureX1 <= objX2) && inRangeY(objY1, objY2);
	}
	// Check to see whether or not the mob is colliding with an object (right)
	private boolean rightCollide(float objX1, float objX2, float objY1, float objY2) {
		return (this.futureX2 >= objX1) && inRangeY(objY1, objY2);
	}
	
	// Is the mob in range of the x side of an object
	private boolean inRangeX(float objX1, float objX2) {
		return (this.x <= objX2) && ((this.x + this.width) >= objX1);
	}
	// Is the mob in range of the y side of an object
	private boolean inRangeY(float objY1, float objY2) {
		return ((this.y + this.height) >= objY1) && (this.y <= objY2);
	}

	
	/* GETTERS */
	
	/**
	 * @return the cu
	 */
	public boolean isCu() {
		return collideUp;
	}

	/**
	 * @return the cd
	 */
	public boolean isCd() {
		return collideDown;
	}

	/**
	 * @return the cl
	 */
	public boolean isCl() {
		return collideLeft;
	}

	/**
	 * @return the cr
	 */
	public boolean isCr() {
		return collideRight;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * @return the x
	 */
	public float getX() {
		return x;
	}


	/**
	 * @return the y
	 */
	public float getY() {
		return y;
	}
	
	public double getHealth() {
		return health;
	}
	
	public double getDamage() {
		return damage;
	}

	/**
	 * @return the mobImage
	 */
	public Image getMobImage() {
		return mobImage;
	}
	
	public float getYmo() {
		return yMomentum;
	}
	
	public int getJumpNum(){
		return maxJumps;
	}
	
	public boolean isAlive() {
		return isAlive;
	}
	
	public Rectangle getBoundingBox() {
		return boundingBox;
	}
	
	public boolean canJump() {
		return canJump;
	}

	public boolean isMovingLeft() {
		return isMovingLeft;
	}
	
	public boolean isMovingRight() {
		return isMovingRight;
	}
	
	/* SETTERS */

	public void setCu(boolean cu) {
		this.collideUp = cu;
	}

	/**
	 * @param cd the cd to set
	 */
	public void setCd(boolean cd) {
		this.collideDown = cd;
	}

	/**
	 * @param cl the cl to set
	 */
	public void setCl(boolean cl) {
		this.collideLeft = cl;
	}

	/**
	 * @param cr the cr to set
	 */
	public void setCr(boolean cr) {
		this.collideRight = cr;
	}
	
	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}
	
	/**
	 * @param x the x to set
	 */
	public  void setX(float x) {
		this.x = x;
	}

	/**
	 * @param y the y to set
	 */
	public  void setY(float y) {
		this.y = y;
	}
	
	/**
	 * @param mobImage the mobImage to set
	 */
	public void setMobImage(Image mobImage) {
		this.mobImage = mobImage;
	}


	public void setYmo(float ymo) {
		this.yMomentum = ymo;
	}
	
	public void setJumpNum(int jumpNum){
		this.maxJumps = jumpNum;
	}
	
	public void setHealth(double health) {
		this.health = health;
	}
	
	public void setDamage(double damage) {
		this.damage = damage;
	}
	
	public void setIsAlive(boolean isAlive){
		this.isAlive = isAlive;
	}

	public void setBoundingBox(Rectangle boundingBox) {
		this.boundingBox = boundingBox;
	}
	
	public void setCanJump(boolean canJump) {
		this.canJump = canJump;
	}
	
	public void setIsMovingLeft(boolean isMovingLeft) {
		this.isMovingLeft = isMovingLeft;
	}
	
	public void setIsMovingRight(boolean isMovingRight) {
		this.isMovingRight = isMovingRight;
	}
	
}//end of class
