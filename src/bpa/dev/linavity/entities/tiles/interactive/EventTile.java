package bpa.dev.linavity.entities.tiles.interactive;

import org.newdawn.slick.SlickException;

import bpa.dev.linavity.GameObject;
import bpa.dev.linavity.Main;
import bpa.dev.linavity.entities.Player;
import bpa.dev.linavity.entities.tiles.Dynamic;
import bpa.dev.linavity.events.Message;

public class EventTile extends Dynamic{

	public EventTile(int i, int j, int id, int xOffset, int yOffset, int width, int height) throws SlickException {
		super(i, j, id, xOffset, yOffset, width, height);
		
	}
	
	@Override
	public void onCollide(GameObject go) throws SlickException {
		if(go instanceof Player && !this.toggle){
			Main.util.setCutsceneActive(true);
			this.toggle = !this.toggle;
		}
	}
	
}//end of class
