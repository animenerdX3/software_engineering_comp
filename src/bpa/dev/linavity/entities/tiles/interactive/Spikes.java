package bpa.dev.linavity.entities.tiles.interactive;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import bpa.dev.linavity.GameObject;
import bpa.dev.linavity.Main;
import bpa.dev.linavity.entities.Player;
import bpa.dev.linavity.entities.enemies.Starter;
import bpa.dev.linavity.entities.tiles.Dynamic;
import bpa.dev.linavity.entities.tiles.Tile;
import bpa.dev.linavity.events.Message;
import bpa.dev.linavity.gamestates.GameLevel;

public class Spikes extends Dynamic {
	
	private final static double damage = 100;
	
	public Spikes(int i, int j, int id, int xOffset, int yOffset, int width, int height, Tile tileUp, Tile tileDown, Tile tileLeft, Tile tileRight) throws SlickException {
		super(i, j, id, xOffset, yOffset, width, height);
		
		if(tileUp.isPassable() || tileUp.getId() == Tile.spikesID)
			if(!tileDown.isPassable() && tileDown.getId() != Tile.spikesID)
				this.texture = new Image("res/tiles/dynamic/spikes_up.png");
			else if(!tileLeft.isPassable() && tileLeft.getId() != Tile.spikesID)
				this.texture = new Image("res/tiles/dynamic/spikes_left.png");
			else
				this.texture = new Image("res/tiles/dynamic/spikes_right.png");
		
	}
	
	@Override
	public void onCollide(GameObject go) throws SlickException {
		
		System.out.println("This shizzle is running my nizzle");
		
		Main.util.getMessageHandler().addMessage(new Message(go, this, Message.damage, -this.damage));
		
	}
	
}
