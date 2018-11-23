package application.users;

import java.util.HashMap;

import application.util.DatabaseServiceStreams;

public class StaffUser extends User {
	
	public StaffUser(int userId, String username, String password, int orgId, String orgType, HashMap<DatabaseServiceStreams, Boolean> serviceStreams) {
		super(userId, username, password, orgId, orgType, serviceStreams);
		// add permissions to the staff user
		this.permissions.replace(UserPermissions.GENERATESUMMARYREPORT, (Boolean) true);
	}
}
