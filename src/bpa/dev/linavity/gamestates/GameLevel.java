package bpa.dev.linavity.gamestates;

import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import bpa.dev.linavity.Main;
import bpa.dev.linavity.assets.ExtraMouseFunctions;
import bpa.dev.linavity.collectibles.GravCapsule;
import bpa.dev.linavity.collectibles.GravPack;
import bpa.dev.linavity.collectibles.HealthPack;
import bpa.dev.linavity.collectibles.Item;
import bpa.dev.linavity.collectibles.KeyCard;
import bpa.dev.linavity.collectibles.UseItem;
import bpa.dev.linavity.cutscenes.Script;
import bpa.dev.linavity.cutscenes.Tutorial;
import bpa.dev.linavity.entities.Abric;
import bpa.dev.linavity.entities.Mob;
import bpa.dev.linavity.entities.Player;
import bpa.dev.linavity.entities.enemies.Bomber;
import bpa.dev.linavity.entities.enemies.Starter;
import bpa.dev.linavity.entities.enemies.Tank;
import bpa.dev.linavity.entities.tiles.Tile;
import bpa.dev.linavity.entities.tiles.interactive.Door;
import bpa.dev.linavity.entities.tiles.interactive.EventTile;
import bpa.dev.linavity.entities.tiles.interactive.Lever;
import bpa.dev.linavity.events.EventData;
import bpa.dev.linavity.events.Message;
import bpa.dev.linavity.utils.ErrorLog;
import bpa.dev.linavity.utils.LoadGame;
import bpa.dev.linavity.utils.LogSystem;
import bpa.dev.linavity.utils.PlayerStats;
import bpa.dev.linavity.utils.SaveGame;
import bpa.dev.linavity.world.Level;
import bpa.dev.linavity.world.ParallaxMap;

public class GameLevel extends BasicGameState{
	
	//GUI
	private Image health_gui = null;
	private Image health_bar = null;
	private Image grav_gui = null;
	private Image grav_bar = null;
	
	//Pause Menu
	private Image pause_menu_ui = null;
	private Image resume = null;
	private Image retry = null;
	private Image saveData = null;
	private Image returnMenu = null;
	
	//Save Menu
	private Image saveBG = null;
	private Image slotOne = null;
	private Image slotTwo = null;
	private Image slotThree = null;
	private Image back = null;
	
	//Inventory Menu
	private Image inventoryBG;
	private Image [][] itemSlots;

	//Parallax Backgrounds for Our Level
	private ParallaxMap bg;

	//Cutscene Images
	private Image [] cutsceneGUI;
	
	//Coins
	private Image [] coins;
	
	// ID of the gamestate
	public static int id = 1;
	
	// Whether or not the pop-up menu is open
	private boolean menuOpen = false;
	private boolean saveOpen = false;
	
	//Mouse Coordinates
	private int xpos; // Mouse's X position
	private int ypos; // Mouse's Y position
	
	//List of all possible enemies
	public static ArrayList <Mob> mobs = new ArrayList<Mob>();
	//List of all items
	public static ArrayList <Item> items = new ArrayList<Item>();
	
	public static Tutorial tutorialScene = new Tutorial();
	
	//Tiles for our level
	private Tile[][] screenTiles;
	private Tile[][] eventTiles;
	
	private boolean alertPlayer;
	
	private Font font;
	private TrueTypeFont ttf;
	
	//Tutorial Images
	public static Image[] tutorialGUI;
	
	private double startTime;
	
	/**
	 * This runs as soon as we compile the program
	 */
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		
		LogSystem.addToLog("Initializing GameLevel...");
		Main.util.gc = gc;
		
		Main.util.levelEvents.setEvents(new ArrayList<EventData>());
		
		//Set tutorial functions
		tutorialGUI = new Image[9];
		tutorialGUI[0] = new Image("res/gui/tutorial/left_right.png");
		tutorialGUI[1] = new Image("res/gui/tutorial/interact.png");
		tutorialGUI[2] = new Image("res/gui/tutorial/jump.png");
		tutorialGUI[3] = new Image("res/gui/tutorial/ladders.png");
		tutorialGUI[4] = new Image("res/gui/tutorial/pause.png");
		tutorialGUI[5] = new Image("res/gui/tutorial/gravitypack.png");
		tutorialGUI[6] = new Image("res/gui/tutorial/shoot.png");
		tutorialGUI[7] = new Image("res/gui/tutorial/inventory.png");
		tutorialGUI[8] = new Image("res/gui/tutorial/useitems.png");
		
		if(Main.util.getLoadGame()) {
			Main.util.setPlayerName(Main.util.getCurrentLoadData().getPlayerName());
			try {
				Main.util.setLevel(new Level(Main.util.getCurrentLoadData().getLevelFound()));
			} catch (FileNotFoundException ex) {
				ErrorLog.displayError(ex);
			}
			LogSystem.addToLog("Loading Game...");
			Main.util.levelNum = Main.util.getCurrentLoadData().getLevelFound();
			Main.util.getInventory().setItems(new ArrayList<Item>());
			Main.util.getCam().setX(Main.util.getCurrentLoadData().getCamX());
			Main.util.getCam().setY(Main.util.getCurrentLoadData().getCamY());
			Main.util.setLevelTime(Main.util.getCurrentLoadData().getLevelTime());
			Main.util.deathCount = Main.util.getCurrentLoadData().getDeathCount();
			LogSystem.addToLog("Creating Mobs...");
			startTime = System.nanoTime();
			mobs = getMobs(Main.util.getCurrentLoadData());
			LogSystem.addToLog("Mobs Created.");
			LogSystem.addToLog("Running code took "+((System.nanoTime() - startTime) / 1000000)+" s");
			Main.util.getPs().addToPlayerStats("" + Main.util.getPlayer().getHealth() + "," + Main.util.getPlayer().getGravPack().getGravpower());
			startTime = System.nanoTime();
			LogSystem.addToLog("Creating Items...");
			items = getItems(Main.util.getCurrentLoadData());
			LogSystem.addToLog("Items Created.");
			LogSystem.addToLog("Running code took "+((System.nanoTime() - startTime) / 1000000)+" s");
			setEvents(Main.util.getCurrentLoadData());
			Main.util.cutsceneVars.setID(Main.util.getCurrentLoadData().getEventID());
			Main.util.setLoadGame(false);
			LogSystem.addToLog("Level Loaded Successfully.");
		}
		else {
			LogSystem.addToLog("Starting a New Game...");
			try {
				Main.util.setLevel(new Level(Main.util.levelNum));
			} catch (FileNotFoundException ex) {
				ErrorLog.displayError(ex);
			}
			
			Main.util.setLevelTime(0);
			LogSystem.addToLog("Creating Mobs...");
			startTime = System.nanoTime();
			mobs = getMobs();
			LogSystem.addToLog("Mobs Created.");
			LogSystem.addToLog("Running code took "+((System.nanoTime() - startTime) / 1000000)+" s");
			LogSystem.addToLog("Creating Items...");
			startTime = System.nanoTime();
			items = getItems();
			LogSystem.addToLog("Items Created.");
			LogSystem.addToLog("Running code took "+((System.nanoTime() - startTime) / 1000000)+" s");

			
			if(Main.util.levelNum == 1) {
				tutorialScene.setTutorial(tutorialGUI[0]);
				tutorialScene.setActive(true);
			}	
			
			playerStats();
			
			Main.util.cutsceneVars.setID(Main.util.cutsceneVars.getLevelStartID());
		}
		
