package ru.lightstar.urlshort.repository;

import ru.lightstar.urlshort.exception.LongUrlAlreadyExistsException;
import ru.lightstar.urlshort.exception.ShortUrlAlreadyExistsException;
import ru.lightstar.urlshort.exception.UrlNotFoundException;
import ru.lightstar.urlshort.model.Account;
import ru.lightstar.urlshort.model.Url;

/**
 * Interface for repository that manipulates <code>Url</code> objects in storage.
 *
 * @author LightStar
 * @since 0.0.1
 */
public interface UrlRepository {

    /**
     * Retrieve <code>Url</code> object for given short url string.
     *
     * @param shortUrl short url string.
     * @return found <code>Url</code> object.
     * @throws UrlNotFoundException thrown if <code>Url</code> object can't be found.
     */
    Url getByShort(String shortUrl) throws UrlNotFoundException;

    /**
     * Register new url for given account.
     *
     * @param account user's account.
     * @param shortUrl short url string.
     * @param longUrl long url string.
     * @param redirectType url's redirect type.
     * @return created <code>Url</code> object.
     * @throws ShortUrlAlreadyExistsException thrown if url for given short url string already exists.
     * @throws LongUrlAlreadyExistsException thrown if url for given long url string already registered in account.
     */
    Url register(Account account, String shortUrl, String longUrl, int redirectType)
            throws ShortUrlAlreadyExistsException, LongUrlAlreadyExistsException;

    /**
     * Increase url's hit count by one.
     *
     * @param url given <code>Url</code> object.
     */
    void increaseHitCount(Url url);
}