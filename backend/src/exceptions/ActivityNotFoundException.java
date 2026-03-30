package exceptions;

/**
 * Exception thrown when an activity with a specified ID is not found in the system.
 */
public class ActivityNotFoundException extends Exception {
    
    public ActivityNotFoundException(String message) {
        super(message);
    }
    
}
