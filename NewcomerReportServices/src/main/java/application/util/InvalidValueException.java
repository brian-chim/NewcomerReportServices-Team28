package application.util;

@SuppressWarnings("serial")
public class InvalidValueException extends Exception {
	
    public InvalidValueException() {
        super("The field value is invalid.");
    }

}
