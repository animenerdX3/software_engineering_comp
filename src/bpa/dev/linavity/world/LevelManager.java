package bpa.dev.linavity.world;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import bpa.dev.linavity.utils.ErrorLog;

public class LevelManager {

	private int [][] world;
	
	/**
	 * Get the file name for a level and read it in
	 * @param directory
	 */
	public LevelManager(String directory) {
		File worldFile = getFile(directory);//Turn into a file type
		try {
			world = getWorldMap(worldFile);//Get the 2D array of ints for the world
		} catch (FileNotFoundException e) {
			ErrorLog.logError(e);
			}
	}//end of LevelManager
	
	/**
	 * Converts the String into a file type
	 * @param directory
	 * @return level as a file
	 */
	public File getFile(String directory) {
		return new File("data/rooms/"+directory+".txt");
	}//end of getFile
	
	/**
	 * 
	 * @param worldFile
	 * @return row size of the array
	 * 
	 */
	public int checkSize(File worldFile) throws FileNotFoundException {
		int arraysize = 0;
		Scanner scan = new Scanner(worldFile);
		
		while(scan.hasNextLine()) {
			scan.nextLine();
			arraysize++;
		}
		
		scan.close();
		return arraysize;
	}//end of checkSize
	
	/**
	 * Gets the world map from the file specified
	 * @param worldFile
	 * @return a 2D array with the information of our world
	 * @throws FileNotFoundException
	 */
	public int[][] getWorldMap(File worldFile) throws FileNotFoundException{
		Scanner scan = new Scanner(worldFile);
		
		int [][] temp_world = null;//the world array
		String [] worldRow = new String[checkSize(worldFile)];//an array for one row
		
		for(int i = 0; i < worldRow.length; i++) {
			worldRow[i] = scan.nextLine();//Get the next row in the file
			String [] worldCol = worldRow[i].split(",");//Split the row into columns
			if(i == 0) {//Once we initialize the size of our columns, we can get the size of the 2D array
				temp_world = new int[worldRow.length][worldCol.length];//Set the size of the 2D array
			}
			for(int x = 0; x < worldCol.length; x++) {
				temp_world[i][x] = Integer.parseInt(worldCol[x]);//Put each element into the array
			}
		}
		
		scan.close();//Close the scanner
		return temp_world;//Return the world
		
	}//end of getWorldMap

	/* GETTERS */
	
	public int[][] getWorld() {
		return world;
	}

	/* SETTERS */
	
	public void setWorld(int[][] world) {
		this.world = world;
	}
	
}//end of class
