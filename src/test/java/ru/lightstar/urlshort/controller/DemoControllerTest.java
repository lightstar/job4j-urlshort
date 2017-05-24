package ru.lightstar.urlshort.controller;

import org.junit.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * <code>DemoController</code> tests.
 *
 * @author LightStar
 * @since 0.0.1
 */
public class DemoControllerTest extends ControllerTest {

    /**
     * Test correctness of show demo request.
     */
    @Test
    public void whenShowHelpThenItShows() throws Exception {
        this.mvc.perform(get("/demo"))
                .andExpect(status().isOk())
                .andExpect(view().name("demo"));
    }
}