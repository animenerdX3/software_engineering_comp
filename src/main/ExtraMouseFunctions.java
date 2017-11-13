package main;

import org.lwjgl.input.Mouse;

public class ExtraMouseFunctions {

	
	/**
	 * @method getMouseY
	 * @description Returns the Y position of the mouse starting from the top left. This is useful since that is how other objects are rendered.
	 * 
	 * @param
	 * 	int: gameContainerHeight
	 * 
	 * @return
	 * 	int
	 */
	public int getMouseY(int gameContainerHeight){
		return gameContainerHeight - Mouse.getY();
	}
	
}
