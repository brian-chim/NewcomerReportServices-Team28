package users;

import java.util.HashMap;

public class User implements UserInterface {

	private int userId;
	private String username;
	private String password;
	private int orgId;
	private String orgType;
	private HashMap<UserPermissions, Boolean> permissions = new HashMap<UserPermissions, Boolean>();

	public User(int userId, String username, String password, int orgId, String orgType) {
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.orgId = orgId;
		this.orgType = orgType;
		// init user permissions hashmap to no permissions at all
		for(int i = 0; i < UserPermissions.values().length; i++) {
			this.permissions.put(UserPermissions.values()[i], (Boolean)false);
		}
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public int getUserId() {
		return this.userId;
	}

	public String getUsername() {
		return this.username;
	}

	public String getPassword() {
		return this.password;
	}

	public int getOrgId() {
		return this.orgId;
	}

	public String getOrgType() {
		return this.orgType;
	}

	public HashMap<UserPermissions, Boolean> getPermissions() {
		return this.permissions;
	}
}
