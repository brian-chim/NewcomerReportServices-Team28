package com.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Majority of this code was found from the following tutorial: http://www.sqlitetutorial.net/sqlite-java/
 * @author sqlitetutorial.net
 */
public class DatabaseDriver {

    /**
     * Connect to the test.db database
     *
     * @return the Connection object
     */
    private static Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:sqlite/db/test.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    /**
     * Connect to a sample database
     *
     * @param fileName the database file name
     */
    public static void createNewDatabase(String fileName) {

        String url = "jdbc:sqlite:sqlite/db/" + fileName;

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Create a new table in the test database
     *
     */
    public static void createNewTable(String tableName, String databaseName) {
        // SQLite connection string
        String url = "jdbc:sqlite:sqlite/db/" + databaseName;

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS "+ tableName + "(\n"
                + "	id integer PRIMARY KEY,\n"
                + "	firstName firstName NOT NULL,\n"
                + "	lastName lastName NOT NULL,\n"
                + "	fundsNeeded real\n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Insert a new row into the agencies table
     *
     * @param name
     */
    public static void insert(String name, String lastName, double fundsNeeded) {
        String sql = "INSERT INTO agency(firstName, lastName, fundsNeeded) VALUES(?,?,?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, lastName);
            pstmt.setDouble(3, fundsNeeded);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void selectAll(){
        String sql = "SELECT firstName, lastName, fundsNeeded FROM agency";

        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getString("firstName") +  "\t" +
                        rs.getString("lastName") + "\t" +
                        rs.getDouble("fundsNeeded"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void update(String firstName, String lastName, double fundsNeeded) {
        String sql = "UPDATE agency SET firstName = ? , "
                + "fundsNeeded = ? "
                + "WHERE lastName = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, firstName);
            pstmt.setDouble(2, fundsNeeded);
            pstmt.setString(3, lastName);
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void delete(String firstName) {
        String sql = "DELETE FROM agency WHERE firstName = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, firstName);
            // execute the delete statement
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //createNewDatabase("test.db");
        //createNewTable("agency", "test.db");
        //insert("Alice", "Ace", 10.32);
        //insert("Charles", "Charlie", 10431341.32);
        //insert("Bob", "Baxter", 435541.32);
        //update("Alice", "Ace", 30.32);
        //delete("Charles");
        //selectAll();
    }
}