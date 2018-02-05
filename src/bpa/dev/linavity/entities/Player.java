package bpa.dev.linavity.entities;

import java.awt.Rectangle;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import bpa.dev.linavity.Main;
import bpa.dev.linavity.entities.tiles.Tile;
import bpa.dev.linavity.events.Message;
import bpa.dev.linavity.weapons.Projectile;

public class Player extends Mob {
	
	private final float rotateSpeed = 9; // the speed at which our player will rotate when gravity is flipped
	private boolean isFlipping; // Is the player rotating
	int flipDuration;
	int jumps;
	private boolean projectileExists = false;
	private Projectile currentProjectile;
	private boolean readyForNextLevel;
	private boolean toggleDirection = false;
	private boolean inventoryOpen;
	private int coolDown;
	private boolean canUseGravpack;
	
	private GravityPack gravPack;
	
	public Player(float x, float y) throws SlickException{
		
		// Call Mob Constructor
		super();
		
		// Player Signature
		this.x = x;
		this.y = y;
		this.mobName = "player";
		this.width = 48;
		this.height = 48;
		this.gravPack = new GravityPack(100);
		this.maxJumps = 0;
		this.walkSpeed = 0.125f;
		this.runSpeed = 0.25f;
		this.health = 100;
		this.damage = 0;
		this.jumpPower = -14;
		this.canJump = true;
		this.boundingBox = new Rectangle((int) this.x, (int) this.y, (int) this.width, (int) this.height);
		this.isFlipping = false;
		this.readyForNextLevel = false;
		
		this.moveLeft = new SpriteSheet("res/sprites/"+this.mobName+"/"+this.mobName+"_left_ani.png",50,50); // declare a SpriteSheet and load it into java with its dimensions
	    this.moveLeftAni = new Animation(this.moveLeft, 450); // declare a Animation, loading the SpriteSheet and inputing the Animation Speed
	    this.moveRight = new SpriteSheet("res/sprites/"+this.mobName+"/"+mobName+"_right_ani.png",50,50); // declare a SpriteSheet and load it into java with its dimensions
	    this.moveRightAni = new Animation(this.moveRight, 450); // declare a Animation, loading the SpriteSheet and inputing the Animation Speed
	    this.standStillLeft = new SpriteSheet("res/sprites/"+this.mobName+"/"+this.mobName+"_1.png",50,50); // declare a SpriteSheet and load it into java with its dimensions
	    this.standStillLeftAni = new Animation(this.standStillLeft, 450); // declare a Animation, loading the SpriteSheet and inputing the Animation Speed
	    this.standStillRight = new SpriteSheet("res/sprites/"+this.mobName+"/"+this.mobName+"_0.png",50,50); // declare a SpriteSheet and load it into java with its dimensions
	    this.standStillRightAni = new Animation(this.standStillRight, 450); // declare a Animation, loading the SpriteSheet and inputing the Animation Speed
	    this.moveLeftFlipped = new SpriteSheet("res/sprites/"+this.mobName+"/"+this.mobName+"_left_flip_ani.png",50,50);// declare a SpriteSheet and load it into java with its dimensions
	    this.moveLeftFlippedAni = new Animation(this.moveLeftFlipped, 450); // declare a Animation, loading the SpriteSheet and inputing the Animation Speed
	    this.moveRightFlipped = new SpriteSheet("res/sprites/"+this.mobName+"/"+this.mobName+"_right_flip_ani.png",50,50);// declare a SpriteSheet and load it into java with its dimensions
	    this.moveRightFlippedAni = new Animation(this.moveRightFlipped, 450); // declare a Animation, loading the SpriteSheet and inputing the Animation Speed
	    this.standStillLeftFlipped = new SpriteSheet("res/sprites/"+this.mobName+"/"+this.mobName+"_0_flip.png",50,50);// declare a SpriteSheet and load it into java with its dimensions
	    this.standStillLeftFlippedAni = new Animation(this.standStillLeftFlipped, 450); // declare a Animation, loading the SpriteSheet and inputing the Animation Speed
	    this.standStillRightFlipped = new SpriteSheet("res/sprites/"+this.mobName+"/"+this.mobName+"_1_flip.png",50,50);// declare a SpriteSheet and load it into java with its dimensions
	    this.standStillRightFlippedAni = new Animation(this.standStillRightFlipped, 450); // declare a Animation, loading the SpriteSheet and inputing the Animation Speed
	    this.currentImage = this.standStillRightAni;
	    this.currentStillImage = this.standStillRightAni;

	    if(Main.util.levelNum != 1)
	    	this.canUseGravpack = true;
	    else
	    	this.canUseGravpack = false;
	    
	    this.inventoryOpen = false;
	}
	
