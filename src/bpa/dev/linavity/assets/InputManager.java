package bpa.dev.linavity.assets;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

public class InputManager {
	
	// Possible keyboard inputs by user
	// Keyboard -> [Space, A, S, D, Shift, Control, Escape, Enter, E, W] 
	// Mouse -> [Mouse X Pos, Mouse Y Pos, left click]
	private boolean[] keyLog = new boolean[11];
	private int[] mouseLog = new int[3];
	
	//Default constructor
	public InputManager (){
		
	}
	
	/**
	 * 
	 * @param gc
	 * @return keyLog	array of booleans; tells what keys are pressed or not pressed
	 */
	public boolean[] getKeyLog(GameContainer gc) {
		updateKeyLog(gc);
		return keyLog;
	}
	
	/**
	 * Update our boolean array for the keys by checking to see if any key has been pressed
	 * 
	 * @param gc
	 */
	public void updateKeyLog(GameContainer gc) {
		
		// Creating our input object
		Input input = gc.getInput();
		
		// Reset keyboard log
		for(int i = 0; i < keyLog.length; i++) 
			keyLog[i] = false;
		
		// 0 - Space
		if(input.isKeyPressed(Input.KEY_SPACE))
			keyLog[0] = true;
		
		// 1 - A or Left Arrow Key
		if(input.isKeyDown(Input.KEY_A) || input.isKeyDown(Input.KEY_LEFT))
			keyLog[1] = true;
		
		// 2 - S or Down Arrow Key
		if(input.isKeyDown(Input.KEY_S) || input.isKeyDown(Input.KEY_DOWN))
			keyLog[2] = true;
		
		//3 - D or Left Arrow Key
		if(input.isKeyDown(Input.KEY_D) || input.isKeyDown(Input.KEY_RIGHT))
			keyLog[3] = true;
		
		//4 - Left Shift
		if(input.isKeyDown(Input.KEY_LSHIFT))
			keyLog[4] = true;
		
		//5 - Left Control
		if(input.isKeyPressed(Input.KEY_LCONTROL))
			keyLog[5] = true;
		
		//6 - Escape
		if(input.isKeyPressed(Input.KEY_ESCAPE))
			keyLog[6] = true;
		
		//7 - Enter
		if(input.isKeyPressed(Input.KEY_ENTER))
			keyLog[7] = true;
		
		//8 - E
		if(input.isKeyPressed(Input.KEY_E))
			keyLog[8] = true;
		
		//9 - W
		if(input.isKeyPressed(Input.KEY_W) || input.isKeyDown(Input.KEY_UP))
			keyLog[9] = true;
		
		//9 - W
		if(input.isKeyPressed(Input.KEY_I))
			keyLog[10] = true;

	}//end of updateKeyLog
	
	/**
	 * Update our mouse array by giving us the coordinates to our cursor on the screen
	 * @param gc
	 * @return mouseLog
	 */
	public int[] getMouseLog(GameContainer gc) {
		updateMouseLog(gc);
		return mouseLog;
	}
	
	/**
	 * update our mouse log and see if anything has been clicked
	 * @param gc
	 */
	public void updateMouseLog(GameContainer gc) {
		
		// Creating our input object
		Input input = gc.getInput(); 
		
		for(int i = 2; i < mouseLog.length; i++) 
			mouseLog[i] = 0;
		
		
		mouseLog[0] = ExtraMouseFunctions.getMouseX(gc.getWidth()); // Updates the x coordinate of the mouse
		mouseLog[1] = ExtraMouseFunctions.getMouseY(gc.getWidth()); // Updates the Y coordinate of the mouse
		
		// Left Click
		if(input.isMousePressed(0))
			mouseLog[2] = 1;
		

	}//end of updateMouseLog

	/* GETTERS */
	
	/**
	 * 
	 * @return the size of our keyLog
	 */
	public int getKeyLogLength() {
		return keyLog.length;
	}//end of getKeyLogLength
	
	/**
	 * 
	 * @return the size of ourMouseLog
	 */
	public int getMouseLogLength() {
		return mouseLog.length;
	}//end of getKeyLogLength
	
}//end of class
