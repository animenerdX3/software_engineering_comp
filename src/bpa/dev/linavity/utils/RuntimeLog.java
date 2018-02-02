package bpa.dev.linavity.utils;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import bpa.dev.linavity.Main;

public class RuntimeLog {
	
	public static void startUpInfo(){
		//Date information
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Date date = new Date();
		//Dimensions Information
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		
		//Start Writing
		LogSystem.addToLog(dateFormat.format(date));
		LogSystem.addToLog("Current Operating System: "+System.getProperty("os.name"));
		LogSystem.addToLog("System Display Resolution: "+gd.getDisplayMode().getWidth()+" x "+gd.getDisplayMode().getHeight());
		LogSystem.addToLog("Window Size: "+Main.WIDTH+" x "+Main.HEIGHT);
		LogSystem.addToLog("FPS: "+Main.FPS);
		
	}//end of startUpInfo
	
}//end of class
