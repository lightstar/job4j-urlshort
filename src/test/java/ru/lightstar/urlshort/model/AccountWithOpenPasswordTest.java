package ru.lightstar.urlshort.model;

import org.junit.Test;
import ru.lightstar.urlshort.TestConstants;

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
        final Account account = new Account(TestConstants.ID, TestConstants.PASSWORD);
        final AccountWithOpenPassword accountWithOpenPassword = new AccountWithOpenPassword(account,
                TestConstants.OPEN_PASSWORD);

        assertThat(accountWithOpenPassword.getOpenPassword(), is(TestConstants.OPEN_PASSWORD));
        assertThat(accountWithOpenPassword.getAccount(), is(account));
    }
}