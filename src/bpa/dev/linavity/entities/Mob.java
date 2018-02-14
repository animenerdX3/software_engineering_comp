package bpa.dev.linavity.entities;

import java.awt.Rectangle;
import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import bpa.dev.linavity.GameObject;
import bpa.dev.linavity.Main;
import bpa.dev.linavity.entities.tiles.Tile;
import bpa.dev.linavity.events.Message;

public abstract class Mob extends GameObject{
	
	// Dimension Variables
		// The x and y coordinates of the mob in the world
		protected float x, y;
		protected float futureX1, futureX2, futureY1, futureY2;
		// The width and height of the mob
		protected int width, height;
	
	// Collision Variables
		// The radius around the mob that we check for collisions
		protected int collisionRadius;
		// On which side(s) is the mob colliding
		protected boolean collideUp;
		protected boolean collideDown;
		protected boolean collideLeft;
		protected boolean collideRight;
	
	// Character Variables
		protected String mobName;
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
		
	//Animation
	    protected SpriteSheet moveRight; // initate a SpriteSheet
	    protected Animation moveRightAni; // initate a Animation
		
	    protected SpriteSheet moveLeft; // initate a SpriteSheet
	    protected Animation moveLeftAni; // initate a Animation
		
	    protected SpriteSheet standStillLeft; //initiate a SpriteSheet
	    protected Animation standStillLeftAni; // initate a Animation
	    
	    protected SpriteSheet standStillRight; //initiate a SpriteSheet
	    protected Animation standStillRightAni; // initate a Animation
	    
	    protected SpriteSheet moveRightFlipped; // initate a SpriteSheet
	    protected Animation moveRightFlippedAni; // initate a Animation
	    
	    protected SpriteSheet moveLeftFlipped; // initate a SpriteSheet
	    protected Animation moveLeftFlippedAni; // initate a Animation
	    
	    protected SpriteSheet standStillLeftFlipped; // initate a SpriteSheet
	    protected Animation standStillLeftFlippedAni; // initate a Animation
	    
	    protected SpriteSheet standStillRightFlipped; // initate a SpriteSheet
	    protected Animation standStillRightFlippedAni; // initate a Animation

	    protected Animation currentStillImage;
	    protected Animation currentImage;
		
	//Mob Movement Variables
		protected boolean autoDirectionLeft;
		protected boolean collidePlayer;
	    
	//Default constructor
	public Mob() 
			throws SlickException{
		
		// Collision variables
		this.collisionRadius = 100;
		this.collideUp = false;
		this.collideDown = false;
		this.collideLeft = false;
		this.collideRight = false;
		this.boundingBox = new Rectangle((int)x,(int)y,width,height);
		
		// Character Variables
		this.jumps = 0;
		this.isAlive = true;
		this.collidePlayer = false;
	
	}

	/*
	 * Make updates to the mob's stats, position, and collision detection
	 */
	public void updateMob(int delta) throws SlickException {
		
		// Update the future position of the mob, to help with collision detection
		updateFuturePosition();
		
		//Reset our collision variables
		this.collideLeft = false;
		this.collideRight = false;
		this.collideUp = false;
		this.collideDown = false;
		
		// Check for collisions with tiles
		checkMobCollisions(Main.util.getLevel().getScreenTiles(new Camera(this.x, this.y, this.collisionRadius), Main.util.getLevel().getEvents()), new Camera(this.x, this.y, this.collisionRadius));
		checkMobCollisions(Main.util.getLevel().getScreenTiles(new Camera(this.x, this.y, this.collisionRadius), Main.util.getLevel().getMap()), new Camera(this.x, this.y, this.collisionRadius));
		
		//Check for collisions with mobs
		this.collidePlayer = false;
		
		//Get the level mobs
		ArrayList<Mob>levelMobs = Main.util.getLevelMobs();
		
		//If the current mob is not the player, check if they are colliding with the player
		if(this != Main.util.getPlayer())
			collideWithMob(Main.util.getPlayer());//Is the mob colliding with the player?
		//If the current mob is the player, check if they are colliding with the other mobs
		else
			for(int i = 1; i < levelMobs.size(); i++)
				collideWithMob(levelMobs.get(i));//Is the player colliding with a mob?
		
		// According to the inputs, update the mobs position in the game world
		updateMobPos();
		
		// Update all mob stats
		updateMobStats(delta);
		
	}//end of updateMob
	
	/**
	 * default update method, for use in the child classes
	 * @param delta
	 * @throws SlickException 
	 */
	public void update(int delta) throws SlickException {
		
	}//end of update
	
