package application.database;

import java.util.HashMap;
import application.users.*;

public class DatabaseUserHandler {

    /**
     * Written by http://www.sqlitetutorial.net/sqlite-java/select/
     * Connect to the test.db database
     * @return the Connection object
     */
    private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:C://sqlite/db/newcomerService.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    /**
     * Insert a new row of data into the necessary tables
     *
     * @param userDetails the details regarding the user to be inserted
     * @param streamList the list of streams that the user has access to
     */
    public static void insertUser(Hashmap<String, String> userDetails, ArrayList<String> streamList) {
        String sql = "INSERT INTO Users(UserType, Username, Password, OrganizationID, Email, EmploymentServiceStream) VALUES(?,?,?,?,?,?)";

        // determine which streams the user belong to
        String employmentServiceStream = streamList.contains("EmploymentServiceStream") ? "True" : "False";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userDetails.get("UserType"));
            pstmt.setString(2, userDetails.get("Username"));
            pstmt.setString(3, userDetails.get("Password"));
            pstmt.setString(4, userDetails.get("OrganizationID"));
            pstmt.setString(5, userDetails.get("Email"));
            pstmt.setString(6, employmentServiceStream));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     *
     * @param username
     * @param password
     * @return
     */
    public static User getUser(String username, String password) {
        String sql = "SELECT * " + "FROM Users WHERE username = ?";
        UserFactory userFactory = new UserFactory();
        Hashmap<String, String> userDetails = new Hashmap<String, String>();
        try (Connection conn = this.connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){

            // set the username in the SQL parameter
            pstmt.setString(1, username);

            // get the results
            ResultSet rs  = pstmt.executeQuery();
            rs.next();
            userDetails.put("Username", rs.getString("Username"));
            userDetails.put("Password", rs.getString("Password"));
            userDetails.put("UserID", rs.getString("UserID"));
            userDetails.put("UserType", rs.getString("UserType"));
            userDetails.put("Email", rs.getString("Email"));
            userDetails.put("OrganizationID", rs.getString("OrganizationID"));
            userDetails.put("EmploymentServiceStream", rs.getString("EmploymentServiceStream"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        if (authenticate(password, userDetails.get("Password"))) {
            return userFactory.getUser(userDetails.get("OrganizationID"));
        } else {
            throw new UserNotFoundException();
        }

    }


    public static void authenticate(String password, String checkPassword) {
        return password.equals(checkPassword);
    }
}
