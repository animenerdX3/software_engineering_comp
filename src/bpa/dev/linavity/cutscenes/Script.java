package bpa.dev.linavity.cutscenes;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import bpa.dev.linavity.Main;

public class Script {

	private Graphics g;
	private int id;
	private int counter;
	private int sceneLength;
	private int timer;
	
	public Script(Graphics g, int id, int sceneLength){
		this.id = id;
		this.g = g;
		this.counter = 0;
		this.timer = 0;
		this.sceneLength = sceneLength;
	}
	
	//START OF SCRIPT
	
	public void startCutscene(){
		String [] dialog = Main.util.getCutscenes().getDialogue();
		String [] names = Main.util.getCutscenes().getNames();
		
		if(this.id == 1){
			displayName(names);
			displayText(dialog);
		}
		
	}//end of startCutscene
	
	public void displayName(String[] names){
			g.setColor(Color.white);
			g.drawString(names[counter], 74, 662 + Main.util.startLetterBottom);
		
	}
	
	public void displayText(String [] dialog){
		int xPosition = 74;
		int yPosition = 732 + Main.util.startLetterBottom;
			g.setColor(Color.white);
			char [] splitDialog = dialog[counter].toCharArray();
			for(int x = 0; x < splitDialog.length; x++){
				this.timer = this.timer + Main.util.delta;
				if(this.timer >= Main.util.getTextSpeed()){
					System.out.println(splitDialog[x]);
					g.drawString(""+splitDialog[x], xPosition, yPosition);
					xPosition = xPosition + 10;
					this.timer = 0;
				}
			}
	}
	
	//END OF SCRIPT
	
	public int getID(){
		return id;
	}
	
}//end of class
