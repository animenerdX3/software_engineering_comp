package bpa.dev.linavity.world;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import bpa.dev.linavity.utils.ErrorLog;

public class EventChannel {

	private int[] eventChannelList;

	/**
	 * Constructor: Creates a int[] for our event channels
	 * 
	 * @param directory: file path to the event channels
	 * 
	 */
	public EventChannel(String directory) {
		File channelFile = getFile(directory);//Turn into a file type
		try {
			eventChannelList = getEventChannels(channelFile);//Get the 2D array of ints for the world
		} catch (FileNotFoundException e) {
			ErrorLog.logError(e);
			}
	}
	
	/**
	 * Converts the String into a file type
	 * @param directory
	 * @return level as a file
	 */
	public File getFile(String directory) {
		return new File("data/rooms/"+directory+".txt");
	}//end of getFile
	
	/**
	 * Gets the world map from the file specified
	 * @param worldFile
	 * @return a 2D array with the information of our world
	 * @throws FileNotFoundException
	 */
	public int[] getEventChannels(File eventChannels) throws FileNotFoundException{
		
		Scanner scan = new Scanner(eventChannels);
		int[] tempChannels = null;
		String[] channels = scan.nextLine().split(",");
		
		for(int i = 0; i < channels.length; i++) {
			tempChannels[i] = Integer.parseInt(channels[i]);
		}
		
		return tempChannels;
		
	}//end of getWorldMap
	
	/**
	 * 
	 * @param channelFile
	 * @return row size of the array
	 * 
	 */
	public int checkSize(File channelFile) throws FileNotFoundException {
		int arraysize = 0;
		Scanner scan = new Scanner(channelFile);
		while(scan.hasNextLine()) {
			scan.nextLine();
			arraysize++;
		}
		
		scan.close();
		return arraysize;
	}//end of checkSize
	
	// Getters
	
	/**
	 * @return the eventChannelList
	 */
	public int[] getEventChannelList() {
		return eventChannelList;
	}
	
	// Setters

	/**
	 * @param eventChannelList the eventChannelList to set
	 */
	public void setEventChannelList(int[] eventChannelList) {
		this.eventChannelList = eventChannelList;
	}
	
}
