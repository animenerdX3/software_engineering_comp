package bpa.dev.linavity.world;

import org.newdawn.slick.SlickException;

import bpa.dev.linavity.entities.Tile;

public class Level {

	// Level ID
	int id;
	
	// 2d array of tile objects that makes up the level
	private Tile[][] tiles;

	/**
	 * Constructor: Creates a level for our gamestate
	 * 
	 * @param id	id for the tiles
	 * @param tileIDs	2D array for the level tile IDs
	 * 
	 * 
	 */
	public Level(int id, int[][] tileIDs) 
			throws SlickException{
		tiles = new Tile[tileIDs.length][tileIDs[0].length];//Create a 2D array with the same size as the tileIDs array
		tiles = createLevel(tileIDs);//Populate the array with tiles based on the tilesID array
	}
	
	/**
	 * Converts the 2D array of ids to an array of tile objects
	 * 
	 * @param tileIDs	2D tile ID array
	 * @return tiles	a 2D array of tile objects for the level
	 * 
	 */
	private Tile[][] createLevel(int[][] tileIDs) 
			throws SlickException {
		
		for(int i = 0; i < tileIDs.length; i++) {//Parse through tile ID 2D array
			
			for(int j = 0; j < tileIDs[i].length; j++) {//Parse through a single row
				tiles[i][j] = new Tile(i, j, tileIDs[i][j]);//Create a tile based on the id
			}
			
		}
	
		return tiles;//Return 2D array of tiles
	}
	
	/* GETTERS */
	
	public int getId() {
		return id;
	}
	
	public Tile[][] getTiles() {
		return tiles;
	}

	public Tile getSingleTile(int i, int j) {
		return tiles[i][j];
	}
	
	/* SETTERS */
	
	public void setId(int id) {
		this.id = id;
	}


	public void setTiles(Tile[][] tiles) {
		this.tiles = tiles;
	}
	
	
}//end of class
