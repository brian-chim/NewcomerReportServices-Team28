package application.users;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class User implements UserInterface {

	private int userId;
	private String username;
	private String password;
	private int orgId;
	// ADMIN, AGENCY, or STAFF
	private String orgType;
	// what the user has access to do
	private HashMap<UserPermissions, Boolean> permissions = new HashMap<UserPermissions, Boolean>();
	private HashMap<ServiceStreams, Boolean> serviceStreams = new HashMap<ServiceStreams, Boolean>();
	
	public User(int userId, String username, String password, int orgId, String orgType, HashMap<ServiceStreams, Boolean> serviceStreams) {
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.orgId = orgId;
		this.orgType = orgType;
		// init user permissions hashmap to no permissions at all
		for(int i = 0; i < UserPermissions.values().length; i++) {
			this.permissions.put(UserPermissions.values()[i], (Boolean)false);
		}
		// set up the service streams
		for(int i = 0; i < ServiceStreams.values().length; i++) {
			this.serviceStreams.put(ServiceStreams.values()[i], (Boolean)false);
		}
		// iterate through the a HashMap taken from https://stackoverflow.com/questions/1066589/iterate-through-a-hashmap
		Iterator<Entry<ServiceStreams, Boolean>> it = serviceStreams.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry<ServiceStreams, Boolean> pair = (Map.Entry<ServiceStreams, Boolean>) it.next();
			this.serviceStreams.replace(pair.getKey(), pair.getValue());
			it.remove();
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

	public void setServiceStream(ServiceStreams stream, Boolean available) {
		this.serviceStreams.replace(stream, available);
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

	public HashMap<ServiceStreams, Boolean> getServiceStreams() {
		return this.serviceStreams;
	}
	
	public boolean equals(User user) {
		boolean userIdEqual = (this.getUserId() == user.getUserId());
		boolean usernameEqual = (this.getUsername().equals(user.getUsername()));
		boolean passwordEqual = (this.getPassword().equals(user.getPassword()));
		boolean orgIdEqual = (this.getOrgId() == user.getOrgId());
		boolean orgTypeEqual = (this.getOrgType().equals(user.getOrgType()));
		boolean permissionsEqual = this.getPermissions().equals(user.getPermissions());
		boolean serviceStreamsEqual = this.getServiceStreams().equals(user.getServiceStreams());
		return userIdEqual && usernameEqual && passwordEqual && orgIdEqual && orgTypeEqual && permissionsEqual && serviceStreamsEqual;
	}
}
