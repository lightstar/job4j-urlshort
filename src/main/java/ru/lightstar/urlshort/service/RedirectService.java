package ru.lightstar.urlshort.service;

import ru.lightstar.urlshort.exception.UrlNotFoundException;
import ru.lightstar.urlshort.model.Url;

/**
 * Service performing url redirect part of application.
 *
 * @author LightStar
 * @since 0.0.1
 */
public interface RedirectService {

    /**
     * Get url object for given short url string.
     *
     * @param shortUrl short url string.
     * @return found <code>Url</code> object.
     * @throws UrlNotFoundException thrown if <code>Url</code> object for given short url string does not exists.
     */
    Url getUrl(String shortUrl) throws UrlNotFoundException;
}
