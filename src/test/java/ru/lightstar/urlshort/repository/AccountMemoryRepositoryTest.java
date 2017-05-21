package ru.lightstar.urlshort.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.lightstar.urlshort.TestConstants;
import ru.lightstar.urlshort.exception.UrlShortException;
import ru.lightstar.urlshort.storage.MemoryStorage;

/**
 * <code>AccountMemoryRepository</code> class tests.
 *
 * @author LightStar
 * @since 0.0.1
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountMemoryRepositoryTest extends Mockito {

    /**
     * Mocked memory storage bean.
     */
    @MockBean
    private MemoryStorage memoryStorage;

    /**
     * <code>AccountMemoryRepository</code> object used in all tests.
     */
    @Autowired
    private AccountMemoryRepository accountMemoryRepository;

    /**
     * Test correctness of <code>getById</code> method.
     */
    @Test
    public void whenGetByIdThenCallMemoryStorage() throws UrlShortException {
        this.accountMemoryRepository.getById(TestConstants.ID);
        verify(this.memoryStorage, times(1)).getAccountById(TestConstants.ID);
        verifyNoMoreInteractions(this.memoryStorage);
    }

    /**
     * Test correctness of <code>create</code> method.
     */
    @Test
    public void whenCreateThenCallMemoryStorage() throws UrlShortException {
        this.accountMemoryRepository.create(TestConstants.ID, TestConstants.PASSWORD);
        verify(this.memoryStorage, times(1)).createAccount(TestConstants.ID,
                TestConstants.PASSWORD);
        verifyNoMoreInteractions(this.memoryStorage);
    }
}