package bpa.dev.linavity.gamestates;


import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import bpa.dev.linavity.assets.ExtraMouseFunctions;


public class MainMenu extends BasicGameState{
	
	// Gamestate ID (0) <-- Main Menu
	public static int id = 0;
	
	// The X & Y positions of the mouse
	public int xpos, ypos;
	
	// Our images
	private Image bg = null;
	private Image play = null;
	private Image exit = null;
	
	// This runs as soon as we compile the program.
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		
		// Initialize our image objects
		bg = new Image("data/bg.jpg"); // Menu Background
		play = new Image("data/button_play.png"); // Play Button
		exit = new Image("data/button_exit.png"); // Exit Button
	}

	// Renders content to the game / screen
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		
		// Draw our menu UI:
		// Background Image
		// Play Button
		// Exit Button
		g.drawImage(bg, 0, 0); // Draw a background
		g.drawImage(play, (gc.getWidth()/2) - (play.getWidth()/2), 300); // Setting the x value as half of the game container and adjusting for the width of the button
		g.drawImage(exit, (gc.getWidth()/2) - (play.getWidth()/2), 400); // Setting the x value as half of the game container and adjusting for the width of the button
		
		g.drawString("XPOS: " + xpos + " | YPOS: " + ypos, 10, 30); // Draw our mouse position for debugging purposes. 
		g.drawString(""+ (300 + play.getHeight()), 10, 50);
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

		// The parameters for checkbounds are the x and y coordinates of the top left of the button and the bottom right of the button
		if(checkBounds( (gc.getWidth()/2) - (play.getWidth()/2) , (gc.getWidth()/2) - (play.getWidth()/2) + play.getWidth() , 300 , 300 + play.getHeight())) {
			if(input.isMouseButtonDown(0)){		
				input.clearKeyPressedRecord();
				sbg.enterState(1);
			}
		}
		
		// The parameters for checkbounds are the x and y coordinates of the top left of the button and the bottom right of the button
		if(checkBounds( (gc.getWidth()/2) - (exit.getWidth()/2) , (gc.getWidth()/2) - (exit.getWidth()/2) + exit.getWidth() , 400 , 400 + exit.getHeight())){
			if(input.isMouseButtonDown(0)){
				System.exit(0);
			}
		}

	}

	// Return the gamestate ID
	public int getID() {
		return MainMenu.id;
	}
	
	//TODO things
	
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
	private boolean checkBounds(int x1, int x2, int y1, int y2){
		if((xpos > x1 && xpos < x2) && (ypos > y1 && ypos < y2 )){
			return true;
		}
		return false;
	}
	
}
