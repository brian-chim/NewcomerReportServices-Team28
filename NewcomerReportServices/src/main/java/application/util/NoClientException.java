package application.util;

@SuppressWarnings("serial")
public class NoClientException extends Exception {
	
    public NoClientException(String message) {
        super("No client: " + message);
    }
    
    public NoClientException() {
        super();
    }
}
