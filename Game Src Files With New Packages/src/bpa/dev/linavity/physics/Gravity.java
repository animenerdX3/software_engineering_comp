package bpa.dev.linavity.physics;

import org.newdawn.slick.SlickException;

public class Gravity {
	
	private static boolean isGravity = true;
	public int gravity;

	public Gravity() throws SlickException {
		this.gravity = 3;
	}
	
	public int getGravity() {
		return gravity;
	}
	
	public void setGravity(){
		this.gravity = -3;
	}
	
public int gravity(int y){
		return falling(y);
		
	}
	
	public int falling(int y){
		if(isGravity)
			return y + this.gravity;
		return y;
	}
	
}//end of class
