package bpa.dev.linavity.entities;

import org.newdawn.slick.SlickException;

import bpa.dev.linavity.utils.Utils;

public class Player extends Mob {
	
	private final float rotateSpeed = 3; // the speed at which our player will rotate when gravity is flipped
	private boolean isFlipping; // Is the player rotating
	private boolean flipDirection; // True = up, false = down
	int flipDuration;
	
	public Player() 
			throws SlickException{
		super();
		isFlipping = false;
		flipDirection = false;
		
	}
	
	public void updatePos(boolean[] keyLog, int delta, Utils util) {
		
		/*
		 *MOVEMENT
		 * Keys
		 * W - Up (0)
		 * A - Left (1)
		 * S - Down (2)
		 * D - Right (3)
		 *  
		 * Left-Shift (4) 
		 */
		
		if(keyLog[0]) //Check For W Key
			jump(util.getGravity().getGravityPower());
		
		if(keyLog[1] && keyLog[4])  //Check For Run :  A + Left-Shift Pressed Together
			this.setX( (int) (getX() - ((200/1000.0f * delta) * 1.7)));
		else if(keyLog[1]) //Check For A Key
			this.setX((int) (getX() - 200/1000.0f * delta));
		
		if(keyLog[3] && keyLog[4])  //Check For Run :  D + Left-Shift Pressed Together
			this.setX( (int) (getX() + ((200/1000.0f * delta) * 1.7)));
		else if(keyLog[3]) //Check For D key
			this.setX((int) (getX() + 200/1000.0f * delta));
		
		// If the user presses control, reverse gravity
		if(keyLog[5]){
			isFlipping = true; // The player is currently flipping
			flipDirection = !flipDirection; // Switch the direction in which the player is flipping
			flipDuration = 0;
			util.getGravity().flipGravity();
		}
				
		if(isFlipping){
			if(flipDirection){
				this.getMobImage().rotate(rotateSpeed);
			}else{
				this.getMobImage().rotate(-rotateSpeed);
			}
			flipDuration += rotateSpeed;
		}
		
		if(flipDuration == 180){
			isFlipping = false;
		}
		
		super.updatePos(util.getGravity().getGravityPower());
		
	}
	
	// Player jumping function
	public void jump(int gravPower){
		if((gravPower / -1) > 0){
			this.setYmo(-14);
		}else{
			this.setYmo(14);//Sets Y-Momentum to 14, this makes the player fight against gravity
		}
	}
	
}//end of class
