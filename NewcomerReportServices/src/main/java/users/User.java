package users;

public class User {

	private int userId;
	private String username;
	private String password;
	private int orgId;
	private String orgType;

	public User(int userId, String username, String password, int orgId, String orgType) {
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.orgId = orgId;
		this.orgType = orgType;
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

}
