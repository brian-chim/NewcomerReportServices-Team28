package application.users;

import java.util.HashMap;

public class UserFactory {

    public User getUser(HashMap<String, String> userDetails) {
        String orgType = userDetails.get("UserType");
        
        //TODO generate the HashMap of serviceStreams (need to add into hashmap for new service streams)
        HashMap<ServiceStreams, Boolean> serviceStreams = new HashMap<ServiceStreams, Boolean>();
        serviceStreams.put(ServiceStreams.EMPLOYMENTSERVICES, Boolean.parseBoolean(userDetails.get("EmploymentServiceStream")));
        
        switch (orgType) {
            case "ADMIN":
                return new AdminUser(Integer.parseInt(userDetails.get("ID")), userDetails.get("Username"), userDetails.get("Password"), Integer.parseInt(userDetails.get("OrganizationID")), "ADMIN", serviceStreams);
            case "AGENCY":
                return new AgencyUser(Integer.parseInt(userDetails.get("ID")), userDetails.get("Username"), userDetails.get("Password"), Integer.parseInt(userDetails.get("OrganizationID")), "AGENCY", serviceStreams);
            case "STAFF":
                return new StaffUser(Integer.parseInt(userDetails.get("ID")), userDetails.get("Username"), userDetails.get("Password"), Integer.parseInt(userDetails.get("OrganizationID")), "STAFF", serviceStreams);
            default:
                return null;
        }
    }
}