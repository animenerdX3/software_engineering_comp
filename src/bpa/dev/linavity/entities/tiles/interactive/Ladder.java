package bpa.dev.linavity.entities.tiles.interactive;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import bpa.dev.linavity.GameObject;
import bpa.dev.linavity.Main;
import bpa.dev.linavity.entities.Player;
import bpa.dev.linavity.entities.tiles.Dynamic;

public class Ladder extends Dynamic {

	private int ladderSpeed = -3;
	
	public Ladder(int i, int j, int id, int xOffset, int yOffset, int width, int height) throws SlickException {
		super(i, j, id, xOffset, yOffset, width, height);
	}
	
	@Override
	public void onCollide(GameObject go) throws SlickException {
		
		Input input = Main.util.gc.getInput();
		
		if(go instanceof Player){ // If the mob colliding with the lever was the player
			
			if(input.isKeyDown(Input.KEY_W) || input.isKeyDown(Input.KEY_UP)) // and the player hits e (interact)
				Main.util.getPlayer().setYMomentum(ladderSpeed);
			
		}
	}
	
	
}

