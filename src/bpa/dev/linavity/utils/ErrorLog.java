package bpa.dev.linavity.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

public class ErrorLog {
	
	static final File error = new File("errorlog.txt");//Text file
	
	/**
	 * Writes any error thrown during the program to a file
	 * @param e Error thrown
	 */
	public static void logError(Exception e){
		
		BufferedWriter bw = null;
		FileWriter fw = null;
		try {
			if(!error.exists())//If the file does not exist, create it
				error.createNewFile();
			
			fw = new FileWriter(error.getAbsoluteFile(), false);
			bw = new BufferedWriter(fw);
			bw.write(e.toString());//Write to the file
			
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
		
		displayError(e);
		
	}
	
	public static void displayError(Exception e) {
		String message = "AN ERROR HAS OCCURRED \n"+e.toString()+"\n WOULD YOU LIKE TO EMAIL THE ERROR?";
		int reply = JOptionPane.showConfirmDialog(null, message, "ERROR", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
          try {
			SendEmail.sendEmail(e);
		} catch (Exception e1) {
			e1.printStackTrace();
			}
        }
        else {
           System.exit(0);//Terminate Program
        }
	}

}
