package ru.lightstar.urlshort.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model class for data in register url request.
 *
 * @author LightStar
 * @since 0.0.1
 */
public class RegisterUrlRequest {

    /**
     * Long url string.
     */
    @JsonProperty("url")
    private String longUrl = "";

    /**
     * Redirect type.
     */
    private int redirectType = 0;

    /**
     * Constructs empty <code>RegisterUrlRequest</code> object.
     */
    public RegisterUrlRequest() {
    }

    /**
     * Get long url string.
     *
     * @return long url string.
     */
    public String getLongUrl() {
        return this.longUrl;
    }

    /**
     * Set long url string.
     *
     * @param longUrl long url string.
     */
    public void setLongUrl(final String longUrl) {
        this.longUrl = longUrl;
    }

    /**
     * Get url's redirect type.
     *
     * @return url's redirect type.
     */
    public int getRedirectType() {
        return this.redirectType;
    }

    /**
     * Set url's redirect type.
     *
     * @param redirectType url's redirect type.
     */
    public void setRedirectType(final int redirectType) {
        this.redirectType = redirectType;
    }
}