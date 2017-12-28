package bpa.dev.linavity.entities.enemies;

import org.newdawn.slick.SlickException;

import bpa.dev.linavity.entities.Mob;
import bpa.dev.linavity.utils.Utils;

public class Starter extends Mob{
	
	Utils util;
	
	int jumps;
	
	public Starter(Utils util, int x, int y) throws SlickException{
		super("res/sprites/starter/starter_0.png", x, y);
		this.jumps = 0;
		this.util = util;
	}
	
	public void moveEnemy(int delta) {
		
		collidePlayer();
	}
	
	public void collidePlayer() {
		if(util.getPlayer().getX() < getX()) {
			System.out.println("AHHHHHHHHHHH");
		}
	}
	
	
}//end of class
