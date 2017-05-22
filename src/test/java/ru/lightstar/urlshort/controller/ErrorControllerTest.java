package ru.lightstar.urlshort.controller;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.context.request.RequestAttributes;
import ru.lightstar.urlshort.TestConstants;

import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * <code>ErrorController</code> tests.
 *
 * @author LightStar
 * @since 0.0.1
 */
public class ErrorControllerTest extends ControllerTest {

    @Autowired
    private ErrorControllerImpl errorController;

    /**
     * Mock object used to retrieve error details.
     */
    @MockBean
    private ErrorAttributes errorAttributes;

    /**
     * Test correctness of error request.
     */
    @Test
    public void whenShowErrorThenResult() throws Exception {
        when(this.errorAttributes.getErrorAttributes(any(RequestAttributes.class), eq(false)))
                .thenReturn(Collections.singletonMap("message", TestConstants.ERROR));

        this.mvc.perform(get("/error"))
                .andExpect(status().isOk())
                .andExpect(content().json(String.format("{\"error\":\"%s\"}", TestConstants.ERROR)));
    }

    /**
     * Test correctness of <code>getErrorPath</code> method.
     */
    @Test
    public void whenGetErrorPathThenResult() throws Exception {
        assertThat(this.errorController.getErrorPath(), is("/error"));
    }
}