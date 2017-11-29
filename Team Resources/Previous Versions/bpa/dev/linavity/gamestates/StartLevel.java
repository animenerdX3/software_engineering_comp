package bpa.dev.linavity.gamestates;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import bpa.dev.linavity.assets.Keyboard;
import bpa.dev.linavity.entities.Player;
import bpa.dev.linavity.physics.Gravity;

public class StartLevel extends BasicGameState{

	private Image alien = null;
	private Image bg = null;
	private Image back = null;
	
	public static int id = 1;
	private Rectangle square;
	private Rectangle collide;
	public static int x = 350, y = 350;
	public static boolean collides = false;
	
	private boolean menuOpen = false;
	
	private int xpos; // Mouse's X position
	private int ypos; // Mouse's Y position
	
	public StartLevel(){ 
		collide = new Rectangle(350, 350, 50, 50);
		square = new Rectangle(x,y,50,50);
	}
	
	// This runs as soon as we compile the program.
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		back = new Image("data/button_back.png");
		alien = new Image("data/alien.png");
		bg = new Image("data/bg.jpg");
	}

	// Renders content to the game / screen
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		
		bg.draw(0,0);
		g.drawString("X: " + x + " Y: " + y + " collides " + collides, 10,50);
		g.setColor(Color.cyan);
		
		collide = new Rectangle(350, 350, 50, 50);
		square = new Rectangle(x,y,50,50);
		
		//alien.drawWarped(x, y, 100, 100, 200, 200, x+300, y+300);
		
		g.draw(square);
		g.draw(collide);
		alien.draw(x,y);
		
		if(menuOpen){
			renderMenu(gc, g);
		}
		
	}

	// Constant Loop, very fast, loops based on a delta (the amount of time that passes between each instance)
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		
		Input input = gc.getInput(); // Create our input object
		
		xpos = ExtraMouseFunctions.getMouseX(gc.getWidth()); // Updates the x coordinate of the mouse
		ypos = ExtraMouseFunctions.getMouseY(gc.getHeight()); // Updates the y coordinate of the mouse
		
		if(!menuOpen){
			//Movement
			new Keyboard(gc, delta);
			movePlayer(gc, delta);
			runPlayer(gc, delta);

			collide(gc);

			if(square.intersects(collide)){
					collides = true;
				}
				else {
					collides = false;
				}

			Gravity.gravity();
		}
		
		// Open pop-up menu
		openMenu(gc, delta);
		checkMenu(gc, sbg, input);
		
	}
	
	public void collide(GameContainer gc){
			
		//Left border
		if(x <= 0){
			x = 0;
		}
		
		//Right world border
		if(x >= gc.getWidth() - alien.getWidth()){
			x = gc.getWidth() - alien.getWidth();
		}
		
		//Top world border
		if(y <= 0){
			y = 0;
		}
		
		//Bottom world border
		if(y>=gc.getHeight() - alien.getHeight()){
			y = gc.getWidth() - alien.getHeight();
		}
		
		
	}//end of collide
	
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
	
	/**
	 * @method renderMenu
	 * @description draws the images needed for the popup menu
	 * 
	 * @param
	 * GameContainer gc, Graphics g
	 * 
	 * @return
	 * 	void:
	 */
	public void renderMenu(GameContainer gc, Graphics g){
		// Back Button
		g.drawImage(back, (gc.getWidth()/2) - (back.getWidth()/2), 400); // Setting the x value as half of the game container and adjusting for the width of the button
	}
	
	/**
	 * @method checkMenu
	 * @description checks mouse actions for the popup menu
	 * 
	 * @param
	 * GameContainer gc, StateBasedGame sbg, Input input
	 * 
	 * @return
	 * 	void:
	 * @throws SlickException 
	 */
	public void checkMenu(GameContainer gc, StateBasedGame sbg, Input input) 
			throws SlickException{
		
		back = new Image("data/button_back.png");
		
		// Back Button
		// The parameters for checkbounds are the x and y coordinates of the top left of the button and the bottom right of the button
		if(MainMenu.checkBounds( (gc.getWidth()/2) - (back.getWidth()/2) , (gc.getWidth()/2) - (back.getWidth()/2) + back.getWidth() , 400 , 400 + back.getHeight(), xpos, ypos)){
			if(input.isMousePressed(0)){
				input.clearKeyPressedRecord();
				sbg.enterState(0);
				menuOpen = false;
			}
			back = new Image("data/button_back_hover.png");
		}
		
	}
	
	// Checks for the user input of the escape key to toggle the in-game menu...
	public void openMenu(GameContainer gc, int delta){
		Input input = gc.getInput(); // Creating our input object
		if(input.isKeyPressed(Input.KEY_ESCAPE)){
			menuOpen = !menuOpen; // ! Makes the escape toggle
		}
	}
	
	public int getID() {
		// TODO Auto-generated method stub
		return StartLevel.id;
	}
	
}