	/*
	 * updates the x1,x2,y1,y2 future positions of the mob to help detect collision
	 */
	protected void updateFuturePosition() {
		this.futureX1 = this.x + this.xMomentum;
		this.futureX2 = this.x + this.width + this.xMomentum;
		this.futureY1 = this.y + this.yMomentum;
		this.futureY2 = this.y + this.height + this.yMomentum;
	}//end of updateFuturePosition
	
	/*
	 * Update the mobs x and y positions
	 */
	protected void updateMobPos() {
		this.x = updateX();
		this.y = updateY();
	}//end pf updateMobPos
	
	/*
	 * Update the mobs x position based on it's x momentum
	 */
	private float updateX() {
		return this.x += this.xMomentum;
	}//end of updateX
	
	/*
	 * Update the mobs y position based on it's y momentum
	 */
	private float updateY() {
		return this.y += this.yMomentum;
	}//end of updateY
	
	/**
	 * uses delta to update our mobs
	 * @param delta
	 */
	private void updateMobStats(int delta) {
		this.accessDelta = delta;
		Main.util.delta = delta;
	}//end of updateMobStats
	
	/**
	 * check if the mob collides with an object
	 * @param level
	 * @param cam
	 * @throws SlickException 
	 */
	public void checkMobCollisions(Tile[][] screenTiles, Camera cam) throws SlickException {
		//Check for through our level map
		for(int r = 0; r < screenTiles.length; r++) { // Run through each row
			for(int c = 0; c < screenTiles[0].length; c++) // Run through each column
				if(screenTiles[r][c] != null)  // If the tile exists
						checkTileCollision(screenTiles[r][c], r, c); // Is the mob colliding with this tile?
		}

	}//end of checkMobCollisions
	
	/**
	 * check to see if the mob has collided with a tile
	 * @param tile
	 * @param i tile row in the level
	 * @param j tile column in the level
	 * @throws SlickException 
	 */
	private void checkTileCollision(Tile tile, int i, int j) throws SlickException {
					
			//Moving Left - Check Collision
			if(onRight(tile)) { //seeing if i am directly to the right of the tile we collided into
				if(leftCollide(tile.getX(), tile.getX() + (float) tile.getCollisionBox().getWidth(), tile.getY(), tile.getY() + (float) tile.getCollisionBox().getHeight())) {
					if(!tile.isPassable()) { // If tile is not passable, check to see if it collides with the player
						this.x = (tile.getX() + (float) tile.getCollisionBox().getWidth()) + 1;
						this.collideLeft = true;
						this.isMovingLeft = false;
						this.xMomentum = 0;
					}
					updateFuturePosition();
					checkDynamicImpassableTiles(tile, i, j);
				} 
			}
			
			//Moving Right - Check Collision
			else if(onLeft(tile)){
				if(rightCollide(tile.getX(), tile.getX() + (float) tile.getCollisionBox().getWidth(), tile.getY(), tile.getY() + (float) tile.getCollisionBox().getHeight())) {
					if(!tile.isPassable()) { // If tile is not passable, check to see if it collides with the player
						this.x = tile.getX() - ((this.width) + 1);
						this.collideRight = true;
						this.isMovingRight = false;
						this.xMomentum = 0;
					}
					updateFuturePosition();
					checkDynamicImpassableTiles(tile, i, j);
				}
			}

			//Moving Down - Check Collision
			else if(onTop(tile)){
				
				if(downCollide(tile.getX(), tile.getX() + (float) tile.getCollisionBox().getWidth(), tile.getY(), tile.getY() + (float) tile.getCollisionBox().getHeight())){
					if(!tile.isPassable()) { // If tile is not passable, check to see if it collides with the player
						this.y = tile.getY() - (this.getHeight() + 1);
						this.collideDown = true;
						this.yMomentum = 0;
					}
					updateFuturePosition();
					checkDynamicImpassableTiles(tile, i, j);
				}
			}

			//Moving Up - Check Collision
			else if(onBottom(tile)){
				if(upCollide(tile.getX(), tile.getX() + (float) tile.getCollisionBox().getWidth(), tile.getY(), tile.getY() + (float) tile.getCollisionBox().getHeight())) {
					if(!tile.isPassable()) { // If tile is not passable, check to see if it collides with the player
					this.y = (tile.getY() + (float) tile.getCollisionBox().getHeight()) + 1;
					this.collideUp = true;
					this.yMomentum = 0;
					}
					updateFuturePosition();
					checkDynamicImpassableTiles(tile, i, j);
				}
			}
			
			checkDynamicPassableTiles(tile, i, j);
	}//end of checkTileCollision
	
