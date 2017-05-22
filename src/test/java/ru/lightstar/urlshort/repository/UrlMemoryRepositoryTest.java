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
import ru.lightstar.urlshort.model.Account;
import ru.lightstar.urlshort.model.Url;
import ru.lightstar.urlshort.storage.MemoryStorage;

/**
 * <code>UrlMemoryRepository</code> class tests.
 *
 * @author LightStar
 * @since 0.0.1
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UrlMemoryRepositoryTest extends Mockito {

    /**
     * Mocked memory storage bean.
     */
    @MockBean
    private MemoryStorage memoryStorage;

    /**
     * <code>UrlMemoryRepository</code> object used in all tests.
     */
    @Autowired
    private UrlMemoryRepository urlMemoryRepository;

    /**
     * Test correctness of <code>getByShort</code> method.
     */
    @Test
    public void whenGetByShortThenCallMemoryStorage() throws UrlShortException {
        this.urlMemoryRepository.getByShort(TestConstants.SHORT_URL);
        verify(this.memoryStorage, times(1)).getUrlByShortUrl(TestConstants.SHORT_URL);
        verifyNoMoreInteractions(this.memoryStorage);
    }

    /**
     * Test correctness of <code>register</code> method.
     */
    @Test
    public void whenRegisterThenCallMemoryStorage() throws UrlShortException {
        final Account account = new Account(TestConstants.ID, TestConstants.PASSWORD);
        this.urlMemoryRepository.register(account, TestConstants.SHORT_URL, TestConstants.LONG_URL,
                TestConstants.REDIRECT_TYPE_PERMANENT);
        verify(this.memoryStorage, times(1)).registerUrl(account, TestConstants.SHORT_URL,
                TestConstants.LONG_URL, TestConstants.REDIRECT_TYPE_PERMANENT);
        verifyNoMoreInteractions(this.memoryStorage);
    }

    /**
     * Test correctness of <code>increaseHitCount</code> method.
     */
    @Test
    public void whenIncreaseHitCountThenCallMemoryStorage() throws UrlShortException {
        final Url url = new Url(TestConstants.SHORT_URL, TestConstants.LONG_URL,
                TestConstants.REDIRECT_TYPE_PERMANENT);
        this.urlMemoryRepository.increaseHitCount(url);
        verify(this.memoryStorage, times(1)).increaseUrlHitCount(url);
        verifyNoMoreInteractions(this.memoryStorage);
    }
}