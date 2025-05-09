package ca.shahrestani.ali.edu.vanier.businesslogic;

import ca.shahrestani.ali.edu.vanier.components.FlowException;

public class LogicallyInvalidActionException extends FlowException {
    public LogicallyInvalidActionException(String message) {
        super(message);
    }
}
