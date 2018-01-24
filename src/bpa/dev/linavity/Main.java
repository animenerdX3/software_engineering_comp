package bpa.dev.linavity;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import bpa.dev.linavity.utils.ErrorLog;
import bpa.dev.linavity.utils.Utils;
import bpa.dev.linavity.gamestates.*;

/**
 *  Linavity
 * @author Peter Gomes, Yannick Almeida, Ethan Guillotte, Chris Furtado
 * @version 1.03
 */

/* Task List
 //TODO Let's Make Maps >///3///<
 */

public class Main extends StateBasedGame{

	// Game State Identifier
	public static final int mainmenu = 0;
	public static final int basicgame = 1;
	public static final int gameover = 2;
	
	// Application Properties
	public static AppGameContainer appgc;
	public static final int WIDTH = 900;
	public static final int HEIGHT = 900;
	public static final int FPS = 60;
	
	// Utility Object - Gives access to other objects that are needed throughout the entire program.
	public static Utils util;
	
	// Class Constructor
	public Main(String name) {
		super(name);
		this.addState(new MainMenu());
		this.addState(new StartLevel());
		this.addState(new GameOver());
	}

	// Initiate our game states
	public void initStatesList(GameContainer gc) throws SlickException {
		util = new Utils();
		this.getState(mainmenu).init(gc, this);
		this.getState(basicgame).init(gc , this);
		this.getState(gameover).init(gc , this);
			this.enterState(mainmenu);
	}
	
	// Main Method
	public static void main(String[] args){
		try {
			appgc = new AppGameContainer(new Main("Linavity - Version 1.04"));
			appgc.setDisplayMode(WIDTH, HEIGHT, false);
			appgc.setAlwaysRender(true); // Constant Rendering 
			appgc.setShowFPS(true);//Show FPS Counter
			appgc.setTargetFrameRate(FPS);
			appgc.start();
		} catch (SlickException e) {
			ErrorLog.logError(e);
		}
	}

}//end of class
