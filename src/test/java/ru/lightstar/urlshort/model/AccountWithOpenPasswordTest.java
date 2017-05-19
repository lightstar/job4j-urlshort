package ru.lightstar.urlshort.model;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * <code>AccountWithOpenPassword</code> class tests.
 *
 * @author LightStar
 * @since 0.0.1
 */
public class AccountWithOpenPasswordTest {

    /**
     * Test correctness of created account with open password.
     */
    @Test
    public void whenCreateAccountWithOpenPasswordThenAllFieldsInitialized() {
        final Account account = new Account("test", "testPassword");
        final AccountWithOpenPassword accountWithOpenPassword = new AccountWithOpenPassword(account,
                "testOpenPassword");

        assertThat(accountWithOpenPassword.getOpenPassword(), is("testOpenPassword"));
        assertThat(accountWithOpenPassword.getAccount(), is(account));
    }
}