package ru.lightstar.urlshort.exception;

/**
 * Exception thrown when url not found but it needed for operation to succeed.
 *
 * @author LightStar
 * @since 0.0.1
 */
public class UrlNotFoundException extends UrlShortException {

    /**
     * Constructs <code>UrlNotFoundException</code> object.
     *
     * @param message message with additional info.
     */
    public UrlNotFoundException(final String message) {
        super(message);
    }
}
