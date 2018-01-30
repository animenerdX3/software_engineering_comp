package bpa.dev.linavity.assets;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import bpa.dev.linavity.utils.ErrorLog;

public class SoundManager {

	private Sound [] sfx;
	private float volume;
	
	public SoundManager() {
		//Set default volume
		this.volume = 0.5f;
	}
	
	public SoundManager(String directory) {
		//Set default volume
		this.volume = 0.5f;
		readSoundFiles(directory);//Find our sound effect files
	}

	/**
	 * creates our array of sound effect objects
	 * @param directory
	 */
	public void readSoundFiles(String directory) {
		File soundFile = getFile(directory);//Turn into a file type
		try {
			sfx = getSoundFiles(soundFile);//Get the array of sound effects
		} catch (FileNotFoundException e) {
			ErrorLog.logError(e);//Write to errorlog file
			}
	}//end of SoundManager
	
	/**
	 * 
	 * @param directory
	 * @return a file object based on our directory
	 */
	public File getFile(String directory) {
		return new File(directory);
	}
	
	/**
	 * finds out how many sound effect objects we actually have
	 * @param soundFile
	 * @return size of the array
	 * 
	 */
	public int checkSize(File soundFile) throws FileNotFoundException {
		int arraysize = 0;
		Scanner scan = new Scanner(soundFile);
		
		while(scan.hasNextLine()) {
			scan.nextLine();
			arraysize++;
		}
		
		scan.close();
		return arraysize;
	}//end of checkSize
	
	/**
	 * Gets the sound paths from the file specified
	 * @param soundFile
	 * @return an array with a bunch of sound files
	 * @throws FileNotFoundException
	 */
	public Sound[] getSoundFiles(File soundFile) throws FileNotFoundException{
		Scanner scan = new Scanner(soundFile);
		
		//Create temporary sound effects array to store info in, just in case something goes wrong
		Sound [] sfx_files = new Sound[checkSize(soundFile)];
		
		//Create our sound effects array
		for(int i = 0; i < sfx_files.length; i++) {
			try {
				sfx_files[i] = new Sound(scan.nextLine());
			} catch (SlickException e) {
				ErrorLog.logError(e);
			}
		}
		
		scan.close();//Close the scanner
		return sfx_files;//Return the files
		
	}//end of getSoundFiles

	/* GETTERS */
	
	/**
	 * 
	 * @return the array of sound effects
	 */
	public Sound [] getSfx() {
		return sfx;
	}
	
	/**
	 * 
	 * @return our sound effect volume
	 */
	public float getVolume() {
		return volume;
	}
	
	/* SETTERS */

	/**
	 * changes our sound effects array
	 * @param sfx
	 */
	public void setSfx(Sound[] sfx) {
		this.sfx = sfx;
	}
	
	/**
	 * changes our sound effects volume
	 * @param volume
	 */
	public void setVolume(float volume) {
		this.volume = volume;
	}
	
}//end of class
