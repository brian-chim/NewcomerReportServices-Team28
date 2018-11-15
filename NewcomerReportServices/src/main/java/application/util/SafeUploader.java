package application.util;

import java.util.ArrayList;
import java.util.HashMap;

import application.database.DatabaseHandler;

public class SafeUploader {
	
	private static int headerOffset = 4;	
	
	/**
	 * Checks if conflict in client_validation_id exists in database for the parsed rows in excel specified by path, if not
	 * insert the row into specified table; otherwise, return the conflicting rows in the file
	 * @param tableName
	 * @param path
	 * @param sheetName
	 * @return a list of conflicting rows. Empty list indicates that all rows are successfully inserted
	 */
	public static ArrayList<Integer> safeUpload(String tableName, String path, String sheetName){
		
		ArrayList<Integer> conflicts = new ArrayList<>();
		
		ArrayList<HashMap<String, String>> data = FileParser.readSpreadsheet(path, sheetName);
		
		HashMap<String, String> select = new HashMap<>();
		
		for (int i=0; i< data.size(); i++) {
			HashMap<String, String> row = data.get(i);
			String id = row.get("client_validation_id");
			select.put("client_validation_id", id);
			if (DatabaseHandler.selectRows(tableName, select, null, null).isEmpty()) {
				DatabaseHandler.insert(tableName, row);
			} else {
				conflicts.add(i+headerOffset);
			}
		}		
		return conflicts;
				
	}

}
