
package application.database;

@SuppressWarnings("serial")
public class UserNotFoundException extends Exception {

    public UserNotFoundException() {
        super("The user cannot be found");
    }
}