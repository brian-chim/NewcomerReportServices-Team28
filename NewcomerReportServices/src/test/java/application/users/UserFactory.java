import java.util.HashMap;
import application.users.*;

public class UserFactory {

    public User getShape(int orgId, Hashmap<String, String> userDetails){
        String orgType = userDetails.get("UserType");
        switch (orgType) {
            case "ADMIN":
                return new AdminUser(userDetails.get("userID"), userDetails.get("username"), userDetails.get("password"), orgId, "TBD");
            case "AGENCY":
                return new AgencyUser(userDetails.get("userID"), userDetails.get("username"), userDetails.get("password"), orgId, "TBD");
            case "STAFF":
                return new StaffUser(userDetails.get("userID"), userDetails.get("username"), userDetails.get("password"), orgId, "TBD");
            default:
                return null;
        }
    }
}