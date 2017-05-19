package ru.lightstar.urlshort.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.lightstar.urlshort.exception.AccountAlreadyExistsException;
import ru.lightstar.urlshort.exception.AccountNotFoundException;
import ru.lightstar.urlshort.model.Account;
import ru.lightstar.urlshort.storage.MemoryStorage;

/**
 * Implementation of account repository that operates data in memory.
 *
 * @author LightStar
 * @since 0.0.1
 */
@Repository
public class AccountMemoryRepository implements AccountRepository {

    /**
     * Memory storage bean that stores data in memory.
     */
    private final MemoryStorage memoryStorage;

    /**
     * Constructs <code>AccountMemoryRepository</code> object.
     *
     * @param memoryStorage injected memory storage bean.
     */
    @Autowired
    public AccountMemoryRepository(final MemoryStorage memoryStorage) {
        this.memoryStorage = memoryStorage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Account getById(final String id) throws AccountNotFoundException {
        return this.memoryStorage.getAccountById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Account create(final String id, final String password) throws AccountAlreadyExistsException {
        return this.memoryStorage.createAccount(id, password);
    }
}