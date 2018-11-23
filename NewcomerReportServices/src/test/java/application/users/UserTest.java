package application.users;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import application.util.DatabaseServiceStreams;

public class UserTest {
	private final HashMap<DatabaseServiceStreams, Boolean> serviceStreams = createTestHashMap();
	private final User testUser = new User(1, "username", "password",
			1, "ADMIN", serviceStreams);

	private static HashMap<DatabaseServiceStreams, Boolean> createTestHashMap() {
		HashMap<DatabaseServiceStreams, Boolean> serviceStreams = new HashMap<DatabaseServiceStreams, Boolean>();
		serviceStreams.put(DatabaseServiceStreams.EMPLOYMENTRELATEDSERVICES, (Boolean)true);
		serviceStreams.put(DatabaseServiceStreams.COMMUNITYCONN, (Boolean)false);
		return serviceStreams;
	}
}
