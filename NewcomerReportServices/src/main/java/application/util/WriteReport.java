package application.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class WriteReport {
	
	/**
	 * Writes a report passed in as a string to a txt file located in the path provided.
	 */
	public static void toTxt(String report, String path) {
		
		// try to write to file
	    try {
	    	FileWriter fileWriter = new FileWriter(path);
	        PrintWriter printWriter = new PrintWriter(fileWriter);
	        printWriter.printf("%s" + "%n", report);
	        
	        printWriter.close();
	    }
	    // unable to write to file
	    catch (IOException e) {
	        e.printStackTrace();
	    }
	}
}
