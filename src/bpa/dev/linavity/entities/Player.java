package bpa.dev.linavity.entities;

import org.newdawn.slick.SlickException;

public class Player extends Mob {
	
	public Player() 
			throws SlickException{
		super();
	}
	
	public void updatePos(boolean[] keyLog, int delta) {
		
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
			jump();
		
		if(keyLog[1] && keyLog[4])  //Check For Run :  A + Left-Shift Pressed Together
			this.setX( (int) (getX() - ((200/1000.0f * delta) * 1.7)));
		else if(keyLog[1]) //Check For A Key
			this.setX((int) (getX() - 200/1000.0f * delta));
		
		if(keyLog[3] && keyLog[4])  //Check For Run :  D + Left-Shift Pressed Together
			this.setX( (int) (getX() + ((200/1000.0f * delta) * 1.7)));
		else if(keyLog[3]) //Check For D key
			this.setX((int) (getX() + 200/1000.0f * delta));
				
		super.updatePos();
		
	}
	
	// Player jumping function
	public void jump(){
		System.out.println(this.getYmo());
		if(this.getYmo() <=0) // New Commentsadf
			this.setYmo(14);//Sets Y-Momentum to 14, this makes the player fight against gravity
	}
	
}//end of class
