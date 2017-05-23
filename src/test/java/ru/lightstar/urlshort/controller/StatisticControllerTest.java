package ru.lightstar.urlshort.controller;

import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import ru.lightstar.urlshort.TestConstants;
import ru.lightstar.urlshort.exception.AccountNotFoundException;
import ru.lightstar.urlshort.model.Account;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * <code>StatisticController</code> tests.
 *
 * @author LightStar
 * @since 0.0.1
 */
public class StatisticControllerTest extends ControllerTest {

    /**
     * Test correctness of statistic request.
     */
    @Test
    public void whenGetStatisticThenResult() throws Exception {
        final Account account = this.setUpMockAuthorization();
        final Map<String, Integer> statisticMap = new HashMap<>();
        statisticMap.put(TestConstants.LONG_URL, TestConstants.HIT_COUNT);
        statisticMap.put(TestConstants.LONG_URL2, TestConstants.HIT_COUNT2);
        when(this.configService.getStatistic(account)).thenReturn(statisticMap);

        this.getStatistic(TestConstants.ID, TestConstants.OPEN_PASSWORD, TestConstants.ID)
                .andExpect(status().isOk())
                .andExpect(content().json(String.format(
                        "{\"%s\":%d,\"%s\":%d}",
                        TestConstants.LONG_URL, TestConstants.HIT_COUNT,
                        TestConstants.LONG_URL2, TestConstants.HIT_COUNT2)));
    }

    /**
     * Test correctness of statistic request when requested id differs from authenticated user.
     */
    @Test
    public void whenGetStatisticWithDifferentIdThenError() throws Exception {
        this.setUpMockAuthorization();
        this.getStatistic(TestConstants.ID, TestConstants.OPEN_PASSWORD, TestConstants.ID2)
                .andExpect(status().isForbidden())
                .andExpect(content().json("{\"error\":\"Access denied\"}"));
    }

    /**
     * Test correctness of statistic request when requested id differs from authenticated user
     * but authenticated user is admin.
     */
    @Test
    public void whenGetStatisticWithDifferentIdAndUserIsAdminThenResult() throws Exception {
        this.setUpMockAuthorization();
        when(this.utilService.getAuthName()).thenReturn(this.adminLogin);
        when(this.utilService.getAuthRole()).thenReturn(TestConstants.ROLE_ADMIN);

        final Account account = new Account(TestConstants.ID2, TestConstants.PASSWORD2);
        when(this.configService.getStatistic(account)).thenReturn(Collections.emptyMap());

        this.getStatistic(this.adminLogin, this.adminPassword, TestConstants.ID2)
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));
    }

    /**
     * Test correctness of statistic request when try request it by admin with wrong password.
     */
    @Test
    public void whenGetStatisticByAdminWithWrongPasswordThenError() throws Exception {
        this.setUpMockAuthorization();
        this.getStatistic(this.adminLogin, TestConstants.PASSWORD, TestConstants.ID2)
                .andExpect(status().isUnauthorized());
    }

    /**
     * Test correctness of statistic request when no auth is provided.
     */
    @Test
    public void whenGetStatisticWithNoAuthThenAuthError() throws Exception {
        this.getStatistic(null, null, TestConstants.ID)
                .andExpect(status().isUnauthorized());
    }

    /**
     * Test correctness of statistic request when authenticated account not found.
     */
    @Test
    public void whenGetStatisticWithAccountNotFoundThenAuthError() throws Exception {
        this.setUpMockAuthorization();
        when(this.configService.getAccountById(TestConstants.ID))
                .thenThrow(new AccountNotFoundException("Account not found"));

        this.getStatistic(TestConstants.ID, TestConstants.OPEN_PASSWORD, TestConstants.ID)
                .andExpect(status().isUnauthorized());
    }

    /**
     * Test correctness of statistic request when wrong password is supplied.
     */
    @Test
    public void whenGetStatisticWithWrongPasswordThenAuthError() throws Exception {
        this.setUpMockAuthorization();
        this.getStatistic(TestConstants.ID, TestConstants.OPEN_PASSWORD2, TestConstants.ID)
                .andExpect(status().isUnauthorized());
    }

    /**
     * Test correctness of statistic request when runtime exception is thrown.
     */
    @Test
    public void whenGetStatisticAndRuntimeExceptionThenError() throws Exception {
        final Account account = this.setUpMockAuthorization();
        when(this.configService.getStatistic(account)).thenThrow(new RuntimeException());

        this.getStatistic(TestConstants.ID, TestConstants.OPEN_PASSWORD, TestConstants.ID)
                .andExpect(status().isInternalServerError())
                .andExpect(content().json("{\"error\":\"Unknown error\"}"));
    }

    /**
     * Make a mock request to get statistic.
     *
     * @param login authorization header's login.
     * @param password authorization header's password.
     * @param id account's id for getting statistic.
     * @return mock request result.
     * @throws Exception shouldn't be thrown.
     */
    private ResultActions getStatistic(final String login, final String password, final String id)
            throws Exception {
        MockHttpServletRequestBuilder builder = this.getJson(String.format("/statistic/%s", id));

        if (login != null && password != null) {
            builder = builder.with(httpBasic(login, password));
        }

        return this.mvc.perform(builder);
    }
}