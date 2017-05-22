package ru.lightstar.urlshort.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.lightstar.urlshort.TestConstants;
import ru.lightstar.urlshort.exception.AccountAlreadyExistsException;
import ru.lightstar.urlshort.model.Account;
import ru.lightstar.urlshort.model.AccountWithOpenPassword;
import ru.lightstar.urlshort.security.AuthenticationProviderImpl;
import ru.lightstar.urlshort.security.SecurityConfiguration;
import ru.lightstar.urlshort.service.ConfigService;
import ru.lightstar.urlshort.service.UtilService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * <code>CreateAccountController</code> tests.
 *
 * @author LightStar
 * @since 0.0.1
 */
@RunWith(SpringRunner.class)
@WebMvcTest(CreateAccountController.class)
@Import({SecurityConfiguration.class,AuthenticationProviderImpl.class})
public class CreateAccountControllerTest extends Mockito {

    /**
     * Auto-created mocked Spring MVC infrastructure.
     */
    @Autowired
    private MockMvc mvc;

    /**
     * Mocked configuration service bean.
     */
    @MockBean
    private ConfigService configService;

    /**
     * Mocked utility service bean.
     */
    @MockBean
    private UtilService utilService;

    /**
     * Test correctness of create account request.
     */
    @Test
    public void whenCreateAccountThenItCreates() throws Exception {
        final Account account = new Account(TestConstants.ID, TestConstants.PASSWORD);
        final AccountWithOpenPassword accountWithOpenPassword = new AccountWithOpenPassword(account,
                TestConstants.OPEN_PASSWORD);
        when(this.configService.createAccount(TestConstants.ID)).thenReturn(accountWithOpenPassword);

        this.mvc.perform(post("/account")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(String.format("{\"AccountId\":\"%s\"}", TestConstants.ID)))
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

        this.mvc.perform(post("/account")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(String.format("{\"AccountId\":\"%s\"}", TestConstants.ID)))
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
        this.mvc.perform(post("/account")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"AccountId\":\"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"error\":\"Invalid parameters\"}"));
    }

    /**
     * Test correctness of create account request when there are no request body.
     */
    @Test
    public void whenCreateAccountAndRequestBodyEmptyThenError() throws Exception {
        this.mvc.perform(post("/account")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"error\":\"Invalid parameters\"}"));
    }
}