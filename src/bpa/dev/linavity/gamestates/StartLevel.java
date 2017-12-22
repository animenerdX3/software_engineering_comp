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

import bpa.dev.linavity.assets.InputManager;
import bpa.dev.linavity.entities.Camera;
import bpa.dev.linavity.entities.tiles.Tile;
import bpa.dev.linavity.physics.Gravity;
import bpa.dev.linavity.utils.Utils;
import bpa.dev.linavity.world.Level;

/* Task List
//TODO Have the level class return 2d array of tiles on screen
//TODO Use that method to render screen tiles
//TODO Also, use that method to have mobs check for collision
*/

public class StartLevel extends BasicGameState{

	// Images
	private Image bg = null;
	private Image back = null;
	
	// ID of the gamestate
	public static int id = 1;
	
	// Whether or not the pop-up menu is open
	private boolean menuOpen = false;
	
	private int xpos; // Mouse's X position
	private int ypos; // Mouse's Y position

	// util object to access our other objects across the project
	Utils util;
	
	//Variables to set up our level
	public Level level;
	public Camera cam;
	
	// List of all user inputs
	public InputManager im = new InputManager();
	private boolean[] keyLog = new boolean[7]; // Keyboard
	private int[] mouseLog = new int[3]; // Mouse
	
	//The tileID to this gamestate (will be read in as a file later on)
	private int[][] tileIDs = {
			{3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3},
			{3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3},
			{3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3},
			{3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3},
			{3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3},
			{3,0,0,0,0,3,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3},
			{3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3},
			{3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3},
			{3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3},
			{3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3},
			{3,0,0,0,0,0,0,0,0,0,0,3,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3},
			{3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,0,0,0,0,0,0,0,0,0,3},
			{3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,0,0,0,0,0,0,0,0,0,3},
			{3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,0,0,0,0,0,0,0,0,0,3},
			{3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,0,0,0,0,0,0,0,0,0,3},
			{3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,0,0,0,0,3,3,0,0,0,3},
			{3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3},
			{3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3},
			{3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3}
	};
	
	//Default constructor
	public StartLevel(){ 
		
	}
	
