package bpa.dev.linavity.physics;

import org.newdawn.slick.SlickException;

public class Gravity {
	
	public int gravityPower; // The factor by which objects in our world are moved by gravity/antigravity
	
	public Gravity() throws SlickException {
		this.gravityPower = 6; // The initial state of gravity
	}
	
	/* GETTERS */
	
	public int getGravityPower() {
		return gravityPower;
	}
	
	/* SETTERS */
	
	public void setGravity(int gravity){
		this.gravityPower = gravity;
	}
	
	/**
	 *  Used to flip gravity
	 */
	public void flipGravity(){
		this.gravityPower = -this.gravityPower;
	}
	
}//end of class
