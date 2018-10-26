package application.database;

import application.users.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DatabaseUserHandler {

    /**
     * Written by http://www.sqlitetutorial.net/sqlite-java/select/
     * Connect to the test.db database
     * @return the Connection object
     */
    private static Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:sqlite/db/newcomerService.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    
    /**
     * Insert a new row of data into the necessary tables.
     *
     * @param userDetails the details regarding the user to be inserted
     * @param streamList the list of streams that the user has access to
     */
    public static boolean insert(String tableName, HashMap<String, String> values) {
        String sql = "INSERT INTO " + tableName;
        String valuesSQL = "(";
        String placeholderSQL = "(";
        
        Integer numVals = values.size();
        Integer index = 1;
        
        // generate sql query with the key, vals from the hasmap
        
        // Iterating over keys only
    	for (String key : values.keySet()) {
    		if(index != numVals) {
        		valuesSQL += key + ", ";
        		placeholderSQL += "?, ";
        	}
        	else {
        		valuesSQL += key + ") VALUES (";
        		placeholderSQL += "?)";
        	}
        	index++;
    	}
    	
        // combine sql query
        sql += valuesSQL + placeholderSQL;
        System.out.println(sql);
    	
    	// connect to db and perfom the query
        try (Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
        		
        	// Iterating over keys and populate statement with values
        	index = 1;
        	for (String key : values.keySet()) {
        	    pstmt.setString(index, values.get(key));
        	    index++;
        	}
            pstmt.executeUpdate();
        } 
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Insert a new row of data into the necessary tables.
     *
     * @param userDetails the details regarding the user to be inserted
     * @param streamList the list of streams that the user has access to
     */
    public static boolean insertUser(HashMap<String, String> userDetails, ArrayList<String> streamList) {
        String sql = "INSERT INTO Users(UserType, Username, Password, OrganizationID, Email, EmploymentServiceStream) VALUES(?,?,?,?,?,?)";

        // determine which streams the user belong to
        String employmentServiceStream = streamList.contains("Employment Related Services") ? "TRUE" : "FALSE";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userDetails.get("UserType"));
            pstmt.setString(2, userDetails.get("Username"));
            pstmt.setString(3, userDetails.get("Password"));
            pstmt.setString(4, userDetails.get("OrganizationID"));
            pstmt.setString(5, userDetails.get("Email"));
            pstmt.setString(6, employmentServiceStream);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Retrieve a user from the database.
     * @param username the username of the user
     * @param password
     * @return a User object based on the type of user that it is
     * @throws UserNotFoundException when the user does not exist
     */
    public static User getUser(String username, String password) throws UserNotFoundException {
        String sql = "SELECT * " + "FROM Users WHERE username = ?";
        UserFactory userFactory = new UserFactory();
        HashMap<String, String> userDetails = new HashMap<String, String>();
        try (Connection conn = connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){

            // set the username in the SQL parameter
            pstmt.setString(1, username);

            // get the results
            ResultSet rs  = pstmt.executeQuery();
            rs.next();
            userDetails.put("Username", rs.getString("Username"));
            userDetails.put("Password", rs.getString("Password"));
            userDetails.put("ID", rs.getString("ID"));
            userDetails.put("UserType", rs.getString("UserType"));
            userDetails.put("Email", rs.getString("Email"));
            userDetails.put("OrganizationID", rs.getString("OrganizationID"));
            userDetails.put("EmploymentServiceStream", rs.getString("EmploymentServiceStream"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        if (authenticate(password, userDetails.get("Password"))) {
            return userFactory.getUser(userDetails);
        } else {
            throw new UserNotFoundException();
        }

    }

    /**
     * Gets all of the agencies that exist in the application.
     * @return a list of agencies
     */
    public static ArrayList<String> getAgencies() {
        String sql = "SELECT * " + "FROM Agency";
        ArrayList<String> agencies = new ArrayList<>();

        try (Connection conn = connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){

            // get the results
            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                agencies.add(rs.getString("AgencyName"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return agencies;
    }


    /**
     * Authenticates the password
     * @param password actual password
     * @param checkPassword password entered by user
     * @return whether or not the passwords match
     */
    public static boolean authenticate(String password, String checkPassword) {
        return password.equals(checkPassword);
    }
}
