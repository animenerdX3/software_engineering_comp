package bpa.dev.linavity.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ErrorLog {
	
	
	static final File error = new File("error/errorlog.txt");
	
	
	public static void logError(Exception e){
		
		BufferedWriter bw = null;
		FileWriter fw = null;
		try {
			bw = new BufferedWriter(fw);
			fw = new FileWriter(error, true);
			fw.write(e.toString());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		//write to file e.printStack()
		
	}

}
