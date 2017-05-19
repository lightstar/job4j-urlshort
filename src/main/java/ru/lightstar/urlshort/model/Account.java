package ru.lightstar.urlshort.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * User account model class.
 *
 * @author LightStar
 * @since 0.0.1
 */
public class Account {

    /**
     * Account's id.
     */
    private String id = "";

    /**
     * Account's hashed password.
     */
    private String password = "";

    /**
     * Map long url string to <code>Url</code> objects.
     */
    private Map<String, Url> urlMap = new ConcurrentHashMap<>();

    /**
     * Constructs empty <code>Account</code> object.
     */
    public Account() {
    }

    /**
     * Constructs <code>Account</code> object.
     *
     * @param id account's id.
     * @param password account's password.
     */
    public Account(final String id, final String password) {
        this.id = id;
        this.password = password;
    }

    /**
     * Get account's id.
     *
     * @return account's id.
     */
    public String getId() {
        return this.id;
    }

    /**
     * Set account's id.
     *
     * @param id account's id.
     */
    public void setId(final String id) {
        this.id = id;
    }

    /**
     * Get account's hashed password.
     *
     * @return account's hashed password.
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Set account's hashed password.
     *
     * @param password account's hashed password.
     */
    public void setPassword(final String password) {
        this.password = password;
    }

    /**
     * Get map of long url strings to <code>Url</code> objects.
     *
     * @return map of long url strings to <code>Url</code> objects.
     */
    public Map<String, Url> getUrlMap() {
        return this.urlMap;
    }

    /**
     * Set map of long url strings to <code>Url</code> objects.
     *
     * @param urlMap map of long url strings to <code>Url</code> objects.
     */
    public void setUrlMap(final Map<String, Url> urlMap) {
        this.urlMap = urlMap;
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

        final Account account = (Account) obj;
        return this.id.equals(account.id) && this.password.equals(account.password) &&
                this.urlMap.equals(account.urlMap);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = this.id.hashCode();
        result = 31 * result + this.password.hashCode();
        result = 31 * result + this.urlMap.hashCode();
        return result;
    }
}