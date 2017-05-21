package ru.lightstar.urlshort.service;

/**
 * Service providing miscellaneous functions.
 *
 * @author LightStar
 * @since 0.0.1
 */
public interface UtilService {

    /**
     * Generate random string of alpha-numeric characters with given length.
     *
     * @param length string's length.
     * @return generated string.
     */
    String getRandomString(int length);

    /**
     * Get algorithm used for hashing passwords.
     *
     * @return algorithm used for hashing passwords.
     */
    String getHashAlg();

    /**
     * Set algorithm used for hashing passwords.
     *
     * @param hashAlg algorithm used for hashing passwords.
     */
    void setHashAlg(String hashAlg);

    /**
     * Generate hashed representation of given password.
     *
     * @param password open password.
     * @return hashed password.
     * @throws RuntimeException thrown if hash algorithm is unknown.
     */
    String getHashedPassword(String password);

    /**
     * Get name of currently authenticated user.
     *
     * @return name of currently authenticated user.
     */
    String getAuthName();

    /**
     * Get role of currently authenticated user.
     *
     * @return role of currently authenticated user.
     */
    String getAuthRole();
}