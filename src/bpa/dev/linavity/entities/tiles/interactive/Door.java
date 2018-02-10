package bpa.dev.linavity.entities.tiles.interactive;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import bpa.dev.linavity.GameObject;
import bpa.dev.linavity.Main;
import bpa.dev.linavity.collectibles.KeyCard;
import bpa.dev.linavity.entities.Player;
import bpa.dev.linavity.entities.tiles.Dynamic;
import bpa.dev.linavity.entities.tiles.Tile;
import bpa.dev.linavity.events.Message;

public class Door extends Dynamic {
	
	private boolean typeDoor;
	
	public Door(int i, int j, int id, int xOffset, int yOffset, int width, int height, Tile tileUp, Tile tileDown, Tile tileLeft, Tile tileRight) throws SlickException {
		super(i, j, id, xOffset, yOffset, width, height);
		
		typeDoor = true;
		
		if(tileUp.isPassable() && !tileLeft.isPassable())
			typeDoor = false;
		
		if(!typeDoor)
			this.texture = new Image("res/tiles/dynamic/trapdoor_closed.png");
	}
	
	@Override
	public void onCollide(GameObject go) throws SlickException {
		
		if(go instanceof Player && !this.toggle){
			for(int i = 0; i < Main.util.getInventory().getItems().size(); i++){
				if(Main.util.getInventory().getItems().get(i) instanceof KeyCard){
					openDoor();
					Main.util.getInventory().getItems().remove(i);
				}
			}
		}
		
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
		
		if(typeDoor)
			this.setTexture(new Image("res/tiles/dynamic/door_open.png"));
		else
			this.setTexture(new Image("res/tiles/dynamic/trapdoor_open.png"));
		
		this.setPassable(true);
		this.toggle = true;
		System.out.println("Open Door");
		
	}
	
	public void closeDoor() throws SlickException {
		
		if(typeDoor)
			this.setTexture(new Image("res/tiles/dynamic/door_closed.png"));
		else
			this.setTexture(new Image("res/tiles/dynamic/trapdoor_closed.png"));
		
		this.setPassable(false);
		this.toggle = false;
		System.out.println("Close Door");
	}
	
}
