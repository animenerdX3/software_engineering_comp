package bpa.dev.linavity.world;

import java.awt.Point;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.newdawn.slick.SlickException;

import bpa.dev.linavity.collectibles.Item;
import bpa.dev.linavity.entities.Camera;
import bpa.dev.linavity.entities.Mob;
import bpa.dev.linavity.entities.tiles.Dynamic;
import bpa.dev.linavity.entities.tiles.Tile;
import bpa.dev.linavity.gamestates.MainMenu;
import bpa.dev.linavity.utils.LogSystem;

public class Level {
	
	// Level ID, Width and Height
	private int id;
	private int levelWidth, levelHeight;
	
	// Score for the level
	private int score;
	
	// Level Data
	private Tile[][] map;
	private Tile[][] events;
	
	private ArrayList<Point[]> channels;
	private ArrayList<String[]> eventConnections;
	
	private int[][] config;

	private ArrayList<Mob> mobs;
	private ArrayList<Item> items;
	private LevelManager lm;
	private double startTime;
	
	/**
	 * Constructor: Creates a level for our gamestate
	 * 
	 * @param id	id for the tiles
	 * @throws FileNotFoundException 
	 * 
	 * 
	 */
	public Level(int id) throws SlickException, FileNotFoundException{
		
		double levelTime = System.nanoTime();
		
		this.setScore(0);
		
		this.lm = new LevelManager(id);
		
		LogSystem.addToLog("Making the Map...");
		startTime = System.nanoTime();
		this.map = lm.makeMap();
		LogSystem.addToLog("Map Created Successfully.");
		LogSystem.addToLog("Running code took "+(Math.round(((System.nanoTime() - startTime) / 1000000000.0) * 1000.0) / 1000.0)+" s");
		LogSystem.addToLog("Making the Events...");
		startTime = System.nanoTime();
		this.events = lm.makeEvents();
		LogSystem.addToLog("Events Created Successfully.");
		LogSystem.addToLog("Running code took "+(Math.round(((System.nanoTime() - startTime) / 1000000000.0) * 1000.0) / 1000.0)+" s");
		LogSystem.addToLog("Making the Channels...");
		startTime = System.nanoTime();
		this.channels = lm.makeChannels();
		LogSystem.addToLog("Channels Created Successfully.");
		LogSystem.addToLog("Running code took "+(Math.round(((System.nanoTime() - startTime) / 1000000000.0) * 1000.0) / 1000.0)+" s");
		startTime = System.nanoTime();
		LogSystem.addToLog("Making Event Connections...");
		this.eventConnections = lm.makeEventConnections();
		LogSystem.addToLog("Event Connections Created Successfully.");
		LogSystem.addToLog("Running code took "+(Math.round(((System.nanoTime() - startTime) / 1000000000.0) * 1000.0) / 1000.0)+" s");
		LogSystem.addToLog("Making the Mobs...");
		startTime = System.nanoTime();
		this.mobs = lm.makeMobs();
		LogSystem.addToLog("Mobs Created Successfully.");
		LogSystem.addToLog("Running code took "+(Math.round(((System.nanoTime() - startTime) / 1000000000.0) * 1000.0) / 1000.0)+" s");
		LogSystem.addToLog("Making the Items...");
		startTime = System.nanoTime();
		this.items = lm.makeItems();
		LogSystem.addToLog("Items Created Successfully.");
		LogSystem.addToLog("Running code took "+(Math.round(((System.nanoTime() - startTime) / 1000000000.0) * 1000.0) / 1000.0)+" s");
		LogSystem.addToLog("Level Created Successfully.");
		LogSystem.addToLog("Running code took "+(Math.round(((System.nanoTime() - levelTime) / 1000000000.0) * 1000.0) / 1000.0)+" s");
		LogSystem.addToLog("");
		
		this.levelWidth = map[0].length * 50;
		this.levelHeight = map.length * 50;
		
		// Assign the channels for our interconnected dynamic tiles
		assignChannels();
		//Assign the event ids for our event system
		assignEventIDs();
	}
	
	private void assignChannels() {
		
		for(int i = 0; i < channels.size(); i++) { // Run through the array list of Point arrays
			
			// Create a temporary Point array from the list of arrays
			Point[] grabChannels = channels.get(i);  
			
			// Make another temp array to hold all of the points except for the first point, which indicates which dynamic tile we are assigning the rest of the poitns to
			Point[] tempPointArray = new Point[grabChannels.length - 1];
			
			// Assign the points to the second temp point array
			for(int j = 1; j < grabChannels.length; j++) {
				tempPointArray[j-1] = grabChannels[j]; 
			}
			
			this.getSingleEventTile(grabChannels[0]).setTargetObjects(tempPointArray);
			
		}
		
	}
	
	private void assignEventIDs() {
		
		for(int i = 0; i < eventConnections.size(); i++) { // Run through the array list of String arrays
			
			// Create a temporary Point array from the list of arrays
			String[] grabEvents = eventConnections.get(i);  
			
			int row = Integer.parseInt(grabEvents[0]);
			int col = Integer.parseInt(grabEvents[1]);
			int id = Integer.parseInt(grabEvents[2]);
			Point connect = new Point(row, col);
			
			Dynamic event = (Dynamic) this.getSingleEventTile(connect);
			event.setEventID(id);
			
		}
	}//end of assignEvents
	
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
						

						try{
						screenTiles[screenI][screenJ] = tiles[i][j];
						}
						catch(ArrayIndexOutOfBoundsException io){
							System.out.println("------------------------------");
							io.printStackTrace();
							
						}
						
						if(screenJ < 18)
							screenJ++;
						isALine = true;
					}
					
				}
				screenJ = 0;
				if(isALine && screenI < 18) {
					screenI++;
					isALine = false;
				}
			}
			
			return screenTiles;
		}
		
	// Retrieve a single tile from the map
	public Tile getSingleMapTile(Point coords) {
		return map[coords.x][coords.y];
	}
	
	// Retrieve a single tile from the events
	public Tile getSingleEventTile(Point coords) {
		return events[coords.x][coords.y];
	}
	
	// Getters
	
	/**
	 * @return the score
	 */
	public int getScore() {
		return score;
	}

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
	 * @return the mobs
	 */
	public ArrayList<Mob> getMobs() {
		return mobs;
	}
	
	/**
	 * @return the items
	 */
	public ArrayList<Item> getItems() {
		return items;
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
	
	public LevelManager getLm() {
		return lm;
	}
	
	// Setters

	/**
	 * @param score the score to set
	 */
	public void setScore(int score) {
		this.score = score;
	}
	
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
	 * @param mobs the mobs to set
	 */
	public void setMobs(ArrayList<Mob> mobs) {
		this.mobs = mobs;
	}
	
	/**
	 * @param items the items to set
	 */
	public void setItems(ArrayList<Item> items) {
		this.items = items;
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
	
	public void setLm(LevelManager lm) {
		this.lm = lm;
	}
	
}//end of class
