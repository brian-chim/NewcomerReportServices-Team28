import java.util.HashMap;

public class UserFactory {

    public User getUser(Hashmap<String, String> userDetails) {
        String orgType = userDetails.get("UserType");
        switch (orgType) {
            case "ADMIN":
                return new AdminUser(userDetails.get("UserID"), userDetails.get("Username"), userDetails.get("Password"), userDetails.get("OrganizationID"), "TBD");
            case "AGENCY":
                return new AgencyUser(userDetails.get("userID"), userDetails.get("username"), userDetails.get("password"), userDetails.get("OrganizationID"), "TBD");
            case "STAFF":
                return new StaffUser(userDetails.get("userID"), userDetails.get("username"), userDetails.get("password"), userDetails.get("OrganizationID"), "TBD");
            default:
                return null;
        }
    }
}