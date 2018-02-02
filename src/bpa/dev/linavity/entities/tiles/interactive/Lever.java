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

public class Lever extends Dynamic {
	
	
	public Lever(int i, int j, int id, int xOffset, int yOffset, int width, int height) throws SlickException {
		super(i, j, id, xOffset, yOffset, width, height);
	}
	
	@Override
	public void onCollide(GameObject go) throws SlickException {

		System.out.println("LEVER COLLIDE");
		
		if(go instanceof Player){ // If the mob colliding with the lever was the player
			System.out.println("INSTANCE OF PLAYER");
			if(Main.util.getKeyLogSpecificKey(8)){ // and the player hits e (interact)
				System.out.println("E GETS PRESSED");
				if(this.toggle){ // If the lever was on
					toggleOff();
				}else{
					toggleOn();
				}
			}
		}
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
