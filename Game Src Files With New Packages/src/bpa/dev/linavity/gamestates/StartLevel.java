package bpa.dev.linavity.gamestates;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import bpa.dev.linavity.assets.ExtraMouseFunctions;
import bpa.dev.linavity.assets.Keyboard;
import bpa.dev.linavity.entities.Player;
import bpa.dev.linavity.physics.Gravity;

public class StartLevel extends BasicGameState{

	private Image bg = null;
	private Image back = null;
	
	public static int id = 1;

	private Rectangle collide;
	public static int x = 350, y = 350;
	public static boolean collides = false;
	
	private boolean menuOpen = false;
	
	private int xpos; // Mouse's X position
	private int ypos; // Mouse's Y position
	Keyboard keyInput = new Keyboard(); // Create our keyboard object || Handles all keyboard input detection and function
	
	public static Player player; // Player Object
	public static Gravity gravity; // Gravity Object
	
	public StartLevel(){ 
		collide = new Rectangle(350, 350, 50, 50);
	}
	
	// This runs as soon as we compile the program.
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		
		gravity = new Gravity(); // Create our gravity object
		player = new Player(); // Create our player object
		back = new Image("data/button_back.png");
		bg = new Image("data/bg.jpg");
		
	}

	// Renders content to the game / screen
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		
		bg.draw(0,0);
		g.drawString("X: " + player.getX() + " Y: " + player.getY() + " collides " + collides, 10,50);
		g.setColor(Color.cyan);
		
		collide = new Rectangle(350, 350, 50, 50);
		
		g.draw(collide);
		
		player.getMobImage().draw(player.getX(), player.getY());
		
		if(menuOpen){
			renderMenu(gc, g);
		}
		
	}

	// Constant Loop, very fast, loops based on a delta (the amount of time that passes between each instance)
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		
		Input input = gc.getInput(); // Create our input object
		
		xpos = ExtraMouseFunctions.getMouseX(gc.getWidth()); // Updates the x coordinate of the mouse
		ypos = ExtraMouseFunctions.getMouseY(gc.getHeight()); // Updates the y coordinate of the mouse
		
		// If the game is not paused
		if(!menuOpen){

			// Make all keyboard-based updates
			keyInput.getInputUpdate(gc, delta);
			
			// Update player attributes
			updatePlayer();
			
			// Check collisions
			collide(gc);
		}
		
		// Open pop-up menu
		openMenu(gc, delta);
		checkMenu(gc, sbg, input);
		
	}
	
	// Perform all updates to the player object
	public void updatePlayer(){
		
		player.setX(keyInput.getX()); // Update the x position of our player object
		player.setY(keyInput.getY()); // Update the y position of our player object

		// keyInput.setX(player.getX()); // Keyboard is handling player movement
		// keyInput.setY(player.getY()); // Keyboard is handling player movement
		
		// player.setY(player.getY() + gravity.gravity(player.getY()));
		//ehhhhhhhhhhhhhhhhhhhhhhhh
		
	}
	
	public void collide(GameContainer gc){
			
		//Left border
		if(x <= 0){
			x = 0;
		}
		
		//Right world border
		if(x >= gc.getWidth() - player.getMobImage().getWidth()){
			x = gc.getWidth() - player.getMobImage().getWidth();
		}
		
		//Top world border
		if(y <= 0){
			y = 0;
		}
		
		//Bottom world border
		if(y>=gc.getHeight() - player.getMobImage().getHeight()){
			y = gc.getWidth() - player.getMobImage().getHeight();
		}
		
		
	}//end of collide
	

	/**
	 * @method renderMenu
	 * @description draws the images needed for the popup menu
	 * 
	 * @param
	 * GameContainer gc, Graphics g
	 * 
	 * @return
	 * 	void:
	 */
	public void renderMenu(GameContainer gc, Graphics g){
		// Back Button
		g.drawImage(back, (gc.getWidth()/2) - (back.getWidth()/2), 400); // Setting the x value as half of the game container and adjusting for the width of the button
	}
	
	/**
	 * @method checkMenu
	 * @description checks mouse actions for the popup menu
	 * 
	 * @param
	 * GameContainer gc, StateBasedGame sbg, Input input
	 * 
	 * @return
	 * 	void:
	 * @throws SlickException 
	 */
	public void checkMenu(GameContainer gc, StateBasedGame sbg, Input input) 
			throws SlickException{
		
		back = new Image("data/button_back.png");
		
		// Back Button
		// The parameters for checkbounds are the x and y coordinates of the top left of the button and the bottom right of the button
		if(MainMenu.checkBounds( (gc.getWidth()/2) - (back.getWidth()/2) , (gc.getWidth()/2) - (back.getWidth()/2) + back.getWidth() , 400 , 400 + back.getHeight(), xpos, ypos)){
			if(input.isMousePressed(0)){
				input.clearKeyPressedRecord();
				sbg.enterState(0);
				menuOpen = false;
			}
			back = new Image("data/button_back_hover.png");
		}
		
	}
	
	// Checks for the user input of the escape key to toggle the in-game menu...
	public void openMenu(GameContainer gc, int delta){
		Input input = gc.getInput(); // Creating our input object
		if(input.isKeyPressed(Input.KEY_ESCAPE)){
			menuOpen = !menuOpen; // ! Makes the escape toggle
		}
	}
	
	public int getID() {
		// TODO Auto-generated method stub
		return StartLevel.id;
	}
	
}
