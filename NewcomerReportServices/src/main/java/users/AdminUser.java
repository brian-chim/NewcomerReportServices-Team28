package users;

public class AdminUser extends User {
	
	public AdminUser(int userId, String username, String password, int orgId, String orgType) {
		super(userId, username, password, orgId, orgType);
		// add permissions to the admin user
		this.getPermissions().replace(UserPermissions.GENERATESUMMARYREPORT, (Boolean) true);
		this.getPermissions().replace(UserPermissions.GENERATETRENDREPORT, (Boolean) true);
		this.getPermissions().replace(UserPermissions.SETACCESS, (Boolean) true);
	}
}
