package bpa.dev.linavity.entities.tiles.interactive;

import org.newdawn.slick.SlickException;

import bpa.dev.linavity.GameObject;
import bpa.dev.linavity.Main;
import bpa.dev.linavity.entities.Player;
import bpa.dev.linavity.entities.tiles.Dynamic;
import bpa.dev.linavity.gamestates.GameLevel;

public class EventTile extends Dynamic{

	public static int [] nonCutsceneIDs = {1, 2};
	
	public EventTile(int i, int j, int id, int xOffset, int yOffset, int width, int height) throws SlickException {
		super(i, j, id, xOffset, yOffset, width, height);
	}
	
	@Override
	public void onCollide(GameObject go) throws SlickException {
		if(go instanceof Player && !this.toggle){
			checkInstances(this.eventID);
			this.toggle = true;
			Main.util.levelEvents.changeEvent((int) super.y / 50, (int) super.x / 50, true);
		}
	}
	
	public void checkInstances(int id) {
		if(Main.util.levelNum == 1)
			levelOneChecks(id);
		if(Main.util.levelNum == 6)
			levelSixChecks(id);
	}
	
	public void levelOneChecks(int id) {
		if(id == 0) {
			Main.util.cutsceneVars.setLength(9);
			Main.util.setCutsceneActive(true);
		}
	}
	
	public void levelSixChecks(int id) {
		if(id == 0) {
			Main.util.countDialog = 9;
			Main.util.cutsceneVars.setLength(16);
			Main.util.setCutsceneActive(true);
		}
	}
	
}//end of class
