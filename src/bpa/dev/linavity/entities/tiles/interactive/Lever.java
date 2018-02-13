package bpa.dev.linavity.entities.tiles.interactive;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import bpa.dev.linavity.GameObject;
import bpa.dev.linavity.Main;
import bpa.dev.linavity.entities.Player;
import bpa.dev.linavity.entities.tiles.Dynamic;
import bpa.dev.linavity.events.EventData;
import bpa.dev.linavity.events.Message;

public class Lever extends Dynamic {
	
	public Lever(int i, int j, int id, int xOffset, int yOffset, int width, int height) throws SlickException {
		super(i, j, id, xOffset, yOffset, width, height);
	}
	
	@Override
	public void onCollide(GameObject go) throws SlickException {
		
		if(go instanceof Player){ // If the mob colliding with the lever was the player
			
			if(Main.util.getKeyLogSpecificKey(8)){ // and the player hits e (interact)
				
				if(this.toggle){ // If the lever was on
					toggleOff();
				}else{
					toggleOn();
				}
			}
		}
	}
	
	public void toggleOn() throws SlickException {
		this.setTexture(new Image("res/tiles/dynamic/Lever_On.png"));
		this.toggle = !this.toggle;
		System.out.println("ON");
		for(int i = 0; i < this.targetObjects.length; i++) {
			Main.util.getMessageHandler().addMessage(new Message(Main.util.getLevel().getSingleEventTile(targetObjects[i]), this, Message.leverToggle, this.toggle));
		};
		Main.util.getSFX(13).play(1f, Main.util.getSoundManager().getVolume());
		Main.util.levelEvents.changeEvent((int) super.y / 50, (int) super.x / 50, true);
	}
	
	public void toggleOff() throws SlickException {
		this.setTexture(new Image("res/tiles/dynamic/Lever_Off.png"));
		this.toggle = !this.toggle;
		for(int i = 0; i < this.targetObjects.length; i++) {
			Main.util.getMessageHandler().addMessage(new Message(Main.util.getLevel().getSingleEventTile(targetObjects[i]), this, Message.leverToggle, this.toggle));
		}
		Main.util.getSFX(14).play(1f, Main.util.getSoundManager().getVolume());
		Main.util.levelEvents.changeEvent((int) super.y / 50, (int) super.x / 50, false);
	}
	
}//end of class
