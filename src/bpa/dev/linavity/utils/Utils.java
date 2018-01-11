package bpa.dev.linavity.utils;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import bpa.dev.linavity.entities.Player;
import bpa.dev.linavity.events.MessageHandler;
import bpa.dev.linavity.physics.Gravity;
import bpa.dev.linavity.world.Level;

public class Utils {

	/*
	 * This class is used to store objects that will be used throughout all of the gamestates
	 */
	
	public final boolean debugMode = true;
	
	private Player player; // Player Object
	private MessageHandler messageHandler; // Message Handler Object
	private Level level; // Level Object
	
	private Music music;
	private Sound sfx;
	
	// Default Constructor
	public Utils() throws SlickException{
		this.player = new Player(this);
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
	
	public Sound getSFX() {
		return sfx;
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
	
	public void setSFX(String sfxDirectory) {
		try {
			sfx = new Sound(sfxDirectory);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
}//end of class
