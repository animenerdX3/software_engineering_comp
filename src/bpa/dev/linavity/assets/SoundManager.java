package bpa.dev.linavity.assets;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class SoundManager {

	private Sound [] sfx;
	private float volume;
	
	public SoundManager() {
		this.volume = 0.5f;
	}
	
	public SoundManager(String directory) {
		this.volume = 0.5f;
		readSoundFiles(directory);
	}

	public void readSoundFiles(String directory) {
		File soundFile = getFile(directory);//Turn into a file type
		try {
			sfx = getSoundFiles(soundFile);//Get the array of sound effects
		} catch (FileNotFoundException e) {
			e.printStackTrace(); 
			}
	}//end of SoundManager
	
	public File getFile(String directory) {
		return new File(directory);
	}
	
	/**
	 * 
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
		
		Sound [] sfx_files = new Sound[checkSize(soundFile)];
		
		for(int i = 0; i < sfx_files.length; i++) {
			try {
				sfx_files[i] = new Sound(scan.nextLine());
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
		
		scan.close();//Close the scanner
		return sfx_files;//Return the files
		
	}//end of getSoundFiles

	/* GETTERS */
	
	public Sound [] getSfx() {
		return sfx;
	}
	
	public float getVolume() {
		return volume;
	}
	
	/* SETTERS */

	public void setSfx(Sound[] sfx) {
		this.sfx = sfx;
	}
	
	public void setVolume(float volume) {
		this.volume = volume;
	}
	
}//end of class
