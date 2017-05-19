package ru.lightstar.urlshort.service;

import ru.lightstar.urlshort.exception.AccountAlreadyExistsException;
import ru.lightstar.urlshort.exception.AccountNotFoundException;
import ru.lightstar.urlshort.exception.LongUrlAlreadyExistsException;
import ru.lightstar.urlshort.model.Account;
import ru.lightstar.urlshort.model.AccountWithOpenPassword;
import ru.lightstar.urlshort.model.Url;

import java.util.Map;

/**
 * Service performing configuration part of application.
 *
 * @author LightStar
 * @since 0.0.1
 */
public interface ConfigService {

    /**
     * Retrieve <code>Account</code> object using id string.
     *
     * @param id account's id.
     * @return retrieved <code>Account</code> object.
     * @throws AccountNotFoundException thrown if account can't be found.
     */
    Account getAccountById(String id) throws AccountNotFoundException;

    /**
     * Creates new account with given id.
     *
     * @param id account's id.
     * @return created <code>Account</code> object and open password.
     * @throws AccountAlreadyExistsException thrown if account with given id already exists.
     */
    AccountWithOpenPassword createAccount(String id) throws AccountAlreadyExistsException;

    /**
     * Register new url for given account.
     *
     * @param account <code>Account</code> object.
     * @param longUrl long url string to register.
     * @param redirectType redirect type for url.
     * @return created <code>Url</code> object.
     * @throws LongUrlAlreadyExistsException thrown if given long url already registered in account.
     */
    Url registerUrl(Account account, String longUrl, int redirectType) throws LongUrlAlreadyExistsException;

    /**
     * Get map of statistic for given account. Its keys are long urls and values - count of hits for that urls.
     *
     * @param account <code>Account</code> object.
     * @return map of statistic.
     */
    Map<String, Integer> getStatistic(Account account);
}
