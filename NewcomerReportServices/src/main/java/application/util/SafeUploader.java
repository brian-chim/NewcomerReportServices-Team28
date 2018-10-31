package application.util;

import java.util.ArrayList;
import java.util.HashMap;

import application.database.DatabaseHandler;

public class SafeUploader {
	
	public static ArrayList<String> safeUpload(String tableName, String path){
		
		ArrayList<String> conflicts = new ArrayList<>();
		
		ArrayList<HashMap<String, String>> data = FileParser.readSpreadsheet(path, tableName);
		
		for (HashMap<String, String> row : data) {
			String id = row.get("client_validation_id");
			if (DatabaseHandler.select(tableName, id) != null) {
				DatabaseHandler.insert(tableName, row);
			} else {
				conflicts.add(row.get("rowNumber"));
			}
		}
		
		return conflicts;
		
		
	}

}
