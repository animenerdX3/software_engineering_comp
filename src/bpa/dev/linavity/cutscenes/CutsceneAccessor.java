package bpa.dev.linavity.cutscenes;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import bpa.dev.linavity.utils.ErrorLog;

public class CutsceneAccessor {

	private boolean makePlayerJump;
	private Image [] playerSprites;
	private boolean moveAbric;
	private boolean abricDirection;
	
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
	
}//end of class
