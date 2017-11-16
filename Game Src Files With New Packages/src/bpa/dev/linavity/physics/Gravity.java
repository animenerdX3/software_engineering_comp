package bpa.dev.linavity.physics;

import org.newdawn.slick.SlickException;


public class Gravity {

	public Gravity() throws SlickException {
		
	}

	private static boolean isGravity = true;
	public static int gravity = 3;
	
	
	public static void setGravity(){
		gravity = -gravity;
	}
	
/*	public int gravity(int y){
		return falling(y);
		
	}
	
	public int falling(int y){
		return y + fall;
	}*/
	
}//end of class