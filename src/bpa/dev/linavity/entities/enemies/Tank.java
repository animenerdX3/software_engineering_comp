package bpa.dev.linavity.entities.enemies;

import java.util.Random;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import bpa.dev.linavity.Main;
import bpa.dev.linavity.entities.GravityPack;
import bpa.dev.linavity.entities.Mob;

public class Tank extends Mob{

	private boolean isDetected;
	private boolean toggleDirection;
	
	Random ran = new Random();
	
	//The least amount of distance the bomber can move
	float direction = 5;
	//The bomber's chance to move: a random number between 0f and 1f
	float chanceToMove = ran.nextFloat();
	//The distance the bomber will move
	float moving = ran.nextInt(6) + direction;
	//The counter for movement
	float counter = 0;
	
	boolean autoDirectionLeft;
	
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
		this.health = 60;
		this.damage = 0.5;
		this.canJump = false;
		this.isDetected = false;
		
		this.moveLeft = new SpriteSheet("res/sprites/"+this.mobName+"/"+this.mobName+"_left_ani.png",50,50); // declare a SpriteSheet and load it into java with its dimentions
	    this.moveLeftAni = new Animation(this.moveLeft, 450); // declare a Animation, loading the SpriteSheet and inputing the Animation Speed
	    this.moveRight = new SpriteSheet("res/sprites/"+this.mobName+"/"+mobName+"_right_ani.png",50,50); // declare a SpriteSheet and load it into java with its dimentions
	    this.moveRightAni = new Animation(this.moveRight, 450); // declare a Animation, loading the SpriteSheet and inputing the Animation Speed
	    this.standStillLeft = new SpriteSheet("res/sprites/"+this.mobName+"/"+this.mobName+"_1.png",50,50); // declare a SpriteSheet and load it into java with its dimentions
	    this.standStillLeftAni = new Animation(this.standStillLeft, 450); // declare a SpriteSheet and load it into java with its dimensions
	    this.standStillRight = new SpriteSheet("res/sprites/"+this.mobName+"/"+this.mobName+"_0.png",50,50); // declare a SpriteSheet and load it into java with its dimentions
	    this.standStillRightAni = new Animation(this.standStillRight, 450); // declare a SpriteSheet and load it into java with its dimensions
	    this.currentImage = this.standStillRightAni;
	} 
	
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
	
		/**
		 * 
		 * @return movement for the enemy when they cannot detect the player
		 */
		public float idleMovement() {
			//Give the bomber a random chance to move
			if(chanceToMove < 0.01f) {
				//Move a specific amount of pixels
				if(counter < moving) {
					//If true, move to the left
					if(autoDirectionLeft) {
						toggleDirection = true;
						this.currentImage = this.moveLeftAni;
						return AILeft();
					}
					//Move to the right
					else {
						toggleDirection = false;
						this.currentImage = this.moveRightAni;
						return AIRight();
					}		
				}
				else {
					counter = 0;
					moving = ran.nextInt(6) + direction;
					autoDirectionLeft = !autoDirectionLeft;
					chanceToMove = ran.nextFloat();
					return 0;
				}
			}
			else {
				chanceToMove = ran.nextFloat();
				return 0;
			}
		}//end of idleMovement
		
		public float AILeft() {
			counter = counter + this.walkSpeed;
			return -this.walkSpeed * this.accessDelta;
		}
		
		public float AIRight() {
			counter = counter + this.walkSpeed;
			return this.walkSpeed * this.accessDelta;
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
		
		return idleMovement();
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
	}//end of yMovement
	
	// Determine whether the enemy has jumped more than it's max
	private boolean maxJumps() {
		if(jumps < maxJumps)
			return false;
		else
			return true;
	}
	
	// Tells the tank that they cannot jump
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
		
		if(this.collidePlayer) {
			dealDamage();
		}
		
		super.updateMob(delta);
		
	}//end of moveEnemy
	
	/**
	 * deals damage to the player
	 */
	public void dealDamage() {
		
		System.out.println("Damage Taken");
		Main.util.getPlayer().setHealth(Main.util.getPlayer().getHealth() - getDamage());
		System.out.println("HEALTH: "+ Main.util.getPlayer().getHealth());
		
	}//end of dealDamage
	
	/**
	 * Checks to see if the tank has been hit with a projectile
	 */
	public void collideProjectile(){
		
		if(Main.util.getPlayer().getCurrentProjectile().getX() >= getX() && Main.util.getPlayer().getCurrentProjectile().getX() <= (getX() + getWidth())) {
			if(Main.util.getPlayer().getCurrentProjectile().getY() >= getY() - (getHeight() / 2)) {
				if(Main.util.getPlayer().getCurrentProjectile().getY() <= getY() + getHeight()){
					System.out.println("ENEMY HIT");
					setHealth(getHealth() - Main.util.getPlayer().getCurrentProjectile().getDamage());
					System.out.println("ENEMY HEALTH: "+getHealth());
					Main.util.getPlayer().setCurrentProjectile(null);
					Main.util.getPlayer().setProjectileExists(false);
				}
			}
		}
	}
	
	/* GETTERS */
	
	/**
	 * 
	 * @return if true, the player is detected. if false, the player is not detected
	 */
	public boolean isDetected() {
		return isDetected;
	}
	
	/* SETTERS */
	
	/**
	 * changes the bomber's detection
	 * @param isDetected
	 */
	public void setDetection(boolean isDetected) {
		this.isDetected = isDetected;
	}
	
	/**
	 * overwrites toString method for saving purposes
	 */
	public String toString() {
		return "Tank,"+this.x+","+this.y+","+this.health;
	}
	
}//end of class