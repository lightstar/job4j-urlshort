package ru.lightstar.urlshort.exception;

/**
 * Exception thrown when account already exists but shouldn't for operation to succeed.
 *
 * @author LightStar
 * @since 0.0.1
 */
public class AccountAlreadyExistsException extends UrlShortException {

    /**
     * Constructs <code>AccountAlreadyExistsException</code> object.
     *
     * @param message message with additional info.
     */
    public AccountAlreadyExistsException(final String message) {
        super(message);
    }
}
