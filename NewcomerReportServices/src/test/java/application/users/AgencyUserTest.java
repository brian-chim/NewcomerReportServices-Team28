package application.users;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AgencyUserTest {

	private final HashMap<ServiceStreams, Boolean> serviceStreams = createTestHashMap();

	private final AgencyUser testAgencyUser = new AgencyUser(1, "username", "password",
			1, "Employment Services", serviceStreams);

	@Test
	@DisplayName("Test Get Permissions")
	void testGetPermissions() {
		HashMap<UserPermissions, Boolean> permissions = new HashMap<UserPermissions, Boolean>();
		for(int i = 0; i < UserPermissions.values().length; i++) {
			permissions.put(UserPermissions.values()[i], (Boolean) false);
		}
		permissions.replace(UserPermissions.UPLOADFILES, (Boolean) true);
		permissions.replace(UserPermissions.UPDATEFILES, (Boolean) true);
		assertEquals(testAgencyUser.getPermissions(), permissions);
	}

	private static HashMap<ServiceStreams, Boolean> createTestHashMap() {
		HashMap<ServiceStreams, Boolean> serviceStreams = new HashMap<ServiceStreams, Boolean>();
		serviceStreams.put(ServiceStreams.EMPLOYMENTSERVICES, (Boolean) true);
		serviceStreams.put(ServiceStreams.CLIENTPROFILEBULK, (Boolean) true);
		serviceStreams.put(ServiceStreams.COMMUNITYCONNECTIONS, (Boolean) true);
		return serviceStreams;
	}

}
