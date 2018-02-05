package bpa.dev.linavity.world;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import org.newdawn.slick.SlickException;

import bpa.dev.linavity.Main;
import bpa.dev.linavity.collectibles.GravCapsule;
import bpa.dev.linavity.collectibles.GravPack;
import bpa.dev.linavity.collectibles.HealthPack;
import bpa.dev.linavity.collectibles.Item;
import bpa.dev.linavity.collectibles.KeyCard;
import bpa.dev.linavity.entities.Mob;
import bpa.dev.linavity.entities.Player;
import bpa.dev.linavity.entities.enemies.Bomber;
import bpa.dev.linavity.entities.enemies.Starter;
import bpa.dev.linavity.entities.enemies.Tank;
import bpa.dev.linavity.entities.tiles.Dynamic;
import bpa.dev.linavity.entities.tiles.Tile;
import bpa.dev.linavity.entities.tiles.interactive.Door;
import bpa.dev.linavity.entities.tiles.interactive.EventTile;
import bpa.dev.linavity.entities.tiles.interactive.Ladder;
import bpa.dev.linavity.entities.tiles.interactive.Lever;
import bpa.dev.linavity.entities.tiles.interactive.Spikes;
import bpa.dev.linavity.utils.ErrorLog;

public class LevelManager {
	
	private final static int map = 0;
	private final static int events = 1;
	
	private Point playerStartingPosition = new Point(0,0);
	
	// File Instance Variables
	File mapFile;
	File eventsFile;
	File channelsFile;
	File configFile;
	File mobsFile;
	
	/*
	 * Dev Notes:
	 * 0 - 100: Tiles
	 * 101 - 200: Mobs
	 * 201 - 300: Items 
	 */
	
	/**
	 * Create all level info based on a level ID
	 * @param directory
	 */
	public LevelManager(int id) {
		
		// FILE CREATION
		mapFile = getFile("data/levels/" + id + "/" + id + ".map");
		// eventsFile = getFile("data/levels/" + id + "/" + id + ".events");
		channelsFile = getFile("data/levels/" + id + "/" + id + ".channels");
		// configFile = getFile("data/levels/" + id + "/" + id + ".levelConfig");
		// characterFile = getFile("data/levels/" + id + "/" + id + ".character");
		// mobsFile = getFile("data/levels/" + id + "/" + id + ".mobs");
		// END OF FILE CREATION
		
	}//end of LevelManager
	
	
	// Gives the level its map
	public Tile[][] makeMap() throws SlickException {
		
		try {
			return createTileArray(get2DIntArray(mapFile), 0);
		} catch (FileNotFoundException e) {
			ErrorLog.logError(e);
			return null;
		}
		
	}
	
	// Gives the level its events
	public Tile[][] makeEvents() throws SlickException {
		
		try {
			return createTileArray(get2DIntArray(mapFile), 1);
		} catch (FileNotFoundException e) {
			ErrorLog.logError(e);
			return null;
		}
		
	}
	
	// Gives the level its channels
	public ArrayList<Point[]> makeChannels() throws SlickException, FileNotFoundException {

		ArrayList<Point[]> channels = new ArrayList<Point[]>();
		
		String[] tempChannelArray = getStringArray(channelsFile);
		
		for(int i = 0; i < tempChannelArray.length; i++) {
			channels.add(makePointArray(tempChannelArray[i]));
		}
		
		return channels;
	}
	
	public int[][] makeConfig() {
		return null;
	}
	 
	public ArrayList<Mob> makeMobs() throws FileNotFoundException, SlickException {
	
		// Create a 2d int array with the ID's of the mobs in the level
		int[][] mobIDs = get2DIntArray(mapFile);
	
		// Create an array list of mobs
		ArrayList<Mob> mobs = new ArrayList<Mob>();
		
		
		mobs.add(new Player((float)this.playerStartingPosition.getX(), (float)this.playerStartingPosition.getY()));
		
		for(int i = 0; i < mobIDs.length; i++) {//Parse through tile ID 2D array
			
			for(int j = 0; j < mobIDs[i].length; j++) {//Parse through a single row
					if(mobIDs[i][j] > 100){
						if(mobIDs[i][j] == 101)
							mobs.add(new Starter(j * 50, i *50));
						else if(mobIDs[i][j] == 102)
							mobs.add(new Tank(j * 50, i *50));
						else if(mobIDs[i][j] == 103)
							mobs.add(new Bomber(j * 50, i *50));
					}
				}
				
			}
		
		return mobs;
	}//end of makeMobs
	