	/**
	 * check to see if the mob has collided with a mob
	 * @param mob
	 */
	protected void collideWithMob(Mob mob) {
		//Moving Left - Check Collision
				if(onRight(mob)) { //seeing if i am directly to the right of the tile we collided into
					if(leftCollide(mob.getX(), mob.getX() + (float) mob.getBoundingBox().getWidth(), mob.getY(), mob.getY() + (float) mob.getBoundingBox().getHeight())) {
							this.x = (mob.getX() + (float) mob.getBoundingBox().getWidth()) + 1;
							this.collideLeft = true;
							this.isMovingLeft = false;
							this.xMomentum = 0;
							this.autoDirectionLeft = !this.autoDirectionLeft;
							this.collidePlayer = true;
							mob.collidePlayer = true;
							updateFuturePosition();
					}
				}
				
				//Moving Right - Check Collision
				else if(onLeft(mob)){
					if(rightCollide(mob.getX(), mob.getX() + (float) mob.getBoundingBox().getWidth(), mob.getY(), mob.getY() + (float) mob.getBoundingBox().getHeight())) {
							this.x = mob.getX() - ((this.width) + 1);
							this.collideRight = true;
							this.isMovingRight = false;
							this.xMomentum = 0;
							this.autoDirectionLeft = !this.autoDirectionLeft;
							this.collidePlayer = true;
							mob.collidePlayer = true;
							updateFuturePosition();
					}
				}

				//Moving Down - Check Collision
				else if(onTop(mob)){
					
					if(downCollide(mob.getX(), mob.getX() + (float) mob.getBoundingBox().getWidth(), mob.getY(), mob.getY() + (float) mob.getBoundingBox().getHeight())){
							System.out.println("COLLIDE DOWN");
							this.y = mob.getY() - (this.getHeight() + 1);
							this.collideDown = true;
							this.yMomentum = 0;
							this.collidePlayer = true;
							mob.collidePlayer = true;
							updateFuturePosition();
					}
				}

				//Moving Up - Check Collision
				else if(onBottom(mob)){
					if(upCollide(mob.getX(), mob.getX() + (float) mob.getBoundingBox().getWidth(), mob.getY(), mob.getY() + (float) mob.getBoundingBox().getHeight())) {
						this.y = (mob.getY() + (float) mob.getBoundingBox().getHeight()) + 1;
						this.collideUp = true;
						this.yMomentum = 0;
						this.collidePlayer = true;
						mob.collidePlayer = true;
						updateFuturePosition();
						}
				}
				
	}//end of collideWithMob
	
	/**
	 * check to see if the mob is colliding with any dynamic tiles
	 * @param tile
	 * @param i
	 * @param j
	 * @throws SlickException 
	 */
	private void checkDynamicPassableTiles(Tile tile, int i, int j) throws SlickException {
		
		// Lever getting activated / toggled
		if(tile.getId() == Tile.leverID) {
			Rectangle rect = Main.util.getPlayer().getBoundingBox();
			if(rect.intersects(tile.getCollisionBox())) {
				tile.onCollide(this);
			}
		}
		
		// Event Tile getting activated / toggled
		if(tile.getId() == Tile.eventTileID) {
			Rectangle rect = Main.util.getPlayer().getBoundingBox();
			if(rect.intersects(tile.getCollisionBox())) {
				tile.onCollide(this);
			}
		}
		
		// Ladder being used
		if(tile.getId() == Tile.ladderTopID || tile.getId() == Tile.ladderMiddleID || tile.getId() == Tile.ladderBottomID) {
			Rectangle rect = Main.util.getPlayer().getBoundingBox();
			if(rect.intersects(tile.getCollisionBox())) {
				tile.onCollide(this);
			}
		}
		
	}//end of checkDynamicPassableTiles
	
	private void checkDynamicImpassableTiles(Tile tile, int i, int j) throws SlickException {
		
		// GravPad Recharging the player's gravPad
		if(tile.getId() == Tile.gravPadID) {
				if(this.collideDown)
					Main.util.getMessageHandler().addMessage(new Message(this, tile, Message.gravPadRecharge, 0.5f));
				else
					this.yMomentum = -20;
		}
		
		if(tile.getId() == Tile.spikesID) {
			tile.onCollide(this);
		}
		
		if(tile.getId() == Tile.doorID) {
			tile.onCollide(this);
		}
		
		if(tile.getId() == Tile.warpHoleID) {
				Main.util.getMessageHandler().addMessage(new Message(this, tile, Message.endLevel, null));
		}

	}//end of checkDynamicImpassableTiles
	
