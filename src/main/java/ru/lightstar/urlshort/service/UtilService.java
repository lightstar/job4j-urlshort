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
     * Generate hashed representation of given password.
     *
     * @param password open password.
     * @return hashed password.
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