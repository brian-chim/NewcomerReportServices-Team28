package application.util;

@SuppressWarnings("serial")
public class InvalidValueException extends Exception {
	
    public InvalidValueException(String message) {
        super("Invalid: " + message);
    }
    
    public InvalidValueException() {
        super();
    }
}
