package application.util;

import java.util.ArrayList;
import java.util.HashMap;

import application.database.DatabaseHandler;
import application.database.DatabaseHandler.ConditionOP;

public class SafeUploader {
	
	private static int headerOffset = 4;	
	
	/**
	 * Checks if conflict in client_validation_id exists in database for the parsed rows in excel specified by path, if not
	 * insert the row into specified table; otherwise, return the conflicting rows in the file
	 * @param tableName
	 * @param path
	 * @param sheetName
	 * @return a list of conflicting rows. Empty list indicates that all rows are successfully inserted
	 * @throws InvalidValueException 
	 */

	public static ArrayList<Integer> safeUpload(String tableName, String path, String sheetName) throws InvalidValueException, NoClientException, ClientAlreadyExistsException {
		
		ArrayList<Integer> conflicts = new ArrayList<>();
		
		ArrayList<HashMap<String, String>> data = FileParser.readSpreadsheet(path, sheetName);
		
		HashMap<String, String> select = new HashMap<>();
		
		for (int i=0; i< data.size(); i++) {
			HashMap<String, String> row = data.get(i);
			
			String id = row.get("client_validation_id");
			select.put("client_validation_id", id);
			
			ArrayList<HashMap<String, String>> clientInfo = new ArrayList<>();
			// retrieve client information by id, to be later used for comparisons

			HashMap<String, String> where = new HashMap<>();
			where.put("client_validation_id", id);
			clientInfo = DatabaseHandler.selectRows(DatabaseServiceStreams.fromUiName("Client Profile Bulk").getDbName(), where, null, ConditionOP.AND);

			
			// throw error if trying to upload information for a client that does not exist
			if((clientInfo.size() == 0) && !tableName.equals(DatabaseServiceStreams.fromUiName("Client Profile Bulk").getDbName())) {
				System.out.println(DatabaseServiceStreams.fromUiName("Client Profile Bulk").getDbName());
				throw new NoClientException("The client with the specified ID does not exist");
			}
			
			// if trying to upload a new client
			if(tableName.equals(DatabaseServiceStreams.fromUiName("Client Profile Bulk").getDbName())) {
				// check that their address doesn't already exist
				HashMap<String, String> whereFields = new HashMap<>();
				ArrayList<String> addressCols = ClientInfoColumnQueries.getClientAddressCols();
				
				for (String col : addressCols) {
		            if(row.get(col) != null) {
		            	whereFields.put(col, row.get(col));
		            }
		        }
				ArrayList<HashMap<String, String>> clientsByAddress = DatabaseHandler.selectRows(tableName, whereFields, null, ConditionOP.AND);
				if(clientsByAddress.size() != 0 && whereFields.size() != 0) {
					throw new ClientAlreadyExistsException("A client already exists with the samne address");
				}
			}
			
			
			if (DatabaseHandler.selectRows(tableName, select, null, null).isEmpty()) {
				for (String field : row.keySet()) {
					try {
						if(field.endsWith("dt") && row.get(field) != "") {
							row.put(field, Formatter.formatDate(row.get(field)));
						} else if(field.equals("postal_cd") && row.get(field) != "") {
							row.put(field, Formatter.formatPostalCode(row.get(field)));
						} else if(field.equals("phone_no") && row.get(field) != "") {
							row.put(field, Formatter.formatPhoneNumber(row.get(field)));
						}
						// if trying to upload new client, need to check no other client may exist with same info but diff ID
						else if(field.equals("email_txt") && row.get(field) != "") {
							// check if a client exists with same email already
							HashMap<String, String> whereEmail = new HashMap<>();
							whereEmail.put("email_txt", row.get(field));
							
							ArrayList<HashMap<String, String>> clientsByEmail = DatabaseHandler.selectRows(tableName, whereEmail, null, ConditionOP.AND);
							
							if(clientsByEmail.size() != 0) {
								throw new ClientAlreadyExistsException("");
							}
						}
					} catch (InvalidValueException e) {
						throw new InvalidValueException("row: " + String.valueOf(i+headerOffset) + " field: " + field);
					}
					
				}
				DatabaseHandler.insert(tableName, row);

			} else {
				conflicts.add(i+headerOffset);
			}
		}		
		return conflicts;
				
	}
	
	public static boolean safeUpdate(String tableName, String path, String sheetName) throws InvalidValueException {
		ArrayList<HashMap<String, String>> data = FileParser.readSpreadsheet(path, sheetName);
		
		for (int i=0; i< data.size(); i++) {
			HashMap<String, String> row = data.get(i);
			for (String field : row.keySet()) {
				try {
					if(field.endsWith("dt") && row.get(field) != "") {
						row.put(field, Formatter.formatDate(row.get(field)));
					} else if(field.equals("postal_cd") && row.get(field) != "") {
						row.put(field, Formatter.formatPostalCode(row.get(field)));
					} else if(field.equals("phone_no") && row.get(field) != "") {
						row.put(field, Formatter.formatPhoneNumber(row.get(field)));
					}
				} catch (InvalidValueException e) {
					throw new InvalidValueException("row: " + String.valueOf(i+headerOffset) + " field: " + field);
				}
				
			}
			DatabaseHandler.insert(tableName, row);
		}		
		return true;
	}

}
