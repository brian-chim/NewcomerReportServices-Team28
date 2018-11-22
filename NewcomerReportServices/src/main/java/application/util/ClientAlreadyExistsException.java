package application.util;

@SuppressWarnings("serial")
public class ClientAlreadyExistsException extends Exception {
	
    public ClientAlreadyExistsException(String message) {
        super("Duplicate Client: " + message);
    }
    
    public ClientAlreadyExistsException() {
        super();
    }
}
