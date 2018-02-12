package bpa.dev.linavity.utils;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import bpa.dev.linavity.assets.InputManager;
import bpa.dev.linavity.assets.MusicManager;
import bpa.dev.linavity.assets.SoundManager;
import bpa.dev.linavity.collectibles.Inventory;
import bpa.dev.linavity.collectibles.Item;
import bpa.dev.linavity.cutscenes.CutsceneAccessor;
import bpa.dev.linavity.cutscenes.CutsceneManager;
import bpa.dev.linavity.entities.Camera;
import bpa.dev.linavity.entities.Mob;
import bpa.dev.linavity.entities.Player;
import bpa.dev.linavity.events.EventAccessor;
import bpa.dev.linavity.events.MessageHandler;
import bpa.dev.linavity.physics.Gravity;
import bpa.dev.linavity.world.Level;

public class Utils {

	/*
	 * This class is used to store objects that will be used throughout all of the gamestates
	 */
	
	//Debug Mode
	public final boolean debugMode = false;
	
	private String playerName;
	
	// Universal Game Objects
	private Level level; // Level Object
	private Player player; // Player Object
	private MessageHandler messageHandler; // Message Handler Object
	private Gravity gravity; // Gravity Object
	public Camera cam; // Camera Object
	
	// List of all user inputs
	public InputManager im = new InputManager();
	private boolean[] keyLog = new boolean[im.getKeyLogLength()]; // Keyboard
	private int[] mouseLog = new int[im.getMouseLogLength()]; // Mouse
	
	// Music Objects
	private MusicManager musicManager = new MusicManager("data/music_paths.assets");
	private Music music;
	private String [] musicQueue;
	
	//Sound Effect Objects
	SoundManager sfxFiles = new SoundManager("data/sound_paths.assets");
	private Sound [] sfx;

	//Cutscene Objects
	CutsceneManager cutscenes = new CutsceneManager("dialogue");
	private boolean cutsceneActive;
	public CutsceneAccessor cutsceneVars = new CutsceneAccessor();
	
	//Event Objects
	public EventAccessor levelEvents = new EventAccessor();
	
	//Counters for each cutscene
	public int countDialog = 0;
	public int idCounter;
	public int startTop = -150;
	public int startBottom = 150;
	
	//Loading Objects
	private LoadGame slotOneData;
	private LoadGame slotTwoData;
	private LoadGame slotThreeData;
	private LoadGame currentLoadData;
	public boolean loadGame = false;
	
	private PlayerStats ps;
	
	//The mobs in the current level
	private ArrayList<Mob> levelMobs;
	private ArrayList<Item> levelItems;
	
	//The current level's timer
	private int levelTime;
	
	//The inventory system
	private Inventory inventory;
	
	public GameContainer gc;
	public int delta;
	
	public int levelNum;
	
	//Coins
	public boolean coinBGrabbed;
	public boolean coinPGrabbed;
	public boolean coinAGrabbed;

	// Default Constructor
	public Utils(int levelID) throws SlickException{
		LogSystem.addToLog("Debug Mode: "+this.debugMode);
		LogSystem.addToLog("");
		//Set Load Slots
		this.slotOneData = new LoadGame(1);
		this.slotTwoData = new LoadGame(2);
		this.slotThreeData = new LoadGame(3);
		//Create default player position
		this.levelNum = levelID;
		//Create universal camera object
	    this.cam = new Camera(0, 0);
	    //Create music queue
		this.musicQueue = musicManager.getMusicQueue();
		//Create sound effect array
		this.sfx = sfxFiles.getSfx();
		//Set universal gravity
		this.gravity = new Gravity();
		//Create universal message handler
		this.setMessageHandler(new MessageHandler());
		//Set timer to 0
		this.levelTime = 0;
		this.inventory = new Inventory();
		this.cutsceneActive = false;
		this.ps = new PlayerStats();
		
		this.coinBGrabbed = false;
		this.coinPGrabbed = false;
		this.coinAGrabbed = false;
	}
	
	
	/* GETTERS */
	
	/**
	 * @return the ps
	 */
	public PlayerStats getPs() {
		return ps;
	}
	
	/**
	 * 
	 * @return the camera object
	 */
	public Camera getCam() {
		return cam;
	}

	/**
	 * 
	 * @return the input object
	 */
	public InputManager getIm() {
		return im;
	}

	/**
	 * 
	 * @param i current key
	 * @return if true, the key is pressed. if false, the key is not pressed
	 */
	public boolean getKeyLogSpecificKey(int i) {
		return keyLog[i];
	}

	/**
	 * 
	 * @param i
	 * @return if true, the mouse button is pressed. if false, the mouse button is not pressed
	 */
	public int getMouseLogSpecificMouse(int i) {
		return mouseLog[i];
	}

	/**
	 * 
	 * @return the player object
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * 
	 * @return the gravity object
	 */
	public Gravity getGravity() {
		return gravity;
	}
	
	/**
	 * 
	 * @return the music object
	 */
	public Music getMusic() {
		return music;
	}
	
	/**
	 * 
	 * @return the music manager object
	 */
	public MusicManager getMusicManager() {
		return musicManager;
	}
	
	/**
	 * 
	 * @return the sound manager object
	 */
	public SoundManager getSoundManager() {
		return sfxFiles;
	}
	
	/**
	 * 
	 * @param i the queue position
	 * @return the music object at the queue position
	 */
	public String getMusicQueue(int i) {
		return musicQueue[i];
	}
	
	/**
	 * 
	 * @param i the sfx position
	 * @return the sound effect object at the sfx position
	 */
	public Sound getSFX(int i) {
		return sfx[i];
	}
	
