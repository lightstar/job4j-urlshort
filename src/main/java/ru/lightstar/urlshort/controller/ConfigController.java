package ru.lightstar.urlshort.controller;

import ru.lightstar.urlshort.service.ConfigService;
import ru.lightstar.urlshort.service.UtilService;

/**
 * Base class for application's configuration controllers.
 *
 * @author LightStar
 * @since 0.0.1
 */
public abstract class ConfigController {

    /**
     * Configuration service bean used to perform configuration tasks.
     */
    final ConfigService configService;

    /**
     * Utility service bean with useful functions.
     */
    final UtilService utilService;

    /**
     * Constructs <code>ConfigController</code> object.
     *
     * @param configService injected configuration service bean.
     * @param utilService injected utility service bean.
     */
    public ConfigController(final ConfigService configService, final UtilService utilService) {
        this.configService = configService;
        this.utilService = utilService;
    }
}