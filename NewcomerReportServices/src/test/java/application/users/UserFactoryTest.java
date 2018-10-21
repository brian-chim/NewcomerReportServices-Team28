package application.users;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import application.database.DatabaseUserHandler;
import application.database.UserNotFoundException;

public class UserFactoryTest {

	private final HashMap<ServiceStreams, Boolean> serviceStreams = new HashMap<ServiceStreams, Boolean>();
	private HashMap<String, String> userDetails = createUserDetails();
	private UserFactory userFactory = new UserFactory();
	
	@Test
	@DisplayName("Create Admin using UserFactory")
	void testCreateAdmin() {
		userDetails.put("UserType", "ADMIN");
		userDetails.put("EmploymentServiceStream", "FALSE");
		User factGen = userFactory.getUser(userDetails);
		User userGen = new AdminUser(1, "username", "password", 1, "ADMIN", serviceStreams);
		assertTrue(factGen.equals(userGen));
	}

	@Test
	@DisplayName("Create Agency user using UserFactory")
	void testCreateAgencyUser() {
		userDetails.put("UserType", "AGENCY");
		userDetails.put("EmploymentServiceStream", "TRUE");
		User factGen = userFactory.getUser(userDetails);
		serviceStreams.put(ServiceStreams.EMPLOYMENTSERVICES, (Boolean) true);
		User userGen = new AgencyUser(1, "username", "password", 1, "AGENCY", serviceStreams);
		assertTrue(factGen.equals(userGen));
	}
	
	@Test
	@DisplayName("Create Staff user using UserFactory")
	void testCreateStaffUser() {
		userDetails.put("UserType", "STAFF");
		userDetails.put("EmploymentServiceStream", "FALSE");
		User factGen = userFactory.getUser(userDetails);
		User userGen = new StaffUser(1, "username", "password", 1, "STAFF", serviceStreams);
		assertTrue(factGen.equals(userGen));
	}
	
	private HashMap<String, String> createUserDetails() {
		HashMap<String, String> userDetails = new HashMap<String, String>();
		userDetails.put("Username", "username");
        userDetails.put("Password", "password");
        userDetails.put("ID", "1");
        userDetails.put("Email", "test@mail.utoronto.ca");
        userDetails.put("OrganizationID", "1");
		return userDetails;
	}
}