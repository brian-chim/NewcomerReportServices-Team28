package application.users;

import java.util.HashMap;

public class StaffUser extends User {
	
	public StaffUser(int userId, String username, String password, int orgId, String orgType, HashMap<ServiceStreams, Boolean> serviceStreams) {
		super(userId, username, password, orgId, orgType, serviceStreams);
		// add permissions to the staff user
		this.getPermissions().replace(UserPermissions.GENERATESUMMARYREPORT, (Boolean) true);
		this.getPermissions().replace(UserPermissions.GENERATETRENDREPORT, (Boolean) true);
	}
}
