package bpa.dev.linavity.gamestates;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import bpa.dev.linavity.Main;
import bpa.dev.linavity.assets.ExtraMouseFunctions;
import bpa.dev.linavity.utils.ErrorLog;
import bpa.dev.linavity.world.ParallaxMap;


public class MainMenu extends BasicGameState /*implements ComponentListener*/{
	
	// Gamestate ID (0) <-- Main Menu
	public static int id = 0;
	
	// The X & Y positions of the mouse
	public int prevXpos, prevYpos;
	public int xpos, ypos;
	
	//TextField
/*	private TextField name;
	private String namevalue;*/
		
	//Title Screen Images
	private ParallaxMap bg;
	private Image title = null;
	private Image play = null;
	private Image load = null;
	private Image options = null;
	private Image exit = null;

	//Option Screen Images
	private Image optionsBG = null;
	private Image emptySlider = null;
	private Image musicSlider = null;
	private Image plusMusic = null;
	private Image minusMusic = null;
	private Image sfxSlider = null;
	private Image plusSFX = null;
	private Image minusSFX = null;
	
	//Load Screen Images
	private Image loadBG = null;
	private Image slotOne = null;
	private Image slotTwo = null;
	private Image slotThree = null;
	
	//Back Button
	private Image back = null;
	
	//Menu Controllers
	private boolean isLoad = false;
	private boolean isOption = false;
	private int menuSize;
	private int currentSelection;
	
	// This runs as soon as we compile the program.
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		
		//TODO Implement in StartLevel
		//Text Field Data
/*		name = new TextField(gc,gc.getDefaultFont(),100,100,300,20,this);
		namevalue = "DefaultName";*/
				
		// Initialize our image objects
		bg = new ParallaxMap("res/titlescreen.png", 0, 0, 0, -3f, true);// Menu Background
		title = new Image("res/title.png");
		
		// Main Menu Buttons
		play = new Image("res/gui/buttons/button_new.png"); // Play Button
		load = new Image("res/gui/buttons/button_load.png"); //Load Button
		exit = new Image("res/gui/buttons/button_exit.png"); // Exit Button
		options = new Image("res/gui/buttons/button_options.png"); // Options Button
		
		//Load Menu Objects
		loadBG = new Image("res/gui/load_screen.png");
		
		if(Main.util.getSlotOneData().getLoadFile() != null)
			slotOne = new Image("res/gui/save_slot.png");
		else
			slotOne = new Image("res/gui/empty_save_slot.png");
		if(Main.util.getSlotTwoData().getLoadFile() != null)
			slotTwo = new Image("res/gui/save_slot.png");
		else
			slotTwo = new Image("res/gui/empty_save_slot.png");
		if(Main.util.getSlotThreeData().getLoadFile() != null)
			slotThree = new Image("res/gui/save_slot.png");
		else
			slotThree = new Image("res/gui/empty_save_slot.png");
		
		//Option Image Objects
		optionsBG = new Image("res/gui/options_bg.png"); //Options Background
		emptySlider = new Image("res/gui/stats/empty_slider.png");
		musicSlider = new Image("res/gui/stats/music_slider.png");
		sfxSlider = new Image("res/gui/stats/sfx_slider.png");
		
		// Option buttons
		plusMusic = new Image("res/gui/buttons/button_plus.png"); // Plus Button	
		minusMusic = new Image("res/gui/buttons/button_minus.png"); // Minus Button
		plusSFX = new Image("res/gui/buttons/button_plus.png"); // Plus Button	
		minusSFX = new Image("res/gui/buttons/button_minus.png"); // Minus Button	
		back = new Image("res/gui/buttons/button_back.png"); // Back Button	

		//Menu Controllers
		menuSize = 4;
		currentSelection = -1;
		
		//Start Music
		Main.util.setMusic(Main.util.getMusicQueue(0));
		Main.util.getMusic().loop(1f, Main.util.getMusicManager().getVolume());
	}//end of init

	// Renders content to the game / screen
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		
/*		if(namevalue.equalsIgnoreCase("DefaultName"))
			name.render(gc, g);
		
		gc.getDefaultFont().drawString(100, 300, "Stored Name: "+namevalue);*/
		
		// DRAW OUR MENU UI //
		
		g.setColor(Color.white);
		
		// Background Image
		bg.getBackgroundLayer().draw(bg.getX(), bg.getY());
		
		if(!isLoad)
			g.drawImage(title, 0, 0);
		
		if(Main.util.debugMode)
			g.drawString("XPOS: " + xpos + " | YPOS: " + ypos, 10, 30); // Draw our mouse position for debugging purposes. 
		
		// Button Rendering
		if(!isOption && !isLoad){ 
			renderMainMenuScreenMain(gc, g); // If the option menu has not been selected, render the main section of the menu
		}else if (isOption && !isLoad){ 
			renderMainMenuScreenOptions(gc, g); // If the option menu is selected, render the options section of the menu
		}else if(!isOption && isLoad) {
			renderMainMenuScreenLoad(gc, g); // If the option menu is selected, render the options section of the menu
		}
		
		// END OUR MENU UI //

	}//end of render

