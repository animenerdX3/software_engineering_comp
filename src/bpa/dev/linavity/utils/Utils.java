package bpa.dev.linavity.utils;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import bpa.dev.linavity.assets.MusicManager;
import bpa.dev.linavity.assets.SoundManager;
import bpa.dev.linavity.entities.Player;
import bpa.dev.linavity.events.MessageHandler;
import bpa.dev.linavity.physics.Gravity;
import bpa.dev.linavity.world.Level;

public class Utils {

	/*
	 * This class is used to store objects that will be used throughout all of the gamestates
	 */
	
	public final boolean debugMode = false;
	
	private Player player; // Player Object
	private MessageHandler messageHandler; // Message Handler Object
	private Level level; // Level Object
	
	private MusicManager musicManager = new MusicManager("data/music_paths.txt");
	
	private Music music;
	
	SoundManager sfxFiles = new SoundManager("data/sound_paths.txt");
	
	private String [] musicQueue;
	
	private Sound [] sfx;
	
	// Default Constructor
	public Utils() throws SlickException{
		this.player = new Player(this);
		this.musicQueue = musicManager.getMusicQueue();
		this.sfx = sfxFiles.getSfx();
		this.setMessageHandler(new MessageHandler());
	}
	
	
	// Getters
	
	public Player getPlayer() {
		return player;
	}

	public Gravity getGravity() {
		return player.getGravity();
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
	
	// Setters

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
	
	public void setMusic(String musicDirectory) {
		try {
			music = new Music(musicDirectory);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public void setSFX(Sound [] sfx) {
		this.sfx = sfx;
	}
	
}//end of class
