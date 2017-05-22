package ru.lightstar.urlshort.controller;

import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import ru.lightstar.urlshort.TestConstants;
import ru.lightstar.urlshort.exception.AccountNotFoundException;
import ru.lightstar.urlshort.model.Account;
import ru.lightstar.urlshort.service.ConfigService;
import ru.lightstar.urlshort.service.RedirectService;
import ru.lightstar.urlshort.service.UtilService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * <code>ControllerTest</code> class.
 *
 * @author LightStar
 * @since 0.0.1
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public abstract class ControllerTest extends Mockito {

    /**
     * Auto-created mocked Spring MVC infrastructure.
     */
    @Autowired
    protected MockMvc mvc;

    /**
     * Mocked configuration service bean.
     */
    @MockBean
    protected ConfigService configService;

    /**
     * Mocked redirect service bean.
     */
    @MockBean
    protected RedirectService redirectService;

    /**
     * Mocked utility service bean.
     */
    @MockBean
    protected UtilService utilService;

    /**
     * Make a mock post request with request and response types set as JSON.
     *
     * @param path request path.
     * @return request builder.
     */
    protected MockHttpServletRequestBuilder postJson(final String path) {
        return post(path)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
    }

    /**
     * Make a mock get request with response type set as JSON.
     *
     * @param path request path.
     * @return request builder.
     */
    protected MockHttpServletRequestBuilder getJson(final String path) {
        return get(path)
                .accept(MediaType.APPLICATION_JSON);
    }

    /**
     * Configure mock services to return correct results for mock authorization to work.
     *
     * @return authorized account.
     * @throws AccountNotFoundException shouldn't be thrown.
     */
    protected Account setUpMockAuthorization() throws AccountNotFoundException {
        when(this.utilService.getHashedPassword(TestConstants.OPEN_PASSWORD)).thenReturn(TestConstants.PASSWORD);

        final Account account = new Account(TestConstants.ID, TestConstants.PASSWORD);
        when(this.configService.getAccountById(TestConstants.ID)).thenReturn(account);

        when(this.utilService.getAuthName()).thenReturn(TestConstants.ID);
        when(this.utilService.getAuthRole()).thenReturn(TestConstants.ROLE_USER);

        return account;
    }
}