package application.users;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AdminUserTest {

	private final HashMap<ServiceStreams, Boolean> serviceStreams = new HashMap<ServiceStreams, Boolean>();

	private final AdminUser testAdminUser = new AdminUser(1, "username", "password",
			1, "Employment Services", serviceStreams);

	@Test
	@DisplayName("Test Get Permissions")
	void testGetPermissions() {
		HashMap<UserPermissions, Boolean> permissions = new HashMap<UserPermissions, Boolean>();
		for(int i = 0; i < UserPermissions.values().length; i++) {
			permissions.put(UserPermissions.values()[i], (Boolean) false);
		}
//		permissions.replace(UserPermissions.GENERATESUMMARYREPORT, (Boolean) true);
//		permissions.replace(UserPermissions.GENERATETRENDREPORT, (Boolean) true);
		permissions.replace(UserPermissions.SETUSERACCESS, (Boolean) true);
		permissions.replace(UserPermissions.SETORGANIZATIONACCESS, (Boolean) true);
		assertEquals(testAdminUser.getPermissions(), permissions);
	}

}