	/**
	 * This runs as soon as we compile the program
	 */
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		util = new Utils();
		cam = new Camera(util.getPlayer().getX(), util.getPlayer().getY());
		back = new Image("res/gui/buttons/button_back.png");
		bg = new Image("res/bg.jpg");
		level = new Level(0, tileIDs);
	}

	/**
	 * Renders content to the game / screen
	 */
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		
		bg.draw(0,0);
		
		renderScreen(gc, sbg, g);
		
		g.drawString("X: " + util.getPlayer().getX() + " Y: " + util.getPlayer().getY(), 10,50);
		g.drawString("Cam X: " + cam.getX() + " Cam Y: " + cam.getY(), 10,70);
		g.drawString("Collide: " + util.getPlayer().isCollide(), 10,90);
		g.drawString("Collide up: " + util.getPlayer().isCu(), 10,110);
		g.drawString("Collide down: " + util.getPlayer().isCd(), 10,130);
		g.drawString("Collide left: " + util.getPlayer().isCl(), 10,150);
		g.drawString("Collide right: " + util.getPlayer().isCr(), 10,170);
		
		//Draw player
		util.getPlayer().getMobImage().draw(423, 718); // I have a feeling this line of code is gonna get roasted on by Mr. Santiago
		
		if(menuOpen){
			renderMenu(gc, g);
		}
		
	}

	/**
	 * Render the screen based on where the camera is on our map
	 * @param gc
	 * @param sbg
	 * @param g
	 */
	private void renderScreen(GameContainer gc, StateBasedGame sbg, Graphics g) {
		
		
		// Get a 2d array of tile objects that are contained within our camera's view
		Tile[][] screenTiles = level.getScreenTiles(cam);
		
		// Temp x and y of tile in relation to the camera
		int tileX, tileY;
		
		// Draw the tiles that belong on the screen.
		for(int i = 0; i < screenTiles.length; i++) {
			for(int j = 0; j < screenTiles[i].length; j++) {
				if(screenTiles[i][j] != null) {
					tileX = screenTiles[i][j].getX() - cam.getX(); // Get the tile's temp x location for the screen rendering
					tileY = screenTiles[i][j].getY() - cam.getY(); // Get the tile's temp y location for the screen rendering
					screenTiles[i][j].getTexture().draw(tileX, tileY); // Draw the tile
				}
			}
		}
		// End of drawing screen tiles
		
	}

	/**
	 * Constant Loop, very fast, loops based on a delta (the amount of time that passes between each instance)
	 */
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		
		// If the game is not paused
		if(!menuOpen){
			
			// Make all keyboard-based updates
			input(gc);
			
			// Make all game information updates
			gameUpdates(gc, sbg, delta);
			
		}
		
		// Open pop-up menu
		openMenu(gc, delta);
		checkMenu(gc, sbg);
		
	}
	
	/**
	 * Make all info game updates
	 * @param gc
	 * @param sbg
	 * @param delta
	 */
	private void gameUpdates(GameContainer gc, StateBasedGame sbg, int delta) {

		// Check Collisions
		collide(gc);
		
		// Update Player Attributes
		updatePlayer(delta);
		
		// Update Camera Coordinates
		cam.updateCameraPos(util.getPlayer().getX(), util.getPlayer().getY());
		
		// Open Pop-up menu
		if(keyLog[6])
			menuOpen = !menuOpen;


		
	}

	
	/**
	 * Our input method uses the input manager class to update all of out input logs
	 * @param gc
	 */
	public void input(GameContainer gc){
	
		// Update our keyboard log
		keyLog = im.getKeyLog(gc);
		
		// Update our mouse log
		mouseLog = im.getMouseLog(gc);
		
	}
	
	/**
	 * Perform all updates to the player object
	 * @param delta
	 */
	public void updatePlayer(int delta){
		
		//Saving our previous X and Y values so we can use them for future reference
		util.getPlayer().setPrevX(util.getPlayer().getX());
		util.getPlayer().setPrevY(util.getPlayer().getY());
		
		// Update the player's position
		util.getPlayer().updatePos(keyLog, delta, util);
		
		// Update the player's attributes
		// player.updateAttributes();
		
	}
	
	/**
	 * Implements world borders
	 * @param gc
	 */
	public void collide(GameContainer gc){

		//Left border
		//if(util.getPlayer().getX() <= 0){
		//	util.getPlayer().setX(0);
		//}
		
		//Right world border
		//if(util.getPlayer().getX()  >= gc.getWidth() - util.getPlayer().getMobImage().getWidth()){
		//	util.getPlayer().setX(gc.getWidth() - util.getPlayer().getMobImage().getWidth());
		//}
		
		//Top world border
//		if(util.getPlayer().getY() <= 0){
//			util.getPlayer().setY(0);
//		}
//		
//		//Bottom world border
//		if(util.getPlayer().getY() >=gc.getHeight() - util.getPlayer().getMobImage().getHeight()){
//			util.getPlayer().setY(gc.getHeight() - util.getPlayer().getMobImage().getHeight());
//			util.getPlayer().setIsFalling(false);
//		}
		
		util.getPlayer().checkCollisions(level, cam);
		
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
		
		back = new Image("res/gui/buttons/button_back.png");
		
		// Back Button
		// The parameters for checkbounds are the x and y coordinates of the top left of the button and the bottom right of the button
		if(MainMenu.checkBounds( (gc.getWidth()/2) - (back.getWidth()/2) , (gc.getWidth()/2) - (back.getWidth()/2) + back.getWidth() , 400 , 400 + back.getHeight(), xpos, ypos)){
			if(input.isMousePressed(0)){
				input.clearKeyPressedRecord();
				sbg.enterState(0);
				menuOpen = false;
			}
			back = new Image("res/gui/buttons/button_back_hover.png");
		}
		
	}
	
	/**
	 * Checks for the user input of the escape key to toggle the in-game menu...
	 * @param gc
	 * @param delta
	 */
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
	
}//end of class
