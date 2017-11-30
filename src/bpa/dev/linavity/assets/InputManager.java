package bpa.dev.linavity.assets;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import bpa.dev.linavity.entities.Player;
import bpa.dev.linavity.gamestates.StartLevel;
import bpa.dev.linavity.physics.Gravity;

public class InputManager {
	
	// Possible keyboard inputs by user
	// Keyboard -> [W, A, S, D, LShift, LControl, ESC] 
	// Mouse -> [Mouse X Pos, Mouse Y Pos, left click]
	private boolean[] keyLog = new boolean[7];
	private int[] mouseLog = new int[3];
	
	public InputManager (){
		
	}
	
	public boolean[] getKeyLog(GameContainer gc) {
		updateKeyLog(gc);
		return keyLog;
	}
	
	public void updateKeyLog(GameContainer gc) {
		
		// Creating our input object
		Input input = gc.getInput();
		
		// Reset keyboard log
		for(int i = 0; i < keyLog.length; i++) {
			keyLog[i] = false;
		}
		
		// WASD
		if(input.isKeyDown(Input.KEY_W)){
			keyLog[0] = true;
		}
		if(input.isKeyDown(Input.KEY_A)){
			keyLog[1] = true;
		}
		if(input.isKeyDown(Input.KEY_S)){
			keyLog[2] = true;
		}
		if(input.isKeyDown(Input.KEY_D)){
			keyLog[3] = true;
		}
		
		// Shift, Control, Escape
		if(input.isKeyDown(Input.KEY_LSHIFT)){
			keyLog[4] = true;
		}
		if(input.isKeyPressed(Input.KEY_LCONTROL)){
			keyLog[5] = true;
		}
		if(input.isKeyPressed(Input.KEY_ESCAPE)){
			keyLog[6] = true;
		}
	}
	
	public int[] getMouseLog(GameContainer gc) {
		updateMouseLog(gc);
		return mouseLog;
	}
	
	public void updateMouseLog(GameContainer gc) {
		
		// Creating our input object
		Input input = gc.getInput(); 
		
		for(int i = 2; i < mouseLog.length; i++) {
			mouseLog[i] = 0;
		}
		
		mouseLog[0] = ExtraMouseFunctions.getMouseX(gc.getWidth()); // Updates the x coordinate of the mouse
		mouseLog[1] = ExtraMouseFunctions.getMouseY(gc.getWidth()); // Updates the Y coordinate of the mouse
		
		// Left Click
		if(input.isMousePressed(0)){
			mouseLog[2] = 1;
		}

	}
		
//	public  void changeGrav(GameContainer gc, int delta) throws SlickException{
//		
//		if(input.isKeyPressed(Input.KEY_LCONTROL)){
//			StartLevel.gravity.setGravity(-StartLevel.gravity.getGravity());
//		}
//	}

}//end of class
