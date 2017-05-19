package ru.lightstar.urlshort.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.lightstar.urlshort.exception.UrlNotFoundException;
import ru.lightstar.urlshort.model.Url;
import ru.lightstar.urlshort.repository.UrlRepository;

/**
 * Implementation of service performing url redirect part of application.
 *
 * @author LightStar
 * @since 0.0.1
 */
@Service
public class RedirectServiceImpl implements RedirectService {

    /**
     * Url repository bean used to manipulate urls in storage.
     */
    private final UrlRepository urlRepository;

    /**
     * Constructs <code>RedirectServiceImpl</code> object.
     *
     * @param urlRepository injected url repository bean.
     */
    @Autowired
    public RedirectServiceImpl(final UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Url getUrl(final String shortUrl) throws UrlNotFoundException {
        final Url url = this.urlRepository.getByShort(shortUrl);
        this.urlRepository.increaseHitCount(url);
        return url;
    }
}
