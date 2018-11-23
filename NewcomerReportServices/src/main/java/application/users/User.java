package application.users;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import application.util.DatabaseServiceStreams;

public class User {

	public int userId;
	public String username;
	private String password;
	public int orgId;
	// ADMIN, AGENCY, or STAFF
	public String orgType;
	// what the user has access to do
	public HashMap<UserPermissions, Boolean> permissions = new HashMap<UserPermissions, Boolean>();
	public HashMap<DatabaseServiceStreams, Boolean> serviceStreams = new HashMap<DatabaseServiceStreams, Boolean>();
	
	public User(int userId, String username, String password, int orgId, String orgType, HashMap<DatabaseServiceStreams, Boolean> serviceStreams) {
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
		for(int i = 0; i < DatabaseServiceStreams.values().length; i++) {
			this.serviceStreams.put(DatabaseServiceStreams.values()[i], (Boolean)false);
		}
		// iterate through the a HashMap taken from https://stackoverflow.com/questions/1066589/iterate-through-a-hashmap
		Iterator<Entry<DatabaseServiceStreams, Boolean>> it = serviceStreams.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry<DatabaseServiceStreams, Boolean> pair = (Map.Entry<DatabaseServiceStreams, Boolean>) it.next();
			this.serviceStreams.replace(pair.getKey(), pair.getValue());
			it.remove();
		}
	}

	public boolean equals(User user) {
		boolean userIdEqual = (this.userId == user.userId);
		boolean usernameEqual = (this.username.equals(user.username));
		boolean passwordEqual = (this.password.equals(user.password));
		boolean orgIdEqual = (this.orgId == user.orgId);
		boolean orgTypeEqual = (this.orgType.equals(user.orgType));
		boolean permissionsEqual = this.permissions.equals(user.permissions);
		boolean serviceStreamsEqual = this.serviceStreams.equals(user.serviceStreams);
		return userIdEqual && usernameEqual && passwordEqual && orgIdEqual && orgTypeEqual && permissionsEqual && serviceStreamsEqual;
	}
}
