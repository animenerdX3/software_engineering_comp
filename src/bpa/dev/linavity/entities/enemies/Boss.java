package bpa.dev.linavity.entities.enemies;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import bpa.dev.linavity.Main;
import bpa.dev.linavity.entities.GravityPack;
import bpa.dev.linavity.entities.Mob;

public class Boss extends Mob{

	private boolean isDetected;
	private boolean toggleDirection;
	
	//Create enemy with constructor
	public Boss(float x, float y) throws SlickException{
		
		// Call Mob Constructor
		super();
		
		// Enemy Signature
		this.x = x;
		this.y = y;
		this.mobName = "boss";
		this.width = 148;
		this.height = 133;
		this.gravPack = new GravityPack();
		this.maxJumps = 0;
		this.walkSpeed = 0.0625f;
		this.runSpeed = 0.125f;
		this.health = 500;
		this.damage = 0.75;
		this.canJump = false;
		this.isDetected = true;
		
		this.moveLeft = new SpriteSheet("res/sprites/"+this.mobName+"/"+this.mobName+"_left_ani.png",150,135); // declare a SpriteSheet and load it into java with its dimentions
	    this.moveLeftAni = new Animation(this.moveLeft, 450); // declare a Animation, loading the SpriteSheet and inputing the Animation Speed
	    this.moveRight = new SpriteSheet("res/sprites/"+this.mobName+"/"+mobName+"_right_ani.png",150,135); // declare a SpriteSheet and load it into java with its dimentions
	    this.moveRightAni = new Animation(this.moveRight, 450); // declare a Animation, loading the SpriteSheet and inputing the Animation Speed
	    this.standStillLeft = new SpriteSheet("res/sprites/"+this.mobName+"/"+this.mobName+"_1.png",150,135); // declare a SpriteSheet and load it into java with its dimentions
	    this.standStillLeftAni = new Animation(this.standStillLeft, 450); // declare a SpriteSheet and load it into java with its dimensions
	    this.standStillRight = new SpriteSheet("res/sprites/"+this.mobName+"/"+this.mobName+"_0.png",150,135); // declare a SpriteSheet and load it into java with its dimentions
	    this.standStillRightAni = new Animation(this.standStillRight, 450); // declare a SpriteSheet and load it into java with its dimensions
	    this.currentImage = this.standStillRightAni;
	}
	
	
	// ENEMY MOMENTUM CONTROL // 
	
	/*
	 * Update the enemy's x and y momentum
	 */
	private void updateMomentums() {
		this.xMomentum = determineXMomentum();
		this.yMomentum = determineYMomentum();
	}
	
	/*
	 * Update the enemy's x momentum
	 */
	private float determineXMomentum() {
		float setXMomentum = 0;
		setXMomentum += xMovement();
		return setXMomentum;
	}
	
	
	/*
	 * Update the player's y momentum
	 */
	private float determineYMomentum() {
		
		// This is the value that we will return for yMomentum based on a few different variables and calculations
		float setYMomentum = 0;
		
		// Gravity affects the yMomentum of the player
		setYMomentum += Main.util.getGravity().getGravityPower();
		
		// Any other function affecting the y momentum of the mob
		setYMomentum += yMovement();
		
		// Return our calculated yMomentum
		return setYMomentum;
	}
	
	/*
	 * Return how the enemy is moving left or right based on input from the user
	 */
	private float xMovement() {

		if(movingLeftOrRight()) {//Find out where the player is
			if(isRunning()) {//Check if the enemy is running
				return runningX();
			}else{//The enemy is walking
				return walkingX();
			}
		}
		
		return 0;
	}//end of xMovement
	
	// Determines if the enemy is moving in the X direction
	private boolean movingLeftOrRight() {
		
		if(this.isDetected && movingLeft()) 
			this.currentImage = this.moveLeftAni;
		
		else if(this.isDetected && movingRight()) 
			this.currentImage = this.moveRightAni;
		
		else {
			if(toggleDirection)
				this.currentImage = this.standStillLeftAni;
			else
				this.currentImage = this.standStillRightAni;
		}
		
		return movingLeft() || movingRight();
	}
	
