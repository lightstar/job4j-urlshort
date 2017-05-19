package ru.lightstar.urlshort.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Model class for data in create account response.
 *
 * @author LightStar
 * @since 0.0.1
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CreateAccountResponse {

    /**
     * Request success flag.
     */
    private boolean success = false;

    /**
     * Result description.
     */
    private String description = "";

    /**
     * Created account's generated password.
     */
    private String password = "";

    /**
     * Constructs <code>CreateAccountResponse</code> object.
     */
    public CreateAccountResponse() {
    }

    /**
     * Check if request was successful.
     *
     * @return <code>true</code> if request was successful and <code>false</code> otherwise.
     */
    public boolean isSuccess() {
        return this.success;
    }

    /**
     * Set request success flag.
     *
     * @param success request success flag.
     */
    public void setSuccess(final boolean success) {
        this.success = success;
    }

    /**
     * Get result description.
     *
     * @return result description.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Set result description.
     *
     * @param description result description.
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * Get created account's password.
     *
     * @return created account's password.
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Set created account's password.
     *
     * @param password created account's password.
     */
    public void setPassword(final String password) {
        this.password = password;
    }
}