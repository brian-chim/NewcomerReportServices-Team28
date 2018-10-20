package application.users;

import java.util.HashMap;

public class UserFactory {

    public User getUser(HashMap<String, String> userDetails) {
        String orgType = userDetails.get("UserType");
        switch (orgType) {
            case "ADMIN":
                return new AdminUser(Integer.parseInt(userDetails.get("ID")), userDetails.get("Username"), userDetails.get("Password"), Integer.parseInt(userDetails.get("OrganizationID")), "ADMIN");
            case "AGENCY":
                return new AgencyUser(Integer.parseInt(userDetails.get("ID")), userDetails.get("Username"), userDetails.get("Password"), Integer.parseInt(userDetails.get("OrganizationID")), "AGENCY");
            case "STAFF":
                return new StaffUser(Integer.parseInt(userDetails.get("ID")), userDetails.get("Username"), userDetails.get("Password"), Integer.parseInt(userDetails.get("OrganizationID")), "STAFF");
            default:
                return null;
        }
    }
}