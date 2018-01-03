package bpa.dev.linavity.entities.tiles;

import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class Tile extends GameObject implements Shape {

	// Tile ID
	private int id;
	
	// X and Y position of the tile in the level (not relative position)
	private float x, y;

	// Width and height of the tiles (Right now, typically 64x64)
	private int height, width;
	
	// Tells the game whether or not the tile lets other objects pass through it.
	private boolean passable;

	// the image of our tile
	private Image texture = null;
	
	// Possible paths for our image files
	TileManager all_tiles = new TileManager("texture_paths");
	String[] texturePaths = all_tiles.getTextures();
	boolean[] isPassable = all_tiles.getPassable();
	
	public Tile(int i, int j, int id) 
			throws SlickException{
		this.height = 50; // Current height of our tiles
		this.width = 50; // Current width of our tiles
		this.x = j * 50; // I & J are the positions of the tile in the 2d array of tiles that make up our level
		this.y = i * 50; // We multiply by 64 since that is the height and width of the tile, to get the proper coordinate of the tile in our level
		this.id = id;

		
			if(isPassable[id])
				passable = true;
			else
				passable = false;
			
		this.texture = new Image(texturePaths[id]);
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
	
	/* SETTERS */
	

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
