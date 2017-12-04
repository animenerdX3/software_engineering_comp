package bpa.dev.linavity.assets;

import org.newdawn.slick.SlickException;

import bpa.dev.linavity.entities.Tile;

public class Level {

	// Level ID
	int id;
	
	// 2d array of tile objects that makes up the level
	private Tile[][] tiles;

	public Level(int id, int[][] tileIDs) 
			throws SlickException{
		tiles = new Tile[tileIDs.length][tileIDs[0].length];
		tiles = createLevel(tileIDs);
	}

	private Tile[][] createLevel(int[][] tileIDs) 
			throws SlickException {
		
		for(int i = 0; i < tileIDs.length; i++) {
			
			for(int j = 0; j < tileIDs[i].length; j++) {
				tiles[i][j] = new Tile(i, j, tileIDs[i][j]);
			}
			
		}
	
		return tiles;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Tile[][] getTiles() {
		return tiles;
	}

	public void setTiles(Tile[][] tiles) {
		this.tiles = tiles;
	}
	
	public Tile getSingleTile(int i, int j) {
		return tiles[i][j];
	}
	
}