	public ArrayList<Item> makeItems() throws FileNotFoundException, SlickException {
		
		// Create a 2d int array with the ID's of the mobs in the level
		int[][] itemsIDs = get2DIntArray(mapFile);
	
		// Create an array list of mobs
		ArrayList<Item> items = new ArrayList<Item>();
		
		for(int i = 0; i < itemsIDs.length; i++) {//Parse through tile ID 2D array
			
			for(int j = 0; j < itemsIDs[i].length; j++) {//Parse through a single row
					if(itemsIDs[i][j] > 200){
						if(itemsIDs[i][j] == 201)
							items.add(new GravPack((j * 50) + 15, i *50 - 40, 40, 80, "gravitypack"));
						else if(itemsIDs[i][j] == 202)
							items.add(new HealthPack((j * 50) + 15, i *50, "healthpack"));
						else if(itemsIDs[i][j] == 203)
							items.add(new GravCapsule((j * 50) + 15, i *50, "gravcapsule"));
						else if(itemsIDs[i][j] == 204)
							items.add(new KeyCard((j*50) + 15, i*50, "keycard"));
					}
				}
				
			}
		
		return items;
	}//end of makeItems
		
	// TILE MANAGEMENT
	
	public Point[] makePointArray(String line) {
		
		int pointArrayCounter = 0;
		
		String[] tempStringArray = line.split(",");
		
		Point[] tempPointArray = new Point[tempStringArray.length / 2];
		
		for(int i = 0; i < tempStringArray.length; i += 2) {
			tempPointArray[pointArrayCounter] = new Point(Integer.parseInt(tempStringArray[i]), Integer.parseInt(tempStringArray[i+1])); 
			pointArrayCounter++;
		}
		
		return tempPointArray;
		
	}
	
	/**
	 * Converts the 2D array of ids to an array of tile objects
	 * 
	 * @param tileIDs	2D tile ID array
	 * @return tiles	a 2D array of tile objects for the level
	 * 
	 */
	private Tile[][] createTileArray(int[][] tileIDs, int creatorID) throws SlickException {
		
		Tile[][] tiles = new Tile[tileIDs.length][tileIDs[0].length]; //Create a 2D array with the same size as the tileIDs array
		
		for(int i = 0; i < tileIDs.length; i++) {//Parse through tile ID 2D array
			
			for(int j = 0; j < tileIDs[i].length; j++) {//Parse through a single row
				
				// Check for Player placement
				if(tileIDs[i][j] == 999){
					this.playerStartingPosition = new Point(j*50, i*50);
				}
				
				// Map Creation
				if(creatorID == 0) {
					// Static Tiles
					if(tileIDs[i][j] < 11) 
						tiles[i][j] = new Tile(i, j, tileIDs[i][j]); // Create a static tile, texture and passability based on ID
					else{
						// Create a default tile where any dynamic tile will be placed
						if(tiles[i-1][j].isPassable())
							tiles[i][j] = new Tile(i, j, tiles[i-1][j].getId());
						else if(tiles[i][j-1].isPassable())
							tiles[i][j] = new Tile(i, j, tiles[i][j-1].getId());
						else if(tiles[i][j+1].isPassable())
							tiles[i][j] = new Tile(i, j, tiles[i][j+1].getId());	
					}
						
				}
				
				// Event Creation
				if(creatorID == 1) {
					// Dynamic Tiles
					if(tileIDs[i][j] > 10 && tileIDs[i][j] < 101) {
						if(tileIDs[i][j] == Tile.eventTileID) // Gravity Pad
							tiles[i][j] = new EventTile(i, j, tileIDs[i][j], 0, 0, 50, 50);
						else if(tileIDs[i][j] == Tile.gravPadID) // Gravity Pad
							tiles[i][j] = new Dynamic(i, j, tileIDs[i][j], 0, 40, 50, 10);
						else if(tileIDs[i][j] == Tile.leverID) // Lever
							tiles[i][j] = new Lever(i, j, tileIDs[i][j], 0, 0, 50, 50);
						else if(tileIDs[i][j] == Tile.doorID) // Door
							tiles[i][j] = new Door(i, j, tileIDs[i][j], 0, 0, 50, 50);
						else if(tileIDs[i][j] == Tile.spikesID) // Spike
							tiles[i][j] = new Spikes(i, j, tileIDs[i][j], 0, 0, 50, 50, new Tile(0,0,tileIDs[i-1][j]), new Tile(0,0,tileIDs[i+1][j]), new Tile(0,0,tileIDs[i][j-1]), new Tile(0,0,tileIDs[i][j+1]));
						else if(tileIDs[i][j] == Tile.ladderTopID || tileIDs[i][j] == Tile.ladderMiddleID || tileIDs[i][j] == Tile.ladderBottomID) // Ladder
							tiles[i][j] = new Ladder(i, j, tileIDs[i][j], 0, 0, 50, 50);
						else if(tileIDs[i][j] == Tile.eventTileID) // Event 
							tiles[i][j] = new EventTile(i, j, tileIDs[i][j], 0, 0, 50, 50);
						else if(tileIDs[i][j] == Tile.platformID) // Platforms
							tiles[i][j] = new Dynamic(i, j, tileIDs[i][j], 0, 16, 50, 17);
						else// Default Dynamic Tile
							tiles[i][j] = new Dynamic(i, j, tileIDs[i][j], 0, 0, 50, 50);
					}else{
						tiles[i][j] = new Tile(i, j, 0); // Create a default tile where any dynamic tile will be placed
					}
				}
				
			}
			
		}
	
		return tiles;//Return 2D array of tiles
	}
	