	/**
	 * @return the messageHandler object
	 */
	public MessageHandler getMessageHandler() {
		return messageHandler;
	}
	
	/**
	 * @return the level map
	 */
	public Level getLevel() {
		return level;
	}

	/**
	 * 
	 * @return if true, load the game in the init. if false, do not load the game in the init
	 */
	public boolean getLoadGame() {
		return loadGame;
	}
	
	/**
	 * 
	 * @return data in the first load slot
	 */
	public LoadGame getSlotOneData() {
		return slotOneData;
	}
	
	/**
	 * 
	 * @return data in the second load slot
	 */
	public LoadGame getSlotTwoData() {
		return slotTwoData;
	}
	
	/**
	 * 
	 * @return data in the third load slot
	 */
	public LoadGame getSlotThreeData() {
		return slotThreeData;
	}
	
	/**
	 * 
	 * @return the current load slot
	 */
	public LoadGame getCurrentLoadData() {
		return currentLoadData;
	}
	
	/**
	 * 
	 * @return all of the mobs in the current level
	 */
	public ArrayList<Mob> getLevelMobs() {
		return levelMobs;
	}
	
	/**
	 * 
	 * @return the level timer
	 */
	public int getLevelTime() {
		return levelTime;
	}
	
	/**
	 * 
	 * @return the universal inventory
	 */
	public Inventory getInventory() {
		return inventory;
	}
	
	/**
	 * 
	 * @return the cutscene manager
	 */
	public CutsceneManager getCutscenes() {
		return cutscenes;
	}
	
	/**
	 * 
	 * @return if true, a cutscene is active. if false, the cutscene is not active
	 */
	public boolean isCutsceneActive() {
		return cutsceneActive;
	}
	
	/**
	 * 
	 * @return the level's items
	 */
	public ArrayList<Item> getLevelItems() {
		return levelItems;
	}
	
	/**
	 * 
	 * @return the player's name
	 */
	public String getPlayerName() {
		return playerName;
	}
	
	/* SETTERS */

	/**
	 * @param ps the ps to set
	 */
	public void setPs(PlayerStats ps) {
		this.ps = ps;
	}

	/**
	 * changes player object
	 * @param player
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	/**
	 * changes the camera object
	 * @param cam
	 */
	public void setCam(Camera cam) {
		this.cam = cam;
	}
	
	/**
	 * changes the input object
	 * @param im
	 */
	public void setIm(InputManager im) {
		this.im = im;
	}

	/**
	 * changes the keyLog
	 * @param keyLog
	 */
	public void setKeyLog(boolean[] keyLog) {
		this.keyLog = keyLog;
	}

	/**
	 * changes the mouseLog
	 * @param mouseLog
	 */
	public void setMouseLog(int[] mouseLog) {
		this.mouseLog = mouseLog;
	}

	/**
	 * changes the messageHandler
	 * @param messageHandler
	 */
	public void setMessageHandler(MessageHandler messageHandler) {
		this.messageHandler = messageHandler;
	}

	/**
	 * changes the level map
	 * @param level
	 */
	public void setLevel(Level level) {
		this.level = level;
	}
	
	/**
	 * changes the current music object
	 * @param musicDirectory
	 */
	public void setMusic(String musicDirectory) {
		try {
			music = new Music(musicDirectory);
		} catch (SlickException e) {
			ErrorLog.logError(e);
		}
	}
	
	/**
	 * changes the sound effects object
	 * @param sfx
	 */
	public void setSFX(Sound [] sfx) {
		this.sfx = sfx;
	}
	
	/**
	 * changes the slot one data
	 * @param slotOneData
	 */
	public void setSlotOneData(LoadGame slotOneData) {
		this.slotOneData = slotOneData;
	}
	
	/**
	 * changes the slot two data
	 * @param slotTwoData
	 */
	public void setSlotTwoData(LoadGame slotTwoData) {
		this.slotTwoData = slotTwoData;
	}
	
	/**
	 * changes the slot three data
	 * @param slotThreeData
	 */
	public void setSlotThreeData(LoadGame slotThreeData) {
		this.slotThreeData = slotThreeData;
	}
	
	/**
	 * changes the current load data
	 * @param currentLoadData
	 */
	public void setCurrentLoadData(LoadGame currentLoadData) {
		this.currentLoadData = currentLoadData;
	}
	
	/**
	 * changes the load game boolean
	 * @param loadGame
	 */
	public void setLoadGame(boolean loadGame) {
		this.loadGame = loadGame;
	}
	
	/**
	 * changes the current level mob arraylist
	 * @param levelMobs
	 */
	public void setLevelMobs(ArrayList<Mob>levelMobs) {
		this.levelMobs = levelMobs;
	}
	
	/**
	 * changes the level timer
	 * @param levelTime
	 */
	public void setLevelTime(int levelTime) {
		this.levelTime = levelTime;
	}

	/**
	 * changes the inventory system
	 * @param inventory
	 */
	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}
	
	/**
	 * changes the cutscene manager
	 * @param cutscenes
	 */
	public void setCutscenes(CutsceneManager cutscenes) {
		this.cutscenes = cutscenes;
	}

	/**
	 * changes the cutscene active boolean
	 * @param cutsceneActive
	 */
	public void setCutsceneActive(boolean cutsceneActive) {
		this.cutsceneActive = cutsceneActive;
	}
	
	/**
	 * changes the level items
	 * @param levelItems
	 */
	public void setLevelItems(ArrayList<Item> levelItems) {
		this.levelItems = levelItems;
	}
	
	/**
	 * changes the player's name
	 * @param playerName
	 */
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	
}//end of class
