package application.users;

import java.util.HashMap;

import application.util.DatabaseServiceStreams;

public class AdminUser extends User {
	
	public AdminUser(int userId, String username, String password, int orgId, String orgType, HashMap<DatabaseServiceStreams, Boolean> serviceStreams) {
		super(userId, username, password, orgId, orgType, serviceStreams);
		// add permissions to the admin user
		this.permissions.replace(UserPermissions.SETUSERACCESS, (Boolean) true);
		this.permissions.replace(UserPermissions.SETORGANIZATIONACCESS, (Boolean) true);
	}
}