	// Check to see if the mob is on the right side of a tile
	private boolean onRight(Tile tile) {
		return this.x > (tile.getX() + (float) tile.getCollisionBox().getWidth());
	}
	
	// Check to see if the mob is on the right side of a tile
	private boolean onLeft(Tile tile) {
		return (this.x + this.width) < tile.getX();
	}
	
	// Check to see if the mob is on the right side of a tile
	private boolean onBottom(Tile tile) {
		return this.y > (tile.getY() + (float) tile.getCollisionBox().getHeight());
	}
	
	// Check to see if the mob is on the right side of a tile
	private boolean onTop(Tile tile) {
		return (this.y + this.height) < tile.getY();
	}
	
	// Check to see if the mob is on the right side of a tile
	private boolean onRight(Mob mob) {
		return this.x > (mob.getX() + (float) mob.getBoundingBox().getWidth());
	}
	
	// Check to see if the mob is on the right side of a tile
	private boolean onLeft(Mob mob) {
		return (this.x + this.width) < mob.getX();
	}
	
	// Check to see if the mob is on the right side of a tile
	private boolean onBottom(Mob mob) {
		return this.y > (mob.getY() + (float) mob.getBoundingBox().getHeight());
	}
	
	// Check to see if the mob is on the right side of a tile
	private boolean onTop(Mob mob) {
		return (this.y + this.height) < mob.getY();
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
	 * @return if true, the mob is colliding up. if false, the mob is not
	 */
	public boolean isCu() {
		return collideUp;
	}

	/**
	 * @return if true, the mob is colliding down. if false, the mob is not
	 */
	public boolean isCd() {
		return collideDown;
	}

	/**
	 * @return if true, the mob is colliding left. if false, the mob is not
	 */
	public boolean isCl() {
		return collideLeft;
	}

	/**
	 * @return if true, the mob is colliding right. if false, the mob is not
	 */
	public boolean isCr() {
		return collideRight;
	}

	/**
	 * @return the mob's width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @return the mob's height
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * @return the mob's x
	 */
	public float getX() {
		return x;
	}

	public float getFutureX1() {
		return futureX1;
	}

	public float getFutureX2() {
		return futureX2;
	}

	/**
	 * @return the mob's y
	 */
	public float getY() {
		return y;
	}
	
	public float getFutureY1() {
		return futureY1;
	}

	public float getFutureY2() {
		return futureY2;
	}
	
	/**
	 * 
	 * @return the mob's x momentum
	 */
	public float getXMomentum() {
		return xMomentum;
	}
	
	/**
	 * 
	 * @return the mob's y momentum
	 */
	public float getYMomentum() {
		return yMomentum;
	}
	
	/**
	 * 
	 * @return the mob's health
	 */
	public double getHealth() {
		return health;
	}
	
	/**
	 * 
	 * @return the mob's damage
	 */
	public double getDamage() {
		return damage;
	}

	/**
	 * 
	 * @return the mob's number of allowed jumps
	 */
	public int getJumpNum(){
		return maxJumps;
	}
	
	/**
	 * 
	 * @return if true, the mob is alive. if false, the mob is not alive
	 */
	public boolean isAlive() {
		return isAlive;
	}
	
	/**
	 * 
	 * @return the mob's bounding box
	 */
	public Rectangle getBoundingBox() {
		return boundingBox;
	}
	
	/**
	 * 
	 * @return if true, the mob can jump. if false, the mob cannot jump.
	 */
	public boolean canJump() {
		return canJump;
	}

	/**
	 * 
	 * @return if true, the mob is moving left. if false, the mob is not
	 */
	public boolean isMovingLeft() {
		return isMovingLeft;
	}
	
	/**
	 * 
	 * @return if true, the mob is moving right. if false, the mob is not
	 */
	public boolean isMovingRight() {
		return isMovingRight;
	}
	
	/**
	 * 
	 * @return the mob's left animation
	 */
	public Animation getLeftAni() {
		return moveLeftAni;
	}
	
	/**
	 * 
	 * @return the mob's right animation
	 */
	public Animation getRightAni() {
		return moveRightAni;
	}
	
	/**
	 * 
	 * @return the mob's still animation
	 */
	public Animation getStillLeftAni() {
		return standStillLeftAni;
	}
	
	/**
	 * 
	 * @return the mob's still animation
	 */
	public Animation getStillRightAni() {
		return standStillRightAni;
	}
	
	/**
	 * 
	 * @return the mob's current animation image
	 */
	public Animation getCurrentImage() {
		return currentImage;
	}
	
	/**
	 * 
	 * @return the mob's current still animation
	 */
	public Animation getCurrentStillImage() {
		return currentStillImage;
	}
	
	/**
	 * 
	 * @return the mob's right animation flipped (for reverse gravity)
	 */
	public Animation getMoveRightFlippedAni() {
			return moveRightFlippedAni;
		}

	/**
	 * 
	 * @return the mob's left animation flipped (for reverse gravity)
	 */
	public Animation getMoveLeftFlippedAni() {
			return moveLeftFlippedAni;
		}

	/**
	 * 
	 * @return the mob's still animation flipped (for reverse gravity)
	 */
	public Animation getStandStillLeftFlippedAni() {
			return standStillLeftFlippedAni;
		}
	
	/**
	 * 
	 * @return the mob's still animation flipped (for reverse gravity)
	 */
	public Animation getStandStillRightFlippedAni() {
			return standStillRightFlippedAni;
		}


	/**
	 * 
	 * @return if true, the player and mob are colliding. If false, they are not
	 */
	public boolean isCollidePlayer() {
		return collidePlayer;
	}
	
	/* SETTERS */
	/**
	 * changes the collision up boolean
	 * @param cu
	 */
	public void setCu(boolean cu) {
		this.collideUp = cu;
	}

	/**
	 * changes the collision down boolean
	 * @param cd
	 */
	public void setCd(boolean cd) {
		this.collideDown = cd;
	}

	/**
	 * changes the collision left boolean
	 * @param cl
	 */
	public void setCl(boolean cl) {
		this.collideLeft = cl;
	}

	/**
	 * changes the collision right boolean
	 * @param cr
	 */
	public void setCr(boolean cr) {
		this.collideRight = cr;
	}
	
	/**
	 * changes the mob's width
	 * @param width
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * changes the mob's height
	 * @param height
	 */
	public void setHeight(int height) {
		this.height = height;
	}
	
	/**
	 * changes the mob's x position
	 * @param x
	 */
	public  void setX(float x) {
		this.x = x;
	}

	public void setFutureX1(float futureX1) {
		this.futureX1 = futureX1;
	}

	public void setFutureX2(float futureX2) {
		this.futureX2 = futureX2;
	}
	
	/**
	 * changes the mob's y position
	 * @param y
	 */
	public  void setY(float y) {
		this.y = y;
	}

	public void setFutureY1(float futureY1) {
		this.futureY1 = futureY1;
	}

	public void setFutureY2(float futureY2) {
		this.futureY2 = futureY2;
	}
	
	/**
	 * changes the mob's x momentum
	 * @param xMomentum
	 */
	public void setXMomentum(float xMomentum) {
		this.xMomentum = xMomentum;
	}
	
	/**
	 * changes the mob's y momentum
	 * @param yMomentum
	 */
	public void setYMomentum(float yMomentum) {
		this.yMomentum = yMomentum;
	}
	
	/**
	 * changes the mob's number of allowed jumps
	 * @param jumpNum
	 */
	public void setJumpNum(int jumpNum){
		this.maxJumps = jumpNum;
	}
	
	/**
	 * changes the mob's health
	 * @param health
	 */
	public void setHealth(double health) {
		this.health = health;
	}
	
	/**
	 * changes the mob's damage
	 * @param damage
	 */
	public void setDamage(double damage) {
		this.damage = damage;
	}
	
	/**
	 * changes the mob's alive boolean
	 * @param isAlive
	 */
	public void setIsAlive(boolean isAlive){
		this.isAlive = isAlive;
	}

	/**
	 * changes the mob's bounding box
	 * @param boundingBox
	 */
	public void setBoundingBox(Rectangle boundingBox) {
		this.boundingBox = boundingBox;
	}
	
	/**
	 * changes the mob's jump boolean
	 * @param canJump
	 */
	public void setCanJump(boolean canJump) {
		this.canJump = canJump;
	}
	
	/**
	 * changes the mob's moving left boolean
	 * @param isMovingLeft
	 */
	public void setIsMovingLeft(boolean isMovingLeft) {
		this.isMovingLeft = isMovingLeft;
	}
	
	/**
	 * changes the mob's moving right boolean
	 * @param isMovingRight
	 */
	public void setIsMovingRight(boolean isMovingRight) {
		this.isMovingRight = isMovingRight;
	}
	
	/**
	 * changes the mob's current animation image
	 * @param currentImage
	 */
	public void setCurrentImage(Animation currentImage) {
		this.currentImage = currentImage;
	}
	
	/**
	 * changes the mob's current still animation image
	 * @param currentStillImage
	 */
	public void setCurrentStillImage(Animation stillCurrentImage) {
		this.currentStillImage = stillCurrentImage;
	}
	
}//end of class
