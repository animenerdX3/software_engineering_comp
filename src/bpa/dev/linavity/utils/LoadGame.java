package bpa.dev.linavity.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import bpa.dev.linavity.Main;

public class LoadGame {	
	
	private int saveSlot;
	private File loadFile;
	
	//Data loaded
	
	private int Xcounter = 0;
	private int Ycounter = 0;
	
	private float [] xPos;
	private float [] yPos;
	
	public LoadGame(int saveSlot) {
		this.saveSlot = 1;
		this.loadFile = new File("saves/linavitySave_"+this.saveSlot+".data");
		try {
			String [] data = getLoadFile(this.loadFile);
			splitPaths(data);
		} catch (FileNotFoundException e) {
			Main.util.loadGame = false;
		}
	}
	
	/**
	 * 
	 * @param checkSize
	 * @return row size of the array
	 * 
	 */
	public int checkSize(File loadFile) throws FileNotFoundException {
		int arraysize = 0;
		Scanner scan = new Scanner(loadFile);
		
		while(scan.hasNextLine()) {
			scan.nextLine();
			arraysize++;
		}
		
		scan.close();
		return arraysize;
	}//end of checkSize
	
	/**
	 * Gets the texture paths from the file specified
	 * @param textureFile
	 * @return a 2D array with the information of our mobs
	 * @throws FileNotFoundException
	 */
	public String[] getLoadFile(File loadFile) throws FileNotFoundException{
		Scanner scan = new Scanner(loadFile);
		
		String [] temp_loadFile = new String[checkSize(loadFile)];//the world array
		
		xPos = new float[temp_loadFile.length];
		yPos = new float[temp_loadFile.length];
		
		for(int i = 0; i < temp_loadFile.length; i++) {
			temp_loadFile[i] = scan.nextLine();
		}
		
		scan.close();//Close the scanner
		return temp_loadFile;//Return the world
		
	}//end of getLoadFile

	public void splitPaths(String[] loadData){
		for(int i = 0; i < loadData.length; i++){
			String [] properties = loadData[i].split(",");
			addToX(properties[0]);
			addToY(properties[1]);
		}
	}
	
	public void addToX(String xPosition){
		xPos[Xcounter] = Float.parseFloat(xPosition);
		Xcounter++;
	}
	
	public void addToY(String yPosition){
		yPos[Ycounter] =  Float.parseFloat(yPosition);
		Ycounter++;
	}
	
	public float[] getXPos() {
		return xPos;
	}
	
	public float[] getYPos() {
		return yPos;
	}
	
}//end of class