		Main.util.setLevelMobs(mobs);
		Main.util.setLevelItems(items);
		
		pause_menu_ui = new Image("res/gui/pausemenu.png");
		resume = new Image("res/gui/buttons/button_resume.png");
		retry = new Image("res/gui/buttons/button_retry.png");
		saveData = new Image("res/gui/buttons/button_save.png");
		returnMenu = new Image("res/gui/buttons/button_return.png");
		back = new Image("res/gui/buttons/button_back.png");
		
		//Save Images
		saveBG = new Image("res/gui/save_screen.png");
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
		
		//Inventory Images
		inventoryBG = new Image("res/gui/inventory/inventoryBG.png");
		itemSlots = new Image[2][4];
		for(int i = 0; i < itemSlots.length; i++)
			for(int x = 0; x < itemSlots[0].length; x++)
				itemSlots[i][x] = new Image("res/gui/inventory/item_slot.png");
		
		bg = new ParallaxMap("res/bg.jpg", 0, 0, -0.5f, 0,  true);
		
		cutsceneGUI = new Image[4];
		cutsceneGUI[0] = new Image("res/gui/cutscenes/screen_darken.png");
		cutsceneGUI[1] = new Image("res/gui/cutscenes/letterbox_top.png"); 
		cutsceneGUI[2] = new Image("res/gui/cutscenes/letterbox_bottom.png");
		cutsceneGUI[3] = new Image("res/gui/cutscenes/dialogbox.png");
		
		coins = new Image[3];
		coins[0] = new Image("res/items/coin_b/coin_b_thumb_empty.png");
		coins[1] = new Image("res/items/coin_p/coin_p_thumb_empty.png");
		coins[2] = new Image("res/items/coin_a/coin_a_thumb_empty.png");
		Main.util.coinBGrabbed = false;
		Main.util.coinPGrabbed = false;
		Main.util.coinAGrabbed = false;
		
		health_gui = new Image("res/gui/stats/health_bar.png");
		health_bar = new Image("res/gui/stats/health_bar_full.png");
		grav_gui = new Image("res/gui/stats/grav_pack.png");
		grav_bar = new Image("res/gui/stats/grav_pack_full.png");
		
		font = new Font("Verdana", Font.BOLD, 22);
		ttf = new TrueTypeFont(font, true);
		