/*	public void componentActivated(AbstractComponent source) { 
		  if (source == name) { 
		   namevalue = name.getText(); 
		  } 
		 
	}//end of componentActivated 
*/	
	// Constant Loop, very fast, loops based on a delta (the amount of time that passes between each instance)
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		
		// !NOTE! the mouse coordinates start from the bottom left of the screen
		// this is different from the shapes that are drawn based on coordinates going from the top right of the screen.
		// Because of this difference, we've created our own mouse functions that take into account the game container
		// dimensions, to give us a proper X and Y value that line up with the coordinates of all of our other objects.
		
		bg.moveBackground();
		
		Input input = gc.getInput(); // Create our input object
		
		prevXpos = xpos;
		prevYpos = ypos;
		xpos = ExtraMouseFunctions.getMouseX(gc.getWidth()); // Updates the x coordinate of the mouse
		ypos = ExtraMouseFunctions.getMouseY(gc.getHeight()); // Updates the y coordinate of the mouse

		
		// Check User Input //
		
		if(!isOption && !isLoad){
			mainButtonAction(gc, sbg, input); // If the option menu has not been selected, check for the main section's buttons
			cycleSelection(input);//Check for key input
			checkMouseBounds();//Check to see if the mouse is moving
		}
		else if(isOption && !isLoad) {
			optionButtonAction(gc, sbg, input); // If the option menu is selected, check for the option section's buttons
		}
		else if(!isOption && isLoad) {
			loadButtonAction(gc, sbg, input); // If the load menu is selected, check for the load section's buttons
			cycleSelection(input);//Check for key input
			checkMouseBounds();//Check to see if the mouse is moving
		}
		
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
		g.drawImage(play, (gc.getWidth()/2) - (play.getWidth()/2), 350); // Setting the x value as half of the game container and adjusting for the width of the button
		
		//Load Button
		g.drawImage(load, (gc.getWidth()/2) - (load.getWidth()/2), 450); // Setting the x value as half of the game container and adjusting for the width of the button
		
		// Options Button
		g.drawImage(options, (gc.getWidth()/2) - (options.getWidth()/2), 550); // Setting the x value as half of the game container and adjusting for the width of the button
		
		// Exit Button
		g.drawImage(exit, (gc.getWidth()/2) - (exit.getWidth()/2), 650); // Setting the x value as half of the game container and adjusting for the width of the button
	}
	

	/**
	 * @method renderMainMenuScreenLoad
	 * @description draws the images needed for the main screen options of the main menu
	 * 
	 * @param
	 * GameContainer gc, Graphics g
	 * 
	 * @return
	 * 	void:
	 */
	public void renderMainMenuScreenLoad(GameContainer gc, Graphics g){
	
		//Background Image
		g.drawImage(loadBG, 0,0);
	
		g.drawImage(slotOne, 100, 150);
		g.setColor(Color.white);
		
		if(Main.util.getSlotOneData().getLoadFile() != null) {
			try {
				g.drawImage(new Image("res/gui/slot1.png"), 100, 150);
				g.drawString("Level "+Main.util.getSlotOneData().getGameStateFound(), 135, 150+75);
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
		
		g.drawImage(slotTwo, 100, 350);
		
		if(Main.util.getSlotTwoData().getLoadFile() != null) {
			try {
				g.drawImage(new Image("res/gui/slot2.png"), 100, 350);
				g.drawString("Level "+Main.util.getSlotOneData().getGameStateFound(), 135, 350+75);
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
		
		g.drawImage(slotThree, 100, 550);
		
		if(Main.util.getSlotThreeData().getLoadFile() != null) {
			try {
				g.drawImage(new Image("res/gui/slot3.png"), 100, 550);
				g.drawString("Level "+Main.util.getSlotOneData().getGameStateFound(), 135, 550+75);
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
		
		// Back Button
		g.drawImage(back, (gc.getWidth()/2) - (back.getWidth()/2), 750); // Setting the x value as half of the game container and adjusting for the width of the button
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
		g.drawImage(minusMusic, 545, 485);
		g.drawImage(plusMusic, 662, 485);
		
		//SFX Options
		g.drawImage(emptySlider, 448, 445 + 135);
		sfxSlider.draw(448,445 + 135,(float) (448+((Main.util.getSoundManager().getVolume() * 100) * 3.77)),(445+135)+35,0,0,(float) ((Main.util.getSoundManager().getVolume() * 100) * 3.77),35);
		g.drawImage(minusSFX, 545, 485 + 135);
		g.drawImage(plusSFX, 662, 485 + 135);
	
		// Back Button
		g.drawImage(back, (gc.getWidth()/2) - (back.getWidth()/2), 700); // Setting the x value as half of the game container and adjusting for the width of the button
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
	
	private void StartGame(Input input, StateBasedGame sbg, int gameStateID) {
		input.clearKeyPressedRecord();
		sbg.enterState(gameStateID);
		Main.util.getMusic().stop();
		Main.util.setMusic(Main.util.getMusicQueue(1));
		Main.util.getMusic().loop(1f, Main.util.getMusicManager().getVolume());
	}
	
	private void LoadButton() {
		isLoad = true;
	}
	
	private void OpenSave(GameContainer gc, Input input, StateBasedGame sbg, int slot) throws SlickException{
		if(Main.util.getSlotOneData().getLoadFile() != null) {
			isLoad = false;
			
			if(slot == 1)
				Main.util.setCurrentLoadData(Main.util.getSlotOneData());
			else if(slot == 2)
				Main.util.setCurrentLoadData(Main.util.getSlotTwoData());
			else if(slot == 3)
				Main.util.setCurrentLoadData(Main.util.getSlotThreeData());
			
			Main.util.setLoadGame(true);
			sbg.getState(Main.util.getCurrentLoadData().getGameStateFound()).init(gc, sbg);
			StartGame(input, sbg, Main.util.getCurrentLoadData().getGameStateFound());
		}
	}//end of OpenSave

	private void OptionButton() {
		isOption = true;
	}
	
	private void ExitButton() {
		System.exit(0);
	}

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
		
		play = new Image("res/gui/buttons/button_new.png");
		load = new Image("res/gui/buttons/button_load.png");
		options = new Image("res/gui/buttons/button_options.png");
		exit = new Image("res/gui/buttons/button_exit.png");
	
		// Play Button
		// The parameters for checkbounds are the x and y coordinates of the top left of the button and the bottom right of the button
		if(checkBounds( (gc.getWidth()/2) - (play.getWidth()/2) , (gc.getWidth()/2) - (play.getWidth()/2) + play.getWidth() , 350 , 350 + play.getHeight())) {
			if(input.isMousePressed(0)) {
				input.clearKeyPressedRecord();
				Main.util.setLoadGame(false);
				sbg.getState(Main.startlevel).init(gc, sbg);
				StartGame(input, sbg, Main.startlevel);
			}
	
			play = new Image("res/gui/buttons/button_new_hover.png");
		}
		else if(currentSelection == 0) {
			if(input.isKeyPressed(Input.KEY_ENTER)){
				Main.util.setLoadGame(false);
				sbg.getState(Main.startlevel).init(gc, sbg);
				StartGame(input, sbg, Main.startlevel);
			}
			play = new Image("res/gui/buttons/button_new_hover.png");
		}
		
		// Load Button
		// The parameters for checkbounds are the x and y coordinates of the top left of the button and the bottom right of the button
		if(checkBounds( (gc.getWidth()/2) - (load.getWidth()/2) , (gc.getWidth()/2) - (load.getWidth()/2) + load.getWidth() , 450 , 450 + load.getHeight())){
			if(input.isMousePressed(0)) {
				input.clearKeyPressedRecord();
				LoadButton();
			}
			
			load = new Image("res/gui/buttons/button_load_hover.png");
		}
		else if(currentSelection == 1) {
			if(input.isKeyPressed(Input.KEY_ENTER)){
				LoadButton();
			}
			load = new Image("res/gui/buttons/button_load_hover.png");
		}
		
		// Options Button
		// The parameters for checkbounds are the x and y coordinates of the top left of the button and the bottom right of the button
		if(checkBounds( (gc.getWidth()/2) - (options.getWidth()/2) , (gc.getWidth()/2) - (options.getWidth()/2) + options.getWidth() , 550 , 550 + options.getHeight())){
			if(input.isMousePressed(0)) {
				input.clearKeyPressedRecord();
				OptionButton();
			}
			
			options = new Image("res/gui/buttons/button_options_hover.png");
		}
		else if(currentSelection == 2) {
			if(input.isKeyPressed(Input.KEY_ENTER)){
				OptionButton();
			}
			options = new Image("res/gui/buttons/button_options_hover.png");
		}
		
		// Exit Button
		// The parameters for checkbounds are the x and y coordinates of the top left of the button and the bottom right of the button
		if(checkBounds( (gc.getWidth()/2) - (exit.getWidth()/2) , (gc.getWidth()/2) - (exit.getWidth()/2) + exit.getWidth() , 650 , 650 + exit.getHeight())){
			if(input.isMousePressed(0))
				ExitButton();
	
			exit = new Image("res/gui/buttons/button_exit_hover.png");
		}
		else if(currentSelection == 3) {
			if(input.isKeyPressed(Input.KEY_ENTER)){
				ExitButton();
			}
			exit = new Image("res/gui/buttons/button_exit_hover.png");
		}
		
	}//end of mainButtonAction

	/**
	 * @method loadButtonAction
	 * @description checks the button detection and handles the according events in the load section of our main menu
	 * 
	 * @param
	 * GameContainer gc
	 * 
	 * @return
	 * 	void:
	 * @throws SlickException 
	 */
	public void loadButtonAction(GameContainer gc, StateBasedGame sbg, Input input) 
			throws SlickException{
				
		if(Main.util.getSlotOneData().getLoadFile() != null)
			slotOne = new Image("res/gui/save_slot.png");
		else
			slotOne = new Image("res/gui/empty_save_slot.png");
		if(Main.util.getSlotTwoData().getLoadFile() != null)
			slotTwo = new Image("res/gui/save_slot.png");
		else
			slotTwo = new Image("res/gui/empty_save_slot.png");
		if(Main.util.getSlotThreeData().getLoadFile() != null)
			slotThree = new Image("res/gui/save_slot.png");
		else
			slotThree = new Image("res/gui/empty_save_slot.png");
		
		back = new Image("res/gui/buttons/button_back.png"); // Back Button	
		
		// Slot 1 Button
		// The parameters for checkbounds are the x and y coordinates of the top left of the button and the bottom right of the button
		if(checkBounds( 100 , 100 + slotOne.getWidth() , 150, 150 + slotOne.getHeight())){
			if(input.isMousePressed(0)) {
				if(Main.util.getSlotOneData().getLoadFile() != null)
					OpenSave(gc, input, sbg, 1);
			}
			
			if(Main.util.getSlotOneData().getLoadFile() != null)
				slotOne = new Image("res/gui/save_slot_hover.png");
			else
				slotOne = new Image("res/gui/empty_save_slot_hover.png");
		}
		else if(currentSelection == 0) {
				if(input.isKeyPressed(Input.KEY_ENTER)) {
					if(Main.util.getSlotOneData().getLoadFile() != null)
						OpenSave(gc, input, sbg, 1);
				}

			if(Main.util.getSlotOneData().getLoadFile() != null)
				slotOne = new Image("res/gui/save_slot_hover.png");
			else
				slotOne = new Image("res/gui/empty_save_slot_hover.png");
		}
		
		// Slot 2 Button
		// The parameters for checkbounds are the x and y coordinates of the top left of the button and the bottom right of the button
		if(checkBounds( 100 , 100 + slotTwo.getWidth() , 350, 350 + slotTwo.getHeight())){
			if(input.isMousePressed(0)) {
				if(Main.util.getSlotTwoData().getLoadFile() != null)
					OpenSave(gc, input, sbg, 2);
			}
				
			if(Main.util.getSlotTwoData().getLoadFile() != null)
				slotTwo = new Image("res/gui/save_slot_hover.png");
			else
				slotTwo = new Image("res/gui/empty_save_slot_hover.png");
		}
		else if(currentSelection == 1) {
			if(input.isKeyPressed(Input.KEY_ENTER)) {
				if(Main.util.getSlotTwoData().getLoadFile() != null)
					OpenSave(gc, input, sbg, 2);
			}

		if(Main.util.getSlotTwoData().getLoadFile() != null)
			slotTwo = new Image("res/gui/save_slot_hover.png");
		else
			slotTwo = new Image("res/gui/empty_save_slot_hover.png");
		}
		
		// Slot 3 Button
		// The parameters for checkbounds are the x and y coordinates of the top left of the button and the bottom right of the button
		if(checkBounds( 100 , 100 + slotThree.getWidth() , 550, 550 + slotThree.getHeight())){
			if(input.isMousePressed(0)){
				if(Main.util.getSlotThreeData().getLoadFile() != null)
					OpenSave(gc, input, sbg, 3);
			}
				
			if(Main.util.getSlotThreeData().getLoadFile() != null)
				slotThree = new Image("res/gui/save_slot_hover.png");
			else
				slotThree = new Image("res/gui/empty_save_slot_hover.png");
		}
		else if(currentSelection == 2) {
			if(input.isKeyPressed(Input.KEY_ENTER)) {
				if(Main.util.getSlotThreeData().getLoadFile() != null)
					OpenSave(gc, input, sbg, 3);
			}

		if(Main.util.getSlotThreeData().getLoadFile() != null)
			slotThree = new Image("res/gui/save_slot_hover.png");
		else
			slotThree = new Image("res/gui/empty_save_slot_hover.png");
		}
		
		// Back Button
		// The parameters for checkbounds are the x and y coordinates of the top left of the button and the bottom right of the button
		if(checkBounds( (gc.getWidth()/2) - (back.getWidth()/2) , (gc.getWidth()/2) - (back.getWidth()/2) + back.getWidth() , 750 , 750 + back.getHeight())){
			if(input.isMousePressed(0)){
				isLoad = false;
			}
			back = new Image("res/gui/buttons/button_back_hover.png");
		}
		else if(currentSelection == 3) {
			if(input.isKeyPressed(Input.KEY_ENTER))
				isLoad = false;
			back = new Image("res/gui/buttons/button_back_hover.png");
		}
		
		else if(input.isKeyPressed(Input.KEY_ESCAPE))
			isLoad = false;
		
	}//end of loadButtonAction
	
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
		
		// Minus Music
		// The parameters for checkbounds are the x and y coordinates of the top left of the button and the bottom right of the button
		if(checkBounds( 545 , 545 + minusMusic.getWidth() , 485, 485 + minusMusic.getHeight())){
			if(input.isMouseButtonDown(0)){
				if(Main.util.getMusicManager().getVolume() >= 0.005f)
						Main.util.getMusicManager().setVolume(Main.util.getMusicManager().getVolume() - 0.005f);
						Main.util.getMusic().setVolume(Main.util.getMusicManager().getVolume());
			}
			minusMusic = new Image("res/gui/buttons/button_minus_hover.png");
		}
		
		// Plus Music Button
		// The parameters for checkbounds are the x and y coordinates of the top left of the button and the bottom right of the button
		if(checkBounds( 662 , 662 + plusMusic.getWidth() , 485, 485 + plusMusic.getHeight())){
			if(input.isMouseButtonDown(0)){
				if(Main.util.getMusicManager().getVolume() <= 0.995f)
						Main.util.getMusicManager().setVolume(Main.util.getMusicManager().getVolume() + 0.005f);
						Main.util.getMusic().setVolume(Main.util.getMusicManager().getVolume());
			}
			plusMusic = new Image("res/gui/buttons/button_plus_hover.png");
		}
		
		// Minus SFX Button
		// The parameters for checkbounds are the x and y coordinates of the top left of the button and the bottom right of the button
		if(checkBounds( 545 , 545 + minusSFX.getWidth() , 485 + 135 , 485 + 135 + minusSFX.getHeight())){
			if(input.isMouseButtonDown(0)){
				if(Main.util.getSoundManager().getVolume() >= 0.005f) {
						Main.util.getSoundManager().setVolume(Main.util.getSoundManager().getVolume() - 0.005f);
						if(Main.util.getSoundManager().getVolume() % 0.1f > 0.095f)
							Main.util.getSFX(0).play(1f, Main.util.getSoundManager().getVolume());
				}
			}
			minusSFX = new Image("res/gui/buttons/button_minus_hover.png");
		}
		
		// Plus SFX Button
		// The parameters for checkbounds are the x and y coordinates of the top left of the button and the bottom right of the button
		if(checkBounds( 662 , 662 + plusSFX.getWidth() , 485 + 135 , 485 + 135 + plusSFX.getHeight())){
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
