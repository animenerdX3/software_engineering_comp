package bpa.dev.linavity.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import bpa.dev.linavity.Main;
import bpa.dev.linavity.entities.*;

public class SaveGame {

	private String gameStateID;
	private int saveSlot;
	private ArrayList<Mob> mobPositions;
	private File saveFile;
	private boolean canOverWrite;
	
	public SaveGame(ArrayList<Mob> mobPositions, int gameStateID, int saveSlot) {
		this.gameStateID = ""+gameStateID;
		this.mobPositions = mobPositions;
		this.saveSlot = saveSlot;
		this.saveFile = new File("saves/linavitySave_"+this.saveSlot+".data");
		this.canOverWrite = true;
	}
	
	/**
	 * Writes information to a save file
	 */
	public void createSave(){
		
		boolean newSave = false;
		BufferedWriter bw = null;
		FileWriter fw = null;
		try {
			if(!this.saveFile.exists()) {//If the file does not exist, create it
				System.out.println("Creating Save File");
				newSave = true;
				saveFile.createNewFile();
			}
			else if(!newSave) {
				System.out.println("Save File Found");
				this.canOverWrite = overWriteSave();
			}
			
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
		
		createLoad();
		
	}//end of createSave
	
	public void createLoad() {
			if(this.saveSlot == 1) 
				Main.util.setSlotOneData(new LoadGame(this.saveSlot));
			else if(this.saveSlot == 2)
				Main.util.setSlotTwoData(new LoadGame(this.saveSlot));
			else if(this.saveSlot == 3)
				Main.util.setSlotThreeData(new LoadGame(this.saveSlot));
		
	}
	
	public boolean overWriteSave() {
		String message = "Would You Like To Overwrite Your Save?";
		int reply = JOptionPane.showConfirmDialog(null, message, "WARNING", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION)
          return true;
        
       return false;
       
	}//end of overWriteSave
	
	public void saveMobs(BufferedWriter bw) throws IOException {
		for(int i = -1; i < mobPositions.size(); i++) {
			if(i == -1)
				bw.write(this.gameStateID);
			else
				bw.write(this.mobPositions.get(i).toString());
			bw.newLine();
		}
	}//end of saveMobs
	
}//end of class