	/**
	 * All Messages / Events for the player are handled here
	 */
	@Override
	public void onMessage(Message message){
		
		// ID 0: Recharge Gravity Pack
		// ID 1: Toggle Lever
		
		if(message.getType() == Message.gravPadRecharge){	
			System.err.println("RECHARGING...");
			this.jumps = 0;
			gravPack.setGravpower(gravPack.getGravpower() + (float) message.getData());
		}
		
		if(message.getType() == Message.damage){
			this.health += (double)message.getData();
		}
		
		if(message.getType() == Message.endLevel) {
			this.readyForNextLevel = true;
		}
		
	}
	
	/*
	 * Update the player's x and y momentum
	 */
	private void updateMomentums() {
		this.xMomentum = determineXMomentum();
		this.yMomentum = determineYMomentum();
	}
	
	/*
	 * Update the player's x momentum
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
	}//end of determineYMomentum
	
	/*
	 * Return how the player is moving left or right based on input from the user
	 */
	private float xMovement() {

		if(movingLeftOrRight() && !this.inventoryOpen) {
			if(isRunning()) {
				return runningX();
			}else{
				return walkingX();
			}
		}
		else {
			this.setIsMovingLeft(false);
			this.setIsMovingRight(false);
		}
		
		return 0;
	}//end of xMovement
	
	// Determines if the player is moving in the X direction
	private boolean movingLeftOrRight() {
		return Main.util.getKeyLogSpecificKey(1) || Main.util.getKeyLogSpecificKey(3);
	}
	
	// Determines if the player is running
	private boolean isRunning() {
		return Main.util.getKeyLogSpecificKey(4);
	}
	
	/*
	 * The player is running, return the value for a specific running direction
	 */
	private float runningX() {
		
		if(Main.util.getKeyLogSpecificKey(1)){ // Moving left
			this.setIsMovingLeft(true);
			return -this.runSpeed * this.accessDelta;
		}else if(Main.util.getKeyLogSpecificKey(3)) { // Moving right
			this.setIsMovingRight(true);
			return this.runSpeed * this.accessDelta;
		}
		
		return 0;
	}
	
	/*
	 * The player is walking, return the value for a specific walking direction
	 */
	private float walkingX() {
		
		if(Main.util.getKeyLogSpecificKey(1)){ // Moving left
			if(!(Main.util.getKeyLogSpecificKey(1) && Main.util.getKeyLogSpecificKey(3))) {
				this.setIsMovingLeft(true);
				return -this.walkSpeed * this.accessDelta;
			}
			else {//Don't Move
				this.setIsMovingLeft(false);
				this.setIsMovingRight(false);
			}
		}else if(Main.util.getKeyLogSpecificKey(3)) { // Moving right
			if(!(Main.util.getKeyLogSpecificKey(1) && Main.util.getKeyLogSpecificKey(3))) {
			this.setIsMovingRight(true);
			return this.walkSpeed * this.accessDelta;
			}
			else {//Don't move
				this.setIsMovingLeft(false);
				this.setIsMovingRight(false);
			}
		}
		
		return 0;
	}//end of walkingX
	
	
	/*
	 * Return how the player is moving left or right based on input from the user
	 */
	private float yMovement() {
		
		// Gravity Pack Control
		if(this.gravPack.isCanFlip() && this.canUseGravpack) { // If the player's gravity pack is currently able to fight gravity
			if(isUsingGravPack() && !this.inventoryOpen) { // And the player is trying to use their gravity pack
				this.isFlipping = !this.isFlipping;
			}
		}else{
			this.isFlipping = false;
		}
		
		if(isFlipping){
			this.gravPackMomentum = Main.util.getGravity().getGravityPower() * -2;
			this.gravPack.depletingGravPack();
			
		}else{
			this.gravPackMomentum = 0;
		}
			
		// End of Gravity Pack Control
		
		
		int jumpMod = 1;
		//If the player is flipped
		if(this.isFlipping) {
			jumpMod = -1;
			//If the player is colliding up
			if(this.collideUp)
				this.jumps = -2;
		}

		//If their maximum jumps have not been reached
		if(!maxJumps()) {
			//If they are jumping
			if(jumping() && !this.inventoryOpen) {
				this.jumps++;
				this.jumpMomentum = jumpPower;
			}
		}
		
		if(this.jumpMomentum < 0){
			this.jumpMomentum += .7;
			if(this.jumpMomentum > 0)
				this.jumpMomentum = 0;
		}
		
		//The the player is colliding down
		if(this.collideDown)
			this.jumps = -1;
		
		return (jumpMod * this.jumpMomentum) + this.gravPackMomentum;
	}//end of yMovement
	
