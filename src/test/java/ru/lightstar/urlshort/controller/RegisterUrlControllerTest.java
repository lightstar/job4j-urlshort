package ru.lightstar.urlshort.controller;

import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import ru.lightstar.urlshort.TestConstants;
import ru.lightstar.urlshort.exception.AccountNotFoundException;
import ru.lightstar.urlshort.exception.LongUrlAlreadyExistsException;
import ru.lightstar.urlshort.model.Account;
import ru.lightstar.urlshort.model.Url;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * <code>RegisterUrlController</code> tests.
 *
 * @author LightStar
 * @since 0.0.1
 */
public class RegisterUrlControllerTest extends ControllerTest {

    /**
     * Test correctness of register url request.
     */
    @Test
    public void whenRegisterUrlThenItRegisters() throws Exception {
        this.testRegisterUrl("http", TestConstants.HTTP_STANDARD_PORT,
                TestConstants.REDIRECT_TYPE_PERMANENT, TestConstants.REDIRECT_TYPE_PERMANENT,
                "http://localhost/");
    }

    /**
     * Test correctness of register url request when port is non-standard.
     */
    @Test
    public void whenRegisterUrlWithNonStandardPortThenItRegisters() throws Exception {
        this.testRegisterUrl("http", TestConstants.HTTP_NONSTANDARD_PORT,
                TestConstants.REDIRECT_TYPE_PERMANENT, TestConstants.REDIRECT_TYPE_PERMANENT,
                String.format("http://localhost:%d/", TestConstants.HTTP_NONSTANDARD_PORT));
    }

    /**
     * Test correctness of register url request when schema is https.
     */
    @Test
    public void whenRegisterUrlWithHttpsSchemaThenItRegisters() throws Exception {
        this.testRegisterUrl("https", TestConstants.HTTPS_STANDARD_PORT,
                TestConstants.REDIRECT_TYPE_PERMANENT, TestConstants.REDIRECT_TYPE_PERMANENT,
                "https://localhost/");
    }

    /**
     * Test correctness of register url request when schema is https and port is non-standard.
     */
    @Test
    public void whenRegisterUrlWithHttpsSchemaAndNonStandardPortThenItRegisters() throws Exception {
        this.testRegisterUrl("https", TestConstants.HTTPS_NONSTANDARD_PORT,
                TestConstants.REDIRECT_TYPE_PERMANENT, TestConstants.REDIRECT_TYPE_PERMANENT,
                String.format("https://localhost:%d/", TestConstants.HTTPS_NONSTANDARD_PORT));
    }

    /**
     * Test correctness of register url request when redirect type is temporary.
     */
    @Test
    public void whenRegisterUrlWithDifferentRedirectTypeThenItRegisters() throws Exception {
        this.testRegisterUrl("http", TestConstants.HTTP_STANDARD_PORT,
                TestConstants.REDIRECT_TYPE_TEMPORARY, TestConstants.REDIRECT_TYPE_TEMPORARY,
                "http://localhost/");
    }

    /**
     * Test correctness of register url request when redirect type not specified.
     */
    @Test
    public void whenRegisterUrlWithNoRedirectTypeThenItRegisters() throws Exception {
        this.testRegisterUrl("http", TestConstants.HTTP_STANDARD_PORT, 0,
                TestConstants.REDIRECT_TYPE_TEMPORARY, "http://localhost/");
    }

    /**
     * Test correctness of register url request when long url already exists for account.
     */
    @Test
    public void whenRegisterUrlAndLongUrlAlreadyExistsThenError() throws Exception {
        final Account account = this.setUpMockAuthorization();
        when(this.configService.registerUrl(account, TestConstants.LONG_URL, TestConstants.REDIRECT_TYPE_PERMANENT))
                .thenThrow(new LongUrlAlreadyExistsException("Long url already exists"));

        this.postRegisterUrl(TestConstants.ID, TestConstants.OPEN_PASSWORD,
                             TestConstants.LONG_URL, TestConstants.REDIRECT_TYPE_PERMANENT)
                .andExpect(status().isConflict())
                .andExpect(content().json(
                        "{\"error\":\"This url already registered in your account\"}"));
    }

    /**
     * Test correctness of register url request when account not found on second call to <code>getAccountById</code>.
     */
    @Test
    public void whenRegisterUrlAndAccountNotFoundOnSecondCallThenError() throws Exception {
        final Account account = this.setUpMockAuthorization();
        when(this.configService.getAccountById(TestConstants.ID))
                .thenReturn(account)
                .thenThrow(new AccountNotFoundException("Account not found"));

        this.postRegisterUrl(TestConstants.ID, TestConstants.OPEN_PASSWORD,
                             TestConstants.LONG_URL, TestConstants.REDIRECT_TYPE_PERMANENT)
                .andExpect(status().isNotFound())
                .andExpect(content().json(
                        "{\"error\":\"Account not found\"}"));
    }

    /**
     * Test correctness of register url request when no auth is provided.
     */
    @Test
    public void whenRegisterUrlWithNoAuthThenAuthError() throws Exception {
        this.postRegisterUrl(null, null, TestConstants.LONG_URL, TestConstants.REDIRECT_TYPE_PERMANENT)
                .andExpect(status().isUnauthorized());
    }

    /**
     * Test correctness of register url request when authenticated account not found.
     */
    @Test
    public void whenRegisterUrlWithAccountNotFoundThenAuthError() throws Exception {
        this.setUpMockAuthorization();
        when(this.configService.getAccountById(TestConstants.ID))
                .thenThrow(new AccountNotFoundException("Account not found"));

        this.postRegisterUrl(TestConstants.ID, TestConstants.OPEN_PASSWORD,
                             TestConstants.LONG_URL, TestConstants.REDIRECT_TYPE_PERMANENT)
                .andExpect(status().isUnauthorized());
    }

