package application.database;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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

}
