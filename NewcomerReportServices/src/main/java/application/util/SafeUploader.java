package application.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import application.database.DatabaseHandler;
import application.database.DatabaseHandler.ConditionOP;

public class SafeUploader {
	
	private final static String dateCell = "dt";
	private final static String postalCodeCell = "postal_cd";
	private final static String	phoneCell = "phone_no";
	private final static String emailCell = "email_txt";
	
	
	private static int headerOffset = 4;
	private static List<String> identifiers = Arrays.asList(
			"street_no",
			"street_nme",
			"street_type_id",
			"street_direction_id",
			"unit_txt",
			"city_txt",
			"province_id");
	private static String clientProfile = DatabaseServiceStreams.fromUiName("Client Profile Bulk").getDbName();
	
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
			select.put("client_validation_id", row.get("client_validation_id"));
			
			checkConflict(row, tableName);
						
			if (DatabaseHandler.selectRows(tableName, select, null, null).isEmpty()) {
				row = autoFormat(row, i, tableName);
				DatabaseHandler.insert(tableName, row);
			} else {
				conflicts.add(i+headerOffset);
			}
		}		
		return conflicts;				
	}
	
	/**
	 * Inserts and Overrides file info in the system database
	 * @param tableName
	 * @param path
	 * @param sheetName
	 * @return true if the update is successful
	 * @throws InvalidValueException
	 * @throws ClientAlreadyExistsException
	 */
	public static boolean safeUpdate(String tableName, String path, String sheetName) throws InvalidValueException, ClientAlreadyExistsException {
		ArrayList<HashMap<String, String>> data = FileParser.readSpreadsheet(path, sheetName);
		
		for (int i=0; i< data.size(); i++) {
			HashMap<String, String> row = data.get(i);
			row = autoFormat(row, i, tableName);
			DatabaseHandler.insert(tableName, row);
		}		
		return true;
	}
	
	private static void checkConflict(HashMap<String, String> row, String tableName) throws NoClientException, ClientAlreadyExistsException {
		String id = row.get("client_validation_id");
		ArrayList<HashMap<String, String>> clientInfo = new ArrayList<>();
		// retrieve client information by id, to be later used for comparisons
		HashMap<String, String> where = new HashMap<>();
		where.put("client_validation_id", id);
		clientInfo = DatabaseHandler.selectRows(clientProfile, where, null, ConditionOP.AND);

		
		// throw error if trying to upload information for a client that does not exist
		if((clientInfo.size() == 0) && !tableName.equals(clientProfile)) {
			throw new NoClientException("The client with the specified ID does not exist");
		}
		
		// if trying to upload a new client
		if(tableName.equals(clientProfile)) {
			// check that their address doesn't already exist
			HashMap<String, String> whereFields = new HashMap<>();
			for(String identityField : identifiers) {
				if(row.get(identityField) != null) {
					whereFields.put(identityField, row.get(identityField));
				}
			}
			
			ArrayList<HashMap<String, String>> clientsByAddress = DatabaseHandler.selectRows(tableName, whereFields, null, ConditionOP.AND);
			if(clientsByAddress.size() != 0 && whereFields.size() != 0) {
				throw new ClientAlreadyExistsException("A client already exists with the samne address");
			}
		}
	}
	
	private static HashMap<String, String> autoFormat(HashMap<String, String> row, int rowNum, String tableName) throws InvalidValueException, ClientAlreadyExistsException {
		for (String field : row.keySet()) {
			try {
				String fieldValue = row.get(field);
				if(field.endsWith(dateCell) && fieldValue != "") {
					row.put(field, Formatter.formatDate(fieldValue));
				} else if(field.equals(postalCodeCell) && fieldValue != "") {
					row.put(field, Formatter.formatPostalCode(fieldValue));
				} else if(field.equals(phoneCell) && fieldValue != "") {
					row.put(field, Formatter.formatPhoneNumber(fieldValue));
				}
				// if trying to upload new client, need to check no other client may exist with same info but diff ID
				else if(field.equals(emailCell) && fieldValue != "") {
					// check if a client exists with same email already
					HashMap<String, String> whereEmail = new HashMap<>();
					whereEmail.put(emailCell, fieldValue);
					
					ArrayList<HashMap<String, String>> clientsByEmail = DatabaseHandler.selectRows(tableName, whereEmail, null, ConditionOP.AND);
					
					if(clientsByEmail.size() != 0) {
						throw new ClientAlreadyExistsException("Client Already Exists");
					}
				}
			} catch (InvalidValueException e) {
				throw new InvalidValueException("row: " + String.valueOf(rowNum + headerOffset) + " field: " + field);
			}
			
		}
		return row;
	}
	

}