	// END OF TILE MANAGEMENT 
	
	/**
	 * Converts the String into a file type
	 * @param directory
	 * @return level as a file
	 */
	public File getFile(String directory) {
		return new File(directory);
	}//end of getFile
	
	/**
	 * 
	 * @param file
	 * @return row size of the array
	 * 
	 */
	public int checkSize(File file) throws FileNotFoundException {
		int arraysize = 0;
		Scanner scan = new Scanner(file);
		
		while(scan.hasNextLine()) {
			scan.nextLine();
			arraysize++;
		}
		
		scan.close();
		return arraysize;
	}//end of checkSize
	
	/**
	 * Gets the world map from the file specified
	 * @param file
	 * @return a 2D array with the information of our world
	 * @throws FileNotFoundException
	 */
	public int[][] get2DIntArray(File file) throws FileNotFoundException{
		
		Scanner scan = new Scanner(file);
		
		int [][] tempArray = null;//the world array
		String [] row = new String[checkSize(file)];//an array for one row
		
		for(int i = 0; i < row.length; i++) {
			row[i] = scan.nextLine();//Get the next row in the file
			String [] col = row[i].split(",");//Split the row into columns
			if(i == 0) {//Once we initialize the size of our columns, we can get the size of the 2D array
				tempArray = new int[row.length][col.length];//Set the size of the 2D array
			}
			for(int x = 0; x < col.length; x++) {
				tempArray[i][x] = Integer.parseInt(col[x]);//Put each element into the array
			}
		}
		
		scan.close();//Close the scanner
		return tempArray;//Return the 2d int array
		
	} // End of get2DIntArray
	
	/**
	 * Gets a string array from a file
	 * @param file
	 * @return a string array
	 * @throws FileNotFoundException
	 */
	public String[] getStringArray(File file) throws FileNotFoundException {
	
		// Create scanner object from file
		Scanner scan = new Scanner(file);
		
		// Create a temporary string array the length of lines in the file
		String[] temp = new String[checkSize(file)];
		
		// For the length of the array, assign strings to the array
		for(int i = 0; i < temp.length; i++) {
			temp[i] = scan.nextLine();
		}
		
		// Close the scanner object
		scan.close();
		
		// Return our string array
		return temp;
		
		
	} // End of getStringArray

	// Getters
	
	// Setters
	
}//end of class
