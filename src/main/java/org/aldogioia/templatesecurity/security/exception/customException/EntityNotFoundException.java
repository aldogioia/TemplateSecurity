package org.aldogioia.templatesecurity.security.exception.customException;

public class EntityNotFoundException extends RuntimeException {
    private final String message;

    public EntityNotFoundException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