	// Determine if the enemy is moving left based on the player
	private boolean movingLeft() {
		
		return Main.util.getPlayer().getX() < getX();
	}
	
	// Determine if the enemy is moving right based on the player
	private boolean movingRight() {
			
		return Main.util.getPlayer().getX() > getX();
	}
	
	
	
	// Determines if the enemy is running
	private boolean isRunning() {
		return false;
	}
	
	/*
	 * The enemy is running, return the value for a specific running direction
	 */
	private float runningX() {
		
		if(this.isDetected) {
			if(movingLeft()) // Moving left
				return -this.runSpeed * this.accessDelta;
			else if(movingRight())  // Moving right
				return this.runSpeed * this.accessDelta;
		}
		
		return 0;
	}
	
	/*
	 * The enemy is walking, return the value for a specific walking direction
	 */
	private float walkingX() {
		
		if(this.isDetected) {
			if(movingLeft()) // Moving left
				return -this.walkSpeed * this.accessDelta;
			else if(movingRight())  // Moving right
				return this.walkSpeed * this.accessDelta;
		}
		return 0;
	
	}//end of walkingX
	
	/*
	 * Return how the enemy is moving up or down
	 */
	private float yMovement() {
		
		if(!maxJumps()) {
			if(jumping()) {
				jumps++;
				jumpMomentum = jumpPower;
			}
		}
		
		if(jumpMomentum < 0){
			jumpMomentum += .7;
			if(jumpMomentum > 0)
				jumpMomentum = 0;
		}
		
		if(this.collideDown)
			this.jumps = 0;
		
		return jumpMomentum;
	}//end of yMovement
	
	// Determine whether the enemy has jumped more than it's max
	private boolean maxJumps() {
		if(jumps < maxJumps)
			return false;
		else
			return true;
	}
	
	// Tells the starter that they cannot jump
	private boolean jumping() {
		return false;
	}
	
	
	/**
	 * Enemy movement AI
	 * @param delta
	 * @throws SlickException 
	 */
	@Override
	public void update(int delta) throws SlickException {
		
		if(Main.util.getPlayer().getCurrentProjectile() != null)
			collideProjectile();
		
		updateMomentums(); 
		
		if(this.collidePlayer)
			dealDamage();
		
		super.updateMob(delta);
		
	}//end of updateEnemy
	
	/**
	 * deals damage to the player
	 */
	public void dealDamage() {
		
		System.out.println("Damage Taken");
		Main.util.getPlayer().setHealth(Main.util.getPlayer().getHealth() - getDamage());
		System.out.println("HEALTH: "+ Main.util.getPlayer().getHealth());
		
	}//end of dealDamage
	
	/**
	 * checks to see if the starter has been hit with a projectile
	 */
	public void collideProjectile(){

		if(Main.util.getPlayer().getCurrentProjectile().getX() >= getX() && Main.util.getPlayer().getCurrentProjectile().getX() <= (getX() + getWidth())) {
			if(Main.util.getPlayer().getCurrentProjectile().getY() >= getY() - (getHeight() / 2)) {
				if(Main.util.getPlayer().getCurrentProjectile().getY() <= getY() + getHeight()){
					System.out.println("ENEMY HIT");
					setHealth(getHealth() - Main.util.getPlayer().getCurrentProjectile().getDamage());
					Main.util.getSFX(5).play(1f, Main.util.getSoundManager().getVolume());
					System.out.println("ENEMY HEALTH: "+getHealth());
					Main.util.getPlayer().setCurrentProjectile(null);
					Main.util.getPlayer().setProjectileExists(false);
				}
			}
		}

		
	}//end of collideProjectile
	
	/* GETTERS */
	
	/**
	 * 
	 * @return if true, the player is detected. if false, the player is not
	 */
	public boolean isDetected() {
		return isDetected;
	}
	
	/* SETTERS */
	
	/**
	 * changes the detection of the player
	 * @param isDetected
	 */
	public void setDetection(boolean isDetected) {
		this.isDetected = isDetected;
	}
	
	/**
	 * overwrites the toString class for saving purposes
	 */
	public String toString() {
		//Save the class name, the x position, the y position, and the health
		return "Boss,"+this.x+","+this.y+","+this.health;
	}
	
}//end of class
