package application.users;

import java.util.HashMap;

import application.util.DatabaseServiceStreams;

public class AdminUser extends User {
	
	public AdminUser(int userId, String username, String password, int orgId, String orgType, HashMap<DatabaseServiceStreams, Boolean> serviceStreams) {
		super(userId, username, password, orgId, orgType, serviceStreams);
		// add permissions to the admin user
//		this.getPermissions().replace(UserPermissions.GENERATESUMMARYREPORT, (Boolean) true);
//		this.getPermissions().replace(UserPermissions.GENERATETRENDREPORT, (Boolean) true);
		this.getPermissions().replace(UserPermissions.SETUSERACCESS, (Boolean) true);
		this.getPermissions().replace(UserPermissions.SETORGANIZATIONACCESS, (Boolean) true);
	}
}
