package ru.lightstar.urlshort.exception;

/**
 * Exception thrown when account not found but it needed for operation to succeed.
 *
 * @author LightStar
 * @since 0.0.1
 */
public class AccountNotFoundException extends UrlShortException {

    /**
     * Constructs <code>AccountNotFoundException</code> object.
     *
     * @param message message with additional info.
     */
    public AccountNotFoundException(final String message) {
        super(message);
    }
}
