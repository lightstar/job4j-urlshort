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
import ru.lightstar.urlshort.exception.AccountNotFoundException;
import ru.lightstar.urlshort.exception.LongUrlAlreadyExistsException;
import ru.lightstar.urlshort.model.Account;
import ru.lightstar.urlshort.model.Url;
import ru.lightstar.urlshort.security.AuthenticationProviderImpl;
import ru.lightstar.urlshort.security.SecurityConfiguration;
import ru.lightstar.urlshort.service.ConfigService;
import ru.lightstar.urlshort.service.UtilService;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * <code>RegisterUrlController</code> tests.
 *
 * @author LightStar
 * @since 0.0.1
 */
@RunWith(SpringRunner.class)
@WebMvcTest(RegisterUrlController.class)
@Import({SecurityConfiguration.class,AuthenticationProviderImpl.class})
public class RegisterUrlControllerTest extends Mockito {

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
        when(this.utilService.getAuthName()).thenReturn(TestConstants.ID);
        when(this.configService.registerUrl(account, TestConstants.LONG_URL, TestConstants.REDIRECT_TYPE_PERMANENT))
                .thenThrow(new LongUrlAlreadyExistsException("Long url already exists"));

        this.mvc.perform(post("/register")
                    .with(httpBasic(TestConstants.ID, TestConstants.OPEN_PASSWORD))
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(String.format("{\"url\":\"%s\",\"redirectType\":%d}", TestConstants.LONG_URL,
                            TestConstants.REDIRECT_TYPE_PERMANENT)))
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
        when(this.utilService.getAuthName()).thenReturn(TestConstants.ID);
        when(this.configService.getAccountById(TestConstants.ID))
                .thenReturn(account)
                .thenThrow(new AccountNotFoundException("Account not found"));

        this.mvc.perform(post("/register")
                .with(httpBasic(TestConstants.ID, TestConstants.OPEN_PASSWORD))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("{\"url\":\"%s\",\"redirectType\":%d}", TestConstants.LONG_URL,
                        TestConstants.REDIRECT_TYPE_PERMANENT)))
                .andExpect(status().isNotFound())
                .andExpect(content().json(
                        "{\"error\":\"Account not found\"}"));
    }

    /**
     * Test correctness of register url request when no auth is provided.
     */
    @Test
    public void whenRegisterUrlWithNoAuthThenAuthError() throws Exception {
        this.mvc.perform(post("/register")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(String.format("{\"url\":\"%s\",\"redirectType\":%d}", TestConstants.LONG_URL,
                                TestConstants.REDIRECT_TYPE_PERMANENT)))
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

        this.mvc.perform(post("/register")
                .with(httpBasic(TestConstants.ID, TestConstants.OPEN_PASSWORD))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("{\"url\":\"%s\",\"redirectType\":%d}", TestConstants.LONG_URL,
                        TestConstants.REDIRECT_TYPE_PERMANENT)))
                .andExpect(status().isUnauthorized());
    }

    /**
     * Test correctness of register url request when authenticated account not found.
     */
    @Test
    public void whenRegisterUrlWithWrongPasswordThenAuthError() throws Exception {
        this.setUpMockAuthorization();

        this.mvc.perform(post("/register")
                .with(httpBasic(TestConstants.ID, TestConstants.OPEN_PASSWORD2))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("{\"url\":\"%s\",\"redirectType\":%d}", TestConstants.LONG_URL,
                        TestConstants.REDIRECT_TYPE_PERMANENT)))
                .andExpect(status().isUnauthorized());
    }

    /**
     * Test correctness of register url request when long url is empty.
     */
    @Test
    public void whenRegisterUrlWithEmptyUrlThenError() throws Exception {
        this.setUpMockAuthorization();

        this.mvc.perform(post("/register")
                    .with(httpBasic(TestConstants.ID, TestConstants.OPEN_PASSWORD))
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"url\":\"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"error\":\"Invalid parameters\"}"));
    }

    /**
     * Test correctness of register url request when redirect type is wrong.
     */
    @Test
    public void whenRegisterUrlWithWrongRedirectTypeThenError() throws Exception {
        this.setUpMockAuthorization();

        this.mvc.perform(post("/register")
                    .with(httpBasic(TestConstants.ID, TestConstants.OPEN_PASSWORD))
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(String.format("{\"url\":\"%s\",\"redirectType\":%d}", TestConstants.LONG_URL,
                        TestConstants.REDIRECT_TYPE_WRONG)))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"error\":\"Invalid parameters\"}"));
    }

    /**
     * Test correctness of register url request when body is empty.
     */
    @Test
    public void whenRegisterUrlWithEmptyBodyThenError() throws Exception {
        this.setUpMockAuthorization();

        this.mvc.perform(post("/register")
                    .with(httpBasic(TestConstants.ID, TestConstants.OPEN_PASSWORD))
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"error\":\"Invalid parameters\"}"));
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
        when(this.utilService.getAuthName()).thenReturn(TestConstants.ID);
        final Url url = new Url(TestConstants.SHORT_URL, TestConstants.LONG_URL, redirectType);
        when(this.configService.registerUrl(account, TestConstants.LONG_URL, redirectType))
                .thenReturn(url);

        this.mvc.perform(post("/register")
                .with(httpBasic(TestConstants.ID, TestConstants.OPEN_PASSWORD))
                .with(request -> {
                    request.setLocalPort(port);
                    request.setScheme(schema);
                    return request;
                })
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(sentRedirectType != 0 ?
                        String.format("{\"url\":\"%s\",\"redirectType\":%d}", TestConstants.LONG_URL,
                                sentRedirectType) :
                        String.format("{\"url\":\"%s\"}", TestConstants.LONG_URL)))
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
     * Configure mock services to return correct results for mock authorization to work.
     *
     * @return authorized account.
     * @throws AccountNotFoundException shouldn't be thrown.
     */
    private Account setUpMockAuthorization() throws AccountNotFoundException {
        final Account account = new Account(TestConstants.ID, TestConstants.PASSWORD);
        when(this.utilService.getHashedPassword(TestConstants.OPEN_PASSWORD)).thenReturn(TestConstants.PASSWORD);
        when(this.configService.getAccountById(TestConstants.ID)).thenReturn(account);
        return account;
    }
}