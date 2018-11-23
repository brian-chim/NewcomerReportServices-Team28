package application.users;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import application.util.DatabaseServiceStreams;

public class AgencyUserTest {

	private final HashMap<DatabaseServiceStreams, Boolean> serviceStreams = createTestHashMap();

	private final AgencyUser testAgencyUser = new AgencyUser(1, "username", "password",
			1, "AGENCY", serviceStreams);

	@Test
	@DisplayName("Test Get Permissions")
	void testGetPermissions() {
		HashMap<UserPermissions, Boolean> permissions = new HashMap<UserPermissions, Boolean>();
		for(int i = 0; i < UserPermissions.values().length; i++) {
			permissions.put(UserPermissions.values()[i], (Boolean) false);
		}
		permissions.replace(UserPermissions.UPLOADFILES, (Boolean) true);
		permissions.replace(UserPermissions.UPDATEFILES, (Boolean) true);
		assertEquals(testAgencyUser.permissions, permissions);
	}

	private static HashMap<DatabaseServiceStreams, Boolean> createTestHashMap() {
		HashMap<DatabaseServiceStreams, Boolean> serviceStreams = new HashMap<DatabaseServiceStreams, Boolean>();
		serviceStreams.put(DatabaseServiceStreams.EMPLOYMENTRELATEDSERVICES, (Boolean) true);
		serviceStreams.put(DatabaseServiceStreams.CLIENTPROFILEBULK, (Boolean) true);
		serviceStreams.put(DatabaseServiceStreams.COMMUNITYCONN, (Boolean) true);
		return serviceStreams;
	}

}
