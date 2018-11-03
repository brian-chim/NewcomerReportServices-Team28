package application.users;

import java.util.HashMap;

public class AgencyUser extends User {

	public AgencyUser(int userId, String username, String password, int orgId, String orgType, HashMap<ServiceStreams, Boolean> serviceStreams) {
		super(userId, username, password, orgId, orgType, serviceStreams);
		// add permissions for the agency user
		this.getPermissions().replace(UserPermissions.UPLOADFILES, (Boolean) true);
		this.getPermissions().replace(UserPermissions.UPDATEFILES, (Boolean) true);
	}

}
