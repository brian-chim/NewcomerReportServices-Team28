package application.util;

@SuppressWarnings("serial")
public class InvalidValueException extends Exception {
	
    public InvalidValueException(String field) {
        super("Invalid: " + field);
    }

}
