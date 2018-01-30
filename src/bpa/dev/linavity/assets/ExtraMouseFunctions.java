package bpa.dev.linavity.assets;

import org.lwjgl.input.Mouse;

public class ExtraMouseFunctions {

	/**
	 * @method getMouseX
	 * @description Returns the X position of the mouse starting from the top left. This is useful since that is how other objects are rendered.
	 * 
	 * @param
	 * 	int: gameContainerHeight
	 * 
	 * @return
	 * 	int
	 */
	public static int getMouseX(int gameContainerWidth){
		return Mouse.getX();
	}//end of getMouseX


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
	public static int getMouseY(int gameContainerHeight){
		return gameContainerHeight - Mouse.getY();
	}//end of getMouseY
	
}//end of class