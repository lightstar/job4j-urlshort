package ru.lightstar.urlshort.controller.response;

/**
 * Model class for data in register url response.
 *
 * @author LightStar
 * @since 0.0.1
 */
public class RegisterUrlResponse {

    /**
     * Short url string for registered url.
     */
    private String shortUrl = "";

    /**
     * Constructs empty <code>RegisterUrlResponse</code> object.
     */
    public RegisterUrlResponse() {
    }

    /**
     * Constructs <code>RegisterUrlResponse</code> object.
     *
     * @param shortUrl short url string for registered url.
     */
    public RegisterUrlResponse(final String shortUrl) {
        this.shortUrl = shortUrl;
    }

    /**
     * Get short url string for registered url.
     *
     * @return short url string.
     */
    public String getShortUrl() {
        return this.shortUrl;
    }

    /**
     * Set short url string for registered url.
     *
     * @param shortUrl short url string.
     */
    public void setShortUrl(final String shortUrl) {
        this.shortUrl = shortUrl;
    }
}