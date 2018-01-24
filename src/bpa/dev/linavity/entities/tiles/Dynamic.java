package bpa.dev.linavity.entities.tiles;

import java.awt.Rectangle;

import org.newdawn.slick.SlickException;

public class Dynamic extends Tile {

	private int messageSendType;
	
	public Dynamic(int i, int j, int id, int mst, int xOffset, int yOffset, int width, int height) throws SlickException {
		super(i, j, id);
		this.setMessageSendType(mst);
		this.width = width;
		this.height = height;
		this.x = this.x + xOffset;
		this.y = this.y + yOffset;
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
