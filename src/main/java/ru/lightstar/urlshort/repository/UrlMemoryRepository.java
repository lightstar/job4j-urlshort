package ru.lightstar.urlshort.repository;

import org.springframework.beans.factory.annotation.Autowired;
import ru.lightstar.urlshort.exception.LongUrlAlreadyExistsException;
import ru.lightstar.urlshort.exception.ShortUrlAlreadyExistsException;
import ru.lightstar.urlshort.exception.UrlNotFoundException;
import ru.lightstar.urlshort.model.Account;
import ru.lightstar.urlshort.model.Url;
import org.springframework.stereotype.*;
import ru.lightstar.urlshort.storage.MemoryStorage;

/**
 * Implementation of url repository that operates data in memory.
 *
 * @author LightStar
 * @since 0.0.1
 */
@Repository
public class UrlMemoryRepository implements UrlRepository {

    /**
     * Memory storage bean that stores data in memory.
     */
    private final MemoryStorage memoryStorage;

    /**
     * Constructs <code>UrlMemoryRepository</code> object.
     *
     * @param memoryStorage injected memory storage bean.
     */
    @Autowired
    public UrlMemoryRepository(final MemoryStorage memoryStorage) {
        this.memoryStorage = memoryStorage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Url getByShort(final String shortUrl) throws UrlNotFoundException {
        return this.memoryStorage.getUrlByShortUrl(shortUrl);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Url register(final Account account, final String shortUrl, final String longUrl, final int redirectType)
            throws ShortUrlAlreadyExistsException, LongUrlAlreadyExistsException {
        return this.memoryStorage.registerUrl(account, shortUrl, longUrl, redirectType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void increaseHitCount(final Url url) {
        this.memoryStorage.increaseUrlHitCount(url);
    }
}