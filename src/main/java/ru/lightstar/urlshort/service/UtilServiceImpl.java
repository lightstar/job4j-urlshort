package ru.lightstar.urlshort.service;

import org.apache.commons.codec.binary.Hex;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Collection;

/**
 * Implementation of service with miscellaneous functions.
 *
 * @author LightStar
 * @since 0.0.1
 */
@Service
public class UtilServiceImpl implements UtilService {

    /**
     * Constant salt used for hashing passwords.
     */
    private static final byte[] HASH_SALT = "u65GmssgJGLZ".getBytes();

    /**
     * Default algorithm used for hashing passwords.
     */
    private static final String HASH_ALG_DEF = "SHA-256";

    /**
     * Encoding of password's characters.
     */
    private static final String PASSWORD_ENCODING = "UTF-8";

    /**
     * Object used to get random numbers.
     */
    private final SecureRandom random = new SecureRandom();

    /**
     * Array of all symbols used for random string generation.
     */
    private final char[] symbols;

    /**
     * Algorithm used for hashing passwords.
     */
    private String hashAlg = HASH_ALG_DEF;

    /**
     * Constructs <code>UtilServiceImpl</code> object.
     */
    public UtilServiceImpl() {
        final StringBuilder sb = new StringBuilder();
        this.appendSymbolRange(sb, '0', '9');
        this.appendSymbolRange(sb, 'a', 'z');
        this.appendSymbolRange(sb, 'A', 'Z');
        this.symbols = sb.toString().toCharArray();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getRandomString(final int length) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(this.symbols[this.random.nextInt(this.symbols.length)]);
        }
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getHashAlg() {
        return this.hashAlg;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setHashAlg(final String hashAlg) {
        this.hashAlg = hashAlg;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getHashedPassword(final String password) {
        String hashedPassword = "";

        if (!password.isEmpty()) {
            try {
                final MessageDigest messageDigest = MessageDigest.getInstance(this.hashAlg);
                messageDigest.update(password.getBytes(Charset.forName(PASSWORD_ENCODING)));
                hashedPassword = Hex.encodeHexString(messageDigest.digest(HASH_SALT));
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }

        return hashedPassword;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getAuthName() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null ? authentication.getName() : "";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getAuthRole() {
        String role = "";

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            if (authorities.size() > 0) {
                final String authority = authorities.iterator().next().getAuthority();
                if (authority != null) {
                    role = authority;
                }
            }
        }

        return role;
    }

    /**
     * Append to given <code>StringBuilder</code> object all chars in given range.
     *
     * @param sb <code>StringBuilder</code> object.
     * @param startChar start char of given range.
     * @param endChar end char of given range.
     */
    private void appendSymbolRange(final StringBuilder sb, final char startChar, final char endChar) {
        for (char ch = startChar; ch <= endChar; ch++) {
            sb.append(ch);
        }
    }
}