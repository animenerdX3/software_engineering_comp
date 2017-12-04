package bpa.dev.linavity.entities;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Tile {

	// Tile ID
	int id;
	
	// X and Y position of the tile in the level (not relative position)
	int x, y;
	
	// Width and height of the tiles (Right now, typically 64x64)
	int height, width;
	
	// the image of our tile
	private Image texture = null;
	
	// Possible paths for our image files
	String[] texturePaths = {"res/floor.png", "res/floor_rust1.png", "res/floor_rust2.png"};
	
	public Tile(int x, int y, int id) 
			throws SlickException{
		this.height = 64;
		this.width = 64;
		this.x = x;
		this.y = y;
		this.id = id;
		this.texture = new Image(texturePaths[id]);
	}
	
}
