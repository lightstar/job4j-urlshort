package ru.lightstar.urlshort.model;

import org.hamcrest.core.IsSame;
import org.junit.Test;
import ru.lightstar.urlshort.TestConstants;

import java.util.Collections;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * <code>Account</code> class tests.
 *
 * @author LightStar
 * @since 0.0.1
 */
public class AccountTest {

    /**
     * <code>Account</code> object used in all tests.
     */
    private final Account account = new Account(TestConstants.ID, TestConstants.PASSWORD);

    /**
     * Test correctness of created account.
     */
    @Test
    public void whenCreateAccountThenAllFieldsInitialized() {
        assertThat(this.account.getId(), is(TestConstants.ID));
        assertThat(this.account.getPassword(), is(TestConstants.PASSWORD));
        assertThat(this.account.getUrlMap(), notNullValue());
        assertThat(this.account.getUrlMap().keySet(), is(empty()));
    }

    /**
     * Test correctness of created empty account.
     */
    @Test
    public void whenCreateEmptyAccountThenAllFieldsInitialized() {
        final Account account = new Account();
        assertThat(account.getId(), isEmptyString());
        assertThat(account.getPassword(), isEmptyString());
        assertThat(account.getUrlMap(), notNullValue());
        assertThat(account.getUrlMap().keySet(), is(empty()));
    }

    /**
     * Test correctness of <code>setId</code> and <code>getId</code> methods.
     */
    @Test
    public void whenSetIdThenItChanges() {
        this.account.setId(TestConstants.ID2);
        assertThat(this.account.getId(), is(TestConstants.ID2));
    }

    /**
     * Test correctness of <code>setId</code> and <code>getId</code> methods.
     */
    @Test
    public void whenSetPasswordThenItChanges() {
        this.account.setPassword(TestConstants.PASSWORD2);
        assertThat(this.account.getPassword(), is(TestConstants.PASSWORD2));
    }

    /**
     * Test correctness of <code>setUrlMap</code> and <code>getUrlMap</code> methods.
     */
    @Test
    public void whenSetUrlMapThenItChanges() {
        final Url url = new Url(TestConstants.SHORT_URL, TestConstants.LONG_URL, TestConstants.REDIRECT_TYPE_PERMANENT);
        this.account.setUrlMap(Collections.singletonMap(TestConstants.LONG_URL, url));
        assertThat(this.account.getUrlMap().keySet(), hasSize(1));
        assertThat(this.account.getUrlMap().get(TestConstants.LONG_URL), IsSame.sameInstance(url));
    }

    /**
     * Test correctness of <code>equals</code> method with the same account.
     */
    @Test
    public void whenEqualsToSameThenTrue() {
        final Account account = new Account(TestConstants.ID, TestConstants.PASSWORD);
        assertTrue(this.account.equals(account));
    }

    /**
     * Test correctness of <code>equals</code> method with account with different id.
     */
    @Test
    public void whenEqualsToNotSameIdThenFalse() {
        final Account account = new Account(TestConstants.ID2, TestConstants.PASSWORD);
        assertFalse(this.account.equals(account));
    }

    /**
     * Test correctness of <code>equals</code> method with account with different password.
     */
    @Test
    public void whenEqualsToNotSamePasswordThenFalse() {
        final Account account = new Account(TestConstants.ID, TestConstants.PASSWORD2);
        assertFalse(this.account.equals(account));
    }

    /**
     * Test correctness of <code>equals</code> method with account with different url map.
     */
    @Test
    public void whenEqualsToNotSameUrlMapThenFalse() {
        final Account account = new Account(TestConstants.ID, TestConstants.PASSWORD);
        account.getUrlMap().put(TestConstants.LONG_URL, new Url(TestConstants.SHORT_URL, TestConstants.LONG_URL,
                TestConstants.REDIRECT_TYPE_PERMANENT));
        assertFalse(this.account.equals(account));
    }

    /**
     * Test correctness of <code>equals</code> method with the null value.
     */
    @Test
    public void whenEqualsToNullThenFalse() {
        assertFalse(this.account.equals(null));
    }

    /**
     * Test correctness of <code>equals</code> method with the value of different class.
     */
    @Test
    public void whenEqualsToObjectThenFalse() {
        assertFalse(this.account.equals(new Object()));
    }

    /**
     * Test equality of hash codes of the same accounts.
     */
    @Test
    public void whenCompareHashCodesOfTheSameAccountsThenTrue() {
        final Account account = new Account(TestConstants.ID, TestConstants.PASSWORD);
        assertTrue(this.account.hashCode() == account.hashCode());
    }
}