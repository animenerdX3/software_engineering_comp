package bpa.dev.linavity.entities.enemies;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import bpa.dev.linavity.Main;
import bpa.dev.linavity.entities.Mob;
import bpa.dev.linavity.utils.Utils;

public class Starter extends Mob{
	
	//Enemy can jump once
	int jumps;
	
	//Create enemy with constructor, sets the x and y of the enemy
	public Starter(int x, int y) throws SlickException{
		super("res/sprites/starter/starter_sheet.png", x, y);
		this.jumps = 0;
		super.setDamage(0.25);
		super.setHealth(100);
	}
	
	/**
	 * Enemy movement AI
	 * @param delta
	 */
	public void enemyUpdates(int delta) {
		
		collidePlayer();
		

		if(Main.util.getPlayer().getCurrentProjectile() != null)
			collideProjectile();
		
		if(Main.util.getPlayer().getX() > getX()) {
			setX(getX() + 1);
			try {
				setMobImage(new Image("res/sprites/starter/starter_1.png"));
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
		else if(Main.util.getPlayer().getX() < getX()) {
			setX(getX() - 1);
			try {
				setMobImage(new Image("res/sprites/starter/starter_0.png"));
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
		
	}//end of moveEnemy
	
	public void collidePlayer() {
		
		if(Main.util.getPlayer().getX() >= getX() && Main.util.getPlayer().getX() <= getX() + getMobImage().getWidth()) {
			if(Main.util.getPlayer().getY() >= getY() - (getMobImage().getHeight() / 2)) {
				if(Main.util.getPlayer().getY() <= getY() + getMobImage().getHeight()){
					System.out.println("Damage Taken");
					Main.util.getPlayer().setHealth(Main.util.getPlayer().getHealth() - getDamage());
					System.out.println("HEALTH: "+ Main.util.getPlayer().getHealth());
				}
			}
		}
		
	}//end of collidePlayer
	
	public void collideProjectile(){
		if(Main.util.getPlayer().getCurrentProjectile().getX() >= getX() && Main.util.getPlayer().getCurrentProjectile().getX() <= getX() + getMobImage().getWidth()){
			if(Main.util.getPlayer().getCurrentProjectile().getY() >= getY() - (getMobImage().getHeight() / 2)){
				System.out.println("ENEMY HIT");
				setHealth(getHealth() - Main.util.getPlayer().getCurrentProjectile().getDamage());
				System.out.println("ENEMY HEALTH: "+getHealth());
			}
		}
	}//end of collideProjectile
	
}//end of class
