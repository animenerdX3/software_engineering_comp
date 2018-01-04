package bpa.dev.linavity.world;

import org.newdawn.slick.SlickException;

import bpa.dev.linavity.entities.Camera;
import bpa.dev.linavity.entities.tiles.Tile;
import bpa.dev.linavity.entities.tiles.foreground.dynamicTiles.Dynamic;
import bpa.dev.linavity.gamestates.MainMenu;

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
	public Level(int id, String directory) 
			throws SlickException{
		LevelManager level = new LevelManager(directory);
		int [][] tileIDs = level.getWorld();
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
				
				// Depending on the tile ID's determine the type of tile that is being generated
				if(tileIDs[i][j] > 4){
					tiles[i][j] = new Dynamic(i, j, tileIDs[i][j], 0);
				}else{
					tiles[i][j] = new Tile(i, j, tileIDs[i][j]);//Create a tile based on the id
				}
				
				
				
			}
			
		}
	
		return tiles;//Return 2D array of tiles
	}
	
	// Returns a 2d array of tiles that are within the boundaries of our camera object
	public Tile[][] getScreenTiles(Camera cam) {
		
		// Get a 2d array of tile objects that are contained within our camera's view
		Tile[][] screenTiles = new Tile[19][19];
		
		// Temp x and y of tile in relation to the camera
		float tileX, tileY;
		
		//Counters for where the tile should be placed in the smaller 2d array that we return
		int screenI = 0;
		int screenJ = 0;
		
		boolean isALine = false;
		
		for(int i = 0; i < tiles.length; i++) {
			for(int j = 0; j < tiles[i].length; j++) {
				tileX = tiles[i][j].getX() - cam.getX();
				tileY = tiles[i][j].getY() - cam.getY();
				if(MainMenu.checkBounds(-cam.getBuffer(), 900, -cam.getBuffer(), 900, tileX, tileY)) {
					screenTiles[screenI][screenJ] = tiles[i][j];
					if(screenJ < 18)
						screenJ++;
					isALine = true;
				}
				
			}
			screenJ = 0;
			if(isALine) {
				screenI++;
				isALine = false;
			}
		}
		
		return screenTiles;
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
