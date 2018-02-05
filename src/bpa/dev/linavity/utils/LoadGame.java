package bpa.dev.linavity.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LoadGame {	
	
	private int saveSlot;
	private File loadFile;
	
	//Data loaded
	
	private int classNameCounter, Xcounter, Ycounter, healthCounter, widthCounter, heightCounter, itemImageCounter;
	
	private int findLevel;
	private float camX, camY;
	private int levelTime;
	
	private int mobSize;
	private int itemsSize;
	
	//Variables for Mobs
	private String [] classNames;
	private float [] xPos, yPos;
	private double [] healthStats;
	private float gravPowerCheck;
	private boolean isFlipped;
	
	//Variables for Items
	private float [] width;
	private float [] height;
	private String [] itemImage;
	
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
	 * @return the size of our array
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
	 * Gets the load file data from the file specified
	 * @param loadFile
	 * @return an array with the information of our mobs
	 * @throws FileNotFoundException
	 */
	public String[] getLoadFile(File loadFile) throws FileNotFoundException{
		Scanner scan = new Scanner(loadFile);
		
		String [] temp_loadFile = new String[checkSize(loadFile)];//the world array
		
		classNames = new String[temp_loadFile.length];
		xPos = new float[temp_loadFile.length];
		yPos = new float[temp_loadFile.length];
		healthStats = new double[temp_loadFile.length];
		width = new float[temp_loadFile.length];
		height = new float[temp_loadFile.length];
		itemImage = new String[temp_loadFile.length];
		
		for(int i = 0; i < temp_loadFile.length; i++) {
			temp_loadFile[i] = scan.nextLine();
		}
		
		scan.close();//Close the scanner
		return temp_loadFile;//Return the world
		
	}//end of getLoadFile

	public void splitPaths(String[] loadData){
		for(int i = 0; i < loadData.length; i++){
			String [] properties = loadData[i].split(",");//Split our row into pieces
			if (i == 0) {
				findLevel = Integer.parseInt(properties[0]);//Set game state
				camX = Float.parseFloat(properties[1]);
				camY = Float.parseFloat(properties[2]);
				levelTime = Integer.parseInt(properties[3]);
			}
			else if(i == 1)
				this.mobSize = Integer.parseInt(properties[0]);
			else if(i > 1 && i < mobSize + 2) {
				addToClass(properties[0]);//Add to class information
				addToX(properties[1]);//Add to x position information
				addToY(properties[2]);//Add to y position information
				addToHealth(properties[3]);//Add to health data
				if(i == 2) {//If getting player data
					addToGravPack(properties[4]);//Add to gravity pack data
					addToFlipping(properties[5]);//Add to flipping data
				}
			}
			else if(i == mobSize + 2)
				this.itemsSize = Integer.parseInt(properties[0]);
			
			else {
				addToX(properties[0]);//Add to x position information
				addToY(properties[1]);//Add to y position information
				addToWidth(properties[2]);//Add to width data
				addToHeight(properties[3]);//Add to height data
				addToItemImage(properties[4]);//Add to item image data
			}
		}
	}//end of splitPaths
	
	public void addToClass(String className) {
		this.classNames[classNameCounter] = className;
		classNameCounter++;
	}
	
	public void addToX(String xPosition){
		this.xPos[Xcounter] = Float.parseFloat(xPosition);
		Xcounter++;
	}
	
	public void addToY(String yPosition){
		this.yPos[Ycounter] =  Float.parseFloat(yPosition);
		Ycounter++;
	}
	
	public void addToHealth(String health) {
		this.healthStats[healthCounter] = Double.parseDouble(health);
		healthCounter++;
	}
	
	public void addToGravPack(String gravPack) {
		this.gravPowerCheck = Float.parseFloat(gravPack);
	}
	
	public void addToFlipping(String isFlipped) {
		this.isFlipped = Boolean.parseBoolean(isFlipped);
	}
	
	public void addToWidth(String width){
		this.width[widthCounter] = Float.parseFloat(width);
		widthCounter++;
	}
	
	public void addToHeight(String height) {
		this.height[heightCounter] = Float.parseFloat(height);
		heightCounter++;
	}
	
	public void addToItemImage(String itemImage) {
		this.itemImage[itemImageCounter] = itemImage;
		itemImageCounter++;
	}
	
	/* GETTERS */
	
	public int getLevelFound() {
		return findLevel;
	}
	
	public int getMobSize() {
		return mobSize;
	}
	
	public int getItemSize() {
		return itemsSize;
	}
	
	public float getCamX() {
		return camX;
	}
	
	public float getCamY() {
		return camY;
	}
	
	public int getLevelTime() {
		return levelTime;
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

	public float[] getWidth() {
		return width;
	}

	public float[] getHeight() {
		return height;
	}

	public String[] getItemImage() {
		return itemImage;
	}
	
}//end of class
