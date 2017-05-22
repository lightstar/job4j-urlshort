package ru.lightstar.urlshort.controller;

import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;
import ru.lightstar.urlshort.TestConstants;
import ru.lightstar.urlshort.exception.UrlNotFoundException;
import ru.lightstar.urlshort.model.Url;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * <code>RedirectControllerTest</code> class.
 *
 * @author LightStar
 * @since 0.0.1
 */
public class RedirectControllerTest extends ControllerTest {

    /**
     * Test correctness of redirect request when redirect type is permanent.
     */
    @Test
    public void whenRedirectWithPermanentTypeThenItRedirects() throws Exception {
        final Url url = new Url(TestConstants.SHORT_URL, TestConstants.LONG_URL,
                TestConstants.REDIRECT_TYPE_PERMANENT);
        when(this.redirectService.getUrl(TestConstants.SHORT_URL)).thenReturn(url);

        this.redirect()
                .andExpect(status().isMovedPermanently())
                .andExpect(redirectedUrl(TestConstants.LONG_URL));
    }

    /**
     * Test correctness of redirect request when redirect type is temporary.
     */
    @Test
    public void whenRedirectWithTemporaryTypeThenItRedirects() throws Exception {
        final Url url = new Url(TestConstants.SHORT_URL, TestConstants.LONG_URL,
                TestConstants.REDIRECT_TYPE_TEMPORARY);
        when(this.redirectService.getUrl(TestConstants.SHORT_URL)).thenReturn(url);

        this.redirect()
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(TestConstants.LONG_URL));
    }

    /**
     * Test correctness of redirect request when url not exists.
     */
    @Test
    public void whenRedirectAndUrlNotFoundThenError() throws Exception {
        when(this.redirectService.getUrl(TestConstants.SHORT_URL)).thenThrow(new UrlNotFoundException("Url not found"));

        this.redirect()
                .andExpect(status().isNotFound())
                .andExpect(content().json("{\"error\":\"Url not found\"}"));
    }

    /**
     * Test correctness of redirect request when runtime exception is thrown.
     */
    @Test
    public void wheRedirectAndRuntimeExceptionThenError() throws Exception {
        when(this.redirectService.getUrl(TestConstants.SHORT_URL)).thenThrow(new RuntimeException());

        this.redirect()
                .andExpect(status().isInternalServerError())
                .andExpect(content().json("{\"error\":\"Unknown error\"}"));
    }

    /**
     * Make a mock redirect request.
     *
     * @return mock request result.
     * @throws Exception shouldn't be thrown.
     */
    private ResultActions redirect() throws Exception {
        return this.mvc.perform(get(String.format("/%s", TestConstants.SHORT_URL)));
    }
}