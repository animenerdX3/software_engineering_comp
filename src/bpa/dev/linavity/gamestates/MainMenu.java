package bpa.dev.linavity.gamestates;


import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import bpa.dev.linavity.Main;
import bpa.dev.linavity.assets.ExtraMouseFunctions;


public class MainMenu extends BasicGameState{
	
	// Gamestate ID (0) <-- Main Menu
	public static int id = 0;
	
	// The X & Y positions of the mouse
	public int prevXpos, prevYpos;
	public int xpos, ypos;
	
	// Our images
	private Image bg = null;
	private Image play = null;
	private Image exit = null;
	private Image emptySlider = null;
	private Image musicSlider = null;
	private Image sfxSlider = null;
	private Image options = null;
	private Image optionsBG = null;
	private Image plusMusic = null;
	private Image minusMusic = null;
	private Image plusSFX = null;
	private Image minusSFX = null;
	private Image back = null;
	
	private boolean isOption = false;
	
	//Menu Controllers
	private int menuSize;
	private int currentSelection;
	
	// This runs as soon as we compile the program.
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		
		// Initialize our image objects
		bg = new Image("res/titlescreen.jpg"); // Menu Background
		
		// Main Menu Buttons
		play = new Image("res/gui/buttons/button_play.png"); // Play Button
		exit = new Image("res/gui/buttons/button_exit.png"); // Exit Button
		options = new Image("res/gui/buttons/button_options.png"); // Options Button
		optionsBG = new Image("res/gui/options_bg.png"); //Options Background
		
		//Option Image Objects
		emptySlider = new Image("res/gui/stats/empty_slider.png");
		musicSlider = new Image("res/gui/stats/music_slider.png");
		sfxSlider = new Image("res/gui/stats/sfx_slider.png");
		
		// Option buttons
		plusMusic = new Image("res/gui/buttons/button_plus.png"); // Plus Button	
		minusMusic = new Image("res/gui/buttons/button_minus.png"); // Minus Button
		plusSFX = new Image("res/gui/buttons/button_plus.png"); // Plus Button	
		minusSFX = new Image("res/gui/buttons/button_minus.png"); // Minus Button	
		back = new Image("res/gui/buttons/button_back.png"); // Back Button	

