package bpa.dev.linavity.assets;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import bpa.dev.linavity.utils.ErrorLog;

public class MusicManager {

	private String [] music_queue;
	private float volume;
	
	public MusicManager() {
		//Set default volume
		this.volume = 0.5f;
	}
	
	public MusicManager(String directory) {
		//Set default volume
		this.volume = 0.5f;
		readMusicFiles(directory);//Find our music files
	}
	
	/**
	 * creates our array of music directories for our queue
	 * @param directory
	 */
	public void readMusicFiles(String directory) {
		File musicFile = getFile(directory);//Turn into a file type
		try {
			music_queue = getMusicFiles(musicFile);//Get the array of music
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
	 * finds out how many music objects we actually have
	 * @param musicFile
	 * @return size of the array
	 * 
	 */
	public int checkSize(File musicFile) throws FileNotFoundException {
		int arraysize = 0;
		Scanner scan = new Scanner(musicFile);
		
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
	public String[] getMusicFiles(File musicFile) throws FileNotFoundException{
		Scanner scan = new Scanner(musicFile);
		
		//Create temporary music array to store info in, just in case anything goes wrong
		String [] temp_music = new String[checkSize(musicFile)];
		
		//Add directories to the array
		for(int i = 0; i < temp_music.length; i++)
				temp_music[i] = scan.nextLine();
		
		scan.close();//Close the scanner
		return temp_music;//Return the files
		
	}//end of getSoundFiles
	
	/* GETTERS */
	
	/**
	 * 
	 * @return the array of music directories
	 */
	public String [] getMusicQueue() {
		return music_queue;
	}
	
	/**
	 * 
	 * @return our music volume
	 */
	public float getVolume() {
		return volume;
	}
	
	/* SETTERS */
	
	/**
	 * changes our music volume
	 * @param volume
	 */
	public void setVolume(float volume) {
		this.volume = volume;
	}
	
	/**
	 * changes our music directories
	 * @param music_queue
	 */
	public void setMusicQueue(String [] music_queue) {
		this.music_queue = music_queue;
	}
	
}//end of class
