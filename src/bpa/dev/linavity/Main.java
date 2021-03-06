package bpa.dev.linavity;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import bpa.dev.linavity.utils.ErrorLog;
import bpa.dev.linavity.utils.LogSystem;
import bpa.dev.linavity.utils.RuntimeLog;
import bpa.dev.linavity.utils.Utils;
import bpa.dev.linavity.gamestates.*;

/**
 *  Linavity
 * @author Peter Gomes, Yannick Almeida, Ethan Guillotte, Chris Furtado
 * @version 1.06
 */

public class Main extends StateBasedGame{
	
	//Game Version
	public static final double version = 1.06;
	
	// Game State Identifiers
	public static final int mainmenu = 0;
	public static final int startlevel = 1;
	public static final int gameover = 2;
	public static final int gameEnd = 3;
	
	// Application Properties
	public static AppGameContainer appgc;
	public static StateBasedGame currentLevel;
	public static final int WIDTH = 900;
	public static final int HEIGHT = 900;
	public static final int FPS = 60;
	public static final String [] icons = new String[] {"res/gui/icon_32_32.png", "res/gui/icon_24_24.png", "res/gui/icon_16_16.png"};
	
	// Utility Object - Gives access to other objects that are needed throughout the entire program
	public static Utils util;
	
	// Class Constructor
	public Main(String name) {
		//Sets the name of our window
		super(name);
		//Add our states to the game
		this.addState(new MainMenu());
		this.addState(new GameLevel());
		this.addState(new GameOver());
		this.addState(new GameEnd());
	}

	/**
	 * set data and enter our first gamestate
	 */
	public void initStatesList(GameContainer gc) throws SlickException {
		LogSystem.addToLog("Starting Program...");
		RuntimeLog.startUpInfo();
		//Set universal data
		util = new Utils(1);
		LogSystem.addToLog("Utility Class Created.");
		currentLevel = this;
		appgc.setShowFPS(util.debugMode);//Show FPS Counter
		
		//Enter the main menu 
		this.enterState(mainmenu);
	}//end of initStatesList
	
	/**
	 * the main method: runs the whole program
	 * @param args
	 */
	public static void main(String[] args){
		try {
			appgc = new AppGameContainer(new Main("Linavity - Version "+version));//Creates our game container
			appgc.setDisplayMode(WIDTH, HEIGHT, false);//Set size of our window
			appgc.setAlwaysRender(true); // Constant Rendering 
			appgc.setTargetFrameRate(FPS);//Set framerate
			appgc.setIcons(icons);//Set icons
			appgc.start();//Start the program
		} catch (SlickException e) {
			LogSystem.addToLog("ERROR FOUND! Terminating Program...");
			ErrorLog.logError(e);//Write to errorlog file
		}
	}//end of main

}//end of class
