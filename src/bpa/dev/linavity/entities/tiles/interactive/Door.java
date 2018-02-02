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
		
		System.out.println("Door on Message");
		
		// Interaction with a lever
		if(message.getType() == Message.leverToggle) {
			if((boolean)message.getData()) { // If the lever is on...
				openDoor();
			}else{ // The lever is off...
				closeDoor();
			}
		}
		
	}
	
	public void openDoor() throws SlickException {
		this.setTexture(new Image("res/tiles/dynamic/door_open.png"));
		this.setPassable(true);
		System.out.println("Open Door");
		
	}
	
	public void closeDoor() throws SlickException {
		this.setTexture(new Image("res/tiles/dynamic/door_closed.png"));
		this.setPassable(false);
		System.out.println("Close Door");
	}
	
}
