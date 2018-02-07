package bpa.dev.linavity.cutscenes;

public class CutsceneAccessor {

	private boolean makePlayerJump;
	
	public CutsceneAccessor() {
		this.makePlayerJump = true;
	}

	/* GETTERS */
	public boolean isMakePlayerJump() {
		return makePlayerJump;
	}

	/* SETTERS */
	public void setMakePlayerJump(boolean makePlayerJump) {
		this.makePlayerJump = makePlayerJump;
	}
	
}//end of class
