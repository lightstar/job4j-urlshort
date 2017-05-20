package ru.lightstar.urlshort.model;

import org.hamcrest.core.IsSame;
import org.junit.Test;

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
    private final Account account = new Account("test", "testPassword");

    /**
     * Test correctness of created account.
     */
    @Test
    public void whenCreateAccountThenAllFieldsInitialized() {
        assertThat(this.account.getId(), is("test"));
        assertThat(this.account.getPassword(), is("testPassword"));
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
        this.account.setId("test2");
        assertThat(this.account.getId(), is("test2"));
    }

    /**
     * Test correctness of <code>setId</code> and <code>getId</code> methods.
     */
    @Test
    public void whenSetPasswordThenItChanges() {
        this.account.setPassword("testPassword2");
        assertThat(this.account.getPassword(), is("testPassword2"));
    }

    /**
     * Test correctness of <code>setUrlMap</code> and <code>getUrlMap</code> methods.
     */
    @Test
    public void whenSetUrlMapThenItChanges() {
        final Url url = new Url("testUrl", "testLongUrl", 301);
        this.account.setUrlMap(Collections.singletonMap("testUrl", url));
        assertThat(this.account.getUrlMap().keySet(), hasSize(1));
        assertThat(this.account.getUrlMap().get("testUrl"), IsSame.sameInstance(url));
    }

    /**
     * Test correctness of <code>equals</code> method with the same account.
     */
    @Test
    public void whenEqualsToSameThenTrue() {
        final Account account = new Account("test", "testPassword");
        assertTrue(this.account.equals(account));
    }

    /**
     * Test correctness of <code>equals</code> method with not the same account.
     */
    @Test
    public void whenEqualsToNotSameThenFalse() {
        final Account account = new Account();
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
     * Test equality of hash codes of the same accounts.
     */
    @Test
    public void whenCompareHashCodesOfTheSameAccountsThenTrue() {
        final Account account = new Account("test", "testPassword");
        assertThat(this.account.hashCode(), is(account.hashCode()));
    }
}