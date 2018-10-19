package users;

public class AgencyUser extends User {
	
	public AgencyUser(int userId, String username, String password, int orgId, String orgType) {
		super(userId, username, password, orgId, orgType);
		this.getPermissions().replace(UserPermissions.UPLOADFILES, (Boolean) true);
	}

}
