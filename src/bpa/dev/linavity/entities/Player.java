package bpa.dev.linavity.entities;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import bpa.dev.linavity.GameObject;
import bpa.dev.linavity.Main;
import bpa.dev.linavity.entities.tiles.Tile;
import bpa.dev.linavity.events.Message;
import bpa.dev.linavity.utils.Utils;
import bpa.dev.linavity.weapons.Projectile;

public class Player extends Mob {
	
	private final float rotateSpeed = 9; // the speed at which our player will rotate when gravity is flipped
	private boolean isFlipping; // Is the player rotating
	int flipDuration;
	int jumps;
	private boolean projectileExists = false;
	private Projectile currentProjectile;
	
	private GravityPack gravPack;
	
	public Player(Utils util) throws SlickException{
		super(util);
		this.jumps = 0;
		this.isFlipping = false;
		this.gravPack = new GravityPack(100);
	}
	
	@Override
	public void onMessage(Message message){
		
		// All Messages / Events for the player are handled here
		
		// ID 0: Recharge Gravity Pack
		if(message.getType() == Message.id){		
		
			gravPack.setGravpower(gravPack.getGravpower() + (int) message.getData());


			Main.util.getMessageHandler().addMessage(new Message( (Tile)message.getFrom(), this, 1, 1 )  ) ;
		}
		
	}
	
	public void updatePos(boolean[] keyLog, int delta, Utils util) {
		
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
		
		if(getGravity().getFlipDirection()){ // Reverse Gravity
			gravPack.depletingGravPack(util);
			if(this.isCu())
				setCanJump(true);
		}else{ // Regular Gravity
			if(this.isCd())
				setCanJump(true);
		}
		
		
		
		if(keyLog[0]) { //Check For Jump Key
			
			//By default, the player can only jump once
			//If the current jump is less or equal to the number of allowed jumps, jump
			if(jumps <= jumpNum && canJump()){
				this.isMovingLeft = false;
				this.isMovingRight = false;
				System.out.println("DOUBLE JUMPING");
				jump(getGravity().getGravityPower());
				jumps++;
			}
			//If not falling, the player can jump
			else if(canJump()){
				this.isMovingLeft = false;
				this.isMovingRight = false;
				System.out.println("NOT FALL JUMPING");
				jump(getGravity().getGravityPower());
				jumps = 1;
			}
		}
			
		/**
		 * if left or right
		 * 		if run
		 * 			move (5 or -5) ... I am running
		 * 		else 
		 * 			move (2 or -2) ... I am walking 
		 */

		
			if(keyLog[1] && keyLog[4]) {  //Check For Run Left:  A + Left-Shift Pressed Together
				this.setX( (int) getX() - 5);
				this.isMovingLeft = true;
				this.isMovingRight = false;
			}
			else if(keyLog[1]) { //Check For A Key
				this.setX((int) getX() - 2);
				this.isMovingLeft = true;
				this.isMovingRight = false;
			}
			
			if(keyLog[3] && keyLog[4]) {  //Check For Run Right:  D + Left-Shift Pressed Together
				this.setX( (int) getX() + 5);
				this.isMovingRight = true;
				this.isMovingLeft = false;
			}
			else if(keyLog[3]) { //Check For D key
				this.setX((int) getX() + 2);
				this.isMovingRight = true;
				this.isMovingLeft = false;
			}
			
		// If the user presses control, reverse gravity
		if(! isFlipping ){
			if(keyLog[5] && this.gravPack.getGravpower() > 0){
				isFlipping = true; // The player is currently flipping
				getGravity().setFlipDirection(!getGravity().getFlipDirection()); // Switch the direction in which the player is flipping
				flipDuration = 0;
				getGravity().flipGravity();
			}
		}
		
		flipAnimation();
		
		if(this.gravPack.getGravpower() < 0){
			getGravity().setFlipDirection(false); // Switch the direction in which the player is flipping
			isFlipping = true;
			flipDuration = 0;
			getGravity().flipGravity();
			this.gravPack.setGravpower(0);
		}
		
		if(getGravity().getFlipDirection()){ // Reverse Gravity
				super.updatePos(util.getGravity().getGravityPower());
		}else{ // Regular Gravity
				super.updatePos(util.getGravity().getGravityPower());
		}
		
		//Projectile Functions
		if(keyLog[7]) {
			if(!projectileExists) {//If projectile does not exist
				projectileExists = true;
				try {
					currentProjectile = new Projectile(util);//Draw default projectile
				} catch (SlickException e) {
					e.printStackTrace();
				}
			}
		}
		if(projectileExists) {//If projectile exists, update the position
			currentProjectile.updatePos();
			//TODO Make if-statement more practical
			if(currentProjectile.getX() > util.getPlayer().getX() + 500) {//If projectile is 500 pixels away from player
				currentProjectile = null;//Destroy object
				projectileExists = false;//Projectile does not exist
			}
		}
		
		gravPack.gravPowerCheck();
		
	}//end of updatePos
	
	public void flipAnimation(){
		if(isFlipping){
			if(getGravity().getFlipDirection()){
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
		
				if(getGravity().getFlipDirection()){ // Reverse Gravity
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
