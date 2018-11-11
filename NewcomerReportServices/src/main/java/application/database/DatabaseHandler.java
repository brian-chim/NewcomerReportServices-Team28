package application.database;
import application.database.UserNotFoundException;
import application.users.*;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class DatabaseHandler {
	
	public enum ConditionOP {
		AND, OR
	}

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
     * @param tableName the name of the table
     * @param values hashmap mappign column names to values
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
        		valuesSQL += key + ") VALUES ";
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
     * Inserts a new agency into the database provided they do not
     * exist already (the name is already in the db)
     * 
     * @param agencyName the name of the agency to add
     * @return true iff agency was added successfully
     */
    public static boolean insertAgency(String agencyName) {
    	// get a list of the existing agencies
    	ArrayList<String> existingAgencies = getAgencies();
    	// set the table name
    	String tableName = "Agency";
    	// create the agency to add
    	HashMap<String, String> agencyToAdd = new HashMap<>();
    	agencyToAdd.put("AgencyName", agencyName);
    	// check if the agency already exists
    	if (existingAgencies.contains(agencyName)) {
    		return false;
    	} else {
    		// try inserting
    		if (insert(tableName, agencyToAdd)) {
    			return true;
    		}
    		// if didn't insert then return false
        	return false;
    	}
    }
    
    /**
     * Delete rows from the db based on a SQL where clause
     * @param tableName the name of the db to delete from
     * @param primaryKey the name of the primary column id to compare the value against
     * @param value delete all entries with primaryKey=value
     * @return a boolean representing if the delete operation was successful or not
     */
    public static boolean delete(String tableName, String primaryKey, String value) {
        String sql = "DELETE FROM " + tableName + " WHERE " + primaryKey + " = (?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the comparing value
            pstmt.setString(1, value);
            // execute the delete statement
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
    
    /**
     * Selects row(s) specified by where clause condition from the table specified. 
     * @param tableName - table being selecting from
     * @param where - where clause conditions <col, value>
     * @param select - columns of the row that will be returned, if null, return *
     * @param op - condition operation for multiple where clauses (AND or OR), if not required, can set to null
     * @return ResultSet
     */
    public static ArrayList<HashMap<String, String>> selectRows(String tableName, HashMap<String, String> where, ArrayList<String> select, ConditionOP op) {
    	if(where.isEmpty()) {
    		return new ArrayList<HashMap<String, String>>();
    	}
    	String cols = "";
    	if(select != null) {
    		for(String col : select) {
    			cols = cols + col + ", ";
    		}
    		cols = cols.substring(0, cols.length()-2);
    	} else {
    		cols = "*";
    	}
    	
    	String delimeter = " " + op + " ";
    		 
    	String whereClause = where.entrySet().stream().map(e -> e.getKey() + "=" + e.getValue()).collect(Collectors.joining(delimeter));  	
    	String sql = "SELECT " + cols + " FROM " + tableName + " WHERE " + whereClause;
    	
    	ArrayList<HashMap<String, String>> result = new ArrayList<>();
    	System.out.println(sql);
    	
        try (Connection conn = connect();
                PreparedStatement pstmt  = conn.prepareStatement(sql)){
            	ResultSet rowResult  = pstmt.executeQuery();

            	ResultSetMetaData rsmd = rowResult.getMetaData();
            	// get number of columns in the result (need to do this instead of cols.size since cols can be ["*"] to get all)
            	int columnsNumber = rsmd.getColumnCount();
            	rowResult.next();
            	do {
        
            		// hashmap for storing the row results (key = col, val = db result)
            		HashMap<String, String> rowVals = new HashMap<>();
            		for(int i = 1; i <= columnsNumber; i++) {
            			// use this instead of cols passed in because they might be "*"
            			String colName = rsmd.getColumnName(i);
            			rowVals.put(colName, rowResult.getString(colName));
            		}
            		// store the row results in the list
            		result.add(rowVals);
            	}
            	while(rowResult.next());
            } 
            catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return result;
    }
    
    
    /**
     * Selects all columns specified from the table specified. If you want to select all columns, pass in ["*"] as cols.
     * @param tableName - table being selecting from
     * @param cols - columns being selected -> ["*"] selects all columns from the table
     * @return ResultSet
     */
    public static ArrayList<HashMap<String, String>> selectCols(String tableName, ArrayList<String> cols) {
        String sql = "SELECT " + cols.toString().replace("[", "").replace("]", "").replace("'", "")  + " FROM " + tableName;
        
        ArrayList<HashMap<String, String>> result = new ArrayList<>();
        try (Connection conn = connect();
            PreparedStatement pstmt  = conn.prepareStatement(sql)){
        	ResultSet rowResult  = pstmt.executeQuery();

        	ResultSetMetaData rsmd = rowResult.getMetaData();
        	// get number of columns in the result (need to do this instead of cols.size since cols can be ["*"] to get all)
        	int columnsNumber = rsmd.getColumnCount();
        	rowResult.next();
        	do {
    
        		// hashmap for storing the row results (key = col, val = db result)
        		HashMap<String, String> rowVals = new HashMap<>();
        		for(int i = 1; i <= columnsNumber; i++) {
        			// use this instead of cols passed in because they might be "*"
        			String colName = rsmd.getColumnName(i);
        			rowVals.put(colName, rowResult.getString(colName));
        		}
        		// store the row results in the list
        		result.add(rowVals);
        	}
        	while(rowResult.next());
        } 
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }  
        return result;
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
     * Gets all of the service streams that exist in the application.
     * @return a list of agencies
     */
    public static ArrayList<String> getServiceStreams() {
        String sql = "SELECT * " + "FROM ServiceStreams";
        ArrayList<String> services = new ArrayList<>();

        try (Connection conn = connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){

            // get the results
            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
            	services.add(rs.getString("Service"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return services;
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
