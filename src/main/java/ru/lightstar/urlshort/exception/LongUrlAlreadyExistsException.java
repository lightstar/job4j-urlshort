package ru.lightstar.urlshort.exception;

/**
 * Exception thrown when long url already exists but shouldn't for operation to succeed.
 *
 * @author LightStar
 * @since 0.0.1
 */
public class LongUrlAlreadyExistsException extends UrlShortException {

    /**
     * Constructs <code>LongUrlAlreadyExistsException</code> object.
     *
     * @param message message with additional info.
     */
    public LongUrlAlreadyExistsException(String message) {
        super(message);
    }
}