package bpa.dev.linavity.entities;


import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import bpa.dev.linavity.Main;
import bpa.dev.linavity.entities.GravityPack;
import bpa.dev.linavity.entities.Mob;

public class Abric extends Mob{
	
	//Create enemy with constructor
	public Abric(float x, float y) throws SlickException{
		
		// Call Mob Constructor
		super();
		
		// Enemy Signature
		this.x = x;
		this.y = y;
		this.mobName = "abric";
		this.width = 40;
		this.height = 48;
		this.gravPack = new GravityPack();
		this.maxJumps = 0;
		this.walkSpeed = 0.0625f;
		this.runSpeed = 0.125f;
		this.health = 30;
		this.damage = 0.25;
		this.canJump = false;
		
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
	
	
	// ENEMY MOMENTUM CONTROL // 
	
	/*
	 * Update the enemy's x and y momentum
	 */
	private void updateMomentums() {
		this.xMomentum = determineXMomentum();
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
	 * Return how the enemy is moving left or right based on input from the user
	 */
	private float xMovement() {

		if(Main.util.cutsceneVars.isMoveAbric()) {//Find out when to move
			return walkingX();
		}
		
		return 0;
	}//end of xMovement
	
	
	/*
	 * The enemy is walking, return the value for a specific walking direction
	 */
	private float walkingX() {

			if(Main.util.cutsceneVars.isAbricDirection()) { // Moving left
				this.currentImage = this.moveLeftAni;
				return -this.walkSpeed * this.accessDelta;
			}
			else { // Moving right
				this.currentImage = this.moveRightAni;
				return this.walkSpeed * this.accessDelta;
			}
	
	}//end of walkingX
	
	/**
	 * Enemy movement AI
	 * @param delta
	 * @throws SlickException 
	 */
	@Override
	public void update(int delta) throws SlickException {
		
		checkDirection();
		
		updateMomentums();
		
		super.updateMob(delta);
		
	}//end of update
	
	
	public void checkDirection() {
		if(Main.util.cutsceneVars.isAbricDirection())
			this.currentImage = this.standStillLeftAni;
		else
			this.currentImage = this.standStillRightAni;
	}
	
	/* GETTERS */

	
	/**
	 * overwrites the toString class for saving purposes
	 */
	public String toString() {
		//Save the class name, the x position, the y position, and the health
		return "Starter,"+this.x+","+this.y+","+this.health;
	}
	
}//end of class
