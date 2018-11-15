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
		assertEquals(testUser.getOrgType(), "ADMIN");
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
	@DisplayName("Test get ServiceStreams")
	void testGetServiceStreams() {
		HashMap<DatabaseServiceStreams, Boolean> serviceStreams = createTestHashMap();
		serviceStreams.put(DatabaseServiceStreams.CLIENTPROFILEBULK, (Boolean) false);
		serviceStreams.put(DatabaseServiceStreams.INFOANDORIENTATION, (Boolean) false);
		serviceStreams.put(DatabaseServiceStreams.LTCLIENTENROL, (Boolean) false);
		serviceStreams.put(DatabaseServiceStreams.LTCLIENTEXIT, (Boolean) false);
		serviceStreams.put(DatabaseServiceStreams.LTCOURSETUP, (Boolean) false);
		serviceStreams.put(DatabaseServiceStreams.NEEDSASSESSMENT, (Boolean) false);
		assertEquals(testUser.getServiceStreams(), serviceStreams);
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

	@Test
	@DisplayName("Test Set Service Stream")
	void testSetServiceStream() {
		testUser.setServiceStream(DatabaseServiceStreams.INFOANDORIENTATION, (Boolean) true);
		HashMap<DatabaseServiceStreams, Boolean> serviceStreams = createTestHashMap();
		serviceStreams.put(DatabaseServiceStreams.CLIENTPROFILEBULK, (Boolean)false);
		serviceStreams.put(DatabaseServiceStreams.INFOANDORIENTATION, (Boolean)false);
		serviceStreams.put(DatabaseServiceStreams.LTCLIENTENROL, (Boolean)false);
		serviceStreams.put(DatabaseServiceStreams.LTCLIENTEXIT, (Boolean)false);
		serviceStreams.put(DatabaseServiceStreams.LTCOURSETUP, (Boolean)false);
		serviceStreams.put(DatabaseServiceStreams.NEEDSASSESSMENT, (Boolean)false);
		serviceStreams.replace(DatabaseServiceStreams.INFOANDORIENTATION, (Boolean) true);
		assertEquals(testUser.getServiceStreams(), serviceStreams);
	}

	private static HashMap<DatabaseServiceStreams, Boolean> createTestHashMap() {
		HashMap<DatabaseServiceStreams, Boolean> serviceStreams = new HashMap<DatabaseServiceStreams, Boolean>();
		serviceStreams.put(DatabaseServiceStreams.EMPLOYMENTRELATEDSERVICES, (Boolean)true);
		serviceStreams.put(DatabaseServiceStreams.COMMUNITYCONN, (Boolean)false);
		return serviceStreams;
	}
}
