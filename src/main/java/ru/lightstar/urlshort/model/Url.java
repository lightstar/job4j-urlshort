package ru.lightstar.urlshort.model;

/**
 * Url model class.
 *
 * @author LightStar
 * @since 0.0.1
 */
public class Url {

    /**
     * Type for permanent redirect.
     */
    public static final int REDIRECT_TYPE_PERMANENT = 301;

    /**
     * Type for temporary redirect.
     */
    public static final int REDIRECT_TYPE_TEMPORARY = 302;

    /**
     * Short url string.
     */
    private String shortUrl = "";

    /**
     * Long url string.
     */
    private String longUrl = "";

    /**
     * Redirect type.
     */
    private int redirectType = 0;

    /**
     * Count of hits for this <code>Url</code> object using short url string.
     */
    private int hitCount = 0;

    /**
     * Constructs empty <code>Url</code> object.
     */
    public Url() {
    }

    /**
     * Constructs <code>Url</code> object.
     *
     * @param shortUrl short url string.
     * @param longUrl long url string.
     * @param redirectType redirect type.
     */
    public Url(final String shortUrl, final String longUrl, final int redirectType) {
        this.shortUrl = shortUrl;
        this.longUrl = longUrl;
        this.redirectType = redirectType;
    }

    /**
     * Get url's short string.
     *
     * @return url's short string.
     */
    public String getShortUrl() {
        return this.shortUrl;
    }

    /**
     * Set url's short string.
     *
     * @param shortUrl url's short string.
     */
    public void setShortUrl(final String shortUrl) {
        this.shortUrl = shortUrl;
    }

    /**
     * Get url's long string.
     *
     * @return url's long string.
     */
    public String getLongUrl() {
        return this.longUrl;
    }

    /**
     * Set url's long string.
     *
     * @param longUrl url's long string.
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

    /**
     * Get url's hit count.
     *
     * @return url's hit count.
     */
    public int getHitCount() {
        return this.hitCount;
    }

    /**
     * Set url's hit count.
     *
     * @param hitCount url's hit count.
     */
    public void setHitCount(final int hitCount) {
        this.hitCount = hitCount;
    }

    /**
     * Increment url's hit count by one.
     */
    public void increaseHitCount() {
        this.hitCount++;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }

        final Url url = (Url) obj;
        return this.shortUrl.equals(url.shortUrl) && this.longUrl.equals(url.longUrl) &&
                this.redirectType != url.redirectType && this.hitCount != url.hitCount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = this.shortUrl.hashCode();
        result = 31 * result + this.longUrl.hashCode();
        result = 31 * result + this.redirectType;
        result = 31 * result + this.hitCount;
        return result;
    }
}