package ru.lightstar.urlshort.storage;

import org.hamcrest.core.IsSame;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
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
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
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
        final Account account = this.memoryStorage.createAccount("test", "testPassword");

        assertThat(account.getId(), is("test"));
        assertThat(account.getPassword(), is("testPassword"));
        assertThat(account.getUrlMap(), notNullValue());
        assertThat(account.getUrlMap().keySet(), is(empty()));
        assertThat(this.memoryStorage.getAccountById("test"), IsSame.sameInstance(account));
    }

    /**
     * Test correctness of <code>getAccountById</code> method when account does not exists.
     */
    @Test(expected = AccountNotFoundException.class)
    public void whenGetAbsentAccountThenException() throws UrlShortException {
        this.memoryStorage.getAccountById("test");
    }

    /**
     * Test correctness of <code>createAccount</code> method when account with that id already exists.
     */
    @Test(expected = AccountAlreadyExistsException.class)
    public void whenCreateAccountWithSameIdThenException() throws UrlShortException {
        this.memoryStorage.createAccount("test", "testPassword");
        this.memoryStorage.createAccount("test", "testPassword");
    }

    /**
     * Test correctness of <code>registerUrl</code> method.
     */
    @Test
    public void whenRegisterUrlThenItRegisters() throws UrlShortException {
        final Account account = this.memoryStorage.createAccount("test", "testPassword");
        final Url url = this.memoryStorage.registerUrl(account, "shortUrl", "longUrl", 301);

        assertThat(url.getShortUrl(), is("shortUrl"));
        assertThat(url.getLongUrl(), is("longUrl"));
        assertThat(url.getRedirectType(), is(301));
        assertThat(url.getHitCount(), is(0));
        assertThat(account.getUrlMap().keySet(), hasSize(1));
        assertThat(account.getUrlMap().get("longUrl"), is(url));
        assertThat(this.memoryStorage.getUrlByShortUrl("shortUrl"), IsSame.sameInstance(url));
    }

    /**
     * Test correctness of <code>getUrlByShortUrl</code> method when url does not exists.
     */
    @Test(expected = UrlNotFoundException.class)
    public void whenGetAbsentUrlThenException() throws UrlShortException {
        this.memoryStorage.getUrlByShortUrl("shortUrl");
    }

    /**
     * Test correctness of <code>registerUrl</code> method when url with the same short url already exists
     * in the same account.
     */
    @Test(expected = ShortUrlAlreadyExistsException.class)
    public void whenRegisterUrlWithSameShortUrlForSameAccountThenException() throws UrlShortException {
        final Account account = this.memoryStorage.createAccount("test", "testPassword");

        this.memoryStorage.registerUrl(account, "shortUrl", "longUrl", 301);
        this.memoryStorage.registerUrl(account, "shortUrl", "longUrl2", 301);
    }

    /**
     * Test correctness of <code>registerUrl</code> method when url with the same short url already exists
     * in another account.
     */
    @Test(expected = ShortUrlAlreadyExistsException.class)
    public void whenRegisterUrlWithSameShortUrlForDifferentAccountThenException() throws UrlShortException {
        final Account account = this.memoryStorage.createAccount("test", "testPassword");
        final Account account2 = this.memoryStorage.createAccount("test2", "testPassword2");

        this.memoryStorage.registerUrl(account, "shortUrl", "longUrl", 301);
        this.memoryStorage.registerUrl(account2, "shortUrl", "longUrl2", 301);
    }

    /**
     * Test correctness of <code>registerUrl</code> method when url with the same long url already exists
     * in the same account.
     */
    @Test(expected = LongUrlAlreadyExistsException.class)
    public void whenRegisterUrlWithSameLongUrlForSameAccountThenException() throws UrlShortException {
        final Account account = this.memoryStorage.createAccount("test", "testPassword");

        this.memoryStorage.registerUrl(account, "shortUrl", "longUrl", 301);
        this.memoryStorage.registerUrl(account, "shortUrl2", "longUrl", 301);
    }

    /**
     * Test correctness of <code>registerUrl</code> method when url with the same long url already exists
     * in another account.
     */
    @Test
    public void whenRegisterUrlWithSameLongUrlForDifferentAccountThenItRegisters() throws UrlShortException {
        final Account account = this.memoryStorage.createAccount("test", "testPassword");
        final Account account2 = this.memoryStorage.createAccount("test2", "testPassword2");

        this.memoryStorage.registerUrl(account, "shortUrl", "longUrl", 301);
        final Url url = this.memoryStorage.registerUrl(account2, "shortUrl2", "longUrl", 301);

        assertThat(this.memoryStorage.getUrlByShortUrl("shortUrl2"), IsSame.sameInstance(url));
    }

    /**
     * Test correctness of <code>increaseHitCount</code> method.
     */
    @Test
    public void whenIncreaseHitCountThenItIncreases() throws UrlShortException {
        final Account account = this.memoryStorage.createAccount("test", "testPassword");
        final Url url = this.memoryStorage.registerUrl(account, "shortUrl", "longUrl", 301);
        this.memoryStorage.increaseUrlHitCount(url);

        assertThat(url.getHitCount(), is(1));
    }
}