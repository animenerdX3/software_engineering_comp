package bpa.dev.linavity.physics;

import bpa.dev.linavity.gamestates.StartLevel;

public class Gravity {

	public static boolean falling = true;
	public static int fall = 3;
	
	
	public static void gravity(){
		if(StartLevel.collides){
			falling = false;
		}
		else {
			falling = true;
			falling();
		}
		
	}
	
	public static void falling(){
		StartLevel.y = StartLevel.y + fall;
	}
	
}//end of class