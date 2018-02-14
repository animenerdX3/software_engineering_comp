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
		if(id == 0) {
			Main.util.setCutsceneActive(true);
		}
		if(id == 1) {
			GameLevel.tutorialScene.setTutorial(GameLevel.tutorialGUI[3]);
			GameLevel.tutorialScene.setActive(true);
			GameLevel.tutorialScene.setTimer(0);
			Main.util.cutsceneVars.setID(Main.util.cutsceneVars.getID() + 1);
		}
		if(id == 2) {
			GameLevel.tutorialScene.setTutorial(GameLevel.tutorialGUI[2]);
			GameLevel.tutorialScene.setActive(true);
			GameLevel.tutorialScene.setTimer(0);
			Main.util.cutsceneVars.setID(Main.util.cutsceneVars.getID() + 1);
		}
		if(id == 3) {
			GameLevel.tutorialScene.setTutorial(GameLevel.tutorialGUI[1]);
			GameLevel.tutorialScene.setActive(true);
			GameLevel.tutorialScene.setTimer(0);
			Main.util.cutsceneVars.setID(Main.util.cutsceneVars.getID() + 1);			
		}
	}
	
}//end of class
