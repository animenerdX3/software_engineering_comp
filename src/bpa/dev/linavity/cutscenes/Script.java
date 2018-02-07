package bpa.dev.linavity.cutscenes;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import bpa.dev.linavity.Main;

public class Script {

	private Graphics g;
	private int id;
	private int sceneLength;
	private int timer;
	private String [] dialog;
	private String [] names;
	
	public Script(Graphics g, int id, int sceneLength){
		this.g = g;
		this.id = id;
		this.timer = 0;
		this.sceneLength = sceneLength;
		this.dialog = Main.util.getCutscenes().getDialogue();
		this.names = Main.util.getCutscenes().getNames();
	}
	
	//START OF SCRIPT
	
	public void displayName(String[] names){
			g.setColor(Color.white);
			g.drawString(names[Main.util.countDialog], 74, 662 + Main.util.startBottom);
	}
	
	public void displayText(String [] dialog){
		int xPosition = 74;
		int yPosition = 732 + Main.util.startBottom;
			g.setColor(Color.white);
			char [] splitDialog = dialog[Main.util.countDialog].toCharArray();
			for(int i = 0; i < splitDialog.length; i++){
				g.drawString(""+splitDialog[i], xPosition, yPosition);
				xPosition = xPosition + 10;
			}
		}
	
	//END OF SCRIPT
	
	public void getEvents(){
		if(Main.util.countDialog == 1){
			//EVENTS GO HERE
		}
	}
	
	public int getID(){
		return id;
	}
	
	/**
	 * @return the sceneLength
	 */
	public int getSceneLength() {
		return sceneLength;
	}

	/**
	 * @return the timer
	 */
	public int getTimer() {
		return timer;
	}

	/**
	 * @return the dialog
	 */
	public String[] getDialog() {
		return dialog;
	}

	/**
	 * @return the names
	 */
	public String[] getNames() {
		return names;
	}

	/**
	 * @param dialog the dialog to set
	 */
	public void setDialog(String[] dialog) {
		this.dialog = dialog;
	}

	/**
	 * @param names the names to set
	 */
	public void setNames(String[] names) {
		this.names = names;
	}

	/**
	 * @param sceneLength the sceneLength to set
	 */
	public void setSceneLength(int sceneLength) {
		this.sceneLength = sceneLength;
	}

	/**
	 * @param timer the timer to set
	 */
	public void setTimer(int timer) {
		this.timer = timer;
	}
	
}//end of class
