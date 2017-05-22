package ru.lightstar.urlshort.model;

import org.junit.Test;
import ru.lightstar.urlshort.TestConstants;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyString;
import static org.junit.Assert.*;

/**
 * <code>Url</code> class tests.
 *
 * @author LightStar
 * @since 0.0.1
 */
public class UrlTest {

    /**
     * <code>Url</code> object used in all tests.
     */
    private final Url url = new Url(TestConstants.SHORT_URL, TestConstants.LONG_URL,
            TestConstants.REDIRECT_TYPE_PERMANENT);

    /**
     * Test correctness of created url.
     */
    @Test
    public void whenCreateUrlThenAllFieldsInitialized() {
        assertThat(this.url.getShortUrl(), is(TestConstants.SHORT_URL));
        assertThat(this.url.getLongUrl(), is(TestConstants.LONG_URL));
        assertThat(this.url.getRedirectType(), is(TestConstants.REDIRECT_TYPE_PERMANENT));
        assertThat(this.url.getHitCount(), is(0));
    }

    /**
     * Test correctness of created empty url.
     */
    @Test
    public void whenCreateEmptyUrlThenAllFieldsInitialized() {
        final Url url = new Url();
        assertThat(url.getShortUrl(), isEmptyString());
        assertThat(url.getLongUrl(), isEmptyString());
        assertThat(url.getRedirectType(), is(0));
        assertThat(url.getHitCount(), is(0));
    }

    /**
     * Test correctness of <code>setShortUrl</code> and <code>getShortUrl</code> methods.
     */
    @Test
    public void whenSetShortUrlThenItChanges() {
        this.url.setShortUrl(TestConstants.SHORT_URL2);
        assertThat(this.url.getShortUrl(), is(TestConstants.SHORT_URL2));
    }

    /**
     * Test correctness of <code>setLongUrl</code> and <code>getLongUrl</code> methods.
     */
    @Test
    public void whenSetLongUrlThenItChanges() {
        this.url.setLongUrl(TestConstants.LONG_URL2);
        assertThat(this.url.getLongUrl(), is(TestConstants.LONG_URL2));
    }

    /**
     * Test correctness of <code>setRedirectType</code> and <code>getRedirectType</code> methods.
     */
    @Test
    public void whenSetRedirectTypeThenItChanges() {
        this.url.setRedirectType(TestConstants.REDIRECT_TYPE_TEMPORARY);
        assertThat(this.url.getRedirectType(), is(TestConstants.REDIRECT_TYPE_TEMPORARY));
    }

    /**
     * Test correctness of <code>setHitCount</code> and <code>getHitCount</code> methods.
     */
    @Test
    public void whenSetHitCountThenItChanges() {
        this.url.setHitCount(TestConstants.HIT_COUNT);
        assertThat(this.url.getHitCount(), is(TestConstants.HIT_COUNT));
    }

    /**
     * Test correctness of <code>setHitCount</code> and <code>getHitCount</code> methods.
     */
    @Test
    public void whenIncreaseHitCountThenItIncreases() {
        this.url.increaseHitCount();
        assertThat(this.url.getHitCount(), is(1));
    }


    /**
     * Test correctness of <code>equals</code> method with the same url.
     */
    @Test
    public void whenEqualsToSameThenTrue() {
        final Url url = new Url(TestConstants.SHORT_URL, TestConstants.LONG_URL,
                TestConstants.REDIRECT_TYPE_PERMANENT);
        assertTrue(this.url.equals(url));
    }

    /**
     * Test correctness of <code>equals</code> method with url with different short url string.
     */
    @Test
    public void whenEqualsToNotSameShortUrlThenFalse() {
        final Url url = new Url(TestConstants.SHORT_URL2, TestConstants.LONG_URL,
                TestConstants.REDIRECT_TYPE_PERMANENT);
        assertFalse(this.url.equals(url));
    }

    /**
     * Test correctness of <code>equals</code> method with url with different long url string.
     */
    @Test
    public void whenEqualsToNotSameLongUrlThenFalse() {
        final Url url = new Url(TestConstants.SHORT_URL, TestConstants.LONG_URL2,
                TestConstants.REDIRECT_TYPE_PERMANENT);
        assertFalse(this.url.equals(url));
    }

    /**
     * Test correctness of <code>equals</code> method with url with different redirect type.
     */
    @Test
    public void whenEqualsToNotSameRedirectTypeThenFalse() {
        final Url url = new Url(TestConstants.SHORT_URL, TestConstants.LONG_URL,
                TestConstants.REDIRECT_TYPE_TEMPORARY);
        assertFalse(this.url.equals(url));
    }

    /**
     * Test correctness of <code>equals</code> method with url with different hit count.
     */
    @Test
    public void whenEqualsToNotSameHitCountThenFalse() {
        final Url url = new Url(TestConstants.SHORT_URL, TestConstants.LONG_URL,
                TestConstants.REDIRECT_TYPE_PERMANENT);
        url.setHitCount(TestConstants.HIT_COUNT);
        assertFalse(this.url.equals(url));
    }

    /**
     * Test correctness of <code>equals</code> method with the null value.
     */
    @Test
    public void whenEqualsToNullThenFalse() {
        assertFalse(this.url.equals(null));
    }

    /**
     * Test correctness of <code>equals</code> method with the value of different class.
     */
    @Test
    public void whenEqualsToObjectThenFalse() {
        assertFalse(this.url.equals(new Object()));
    }

    /**
     * Test equality of hash codes of the same urls.
     */
    @Test
    public void whenCompareHashCodesOfTheSameUrlsThenTrue() {
        final Url url = new Url(TestConstants.SHORT_URL, TestConstants.LONG_URL,
                TestConstants.REDIRECT_TYPE_PERMANENT);
        assertTrue(this.url.hashCode() == url.hashCode());
    }
}