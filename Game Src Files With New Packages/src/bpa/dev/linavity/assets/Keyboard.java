package bpa.dev.linavity.assets;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

import bpa.dev.linavity.physics.Gravity;

public class Keyboard {
	
	private int x, y;
	
	public Keyboard(){
		
	}
	
	public  void changeGrav(GameContainer gc, int delta){
		Input input = gc.getInput(); // Creating our input object
		if(input.isKeyPressed(Input.KEY_LCONTROL)){
			Gravity.setGravity();
		}
	}
	
	public void movePlayer(GameContainer gc, int delta){
		Input input = gc.getInput(); // Creating our input object
		if(input.isKeyDown(Input.KEY_S) || input.isKeyDown(Input.KEY_DOWN)){
			y += 200/1000.0f * delta;

		}
		if(input.isKeyDown(Input.KEY_A) || input.isKeyDown(Input.KEY_LEFT)){
			x -= 200/1000.0f * delta;

		}
		if(input.isKeyDown(Input.KEY_D) || input.isKeyDown(Input.KEY_RIGHT)){
			x += 200/1000.0f * delta;

		}
		if(input.isKeyDown(Input.KEY_W) || input.isKeyDown(Input.KEY_UP)){
			y -= 200/1000.0f * delta;

		}
	}//end of movePlayer
	
	public void runPlayer(GameContainer gc, int delta){
		Input input = gc.getInput(); // Creating our input object
		if((input.isKeyDown(Input.KEY_A) || input.isKeyDown(Input.KEY_LEFT)) && input.isKeyDown(Input.KEY_LSHIFT)){
			x -= (200/1000.0f * delta) * 1.5;

		}
		else if((input.isKeyDown(Input.KEY_D) || input.isKeyDown(Input.KEY_RIGHT)) && input.isKeyDown(Input.KEY_LSHIFT)){
			x += (200/1000.0f * delta) * 1.5;

		}
	}//end of runPlayer

	public void getInputUpdate(GameContainer gc, int delta){
		changeGrav(gc, delta);
		movePlayer(gc, delta);
		runPlayer(gc, delta);
	}
	
	//GETTERS
	
	public int getX() {
		return x;
	}
	
	public  int getY() {
		return y;
	}
	
	//SETTERS
	
	public  void setX(int x) {
		this.x = x;
	}

	public  void setY(int y) {
		this.y = y;
	}

	
	
	
}
