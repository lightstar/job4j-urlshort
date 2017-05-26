package ru.lightstar.urlshort.storage;

import org.springframework.stereotype.Component;
import ru.lightstar.urlshort.exception.*;
import ru.lightstar.urlshort.model.Account;
import ru.lightstar.urlshort.model.Url;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This component stores objects in memory, so all of them are dropped on application restart.
 *
 * @author LightStar
 * @since 0.0.1
 */
@Component
public class MemoryStorage {

    /**
     * Map short url strings to <code>Url</code> objects.
     */
    private final Map<String, Url> urlMap = new ConcurrentHashMap<>();

    /**
     * Map account id strings to <code>Account</code> objects.
     */
    private final Map<String, Account> accountMap = new ConcurrentHashMap<>();

    /**
     * Retrieve <code>Url</code> object using short url string.
     *
     * @param shortUrl short url string.
     * @return corresponding <code>Url</code> object.
     * @throws UrlNotFoundException thrown if <code>Url</code> object with this short url does not exists.
     */
    public Url getUrlByShortUrl(final String shortUrl) throws UrlNotFoundException {
        final Url url = this.urlMap.get(shortUrl);
        if (url == null) {
            throw new UrlNotFoundException(String.format("Url with short url '%s' not found", shortUrl));
        }
        return url;
    }

    /**
     * Retrieve <code>Account</code> object using id string.
     *
     * @param id account's id.
     * @return corresponding <code>Account</code> object.
     * @throws AccountNotFoundException thrown if <code>Account</code> object with this id does not exists.
     */
    public Account getAccountById(final String id) throws AccountNotFoundException {
        final Account account = this.accountMap.get(id);
        if (account == null) {
            throw new AccountNotFoundException(String.format("Account with id '%s' not found", id));
        }
        return account;
    }

    /**
     * Create new account.
     *
     * @param id account's id.
     * @param password account's hashed password.
     * @return created <code>Account</code> object.
     * @throws AccountAlreadyExistsException thrown if <code>Account</code> object with this id already exists.
     */
    public Account createAccount(final String id, final String password)
            throws AccountAlreadyExistsException {
        final Account account = new Account(id, password);
        if (this.accountMap.putIfAbsent(id, account) != null) {
            throw new AccountAlreadyExistsException(String.format("Account with id '%s' already exists", id));
        }

        return account;
    }

    /**
     * Register new url for account.
     *
     * @param account <code>Account</code> object.
     * @param shortUrl short url string.
     * @param longUrl long url string.
     * @param redirectType url's redirect type.
     * @return created <code>Url</code> object.
     * @throws ShortUrlAlreadyExistsException thrown if <code>Url</code> object with given short url string already
     * exists.
     * @throws LongUrlAlreadyExistsException thrown if <code>Url</code> object with given long url string already
     * registered in account.
     */
    public synchronized Url registerUrl(final Account account, final String shortUrl, final String longUrl,
                                         final int redirectType)
            throws ShortUrlAlreadyExistsException, LongUrlAlreadyExistsException {

        if (this.urlMap.containsKey(shortUrl)) {
            throw new ShortUrlAlreadyExistsException(String.format("Url with short url '%s' already exists", shortUrl));
        }

        if (account.getUrlMap().containsKey(longUrl)) {
            throw new LongUrlAlreadyExistsException(String.format("Url '%s' already exists for that account", longUrl));
        }

        final Url url = new Url(shortUrl, longUrl, redirectType);
        account.getUrlMap().put(longUrl, url);
        this.urlMap.put(shortUrl, url);

        return url;
    }

    /**
     * Increase hit count for the given <code>Url</code> object.
     *
     * @param url <code>Url</code> object.
     */
    public void increaseUrlHitCount(final Url url) {
        url.increaseHitCount();
    }
}