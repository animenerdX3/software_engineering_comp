package bpa.dev.linavity.entities.tiles.interactive;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import bpa.dev.linavity.GameObject;
import bpa.dev.linavity.Main;
import bpa.dev.linavity.entities.Player;
import bpa.dev.linavity.entities.enemies.Starter;
import bpa.dev.linavity.entities.tiles.Dynamic;
import bpa.dev.linavity.events.Message;
import bpa.dev.linavity.gamestates.GameLevel;

public class Door extends Dynamic {
	
	
	public Door(int i, int j, int id, int xOffset, int yOffset, int width, int height) throws SlickException {
		super(i, j, id, xOffset, yOffset, width, height);
	}
	
	@Override
	public void onCollide(GameObject go) throws SlickException {

		
	}
	
	@Override
	public void onMessage(Message message) throws SlickException {
		
	}
	
	public void toggleOn() throws SlickException {
		this.setTexture(new Image("res/tiles/static/Lever_On.png"));
		this.toggle = !this.toggle;
		System.out.println("ON");
		
	}
	
	public void toggleOff() throws SlickException {
		this.setTexture(new Image("res/tiles/static/Lever_Off.png"));
		this.toggle = !this.toggle;
		System.out.println("OFF");
	}
	
}
