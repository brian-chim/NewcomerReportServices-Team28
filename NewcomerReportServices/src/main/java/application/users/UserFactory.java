package application.users;

import java.util.ArrayList;
import java.util.HashMap;

import application.database.DatabaseHandler;
import application.util.DatabaseServiceStreams;

public class UserFactory {

    public User getUser(HashMap<String, String> userDetails) {
        String orgType = userDetails.get("UserType");
        
        HashMap<DatabaseServiceStreams, Boolean> serviceStreams = new HashMap<DatabaseServiceStreams, Boolean>();
        ArrayList<String> streams = DatabaseHandler.getServiceStreams();
        for (String stream : streams) {
        	String streamDetails = userDetails.get(DatabaseServiceStreams.fromUiName(stream).getDbName());
        	if (Boolean.valueOf(streamDetails)) {
        		serviceStreams.put(DatabaseServiceStreams.fromUiName(stream), Boolean.TRUE);
        	}
        }
        switch (orgType) {
            case "ADMIN":
                return new AdminUser(Integer.parseInt(userDetails.get("ID")), userDetails.get("Username"), userDetails.get("Password"), Integer.parseInt(userDetails.get("OrganizationID")), "ADMIN", serviceStreams);
            case "AGENCY":
                return new AgencyUser(Integer.parseInt(userDetails.get("ID")), userDetails.get("Username"), userDetails.get("Password"), Integer.parseInt(userDetails.get("OrganizationID")), "AGENCY", serviceStreams);
            case "TEQLIP STAFF":
                return new StaffUser(Integer.parseInt(userDetails.get("ID")), userDetails.get("Username"), userDetails.get("Password"), Integer.parseInt(userDetails.get("OrganizationID")), "STAFF", serviceStreams);
            default:
                return null;
        }
    }
}