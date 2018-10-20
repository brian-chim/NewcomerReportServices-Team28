package application.users;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserTest {

	private final User testUser = new User(1, "username", "password",
			1, "Employment Services");

	@Test
	@DisplayName("Test Get UserId")
	void testGetUserId() {
		assertEquals(testUser.getUserId(), 1);
	}

	@Test
	@DisplayName("Test Get Username")
	void testGetUsername() {
		assertEquals(testUser.getUsername(), "username");
	}

	@Test
	@DisplayName("Test Get Password")
	void testGetPassword() {
		assertEquals(testUser.getPassword(), "password");
	}

	@Test
	@DisplayName("Test Get OrgId")
	void testGetOrgId() {
		assertEquals(testUser.getOrgId(), 1);
	}

	@Test
	@DisplayName("Test Get OrgType")
	void testGetOrgType() {
		assertEquals(testUser.getOrgType(), "Employment Services");
	}

	@Test
	@DisplayName("Test Get Permissions")
	void testGetPermissions() {
		HashMap<UserPermissions, Boolean> permissions = new HashMap<UserPermissions, Boolean>();
		for(int i = 0; i < UserPermissions.values().length; i++) {
			permissions.put(UserPermissions.values()[i], (Boolean) false);
		}
		assertEquals(testUser.getPermissions(), permissions);
	}
	
	@Test
	@DisplayName("Test set UserId")
	void testSetUserId() {
		testUser.setUserId(2);
		assertEquals(testUser.getUserId(), 2);
	}

	@Test
	@DisplayName("Test Set Username")
	void testSetUsername() {
		testUser.setUsername("test");
		assertEquals(testUser.getUsername(), "test");
	}

	@Test
	@DisplayName("Test Set Password")
	void testSetPassword() {
		testUser.setPassword("test");
		assertEquals(testUser.getPassword(), "test");
	}

	@Test
	@DisplayName("Test Set OrgId")
	void testSetOrgId() {
		testUser.setOrgId(2);
		assertEquals(testUser.getOrgId(), 2);
	}

	@Test
	@DisplayName("Test Set OrgType")
	void testSetOrgType() {
		testUser.setOrgType("test");
		assertEquals(testUser.getOrgType(), "test");
	}

}
