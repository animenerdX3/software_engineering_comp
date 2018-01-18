package bpa.dev.linavity.entities;

import java.awt.Rectangle;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import bpa.dev.linavity.Main;
import bpa.dev.linavity.events.*;
import bpa.dev.linavity.weapons.*;

public class Player extends Mob {
	
	private final float rotateSpeed = 9; // the speed at which our player will rotate when gravity is flipped
	private boolean isFlipping; // Is the player rotating
	int flipDuration;
	int jumps;
	private boolean projectileExists = false;
	private Projectile currentProjectile;
	
	private GravityPack gravPack;
	
	public Player(float x, float y) throws SlickException{
		
		// Call Mob Constructor
		super();
		
		// Player Signature
		this.x = x;
		this.y = y;
		this.mobImage = new Image("res/sprites/player/player_0.png");
		this.width = this.mobImage.getWidth() - 2;
		this.height = this.mobImage.getHeight() - 2;
		this.gravPack = new GravityPack(100);
		this.maxJumps = 1;
		this.walkSpeed = 0.125f;
		this.runSpeed = 0.25f;
		this.health = 100;
		this.damage = 0;
		this.jumpPower = -14;
		this.canJump = true;
		this.boundingBox = new Rectangle((int) this.x, (int) this.y, (int) this.width, (int) this.height);

		this.isFlipping = false;
	}
	
	@Override
	public void onMessage(Message message){
		
		// All Messages / Events for the player are handled here
		
		// ID 0: Recharge Gravity Pack
		if(message.getType() == Message.thing){				
			gravPack.setGravpower(gravPack.getGravpower() + (int) message.getData());
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
			this.jumps = 0;
		
		return jumpMomentum;
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
		
		updateMomentums();
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
	}//end of updatePos
	
	public void flipAnimation(){
		if(isFlipping){
			if(Main.util.getGravity().getFlipDirection()){
				this.getMobImage().rotate(rotateSpeed);
			}else{
				this.getMobImage().rotate(-rotateSpeed);
			}
			flipDuration += rotateSpeed;
		}
		if(flipDuration >= 180){
			isFlipping = false;
		}
		
	}
	
	// Player jumping function
	public void jump(int gravPower){
		
		//If the player is not falling, then you can jump		
		int power = 16;

		if(Main.util.getGravity().getFlipDirection()){ // Reverse Gravity
				if((gravPower / -1) > 0){
					this.setYmo(-power);
					setCanJump(false);
				}else{
					this.setYmo(power);//Sets Y-Momentum to 14, this makes the player fight against gravity
					setCanJump(false);
				}
			
		}else{ // Regular Gravity
				if((gravPower / -1) > 0){
					this.setYmo(-power);
					setCanJump(false);
				}else{
					this.setYmo(power);//Sets Y-Momentum to 14, this makes the player fight against gravity'
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
	
}//end of class
