package bpa.dev.linavity.utils;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import bpa.dev.linavity.assets.InputManager;
import bpa.dev.linavity.assets.MusicManager;
import bpa.dev.linavity.assets.SoundManager;
import bpa.dev.linavity.entities.Camera;
import bpa.dev.linavity.entities.Player;
import bpa.dev.linavity.entities.tiles.interactive.Lever;
import bpa.dev.linavity.events.MessageHandler;
import bpa.dev.linavity.physics.Gravity;
import bpa.dev.linavity.world.Level;

public class Utils {

	/*
	 * This class is used to store objects that will be used throughout all of the gamestates
	 */
	
	public final boolean debugMode = true;
	
	// Universal Game Objects
	private Player player; // Player Object
	private MessageHandler messageHandler; // Message Handler Object
	private Level level; // Level Object
	private Level events;//Level Object For Events
	private Gravity gravity; // Gravity Object
	public Camera cam; // Camera Object
	
	// List of all user inputs
	public InputManager im = new InputManager();
	private boolean[] keyLog = new boolean[im.getKeyLogLength()]; // Keyboard
	private int[] mouseLog = new int[im.getMouseLogLength()]; // Mouse
	
	// Music Objects
	private MusicManager musicManager = new MusicManager("data/music_paths.txt");
	private Music music;
	SoundManager sfxFiles = new SoundManager("data/sound_paths.txt");
	private String [] musicQueue;
	private Sound [] sfx;
	
	public Lever testLever = new Lever();
	
	// Default Constructor
	public Utils() throws SlickException{
		this.player = new Player(450, 1100);
	    this.cam = new Camera(this.getPlayer().getX(), this.getPlayer().getY());
		this.musicQueue = musicManager.getMusicQueue();
		this.sfx = sfxFiles.getSfx();
		this.gravity = new Gravity();
		this.setMessageHandler(new MessageHandler());
	}
	
	
	// Getters
	
	public Camera getCam() {
		return cam;
	}

	public InputManager getIm() {
		return im;
	}


	public boolean getKeyLogSpecificKey(int i) {
		return keyLog[i];
	}


	public int getMouseLogSpecificMouse(int i) {
		return mouseLog[i];
	}

	public Player getPlayer() {
		return player;
	}

	public Gravity getGravity() {
		return gravity;
	}
	
	public Music getMusic() {
		return music;
	}
	
	public MusicManager getMusicManager() {
		return musicManager;
	}
	
	public SoundManager getSoundManager() {
		return sfxFiles;
	}
	
	public String getMusicQueue(int i) {
		return musicQueue[i];
	}
	
	public Sound getSFX(int i) {
		return sfx[i];
	}
	
	/**
	 * @return the messageHandler
	 */
	public MessageHandler getMessageHandler() {
		return messageHandler;
	}
	
	/**
	 * @return the level
	 */
	public Level getLevel() {
		return level;
	}
	
	/**
	 * @return the events
	 */
	public Level getEvents() {
		return events;
	}
	
	// Setters
	
	public void setCam(Camera cam) {
		this.cam = cam;
	}
	
	public void setIm(InputManager im) {
		this.im = im;
	}


	public void setKeyLog(boolean[] keyLog) {
		this.keyLog = keyLog;
	}


	public void setMouseLog(int[] mouseLog) {
		this.mouseLog = mouseLog;
	}

	/**
	 * @param messageHandler the messageHandler to set
	 */
	public void setMessageHandler(MessageHandler messageHandler) {
		this.messageHandler = messageHandler;
	}

	/**
	 * @param level the level to set
	 */
	public void setLevel(Level level) {
		this.level = level;
	}
	
	/**
	 * @param events the events to set
	 */
	public void setEvents(Level events) {
		this.events = events;
	}
	
	public void setMusic(String musicDirectory) {
		try {
			music = new Music(musicDirectory);
		} catch (SlickException e) {
			ErrorLog.logError(e);
		}
	}
	
	public void setSFX(Sound [] sfx) {
		this.sfx = sfx;
	}
	
}//end of class
