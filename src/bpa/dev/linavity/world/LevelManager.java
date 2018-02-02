package bpa.dev.linavity.world;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import org.newdawn.slick.SlickException;

import bpa.dev.linavity.entities.tiles.Dynamic;
import bpa.dev.linavity.entities.tiles.Tile;
import bpa.dev.linavity.entities.tiles.interactive.Door;
import bpa.dev.linavity.entities.tiles.interactive.Lever;
import bpa.dev.linavity.utils.ErrorLog;

public class LevelManager {
	
	private final static int map = 0;
	private final static int events = 1;
	
	// File Instance Variables
	File mapFile;
	File eventsFile;
	File channelsFile;
	File configFile;
	File characterFile;
	File mobsFile;
	
	
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
	
	public int[][] makeCharacter() {
		return null;
	}
	 
	public int[][] mobs() {
		return null;
	}
	
	
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
				
				// Map Creation
				if(creatorID == 0) {
					// Static Tiles
					if(tileIDs[i][j] < 12) 
						tiles[i][j] = new Tile(i, j, tileIDs[i][j]); // Create a static tile, texture and passability based on ID
					else
						tiles[i][j] = new Tile(i, j, Tile.defaultTileTexture); // Create a defualt tile where any dynamic tile will be placed
				}
				
				// Event Creation
				if(creatorID == 1) {
					// Dynamic Tiles
					if(tileIDs[i][j] > 11) {
						if(tileIDs[i][j] == Tile.gravPadID) // Gravity Pad
							tiles[i][j] = new Dynamic(i, j, tileIDs[i][j], 0, 40, 50, 10);
						else if(tileIDs[i][j] == Tile.leverID) // Lever
							tiles[i][j] = new Lever(i, j, tileIDs[i][j], 0, 0, 50, 50);
						else if(tileIDs[i][j] == Tile.doorID) // Door
							tiles[i][j] = new Door(i, j, tileIDs[i][j], 0, 0, 50, 50);
						else // Default Dynamic Tile
							tiles[i][j] = new Dynamic(i, j, tileIDs[i][j], 0, 0, 50, 50);
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
