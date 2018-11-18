package application.users;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import application.util.DatabaseServiceStreams;

public class StaffUserTest {

	private final HashMap<DatabaseServiceStreams, Boolean> serviceStreams = new HashMap<DatabaseServiceStreams, Boolean>();

	private final StaffUser testStaffUser = new StaffUser(1, "username", "password",
			1, "STAFF", serviceStreams);

	@Test
	@DisplayName("Test Get Permissions")
	void testGetPermissions() {
		HashMap<UserPermissions, Boolean> permissions = new HashMap<UserPermissions, Boolean>();
		for(int i = 0; i < UserPermissions.values().length; i++) {
			permissions.put(UserPermissions.values()[i], (Boolean) false);
		}
		permissions.replace(UserPermissions.GENERATESUMMARYREPORT, (Boolean) true);
		permissions.replace(UserPermissions.GENERATETRENDREPORT, (Boolean) true);

		assertEquals(testStaffUser.getPermissions(), permissions);
	}

}