    /**
     * Test correctness of register url request when wrong password is supplied.
     */
    @Test
    public void whenRegisterUrlWithWrongPasswordThenAuthError() throws Exception {
        this.setUpMockAuthorization();
        this.postRegisterUrl(TestConstants.ID, TestConstants.OPEN_PASSWORD2,
                             TestConstants.LONG_URL, TestConstants.REDIRECT_TYPE_PERMANENT)
                .andExpect(status().isUnauthorized());
    }

    /**
     * Test correctness of register url request when long url is empty.
     */
    @Test
    public void whenRegisterUrlWithEmptyUrlThenError() throws Exception {
        this.setUpMockAuthorization();
        this.postRegisterUrl(TestConstants.ID, TestConstants.OPEN_PASSWORD, "", 0)
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"error\":\"Invalid parameters\"}"));
    }

    /**
     * Test correctness of register url request when redirect type is wrong.
     */
    @Test
    public void whenRegisterUrlWithWrongRedirectTypeThenError() throws Exception {
        this.setUpMockAuthorization();
        this.postRegisterUrl(TestConstants.ID, TestConstants.OPEN_PASSWORD,
                             TestConstants.LONG_URL, TestConstants.REDIRECT_TYPE_WRONG)
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"error\":\"Invalid parameters\"}"));
    }

    /**
     * Test correctness of register url request when body is empty.
     */
    @Test
    public void whenRegisterUrlWithEmptyBodyThenError() throws Exception {
        this.setUpMockAuthorization();
        this.postRegisterUrl(TestConstants.ID, TestConstants.OPEN_PASSWORD, null, 0)
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"error\":\"Invalid parameters\"}"));
    }

    /**
     * Test correctness of register url request when runtime exception is thrown.
     */
    @Test
    public void whenRegisterUrlAndRuntimeExceptionThenError() throws Exception {
        final Account account = this.setUpMockAuthorization();
        when(this.configService.registerUrl(account, TestConstants.LONG_URL, TestConstants.REDIRECT_TYPE_PERMANENT))
                .thenThrow(new RuntimeException());

        this.postRegisterUrl(TestConstants.ID, TestConstants.OPEN_PASSWORD,
                TestConstants.LONG_URL, TestConstants.REDIRECT_TYPE_PERMANENT)
                .andExpect(status().isInternalServerError())
                .andExpect(content().json("{\"error\":\"Unknown error\"}"));
    }

    /**
     * Generic test of register url request.
     *
     * @param schema used http schema.
     * @param port used http port.
     * @param sentRedirectType redirect type that is sent in the request.
     * @param redirectType redirect type that must be in the registered url.
     * @param urlBase base url that must be in the beginning of the returned short url.
     * @throws Exception shouldn't be thrown.
     */
    private void testRegisterUrl(final String schema, final int port, final int sentRedirectType,
                                 final int redirectType, final String urlBase) throws Exception {
        final Account account = this.setUpMockAuthorization();
        final Url url = new Url(TestConstants.SHORT_URL, TestConstants.LONG_URL, redirectType);
        when(this.configService.registerUrl(account, TestConstants.LONG_URL, redirectType))
                .thenReturn(url);

        this.postRegisterUrl(TestConstants.ID, TestConstants.OPEN_PASSWORD, schema, port,
                             TestConstants.LONG_URL, sentRedirectType)
                .andExpect(status().isCreated())
                .andExpect(content().json(String.format(
                        "{\"shortUrl\":\"%s%s\"}",
                        urlBase, TestConstants.SHORT_URL)));

        verify(this.utilService, times(1)).getHashedPassword(TestConstants.OPEN_PASSWORD);
        verify(this.utilService, times(1)).getAuthName();
        verifyNoMoreInteractions(this.utilService);

        verify(this.configService, times(2)).getAccountById(TestConstants.ID);
        verify(this.configService, times(1)).registerUrl(account, TestConstants.LONG_URL,
                redirectType);
        verifyNoMoreInteractions(this.configService);
    }

    /**
     * Make a mock post request to register url.
     *
     * @param login authorization header's login.
     * @param password authorization header's password.
     * @param url registered url.
     * @param redirectType registered url's redirect type.
     * @return mock request result.
     * @throws Exception shouldn't be thrown.
     */
    private ResultActions postRegisterUrl(final String login, final String password, final String url,
                                          final int redirectType) throws Exception {
        return this.postRegisterUrl(login, password, null, 0, url, redirectType);
    }

    /**
     * Make a mock post request to register url.
     *
     * @param login authorization header's login.
     * @param password authorization header's password.
     * @param schema request's schema.
     * @param port request's port.
     * @param url registered url.
     * @param redirectType registered url's redirect type.
     * @return mock request result.
     * @throws Exception shouldn't be thrown.
     */
    private ResultActions postRegisterUrl(final String login, final String password, final String schema, final int port,
                                          final String url, final int redirectType) throws Exception {
        MockHttpServletRequestBuilder builder = this.postJson("/register");

        if (login != null && password != null) {
            builder = builder.with(httpBasic(login, password));
        }

        if (schema != null && port != 0) {
            builder = builder.with(request -> {
                request.setScheme(schema);
                request.setLocalPort(port);
                return request;
            });
        }

        if (url != null) {
            if (redirectType == 0) {
                builder = builder.content(String.format("{\"url\":\"%s\"}", url));
            } else {
                builder = builder.content(String.format("{\"url\":\"%s\",\"redirectType\":%d}", url, redirectType));
            }
        }

        return this.mvc.perform(builder);
    }
}