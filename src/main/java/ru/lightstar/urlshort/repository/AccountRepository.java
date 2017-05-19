package ru.lightstar.urlshort.repository;

import ru.lightstar.urlshort.exception.AccountAlreadyExistsException;
import ru.lightstar.urlshort.exception.AccountNotFoundException;
import ru.lightstar.urlshort.model.Account;

/**
 * Interface for repository that manipulates user accounts.
 *
 * @author LightStar
 * @since 0.0.1
 */
public interface AccountRepository {

    /**
     * Retrieve <code>Account</code> object by id.
     *
     * @param id account's id.
     * @return found <code>Account</code> object.
     * @throws AccountNotFoundException thrown if account with given id does not exists.
     */
    Account getById(String id) throws AccountNotFoundException;

    /**
     * Create new account with given id and password.
     *
     * @param id account's id.
     * @param password account's hashed password.
     * @return created <code>Account</code> object.
     * @throws AccountAlreadyExistsException thrown if account with given id already exists.
     */
    Account create(String id, String password) throws AccountAlreadyExistsException;
}