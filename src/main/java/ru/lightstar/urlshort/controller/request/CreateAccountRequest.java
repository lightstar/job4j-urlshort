package ru.lightstar.urlshort.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model class for data in create account request.
 *
 * @author LightStar
 * @since 0.0.1
 */
public class CreateAccountRequest {

    /**
     * Account's id.
     */
    @JsonProperty("AccountId")
    private String accountId = "";

    /**
     * Constructs empty <code>CreateAccountRequest</code> object.
     */
    public CreateAccountRequest() {
    }

    /**
     * Get account's id.
     *
     * @return account's id.
     */
    public String getAccountId() {
        return this.accountId;
    }

    /**
     * Set account's id.
     *
     * @param accountId account's id.
     */
    public void setAccountId(final String accountId) {
        this.accountId = accountId;
    }
}