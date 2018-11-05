package application.database;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import application.database.DatabaseHandler.ConditionOP;

public class DatabaseHandlerTest {
	
	@Test
	@DisplayName("get list of service streams")
	void testGetServiceStreams() {
		ArrayList<String> services = DatabaseHandler.getServiceStreams();
		ArrayList<String> streams = new ArrayList<String>(
				Arrays.asList(
						"Language Training Services",
						"Information & Orientation Services",
						"Community Connections Services",
						"Employment Related Services",
						"Needs Assessment & Referral Service (NARS)",
		    	        "Client Profile Bulk"
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
		System.out.println(res);
		
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
}
