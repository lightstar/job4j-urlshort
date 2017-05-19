package ru.lightstar.urlshort.model;

/**
 * Class that combines account and open password as account itself contains only hashed password.
 *
 * @author LightStar
 * @since 0.0.1
 */
public class AccountWithOpenPassword {

    /**
     * User's account object.
     */
    private final Account account;

    /**
     * Account's open password.
     */
    private final String openPassword;

    /**
     * Constructs <code>AccountWithOpenPassword</code> object.
     *
     * @param account <code>Account</code> object.
     * @param openPassword account's open password.
     */
    public AccountWithOpenPassword(final Account account, final String openPassword) {
        this.account = account;
        this.openPassword = openPassword;
    }

    /**
     * Get <code>Account</code> object.
     *
     * @return <code>Account</code> object.
     */
    public Account getAccount() {
        return this.account;
    }

    /**
     * Get account's open password.
     *
     * @return account's open password.
     */
    public String getOpenPassword() {
        return this.openPassword;
    }
}