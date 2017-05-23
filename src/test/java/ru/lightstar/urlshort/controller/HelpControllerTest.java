package ru.lightstar.urlshort.controller;

import org.junit.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * <code>HelpController</code> tests.
 *
 * @author LightStar
 * @since 0.0.1
 */
public class HelpControllerTest extends ControllerTest {

    /**
     * Test correctness of show help request.
     */
    @Test
    public void whenShowHelpThenItShows() throws Exception {
        this.mvc.perform(get("/help"))
                .andExpect(status().isOk())
                .andExpect(view().name("help"));
    }
}