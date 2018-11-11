package application.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class WriteReport {
	
	/**
	 * creates a summary report in the Summary Reports folder located at the project root.
	 * @param unformattedReport - list of hashmaps, where hashmap is col:val
	 */
	public static void summaryReport(ArrayList<HashMap<String, String>> unformattedReport ) {
		
		// try to write to file
	    try {
	    	FileWriter fileWriter = new FileWriter("../Summary Reports/report.txt");
	        PrintWriter printWriter = new PrintWriter(fileWriter);
	        
	        // loops through each row
	        for (HashMap<String, String> row : unformattedReport) {
	        	
	        	String rowString = "|  ";

	        	// loops through cols
		        for (String key : row.keySet()) {
		        	rowString += key + ": " + row.get(key) + "  |  ";
		        }
		        // write row to file
		        printWriter.printf("%s" + "%n", rowString);
	        }
	        
	        printWriter.close();
	    }
	    // unable to write to file
	    catch (IOException e) {
	        e.printStackTrace();
	    }
	}
}
