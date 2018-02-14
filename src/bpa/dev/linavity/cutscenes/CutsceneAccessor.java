package bpa.dev.linavity.cutscenes;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import bpa.dev.linavity.utils.ErrorLog;

public class CutsceneAccessor {

	private Image [] playerSprites;
	private boolean moveAbric;
	private boolean abricDirection;
	private int ID;
	private int levelStartID;
	private int length;
	
	public CutsceneAccessor() {
		this.playerSprites = new Image[1];
		try {
			this.playerSprites[0] = new Image("res/sprites/player/calvin_0.png");
		} catch (SlickException e) {
			ErrorLog.displayError(e);
		}
		this.moveAbric = false;
		this.abricDirection = true;
		this.ID = 0;
		this.levelStartID = 0;
		this.length = 9;
	}
	
	/* GETTERS */
	
	public Image[] getPlayerSprites() {
		return playerSprites;
	}

	public boolean isMoveAbric() {
		return moveAbric;
	}

	public boolean isAbricDirection() {
		return abricDirection;
	}

	public int getID() {
		return ID;
	}
	
	public int getLength() {
		return length;
	}

	public int getLevelStartID() {
		return levelStartID;
	}
	
	/* SETTERS */

	public void setPlayerSprites(Image[] playerSprites) {
		this.playerSprites = playerSprites;
	}
	
	public void setMoveAbric(boolean moveAbric) {
		this.moveAbric = moveAbric;
	}

	public void setAbricDirection(boolean abricDirection) {
		this.abricDirection = abricDirection;
	}
	
	public void setID(int id) {
		this.ID = id;
	}
	
	public void setLength(int length) {
		this.length = length;
	}
	
	public void setLevelStartID(int levelStartID) {
		this.levelStartID = levelStartID;
	}
	
}//end of class
