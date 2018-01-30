package bpa.dev.linavity.entities;

import java.awt.Rectangle;
import java.util.ArrayList;

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
	    this.standStill = new SpriteSheet("res/sprites/"+this.mobName+"/"+this.mobName+"_0.png",50,50); // declare a SpriteSheet and load it into java with its dimensions
	    this.standStillAni = new Animation(this.standStill, 450); // declare a Animation, loading the SpriteSheet and inputing the Animation Speed
	    this.moveLeftFlipped = new SpriteSheet("res/sprites/"+this.mobName+"/"+this.mobName+"_left_flip_ani.png",50,50);// declare a SpriteSheet and load it into java with its dimensions
	    this.moveLeftFlippedAni = new Animation(this.moveLeftFlipped, 450); // declare a Animation, loading the SpriteSheet and inputing the Animation Speed
	    this.moveRightFlipped = new SpriteSheet("res/sprites/"+this.mobName+"/"+this.mobName+"_right_flip_ani.png",50,50);// declare a SpriteSheet and load it into java with its dimensions
	    this.moveRightFlippedAni = new Animation(this.moveRightFlipped, 450); // declare a Animation, loading the SpriteSheet and inputing the Animation Speed
	    this.standStillFlipped = new SpriteSheet("res/sprites/"+this.mobName+"/"+this.mobName+"_0_flip.png",50,50);// declare a SpriteSheet and load it into java with its dimensions
	    this.standStillFlippedAni = new Animation(this.standStillFlipped, 450); // declare a Animation, loading the SpriteSheet and inputing the Animation Speed
	    this.currentImage = this.standStillAni;
	}
	
	@Override
	public void onMessage(Message message){
		
		// All Messages / Events for the player are handled here
		
		// ID 0: Recharge Gravity Pack
		// ID 1: Toggle Lever
		
		if(message.getType() == Message.gravPadRecharge){	
			System.err.println("RECHARGING...");
			this.jumps = 0;
			gravPack.setGravpower(gravPack.getGravpower() + (float) message.getData());
		}
		if(message.getType() == Message.leverToggle){	
			
			Main.util.testLever.setID((int) message.getData());
			Main.util.testLever.setToggle(!Main.util.testLever.getToggle());
			Main.util.testLever.callFunction();
			if(Main.util.testLever.getToggle()) {
				try {
					Tile change = (Tile) message.getFrom();
					change.setTexture(new Image("res/tiles/static/Lever_On.png"));
				} catch (SlickException e) {
					e.printStackTrace();
				}
			}
			else {
				try {
					Tile change = (Tile) message.getFrom();
					change.setTexture(new Image("res/tiles/static/Lever_Off.png"));
				} catch (SlickException e) {
					e.printStackTrace();
				}
			}
			
		}
		
		if(message.getType() == Message.endLevel) {
			this.readyForNextLevel = true;
		}
		
	}
	
	// PLAYER MOMENTUM CONTROL // 
	
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
	}
	
	/*
	 * Return how the player is moving left or right based on input from the user
	 */
	private float xMovement() {

		if(movingLeftOrRight()) {
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
	}
	
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
			else {
				this.setIsMovingLeft(false);
				this.setIsMovingRight(false);
			}
		}else if(Main.util.getKeyLogSpecificKey(3)) { // Moving right
			if(!(Main.util.getKeyLogSpecificKey(1) && Main.util.getKeyLogSpecificKey(3))) {
			this.setIsMovingRight(true);
			return this.walkSpeed * this.accessDelta;
			}
			else {
				this.setIsMovingLeft(false);
				this.setIsMovingRight(false);
			}
		}
		
		return 0;
	}
	
	
	/*
	 * Return how the player is moving left or right based on input from the user
	 */
	private float yMovement() {
		
		// Gravity Pack Control
		if(this.gravPack.isCanFlip()) { // If the player's gravity pack is currently able to fight gravity
			if(isUsingGravPack()) // And the player is trying to use their gravity pack
				this.isFlipping = !this.isFlipping;
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
		
		// Jumping
		
		int jumpMod = 1;
		if(this.isFlipping) {
			jumpMod = -1;
			if(this.collideUp)
				this.jumps = -2;
		}

		if(!maxJumps()) {
			if(jumping()) {
				this.jumps++;
				this.jumpMomentum = jumpPower;
			}
		}
		
		if(this.jumpMomentum < 0){
			this.jumpMomentum += .7;
			if(this.jumpMomentum > 0)
				this.jumpMomentum = 0;
		}
		
		if(this.collideDown)
			this.jumps = -1;
		// End of Jumping
		
		return (jumpMod * this.jumpMomentum) + this.gravPackMomentum;
	}
	
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
	
	// END OF PLAYER MOMENTUM CONTROL //
	
	
	// Using keyLog from Utils now, movement may break for a bit
	@Override
	public void update(int delta) {
		
		/*
		 *MOVEMENT
		 * Keys
		 * Space - Jump (0)
		 * A - Left (1)
		 * S - Down (2)
		 * D - Right (3)
		 *  
		 * Left-Shift (4) 
		 * 
		 * Z - Shoot (7) 
		 */
		
		this.gravPack.gravPowerCheck();
		
		checkFlipAnimation();
		
		updateMomentums();
		
		shootProjectile();
		
		super.updateMob(delta);
		
//		if(Main.util.getGravity().getFlipDirection()){ // Reverse Gravity
//			gravPack.depletingGravPack();
//			if(this.isCu())
//				setCanJump(true);
//		}else{ // Regular Gravity
//			if(this.isCd())
//				setCanJump(true);
//		}
//		
//		
//		
//		if(Main.util.getKeyLogSpecificKey(0)) { //Check For Jump Key
//			
//			//By default, the player can only jump once
//			//If the current jump is less or equal to the number of allowed jumps, jump
//			if(jumps <= jumpNum && canJump()){
//				this.isMovingLeft = false;
//				this.isMovingRight = false;
//				System.out.println("DOUBLE JUMPING");
//				jump(Main.util.getGravity().getGravityPower());
//				jumps++;
//			}
//			//If not falling, the player can jump
//			else if(canJump()){
//				this.isMovingLeft = false;
//				this.isMovingRight = false;
//				System.out.println("NOT FALL JUMPING");
//				jump(Main.util.getGravity().getGravityPower());
//				jumps = 1;
//			}
//		}
//			
//		/**
//		 * if left or right
//		 * 		if run
//		 * 			move (5 or -5) ... I am running
//		 * 		else 
//		 * 			move (2 or -2) ... I am walking 
//		 */
//
//		
//			if(Main.util.getKeyLogSpecificKey(1) && Main.util.getKeyLogSpecificKey(4)) {  //Check For Run Left:  A + Left-Shift Pressed Together
//				this.setX( (int) getX() - 5);
//				this.isMovingLeft = true;
//				this.isMovingRight = false;
//				try {
//					if(Main.util.getGravity().getFlipDirection()) {
//						this.setMobImage(new Image("res/sprites/player/player_0.png"));
//						this.getMobImage().rotate(flipDuration);
//					}
//					else {
//						this.setMobImage(new Image("res/sprites/player/player_1.png"));
//					}
//				} catch (SlickException e) {
//					e.printStackTrace();
//				}
//			}
//			else if(Main.util.getKeyLogSpecificKey(1)) { //Check For A Key
//				this.setX((int) getX() - 2);
//				this.isMovingLeft = true;
//				this.isMovingRight = false;
//				try {
//					if(Main.util.getGravity().getFlipDirection()) {
//						this.setMobImage(new Image("res/sprites/player/player_0.png"));
//						this.getMobImage().rotate(flipDuration);
//					}
//					else {
//						this.setMobImage(new Image("res/sprites/player/player_1.png"));
//					}
//				} catch (SlickException e) {
//					e.printStackTrace();
//				}
//			}
//			
//			if(Main.util.getKeyLogSpecificKey(3) && Main.util.getKeyLogSpecificKey(4)) {  //Check For Run Right:  D + Left-Shift Pressed Together
//				this.setX( (int) getX() + 5);
//				this.isMovingRight = true;
//				this.isMovingLeft = false;
//				try {
//					if(Main.util.getGravity().getFlipDirection()) {
//						this.setMobImage(new Image("res/sprites/player/player_1.png"));
//						this.getMobImage().rotate(flipDuration);
//					}
//					else {
//						this.setMobImage(new Image("res/sprites/player/player_0.png"));
//					}
//				} catch (SlickException e) {
//					e.printStackTrace();
//				}
//			}
//			else if(Main.util.getKeyLogSpecificKey(3)) { //Check For D key
//				this.setX((int) getX() + 2);
//				this.isMovingRight = true;
//				this.isMovingLeft = false;
//				try {
//					if(Main.util.getGravity().getFlipDirection()) {
//						this.setMobImage(new Image("res/sprites/player/player_1.png"));
//						this.getMobImage().rotate(flipDuration);
//					}
//					else {
//						this.setMobImage(new Image("res/sprites/player/player_0.png"));
//					}
//				} catch (SlickException e) {
//					e.printStackTrace();
//				}
//			}
//			
//		// If the user presses control, reverse gravity
//		if(! isFlipping ){
//			if(Main.util.getKeyLogSpecificKey(5) && this.gravPack.getGravpower() > 0){
//				isFlipping = true; // The player is currently flipping
//				Main.util.getGravity().setFlipDirection(!Main.util.getGravity().getFlipDirection()); // Switch the direction in which the player is flipping
//				flipDuration = 0;
//				Main.util.getGravity().flipGravity();
//			}
//		}
//		
//		flipAnimation();
//		
//		if(this.gravPack.getGravpower() < 0){
//			Main.util.getGravity().setFlipDirection(false); // Switch the direction in which the player is flipping
//			isFlipping = true;
//			flipDuration = 0;
//			Main.util.getGravity().flipGravity();
//			this.gravPack.setGravpower(0);
//		}
//		
//		if(Main.util.getGravity().getFlipDirection()){ // Reverse Gravity
//				super.updatePlayer(Main.util.getGravity().getGravityPower());
//		}else{ // Regular Gravity
//				super.updatePlayer(Main.util.getGravity().getGravityPower());
//		}
//		
//		//Projectile Functions
//		if(Main.util.getKeyLogSpecificKey(7)) {
//			if(!projectileExists) {//If projectile does not exist
//				projectileExists = true;
//				try {
//					currentProjectile = new Projectile();//Draw default projectile
//				} catch (SlickException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		if(projectileExists) {//If projectile exists, update the position
//			currentProjectile.updatePos();
//			if(currentProjectile.getX() > Main.util.getPlayer().getX() + 500) {//If projectile is 500 pixels away from player
//				currentProjectile = null;//Destroy object
//				projectileExists = false;//Projectile does not exist
//			}
//		}
//		
//		gravPack.gravPowerCheck();
//		
	}//end of update
	
	public void checkFlipAnimation() {
		  //If Moving Left Upside Down
	      if (Main.util.getKeyLogSpecificKey(1)) {
	    	  if(isFlipping())
	    		  setCurrentImage(getMoveLeftFlippedAni());
	    	  else
	            setCurrentImage(getLeftAni());
	        }
	      
	      //If Moving Right Upside Down
	      else if (Main.util.getKeyLogSpecificKey(3)) {

	    	  if(Main.util.getPlayer().isFlipping())
	    		  setCurrentImage(getMoveRightFlippedAni()); 
	    	  else
	    	  	setCurrentImage(getRightAni());
	      }
	      
	      //If Still Upside Down
	      else{
	        	if(Main.util.getPlayer().isFlipping())
	        		setCurrentImage(getStandStillFlippedAni());
	        	else
	        	setCurrentImage(getStillAni());
	        }
	}//end of checkFlipAnimation
	
	public void shootProjectile() {
		//Projectile Functions
		if(Main.util.getKeyLogSpecificKey(7)) {
			if(!projectileExists) {//If projectile does not exist
				projectileExists = true;				
				try {
					if(Main.util.getPlayer().isMovingLeft) {
						Main.util.getSFX(1).play(1f, Main.util.getSoundManager().getVolume());
						currentProjectile = new Projectile(true, false);//Draw default projectile
					}
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
	}
	
	public void flipAnimation(){
/*		if(isFlipping){
			if(Main.util.getGravity().getFlipDirection()){
				this.getMobImage().rotate(rotateSpeed);
			}else{
				this.getMobImage().rotate(-rotateSpeed);
			}
			flipDuration += rotateSpeed;
		}*/
		
	}
	
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
	
	public boolean isProjectileExists() {
		return projectileExists;
	}

	public Projectile getCurrentProjectile() {
		return currentProjectile;
	}

	public boolean isFlipping(){
		return isFlipping;
	}
	
	public GravityPack getGravPack(){
		return gravPack;
	}
	
	public boolean isReadyForNextLevel() {
		return readyForNextLevel;
	}
	
	/* SETTERS */
	
	public void setProjectileExists(boolean projectileExists) {
		this.projectileExists = projectileExists;
	}

	public void setCurrentProjectile(Projectile currentProjectile) {
		this.currentProjectile = currentProjectile;
	}

	public void setIsFlipping(boolean isFlipping){
		this.isFlipping = isFlipping;
	}
	
	public void setGravPack(GravityPack gravPack){
		this.gravPack = gravPack;
	}
	
	public void setReadyForNextLevel (boolean readyForNextLevel) {
		this.readyForNextLevel = readyForNextLevel;
	}
	
	public String toString() {
		return "Player,"+this.x+","+this.y+","+this.health+","+this.gravPack.toString()+","+this.isFlipping;
	}
	
}//end of class
