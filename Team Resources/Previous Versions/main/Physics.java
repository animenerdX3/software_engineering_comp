package main;

public class Physics {

	public static boolean falling = true;
	public static int fall = 3;
	
	
	public static void gravity(){
		if(BasicGame.collides){
			falling = false;
		}
		else {
			falling = true;
			falling();
		}
		
	}
	
	public static void falling(){
		BasicGame.y = BasicGame.y + fall;
	}
	
}//end of class