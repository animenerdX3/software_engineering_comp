package bpa.dev.linavity.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

public class LogSystem {
	
	private static boolean firstWrite = true;
	
	static final File log = new File("log.txt");//Text file
	
	/**
	 * Writes all runtime settings to a file and also any all movements and events happening in the game
	 * @param e Error thrown
	 */
	public static void addToLog(String content){
		
		BufferedWriter bw = null;
		FileWriter fw = null;
		try {
			if(!log.exists())//If the file does not exist, create it
				log.createNewFile();
			else if(firstWrite){
				log.delete();
				log.createNewFile();
				firstWrite = false;
			}
			
			fw = new FileWriter(log.getAbsoluteFile(), true);
			bw = new BufferedWriter(fw);
			bw.write(content);//Write to the file
			bw.newLine();
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		finally {
			try {
				if(bw != null)
					bw.close();//Close bufferedwriter
				if(fw != null)
					fw.close();//Close filewriter
			}catch(IOException ex) {
				ex.printStackTrace();
			}
		}
	}//end of log

}//end of class
