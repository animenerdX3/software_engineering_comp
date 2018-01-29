package bpa.dev.linavity.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.newdawn.slick.SlickException;

import bpa.dev.linavity.Main;
import bpa.dev.linavity.entities.GravityPack;

public class LoadGame {	
	
	private int saveSlot;
	private File loadFile;
	
	//Data loaded
	
	private int classNameCounter, Xcounter, Ycounter, healthCounter;
	
	private int findGameState;
	private String [] classNames;
	private float [] xPos, yPos;
	private double [] healthStats;
	private float gravPowerCheck;
	private boolean isFlipped;
	
	public LoadGame(int saveSlot) {
		this.saveSlot = saveSlot;
		this.loadFile = new File("saves/linavitySave_"+this.saveSlot+".data");
		try {
			String [] data = getLoadFile(this.loadFile);
			splitPaths(data);
		} catch (FileNotFoundException e) {
			this.loadFile = null;
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
		
		classNames = new String[temp_loadFile.length];
		xPos = new float[temp_loadFile.length];
		yPos = new float[temp_loadFile.length];
		healthStats = new double[temp_loadFile.length];
		
		for(int i = 0; i < temp_loadFile.length; i++) {
			temp_loadFile[i] = scan.nextLine();
		}
		
		scan.close();//Close the scanner
		return temp_loadFile;//Return the world
		
	}//end of getLoadFile

	public void splitPaths(String[] loadData){
		for(int i = 0; i < loadData.length; i++){
			String [] properties = loadData[i].split(",");
			if(i != 0) {
				addToClass(properties[0]);
				addToX(properties[1]);
				addToY(properties[2]);
				addToHealth(properties[3]);
				if(i == 1) {//If getting player data
					addToGravPack(properties[4]);
					addToFlipping(properties[5]);
				}
			}
			else
				findGameState = Integer.parseInt(properties[0]);
		}
	}//end of splitPaths
	
	public void addToClass(String className) {
		classNames[classNameCounter] = className;
		classNameCounter++;
	}
	
	public void addToX(String xPosition){
		xPos[Xcounter] = Float.parseFloat(xPosition);
		Xcounter++;
	}
	
	public void addToY(String yPosition){
		yPos[Ycounter] =  Float.parseFloat(yPosition);
		Ycounter++;
	}
	
	public void addToHealth(String health) {
		healthStats[healthCounter] = Double.parseDouble(health);
		healthCounter++;
	}
	
	public void addToGravPack(String gravPack) {
		gravPowerCheck = Float.parseFloat(gravPack);
	}
	
	public void addToFlipping(String isFlipped) {
		this.isFlipped = Boolean.parseBoolean(isFlipped);
	}
	
	/* GETTERS */
	
	public int getGameStateFound() {
		return findGameState;
	}
	
	public File getLoadFile() {
		return loadFile;
	}
	
	public String[] getClassNames() {
		return classNames;
	}
	
	public float[] getXPos() {
		return xPos;
	}
	
	public float[] getYPos() {
		return yPos;
	}
	
	public double[] getHealthStats() {
		return healthStats;
	}
	
	public float getGravPowerCheck() {
		return gravPowerCheck;
	}
	
	public boolean getFlipping() {
		return isFlipped;
	}
	
}//end of class
