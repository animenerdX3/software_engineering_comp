package bpa.dev.linavity.gamestates;

import java.awt.Rectangle;
import java.util.ArrayList;

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
import bpa.dev.linavity.entities.*;
import bpa.dev.linavity.entities.enemies.*;
import bpa.dev.linavity.entities.tiles.Tile;
import bpa.dev.linavity.world.Level;
import bpa.dev.linavity.world.ParallaxMap;

public class StartLevel extends BasicGameState{
	
	//GUI
	private Image health_gui = null;
	private Image health_bar = null;
	private Image grav_gui = null;
	private Image grav_bar = null;
	
	//Pause Menu
	private Image pause_menu_ui = null;
	private Image back = null;
	
	//Parallax Backgrounds for Our Level
	private ParallaxMap bg;
	
	// ID of the gamestate
	public static int id = 1;
	
	// Whether or not the pop-up menu is open
	private boolean menuOpen = false;
	
	//Mouse Coordinates
	private int xpos; // Mouse's X position
	private int ypos; // Mouse's Y position
	
	//List of all possible enemies
	public static ArrayList <Mob> mobs = new ArrayList<Mob>();
	
	//Tiles for our level
	private Tile[][] screenTiles;
	private Tile[][] eventTiles;
	
	/**
	 * This runs as soon as we compile the program
	 */
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		
		mobs = getMobs();
		back = new Image("res/gui/buttons/button_back.png");
		bg = new ParallaxMap("res/bg.jpg", -450, 0.5f, true);
		health_gui = new Image("res/gui/stats/health_bar.png");
		health_bar = new Image("res/gui/stats/health_bar_full.png");
		grav_gui = new Image("res/gui/stats/grav_pack.png");
		grav_bar = new Image("res/gui/stats/grav_pack_full.png");
		Main.util.setLevel(new Level(0, "startlevel"));
		Main.util.setEvents(new Level(0, "startlevel_events"));	
		
	}//end of init

	private ArrayList<Mob> getMobs() throws SlickException {
		//Add mobs
		ArrayList <Mob> mobs = new ArrayList<Mob>();
		
		mobs.add(Main.util.getPlayer());
		mobs.add(new Starter(500, 750));
		mobs.add(new Tank(1100, 750));
		mobs.add(new Tank(1200, 750));
		mobs.add(new Bomber(600, 650));
		mobs.add(new Bomber(615, 650));
		mobs.add(new Bomber(660, 650));
		
		return mobs;
	}//end of getMobs
	
	/**
	 * Renders content to the game / screen
	 * Note: Positioning of objects matter: Draw backgrounds first, foregrounds last
	 */
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		
		bg.getBackgroundLayer().draw(bg.getX(),0);
		
		renderScreen(gc, sbg, g);
		
		if(Main.util.debugMode) {
			g.drawString("X: " + Main.util.getPlayer().getX() + " Y: " + Main.util.getPlayer().getY(), 10,50);
			g.drawString("Cam X: " + Main.util.getCam().getX() + " Cam Y: " + Main.util.getCam().getY(), 10,70);
			g.drawString("Collide up: " + Main.util.getPlayer().isCu(), 10,110);
			g.drawString("Collide down: " + Main.util.getPlayer().isCd(), 10,130);
			g.drawString("Collide left: " + Main.util.getPlayer().isCl(), 10,150);
			g.drawString("Collide right: " + Main.util.getPlayer().isCr(), 10,170);
			g.drawString("XPOS: " + xpos + " | YPOS: " + ypos, 10, 190); // Draw our mouse position for debugging purposes.
			g.drawString("Can Jump: "+Main.util.getPlayer().canJump(), 10, 230); 
			if(Main.util.getPlayer().getCurrentProjectile() != null) {
				g.drawString("Shot Left: "+Main.util.getPlayer().getCurrentProjectile().isShotLeft(), 10, 250); 
				g.drawString("Shot Right: "+Main.util.getPlayer().getCurrentProjectile().isShotRight(), 10, 270); 
			}
		}
		
		//Draw enemies
		for(int i = 0; i < mobs.size(); i++){
			if(mobs.get(i).isAlive())
				mobs.get(i).getCurrentImage().draw(mobs.get(i).getX() - Main.util.cam.getX(), mobs.get(i).getY() - Main.util.cam.getY());
			else if(i > 0)
				mobs.remove(i);
		}

		
		//If a projectile exists, then draw it on the screen
		if(Main.util.getPlayer().isProjectileExists()) {
			Main.util.getPlayer().getCurrentProjectile().getProjectileImage().draw(Main.util.getPlayer().getCurrentProjectile().getX() - Main.util.getCam().getX(), Main.util.getPlayer().getCurrentProjectile().getY() - Main.util.getCam().getY());
			if(Main.util.debugMode)
				g.drawRect(Main.util.getPlayer().getCurrentProjectile().getX() - Main.util.getCam().getX(), Main.util.getPlayer().getCurrentProjectile().getY() - Main.util.getCam().getY(), Main.util.getPlayer().getCurrentProjectile().getWidth(), Main.util.getPlayer().getCurrentProjectile().getHeight());
		}
		
		if(Main.util.debugMode) {
		
		g.setColor(Color.orange);
		
		for(int i = 0; i < mobs.size(); i++) {

		mobs.get(i).setBoundingBox(new Rectangle((int) (mobs.get(i).getX() - Main.util.getCam().getX()), (int) (mobs.get(i).getY() - Main.util.getCam().getY()), mobs.get(i).getWidth(), mobs.get(i).getHeight()));
		g.drawRect((int) mobs.get(i).getBoundingBox().getX(), (int) mobs.get(i).getBoundingBox().getY(), (int) mobs.get(i).getBoundingBox().getWidth(), (int) mobs.get(i).getBoundingBox().getHeight());
			}
		}
		
		health_gui.draw(0,0);
		health_bar.draw(25,850,(float) (25+(Main.util.getPlayer().getHealth() * 2.7)),850+27,0,0,(float) (Main.util.getPlayer().getHealth() * 2.7),27);
		
		grav_gui.draw(0,0);
		grav_bar.draw(318,850,(float) (318+(Main.util.getPlayer().getGravPack().getGravpower() * 2.7)),850+27,0,0,(float) (Main.util.getPlayer().getGravPack().getGravpower() * 2.7),27);
		
		//Draw menu, if open
		if(menuOpen){
			renderMenu(gc, g);
		}
		
	}//end of render

	/**
	 * Render the screen based on where the camera is on our map
	 * @param gc
	 * @param sbg
	 * @param g
	 */
	private void renderScreen(GameContainer gc, StateBasedGame sbg, Graphics g) {
		
		// Get a 2d array of tile objects that are contained within our camera's view
		screenTiles = Main.util.getLevel().getScreenTiles(Main.util.getCam());
		
		// Temp x and y of tile in relation to the camera
		float tileX, tileY;
		
		// Draw the tiles that belong on the screen.
		for(int i = 0; i < screenTiles.length; i++) {
			for(int j = 0; j < screenTiles[i].length; j++) {
				if(screenTiles[i][j] != null) {
					tileX = screenTiles[i][j].getX() - Main.util.getCam().getX(); // Get the tile's temp x location for the screen rendering
					tileY = screenTiles[i][j].getY() - Main.util.getCam().getY(); // Get the tile's temp y location for the screen rendering
					screenTiles[i][j].getTexture().draw(tileX, tileY); // Draw the tile
					if(Main.util.debugMode) {
						screenTiles[i][j].setCollisionBox(new Rectangle((int) tileX, (int) tileY, (int) screenTiles[i][j].getWidth(), (int) screenTiles[i][j].getHeight()));
						if(!screenTiles[i][j].isPassable()) {
							g.setColor(Color.white);
							g.drawRect((int)screenTiles[i][j].getCollisionBox().getX(), (int) screenTiles[i][j].getCollisionBox().getY(), (int) screenTiles[i][j].getCollisionBox().getWidth(), (int)screenTiles[i][j].getCollisionBox().getHeight());
						}
					}
				}
			}
		}
		// End of drawing screen tiles
	
		// Get a 2d array of tile objects that are contained within our camera's view
		eventTiles = Main.util.getEvents().getScreenTiles(Main.util.getCam());
		
		// Temp x and y of tile in relation to the camera
		float eventTileX, eventTileY;
		
		// Draw the tiles that belong on the screen.
		for(int i = 0; i < eventTiles.length; i++) {
			for(int j = 0; j < eventTiles[i].length; j++) {
				if(eventTiles[i][j] != null) {
					eventTileX = eventTiles[i][j].getX() - Main.util.getCam().getX(); // Get the tile's temp x location for the screen rendering
					eventTileY = eventTiles[i][j].getY() - Main.util.getCam().getY(); // Get the tile's temp y location for the screen rendering
					eventTiles[i][j].getTexture().draw(eventTileX, eventTileY); // Draw the tile
					if(Main.util.debugMode) {
						eventTiles[i][j].setCollisionBox(new Rectangle((int) eventTileX, (int) eventTileY, (int) eventTiles[i][j].getWidth(), (int) eventTiles[i][j].getHeight()));
						if(!eventTiles[i][j].isPassable()) {
							g.setColor(Color.white);
							g.drawRect((int)eventTiles[i][j].getCollisionBox().getX(), (int) eventTiles[i][j].getCollisionBox().getY(), (int) eventTiles[i][j].getCollisionBox().getWidth(), (int)eventTiles[i][j].getCollisionBox().getHeight());
						}
					}
				}
			}
		}
		// End of drawing event tiles
		
	}//end of renderScreen

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
	private void renderMenu(GameContainer gc, Graphics g){
		// Back Button
		g.drawImage(pause_menu_ui, 0,0); // Setting the pause menu ui
		g.drawImage(back, (gc.getWidth()/2) - (back.getWidth()/2), 400); // Setting the x value as half of the game container and adjusting for the width of the button
	}//end of renderMenu

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
			
			//Update Animations
			updateAnimation(delta);
			
			// Message Handler
			Main.util.getMessageHandler().dispatchMessages();
			
			bg.moveBackground();
			
		}
		else {
			stopAnimation();//Stop updating animation
			Main.util.getMusic().pause();//Pause the music
		}
		
		// Open pop-up menu
		openMenu(gc, delta);
		checkMenu(gc, sbg);
		
		//Mouse Functions
		xpos = ExtraMouseFunctions.getMouseX(gc.getWidth()); // Updates the x coordinate of the mouse
		ypos = ExtraMouseFunctions.getMouseY(gc.getHeight()); // Updates the y coordinate of the mouse
		
	}//end of update
	
	/**
	 * Our input method uses the input manager class to update all of out input logs
	 * @param gc
	 */
	private void input(GameContainer gc){
	
		// Update our keyboard log
		Main.util.setKeyLog(Main.util.getIm().getKeyLog(gc));
		
		// Update our mouse log
		Main.util.setMouseLog(Main.util.getIm().getMouseLog(gc));
		
	}//end of input
	
	/**
	 * Make all info game updates
	 * @param gc
	 * @param sbg
	 * @param delta
	 */
	private void gameUpdates(GameContainer gc, StateBasedGame sbg, int delta) {
		
		//Check for End of Level
		endLevel(sbg);
		
		// Update Mob Attributes
		updateMobs(delta);
		
		//Check to see if mobs are alive
		checkMobStatus(sbg);
		
		// Update Camera Coordinates
		Main.util.getCam().updateCameraPos(mobs.get(0).getX(), mobs.get(0).getY());
		Main.util.getCam().outOfBoundsKill()
		;		
		// Open Pop-up menu
		if(Main.util.getKeyLogSpecificKey(6)) {
			Main.util.getSFX(0).play(1f, Main.util.getSoundManager().getVolume());
			menuOpen = !menuOpen;
		}
	}//end of gameUpdates
	
	/**
	 * Founds out when the gamestate should change
	 * @param gc
	 */
	private void endLevel(StateBasedGame sbg) {
		if(Main.util.getPlayer().isReadyForNextLevel()) {
			sbg.enterState(getID() + 1);
		}
	}//end of endLevel

	/**
	 * Perform all updates to the player object
	 * @param delta
	 */
	private void updateMobs(int delta){
		// Update the mob's position
		for(int i = 0; i < mobs.size(); i++) 
			mobs.get(i).update(delta);
	}//end of updateMobs
	
	private void startAnimation() {
		
		//Starts animation for all mobs
		for(int i = 0; i < mobs.size(); i++) {
			 mobs.get(i).getLeftAni().start(); 
			 mobs.get(i).getRightAni().start();
			 mobs.get(i).getStillAni().start(); 
			 if(i == 0) {//If we are updating the player
				 mobs.get(i).getMoveLeftFlippedAni().start();
				 mobs.get(i).getMoveRightFlippedAni().start(); 
				 mobs.get(i).getStandStillFlippedAni().start(); 
			 }
		}
		
	}//end of stopAnimation
	
	private void updateAnimation(int delta) {
		//Animation
		for(int i = 0; i < mobs.size(); i++) {
			 mobs.get(i).getLeftAni().update(delta); // this line makes sure the speed of the Animation is true
			 mobs.get(i).getRightAni().update(delta); // this line makes sure the speed of the Animation is true
			 mobs.get(i).getStillAni().update(delta); // this line makes sure the speed of the Animation is true
			 if(i == 0) {//If we are updating the player
				 mobs.get(i).getMoveLeftFlippedAni().update(delta); // this line makes sure the speed of the Animation is true
				 mobs.get(i).getMoveRightFlippedAni().update(delta); // this line makes sure the speed of the Animation is true
				 mobs.get(i).getStandStillFlippedAni().update(delta); // this line makes sure the speed of the Animation is true
			 }
		}
		
	}//end of updateAnimation

	private void stopAnimation() {
		
		//Stop animation for all mobs
		for(int i = 0; i < mobs.size(); i++) {
			 mobs.get(i).getLeftAni().stop(); 
			 mobs.get(i).getRightAni().stop();
			 mobs.get(i).getStillAni().stop(); 
			 if(i == 0) {//If we are updating the player
				 mobs.get(i).getMoveLeftFlippedAni().stop();
				 mobs.get(i).getMoveRightFlippedAni().stop(); 
				 mobs.get(i).getStandStillFlippedAni().stop(); 
			 }
		}
		
	}//end of stopAnimation
	
	private void checkMobStatus(StateBasedGame sbg){

		for(int i = 0; i < mobs.size(); i++){
			if(mobs.get(i).getHealth() <= 0){
				mobs.get(i).setIsAlive(false);
				if(i == 0)
					resetLevel(sbg);
			}
		}
	}//end of checkMobStatus
	
	/**
	 * Resets the level when the player dies
	 * @param sbg
	 */
	private void resetLevel(StateBasedGame sbg) {
		Main.util.getPlayer().setHealth(100);
		Main.util.getPlayer().getGravPack().setGravpower(100);
		Main.util.getPlayer().setX(450);
		Main.util.getPlayer().setY(1100);
		Main.util.getPlayer().setIsAlive(true);
		sbg.enterState(Main.gameover);
	}//end of resetLevel
	
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
	private void checkMenu(GameContainer gc, StateBasedGame sbg) 
			throws SlickException{
		
		// Create our input object
		Input input = gc.getInput();
		
		pause_menu_ui = new Image("res/gui/pausemenu.png");//Pause menu user interface
		back = new Image("res/gui/buttons/button_back.png");
		
		// Back Button
		// The parameters for checkbounds are the x and y coordinates of the top left of the button and the bottom right of the button
		if(MainMenu.checkBounds( (gc.getWidth()/2) - (back.getWidth()/2) , (gc.getWidth()/2) - (back.getWidth()/2) + back.getWidth() , 400 , 400 + back.getHeight(), xpos, ypos)){
			if(input.isMousePressed(0)){
				input.clearKeyPressedRecord();
				Main.util.getMusic().stop();
				Main.util.setMusic(Main.util.getMusicQueue(0));
				Main.util.getMusic().loop(1f, Main.util.getMusicManager().getVolume());
				startAnimation();
				sbg.enterState(Main.mainmenu);
				menuOpen = false;
			}
			back = new Image("res/gui/buttons/button_back_hover.png");
		}
		
	}//end of checkMenu
	
	/**
	 * Checks for the user input of the escape key to toggle the in-game menu...
	 * @param gc
	 * @param delta
	 */
	private void openMenu(GameContainer gc, int delta){
		Input input = gc.getInput(); // Creating our input object
		if(input.isKeyPressed(Input.KEY_ESCAPE)){
			Main.util.getMusic().resume();
			startAnimation();
			menuOpen = !menuOpen; // ! Makes the escape toggle
		}
	}//end of openMenu
	
	public int getID() {
		return StartLevel.id;
	}//end of getID
	
}//end of class
