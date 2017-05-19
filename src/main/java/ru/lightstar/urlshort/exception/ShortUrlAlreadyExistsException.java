package ru.lightstar.urlshort.exception;

/**
 * Exception thrown when short url already exists but shouldn't for operation to succeed.
 *
 * @author LightStar
 * @since 0.0.1
 */
public class ShortUrlAlreadyExistsException extends UrlShortException {

    /**
     * Constructs <code>ShortUrlAlreadyExistsException</code> object.
     *
     * @param message message with additional info.
     */
    public ShortUrlAlreadyExistsException(final String message) {
        super(message);
    }
}