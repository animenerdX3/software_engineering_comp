package bpa.dev.linavity.entities.tiles;

import java.awt.Point;

import org.newdawn.slick.SlickException;

import bpa.dev.linavity.GameObject;
import bpa.dev.linavity.events.Message;

public class Dynamic extends Tile {

	// An array of points, indicating which objects this one will interact with.
	protected Point[] targetObjects;
	
	// A toggle boolean used for different tiles that toggle
	protected boolean toggle;
	
	public Dynamic(int i, int j, int id, int xOffset, int yOffset, int width, int height) throws SlickException {
		super(i, j, id);
		this.width = width;
		this.height = height;
		this.x = this.x + xOffset;
		this.y = this.y + yOffset;
		this.toggle = false;
	}
	
	public void onMessage(Message message) throws SlickException{
		// Event Handling
	}
	
	public void onCollide(GameObject go) throws SlickException {
	}

	// Getters
	
	/**
	 * @return the targetObjects
	 */
	@Override
	public Point[] getTargetObjects() {
		return targetObjects;
	}	
	
	// Setters

	/**
	 * @param targetObjects the targetObjects to set
	 */
	@Override
	public void setTargetObjects(Point[] targetObjects) {
		this.targetObjects = targetObjects;
	}
	
}
