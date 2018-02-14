package bpa.dev.linavity.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import bpa.dev.linavity.Main;

public class PlayerStats {

	private static File character; //Text file
	
	/**
	 * Writes all runtime settings to a file and also any all movements and events happening in the game
	 * @param e Error thrown
	 */
	public void addToPlayerStats(String content, boolean overWrite){
		
		character = new File("data/levels/" + (Main.util.levelNum) + "/" + (Main.util.levelNum) + ".character");
		
		BufferedWriter bw = null;
		FileWriter fw = null;
		try {
			if(!character.exists()) // If the file does not exist, create it
				character.createNewFile();
			else if(overWrite){
				character.delete();
				character.createNewFile();
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
	
	public ArrayList<String> readPlayerStats() throws FileNotFoundException {
		
		File readFile = new File("data/levels/" + Main.util.levelNum + "/" + Main.util.levelNum + ".character");
		
		Scanner scan = new Scanner(readFile);

		ArrayList<String> tempArray = new ArrayList<String>();
		
		while(scan.hasNextLine()) {
				tempArray.add(scan.nextLine());
			}
		
		scan.close();
		
		return tempArray;
	}
	
}//end of class
