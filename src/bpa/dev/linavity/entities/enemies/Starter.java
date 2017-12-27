package bpa.dev.linavity.entities.enemies;

import org.newdawn.slick.SlickException;

import bpa.dev.linavity.entities.Mob;
import bpa.dev.linavity.utils.Utils;

public class Starter extends Mob{

	int jumps;
	
	public Starter() throws SlickException{
		super("res/sprites/starter/starter_0.png");
		this.jumps = 0;
	}
	
	public void moveEnemy(int delta) {
		
				this.setX( (int) (getX() + 4));
	}
	
	
}//end of class
