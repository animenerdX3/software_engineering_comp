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
import bpa.dev.linavity.entities.Camera;
import bpa.dev.linavity.entities.enemies.Starter;
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
	private Image health_gui = null;
	private Image health_bar = null;
	private Image grav_gui = null;
	private Image grav_bar = null;
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
	
	private Rectangle bounds;
	private Rectangle enemybounds;
	
	//List of all possible enemies
	Starter [] enemies = new Starter[1];
	
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
		health_gui = new Image("res/gui/stats/health_bar.png");
		health_bar = new Image("res/gui/stats/health_bar_full.png");
		grav_gui = new Image("res/gui/stats/grav_pack.png");
		grav_bar = new Image("res/gui/stats/grav_pack_full.png");
		level = new Level(0, "startlevel");
		bounds = new Rectangle(util.getPlayer().getX() - cam.getX(), util.getPlayer().getY() - cam.getY(), 50, 50);
		
		//Create enemies
		enemies[0] = new Starter(util, 300, 750);
		enemybounds = new Rectangle(enemies[0].getX() - cam.getX(), enemies[0].getY() - cam.getY(), 50, 50);

	}

	/**
	 * Renders content to the game / screen
	 * Note: Positioning of objects matter: Draw backgrounds first, foregrounds last
	 */
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		
		bg.draw(0,0);
		
		renderScreen(gc, sbg, g);
		
		if(util.debugMode) {
			g.drawString("X: " + util.getPlayer().getX() + " Y: " + util.getPlayer().getY(), 10,50);
			g.drawString("Cam X: " + cam.getX() + " Cam Y: " + cam.getY(), 10,70);
			g.drawString("Collide: " + util.getPlayer().isCollide(), 10,90);
			g.drawString("Collide up: " + util.getPlayer().isCu(), 10,110);
			g.drawString("Collide down: " + util.getPlayer().isCd(), 10,130);
			g.drawString("Collide left: " + util.getPlayer().isCl(), 10,150);
			g.drawString("Collide right: " + util.getPlayer().isCr(), 10,170);
			g.drawString("XPOS: " + xpos + " | YPOS: " + ypos, 10, 190); // Draw our mouse position for debugging purposes.
			System.out.println("Player Y: "+util.getPlayer().getY());
		}
		
		//Draw enemies
		enemies[0].getMobImage().draw(enemies[0].getX() - cam.getX(), enemies[0].getY() - cam.getY());
		
		
		//If a projectile exists, then draw it on the screen
		if(util.getPlayer().isProjectileExists()) {
			util.getPlayer().getCurrentProjectile().getProjectileImage().draw(util.getPlayer().getCurrentProjectile().getX() - cam.getX() + 100, util.getPlayer().getCurrentProjectile().getY() - cam.getY() + 15);
		}
		
		//Draw player
		util.getPlayer().getMobImage().draw(util.getPlayer().getX() - cam.getX(), util.getPlayer().getY() - cam.getY());
		if(util.debugMode) {
		bounds = new Rectangle(util.getPlayer().getX() - cam.getX(), util.getPlayer().getY() - cam.getY(), 50, 50);
		g.setColor(Color.blue);
		g.draw(bounds);
		
		enemybounds = new Rectangle(enemies[0].getX() - cam.getX(), enemies[0].getY() - cam.getY(), 50, 50);
		g.setColor(Color.orange);
		g.draw(enemybounds);
		}
		
		health_gui.draw(0,0);
		health_bar.draw(0,0);
		
		grav_gui.draw(0,0);
		grav_bar.draw(0,0);
		
		//Draw menu, if open
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
		float tileX, tileY;
		
		// Draw the tiles that belong on the screen.
		for(int i = 0; i < screenTiles.length; i++) {
			for(int j = 0; j < screenTiles[i].length; j++) {
				if(screenTiles[i][j] != null) {
					tileX = screenTiles[i][j].getX() - cam.getX(); // Get the tile's temp x location for the screen rendering
					tileY = screenTiles[i][j].getY() - cam.getY(); // Get the tile's temp y location for the screen rendering
					screenTiles[i][j].getTexture().draw(tileX, tileY); // Draw the tile
					if(util.debugMode) {
					Rectangle tileBounds = new Rectangle(tileX, tileY, screenTiles[i][j].getWidth(), screenTiles[i][j].getHeight());
					g.setColor(Color.white);
					g.draw(tileBounds);
					}
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
		
		//Mouse Functions
		xpos = ExtraMouseFunctions.getMouseX(gc.getWidth()); // Updates the x coordinate of the mouse
		ypos = ExtraMouseFunctions.getMouseY(gc.getHeight()); // Updates the y coordinate of the mouse
		
		if(util.getPlayer().getHealth() <= 0) {
			util.getPlayer().setHealth(100);
			util.getPlayer().setX(100);
			util.getPlayer().setY(100);
			sbg.enterState(2);
		}
		
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
		
		updateEnemies(delta);
		
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
	 * Perform all updates to the enemy objects
	 * @param delta
	 */
	public void updateEnemies(int delta){
		
		// Update the player's position
		enemies[0].moveEnemy(delta);
		
	}
	
	/**
	 * Implements world borders
	 * @param gc
	 */
	public void collide(GameContainer gc){
		
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
