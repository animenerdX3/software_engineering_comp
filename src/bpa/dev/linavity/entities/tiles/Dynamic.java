package bpa.dev.linavity.entities.tiles;

import java.awt.Point;
import java.awt.Rectangle;

import org.newdawn.slick.SlickException;

public class Dynamic extends Tile {

	private Point[] targetObjects;
	
	public Dynamic(int i, int j, int id, int mst, int xOffset, int yOffset, int width, int height) throws SlickException {
		super(i, j, id);
		this.width = width;
		this.height = height;
		this.x = this.x + xOffset;
		this.y = this.y + yOffset;
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
