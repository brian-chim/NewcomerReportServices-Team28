package application.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FileParserTest {
	
	@Test
	@DisplayName("Test File Parsing")
	void testFileParsing() {
		ArrayList<HashMap<String, String>> result = FileParser.readSpreadsheet("src/test/java/application/util/sample.xlsx", "Employment");
		assertEquals(result.get(0).get("client_validation_id"), "12345678");
		assertEquals(result.get(0).get("processing_details"), "Test1");		
	}

}
