package bpa.dev.linavity.entities.enemies;

import org.newdawn.slick.SlickException;

import bpa.dev.linavity.entities.Mob;
import bpa.dev.linavity.utils.Utils;

public class Starter extends Mob{
	
	//Player and Gravity needed
	Utils util;
	
	//Enemy can jump once
	int jumps;
	
	//Create enemy with constructor, sets the x and y of the enemy
	public Starter(Utils util, int x, int y) throws SlickException{
		super("res/sprites/starter/starter_0.png", x, y);
		this.jumps = 0;
		this.util = util;
	}
	
	/**
	 * Enemy movement AI
	 * @param delta
	 */
	public void moveEnemy(int delta) {
		
		if(util.getPlayer().getX() > getX()) {
			setX(getX() + 2);
		}
		else if(util.getPlayer().getX() < getX()) {
			setX(getX() - 2);
		}
		
	}//end of moveEnemy
	
	public void collidePlayer() {
		//TODO if enemy collides with the player, take some health away from Player
	}//end of collidePlayer
	
}//end of class