		LogSystem.addToLog("GameLevel initialized successfully.");
		LogSystem.addToLog("");
		
	}//end of init

	private void playerStats() {
		
		Main.util.getInventory().setItems(new ArrayList<Item>());
		
		ArrayList<String> stats = new ArrayList<String>();
		try {
			stats = Main.util.getPs().readPlayerStats();
		} catch (FileNotFoundException e) {
			ErrorLog.displayError(e);
		}
		
		for(int i = 0; i < stats.size(); i++) {
			if(i == 0) {
				String [] playerStats = stats.get(i).split(",");
				Main.util.getPlayer().setHealth(Double.parseDouble(playerStats[0]));
				Main.util.getPlayer().getGravPack().setGravpower(Float.parseFloat(playerStats[1]));
			}
			else if(stats.size() > 1) {
					try {
						
						if(stats.get(i).equalsIgnoreCase("gravcapsule"))
						Main.util.getInventory().addToInventory(new GravCapsule(0, 0, "gravcapsule"));
						
						else if(stats.get(i).equalsIgnoreCase("healthpack"))
							Main.util.getInventory().addToInventory(new HealthPack(0, 0, "healthpack"));
						
						else if(stats.get(i).equalsIgnoreCase("keycard"))
							Main.util.getInventory().addToInventory(new KeyCard(0, 0, "keycard"));
						
					} catch (SlickException e) {
						ErrorLog.displayError(e);
					}
			}
				
		}
	}
	
	private ArrayList<Mob> getMobs() throws SlickException {
		
		Main.util.setPlayer((Player)Main.util.getLevel().getMobs().get(0));
		
		return Main.util.getLevel().getMobs();
	}//end of getMobs
	
	private ArrayList<Mob> getMobs(LoadGame loadFile) throws SlickException {
		//Add mobs
		ArrayList <Mob> mobs = new ArrayList<Mob>();
		
		String[] classNames = loadFile.getClassNames();
		int mobSize = loadFile.getMobSize();
		float[] xPos = loadFile.getXPos();
		float[] yPos = loadFile.getYPos();
		double[] healthStats = loadFile.getHealthStats();
		float gravPower = loadFile.getGravPowerCheck();
		boolean isFlipping = loadFile.getFlipping();
		
		for(int i = 0; i < mobSize; i++) {
			if(classNames[i].equalsIgnoreCase("Player")) {
				Main.util.setPlayer(new Player(xPos[i], yPos[i]));
				mobs.add(Main.util.getPlayer());
				Main.util.getPlayer().getGravPack().setGravpower(gravPower);
				Main.util.getPlayer().setIsFlipping(isFlipping);
			}
			else if(classNames[i].equalsIgnoreCase("Starter"))
				mobs.add(new Starter(xPos[i], yPos[i]));
			else if(classNames[i].equalsIgnoreCase("Tank"))
				mobs.add(new Tank(xPos[i], yPos[i]));
			else if(classNames[i].equalsIgnoreCase("Bomber"))
				mobs.add(new Bomber(xPos[i], yPos[i]));
			
			mobs.get(i).setHealth(healthStats[i]);
		}
		
		return mobs;
	}//end of getMobs
	
	private ArrayList<Item> getItems() throws SlickException {
		return Main.util.getLevel().getItems();
	}//end of getItems
	
	private ArrayList<Item> getItems(LoadGame loadFile) throws SlickException {
		//Add mobs
		ArrayList <Item> items = new ArrayList<Item>();
		
		int itemsSize = loadFile.getItemSize();
		int mobSize = loadFile.getMobSize();
		float[] xPos = loadFile.getXPos();
		float[] yPos = loadFile.getYPos();
		float[] width = loadFile.getWidth();
		float[] height = loadFile.getHeight();
		String []itemImage = loadFile.getItemImage();
		
		boolean setGrav= true;
		
		for(int i = 0; i < itemImage.length; i++) {
			if(i < itemsSize) {
				if(itemImage[i].equalsIgnoreCase("gravitypack")) {
					setGrav = false;
					items.add(new GravPack(xPos[i + mobSize ], yPos[i + mobSize ], width[i], height[i], itemImage[i]));
				}
				else if(itemImage[i].equalsIgnoreCase("healthpack"))
					items.add(new HealthPack(xPos[i + mobSize], yPos[i + mobSize], width[i], height[i], itemImage[i]));
				else if(itemImage[i].equalsIgnoreCase("gravcapsule"))
					items.add(new GravCapsule(xPos[i + mobSize], yPos[i + mobSize], width[i], height[i], itemImage[i]));
				else if(itemImage[i].equalsIgnoreCase("keycard"))
					items.add(new KeyCard(xPos[i + mobSize], yPos[i + mobSize], width[i], height[i], itemImage[i]));
			}
			else if(itemImage[i] != null){
				System.out.println(itemImage[i]);
				if(itemImage[i].equalsIgnoreCase("healthpack"))
					Main.util.getInventory().addToInventory(new HealthPack(xPos[i + mobSize], yPos[i + mobSize], width[i], height[i], itemImage[i]));
				else if(itemImage[i].equalsIgnoreCase("gravcapsule"))
					Main.util.getInventory().addToInventory(new GravCapsule(xPos[i + mobSize], yPos[i + mobSize], width[i], height[i], itemImage[i]));
				else if(itemImage[i].equalsIgnoreCase("keycard"))
					Main.util.getInventory().addToInventory(new KeyCard(xPos[i + mobSize], yPos[i + mobSize], width[i], height[i], itemImage[i]));
			}
		}
		
		if(setGrav)
			Main.util.getPlayer().setCanUseGravpack(true);
		
		return items;
	}//end of getItems
	
	public void setEvents(LoadGame loadFile) {
		
		int events = loadFile.getEventSize();
		int [] row = loadFile.getRow();
		int [] col = loadFile.getCol();
		String [] name = loadFile.getName();
		Object [] data = loadFile.getData();
		
		for(int i = 0; i < events; i++) {
			if(name[i].equalsIgnoreCase("door")) {
				Main.util.getMessageHandler().addMessage(new Message(Main.util.getLevel().getSingleEventTile(new Point(row[i], col[i])), null, Message.autoOpenDoor, Boolean.parseBoolean(""+data[i])));
			}
			if(name[i].equalsIgnoreCase("event")) {
				EventTile event = (EventTile) Main.util.getLevel().getSingleEventTile(new Point(row[i], col[i]));
				event.setToggle(Boolean.parseBoolean(""+data[i]));
			}
			if(name[i].equalsIgnoreCase("lever")) {
				Main.util.getMessageHandler().addMessage(new Message(Main.util.getLevel().getSingleEventTile(new Point(row[i], col[i])), null, Message.leverToggle, Boolean.parseBoolean(""+data[i])));
			}
		}
		
	}//end of setEvents
	
	/**
	 * Renders content to the game / screen
	 * Note: Positioning of objects matter: Draw backgrounds first, foregrounds last
	 */
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		
		bg.getBackgroundLayer().draw(bg.getX(),bg.getY());
		
		renderScreen(gc, sbg, g);
		
		if(Main.util.debugMode) {
			g.drawString("X: " + Main.util.getPlayer().getX() + " Y: " + Main.util.getPlayer().getY(), 10,50);
			g.drawString("Cam X: " + Main.util.getCam().getX() + " Cam Y: " + Main.util.getCam().getY(), 10,70);
			g.drawString("Collide up: " + Main.util.getPlayer().isCu(), 10,110);
			g.drawString("Collide down: " + Main.util.getPlayer().isCd(), 10,130);
			g.drawString("Collide left: " + Main.util.getPlayer().isCl(), 10,150);
			g.drawString("Collide right: " + Main.util.getPlayer().isCr(), 10,170);
			g.drawString("XPOS: " + xpos + " | YPOS: " + ypos, 10, 190); // Draw our mouse position for debugging purposes.
			g.drawString("Can Jump: "+Main.util.getPlayer().canJump(), 10, 230); 
			if(Main.util.getPlayer().getCurrentProjectile() != null) {
				g.drawString("X: "+Main.util.getPlayer().getCurrentProjectile().getX(), 10, 250);
				g.drawString("Y: "+Main.util.getPlayer().getCurrentProjectile().getY(), 10, 270);
				g.drawString("Shot Left: "+Main.util.getPlayer().getCurrentProjectile().isShotLeft(), 10, 290); 
				g.drawString("Shot Right: "+Main.util.getPlayer().getCurrentProjectile().isShotRight(), 10, 310);
				g.drawString("Collided: "+Main.util.getPlayer().getCurrentProjectile().isCollide(), 10, 330); 
			}
		}
		
		//Draw enemies
		for(int i = 0; i < mobs.size(); i++){
			if(mobs.get(i).isAlive())
				mobs.get(i).getCurrentImage().draw(mobs.get(i).getX() - Main.util.cam.getX(), mobs.get(i).getY() - Main.util.cam.getY());
			else if(i > 0)
				mobs.remove(i);
		}

		//Draw items
		for(int i = 0; i < items.size(); i++) {
			if(items.get(i).isActive())
				items.get(i).getItemAni().draw(items.get(i).getX() - Main.util.cam.getX(), items.get(i).getY() - Main.util.cam.getY());
			else
				items.remove(i);
		}
		
		ttf.drawString(770,50, "Timer: "+Main.util.getLevelTime() / 1000, Color.red);
		ttf.drawString(20,20, "Deaths: "+Main.util.deathCount, Color.red);
		
		g.drawImage(coins[0], 750, 10);
		g.drawImage(coins[1], 800, 10);
		g.drawImage(coins[2], 850, 10);
		
		//If a projectile exists, then draw it on the screen
		if(Main.util.getPlayer().isProjectileExists()) {
			Main.util.getPlayer().getCurrentProjectile().getProjectileImage().draw(Main.util.getPlayer().getCurrentProjectile().getX() - Main.util.getCam().getX(), Main.util.getPlayer().getCurrentProjectile().getY() - Main.util.getCam().getY());
			Main.util.getPlayer().getCurrentProjectile().setCollisionBox(new Rectangle((int) (Main.util.getPlayer().getCurrentProjectile().getX() - Main.util.getCam().getX()), (int) (Main.util.getPlayer().getCurrentProjectile().getY() - Main.util.getCam().getY()), (int) Main.util.getPlayer().getCurrentProjectile().getWidth(), (int) Main.util.getPlayer().getCurrentProjectile().getHeight()));
			if(Main.util.debugMode)
				g.drawRect(Main.util.getPlayer().getCurrentProjectile().getX() - Main.util.getCam().getX(), Main.util.getPlayer().getCurrentProjectile().getY() - Main.util.getCam().getY(), Main.util.getPlayer().getCurrentProjectile().getWidth(), Main.util.getPlayer().getCurrentProjectile().getHeight());
		}
		
		g.setColor(Color.orange);
		
		for(int i = 0; i < mobs.size(); i++) {

		mobs.get(i).setBoundingBox(new Rectangle((int) (mobs.get(i).getX() - Main.util.getCam().getX()), (int) (mobs.get(i).getY() - Main.util.getCam().getY()), mobs.get(i).getWidth(), mobs.get(i).getHeight()));
		if(Main.util.debugMode)
			g.drawRect((int) mobs.get(i).getBoundingBox().getX(), (int) mobs.get(i).getBoundingBox().getY(), (int) mobs.get(i).getBoundingBox().getWidth(), (int) mobs.get(i).getBoundingBox().getHeight());
			}
		
		for(int i = 0; i < items.size(); i++) {
			items.get(i).setCollisionBox(new Rectangle((int) (items.get(i).getX() - Main.util.getCam().getX()), (int) (items.get(i).getY() - Main.util.getCam().getY()), (int)items.get(i).getWidth(), (int)items.get(i).getHeight()));
			if(Main.util.debugMode)
				g.drawRect((int) items.get(i).getCollisionBox().getX(), (int) items.get(i).getCollisionBox().getY(), (int) items.get(i).getCollisionBox().getWidth(), (int) items.get(i).getCollisionBox().getHeight());
		}
		
		health_gui.draw(0,0);
		health_bar.draw(25,850,(float) (25+(Main.util.getPlayer().getHealth() * 2.7)),850+27,0,0,(float) (Main.util.getPlayer().getHealth() * 2.7),27);
		
		if(Main.util.getPlayer().canUseGravpack()) {
			grav_gui.draw(0,0);
			grav_bar.draw(318,850,(float) (318+(Main.util.getPlayer().getGravPack().getGravpower() * 2.7)),850+27,0,0,(float) (Main.util.getPlayer().getGravPack().getGravpower() * 2.7),27);
		}
		
		renderCutscene(gc, g);
		
		if(!menuOpen && !saveOpen && !Main.util.getPlayer().isInventoryOpen())
			tutorialScene.update(g, Main.util.delta);
		
		//Draw menu, if open
		if(!menuOpen && !saveOpen && Main.util.getPlayer().isInventoryOpen())
			renderInventoryMenu(gc, g);
		else if(menuOpen && !saveOpen && !Main.util.getPlayer().isInventoryOpen())
			renderMenu(gc, g);
		else if(saveOpen && !menuOpen && !Main.util.getPlayer().isInventoryOpen()) 
			renderSaveMenu(gc, g);
		
		
	}//end of render

	/**
	 * Render the screen based on where the camera is on our map
	 * @param gc
	 * @param sbg
	 * @param g
	 */
	private void renderScreen(GameContainer gc, StateBasedGame sbg, Graphics g) {
		
		// Get a 2d array of tile objects that are contained within our camera's view
		screenTiles = Main.util.getLevel().getScreenTiles(Main.util.getCam(), Main.util.getLevel().getMap());
		
		// Temp x and y of tile in relation to the camera
		float tileX, tileY;
		
		// Draw the tiles that belong on the screen.
		for(int i = 0; i < screenTiles.length; i++) {
			for(int j = 0; j < screenTiles[i].length; j++) {
				if(screenTiles[i][j] != null) {
					tileX = screenTiles[i][j].getX() - Main.util.getCam().getX(); // Get the tile's temp x location for the screen rendering
					tileY = screenTiles[i][j].getY() - Main.util.getCam().getY(); // Get the tile's temp y location for the screen rendering
					screenTiles[i][j].getTexture().draw(tileX, tileY); // Draw the tile

						screenTiles[i][j].setCollisionBox(new Rectangle((int) tileX, (int) tileY, (int) screenTiles[i][j].getWidth(), (int) screenTiles[i][j].getHeight()));
						if(!screenTiles[i][j].isPassable()) {
							g.setColor(Color.white);
							if(Main.util.debugMode)
								g.drawRect((int)screenTiles[i][j].getCollisionBox().getX(), (int) screenTiles[i][j].getCollisionBox().getY(), (int) screenTiles[i][j].getCollisionBox().getWidth(), (int)screenTiles[i][j].getCollisionBox().getHeight());
						
					}
				}
			}
		}
		// End of drawing screen tiles
	
		// Get a 2d array of tile objects that are contained within our camera's view
		eventTiles = Main.util.getLevel().getScreenTiles(Main.util.getCam(), Main.util.getLevel().getEvents());
		
		// Temp x and y of tile in relation to the camera
		float eventTileX, eventTileY;
		
		// Draw the tiles that belong on the screen.
		for(int i = 0; i < eventTiles.length; i++) {
			for(int j = 0; j < eventTiles[i].length; j++) {
				if(eventTiles[i][j] != null) {
					eventTileX = eventTiles[i][j].getX() - Main.util.getCam().getX(); // Get the tile's temp x location for the screen rendering
					eventTileY = eventTiles[i][j].getY() - Main.util.getCam().getY(); // Get the tile's temp y location for the screen rendering
					eventTiles[i][j].getTexture().draw(eventTileX, eventTileY); // Draw the tile
						eventTiles[i][j].setCollisionBox(new Rectangle((int) eventTileX, (int) eventTileY, (int) eventTiles[i][j].getWidth(), (int) eventTiles[i][j].getHeight()));
						if(!eventTiles[i][j].isPassable()) {
							g.setColor(Color.white);
							if(Main.util.debugMode)
								g.drawRect((int)eventTiles[i][j].getCollisionBox().getX(), (int) eventTiles[i][j].getCollisionBox().getY(), (int) eventTiles[i][j].getCollisionBox().getWidth(), (int)eventTiles[i][j].getCollisionBox().getHeight());
						}
				}
			}
		}
		// End of drawing event tiles
		
	}//end of renderScreen


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
	private void renderMenu(GameContainer gc, Graphics g){
		// Back Button
		g.drawImage(pause_menu_ui, 0,0); // Setting the pause menu ui
		g.drawImage(resume, (gc.getWidth()/2) - (resume.getWidth()/2), 325); // Setting the x value as half of the game container and adjusting for the width of the button
		g.drawImage(retry, (gc.getWidth()/2) - (retry.getWidth()/2), 405); // Setting the x value as half of the game container and adjusting for the width of the button
		g.drawImage(saveData, (gc.getWidth()/2) - (saveData.getWidth()/2), 485); // Setting the x value as half of the game container and adjusting for the width of the button
		g.drawImage(returnMenu, (gc.getWidth()/2) - (returnMenu.getWidth()/2), 565); // Setting the x value as half of the game container and adjusting for the width of the button
	}//end of renderMenu

	/**
	 * @method renderSaveMenu
	 * @description draws the images needed for the save menu
	 * 
	 * @param
	 * GameContainer gc, Graphics g
	 * 
	 * @return
	 * 	void:
	 */
	private void renderSaveMenu(GameContainer gc, Graphics g){

		//Background Image
		g.drawImage(saveBG, 0,0);

		g.drawImage(slotOne, 100, 150);
		g.setColor(Color.white);
		
		if(Main.util.getSlotOneData().getLoadFile() != null) {
			try {
				g.drawImage(new Image("res/gui/slot1.png"), 100, 150);
				g.drawString(Main.util.getSlotOneData().getPlayerName(), 135, 225);
				g.drawString("Level "+Main.util.getSlotOneData().getLevelFound(), 135, 245);
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
		
		g.drawImage(slotTwo, 100, 350);
		
		if(Main.util.getSlotTwoData().getLoadFile() != null) {
			try {
				g.drawImage(new Image("res/gui/slot2.png"), 100, 350);
				g.drawString(Main.util.getSlotTwoData().getPlayerName(), 135, 425);
				g.drawString("Level "+Main.util.getSlotTwoData().getLevelFound(), 135, 445);
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
		
		g.drawImage(slotThree, 100, 550);
		
		if(Main.util.getSlotThreeData().getLoadFile() != null) {
			try {
				g.drawImage(new Image("res/gui/slot3.png"), 100, 550);
				g.drawString(Main.util.getSlotThreeData().getPlayerName(), 135, 625);
				g.drawString("Level "+Main.util.getSlotThreeData().getLevelFound(), 135, 645);
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
		
		// Back Button
		g.drawImage(back, (gc.getWidth()/2) - (back.getWidth()/2), 750); // Setting the x value as half of the game container and adjusting for the width of the button
	}	
	
	private void renderInventoryMenu(GameContainer gc, Graphics g) {
		//Background Image
		g.drawImage(inventoryBG, 0, 0);
		int slotX = 35;
		int slotY = 248;
		int counter = 0;
		for(int i = 0; i < itemSlots.length; i++) {
			for(int x = 0; x < itemSlots[0].length; x++) {
				g.drawImage(itemSlots[i][x], slotX, slotY);
				//Draw the thumbnails at the center of the item slot
				if(Main.util.getInventory().getItems().size() != 0 && counter < Main.util.getInventory().getItems().size()){
					g.drawImage(Main.util.getInventory().getItems().get(counter).getThumb(), slotX + ((itemSlots[i][x].getWidth() / 2) - (Main.util.getInventory().getItems().get(counter).getThumb().getWidth() / 2)), slotY + ((itemSlots[i][x].getHeight() / 2) - (Main.util.getInventory().getItems().get(counter).getThumb().getHeight() / 2)));
					counter++;
				}
				slotX = slotX + 216;
			}
				slotX = 35;
				slotY = slotY + 305;
		}
	}//end of renderInventoryMenu
	
	private void renderCutscene(GameContainer gc, Graphics g){
		
		Script script;
		
		if(Main.util.isCutsceneActive()){
			tutorialScene.setActive(false);
			stopPlayerAnimation();
			script = new Script(g, Main.util.cutsceneVars.getID(), Main.util.cutsceneVars.getLength());
			g.drawImage(cutsceneGUI[0], 0, 0);
			g.drawImage(cutsceneGUI[1], 0, Main.util.startTop);
			script.drawSprites();
			g.drawImage(cutsceneGUI[2], 0, Main.util.startBottom);
			g.drawImage(cutsceneGUI[3], 0, Main.util.startBottom);
			if(Main.util.startTop <= 0 && Main.util.startBottom >= 0){
				Main.util.startTop = Main.util.startTop + 8;
				Main.util.startBottom = Main.util.startBottom - 8;
			}
			checkCutscenes(script, g);
		}else{
			Main.util.startTop = -150;
			Main.util.startBottom = 150;
		}
		
	}//end of renderCutscene
	
	/**
	 * Constant Loop, very fast, loops based on a delta (the amount of time that passes between each instance)
	 */
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		
		// If the game is not paused
		if(!menuOpen && !saveOpen){
			
			if(!Main.util.isCutsceneActive())
				if(Main.util.getPlayer().isInventoryOpen())
					checkInventoryMenu(gc);
			
			if(!Main.util.isCutsceneActive())
				updateTimer(delta);
			
			// Make all keyboard-based updates
				input(gc);
			
			// Make all game information updates
			gameUpdates(gc, sbg, delta);
			
			//Update Animations
			updateAnimation(delta);
			
			// Message Handler
			Main.util.getMessageHandler().dispatchMessages();
			
			bg.moveBackground();
			
			if(Main.util.coinBGrabbed)
				coins[0] = new Image("res/items/coin_b/coin_b_thumb.png");
			if(Main.util.coinPGrabbed)
				coins[1] = new Image("res/items/coin_p/coin_p_thumb.png");
			if(Main.util.coinAGrabbed)
				coins[2] = new Image("res/items/coin_a/coin_a_thumb.png");
			
		}
		else if(menuOpen && !saveOpen) {
			Main.appgc.setMouseGrabbed(false);
			stopAnimation();//Stop updating animation
			Main.util.getMusic().pause();//Pause the music
			if(Main.util.getSFX(2).playing())
				Main.util.getSFX(2).stop();
			// Open pop-up menu
			checkMenu(gc, sbg);
		}
		else if(!menuOpen && saveOpen) {
			Main.appgc.setMouseGrabbed(false);
			checkSaveMenu(gc,sbg);
		}
		
		//Mouse Functions
		xpos = ExtraMouseFunctions.getMouseX(gc.getWidth()); // Updates the x coordinate of the mouse
		ypos = ExtraMouseFunctions.getMouseY(gc.getHeight()); // Updates the y coordinate of the mouse
		
	}//end of update
	
	private void updateTimer(int delta) {
		if(!Main.util.isCutsceneActive())
			Main.util.setLevelTime((Main.util.getLevelTime() + delta));
	}//end of updateTimer
	
	/**
	 * Our input method uses the input manager class to update all of out input logs
	 * @param gc
	 */
	private void input(GameContainer gc){
	
		// Update our keyboard log
		Main.util.setKeyLog(Main.util.getIm().getKeyLog(gc));
		
		// Update our mouse log
		Main.util.setMouseLog(Main.util.getIm().getMouseLog(gc));
		
	}//end of input
	
	/**
	 * Make all info game updates
	 * @param gc
	 * @param sbg
	 * @param delta
	 * @throws SlickException 
	 */
	private void gameUpdates(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		
		//Check for End of Level
		endLevel(gc, sbg);
		
		//Check to see if player is dying
		alertPlayer(15);
		
		// Update Mob Attributes
		updateMobs(delta);
		updateItems();
		
		//Check to see if mobs are alive
		checkMobStatus(gc, sbg);
		
		// Update Camera Coordinates
		Main.util.getCam().updateCameraPos(mobs.get(0).getX(), mobs.get(0).getY());
		Main.util.getCam().outOfBoundsKill()
		;		
		// Open Pop-up menu
		if(!Main.util.getPlayer().isInventoryOpen() && Main.util.getKeyLogSpecificKey(6)) {
			Main.util.getSFX(0).play(1f, Main.util.getSoundManager().getVolume());
			menuOpen = !menuOpen;
			LogSystem.addToLog("Menu Open: "+menuOpen);
		}
		
		if(!menuOpen && (Main.util.getKeyLogSpecificKey(10) || Main.util.getKeyLogSpecificKey(6))) {
			Main.appgc.setMouseGrabbed(Main.util.getPlayer().isInventoryOpen());
			Main.util.getSFX(0).play(1f, Main.util.getSoundManager().getVolume());
			Main.util.getPlayer().setInventoryOpen(!Main.util.getPlayer().isInventoryOpen());
			LogSystem.addToLog("Inventory Open: "+Main.util.getPlayer().isInventoryOpen());
		}
	}//end of gameUpdates
	
	/**
	 * Founds out when the gamestate should change
	 * @param gc
	 */
	private void endLevel(GameContainer gc, StateBasedGame sbg) {
		if(Main.util.getPlayer().isReadyForNextLevel()) {
			Main.util.getPs().setFirstWrite(true);
			LogSystem.addToLog("Level Ended.");
			Main.util.levelNum++;
			newLevel(gc, sbg);
		}
	}//end of endLevel

	private void newLevel(GameContainer gc, StateBasedGame sbg){
		
		PlayerStats ps = new PlayerStats();
		ps.addToPlayerStats("" + Main.util.getPlayer().getHealth() + ", " + Main.util.getPlayer().getGravPack().getGravpower());
		for(int i = 0; i < Main.util.getInventory().getItems().size(); i++)
			ps.addToPlayerStats(Main.util.getInventory().getItems().get(i).getItemImage());
		ps.setFirstWrite(true);

		Main.util.getLevel().setScore(Main.util.getLevel().getScore() + calculateScoring());
		
		System.out.println("YOUR SCORE FOR THIS LEVEL WAS: " + Main.util.getLevel().getScore());
		
		try {
			init(gc, sbg);
		} catch (SlickException e) {
			ErrorLog.displayError(e);
		}
		
	}//end of newLevel
	
	private int calculateScoring(){
		
		int score = 500;
		
		int coinsCollected = 0;		
		
		if(Main.util.coinAGrabbed)
			coinsCollected++;
		if(Main.util.coinBGrabbed)
			coinsCollected++;
		if(Main.util.coinPGrabbed)
			coinsCollected++;
		
		if(coinsCollected == 1)
			score += 250;
		else if(coinsCollected == 2)
			score += 500;
		else if(coinsCollected == 3)
			score += 1000;
		
		score -= (Main.util.getLevelTime()/1000);
		
		score += (Main.util.getPlayer().getHealth() + Main.util.getPlayer().getGravPack().getGravpower());
		
		return score;
	}
	
	private void alertPlayer(double alertHealth) {
		if(Main.util.getPlayer().getHealth() <= alertHealth && Main.util.getPlayer().getHealth() != 0 && !alertPlayer) {
			alertPlayer = true;
			playDeathSound();
		}else if(Main.util.getPlayer().getHealth() > alertHealth) {
			Main.util.getSFX(2).stop();
			if(!Main.util.getMusic().playing())
				Main.util.getMusic().resume();
			alertPlayer = false;
		}
	}
	
	private void playDeathSound() {
		LogSystem.addToLog("Player Has Low Health.");
		Main.util.getMusic().pause();
		Main.util.getSFX(2).loop(1f, Main.util.getSoundManager().getVolume());
	}
	
	/**
	 * Perform all updates to the player object
	 * @param delta
	 * @throws SlickException 
	 */
	private void updateMobs(int delta) throws SlickException{
		// Update the mob's position
		for(int i = 0; i < mobs.size(); i++){
			mobs.get(i).update(delta);
		}
		
	}//end of updateMobs
	
	private void updateItems() {
		for(int i = 0; i < items.size(); i++)
			items.get(i).update();
	}//end of updateItems
	
	private void startAnimation() {
		
		//Starts animation for all mobs
		for(int i = 0; i < mobs.size(); i++) {
			 mobs.get(i).getLeftAni().start(); 
			 mobs.get(i).getRightAni().start();
			 mobs.get(i).getStillLeftAni().start();
			 mobs.get(i).getStillRightAni().start(); 
			 if(i == 0) {//If we are updating the player
				 mobs.get(i).getMoveLeftFlippedAni().start();
				 mobs.get(i).getMoveRightFlippedAni().start(); 
				 mobs.get(i).getStandStillLeftFlippedAni().start(); 
				 mobs.get(i).getStandStillRightFlippedAni().start(); 
			 }
		}
		
		for(int i = 0; i < items.size(); i++)
			items.get(i).getItemAni().start();
		
	}//end of stopAnimation
	
	private void updateAnimation(int delta) {
		//Animation
		for(int i = 0; i < mobs.size(); i++) {
			 mobs.get(i).getLeftAni().update(delta); // this line makes sure the speed of the Animation is true
			 mobs.get(i).getRightAni().update(delta); // this line makes sure the speed of the Animation is true
			 mobs.get(i).getStillLeftAni().update(delta); // this line makes sure the speed of the Animation is true
			 mobs.get(i).getStillRightAni().update(delta); // this line makes sure the speed of the Animation is true
			 if(i == 0) {//If we are updating the player
				 mobs.get(i).getMoveLeftFlippedAni().update(delta); // this line makes sure the speed of the Animation is true
				 mobs.get(i).getMoveRightFlippedAni().update(delta); // this line makes sure the speed of the Animation is true
				 mobs.get(i).getStandStillLeftFlippedAni().update(delta); // this line makes sure the speed of the Animation is true
				 mobs.get(i).getStandStillRightFlippedAni().update(delta); // this line makes sure the speed of the Animation is true
			 }
		}
		
		for(int i = 0; i < items.size(); i++)
			items.get(i).getItemAni().update(delta);
		
	}//end of updateAnimation

	private void stopAnimation() {
		
		//Stop animation for all mobs
		for(int i = 0; i < mobs.size(); i++) {
			 mobs.get(i).getLeftAni().stop(); 
			 mobs.get(i).getRightAni().stop();
			 mobs.get(i).getStillLeftAni().stop(); 
			 mobs.get(i).getStillRightAni().stop(); 
			 if(i == 0) {//If we are updating the player
				 mobs.get(i).getMoveLeftFlippedAni().stop();
				 mobs.get(i).getMoveRightFlippedAni().stop(); 
				 mobs.get(i).getStandStillLeftFlippedAni().stop();
				 mobs.get(i).getStandStillRightFlippedAni().stop(); 
			 }
		}
		
		for(int i = 0; i < items.size(); i++)
			items.get(i).getItemAni().stop();
		
	}//end of stopAnimation
	
	private void startPlayerAnimation() {
		Main.util.getPlayer().getCurrentImage().start();
	}
	
	private void stopPlayerAnimation() {
		Main.util.getPlayer().getCurrentImage().stop();
	}
	
	private void checkMobStatus(GameContainer gc, StateBasedGame sbg){

		for(int i = 0; i < mobs.size(); i++){
			if(mobs.get(i).getHealth() <= 0){
				Main.util.getLevel().setScore(enemyScoreCalculator(mobs.get(i))); // Update the player's score for the level based on the mobs that he kills
				mobs.get(i).setIsAlive(false);
				if(i == 0)
					try {
						resetLevel(gc, sbg);
					} catch (SlickException e) {
						ErrorLog.logError(e);
					}
			}
		}
	}//end of checkMobStatus
	
	
	// Takes in a mob that the player has killed and returns a score based on the type of enemy
	private int enemyScoreCalculator(Mob mob){
		if(mob instanceof Starter)
			return Main.util.getLevel().getScore() + 50;
		else if(mob instanceof Tank || mob instanceof Bomber)
			return Main.util.getLevel().getScore() + 100;
		else
			return 0;
	}
	
	/**
	 * Resets the level when the player dies
	 * @param sbg
	 * @throws SlickException 
	 */
	private void resetLevel(GameContainer gc, StateBasedGame sbg) throws SlickException {
		LogSystem.addToLog("Resetting Level...");
		Main.appgc.setMouseGrabbed(false);
		Main.util.getPlayer().setHealth(100);
		Main.util.getPlayer().getGravPack().setGravpower(100);
		Main.util.getMusic().stop();
		Main.util.deathCount++;
		sbg.getState(GameLevel.id).init(gc, sbg);
		sbg.enterState(Main.gameover);
	}//end of resetLevel
	
	
	public void checkCutscenes(Script script, Graphics g){
		if(Main.util.isCutsceneActive()){
			if(Main.util.countDialog < script.getSceneLength()){
				script.displayName(script.getNames());
				script.displayText(script.getDialog());
				if(Main.util.getKeyLogSpecificKey(7))
					Main.util.countDialog = Main.util.countDialog + 1;
				script.getEvents();
			}
			else {
				Main.util.setCutsceneActive(false);
				for(int i = 0; i < mobs.size(); i++) {
					if(mobs.get(i) instanceof Abric)
						mobs.get(i).setIsAlive(false);
				}
				if(script.getID() == 0) {
					Main.util.getMessageHandler().addMessage(new Message(Main.util.getLevel().getSingleEventTile(new Point(21, 8)), null, Message.autoOpenDoor, true));
				}
				Main.util.cutsceneVars.setID(Main.util.cutsceneVars.getID() + 1);
				startPlayerAnimation();
			}
		}
	}//end of checkCutscenes
	
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
	private void checkMenu(GameContainer gc, StateBasedGame sbg) 
			throws SlickException{
		
		// Create our input object
		Input input = gc.getInput();
		
		pause_menu_ui = new Image("res/gui/pausemenu.png");//Pause menu user interface
		resume = new Image("res/gui/buttons/button_resume.png");
		retry = new Image("res/gui/buttons/button_retry.png");
		saveData = new Image("res/gui/buttons/button_save.png");
		returnMenu = new Image("res/gui/buttons/button_return.png");
		
		// Resume Button
		// The parameters for checkbounds are the x and y coordinates of the top left of the button and the bottom right of the button
		if(MainMenu.checkBounds( (gc.getWidth()/2) - (resume.getWidth()/2) , (gc.getWidth()/2) - (resume.getWidth()/2) + resume.getWidth() , 325 , 325 + resume.getHeight(), xpos, ypos)){
			if(input.isMousePressed(0)){
				LogSystem.addToLog("Resuming Level.");
				Main.appgc.setMouseGrabbed(true);
				Main.util.getSFX(0).play(1f, Main.util.getSoundManager().getVolume());
				Main.util.getMusic().resume();
				startAnimation();
				alertPlayer = false;
				menuOpen = false;
			}
			resume = new Image("res/gui/buttons/button_resume_hover.png");
		}
		else if(input.isKeyPressed(Input.KEY_ESCAPE)){
			LogSystem.addToLog("Resuming Level.");
			Main.appgc.setMouseGrabbed(true);
			Main.util.getSFX(0).play(1f, Main.util.getSoundManager().getVolume());
			Main.util.getMusic().resume();
			startAnimation();
			alertPlayer = false;
			menuOpen = false; // ! Makes the escape toggle
		}
		
		// Retry Button
		// The parameters for checkbounds are the x and y coordinates of the top left of the button and the bottom right of the button
		if(MainMenu.checkBounds( (gc.getWidth()/2) - (retry.getWidth()/2) , (gc.getWidth()/2) - (retry.getWidth()/2) + retry.getWidth() , 405 , 405 + retry.getHeight(), xpos, ypos)){
			if(input.isMousePressed(0)){
				LogSystem.addToLog("Resetting Level...");
				input.clearKeyPressedRecord();
				menuOpen = false;
				Main.util.getMusic().stop();
				sbg.getState(GameLevel.id).init(gc, sbg);
				Main.util.setMusic(Main.util.getMusicQueue(1));
				Main.util.getMusic().loop(1f, Main.util.getMusicManager().getVolume());
			}
			retry = new Image("res/gui/buttons/button_retry_hover.png");
		}
		
		// Save Button
		// The parameters for checkbounds are the x and y coordinates of the top left of the button and the bottom right of the button
		if(MainMenu.checkBounds( (gc.getWidth()/2) - (saveData.getWidth()/2) , (gc.getWidth()/2) - (saveData.getWidth()/2) + saveData.getWidth() , 485 , 485 + saveData.getHeight(), xpos, ypos)){
			if(input.isMousePressed(0)){
				LogSystem.addToLog("Save Menu Button Pressed By User.");
				input.clearKeyPressedRecord();
				menuOpen = false;
				saveOpen = true;
			}
			saveData = new Image("res/gui/buttons/button_save_hover.png");
		}
		
		// Return Button
		// The parameters for checkbounds are the x and y coordinates of the top left of the button and the bottom right of the button
		if(MainMenu.checkBounds( (gc.getWidth()/2) - (returnMenu.getWidth()/2) , (gc.getWidth()/2) - (returnMenu.getWidth()/2) + returnMenu.getWidth() , 565 , 565 + returnMenu.getHeight(), xpos, ypos)){
			if(input.isMousePressed(0)){
				LogSystem.addToLog("Returning to Main Menu...");
				LogSystem.addToLog("");
				input.clearKeyPressedRecord();
				Main.util.getMusic().stop();
				Main.util.setMusic(Main.util.getMusicQueue(0));
				Main.util.getMusic().loop(1f, Main.util.getMusicManager().getVolume());
				startAnimation();
				sbg.enterState(Main.mainmenu);
				menuOpen = false;
			}
			returnMenu = new Image("res/gui/buttons/button_return_hover.png");
		}
		
	}//end of checkMenu
	
	/**
	 * @method checkSaveMenu
	 * @description checks the button detection and handles the according events in the  save menu
	 * 
	 * @param
	 * GameContainer gc
	 * 
	 * @return
	 * 	void:
	 * @throws SlickException 
	 */
	private void checkSaveMenu(GameContainer gc, StateBasedGame sbg) 
			throws SlickException{
		
		Input input = gc.getInput();
		
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
		if(MainMenu.checkBounds( 100 , 100 + slotOne.getWidth() , 150, 150 + slotOne.getHeight(), xpos, ypos)){
			if(input.isMousePressed(0)){
				LogSystem.addToLog("Saving Data To Slot One...");
				SaveGame saveProgress = new SaveGame(mobs, items, Main.util.getInventory().getItems(), Main.util.levelEvents.getEvents(), Main.util.levelNum, Main.util.getCam().getX(), Main.util.getCam().getY(), 1, Main.util.getLevelTime());
				saveProgress.createSave();
				LogSystem.addToLog("Data Saved Successfully.");
			}
			if(Main.util.getSlotOneData().getLoadFile() != null)
				slotOne = new Image("res/gui/save_slot_hover.png");
			else
				slotOne = new Image("res/gui/empty_save_slot_hover.png");
		}
		
		// Slot 2 Button
		// The parameters for checkbounds are the x and y coordinates of the top left of the button and the bottom right of the button
		if(MainMenu.checkBounds( 100 , 100 + slotTwo.getWidth() , 350, 350 + slotTwo.getHeight(), xpos, ypos)){
			if(input.isMousePressed(0)){
				LogSystem.addToLog("Saving Data To Slot Two...");
				SaveGame saveProgress = new SaveGame(mobs, items, Main.util.getInventory().getItems(), Main.util.levelEvents.getEvents(), Main.util.levelNum, Main.util.getCam().getX(), Main.util.getCam().getY(), 2, Main.util.getLevelTime());
				saveProgress.createSave();
				LogSystem.addToLog("Data Saved Successfully.");
			}
			if(Main.util.getSlotTwoData().getLoadFile() != null)
				slotTwo = new Image("res/gui/save_slot_hover.png");
			else
				slotTwo = new Image("res/gui/empty_save_slot_hover.png");
		}
		
		// Slot 3 Button
		// The parameters for checkbounds are the x and y coordinates of the top left of the button and the bottom right of the button
		if(MainMenu.checkBounds( 100 , 100 + slotThree.getWidth() , 550, 550 + slotThree.getHeight(), xpos, ypos)){
			if(input.isMousePressed(0)){
				LogSystem.addToLog("Saving Data To Slot Three...");
				SaveGame saveProgress = new SaveGame(mobs, items, Main.util.getInventory().getItems(), Main.util.levelEvents.getEvents(), Main.util.levelNum, Main.util.getCam().getX(), Main.util.getCam().getY(), 3, Main.util.getLevelTime());
				saveProgress.createSave();
				LogSystem.addToLog("Data Saved Successfully.");
			}
			if(Main.util.getSlotThreeData().getLoadFile() != null)
				slotThree = new Image("res/gui/save_slot_hover.png");
			else
				slotThree = new Image("res/gui/empty_save_slot_hover.png");
		}
		
		// Back Button
		// The parameters for checkbounds are the x and y coordinates of the top left of the button and the bottom right of the button
		if(MainMenu.checkBounds( (gc.getWidth()/2) - (back.getWidth()/2) , (gc.getWidth()/2) - (back.getWidth()/2) + back.getWidth() , 750 , 750 + back.getHeight(), xpos, ypos)){
			if(input.isMousePressed(0)){
				LogSystem.addToLog("Returning To Pause Menu.");
				saveOpen = false;
				menuOpen = true;
			}
			back = new Image("res/gui/buttons/button_back_hover.png");
		}
		else if(input.isKeyPressed(Input.KEY_ESCAPE)) {
			LogSystem.addToLog("Returning To Pause Menu.");
			saveOpen = false;
			menuOpen = true;
		}
		
	}//end of checkSaveMenu
	
	/**
	 * @method checknventoryMenu
	 * @description checks mouse actions for the inventory menu
	 * 
	 * @param
	 * GameContainer gc, StateBasedGame sbg, Input input
	 * 
	 * @return
	 * 	void:
	 * @throws SlickException 
	 */
	private void checkInventoryMenu(GameContainer gc) 
			throws SlickException{
		
		// Create our input object
		Input input = gc.getInput();
		
		//Inventory Images
		inventoryBG = new Image("res/gui/inventory/inventoryBG.png");
		for(int i = 0; i < itemSlots.length; i++)
			for(int x = 0; x < itemSlots[0].length; x++)
				itemSlots[i][x] = new Image("res/gui/inventory/item_slot.png");
		
		// Inventory Slots
		// The parameters for checkbounds are the x and y coordinates of the top left of the button and the bottom right of the button
				//Slot 1
				if(MainMenu.checkBounds(35, 35 + itemSlots[0][0].getWidth() , 248, 248 + itemSlots[0][0].getHeight(), xpos, ypos)){
					if(input.isMousePressed(0)){
						useInventory(0);
						}
					itemSlots[0][0] = new Image("res/gui/inventory/item_slot_hover.png");
					}
				
				//Slot 2
				if(MainMenu.checkBounds(251, 251 + itemSlots[0][1].getWidth() , 248, 248 + itemSlots[0][1].getHeight(), xpos, ypos)){
					if(input.isMousePressed(0)){
						useInventory(1);
						}
					itemSlots[0][1] = new Image("res/gui/inventory/item_slot_hover.png");
					}
				
				//Slot 3
				if(MainMenu.checkBounds(467, 467 + itemSlots[0][2].getWidth() , 248, 248 + itemSlots[0][2].getHeight(), xpos, ypos)){
					if(input.isMousePressed(0)){
						useInventory(2);
						}
					itemSlots[0][2] = new Image("res/gui/inventory/item_slot_hover.png");
					}
				
				//Slot 4
				if(MainMenu.checkBounds(683, 683 + itemSlots[0][3].getWidth() , 248, 248 + itemSlots[0][3].getHeight(), xpos, ypos)){
					if(input.isMousePressed(0)){
						useInventory(3);
						}
					itemSlots[0][3] = new Image("res/gui/inventory/item_slot_hover.png");
					}
				
				//Slot 5
				if(MainMenu.checkBounds(35, 35 + itemSlots[1][0].getWidth() , 553, 553 + itemSlots[1][0].getHeight(), xpos, ypos)){
					if(input.isMousePressed(0)){
						useInventory(4);
						}
					itemSlots[1][0] = new Image("res/gui/inventory/item_slot_hover.png");
					}
				
				//Slot 6
				if(MainMenu.checkBounds(251, 251 + itemSlots[1][1].getWidth() , 553, 553 + itemSlots[1][1].getHeight(), xpos, ypos)){
					if(input.isMousePressed(0)){
						useInventory(5);
						}
					itemSlots[1][1] = new Image("res/gui/inventory/item_slot_hover.png");
					}
				
				//Slot 7
				if(MainMenu.checkBounds(467, 467 + itemSlots[1][2].getWidth() , 553, 553 + itemSlots[1][2].getHeight(), xpos, ypos)){
					if(input.isMousePressed(0)){
						useInventory(6);
						}
					itemSlots[1][2] = new Image("res/gui/inventory/item_slot_hover.png");
					}
				
				//Slot 8
				if(MainMenu.checkBounds(683, 683 + itemSlots[1][3].getWidth() , 553, 553 + itemSlots[1][3].getHeight(), xpos, ypos)){
					if(input.isMousePressed(0)){
						useInventory(7);
						}
					itemSlots[1][3] = new Image("res/gui/inventory/item_slot_hover.png");
					}
		
	}//end of checkInventoryMenu
	
	private void useInventory(int pos) {
		if(pos < Main.util.getInventory().getItems().size()) {
			if(Main.util.getInventory().getItems().get(pos) instanceof HealthPack){
				new UseItem("health");
				Main.util.getInventory().removeFromInventory(Main.util.getInventory().getItems().get(pos));
			} else if(Main.util.getInventory().getItems().get(pos) instanceof GravCapsule){
				new UseItem("gravcapsule");
				Main.util.getInventory().removeFromInventory(Main.util.getInventory().getItems().get(pos));
			}
		}
	}//end of useInventory
	
	public int getID() {
		return GameLevel.id;
	}//end of getID
	
}//end of class
