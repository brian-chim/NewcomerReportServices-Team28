package application.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ReportGeneratorTest {

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

        String actual = ReportGenerator.generateSummaryReport("EmploymentServiceStream", cols);
        assertEquals("Summary Report of Frequencies of EmploymentServiceStream\n" +
                "SERVICELANGUAGE:\n" +
                "\n" +
                "English: 2\n" +
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

        String actual = ReportGenerator.generateSummaryReport("EmploymentServiceStream", cols);
        assertEquals("Summary Report of Frequencies of EmploymentServiceStream", actual);
    }
}
