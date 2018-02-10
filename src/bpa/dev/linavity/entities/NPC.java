package bpa.dev.linavity.entities;

import java.awt.Rectangle;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import bpa.dev.linavity.Main;
import bpa.dev.linavity.entities.tiles.interactive.EventTile;
import bpa.dev.linavity.events.Message;

public class NPC extends Mob {
	
	private GravityPack gravPack;
	
	public NPC(float x, float y) throws SlickException{
		
		// Call Mob Constructor
		super();
		
		// Player Signature
		this.x = x;
		this.y = y;
		this.mobName = "abric";
		this.width = 48;
		this.height = 48;
		this.setGravPack(new GravityPack(100));
		this.maxJumps = 0;
		this.walkSpeed = 0.125f;
		this.runSpeed = 0.25f;
		this.health = 100;
		this.canJump = true;
		this.boundingBox = new Rectangle((int) this.x, (int) this.y, (int) this.width, (int) this.height);
		
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

	}
	
	/**
	 * All Messages / Events for the player are handled here
	 */
	@Override
	public void onMessage(Message message){
		
		// If an event tile tells this NPC to activate
		if(message.getFrom() instanceof EventTile && message.getType() == Message.activateNPC){
			
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
	 * Return how the player is moving  up or down
	 */
	private float yMovement() {

		return 0;
	}//end of yMovement
	
	/**
	 * update the player's functions
	 * @throws SlickException 
	 */
	@Override
	public void update(int delta) throws SlickException {

		
		updateMomentums();
		
		super.updateMob(delta);
	
	}//end of update
	
	/**
	 * Checks the player's animation
	 */
	public void checkAnimation() {
		
	
	}//end of checkAnimation

	/* GETTERS */
	
	public GravityPack getGravPack() {
		return gravPack;
	}

	/* SETTERS */
	public void setGravPack(GravityPack gravPack) {
		this.gravPack = gravPack;
	}
	

	
	/* GETTERS */

	
	/* SETTERS */

	
}//end of class
