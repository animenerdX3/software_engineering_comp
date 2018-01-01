package bpa.dev.linavity.entities.enemies;

import org.newdawn.slick.Image;
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
		super.setDamage(0.25);
	}
	
	/**
	 * Enemy movement AI
	 * @param delta
	 */
	public void moveEnemy(int delta) {
		
		collidePlayer();
		
		if(util.getPlayer().getX() > getX()) {
			setX(getX() + 1);
			try {
				setMobImage(new Image("res/sprites/starter/starter_1.png"));
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
		else if(util.getPlayer().getX() < getX()) {
			setX(getX() - 1);
			try {
				setMobImage(new Image("res/sprites/starter/starter_0.png"));
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
		
	}//end of moveEnemy
	
	public void collidePlayer() {
		
		if(util.getPlayer().getX() >= getX() && util.getPlayer().getX() <= getX() + getMobImage().getWidth()) {
			if(util.getPlayer().getY() >= getY() - (getMobImage().getHeight() / 2)) {
				System.out.println("Damage Taken");
				util.getPlayer().setHealth(util.getPlayer().getHealth() - getDamage());
				System.out.println("HEALTH: "+util.getPlayer().getHealth());
			}
		}
		
	}//end of collidePlayer
	
}//end of class
