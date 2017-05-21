package ru.lightstar.urlshort;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Application main method test.
 *
 * @author Lightstar
 * @since 0.0.1
 */
public class ApplicationTest {

    /**
     * String that must be in output when spring boot sets up successfully.
     */
    private static final String SPRING_BOOT_STARTUP = "root of context hierarchy";

    /**
     * Test successful spring boot application start.
     */
    @Test
    public void whenRunMainThenSpringBootStarts() {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        Application.main(new String[]{});

        assertThat(outputStream.toString()).contains(SPRING_BOOT_STARTUP);
    }
}