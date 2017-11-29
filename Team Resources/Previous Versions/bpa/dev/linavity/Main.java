package bpa.dev.linavity;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import bpa.dev.linavity.gamestates.StartLevel;
import bpa.dev.linavity.gamestates.MainMenu;

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
			appgc = new AppGameContainer(new Main("Coolest SciFi Platformer Alien Megatastic Palooza of Escape Guy Does Escape Things 100"));
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
