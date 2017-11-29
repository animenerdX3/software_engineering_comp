package bpa.dev.linavity.assets;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

import bpa.dev.linavity.physics.Gravity;

public class Keyboard {

	public Keyboard(GameContainer gc, int delta){
		changeGrav(gc, delta);
	}
	
	public static void changeGrav(GameContainer gc, int delta){
		Input input = gc.getInput(); // Creating our input object
		if(input.isKeyPressed(Input.KEY_LCONTROL)){
			Gravity.fall = (-Gravity.fall);
		}
	}
	
}
