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
import bpa.dev.linavity.assets.InputManager;
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

	
	public static Player player; // Player Object
	public static Gravity gravity; // Gravity Object
	
	// List of all user inputs
	public InputManager im = new InputManager();
	private boolean[] keyLog = new boolean[7]; // Keyboard
	private int[] mouseLog = new int[3]; // Mouse
	
	public StartLevel(){ 
		collide = new Rectangle(350, 350, 50, 50);
	}
	
	// This runs as soon as we compile the program.
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		gravity = new Gravity(); // Create our gravityPower object
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
		
		// If the game is not paused
		if(!menuOpen){

			// Make all keyboard-based updates
			input(gc);
			
			gameUpdates(gc, sbg, delta);
			
		}
		
		// Open pop-up menu
		openMenu(gc, delta);
		checkMenu(gc, sbg);
		
	}
	
	// Make all info game updates
	private void gameUpdates(GameContainer gc, StateBasedGame sbg, int delta) {
		
		// Update Player Attributes
		updatePlayer(delta);
		
		// Check Collisions
		collide(gc);
		
	}

	
	// Our input method uses the input manager class to update all of out input logs
	public void input(GameContainer gc){
	
		// Update our keyboard log
		keyLog = im.getKeyLog(gc);
		
		// Update our mouse log
		mouseLog = im.getMouseLog(gc);
		
	}
	
	// Perform all updates to the player object
	public void updatePlayer(int delta){
		
		// Update the player's position
		player.updatePos(keyLog, delta);
		
		// Update the player's attributes
		// player.updateAttributes();
		
	}
	

	public void collide(GameContainer gc){

		//Left border
		if(player.getX() <= 0){
			player.setX(0);
		}
		
		//Right world border
		if(player.getX()  >= gc.getWidth() - player.getMobImage().getWidth()){
			player.setX(gc.getWidth() - player.getMobImage().getWidth());
		}
		
		//Top world border
		if(player.getY() <= 0){
			player.setY(0);
		}
		
		//Bottom world border
		if(player.getY() >=gc.getHeight() - player.getMobImage().getHeight()){
			player.setY(gc.getWidth() - player.getMobImage().getHeight());
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
	public void checkMenu(GameContainer gc, StateBasedGame sbg) 
			throws SlickException{
		
		// Create our input object
		Input input = gc.getInput();
		
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
