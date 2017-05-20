package ru.lightstar.urlshort.service;

import org.hamcrest.core.IsSame;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.lightstar.urlshort.exception.ShortUrlAlreadyExistsException;
import ru.lightstar.urlshort.exception.UrlShortException;
import ru.lightstar.urlshort.model.Account;
import ru.lightstar.urlshort.model.AccountWithOpenPassword;
import ru.lightstar.urlshort.model.Url;
import ru.lightstar.urlshort.repository.AccountRepository;
import ru.lightstar.urlshort.repository.UrlRepository;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * <code>ConfigService</code> tests.
 *
 * @author Lightstar
 * @since 0.0.1
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ConfigServiceTest extends Mockito {

    /**
     * Mocked utils service bean.
     */
    @MockBean
    private UtilService utilService;

    /**
     * Mocked account repository bean.
     */
    @MockBean
    private AccountRepository accountRepository;

    /**
     * Mocked url repository bean.
     */
    @MockBean
    private UrlRepository urlRepository;

    /**
     * <code>ConfigService</code> object used in all tests.
     */
    @Autowired
    private ConfigService configService;

    /**
     * Test correctness of <code>getAccountById</code> method.
     */
    @Test
    public void whenGetAccountByIdThenCallRepository() throws UrlShortException {
        this.configService.getAccountById("test");

        verify(this.accountRepository, times(1)).getById("test");
        verifyNoMoreInteractions(this.accountRepository);
    }

    /**
     * Test correctness of <code>createAccount</code> method.
     */
    @Test
    public void whenCreateAccountThenItCreatesUsingUtilServiceAndRepository() throws UrlShortException {
        when(this.utilService.getRandomString(anyInt())).thenReturn("testPassword");
        when(this.utilService.getHashedPassword("testPassword")).thenReturn("hashedTestPassword");

        final Account account = new Account("test", "hashedTestPassword");
        when(this.accountRepository.create("test", "hashedTestPassword")).thenReturn(account);

        final AccountWithOpenPassword accountWithOpenPassword = this.configService.createAccount("test");

        assertThat(accountWithOpenPassword.getAccount(), IsSame.sameInstance(account));
        assertThat(accountWithOpenPassword.getOpenPassword(), is("testPassword"));

        verify(this.accountRepository, times(1)).create("test", "hashedTestPassword");
        verifyNoMoreInteractions(this.accountRepository);
    }

    /**
     * Test correctness of <code>registerUrl</code> method.
     */
    @Test
    public void whenRegisterUrlThenItRegistersUsingUtilServiceAndRepository() throws UrlShortException {
        when(this.utilService.getRandomString(anyInt())).thenReturn("shortUrl");

        final Account account = new Account("test", "testPassword");
        final Url url = new Url("shortUrl", "longUrl", 301);
        when(this.urlRepository.register(account, "shortUrl", "longUrl", 301)).thenReturn(url);

        final Url resultUrl = this.configService.registerUrl(account, "longUrl", 301);

        assertThat(resultUrl, IsSame.sameInstance(url));
        verify(this.urlRepository, times(1)).register(account, "shortUrl",
                "longUrl", 301);
        verifyNoMoreInteractions(this.urlRepository);
    }

    /**
     * Test correctness of <code>registerUrl</code> method when short url collision happens.
     */
    @Test
    public void whenRegisterUrlAndShortUrlCollisionHappensThenItWillTryAgain() throws UrlShortException {
        when(this.utilService.getRandomString(anyInt())).thenReturn("shortUrl").thenReturn("anotherShortUrl");

        final Account account = new Account("test", "testPassword");
        final Url url = new Url("anotherShortUrl", "longUrl", 301);

        when(this.urlRepository.register(account, "shortUrl", "longUrl", 301))
                .thenThrow(new ShortUrlAlreadyExistsException("Short url already exists"));
        when(this.urlRepository.register(account, "anotherShortUrl", "longUrl", 301))
                .thenReturn(url);

        final Url resultUrl = this.configService.registerUrl(account, "longUrl", 301);

        assertThat(resultUrl, IsSame.sameInstance(url));
        verify(this.urlRepository, times(2)).register(same(account), anyString(),
                eq("longUrl"), eq(301));
        verifyNoMoreInteractions(this.urlRepository);
    }

    /**
     * Test correctness of <code>getStatistic</code> method.
     */
    @Test
    public void whenGetStatisticThenResult() {
        final Url url1 = new Url("shortUrl1", "longUrl1", 301);
        url1.setHitCount(5);
        final Url url2 = new Url("shortUrl2", "longUrl2", 302);
        url2.setHitCount(12);

        final Map<String, Url> urlMap = new HashMap<>();
        urlMap.put("longUrl1", url1);
        urlMap.put("longUrl2", url2);

        final Account account = new Account("test", "testPassword");
        account.setUrlMap(urlMap);

        final Map<String, Integer> statisticMap = this.configService.getStatistic(account);

        assertThat(statisticMap.keySet(), hasSize(2));
        assertThat(statisticMap, allOf(hasEntry("longUrl1", 5), hasEntry("longUrl2", 12)));
    }
}