package bpa.dev.linavity.entities.tiles.foreground.dynamicTiles;

import org.newdawn.slick.SlickException;

import bpa.dev.linavity.entities.tiles.Tile;

public class Dynamic extends Tile {

	private int messageSendType;
	
	public Dynamic(int i, int j, int id, int mst) throws SlickException {
		super(i, j, id);
		this.setMessageSendType(mst);
	}

	// Getters
	
	/**
	 * @return the messageSendType
	 */
	public int getMessageSendType() {
		return messageSendType;
	}
	
	// Setters

	/**
	 * @param messageSendType the messageSendType to set
	 */
	public void setMessageSendType(int messageSendType) {
		this.messageSendType = messageSendType;
	}

}
