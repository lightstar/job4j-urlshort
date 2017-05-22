package ru.lightstar.urlshort.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.lightstar.urlshort.controller.request.RegisterUrlRequest;
import ru.lightstar.urlshort.controller.response.RegisterUrlResponse;
import ru.lightstar.urlshort.exception.AccountNotFoundException;
import ru.lightstar.urlshort.exception.InvalidParametersException;
import ru.lightstar.urlshort.exception.LongUrlAlreadyExistsException;
import ru.lightstar.urlshort.model.Account;
import ru.lightstar.urlshort.model.Url;
import ru.lightstar.urlshort.service.ConfigService;
import ru.lightstar.urlshort.service.UtilService;

import javax.servlet.http.HttpServletRequest;

/**
 * Controller performing register url request.
 *
 * @author LightStar
 * @since 0.0.1
 */
@RestController
public class RegisterUrlController extends ConfigController {

    /**
     * Default port for the "http" schema.
     */
    private static final int HTTP_DEFAULT_PORT = 80;

    /**
     * Default port for the "https" schema.
     */
    private static final int HTTPS_DEFAULT_PORT = 443;

    /**
     * String representation of the "http" schema.
     */
    private static final String HTTP_SCHEMA = "http";

    /**
     * String representation of the "https" schema.
     */
    private static final String HTTPS_SCHEMA = "https";

    /**
     * Constructs <code>RegisterUrlController</code> object.
     *
     * @param configService injected configuration service bean.
     * @param utilService injected utility service bean.
     */
    @Autowired
    public RegisterUrlController(final ConfigService configService, final UtilService utilService) {
        super(configService, utilService);
    }

    /**
     * Handler for register url request.
     *
     * @param request user request data object.
     * @param servletRequest servlet's request object used to get application url and context path.
     * @return response data object.
     * @throws AccountNotFoundException thrown if account with authenticated id does not exists.
     * @throws LongUrlAlreadyExistsException thrown if given long url already registered in account.
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterUrlResponse registerUrl(@RequestBody final RegisterUrlRequest request,
                                           final HttpServletRequest servletRequest)
            throws AccountNotFoundException, LongUrlAlreadyExistsException {
        this.fixRegisterUrlRequest(request);
        this.checkRegisterUrlRequest(request);

        final Account account = this.configService.getAccountById(this.utilService.getAuthName());
        final Url url = this.configService.registerUrl(account, request.getLongUrl(), request.getRedirectType());
        final String portString = this.getPortString(servletRequest);

        final RegisterUrlResponse registerUrlResponse = new RegisterUrlResponse();
        registerUrlResponse.setShortUrl(String.format("%s://%s%s%s/%s", servletRequest.getScheme(),
                servletRequest.getServerName(), portString, servletRequest.getContextPath(), url.getShortUrl()));

        return registerUrlResponse;
    }

    /**
     * Generate port string for the current deployment that can be concatenated to host part of URL.
     * Returns empty string if deployment port is default for the schema.
     *
     * @param request servlet's request object.
     * @return generated port string.
     */
    private String getPortString(final HttpServletRequest request) {
        final String portString;
        if ((request.getScheme().equals(HTTP_SCHEMA) && request.getLocalPort() == HTTP_DEFAULT_PORT) ||
                (request.getScheme().equals(HTTPS_SCHEMA) && request.getLocalPort() == HTTPS_DEFAULT_PORT)) {
            portString = "";
        } else {
            portString = String.format(":%d", request.getLocalPort());
        }
        return portString;
    }

    /**
     * Fill register url request data with default values if they are not provided.
     *
     * @param request request data object.
     */
    private void fixRegisterUrlRequest(final RegisterUrlRequest request) {
        if (request.getRedirectType() == 0) {
            request.setRedirectType(Url.REDIRECT_TYPE_TEMPORARY);
        }
    }

    /**
     * Check register url request data.
     *
     * @param request request data object.
     * @throws InvalidParametersException thrown if long url is empty or redirect type is invalid.
     */
    private void checkRegisterUrlRequest(final RegisterUrlRequest request) {
        if (request.getLongUrl().isEmpty()) {
            throw new InvalidParametersException();
        }

        if (request.getRedirectType() != Url.REDIRECT_TYPE_PERMANENT &&
                request.getRedirectType() != Url.REDIRECT_TYPE_TEMPORARY) {
            throw new InvalidParametersException();
        }
    }
}
