package ru.lightstar.urlshort.controller;

import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;
import ru.lightstar.urlshort.TestConstants;
import ru.lightstar.urlshort.exception.AccountAlreadyExistsException;
import ru.lightstar.urlshort.model.Account;
import ru.lightstar.urlshort.model.AccountWithOpenPassword;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * <code>CreateAccountController</code> tests.
 *
 * @author LightStar
 * @since 0.0.1
 */
public class CreateAccountControllerTest extends ControllerTest {

    /**
     * Test correctness of create account request.
     */
    @Test
    public void whenCreateAccountThenItCreates() throws Exception {
        final Account account = new Account(TestConstants.ID, TestConstants.PASSWORD);
        final AccountWithOpenPassword accountWithOpenPassword = new AccountWithOpenPassword(account,
                TestConstants.OPEN_PASSWORD);
        when(this.configService.createAccount(TestConstants.ID)).thenReturn(accountWithOpenPassword);

        this.postCreateAccount(TestConstants.ID)
                .andExpect(status().isCreated())
                .andExpect(content().json(String.format(
                        "{\"success\":true, \"description\":\"Your account is opened\",\"password\":\"%s\"}",
                        TestConstants.OPEN_PASSWORD)));

        verify(this.configService, times(1)).createAccount(TestConstants.ID);
        verifyNoMoreInteractions(this.configService);
    }

    /**
     * Test correctness of create account request when account already exists.
     */
    @Test
    public void whenCreateAccountAndAccountAlreadyExistsThenNoSuccess() throws Exception {
        when(this.configService.createAccount(TestConstants.ID)).thenThrow(
                new AccountAlreadyExistsException("Account already exists"));

        this.postCreateAccount(TestConstants.ID)
                .andExpect(status().isConflict())
                .andExpect(content().json(
                        "{\"success\":false, \"description\":\"Account with that ID already exists\"}"));

        verify(this.configService, times(1)).createAccount(TestConstants.ID);
        verifyNoMoreInteractions(this.configService);
    }

    /**
     * Test correctness of create account request when account id is empty.
     */
    @Test
    public void whenCreateAccountAndAccountIdEmptyThenError() throws Exception {
        this.postCreateAccount("")
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"error\":\"Invalid parameters\"}"));
    }

    /**
     * Test correctness of create account request when there are no request body.
     */
    @Test
    public void whenCreateAccountAndRequestBodyEmptyThenError() throws Exception {
        this.mvc.perform(this.postJson("/account"))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"error\":\"Invalid parameters\"}"));
    }

    /**
     * Test correctness of create account request when runtime exception is thrown.
     */
    @Test
    public void whenCreateAccountAndRuntimeExceptionThenError() throws Exception {
        when(this.configService.createAccount(TestConstants.ID)).thenThrow(new RuntimeException());

        this.postCreateAccount(TestConstants.ID)
                .andExpect(status().isInternalServerError())
                .andExpect(content().json("{\"error\":\"Unknown error\"}"));
    }

    /**
     * Make a mock post request to create account.
     *
     * @param accountId account's id to create.
     * @return mock request result.
     */
    private ResultActions postCreateAccount(final String accountId) throws Exception {
        return this.mvc.perform(this.postJson("/account")
                .content(String.format("{\"AccountId\":\"%s\"}", accountId)));
    }
}