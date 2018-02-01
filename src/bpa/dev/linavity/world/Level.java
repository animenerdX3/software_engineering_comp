package bpa.dev.linavity.world;

import java.awt.Point;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.newdawn.slick.SlickException;

import bpa.dev.linavity.entities.Camera;
import bpa.dev.linavity.entities.tiles.Dynamic;
import bpa.dev.linavity.entities.tiles.Tile;
import bpa.dev.linavity.gamestates.MainMenu;

public class Level {
	
	// Level ID, Width and Height
	private int id;
	private int levelWidth, levelHeight;
	
	// Level Data
	private Tile[][] map;
	private Tile[][] events;
	
	private ArrayList<Point[]> channels;
	
	private int[][] config;
	
	private int[][] character;
	private int[][] mobs;

	/**
	 * Constructor: Creates a level for our gamestate
	 * 
	 * @param id	id for the tiles
	 * @throws FileNotFoundException 
	 * 
	 * 
	 */
	public Level(int id) throws SlickException, FileNotFoundException{
		
		LevelManager lm = new LevelManager(id);
		
		this.map = lm.makeMap();
		this.events = lm.makeEvents();
		//this.channels = lm.makeChannels();
		
		this.levelWidth = map[0].length * 50;
		this.levelHeight = map.length * 50;

	}
	
	// Returns a 2d array of tiles that are within the boundaries of our camera object
		public Tile[][] getScreenTiles(Camera cam, Tile[][] tiles) {
			
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
	
	// Getters
	
	/**
	 * @return the map
	 */
	public Tile[][] getMap() {
		return map;
	}



	/**
	 * @return the events
	 */
	public Tile[][] getEvents() {
		return events;
	}



	/**
	 * @return the channels
	 */
	public ArrayList<Point[]> getChannels() {
		return channels;
	}



	/**
	 * @return the config
	 */
	public int[][] getConfig() {
		return config;
	}



	/**
	 * @return the character
	 */
	public int[][] getCharacter() {
		return character;
	}



	/**
	 * @return the mobs
	 */
	public int[][] getMobs() {
		return mobs;
	}
	
	public int getLevelWidth() {
		return levelWidth;
	}
	
	public int getLevelHeight() {
		return levelHeight;
	}
	
	public int getId() {
		return id;
	}
	
	// Setters

	/**
	 * @param map the map to set
	 */
	public void setMap(Tile[][] map) {
		this.map = map;
	}



	/**
	 * @param events the events to set
	 */
	public void setEvents(Tile[][] events) {
		this.events = events;
	}



	/**
	 * @param channels the channels to set
	 */
	public void setChannels(ArrayList<Point[]> channels) {
		this.channels = channels;
	}



	/**
	 * @param config the config to set
	 */
	public void setConfig(int[][] config) {
		this.config = config;
	}



	/**
	 * @param character the character to set
	 */
	public void setCharacter(int[][] character) {
		this.character = character;
	}



	/**
	 * @param mobs the mobs to set
	 */
	public void setMobs(int[][] mobs) {
		this.mobs = mobs;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setLevelWidth(int levelWidth) {
		this.levelWidth = levelWidth;
	}

	public void setLevelHeight(int levelHeight) {
		this.levelHeight = levelHeight;
	}


	// Old getter for a single tile

//	public Tile getSingleTile(int i, int j) {
//		return tiles[i][j];
//	}
//	
//	public Tile getSingleTile(Point coords) {
//		return tiles[coords.x][coords.y];
//	}
	
	
	
}//end of class
