package bpa.dev.linavity.entities.enemies;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import bpa.dev.linavity.Main;
import bpa.dev.linavity.entities.GravityPack;
import bpa.dev.linavity.entities.Mob;

public class Starter extends Mob{

	//Create enemy with constructor
	public Starter(float x, float y) throws SlickException{
		
		// Call Mob Constructor
		super();
		
		// Enemy Signature (Eventually Read in from file)
		this.x = x;
		this.y = y;
		this.mobImage = new Image("res/sprites/starter/starter_0.png");
		this.width = this.mobImage.getWidth() - 2;
		this.height = this.mobImage.getHeight() - 2;
		this.gravPack = new GravityPack(0);
		this.maxJumps = 0;
		this.walkSpeed = 0.0625f;
		this.runSpeed = 0.125f;
		this.health = 100;
		this.damage = 0.25;
		this.canJump = false;
		
		
		
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

		if(movingLeftOrRight()) {
			if(isRunning()) {
				return runningX();
			}else{
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
		
		if(movingLeft()){ // Moving left
			return -this.runSpeed * this.accessDelta;
		}else if(movingRight()) { // Moving right
			return this.runSpeed * this.accessDelta;
		}
		
		return 0;
	}
	
	/*
	 * The enemy is walking, return the value for a specific walking direction
	 */
	private float walkingX() {
		
		if(movingLeft()){ // Moving left
			return -this.walkSpeed * this.accessDelta;
		}else if(movingRight()) { // Moving right
			return this.walkSpeed * this.accessDelta;
		}
		
		return 0;
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
		
		if(Main.util.getPlayer().getX() >= getX() && Main.util.getPlayer().getX() <= getX() + getMobImage().getWidth()) {
			if(Main.util.getPlayer().getY() >= getY() - (getMobImage().getHeight() / 2)) {
				if(Main.util.getPlayer().getY() <= getY() + getMobImage().getHeight()){
					System.out.println("Damage Taken");
					Main.util.getPlayer().setHealth(Main.util.getPlayer().getHealth() - getDamage());
					System.out.println("HEALTH: "+ Main.util.getPlayer().getHealth());
				}
			}
		}
		
	}//end of collidePlayer
	
	public void collideProjectile(){
		if(Main.util.getPlayer().getCurrentProjectile().getX() >= getX() && Main.util.getPlayer().getCurrentProjectile().getX() <= getX() + getMobImage().getWidth()){
			if(Main.util.getPlayer().getCurrentProjectile().getY() >= getY() - (getMobImage().getHeight() / 2)){
				System.out.println("ENEMY HIT");
				setHealth(getHealth() - Main.util.getPlayer().getCurrentProjectile().getDamage());
				System.out.println("ENEMY HEALTH: "+getHealth());
			}
		}
	}//end of collideProjectile
	
}//end of class