		menuSize = 3;
		currentSelection = -1;
		Main.util.setMusic(Main.util.getMusicQueue(0));
		Main.util.getMusic().loop(1f, Main.util.getMusicManager().getVolume());
	}

	// Renders content to the game / screen
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		
		// DRAW OUR MENU UI //
		
		// Background Image
		g.drawImage(bg, 0, 0);
		if(Main.util.debugMode)
			g.drawString("XPOS: " + xpos + " | YPOS: " + ypos, 10, 30); // Draw our mouse position for debugging purposes. 
		
		// Button Rendering
		if(!isOption){ 
			renderMainMenuScreenMain(gc, g); // If the option menu has not been selected, render the main section of the menu
		}else{ 
			renderMainMenuScreenOptions(gc, g); // If the option menu is selected, render the options section of the menu
		}
		
		// END OUR MENU UI //

	}

	// Constant Loop, very fast, loops based on a delta (the amount of time that passes between each instance)
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		
		// !NOTE! the mouse coordinates start from the bottom left of the screen
		// this is different from the shapes that are drawn based on coordinates going from the top right of the screen.
		// Because of this difference, we've created our own mouse functions that take into account the game container
		// dimensions, to give us a proper X and Y value that line up with the coordinates of all of our other objects.
		
		Input input = gc.getInput(); // Create our input object
		
		prevXpos = xpos;
		prevYpos = ypos;
		xpos = ExtraMouseFunctions.getMouseX(gc.getWidth()); // Updates the x coordinate of the mouse
		ypos = ExtraMouseFunctions.getMouseY(gc.getHeight()); // Updates the y coordinate of the mouse

		
		// Check User Input //
		
		if(!isOption){
			mainButtonAction(gc, sbg, input); // If the option menu has not been selected, check for the main section's buttons
			cycleSelection(input);//Check for key input
			checkMouseBounds();//Check to see if the mouse is moving
		}else
			optionButtonAction(gc, sbg, input); // If the option menu is selected, check for the option section's buttons
		
	}//end of update


	/**
	 * @method renderMainMenuScreenMain
	 * @description draws the images needed for the main screen of the main menu
	 * 
	 * @param
	 * 	GameContainer gc, Graphics g
	 * 
	 * @return
	 * 	void:
	 */
	public void renderMainMenuScreenMain(GameContainer gc, Graphics g){
		// Play Button 
		g.drawImage(play, (gc.getWidth()/2) - (play.getWidth()/2), 300); // Setting the x value as half of the game container and adjusting for the width of the button
		
		// Options Button
		g.drawImage(options, (gc.getWidth()/2) - (play.getWidth()/2), 400); // Setting the x value as half of the game container and adjusting for the width of the button
		
		// Exit Button
		g.drawImage(exit, (gc.getWidth()/2) - (play.getWidth()/2), 500); // Setting the x value as half of the game container and adjusting for the width of the button
	}
	

	private void cycleSelection(Input input) {
		if(input.isKeyPressed(Input.KEY_S) || input.isKeyPressed(Input.KEY_DOWN)) {
			if(currentSelection + 1 == menuSize)
				currentSelection = 0;
			else
				currentSelection = currentSelection + 1;
		}
		else if(input.isKeyPressed(Input.KEY_W) || input.isKeyPressed(Input.KEY_UP)) {
			if(currentSelection - 1 < 0)
				currentSelection = menuSize - 1;
			else
				currentSelection = currentSelection - 1;
		}
	}//end of cycleSelection
	
	private void checkMouseBounds() {
		int buffer = 1;
		boolean checkLeftPixels = prevXpos <= xpos - buffer;
		boolean checkRightPixels = prevXpos >= xpos + buffer;
		boolean checkTopPixels = prevYpos <= ypos - buffer;
		boolean checkBottomPixels = prevYpos >= ypos + buffer;
		if(checkLeftPixels || checkRightPixels && checkTopPixels || checkBottomPixels)
			currentSelection = -1;
	}//end of checkMouseBounds
	
	/**
	 * @method mainButtonAction
	 * @description checks the button detection and handles the according events in the main section of our main menu
	 * 
	 * @param
	 * GameContainer gc
	 * 
	 * @return
	 * 	void:
	 * @throws SlickException 
	 */
	public void mainButtonAction(GameContainer gc, StateBasedGame sbg, Input input) 
			throws SlickException{
		
		play = new Image("res/gui/buttons/button_play.png");
		options = new Image("res/gui/buttons/button_options.png");
		exit = new Image("res/gui/buttons/button_exit.png");

		// Play Button
		// The parameters for checkbounds are the x and y coordinates of the top left of the button and the bottom right of the button
		if(checkBounds( (gc.getWidth()/2) - (play.getWidth()/2) , (gc.getWidth()/2) - (play.getWidth()/2) + play.getWidth() , 300 , 300 + play.getHeight())) {
			if(input.isMousePressed(0))	
				MainMenuButton(input, sbg);

			play = new Image("res/gui/buttons/button_play_hover.png");
		}
		else if(currentSelection == 0) {
			if(input.isKeyPressed(Input.KEY_ENTER)){
				MainMenuButton(input, sbg);
			}
			play = new Image("res/gui/buttons/button_play_hover.png");
		}
		
		// Options Button
		// The parameters for checkbounds are the x and y coordinates of the top left of the button and the bottom right of the button
		if(checkBounds( (gc.getWidth()/2) - (options.getWidth()/2) , (gc.getWidth()/2) - (options.getWidth()/2) + options.getWidth() , 400 , 400 + options.getHeight())){
			if(input.isMousePressed(0))
				OptionButton();
			
			options = new Image("res/gui/buttons/button_options_hover.png");
		}
		else if(currentSelection == 1) {
			if(input.isKeyPressed(Input.KEY_ENTER)){
				OptionButton();
			}
			options = new Image("res/gui/buttons/button_options_hover.png");
		}
		
		// Exit Button
		// The parameters for checkbounds are the x and y coordinates of the top left of the button and the bottom right of the button
		if(checkBounds( (gc.getWidth()/2) - (exit.getWidth()/2) , (gc.getWidth()/2) - (exit.getWidth()/2) + exit.getWidth() , 500 , 500 + exit.getHeight())){
			if(input.isMousePressed(0))
				ExitButton();

			exit = new Image("res/gui/buttons/button_exit_hover.png");
		}
		else if(currentSelection == 2) {
			if(input.isKeyPressed(Input.KEY_ENTER)){
				ExitButton();
			}
			exit = new Image("res/gui/buttons/button_exit_hover.png");
		}
		
	}//end of mainButtonAction
	
	private void MainMenuButton(Input input, StateBasedGame sbg) {
		input.clearKeyPressedRecord();
		sbg.enterState(Main.startlevel);
		Main.util.getMusic().stop();
		Main.util.setMusic(Main.util.getMusicQueue(1));
		Main.util.getMusic().loop(1f, Main.util.getMusicManager().getVolume());
	}
	
	private void OptionButton() {
		isOption = true;
	}
	
	private void ExitButton() {
		System.exit(0);
	}
	
	/**
	 * @method renderMainMenuScreenOptions
	 * @description draws the images needed for the main screen options of the main menu
	 * 
	 * @param
	 * GameContainer gc, Graphics g
	 * 
	 * @return
	 * 	void:
	 */
	public void renderMainMenuScreenOptions(GameContainer gc, Graphics g){

		//Background Image
		g.drawImage(optionsBG, 0,0);
		
		//Music Options
		g.drawImage(emptySlider, 448, 445);
		musicSlider.draw(448,445,(float) (448+((Main.util.getMusicManager().getVolume() * 100) * 3.77)),445+35,0,0,(float) ((Main.util.getMusicManager().getVolume() * 100) * 3.77),35);
		g.drawImage(plusMusic, 545, 485);
		g.drawImage(minusMusic, 662, 485);
		
		//SFX Options
		g.drawImage(emptySlider, 448, 445 + 135);
		sfxSlider.draw(448,445 + 135,(float) (448+((Main.util.getSoundManager().getVolume() * 100) * 3.77)),(445+135)+35,0,0,(float) ((Main.util.getSoundManager().getVolume() * 100) * 3.77),35);
		g.drawImage(plusSFX, 545, 485 + 135);
		g.drawImage(minusSFX, 662, 485 + 135);

		// Back Button
		g.drawImage(back, (gc.getWidth()/2) - (back.getWidth()/2), 700); // Setting the x value as half of the game container and adjusting for the width of the button
	}

	/**
	 * @method optionButtonAction
	 * @description checks the button detection and handles the according events in the options section of our main menu
	 * 
	 * @param
	 * GameContainer gc
	 * 
	 * @return
	 * 	void:
	 * @throws SlickException 
	 */
	public void optionButtonAction(GameContainer gc, StateBasedGame sbg, Input input) 
			throws SlickException{
		
		plusMusic = new Image("res/gui/buttons/button_plus.png"); // Plus Button	
		minusMusic = new Image("res/gui/buttons/button_minus.png"); // Minus Button
		plusSFX = new Image("res/gui/buttons/button_plus.png"); // Plus Button	
		minusSFX = new Image("res/gui/buttons/button_minus.png"); // Minus Button	
		back = new Image("res/gui/buttons/button_back.png"); // Back Button	
		
		// Plus Music Button
		// The parameters for checkbounds are the x and y coordinates of the top left of the button and the bottom right of the button
		if(checkBounds( 545 , 545 + minusSFX.getWidth() , 485, 485 + minusSFX.getHeight())){
			if(input.isMouseButtonDown(0)){
				if(Main.util.getMusicManager().getVolume() <= 0.995f)
						Main.util.getMusicManager().setVolume(Main.util.getMusicManager().getVolume() + 0.005f);
						Main.util.getMusic().setVolume(Main.util.getMusicManager().getVolume());
			}
			plusMusic = new Image("res/gui/buttons/button_plus_hover.png");
		}
		
		// Minus Music Button
		// The parameters for checkbounds are the x and y coordinates of the top left of the button and the bottom right of the button
		if(checkBounds( 662 , 662 + minusMusic.getWidth() , 485, 485 + minusMusic.getHeight())){
			if(input.isMouseButtonDown(0)){
				if(Main.util.getMusicManager().getVolume() >= 0.005f)
						Main.util.getMusicManager().setVolume(Main.util.getMusicManager().getVolume() - 0.005f);
						Main.util.getMusic().setVolume(Main.util.getMusicManager().getVolume());
			}
			minusMusic = new Image("res/gui/buttons/button_minus_hover.png");
		}
		
		// Plus SFX Button
		// The parameters for checkbounds are the x and y coordinates of the top left of the button and the bottom right of the button
		if(checkBounds( 545 , 545 + minusSFX.getWidth() , 485 + 135 , 485 + 135 + minusSFX.getHeight())){
			if(input.isMouseButtonDown(0)){
				if(Main.util.getSoundManager().getVolume() <= 0.995f)
						Main.util.getSoundManager().setVolume(Main.util.getSoundManager().getVolume() + 0.005f);
						if(Main.util.getSoundManager().getVolume() % 0.1f > 0.095f) {
							System.out.println(Main.util.getSoundManager().getVolume());
							Main.util.getSFX(0).play(1f, Main.util.getSoundManager().getVolume());
						}
			}
			plusSFX = new Image("res/gui/buttons/button_plus_hover.png");
		}
		
		// Minus SFX Button
		// The parameters for checkbounds are the x and y coordinates of the top left of the button and the bottom right of the button
		if(checkBounds( 662 , 662 + minusSFX.getWidth() , 485 + 135 , 485 + 135 + minusSFX.getHeight())){
			if(input.isMouseButtonDown(0)){
				if(Main.util.getSoundManager().getVolume() >= 0.005f) {
						Main.util.getSoundManager().setVolume(Main.util.getSoundManager().getVolume() - 0.005f);
						if(Main.util.getSoundManager().getVolume() % 0.1f > 0.095f)
							Main.util.getSFX(0).play(1f, Main.util.getSoundManager().getVolume());
				}
			}
			minusSFX = new Image("res/gui/buttons/button_minus_hover.png");
		}
		
		// Back Button
		// The parameters for checkbounds are the x and y coordinates of the top left of the button and the bottom right of the button
		if(checkBounds( (gc.getWidth()/2) - (back.getWidth()/2) , (gc.getWidth()/2) - (back.getWidth()/2) + back.getWidth() , 700 , 700 + back.getHeight())){
			if(input.isMousePressed(0)){
				isOption = false;
			}
			back = new Image("res/gui/buttons/button_back_hover.png");
		}
		else if(input.isKeyPressed(Input.KEY_ESCAPE))
			isOption = false;
		
	}//end of optionButtonAction
	
	
	/**
	 * @method checkBounds
	 * @description Returns true/false if the mouse is within the boundaries of the given area
	 * 
	 * @param
	 * 	int, int, int, int: The X and Y positions for the top right and bottom right boundaries
	 * 
	 * @return
	 * 	boolean: Whether or not the mouse is in the bounds
	 */
	boolean checkBounds(int x1, int x2, int y1, int y2){
		if((xpos > x1 && xpos < x2) && (ypos > y1 && ypos < y2 )){
			return true;
		}
		return false;
	}
	
	// For other classes to use the checkBounds method
	public static boolean checkBounds(int x1, int x2, int y1, int y2, float tileX, float tileY){
		if((tileX > x1 && tileX < x2) && (tileY > y1 && tileY < y2 )){
			return true;
		}
		return false;
	}
	
	// Return the gamestate ID
	public int getID() {
		return MainMenu.id;
	}
	
}//end of class
