package bpa.dev.linavity.cutscenes;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class Tutorial {

	private Image tutorial;
	private boolean isActive;
	private int timer;
	
	public Tutorial() {
		this.isActive = false;
		this.timer = 0;
	}
	
	public void update(Graphics g, int delta) {
		if(isActive) {
			g.drawImage(tutorial, 0, 0);
			if(this.timer < 3000) {
				this.timer = this.timer + delta;
			}
			else {
				this.timer = 0;
				this.isActive = false;
			}
		}
	}//end of update

	public Image getTutorial() {
		return tutorial;
	}

	public boolean isActive() {
		return isActive;
	}

	public int getTimer() {
		return timer;
	}

	public void setTutorial(Image tutorial) {
		this.tutorial = tutorial;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	public void setTimer(int timer) {
		this.timer = timer;
	}
	
}//end of class
