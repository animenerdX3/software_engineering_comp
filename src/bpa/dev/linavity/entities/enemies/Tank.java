package bpa.dev.linavity.entities.enemies;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import bpa.dev.linavity.Main;
import bpa.dev.linavity.entities.GravityPack;
import bpa.dev.linavity.entities.Mob;

public class Tank extends Mob{

	private boolean isDetected;
	
	//Create enemy with constructor
	public Tank(float x, float y) throws SlickException{
		
		// Call Mob Constructor
		super();
		
		// Enemy Signature (Eventually Read in from file)
		this.x = x;
		this.y = y;
		this.mobName = "tank";
		this.width = 48;
		this.height = 48;
		this.gravPack = new GravityPack();
		this.maxJumps = 0;
		this.walkSpeed = 0.0313f;
		this.runSpeed = 0.0625f;
		this.health = 50;
		this.damage = 0.5;
		this.canJump = false;
		this.isDetected = false;
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

		detectPlayer(500,500,50,50);//Check to see if the player is in their range of vision
		if(movingLeftOrRight()) {//Find out where the player is
			if(isRunning()) {//Check if the enemy is running
				return runningX();
			}else{//The enemy is walking
				return walkingX();
			}
		}
		
		return 0;
	}
	
	// Determines if the enemy is moving in the X direction
	private boolean movingLeftOrRight() {
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
	}
	
	/**
	 * Determines if the player is within detection radius of emeny
	 * @param leftXRange the left side of the enemy in pixels
	 * @param rightXRange the right side of the enemy in pixels
	 * @param topYRange the top of the enemy in pixels
	 * @param bottomYRange the bottom of the enemy in pixels
	 */
	private void detectPlayer(int leftXRange, int rightXRange, int topYRange, int bottomYRange) {
		if(this.x - leftXRange <= Main.util.getPlayer().getX() && this.x + rightXRange >= Main.util.getPlayer().getX()) { //Check X Range
			if(this.y - topYRange <= Main.util.getPlayer().getY() && this.y + bottomYRange >= Main.util.getPlayer().getY()) //Check Y Range
				this.isDetected = true;
		}
		else
			this.isDetected = false;
	}
	
	/*
	 * Return how the enemy is moving left or right based on input from the user
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
	}
	
	// Determine whether the enemy has jumped more than it's max
	private boolean maxJumps() {
		if(jumps < maxJumps)
			return false;
		else
			return true;
	}
	
	// Determines if the enemy is running
	private boolean jumping() {
		return false;
	}
	
	// END OF ENEMY MOMENTUM CONTROL //
	
	
	/**
	 * Enemy movement AI
	 * @param delta
	 */
	public void enemyUpdates(int delta) {

		collidePlayer();
		
		if(Main.util.getPlayer().getCurrentProjectile() != null)
			collideProjectile();
		
		updateMomentums(); 
		
		super.updateMob(delta);
		
	}//end of moveEnemy
	
	public void collidePlayer() {
		
		if(Main.util.getPlayer().getX() >= getX() && Main.util.getPlayer().getX() <= getX() + getWidth()) {
			if(Main.util.getPlayer().getY() >= getY() - (getHeight() / 2)) {
				if(Main.util.getPlayer().getY() <= getY() + getHeight()){
					System.out.println("Damage Taken");
					Main.util.getPlayer().setHealth(Main.util.getPlayer().getHealth() - getDamage());
					System.out.println("HEALTH: "+ Main.util.getPlayer().getHealth());
				}
			}
		}
		
	}//end of collidePlayer
	
	public void collideProjectile(){
		if(Main.util.getPlayer().getCurrentProjectile().getX() >= getX() && Main.util.getPlayer().getCurrentProjectile().getX() <= getX() + getWidth()){
			if(Main.util.getPlayer().getCurrentProjectile().getY() >= getY() - (getHeight() / 2)){
				System.out.println("ENEMY HIT");
				setHealth(getHealth() - Main.util.getPlayer().getCurrentProjectile().getDamage());
				System.out.println("ENEMY HEALTH: "+getHealth());
			}
		}
	}//end of collideProjectile
	
	/* GETTERS */
	
	public boolean isDetected() {
		return isDetected;
	}
	
	/* SETTERS */
	
	public void setDetection(boolean isDetected) {
		this.isDetected = isDetected;
	}
	
}//end of class
