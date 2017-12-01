package bpa.dev.linavity.physics;

import org.newdawn.slick.SlickException;

public class Gravity {
	
	public int gravityPower; // The factor by which objects in our world are moved by gravity/antigravity

	public Gravity() throws SlickException {
		this.gravityPower = 3; // The initial state of gravity
	}
	
	// Getter method for gravityPower
	public int getGravity() {
		return gravityPower;
	}
	
	// Setter method for gravityPower
	public void setGravity(int gravity){
		this.gravityPower = gravity;
	}
	
}//end of class
