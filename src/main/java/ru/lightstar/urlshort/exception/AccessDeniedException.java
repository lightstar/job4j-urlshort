package ru.lightstar.urlshort.exception;

/**
 * Exception thrown when user does not have access to operation he tries to perform.
 *
 * @author LightStar
 * @since 0.0.1
 */
public class AccessDeniedException extends UrlShortException {

    /**
     * Constructs <code>AccessDeniedException</code> object.
     *
     * @param message message with additional info.
     */
    public AccessDeniedException(String message) {
        super(message);
    }
}