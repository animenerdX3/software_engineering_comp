package main;

public class Physics {

	public static boolean falling = true;
	
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
		BasicGame.y = BasicGame.y + 2;
	}
	
}//end of class
