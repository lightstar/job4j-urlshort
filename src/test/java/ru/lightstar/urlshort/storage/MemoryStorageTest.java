package ru.lightstar.urlshort.storage;

import org.hamcrest.core.IsSame;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import ru.lightstar.urlshort.TestConstants;
import ru.lightstar.urlshort.exception.*;
import ru.lightstar.urlshort.model.Account;
import ru.lightstar.urlshort.model.Url;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.springframework.test.annotation.DirtiesContext.ClassMode;

/**
 * <code>MemoryStorage</code> class tests.
 *
 * @author LightStar
 * @since 0.0.1
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class MemoryStorageTest {

    /**
     * <code>MemoryStorage</code> component used in all tests.
     */
    @Autowired
    private MemoryStorage memoryStorage;

    /**
     * Test correctness of <code>createAccount</code> method.
     */
    @Test
    public void whenCreateAccountThenItCreates() throws UrlShortException {
        final Account account = this.memoryStorage.createAccount(TestConstants.ID, TestConstants.PASSWORD);

        assertThat(account.getId(), is(TestConstants.ID));
        assertThat(account.getPassword(), is(TestConstants.PASSWORD));
        assertThat(account.getUrlMap(), notNullValue());
        assertThat(account.getUrlMap().keySet(), is(empty()));
        assertThat(this.memoryStorage.getAccountById(TestConstants.ID), IsSame.sameInstance(account));
    }

    /**
     * Test correctness of <code>getAccountById</code> method when account does not exists.
     */
    @Test(expected = AccountNotFoundException.class)
    public void whenGetAbsentAccountThenException() throws UrlShortException {
        this.memoryStorage.getAccountById(TestConstants.ID);
    }

    /**
     * Test correctness of <code>createAccount</code> method when account with that id already exists.
     */
    @Test(expected = AccountAlreadyExistsException.class)
    public void whenCreateAccountWithSameIdThenException() throws UrlShortException {
        this.memoryStorage.createAccount(TestConstants.ID, TestConstants.PASSWORD);
        this.memoryStorage.createAccount(TestConstants.ID, TestConstants.PASSWORD);
    }

    /**
     * Test correctness of <code>registerUrl</code> method.
     */
    @Test
    public void whenRegisterUrlThenItRegisters() throws UrlShortException {
        final Account account = this.memoryStorage.createAccount(TestConstants.ID, TestConstants.PASSWORD);
        final Url url = this.memoryStorage.registerUrl(account, TestConstants.SHORT_URL, TestConstants.LONG_URL,
                TestConstants.REDIRECT_TYPE_PERMANENT);

        assertThat(url.getShortUrl(), is(TestConstants.SHORT_URL));
        assertThat(url.getLongUrl(), is(TestConstants.LONG_URL));
        assertThat(url.getRedirectType(), is(TestConstants.REDIRECT_TYPE_PERMANENT));
        assertThat(url.getHitCount(), is(0));
        assertThat(account.getUrlMap().keySet(), hasSize(1));
        assertThat(account.getUrlMap().get(TestConstants.LONG_URL), is(url));
        assertThat(this.memoryStorage.getUrlByShortUrl(TestConstants.SHORT_URL), IsSame.sameInstance(url));
    }

    /**
     * Test correctness of <code>getUrlByShortUrl</code> method when url does not exists.
     */
    @Test(expected = UrlNotFoundException.class)
    public void whenGetAbsentUrlThenException() throws UrlShortException {
        this.memoryStorage.getUrlByShortUrl(TestConstants.SHORT_URL);
    }

    /**
     * Test correctness of <code>registerUrl</code> method when url with the same short url already exists
     * in the same account.
     */
    @Test(expected = ShortUrlAlreadyExistsException.class)
    public void whenRegisterUrlWithSameShortUrlForSameAccountThenException() throws UrlShortException {
        final Account account = this.memoryStorage.createAccount(TestConstants.ID, TestConstants.PASSWORD);

        this.memoryStorage.registerUrl(account, TestConstants.SHORT_URL, TestConstants.LONG_URL,
                TestConstants.REDIRECT_TYPE_PERMANENT);
        this.memoryStorage.registerUrl(account, TestConstants.SHORT_URL, TestConstants.LONG_URL2,
                TestConstants.REDIRECT_TYPE_PERMANENT);
    }

    /**
     * Test correctness of <code>registerUrl</code> method when url with the same short url already exists
     * in another account.
     */
    @Test(expected = ShortUrlAlreadyExistsException.class)
    public void whenRegisterUrlWithSameShortUrlForDifferentAccountThenException() throws UrlShortException {
        final Account account = this.memoryStorage.createAccount(TestConstants.ID, TestConstants.PASSWORD);
        final Account account2 = this.memoryStorage.createAccount(TestConstants.ID2, TestConstants.PASSWORD2);

        this.memoryStorage.registerUrl(account, TestConstants.SHORT_URL, TestConstants.LONG_URL,
                TestConstants.REDIRECT_TYPE_PERMANENT);
        this.memoryStorage.registerUrl(account2, TestConstants.SHORT_URL, TestConstants.LONG_URL2,
                TestConstants.REDIRECT_TYPE_PERMANENT);
    }

    /**
     * Test correctness of <code>registerUrl</code> method when url with the same long url already exists
     * in the same account.
     */
    @Test(expected = LongUrlAlreadyExistsException.class)
    public void whenRegisterUrlWithSameLongUrlForSameAccountThenException() throws UrlShortException {
        final Account account = this.memoryStorage.createAccount(TestConstants.ID, TestConstants.PASSWORD);

        this.memoryStorage.registerUrl(account, TestConstants.SHORT_URL, TestConstants.LONG_URL,
                TestConstants.REDIRECT_TYPE_PERMANENT);
        this.memoryStorage.registerUrl(account, TestConstants.SHORT_URL2, TestConstants.LONG_URL,
                TestConstants.REDIRECT_TYPE_PERMANENT);
    }

    /**
     * Test correctness of <code>registerUrl</code> method when url with the same long url already exists
     * in another account.
     */
    @Test
    public void whenRegisterUrlWithSameLongUrlForDifferentAccountThenItRegisters() throws UrlShortException {
        final Account account = this.memoryStorage.createAccount(TestConstants.ID, TestConstants.PASSWORD);
        final Account account2 = this.memoryStorage.createAccount(TestConstants.ID2, TestConstants.PASSWORD2);

        this.memoryStorage.registerUrl(account, TestConstants.SHORT_URL, TestConstants.LONG_URL,
                TestConstants.REDIRECT_TYPE_PERMANENT);
        final Url url = this.memoryStorage.registerUrl(account2, TestConstants.SHORT_URL2, TestConstants.LONG_URL,
                TestConstants.REDIRECT_TYPE_PERMANENT);

        assertThat(this.memoryStorage.getUrlByShortUrl(TestConstants.SHORT_URL2), IsSame.sameInstance(url));
    }

    /**
     * Test correctness of <code>increaseHitCount</code> method.
     */
    @Test
    public void whenIncreaseHitCountThenItIncreases() throws UrlShortException {
        final Account account = this.memoryStorage.createAccount(TestConstants.ID, TestConstants.PASSWORD);
        final Url url = this.memoryStorage.registerUrl(account, TestConstants.SHORT_URL, TestConstants.LONG_URL,
                TestConstants.REDIRECT_TYPE_PERMANENT);
        this.memoryStorage.increaseUrlHitCount(url);

        assertThat(url.getHitCount(), is(1));
    }
}