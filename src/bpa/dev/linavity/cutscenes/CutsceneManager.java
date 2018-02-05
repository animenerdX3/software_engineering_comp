package bpa.dev.linavity.cutscenes;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import bpa.dev.linavity.utils.ErrorLog;

public class CutsceneManager {

	private int dialoguecounter = 0;
	private int namescounter = 0;
	
	private String [] dialogue;
	private String [] names;
	
	/**
	 * Get the file name for a level and read it in
	 * @param directory
	 */
	public CutsceneManager(String directory){
		File textureFile = getFile(directory);//Turn into a file type
		try {
			String [] dialogueInfo = getSceneDialogue(textureFile);//Get the 2D array of ints for the world
			splitPaths(dialogueInfo);
		} catch (FileNotFoundException e) {
			ErrorLog.logError(e);
			}
	}
	
	/**
	 * Converts the String into a file type
	 * @param directory
	 * @return textures as a file
	 */
	public File getFile(String directory) {
		return new File("data/cutscenes/"+directory+".script");
	}//end of getFile
	
	/**
	 * 
	 * @param checkSize
	 * @return row size of the array
	 * 
	 */
	public int checkSize(File dialogueFile) throws FileNotFoundException {
		int arraysize = 0;
		Scanner scan = new Scanner(dialogueFile);
		
		while(scan.hasNextLine()) {
			scan.nextLine();
			arraysize++;
		}
		
		scan.close();
		return arraysize;
	}//end of checkSize
	
	/**
	 * Gets the texture paths from the file specified
	 * @param dialogueInfo
	 * @return a 2D array with the information of our textures
	 * @throws FileNotFoundException
	 */
	public String[] getSceneDialogue(File dialogueInfo) throws FileNotFoundException{
		Scanner scan = new Scanner(dialogueInfo);
		
		String [] temp_dialogueInfo = new String[checkSize(dialogueInfo)];//the world array
		
		dialogue = new String[temp_dialogueInfo.length];
		names = new String[temp_dialogueInfo.length];
		
		for(int i = 0; i < temp_dialogueInfo.length; i++) {
			temp_dialogueInfo[i] = scan.nextLine();
		}
		
		scan.close();//Close the scanner
		return temp_dialogueInfo;//Return the script
		
	}//end of getSceneDialogue

	public void splitPaths(String[] dialogueInfo){
		for(int i = 0; i < dialogueInfo.length; i++){
			String [] properties = dialogueInfo[i].split("/");
			addToDialogue(properties[0]);
			addToNames(properties[1]);
		}
	}
	
	public void addToDialogue(String dialogueInfo){
		dialogue[dialoguecounter] = dialogueInfo;
		dialoguecounter++;
	}
	
	public void addToNames(String namesInfo){
		names[namescounter] = namesInfo;
		namescounter++;
	}

	/* GETTERS */

	public String[] getDialogue() {
		return dialogue;
	}

	public String[] getNames() {
		return names;
	}
	
}//end of class
