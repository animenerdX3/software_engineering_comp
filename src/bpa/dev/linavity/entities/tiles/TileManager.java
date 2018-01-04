package bpa.dev.linavity.entities.tiles;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TileManager {

	private int texturecounter = 0;
	private int passablecounter = 0;
	
	private String [] textures;
	private boolean [] passable;
	
	/**
	 * Get the file name for a level and read it in
	 * @param directory
	 */
	public TileManager(String directory){
		File textureFile = getFile(directory);//Turn into a file type
		try {
			String [] texturePaths = getTextureMap(textureFile);//Get the 2D array of ints for the world
			splitPaths(texturePaths);
		} catch (FileNotFoundException e) {
			e.printStackTrace(); 
			}
	}
	
	/**
	 * Converts the String into a file type
	 * @param directory
	 * @return level as a file
	 */
	public File getFile(String directory) {
		return new File("data/"+directory+".txt");
	}//end of getFile
	
	/**
	 * 
	 * @param textureFile
	 * @return row size of the array
	 * 
	 */
	public int checkSize(File textureFile) throws FileNotFoundException {
		int arraysize = 0;
		Scanner scan = new Scanner(textureFile);
		
		while(scan.hasNextLine()) {
			scan.nextLine();
			arraysize++;
		}
		
		scan.close();
		return arraysize;
	}//end of checkSize
	
	/**
	 * Gets the world map from the file specified
	 * @param textureFile
	 * @return a 2D array with the information of our world
	 * @throws FileNotFoundException
	 */
	public String[] getTextureMap(File textureFile) throws FileNotFoundException{
		Scanner scan = new Scanner(textureFile);
		
		String [] temp_textures = new String[checkSize(textureFile)];//the world array
		
		textures = new String[temp_textures.length];
		passable = new boolean[temp_textures.length];
		
		for(int i = 0; i < temp_textures.length; i++) {
			temp_textures[i] = scan.nextLine();
		}
		
		scan.close();//Close the scanner
		return temp_textures;//Return the world
		
	}//end of getTextureMap

	public void splitPaths(String[] texturePaths){
		for(int i = 0; i < texturePaths.length; i++){
			String [] properties = texturePaths[i].split(",");
			addToTextures(properties[0]);
			addToPassable(properties[1]);
		}
	}
	
	public void addToTextures(String texture){
		textures[texturecounter] = texture;
		texturecounter++;
	}
	
	public void addToPassable(String pass){
		passable[passablecounter] = Boolean.parseBoolean(pass);
		passablecounter++;
	}
	
	/* GETTERS */
	
	public String[] getTextures() {
		return textures;
	}
	
	public boolean[] getPassable() {
		return passable;
	}

	/* SETTERS */
	
	public void setTextures(String[] textures) {
		this.textures = textures;
	}
	
	public void setPassable(boolean[] passable) {
		this.passable = passable;
	}
	
}//end of TileManager
