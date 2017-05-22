package ru.lightstar.urlshort;

/**
 * Constant values for tests.
 *
 * @author Lightstar
 * @since 0.0.1
 */
public class TestConstants {

    /**
     * Test account's id.
     */
    public static final String ID = "test";

    /**
     * Another test account's id.
     */
    public static final String ID2 = "test2";

    /**
     * Test account's hashed password.
     */
    public static final String PASSWORD = "testPassword";

    /**
     * Another test account's hashed password.
     */
    public static final String PASSWORD2 = "testPassword2";

    /**
     * Test account's open password.
     */
    public static final String OPEN_PASSWORD = "testOpenPassword";

    /**
     * Another test account's open password.
     */
    public static final String OPEN_PASSWORD2 = "testOpenPassword2";

    /**
     * Test short url string.
     */
    public static final String SHORT_URL = "shortUrl";

    /**
     * Another test short url string.
     */
    public static final String SHORT_URL2 = "shortUrl2";

    /**
     * Test long url string.
     */
    public static final String LONG_URL = "longUrl";

    /**
     * Another test long url string.
     */
    public static final String LONG_URL2 = "longUrl2";

    /**
     * Test permanent url redirect type.
     */
    public static final int REDIRECT_TYPE_PERMANENT = 301;

    /**
     * Test temporary url redirect type.
     */
    public static final int REDIRECT_TYPE_TEMPORARY = 302;

    /**
     * Test wrong url redirect type.
     */
    public static final int REDIRECT_TYPE_WRONG = 303;

    /**
     * Test url's hit count.
     */
    public static final int HIT_COUNT = 5;

    /**
     * Another test url's hit count.
     */
    public static final int HIT_COUNT2 = 12;

    /**
     * Test response description.
     */
    public static final String DESCRIPTION = "testDescription";

    /**
     * Test error message.
     */
    public static final String ERROR = "testError";

    /**
     * Test hash algorithm (not valid though).
     */
    public static final String HASH_ALG = "testHashAlg";

    /**
     * Test user's role.
     */
    public static final String ROLE = "ROLE_USER";

    /**
     * Standard http schema port used for tests.
     */
    public static final int HTTP_STANDARD_PORT = 80;

    /**
     * Non-standard http schema port used for tests.
     */
    public static final int HTTP_NONSTANDARD_PORT = 8080;

    /**
     * Standard https schema port used for tests.
     */
    public static final int HTTPS_STANDARD_PORT = 443;

    /**
     * Non-standard https schema port used for tests.
     */
    public static final int HTTPS_NONSTANDARD_PORT = 8443;

    /**
     * String that must be in output when spring boot sets up successfully.
     */
    public static final String SPRING_BOOT_STARTUP = "root of context hierarchy";
}