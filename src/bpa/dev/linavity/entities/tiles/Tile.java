package bpa.dev.linavity.entities.tiles;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import bpa.dev.linavity.GameObject;

public class Tile extends GameObject implements Shape {

	// Final Ints for dynamic tile ID's
	public final static int eventTileID = 12;
	public final static int gravPadID = 13;
	public final static int warpHoleID = 20;
	public final static int leverID = 21;
	public final static int ladderTopID = 23;
	public final static int ladderMiddleID = 24;
	public final static int ladderBottomID = 25;
	public final static int spikesID = 27;
	public final static int doorID = 31;
	
	// Tile ID
	private int id;
	
	// X and Y position of the tile in the level (not relative position)
	protected float x, y;
	protected float xOffset, yOffset;

	// Width and height of the tiles (Right now, typically 64x64)
	protected int height, width;
	
	// Tells the game whether or not the tile lets other objects pass through it.
	private boolean passable;
	
	// Toggle boolean for dynamic tiles that need one
	boolean toggle = false;

	// the image of our tile
	protected Image texture = null;
	
	// Possible paths for our image files
	TileManager all_tiles = new TileManager("texture_paths");
	String[] texturePaths = all_tiles.getTextures();
	boolean[] isPassable = all_tiles.getPassable();
	
	protected Rectangle collisionBox;
	
	public Tile(int i, int j, int id) 
			throws SlickException{
		this.height = 50; // Current height of our tiles
		this.width = 50; // Current width of our tiles
		this.x = j * 50; // I & J are the positions of the tile in the 2d array of tiles that make up our level
		this.y = i * 50; // We multiply by 64 since that is the height and width of the tile, to get the proper coordinate of the tile in our level
		this.id = id;
		this.xOffset = 0;
		this.yOffset = 0;

		
			if(isPassable[id])
				passable = true;
			else
				passable = false;
			
		this.texture = new Image(texturePaths[id]);
		this.collisionBox = new Rectangle((int) (this.x + this.xOffset), (int) (this.y+this.yOffset), (int) this.width, (int) this.height);
		
	}
	
	public void onCollide(GameObject go) throws SlickException {
		// Special Collision functions
	}
	
	// Getters
	
	/**
	 * @return the targetObjects
	 */
	public Point[] getTargetObjects() {
		return null;
	}	

	/**
	 * @return the passable
	 */
	public boolean isPassable() {
		return passable;
	}
	
	public int getId() {
		return id;
	}


	public float getX() {
		return x;
	}


	public float getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Image getTexture() {
		return texture;
	}
	
	public Rectangle getCollisionBox() {
		return collisionBox;
	}
	
	
	// Setters

	/**
	 * @param targetObjects the targetObjects to set
	 */
	public void setTargetObjects(Point[] targetObjects) {
	}
	
	/**
	 * @param passable the passable to set
	 */
	public void setPassable(boolean passable) {
		this.passable = passable;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setTexture(Image texture) {
		this.texture = texture;
	}
	
	public void setCollisionBox(Rectangle collisionBox) {
		this.collisionBox = collisionBox;
	}
	

/*
 * 	Methods Used for Implementing Shape, Some May Not Be Used
 */
	
	@Override
	public boolean contains(Point2D arg0) {
		return false;
	}

	@Override
	public boolean contains(Rectangle2D arg0) {
		return false;
	}

	@Override
	public boolean contains(double arg0, double arg1) {
		return false;
	}

	@Override
	public boolean contains(double arg0, double arg1, double arg2, double arg3) {
		return false;
	}

	@Override
	public java.awt.Rectangle getBounds() {
		return null;
	}

	@Override
	public Rectangle2D getBounds2D() {
		return null;
	}

	@Override
	public PathIterator getPathIterator(AffineTransform arg0) {
		return null;
	}

	@Override
	public PathIterator getPathIterator(AffineTransform arg0, double arg1) {
		return null;
	}

	@Override
	public boolean intersects(Rectangle2D arg0) {
		return false;
	}

	@Override
	public boolean intersects(double arg0, double arg1, double arg2, double arg3) {
		return false;
	}
	
}
