package ru.lightstar.urlshort.controller.response;

/**
 * Model class for data in error response.
 *
 * @author LightStar
 * @since 0.0.1
 */
public class ErrorResponse {

    /**
     * Error message.
     */
    private String error = "";

    /**
     * Constructs empty <code>ErrorResponse</code> object.
     */
    public ErrorResponse() {
    }

    /**
     * Constructs <code>ErrorResponse</code> object.
     *
     * @param error error message.
     */
    public ErrorResponse(final String error) {
        this.error = error;
    }

    /**
     * Get error message.
     *
     * @return error message.
     */
    public String getError() {
        return this.error;
    }

    /**
     * Set error message.
     *
     * @param error error message.
     */
    public void setError(final String error) {
        this.error = error;
    }
}