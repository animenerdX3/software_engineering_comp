package bpa.dev.linavity;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import bpa.dev.linavity.gamestates.StartLevel;
import bpa.dev.linavity.gamestates.MainMenu;

/**
 *  Linavity
 * @author Peter Gomes, Yannick Almeida, Ethan Guillotte, Chris Furtado
 * @version 1.01
 */

/* Task List
 //TODO Change Grav Does Not Work - FIX IT!!!
 //TODO Work on Block Collision
 //TODO Let's Make Maps >///3///<
 */

public class Main extends StateBasedGame{

	// Game State Identifier
	public static final int mainmenu = 0;
	public static final int basicgame = 1;
	
	// Application Properties
	public static final int WIDTH = 900;
	public static final int HEIGHT = 900;
	public static final int FPS = 60;
	
	// Class Constructor
	public Main(String name) {
		super(name);
		this.addState(new MainMenu());
		this.addState(new StartLevel());
	}

	public void initStatesList(GameContainer gc) throws SlickException {
		this.getState(mainmenu).init(gc, this);
		this.getState(basicgame).init(gc , this);
		this.enterState(mainmenu);
	}
	
	// Main Method
	public static void main(String[] args){
		AppGameContainer appgc;
		try {
			appgc = new AppGameContainer(new Main("Linavity - Version 1.01"));
			appgc.setDisplayMode(WIDTH, HEIGHT, false);
			appgc.setAlwaysRender(true); // Constant Rendering 
			appgc.setTargetFrameRate(FPS);
			appgc.start();
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
