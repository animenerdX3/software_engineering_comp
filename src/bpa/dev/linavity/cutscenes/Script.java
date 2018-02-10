package bpa.dev.linavity.cutscenes;

import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;

import bpa.dev.linavity.Main;

public class Script {

	@SuppressWarnings("unused")
	private Graphics g;
	private int id;
	private int sceneLength;
	private int timer;
	private String [] dialog;
	private String [] names;
	private Font font = new Font("OCR A Extended", Font.ITALIC, 24);
	private TrueTypeFont ttf = new TrueTypeFont(font, true);
	
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
		ttf.drawString(74, 662 + Main.util.startBottom, names[Main.util.countDialog], Color.white);
			//g.drawString(names[Main.util.countDialog], 74, 662 + Main.util.startBottom);
	}
	
	public void displayText(String [] dialog){
		int xPosition = 74;
		int yPosition = 732 + Main.util.startBottom;
		int textCounter = 0;
			char [] splitDialog = dialog[Main.util.countDialog].toCharArray();
			for(int i = 0; i < splitDialog.length; i++){
				ttf.drawString(xPosition, yPosition,""+splitDialog[i], Color.white);
				xPosition = xPosition + 15;
				textCounter++;
				if(textCounter > 35 && splitDialog[i] == 32){
					yPosition = yPosition + 40;
					xPosition = 74;
					textCounter = 0;
				}
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
