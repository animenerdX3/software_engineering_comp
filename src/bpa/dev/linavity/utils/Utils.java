package bpa.dev.linavity.utils;

import org.newdawn.slick.SlickException;

import bpa.dev.linavity.entities.Player;
import bpa.dev.linavity.physics.Gravity;

public class Utils {

	/*
	 * This class is used to store objects that will be used throughout all of the gamestates
	 */
	
	public final boolean debugMode = true;
	
	private Player player; // Player Object
	private Gravity gravity; // Gravity Object
	
	public Utils() throws SlickException{
		this.player = new Player();
		this.gravity = new Gravity();
	}

	public Player getPlayer() {
		return player;
	}

	public Gravity getGravity() {
		return gravity;
	}
	
	
}//end of class
