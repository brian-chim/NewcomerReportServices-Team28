package application.users;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import application.database.DatabaseHandler;
import application.database.UserNotFoundException;

public class UserTest {
	private final HashMap<ServiceStreams, Boolean> serviceStreams = createTestHashMap();
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
		HashMap<ServiceStreams, Boolean> serviceStreams = createTestHashMap();
		serviceStreams.put(ServiceStreams.CLIENTPROFILEBULK, (Boolean) false);
		serviceStreams.put(ServiceStreams.INFOANDORIENTATION, (Boolean) false);
		serviceStreams.put(ServiceStreams.LANGUAGETRAINING, (Boolean) false);
		serviceStreams.put(ServiceStreams.NEEDSASSESSMENTS, (Boolean) false);
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
		testUser.setServiceStream(ServiceStreams.INFOANDORIENTATION, (Boolean) true);
		HashMap<ServiceStreams, Boolean> serviceStreams = createTestHashMap();
		serviceStreams.put(ServiceStreams.CLIENTPROFILEBULK, (Boolean)false);
		serviceStreams.put(ServiceStreams.INFOANDORIENTATION, (Boolean)false);
		serviceStreams.put(ServiceStreams.LANGUAGETRAINING, (Boolean)false);
		serviceStreams.put(ServiceStreams.NEEDSASSESSMENTS, (Boolean)false);
		serviceStreams.replace(ServiceStreams.INFOANDORIENTATION, (Boolean) true);
		assertEquals(testUser.getServiceStreams(), serviceStreams);
	}

	private static HashMap<ServiceStreams, Boolean> createTestHashMap() {
		HashMap<ServiceStreams, Boolean> serviceStreams = new HashMap<ServiceStreams, Boolean>();
		serviceStreams.put(ServiceStreams.EMPLOYMENTSERVICES, (Boolean)true);
		serviceStreams.put(ServiceStreams.COMMUNITYCONNECTIONS, (Boolean)false);
		return serviceStreams;
	}
	
	public static void main(String[] args) throws UserNotFoundException {
		System.out.println(DatabaseHandler.getUser("admin@mail.com", "123").getServiceStreams());
	}
}
