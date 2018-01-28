package bpa.dev.linavity.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import bpa.dev.linavity.entities.*;

public class SaveGame {

	private int saveSlot;
	private ArrayList<Mob> mobPositions;
	private File saveFile;
	private boolean canOverWrite;
	
	public SaveGame(ArrayList<Mob> mobPositions) {
		this.mobPositions = mobPositions;
		this.saveSlot = 1;
		this.saveFile = new File("saves/linavitySave_"+this.saveSlot+".data");
		this.canOverWrite = true;
	}
	
	/**
	 * Writes information to a file
	 */
	public void createSave(){
		
		System.err.println("SAVING...");
		
		BufferedWriter bw = null;
		FileWriter fw = null;
		try {
			if(!this.saveFile.exists())//If the file does not exist, create it
				saveFile.createNewFile();
			else
				this.canOverWrite = overWriteSave();
			
			if(this.canOverWrite) {
				saveFile.delete();
				saveFile.createNewFile();
				fw = new FileWriter(this.saveFile.getAbsoluteFile(), true);
				bw = new BufferedWriter(fw);
				saveMobs(bw);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
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
		
	}//end of createSave
	
	public boolean overWriteSave() {
		String message = "Would You Like To Overwrite Your Save?";
		int reply = JOptionPane.showConfirmDialog(null, message, "WARNING", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION)
          return true;
        
       return false;
       
	}//end of overWriteSave
	
	public void saveMobs(BufferedWriter bw) throws IOException {
		for(int i = 0; i < mobPositions.size(); i++) {
			bw.write(this.mobPositions.get(i).toString());
			bw.newLine();
		}
	}//end of saveMobs
	
}//end of class
