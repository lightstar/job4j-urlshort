package ru.lightstar.urlshort;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for spring boot application.
 */
@SpringBootApplication
public class Application {

    /**
     * Application entry point.
     *
     * @param args application arguments.
     */
    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }
}