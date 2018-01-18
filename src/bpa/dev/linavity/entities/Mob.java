package bpa.dev.linavity.entities;

import java.awt.Rectangle;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import bpa.dev.linavity.GameObject;
import bpa.dev.linavity.Main;
import bpa.dev.linavity.entities.tiles.Tile;
import bpa.dev.linavity.physics.Gravity;
import bpa.dev.linavity.utils.Utils;
import bpa.dev.linavity.world.Level;

public abstract class Mob extends GameObject{
	
	// Dimension Variables
		// The x and y coordinates of the mob in the world
		protected float x;
		protected float y;
		private float futureX1, futureX2, futureY1, futureY2;
		// The width and height of the mob
		private int width, height;
	
	// Collision Variables
		// Is the mob colliding with something
		private boolean collide;
		// On which side(s) is the mob colliding
		private boolean cu;
		protected boolean cd;
		private boolean cl;
		private boolean cr;
	
	// Character Variables
		private Image mobImage = null;
		private GravityPack gravPack;
		
	// Physics Variables
		// Momentum in the y direction
		protected float yMomentum;
		// Momentum in the x direction
		protected float xMomentum;
		// Momentum added by jumping
		protected float jumpMomentum;
		// Maximum Number of Possible Jumps:
		protected int maxJumps;
		// Current number of jumps
		protected int jumps;
		// The power that the jump controls
		protected float jumpPower;
		// The speed at which the mob runs
		protected float runSpeed;
		protected float walkSpeed;
		
	//Mob Game Stats
		private double health, damage;
		private boolean isAlive;
		
		private boolean canJump;
		protected boolean isMovingLeft;
		protected boolean isMovingRight;
		
		private Rectangle boundingBox = new Rectangle();
	
	//Default constructor
	public Mob() 
			throws SlickException{
		
		// Character Variables
		this.setMobImage(new Image("res/sprites/player/player_0.png")); //By default, our Mob has the player skin
		this.health = 100;
		this.gravPack = new GravityPack();
		
		// Dimension Variables
		this.width = this.getMobImage().getWidth() - 2;
		this.height = this.getMobImage().getHeight() - 2;
		
		// Collision variables
		this.setCollide(false);
		this.cu = false;
		this.cd = false;
		this.cl = false;
		this.cr = false;
		
		// Position
		this.x = 100;
		this.y = 100;
		
		// Physic's Variables
		this.yMomentum = 0;
		this.xMomentum = 0;
		this.jumpMomentum = 0;
		this.jumpPower = -14;
		
		this.walkSpeed = 2;
		this.runSpeed = 4;
		
		this.isAlive = true;
		this.canJump = true;
		
		this.boundingBox = new Rectangle((int) this.x, (int) this.y, (int) this.width, (int) this.height);
	}
	
	//Non-Default constructor
	public Mob(String textureDirectory, int x, int y) 
			throws SlickException{
		// Character Variables
				this.setMobImage(new Image(textureDirectory)); //By default, our Mob has the player skin
		
		this.x = x;
		this.y = y;
				
		// Dimension Variables
		this.width = this.getMobImage().getWidth();
		this.height = this.getMobImage().getHeight();
		this.maxJumps = 1;
		this.isAlive = true;
		this.canJump = true;  
		
		this.boundingBox = new Rectangle((int) this.x, (int) this.y, (int) this.width, (int) this.height);
	}
	
