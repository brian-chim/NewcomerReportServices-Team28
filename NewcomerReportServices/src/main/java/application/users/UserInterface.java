package application.users;

import java.util.HashMap;

import application.util.DatabaseServiceStreams;

public interface UserInterface {

	public void setUserId(int id);
	public void setUsername(String username);
	public void setPassword(String password);
	public void setOrgId(int orgId);
	public void setOrgType(String orgType);
	public void setServiceStream(DatabaseServiceStreams serviceStream, Boolean available);
	
	public int getUserId();
	public String getUsername();
	public String getPassword();
	public int getOrgId();
	public String getOrgType();
	public HashMap<UserPermissions, Boolean> getPermissions();
	public HashMap<DatabaseServiceStreams, Boolean> getServiceStreams();
}
