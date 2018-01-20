package bpa.dev.linavity.assets;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import bpa.dev.linavity.utils.ErrorLog;

public class MusicManager {

	private String [] music_queue;
	private float volume;
	
	public MusicManager() {
		this.volume = 0.5f;
	}
	
	public MusicManager(String directory) {
		this.volume = 0.5f;
		readMusicFiles(directory);
	}
	
	public void readMusicFiles(String directory) {
		File musicFile = getFile(directory);//Turn into a file type
		try {
			music_queue = getMusicFiles(musicFile);//Get the array of music
		} catch (FileNotFoundException e) {
			ErrorLog.logError(e);
			}
	}//end of SoundManager
	
	public File getFile(String directory) {
		return new File(directory);
	}
	
	/**
	 * 
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
		
		String [] temp_music = new String[checkSize(musicFile)];
		
		for(int i = 0; i < temp_music.length; i++) {
				temp_music[i] = scan.nextLine();
		}
		
		scan.close();//Close the scanner
		return temp_music;//Return the files
		
	}//end of getSoundFiles
	
	/* GETTERS */
	
	public String [] getMusicQueue() {
		return music_queue;
	}
	
	public float getVolume() {
		return volume;
	}
	
	/* SETTERS */
	
	public void setVolume(float volume) {
		this.volume = volume;
	}
	
	public void setMusicQueue(String [] music_queue) {
		this.music_queue = music_queue;
	}
	
}//end of class