	/*
	 * Make updates to the mob's stats, position, and collision detection
	 */
	public void updateMob() {
		
		updateFuturePosition();
		
		// Check for collisions with the mob
		checkMobCollisions(Main.util.getLevel(), Main.util.getCam());
		
		// According to the inputs, update the mobs position in the game world
		updateMobPos();
		
		// Update all mob stats
		updateMobStats();
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
	 * Check to see if the mob makes collisions with any other object in the game world
	 */
	//private void checkMobCollisions() {
		
//	}
	
	
	/*
	 * Update mob stats based on game events
	 * 
	 *  Might not need this depending on how we handle messages
	 * 
	 */
	private void updateMobStats() {
		
	}

	/**
	 * Update our mob's position
	 */
	public void update(int gravPower) {
		
		
		//Gravity affecting the player
//		if((gravPower / -1) > 0){ // If the gravity is reversed, flip the decrementation to incrementation
//			this.setY((int) (this.getY() + gravPower - yMomentum));
//			if(yMomentum < 0) 
//				yMomentum += .7;
//		}else{ 
//			this.setY((int) (this.getY() + gravPower - yMomentum));
//			if(yMomentum > 0)
//				yMomentum -= .7;
//		}
		
	}
	
	
	
	
	
	public void checkMobCollisions(Level level, Camera cam) {
		
		Tile[][] screenTiles = level.getScreenTiles(cam); // Load in the visible part of the level
		
		this.cl = false;
		this.cr = false;
		this.cu = false;
		this.cd = false;
//		
//		float px1 = this.x + this.xMomentum;
//		float px2 = px1 + this.width;
//		float py1 = this.y + this.yMomentum;
//		float py2 = py1 + this.height;
		
		for(int r = 0; r < screenTiles.length; r++) { // Run through each row
			for(int c = 0; c < screenTiles[0].length; c++) { // Run through each column
				if(screenTiles[r][c] != null) { // If the tile exists
					if(!screenTiles[r][c].isPassable()) { // And the tile is not passable, check to see if it collides with the player
						
						checkTileCollision(screenTiles[r][c]); // Is the mob colliding with this tile?
						
						
						
//						if(this.boundingBox.intersects(screenTiles[r][c].getCollisionBox())) {
//							if(newLeftCollision(screenTiles[r][c])) {
//								this.cl = true;
//								float spaceX = (screenTiles[r][c].getX() + screenTiles[r][c].getWidth()) - this.x;
//								this.x += spaceX + 1;
//								this.xMomentum = 0;
//								break;
//							}
//							if(newRightCollision(screenTiles[r][c])) {
//								this.cr = true;
//								float spaceX = screenTiles[r][c].getX() - (this.x + this.width);
//								this.xMomentum = 0;
//								break;
//							}
//							if(newUpCollision(screenTiles[r][c])) {
//								this.cu = true;
//							}
//							if(newDownCollision(screenTiles[r][c])) {
//								this.cd = true;
//							}
//						}
//						
						//Left Collision
//						if(leftCollision(screenTiles[r][c])) {
//							System.err.println("LEFT COLLISION");
//							this.cl = true;
//							this.isMovingLeft = false;
//							this.isMovingRight = false;
//							this.x = x + 2;
//						}
//						
//						//Right Collision
//						if(rightCollision(screenTiles[r][c])) {
//								System.err.println("RIGHT COLLISION");
//								this.isMovingLeft = false;
//								this.isMovingRight = false;
//								this.cr = true;
//								this.x = x - 2;
//						}
//						
//						//Up Collision
//						if(upCollision(screenTiles[r][c])) {
//							if(screenTiles[r+2][c].isPassable()) {//No blocks on top of the player
//								System.err.println("UP COLLISION");
//								this.cu = true;
//								this.y = y + 1;
//							}
//							else {
//								this.canJump = false;
//							}
//						}
//						
//						//Down Collision
//						if(downCollision(screenTiles[r][c])) {
//							
//							System.err.println("DOWN COLLISION");
//							this.cd = true;
//							this.y = y - 1;
//							
//							if(screenTiles[r-2][c].isPassable()) {//No blocks on top of the player
//								this.canJump = true;
//							}
//						}
						
						
					}
				}
			}
		}
		
		
		
	}
	
	private void checkTileCollision(Tile tile) {
			
			//moving left - collision check
			if(this.x > (tile.getX() + tile.getWidth())) { //seeing if i am directly to the right of the tile we collided into
				if(leftCollide(tile.getX(), tile.getX() + tile.getWidth(), tile.getY(), tile.getY() + tile.getHeight())) {
					this.x = (tile.getX() + tile.getWidth()) + 1;
					this.cl = true;
					this.xMomentum = 0;
					updateFuturePosition();
					checkTileCollision(tile);
				} 
			}
			
			//moving right
			else if((this.x + this.width) < tile.getX()){
				if(rightCollide(tile.getX(), tile.getX() + tile.getWidth(), tile.getY(), tile.getY() + tile.getHeight())) {
					this.x = tile.getX() - ((this.width) + 1);
					this.cr = true;
					this.xMomentum = 0;
					updateFuturePosition();
					checkTileCollision(tile);
				}
			}

			else if((this.y + this.height) < tile.getY()){
				if(downCollide(tile.getX(), tile.getX() + tile.getWidth(), tile.getY(), tile.getY() + tile.getHeight())){
					this.y = tile.getY() - (this.getHeight() + 1);
					this.cd = true;
					this.yMomentum = 0;
					updateFuturePosition();
					checkTileCollision(tile);
				}
			}

			else if(this.y > (tile.getY() + tile.getHeight())){
				if(upCollide(tile.getX(), tile.getX() + tile.getWidth(), tile.getY(), tile.getY() + tile.getHeight())) {
					this.y = (tile.getY() + tile.getHeight()) + 1;
					this.cu = true;
					this.yMomentum = 0;
					updateFuturePosition();
					checkTileCollision(tile);
				}
			}
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
	
	// Check for mob colliding with

	// NEW ATTEMPT AT COLLISION DETECTION
	
	public boolean newLeftCollision(Tile tile) {
		
		//Left Collision
		if(this.x <= (tile.getX() + tile.getWidth()))
			return true;
		else
			return false;
	}
	
	public boolean newRightCollision(Tile tile) {
		
		float px1 = this.x + this.xMomentum;
		float px2 = px1 + this.width;
		float py1 = this.y + this.yMomentum;
		float py2 = py1 + this.height;
		
		//Right Collision
		if((this.x + this.width) >= tile.getX())
			return true;
		else
			return false;
	}
	
	public boolean newUpCollision(Tile tile) {
		
		float px1 = this.x + this.xMomentum;
		float px2 = px1 + this.width;
		float py1 = this.y + this.yMomentum;
		float py2 = py1 + this.height;
		
		//Up Collision
		if(this.y <= (tile.getY() + tile.getHeight()))
			return true;
		else
			return false;
	}
	
	public boolean newDownCollision(Tile tile) {
		
		float px1 = this.x + this.xMomentum;
		float px2 = px1 + this.width;
		float py1 = this.y + this.yMomentum;
		float py2 = py1 + this.height;
		
		//Down Collision
		if((this.y + this.height) >= tile.getY())
			return true;
		else
			return false;
	}
	
	// END OF NEW ATTEMPT AT COLLISION DETECTION
	
	public boolean leftCollision(Tile tile) {
		//Left Collision
		
		float checkRightX =  tile.getX() + tile.getWidth();
		float checkRightY_Up = tile.getY() - tile.getHeight();
		float checkRightY_Down = tile.getY() + tile.getHeight();
		
		if(x == checkRightX && (y > checkRightY_Up && y < checkRightY_Down))
			return true;
		
		return false;
	}
	
	public boolean rightCollision(Tile tile) {
		//Right Collision
		
		float checkLeftX =  tile.getX() - tile.getWidth();
		float checkLeftY_Up = tile.getY() - tile.getHeight();
		float checkLeftY_Down = tile.getY() + tile.getHeight();
		
		if(x == checkLeftX && (y > checkLeftY_Up && y < checkLeftY_Down))
			return true;
		
		return false;
	}
	
	public boolean downCollision(Tile tile) {
		//Down Collision
		
		float checkDownY =  (tile.getY()) - tile.getHeight();
		float checkDownX_Left = (tile.getX()) - tile.getWidth();
		float checkDownX_Right = (tile.getX()) + tile.getWidth();
		
		
		if(y == checkDownY  && (x > checkDownX_Left && x < checkDownX_Right))
			return true;
		
		return false;
	}
	
	public boolean upCollision(Tile tile) {
		//Up Collision
		
		float checkUpY =  tile.getY() + tile.getHeight();
		float checkUpX_Left = tile.getX() - tile.getWidth();
		float checkUpX_Right = tile.getX() + tile.getWidth();
		
		if(y == checkUpY  && (x > checkUpX_Left && x < checkUpX_Right)) 
			return true;
		
		return false;
	}
	
	/* GETTERS */
	
	/**
	 * @return the cu
	 */
	public boolean isCu() {
		return cu;
	}

	/**
	 * @return the cd
	 */
	public boolean isCd() {
		return cd;
	}

	/**
	 * @return the cl
	 */
	public boolean isCl() {
		return cl;
	}

	/**
	 * @return the cr
	 */
	public boolean isCr() {
		return cr;
	}

	public boolean isCollide() {
		return collide;
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

	public void setCollide(boolean collide) {
		this.collide = collide;
	}
	
	/**
	 * @param cu the cu to set
	 */
	public void setCu(boolean cu) {
		this.cu = cu;
	}

	/**
	 * @param cd the cd to set
	 */
	public void setCd(boolean cd) {
		this.cd = cd;
	}

	/**
	 * @param cl the cl to set
	 */
	public void setCl(boolean cl) {
		this.cl = cl;
	}

	/**
	 * @param cr the cr to set
	 */
	public void setCr(boolean cr) {
		this.cr = cr;
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
