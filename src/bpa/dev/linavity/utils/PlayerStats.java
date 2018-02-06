package bpa.dev.linavity.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JOptionPane;

import bpa.dev.linavity.Main;

public class PlayerStats {
	
	private static boolean firstWrite = true;

	private static File character; //Text file
	
	/**
	 * Writes all runtime settings to a file and also any all movements and events happening in the game
	 * @param e Error thrown
	 */
	public static void addToPlayerStats(String content){
		
		character = new File("data/levels/" + (Main.util.levelNum) + "/" + (Main.util.levelNum) + ".character");
		
		BufferedWriter bw = null;
		FileWriter fw = null;
		try {
			if(!character.exists()) // If the file does not exist, create it
				character.createNewFile();
			else if(firstWrite){
				character.delete();
				character.createNewFile();
				firstWrite = false;
			}
			
			fw = new FileWriter(character.getAbsoluteFile(), true);
			bw = new BufferedWriter(fw);
			bw.write(content); // Write to the file
			bw.newLine();
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		finally {
			try {
				if(bw != null)
					bw.close(); // Close bufferedwriter
				if(fw != null)
					fw.close(); // Close filewriter
			}catch(IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public String[] readPlayerStats() throws FileNotFoundException {
		
		File readFile = new File("data/levels/" + Main.util.levelNum + "/" + Main.util.levelNum + ".character");
		
		Scanner scan = new Scanner(readFile);

		String tempString = scan.nextLine();
		
		String[] tempArray = tempString.split(",");//Split the row into columns
		
		return tempArray;
	}
	
	/*GETTERS*/
	
	/**
	 * @return the firstWrite
	 */
	public static boolean isFirstWrite() {
		return firstWrite;
	}
	
	/*SETTERS*/
	
	/**
	 * @param firstWrite the firstWrite to set
	 */
	public static void setFirstWrite(boolean firstWrite) {
		PlayerStats.firstWrite = firstWrite;
	}
	

}//end of class
