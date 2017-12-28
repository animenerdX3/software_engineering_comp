package bpa.dev.linavity.entities;

import org.newdawn.slick.SlickException;

import bpa.dev.linavity.utils.Utils;
import bpa.dev.linavity.weapons.Projectile;

public class Player extends Mob {
	
	private final float rotateSpeed = 9; // the speed at which our player will rotate when gravity is flipped
	private boolean isFlipping; // Is the player rotating
	private boolean flipDirection; // True = up, false = down
	int flipDuration;
	int jumps;
	private boolean projectileExists = false;
	private Projectile currentProjectile;
	
	private int gravityPack;
	
	public Player() 
			throws SlickException{
		super();
		this.jumps = 0;
		this.isFlipping = false;
		this.flipDirection = false;
		
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
		 */
		
		
		if(flipDirection){ // Reverse Gravity
			if(this.isCu())
				this.isFalling = false;
		}else{ // Regular Gravity
			if(this.isCd())
				this.isFalling = false;
		}
		
		
		
		if(keyLog[0]) { //Check For Jump Key
			
			//By default, the player can only jump once
			//If the current jump is less or equal to the number of allowed jumps, jump
			if(jumps <= jumpNum){
				jump(util.getGravity().getGravityPower());
				jumps++;
			}
			//If not falling, the player can jump
			else if(!isFalling){
				jump(util.getGravity().getGravityPower());
				jumps = 1;
			}
		}
		
		// if there isn't a collision in the x direction
//		if(!this.isCl() || !this.isCr()){
			
			if(keyLog[1] && keyLog[4]) {  //Check For Run Left:  A + Left-Shift Pressed Together
				this.setX( (int) (getX() - ((200/1000.0f * delta) * 1.7)));
			}
			else if(keyLog[1]) { //Check For A Key
				this.setX((int) (getX() - 200/1000.0f * delta));
			}
			
			if(keyLog[3] && keyLog[4])  //Check For Run Right:  D + Left-Shift Pressed Together
				this.setX( (int) (getX() + ((200/1000.0f * delta) * 1.7)));
			
			else if(keyLog[3]) //Check For D key
				this.setX((int) (getX() + 200/1000.0f * delta));		
		
		// If the user presses control, reverse gravity
		if(isFlipping == false){
			if(keyLog[5]){
				isFlipping = true; // The player is currently flipping
				flipDirection = !flipDirection; // Switch the direction in which the player is flipping
				flipDuration = 0;
				util.getGravity().flipGravity();
			}
		}
		
				
		if(isFlipping){ //
			if(flipDirection){
				this.getMobImage().rotate(rotateSpeed);
			}else{
				this.getMobImage().rotate(-rotateSpeed);
			}
			flipDuration += rotateSpeed;
		}
		
		if(flipDuration >= 180){
			isFlipping = false;
		}
		
		if(flipDirection){ // Reverse Gravity
				super.updatePos(util.getGravity().getGravityPower());
		}else{ // Regular Gravity
				super.updatePos(util.getGravity().getGravityPower());
		}
		
		//Projectile Functions
		if(keyLog[7]) {
			if(!projectileExists) {
				projectileExists = true;
				try {
					currentProjectile = new Projectile(util);
				} catch (SlickException e) {
					e.printStackTrace();
				}
			}
		}
		if(projectileExists) {
			currentProjectile.updatePos();
			if(currentProjectile.getX() > util.getPlayer().getX() + 500) {
				currentProjectile = null;
				projectileExists = false;
			}
		}
		
	}//end of updatePos
	
	// Player jumping function
	public void jump(int gravPower){
		
		//If the player is not falling, then you can jump
		
				int power = 16;
		
				if(flipDirection){ // Reverse Gravity
						if((gravPower / -1) > 0){
							this.setYmo(-power);
							isFalling = true;
						}else{
							this.setYmo(power);//Sets Y-Momentum to 14, this makes the player fight against gravity
							isFalling = true;
						}
					
				}else{ // Regular Gravity
						if((gravPower / -1) > 0){
							this.setYmo(-power);
							isFalling = true;
						}else{
							this.setYmo(power);//Sets Y-Momentum to 14, this makes the player fight against gravity'
							isFalling = true;
						}
					
				}
		

		
	}
	
	public boolean isProjectileExists() {
		return projectileExists;
	}

	public Projectile getCurrentProjectile() {
		return currentProjectile;
	}

	public void setProjectileExists(boolean projectileExists) {
		this.projectileExists = projectileExists;
	}

	public void setCurrentProjectile(Projectile currentProjectile) {
		this.currentProjectile = currentProjectile;
	}

	//This may have important information in regards to the hitbox of the player
	public String toString() {
		return "Image: "+getMobImage();
	}
	
}//end of class
