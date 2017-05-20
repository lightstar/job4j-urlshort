package ru.lightstar.urlshort.service;

import org.hamcrest.core.IsSame;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.lightstar.urlshort.exception.UrlShortException;
import ru.lightstar.urlshort.model.Url;
import ru.lightstar.urlshort.repository.UrlRepository;

import static org.junit.Assert.assertThat;

/**
 * <code>RedirectService</code> tests.
 *
 * @author Lightstar
 * @since 0.0.1
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedirectServiceTest extends Mockito {

    /**
     * Mocked url repository bean.
     */
    @MockBean
    private UrlRepository urlRepository;

    /**
     * <code>RedirectService</code> object used in all tests.
     */
    @Autowired
    private RedirectService redirectService;

    /**
     * Test correctness of <code>getUrl</code> method.
     */
    @Test
    public void whenGetUrlThenItRetrievesFromRepositoryAndHitCountIncreases() throws UrlShortException {
        final Url url = new Url("shortUrl", "longUrl", 301);
        when(this.urlRepository.getByShort("shortUrl")).thenReturn(url);

        final Url resultUrl = this.redirectService.getUrl("shortUrl");

        assertThat(resultUrl, IsSame.sameInstance(url));
        verify(this.urlRepository, times(1)).getByShort("shortUrl");
        verify(this.urlRepository, times(1)).increaseHitCount(url);
        verifyNoMoreInteractions(this.urlRepository);
    }
}