package bpa.dev.linavity.cutscenes;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import bpa.dev.linavity.utils.ErrorLog;

public class CutsceneAccessor {

	private boolean makePlayerJump;
	private Image [] playerSprites;
	private boolean moveAbric;
	private boolean abricDirection;
	private int ID;
	private int length;
	
	public CutsceneAccessor() {
		this.makePlayerJump = true;
		this.playerSprites = new Image[1];
		try {
			this.playerSprites[0] = new Image("res/sprites/player/calvin_0.png");
		} catch (SlickException e) {
			ErrorLog.displayError(e);
		}
		this.moveAbric = false;
		this.abricDirection = true;
		this.ID = 1;
		this.length = 9;
	}
	
	/* GETTERS */
	public boolean isMakePlayerJump() {
		return makePlayerJump;
	}
	
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

	/* SETTERS */
	public void setMakePlayerJump(boolean makePlayerJump) {
		this.makePlayerJump = makePlayerJump;
	}
	
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
	
}//end of class
