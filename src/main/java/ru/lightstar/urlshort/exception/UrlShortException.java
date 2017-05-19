package ru.lightstar.urlshort.exception;

/**
 * Base class for all application-specific exceptions.
 *
 * @author LightStar
 * @since 0.0.1
 */
public class UrlShortException extends Exception {

    /**
     * Constructs <code>UrlShortException</code> object.
     *
     * @param message message with additional info.
     */
    public UrlShortException(final String message) {
        super(message);
    }
}
