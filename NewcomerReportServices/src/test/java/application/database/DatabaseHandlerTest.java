package application.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import application.database.DatabaseHandler.ConditionOP;

public class DatabaseHandlerTest {
	
	// ideally the below should all be removed in favour of Mocking the db, 
	// instead just save a temp copy of the db for now.
	private static File dbCopy;
	
	@BeforeAll
	static void saveCopyOfDb() {
		dbCopy = new File("sqlite/db/newcomerServiceCopy.db");
		File orig = new File("sqlite/db/newcomerService.db");
		try {
			FileUtils.copyFile(orig, dbCopy);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Failed to copy the original db.");
		}
	}
	/* uncomment this if db should be replaced after every test
	@AfterEach
	void resetDb() {
		File currDb = new File("sqlite/db/newcomerService.db");
		try {
			FileUtils.copyFile(dbCopy, currDb);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Failed to reset the db.");
		}
	}
	 */
	@AfterAll
	static void cleanUp() {
		try {
			File currDb = new File("sqlite/db/newcomerService.db");
			FileUtils.copyFile(dbCopy, currDb);
			Files.delete(Paths.get("sqlite/db/newcomerServiceCopy.db"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Failed to delete the saved copy of db.");
		}
	}

	@Test
	@DisplayName("get list of service streams")
	void testGetServiceStreams() {
		ArrayList<String> services = DatabaseHandler.getServiceStreams();
		ArrayList<String> streams = new ArrayList<String>(
				Arrays.asList(
						"Information & Orientation Services",
						"Community Connections Services",
						"Employment Related Services",
						"Needs Assessment & Referral Service (NARS)",
		    	        "Client Profile Bulk",
						"Language Training Client Enrolment Services",
						"Language Training Client Exit Services",
						"Language Training Course Setup Services"
		    	        )
				);
		assertEquals(services, streams);
	}

	@Test
	@DisplayName("select all columns")
	void testSelectAll() {
		ArrayList<String> cols = new ArrayList<String>(
				Arrays.asList(
						"*"
		    	        )
				);
		
		ArrayList<HashMap<String, String>> res = DatabaseHandler.selectCols("Test", cols);
		HashMap<String, String> resRow1 = new HashMap<>();
		resRow1.put("Test1", "a");
		resRow1.put("Test2", "b");
		resRow1.put("Test3", "c");
		HashMap<String, String> resRow2 = new HashMap<>();
		resRow2.put("Test1", "d");
		resRow2.put("Test2", "e");
		resRow2.put("Test3", "f");
		
		assertEquals(resRow1, res.get(0));
		assertEquals(resRow2, res.get(1));
	}
	
	@Test
	@DisplayName("select single column")
	void testSelectSingleCol() {
		ArrayList<String> cols = new ArrayList<String>(
				Arrays.asList(
						"Test1"
		    	        )
				);
		
		ArrayList<HashMap<String, String>> res = DatabaseHandler.selectCols("Test", cols);
		HashMap<String, String> resRow1 = new HashMap<>();
		resRow1.put("Test1", "a");
		HashMap<String, String> resRow2 = new HashMap<>();
		resRow2.put("Test1", "d");
		
		assertEquals(resRow1, res.get(0));
		assertEquals(resRow2, res.get(1));
	}
	
	@Test
	@DisplayName("select multiple columns")
	void testSelectMultipleCols() {
		ArrayList<String> cols = new ArrayList<String>(
				Arrays.asList(
						"Test1",
						"Test2"
		    	        )
				);
		
		ArrayList<HashMap<String, String>> res = DatabaseHandler.selectCols("Test", cols);
		HashMap<String, String> resRow1 = new HashMap<>();
		resRow1.put("Test1", "a");
		resRow1.put("Test2", "b");
		HashMap<String, String> resRow2 = new HashMap<>();
		resRow2.put("Test1", "d");
		resRow2.put("Test2", "e");
		
		assertEquals(resRow1, res.get(0));
		assertEquals(resRow2, res.get(1));
	}

	@Test
	@DisplayName("select no columns")
	void testSelectZeroCols() {
		ArrayList<String> cols = new ArrayList<String>();
		
		ArrayList<HashMap<String, String>> res = DatabaseHandler.selectCols("Test", cols);
		ArrayList<HashMap<String, String>> emptyArr = new ArrayList<>();
		
		assertEquals(emptyArr, res);
	}
	
	@Test
	@DisplayName("select invalid column")
	void testSelectInvalidCol() {
		ArrayList<String> cols = new ArrayList<String>(
				Arrays.asList(
						"invalid"
		    	        )
				);
		ArrayList<HashMap<String, String>> res = DatabaseHandler.selectCols("Test", cols);
		ArrayList<HashMap<String, String>> emptyArr = new ArrayList<>();
		
		assertEquals(emptyArr, res);
	}
	
	@Test
	@DisplayName("select * of a row")
	void testSelectRowAll() {
		HashMap<String, String> where = new HashMap<>();
		where.put("Test3", "\"c\"");
		ArrayList<HashMap<String, String>> res = DatabaseHandler.selectRows("Test", where, null, null);
		HashMap<String, String> resRow = new HashMap<>();
		resRow.put("Test1", "a");
		resRow.put("Test2", "b");
		resRow.put("Test3", "c");
		
		assertEquals(resRow, res.get(0));		
	}
	
	@Test
	@DisplayName("select some columns of a row")
	void testSelectRowCols() {
		HashMap<String, String> where = new HashMap<>();
		where.put("Test3", "\"c\"");
		ArrayList<String> select = new ArrayList<>();
		select.add("Test2");
		ArrayList<HashMap<String, String>> res = DatabaseHandler.selectRows("Test", where, select, null);
		HashMap<String, String> resRow = new HashMap<>();
		resRow.put("Test2", "b");
		
		assertEquals(resRow, res.get(0));		
	}
	
	@Test
	@DisplayName("multiple where conditions")
	void testSelectMultipleConditions() {
		HashMap<String, String> where = new HashMap<>();
		where.put("Test1", "\"a\"");
		where.put("Test3", "\"g\"");
		ArrayList<String> select = new ArrayList<>();
		select.add("Test2");
		ArrayList<HashMap<String, String>> res = DatabaseHandler.selectRows("Test", where, select, ConditionOP.AND);
		HashMap<String, String> resRow = new HashMap<>();
		resRow.put("Test2", "k");
		
		assertEquals(resRow, res.get(0));		
	}

	@Test
	@DisplayName("delete none of the rows")
	void testDeleteNone() {
		HashMap<String, String> data = new HashMap<>();
		data.put("Test1", "toBeDeleted1");
		data.put("Test2", "toBeDeleted2");
		data.put("Test3", "toBeDeleted3");

		// insert into the db 10 times
		for(int i = 0; i < 10; i++) {
			DatabaseHandler.insert("Test", data);
		}

 		// check if they have been inserted properly
		HashMap<String, String> where = new HashMap<>();
		where.put("Test1", "\"toBeDeleted1\"");
		assertEquals(DatabaseHandler.selectRows("Test", where, null, null).size(), 10);
		// delete none of the rows
		DatabaseHandler.delete("Test", "Test1", "randomString");
		assertEquals(DatabaseHandler.selectRows("Test", where, null, null).size(), 10);
		// clean up the test db by removing added entries
		DatabaseHandler.delete("Test", "Test1", "toBeDeleted1");
	}

	@Test
	@DisplayName("delete some of the rows")
	void testDeleteSome() {
		HashMap<String, String> data = new HashMap<>();
		data.put("Test1", "toBeDeleted1");
		data.put("Test2", "toBeDeleted2");
		data.put("Test3", "toBeDeleted3");

		// insert into the db 10 times
		for(int i = 0; i < 10; i++) {
			DatabaseHandler.insert("Test", data);
		}
		data.replace("Test2", "NotDeleted");
		DatabaseHandler.insert("Test", data);

		// check if they have been inserted properly
		HashMap<String, String> where = new HashMap<>();
		where.put("Test1", "\"toBeDeleted1\"");
		assertEquals(DatabaseHandler.selectRows("Test", where, null, null).size(), 11);
		// delete none of the rows
		DatabaseHandler.delete("Test", "Test2", "toBeDeleted2");
		assertEquals(DatabaseHandler.selectRows("Test", where, null, null).size(), 1);
		// clean up the test db by removing added entries
		DatabaseHandler.delete("Test", "Test1", "toBeDeleted1");
	}

	@Test
	@DisplayName("delete all of the rows")
	void testDeleteAll() {
		HashMap<String, String> data = new HashMap<>();
		data.put("Test1", "toBeDeleted1");
		data.put("Test2", "toBeDeleted2");
		data.put("Test3", "toBeDeleted3");

		// insert into the db 10 times
		for(int i = 0; i < 10; i++) {
			DatabaseHandler.insert("Test", data);
		}

		// check if they have been inserted properly
		HashMap<String, String> where = new HashMap<>();
		where.put("Test1", "\"toBeDeleted1\"");
		assertEquals(DatabaseHandler.selectRows("Test", where, null, null).size(), 10);
		// delete none of the rows
		DatabaseHandler.delete("Test", "Test2", "toBeDeleted2");
		assertEquals(DatabaseHandler.selectRows("Test", where, null, null).size(), 0);
	}

	@Test
	@DisplayName("insert a row successfully")
	void testInsert() {
		HashMap<String, String> data = new HashMap<>();
		data.put("Test1", "toBeDeleted1");
		data.put("Test2", "toBeDeleted2");
		data.put("Test3", "toBeDeleted3");

		// insert into the db 10 times
		for(int i = 0; i < 10; i++) {
			DatabaseHandler.insert("Test", data);
		}

		// check if they have been inserted properly
		HashMap<String, String> where = new HashMap<>();
		where.put("Test1", "\"toBeDeleted1\"");
		assertEquals(DatabaseHandler.selectRows("Test", where, null, null).size(), 10);
		ArrayList<HashMap<String, String>> res = DatabaseHandler.selectRows("Test", where, null, null);
		HashMap<String, String> resRow = res.get(0);

		assertEquals(resRow, data);
		// delete the newly inserted row
		DatabaseHandler.delete("Test", "Test2", "toBeDeleted2");
	}

	@Test
	@DisplayName("insert new agency")
	void testInsertNewAgency() {
		String agencyName = "TestAgency";
		// check that this is not false because agency already exists
		assertTrue(DatabaseHandler.insertAgency(agencyName));
		// check that it was properly added
		assertTrue(DatabaseHandler.getAgencies().contains(agencyName));
		// clear the db
	}

	@Test
	@DisplayName("insert existing agency")
	void testInsertExistingAgency() {
		String agencyName = "TestExistingAgency";
		// insert the agency first so it exists
		DatabaseHandler.insertAgency(agencyName);
		// check that this is false because agency already exists
		assertFalse(DatabaseHandler.insertAgency(agencyName));
		// check that it is still in the db
		assertTrue(DatabaseHandler.getAgencies().contains(agencyName));
		// clear the db
		DatabaseHandler.delete("Agency", "AgencyName", agencyName);
	}

}
