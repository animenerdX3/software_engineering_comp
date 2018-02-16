package bpa.dev.linavity.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LoadGame {	
	
	private int saveSlot;
	private File loadFile;
	
	//Data loaded
	
	private String playerName;
	private int classNameCounter, Xcounter, Ycounter, healthCounter;
	private int widthCounter, heightCounter, itemImageCounter;
	private int rowCounter, colCounter, nameCounter, dataCounter;
	
	private int findLevel;
	private float camX, camY;
	private int levelTime;
	
	private int mobSize, itemsSize, inventorySize, eventSize;
	
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
	
	//Variables for Events
	private int [] row, col;
	private String [] name;
	private Object [] data;
	private int eventID;
	private int score;
	
	//Death Count
	private int deathCount;
	
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
		
		//Create arrays
		classNames = new String[temp_loadFile.length];
		xPos = new float[temp_loadFile.length];
		yPos = new float[temp_loadFile.length];
		healthStats = new double[temp_loadFile.length];
		width = new float[temp_loadFile.length];
		height = new float[temp_loadFile.length];
		itemImage = new String[temp_loadFile.length];
		row = new int[temp_loadFile.length];
		col = new int[temp_loadFile.length];
		name = new String[temp_loadFile.length];
		data = new Object[temp_loadFile.length];
		
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
				playerName = ""+properties[0];
				findLevel = Integer.parseInt(properties[1]);//Set game state
				camX = Float.parseFloat(properties[2]);
				camY = Float.parseFloat(properties[3]);
				levelTime = Integer.parseInt(properties[4]);
				deathCount = Integer.parseInt(properties[5]);
				score = Integer.parseInt(properties[6]);
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
			
			else if (i > mobSize + 2 && i < mobSize + itemsSize + 3) {
				addToX(properties[0]);//Add to x position information
				addToY(properties[1]);//Add to y position information
				addToWidth(properties[2]);//Add to width data
				addToHeight(properties[3]);//Add to height data
				addToItemImage(properties[4]);//Add to item image data
			}
			
			else if(i == mobSize + itemsSize + 3)
				this.inventorySize = Integer.parseInt(properties[0]);
			
			else if (i > mobSize + itemsSize + 3 && i < mobSize + itemsSize + inventorySize + 4) {
				addToX(properties[0]);//Add to x position information
				addToY(properties[1]);//Add to y position information
				addToWidth(properties[2]);//Add to width data
				addToHeight(properties[3]);//Add to height data
				addToItemImage(properties[4]);//Add to item image data
			}
			
			else if(i == mobSize + itemsSize + inventorySize + 4)
				this.eventSize = Integer.parseInt(properties[0]);
			
			else if (i < loadData.length - 1){
				addToRow(properties[0]);
				addToCol(properties[1]);
				addToName(properties[2]);
				addToData(properties[3]);
			}
			
			else
				this.eventID = Integer.parseInt(properties[0]);
				
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
	
	public void addToRow(String row) {
		this.row[rowCounter] = Integer.parseInt(row);
		rowCounter++;
	}
	
	public void addToCol(String col) {
		this.col[colCounter] = Integer.parseInt(col);
		colCounter++;
	}
	
	public void addToName(String name) {
		this.name[nameCounter] = name;
		nameCounter++;
	}
	
	public void addToData(String data) {
		this.data[dataCounter] = (Object)data;
		dataCounter++;
	}
	
	/* GETTERS */
	
	public String getPlayerName() {
		return playerName;
	}
	
	public int getLevelFound() {
		return findLevel;
	}
	
	public int getMobSize() {
		return mobSize;
	}
	
	public int getItemSize() {
		return itemsSize;
	}
	
	public int getInventorySize() {
		return inventorySize;
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

	public int[] getRow() {
		return row;
	}

	public int[] getCol() {
		return col;
	}

	public String[] getName() {
		return name;
	}

	public Object[] getData() {
		return data;
	}

	public int getEventSize() {
		return eventSize;
	}

	public int getEventID() {
		return eventID;
	}
	
	public int getDeathCount() {
		return deathCount;
	}
	
	public int getScore() {
		return score;
	}
	
}//end of class
