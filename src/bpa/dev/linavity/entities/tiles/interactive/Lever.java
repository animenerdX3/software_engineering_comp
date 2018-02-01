package bpa.dev.linavity.entities.tiles.interactive;

import org.newdawn.slick.SlickException;

import bpa.dev.linavity.Main;
import bpa.dev.linavity.entities.enemies.Starter;
import bpa.dev.linavity.gamestates.GameLevel;

public class Lever {
	
	private boolean toggle;
	private int id;
	
	public Lever() {
		this.toggle = false;
		this.id = 0;
	}
	
	public void callFunction() {
		if(this.toggle)
			toggleOn();
		else
			toggleOff();
	}
	
	public void toggleOn() {
		if(this.id == 0)
			System.out.println("ON");
		else if(this.id == 1) {
			try {
				if(Main.currentLevel.getCurrentStateID() == 1) 
					GameLevel.mobs.add(new Starter(400, 1100));
				
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void toggleOff() {
		System.out.println("OFF");
	}
	
	public boolean getToggle() {
		return toggle;
	}
	
	public int getID() {
		return id;
	}
	
	public void setToggle(boolean toggle) {
		this.toggle = toggle;
	}
	
	public void setID(int id) {
		this.id = id;
	}
	
}