	// Determine whether the player is trying to use their gravity pack
	private boolean isUsingGravPack(){
		return Main.util.getKeyLogSpecificKey(5);
	}
	
	// Determine whether the player has jumped more than it's max
	private boolean maxJumps() {
		if(this.jumps < this.maxJumps)
			return false;
		else
			return true;
	}
	
	// Determines if the player is running
	private boolean jumping() {
		return Main.util.getKeyLogSpecificKey(0);
	}
	
	
	/**
	 * update the player's functions
	 * @throws SlickException 
	 */
	@Override
	public void update(int delta) throws SlickException {
		
		/*
		 *MOVEMENT
		 * Keys
		 * Space - Jump (0)
		 * A or Left Arrow - Left (1)
		 * S or Down Arrow - Down (2)
		 * D or Right Arrow - Right (3)
		 *  
		 * Left Shift - Run (4)
		 * Left-Control - Reverse Gravity(5) 
		 * 
		 * Enter - Shoot (7) 
		 * I - Inventory (10)
		 */
		
		this.gravPack.gravPowerCheck();
		
		checkAnimation();
		
		updateMomentums();
		
		shootProjectile(delta);
		
		super.updateMob(delta);
	
	}//end of update
	
	/**
	 * Checks the player's animation
	 */
	public void checkAnimation() {
		
		  //If Moving Left Upside Down
	      if (Main.util.getKeyLogSpecificKey(1) && !this.inventoryOpen) {
	    	  toggleDirection = true;
	    	  if(isFlipping())
	    		  setCurrentImage(getMoveLeftFlippedAni());
	    	  else
	            setCurrentImage(getLeftAni());
	        }
	      
	      //If Moving Right Upside Down
	      else if (Main.util.getKeyLogSpecificKey(3) && !this.inventoryOpen) {
	    	  toggleDirection = false;
	    	  if(Main.util.getPlayer().isFlipping())
	    		  setCurrentImage(getMoveRightFlippedAni()); 
	    	  else
	    	  	setCurrentImage(getRightAni());
	      }
	      
	      //If Still Upside Down
	      else{
	        	if(Main.util.getPlayer().isFlipping()) {
	        		if(toggleDirection)
	        			setCurrentImage(getStandStillLeftFlippedAni());
	        		else
	        			setCurrentImage(getStandStillRightFlippedAni());
	        	}
	        	else {
	        		if(toggleDirection)
		        		setCurrentImage(getStillLeftAni());
	        		else
		        		setCurrentImage(getStillRightAni());
	        	}
	        }
	}//end of checkAnimation
	
	/**
	 * handles all projectile shooting functions
	 */
	public void shootProjectile(int delta) {
		this.coolDown = this.coolDown + delta;
		//Projectile Functions
		if(Main.util.getKeyLogSpecificKey(7) && !this.inventoryOpen && this.coolDown > 500) {
			this.coolDown = 0;
			if(!projectileExists) {//If projectile does not exist
				projectileExists = true;				
				try {
					//If the player is moving left, shoot in the left direction
					if(toggleDirection) {
						Main.util.getSFX(1).play(1f, Main.util.getSoundManager().getVolume());
						currentProjectile = new Projectile(true, false);//Draw default projectile
					}
					//If the player is moving right, shoot in the right direction
					else {
						Main.util.getSFX(1).play(1f, Main.util.getSoundManager().getVolume());
						currentProjectile = new Projectile(false, true);//Draw default projectile
					}
				} catch (SlickException e) {
					e.printStackTrace();
				}
			}
		}
		if(projectileExists) {//If projectile exists, update the position
			currentProjectile.updatePos();
			if(currentProjectile.getX() > Main.util.getPlayer().getX() + 500 || currentProjectile.getX() < Main.util.getPlayer().getX() - 500) {//If projectile is 500 pixels away from player
				currentProjectile = null;//Destroy object
				projectileExists = false;//Projectile does not exist
			}
		}
	}//end of shootProjectile
	
