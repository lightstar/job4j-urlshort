package ru.lightstar.urlshort.service;

import org.hamcrest.core.IsSame;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.lightstar.urlshort.TestConstants;
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
        this.configService.getAccountById(TestConstants.ID);

        verify(this.accountRepository, times(1)).getById(TestConstants.ID);
        verifyNoMoreInteractions(this.accountRepository);
    }

    /**
     * Test correctness of <code>createAccount</code> method.
     */
    @Test
    public void whenCreateAccountThenItCreatesUsingUtilServiceAndRepository() throws UrlShortException {
        when(this.utilService.getRandomString(anyInt())).thenReturn(TestConstants.OPEN_PASSWORD);
        when(this.utilService.getHashedPassword(TestConstants.OPEN_PASSWORD)).thenReturn(TestConstants.PASSWORD);

        final Account account = new Account(TestConstants.ID, TestConstants.PASSWORD);
        when(this.accountRepository.create(TestConstants.ID, TestConstants.PASSWORD)).thenReturn(account);

        final AccountWithOpenPassword accountWithOpenPassword = this.configService.createAccount(TestConstants.ID);

        assertThat(accountWithOpenPassword.getAccount(), IsSame.sameInstance(account));
        assertThat(accountWithOpenPassword.getOpenPassword(), is(TestConstants.OPEN_PASSWORD));

        verify(this.accountRepository, times(1)).create(TestConstants.ID, TestConstants.PASSWORD);
        verifyNoMoreInteractions(this.accountRepository);
    }

    /**
     * Test correctness of <code>registerUrl</code> method.
     */
    @Test
    public void whenRegisterUrlThenItRegistersUsingUtilServiceAndRepository() throws UrlShortException {
        when(this.utilService.getRandomString(anyInt())).thenReturn(TestConstants.SHORT_URL);

        final Account account = new Account(TestConstants.ID, TestConstants.PASSWORD);
        final Url url = new Url(TestConstants.SHORT_URL, TestConstants.LONG_URL,
                TestConstants.REDIRECT_TYPE_PERMANENT);
        when(this.urlRepository.register(account, TestConstants.SHORT_URL, TestConstants.LONG_URL,
                TestConstants.REDIRECT_TYPE_PERMANENT)).thenReturn(url);

        final Url resultUrl = this.configService.registerUrl(account, TestConstants.LONG_URL,
                TestConstants.REDIRECT_TYPE_PERMANENT);

        assertThat(resultUrl, IsSame.sameInstance(url));
        verify(this.urlRepository, times(1)).register(account, TestConstants.SHORT_URL,
                TestConstants.LONG_URL, TestConstants.REDIRECT_TYPE_PERMANENT);
        verifyNoMoreInteractions(this.urlRepository);
    }

    /**
     * Test correctness of <code>registerUrl</code> method when short url collision happens.
     */
    @Test
    public void whenRegisterUrlAndShortUrlCollisionHappensThenItWillTryAgain() throws UrlShortException {
        when(this.utilService.getRandomString(anyInt())).thenReturn(TestConstants.SHORT_URL)
                .thenReturn(TestConstants.SHORT_URL2);

        final Account account = new Account(TestConstants.ID, TestConstants.PASSWORD);
        final Url url = new Url(TestConstants.SHORT_URL2, TestConstants.LONG_URL,
                TestConstants.REDIRECT_TYPE_PERMANENT);

        when(this.urlRepository.register(account, TestConstants.SHORT_URL, TestConstants.LONG_URL,
                TestConstants.REDIRECT_TYPE_PERMANENT))
                .thenThrow(new ShortUrlAlreadyExistsException("Short url already exists"));
        when(this.urlRepository.register(account, TestConstants.SHORT_URL2, TestConstants.LONG_URL,
                TestConstants.REDIRECT_TYPE_PERMANENT)).thenReturn(url);

        final Url resultUrl = this.configService.registerUrl(account, TestConstants.LONG_URL,
                TestConstants.REDIRECT_TYPE_PERMANENT);

        assertThat(resultUrl, IsSame.sameInstance(url));
        verify(this.urlRepository, times(2)).register(same(account), anyString(),
                eq(TestConstants.LONG_URL), eq(TestConstants.REDIRECT_TYPE_PERMANENT));
        verifyNoMoreInteractions(this.urlRepository);
    }

    /**
     * Test correctness of <code>getStatistic</code> method.
     */
    @Test
    public void whenGetStatisticThenResult() {
        final Url url1 = new Url(TestConstants.SHORT_URL, TestConstants.LONG_URL,
                TestConstants.REDIRECT_TYPE_PERMANENT);
        url1.setHitCount(TestConstants.HIT_COUNT);
        final Url url2 = new Url(TestConstants.SHORT_URL2, TestConstants.LONG_URL2,
                TestConstants.REDIRECT_TYPE_TEMPORARY);
        url2.setHitCount(TestConstants.HIT_COUNT2);

        final Map<String, Url> urlMap = new HashMap<>();
        urlMap.put(TestConstants.LONG_URL, url1);
        urlMap.put(TestConstants.LONG_URL2, url2);

        final Account account = new Account(TestConstants.ID, TestConstants.PASSWORD);
        account.setUrlMap(urlMap);

        final Map<String, Integer> statisticMap = this.configService.getStatistic(account);

        assertThat(statisticMap.keySet(), hasSize(2));
        assertThat(statisticMap, allOf(hasEntry(TestConstants.LONG_URL, TestConstants.HIT_COUNT),
                hasEntry(TestConstants.LONG_URL2, TestConstants.HIT_COUNT2)));
    }
}