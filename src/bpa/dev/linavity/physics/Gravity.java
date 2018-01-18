package bpa.dev.linavity.physics;

import org.newdawn.slick.SlickException;

public class Gravity {
	
	private boolean flipDirection; // True = up, false = down
	private int gravityPower; // The factor by which objects in our world are moved by gravity/antigravity
	
	public Gravity() throws SlickException {
		this.gravityPower = 4; // The initial state of gravity
		this.flipDirection = false;
	}
	
	/* GETTERS */
	
	public int getGravityPower() {
		return gravityPower;
	}
	
	public boolean getFlipDirection() {
		 return flipDirection;
	}
	
	/* SETTERS */
	
	public void setGravity(int gravity){
		this.gravityPower = gravity;
	}
	
	public void setFlipDirection(boolean flipGravity){
			this.flipDirection = flipGravity;
	}
	
	/**
	 *  Used to flip gravity
	 */
	public void flipGravity(){
		if(flipDirection) {
			setGravity(-1);
		}
		else
			setGravity(1);
	}
	
}//end of class