	/**
	 * Creates an animation for when the player reverses gravity
	 */
	public void flipAnimation(){
/*		if(isFlipping){
			if(Main.util.getGravity().getFlipDirection()){
				this.getMobImage().rotate(rotateSpeed);
			}else{
				this.getMobImage().rotate(-rotateSpeed);
			}
			flipDuration += rotateSpeed;
		}*/
	}//end of flipAnimation
	
	// Player jumping function
	public void jump(int gravPower){
		
		//If the player is not falling, then you can jump		
		int power = 16;

		if(Main.util.getGravity().getFlipDirection()){ // Reverse Gravity
				if((gravPower / -1) > 0){
					this.setYMomentum(-power);
					setCanJump(false);
				}else{
					this.setYMomentum(power);//Sets Y-Momentum to 14, this makes the player fight against gravity
					setCanJump(false);
				}
			
		}else{ // Regular Gravity
				if((gravPower / -1) > 0){
					this.setYMomentum(-power);
					setCanJump(false);
				}else{
					this.setYMomentum(power);//Sets Y-Momentum to 14, this makes the player fight against gravity'
					setCanJump(false);
				}
			
		}
		

		
	}
	
	/* GETTERS */
	
	/**
	 * 
	 * @return if true, a projectile currently exists. if false, there is none
	 */
	public boolean isProjectileExists() {
		return projectileExists;
	}

	/**
	 * 
	 * @return the player's projectile
	 */
	public Projectile getCurrentProjectile() {
		return currentProjectile;
	}

	/**
	 * 
	 * @return if true, the player is flipping. if false, the player is not
	 */
	public boolean isFlipping(){
		return isFlipping;
	}
	
	/**
	 * 
	 * @return the player's gravity pack
	 */
	public GravityPack getGravPack(){
		return gravPack;
	}
	
	/**
	 * 
	 * @return if true, the player can transition to the next level. if false, the player cannot
	 */
	public boolean isReadyForNextLevel() {
		return readyForNextLevel;
	}
	
	/**
	 * 
	 * @return if true, the inventory is open. if false, the inventory is not open
	 */
	public boolean isInventoryOpen() {
		return inventoryOpen;
	}
	
	/**
	 * 
	 * @return if true, the player can use their gravpack. if falase, the player cannot
	 */
	public boolean canUseGravpack() {
		return canUseGravpack;
	}
	
	/* SETTERS */

	/**
	 * changes the projectile's existence
	 * @param projectileExists
	 */
	public void setProjectileExists(boolean projectileExists) {
		this.projectileExists = projectileExists;
	}

	/**
	 * changes the projectile
	 * @param currentProjectile
	 */
	public void setCurrentProjectile(Projectile currentProjectile) {
		this.currentProjectile = currentProjectile;
	}

	/**
	 * changes the flipping boolean
	 * @param isFlipping
	 */
	public void setIsFlipping(boolean isFlipping){
		this.isFlipping = isFlipping;
	}
	
	/**
	 * changes the player's gravity pack
	 * @param gravPack
	 */
	public void setGravPack(GravityPack gravPack){
		this.gravPack = gravPack;
	}
	
	/**
	 * changes the player's ready for next level boolean
	 * @param readyForNextLevel
	 */
	public void setReadyForNextLevel (boolean readyForNextLevel) {
		this.readyForNextLevel = readyForNextLevel;
	}
	
	/**
	 * changes the player's inventory open boolean
	 * @param inventoryOpen
	 */
	public void setInventoryOpen(boolean inventoryOpen) {
		this.inventoryOpen = inventoryOpen;
	}
	
	/**
	 * changes the player's can use gravpack boolean
	 * @param canUseGravpack
	 */
	public void setCanUseGravpack(boolean canUseGravpack) {
		this.canUseGravpack = canUseGravpack;
	}
	
	/**
	 * overwrite's the class's toString method for saving purposes
	 */
	public String toString() {
		//Save the class name, x position, y position, health, gravity pack, and flipping boolean
		return "Player,"+this.x+","+this.y+","+this.health+","+this.gravPack.toString()+","+this.isFlipping;
	}
	
}//end of class
