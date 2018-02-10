package bpa.dev.linavity.gamestates;


import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import bpa.dev.linavity.Main;
import bpa.dev.linavity.assets.ExtraMouseFunctions;


public class GameOver extends BasicGameState{
	
	// Gamestate ID (2) <-- Game Over Screen
	public static int id = 2;
	
	// The X & Y positions of the mouse
	public int xpos, ypos;
	
	// Our images
	private Image bg = null;
	private Image back = null;
	
	// This runs as soon as we compile the program.
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		
		// Initialize our image objects
		bg = new Image("res/gui/gameover.png"); // Menu Background

	}

	// Renders content to the game / screen
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		
		g.drawImage(bg, 0, 0);
		
		// DRAW OUR MENU UI //
		
		if(Main.util.debugMode){
			g.setColor(Color.white);
			g.drawString("XPOS: " + xpos + " | YPOS: " + ypos, 10, 30); // Draw our mouse position for debugging purposes. 
		}
		
		renderGameOverScreen(gc, g);
		
		// END OUR MENU UI //
		

	}

	// Constant Loop, very fast, loops based on a delta (the amount of time that passes between each instance)
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		
		// !NOTE! the mouse coordinates start from the bottom left of the screen
		// this is different from the shapes that are drawn based on coordinates going from the top right of the screen.
		// Because of this difference, we've created our own mouse functions that take into account the game container
		// dimensions, to give us a proper X and Y value that line up with the coordinates of all of our other objects.
		
		Input input = gc.getInput(); // Create our input object
		
		xpos = ExtraMouseFunctions.getMouseX(gc.getWidth()); // Updates the x coordinate of the mouse
		ypos = ExtraMouseFunctions.getMouseY(gc.getHeight()); // Updates the y coordinate of the mouse

		
		// CHECK OUR MOUSE INPUT //

		backButtonAction(gc, sbg, input); // If the option menu has not been selected, check for the main section's buttons

		// END OUR MOUSE INPUT

	}


	/**
	 * @method renderMainMenuScreenMain
	 * @description draws the images needed for the main screen of the main menu
	 * 
	 * @param
	 * 	GameContainer gc, Graphics g
	 * 
	 * @return
	 * 	void:
	 */
	public void renderGameOverScreen(GameContainer gc, Graphics g){
		
		// Back Button
		g.drawImage(back, (gc.getWidth()/2) - (back.getWidth()/2), 500); // Setting the x value as half of the game container and adjusting for the width of the button
		
	}

	/**
	 * @method optionButtonAction
	 * @description checks the button detection and handles the according events in the options section of our main menu
	 * 
	 * @param
	 * GameContainer gc
	 * 
	 * @return
	 * 	void:
	 * @throws SlickException 
	 */
	public void backButtonAction(GameContainer gc, StateBasedGame sbg, Input input) 
			throws SlickException{
		
		back = new Image("res/gui/buttons/button_back.png");
		
		// Back Button
		// The parameters for checkbounds are the x and y coordinates of the top left of the button and the bottom right of the button
		if(checkBounds( (gc.getWidth()/2) - (back.getWidth()/2) , (gc.getWidth()/2) - (back.getWidth()/2) + back.getWidth() , 500 , 500 + back.getHeight())){
			if(input.isMousePressed(0)){
				input.clearKeyPressedRecord();
				Main.appgc.setMouseGrabbed(true);
				Main.util.getPlayer().setIsAlive(true);
				Main.util.setMusic(Main.util.getMusicQueue(1));
				Main.util.getMusic().loop(1f, Main.util.getMusicManager().getVolume());
				sbg.enterState(Main.startlevel);
			}
			back = new Image("res/gui/buttons/button_back_hover.png");
		}
		
	}
	
	
	/**
	 * @method checkBounds
	 * @description Returns true/false if the mouse is within the boundaries of the given area
	 * 
	 * @param
	 * 	int, int, int, int: The X and Y positions for the top right and bottom right boundaries
	 * 
	 * @return
	 * 	boolean: Whether or not the mouse is in the bounds
	 */
	boolean checkBounds(int x1, int x2, int y1, int y2){
		if((xpos > x1 && xpos < x2) && (ypos > y1 && ypos < y2 )){
			return true;
		}
		return false;
	}
	
	// For other classes to use the checkBounds method
	public static boolean checkBounds(int x1, int x2, int y1, int y2, float tileX, float tileY){
		if((tileX > x1 && tileX < x2) && (tileY > y1 && tileY < y2 )){
			return true;
		}
		return false;
	}
	
	// Return the gamestate ID
	public int getID() {
		return GameOver.id;
	}
	
}
