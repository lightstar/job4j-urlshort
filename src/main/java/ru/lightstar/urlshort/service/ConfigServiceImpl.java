package ru.lightstar.urlshort.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.lightstar.urlshort.exception.AccountAlreadyExistsException;
import ru.lightstar.urlshort.exception.AccountNotFoundException;
import ru.lightstar.urlshort.exception.LongUrlAlreadyExistsException;
import ru.lightstar.urlshort.exception.ShortUrlAlreadyExistsException;
import ru.lightstar.urlshort.model.Account;
import ru.lightstar.urlshort.model.AccountWithOpenPassword;
import ru.lightstar.urlshort.model.Url;
import ru.lightstar.urlshort.repository.AccountRepository;
import ru.lightstar.urlshort.repository.UrlRepository;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of service performing configuration part of application.
 *
 * @author LightStar
 * @since 0.0.1
 */
@Service
public class ConfigServiceImpl implements ConfigService {

    /**
     * Generated account's passwords length.
     */
    private final static int PASSWORD_LENGTH = 8;

    /**
     * Short url string length.
     */
    private final static int SHORT_URL_LENGTH = 6;

    /**
     * Account repository bean used to manipulate accounts in storage.
     */
    private final AccountRepository accountRepository;

    /**
     * Url repository bean used to manipulate urls in storage.
     */
    private final UrlRepository urlRepository;

    /**
     * Util service bean with useful functions.
     */
    private final UtilService utilService;

    /**
     * Constructs <code>ConfigServiceImpl</code> object.
     *
     * @param accountRepository injected account repository bean.
     * @param urlRepository injected url repository bean.
     * @param utilService injected util service bean.
     */
    @Autowired
    public ConfigServiceImpl(final AccountRepository accountRepository, final UrlRepository urlRepository,
                             final UtilService utilService) {
        this.accountRepository = accountRepository;
        this.urlRepository = urlRepository;
        this.utilService = utilService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Account getAccountById(final String id) throws AccountNotFoundException {
        return this.accountRepository.getById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AccountWithOpenPassword createAccount(final String id) throws AccountAlreadyExistsException {
        final String openPassword = this.utilService.getRandomString(PASSWORD_LENGTH);
        final Account account = this.accountRepository.create(id, this.utilService.getHashedPassword(openPassword));
        return new AccountWithOpenPassword(account, openPassword);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Url registerUrl(final Account account, final String longUrl, final int redirectType)
            throws LongUrlAlreadyExistsException {
        while (true) {
            final String shortUrl = this.utilService.getRandomString(SHORT_URL_LENGTH);
            try {
                return this.urlRepository.register(account, shortUrl, longUrl, redirectType);
            } catch (ShortUrlAlreadyExistsException e) {
                // Implicit handling of this exception is trying to register url again with another short url string.
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, Integer> getStatistic(final Account account) {
        final Map<String, Integer> statistic = new HashMap<>();
        for (final Url url : account.getUrlMap().values()) {
            statistic.put(url.getLongUrl(), url.getHitCount());
        }
        return statistic;
    }
}
