package application.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

import application.database.DatabaseHandler;

public class ReportGeneratorTest {

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
    @DisplayName("generate a Employment summary report")
    void testEmploymentSummaryReport() {
        ArrayList<String> cols = new ArrayList<String>(
                Arrays.asList(
                        "client_birth_dt",
                        "session_service_lang_id",
                        "session_employment_status_id"
                )
        );
        // drop the db for Employment Service Stream first
        DatabaseHandler.delete("EmploymentServiceStream", "", "");
        ArrayList<HashMap<String, String>> testSs = FileParser.readSpreadsheet("src/test/java/application/util/reportgeneratortestexcel.xlsx", "Employment");
		for (int i=0; i< testSs.size(); i++) {
			HashMap<String, String> row = testSs.get(i);
			DatabaseHandler.insert("EmploymentServiceStream", row);
		}
        String actual = ReportGenerator.generateSummaryReport("EmploymentServiceStream", cols, "../test/");
        assertEquals("Summary Report of Frequencies of EmploymentServiceStream\n" +
                "SERVICELANGUAGE:\n" +
                "\n" +
                "English: 1\n" +
                "\n" +
                "French: 1\n" +
                "\n" +
                "EMPLOYMENTSTATUS:\n" +
                "\n" +
                "Unemployed: 2\n" +
                "\n" +
                "CLIENTBIRTHDATE:\n" +
                "\n" +
                "1978-05-20: 2\n", actual);
    }

    @Test
    @DisplayName("generate an empty Employment summary report")
    void testEmploymentSummaryReportNoCols() {
        ArrayList<String> cols = new ArrayList<String>(
                Arrays.asList(
                )
        );

        String actual = ReportGenerator.generateSummaryReport("EmploymentServiceStream", cols, "../test/");
        assertEquals("Summary Report of Frequencies of EmploymentServiceStream", actual);
    }
}
