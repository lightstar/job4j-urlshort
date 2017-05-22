package ru.lightstar.urlshort.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.lightstar.urlshort.controller.request.CreateAccountRequest;
import ru.lightstar.urlshort.controller.response.CreateAccountResponse;
import ru.lightstar.urlshort.exception.AccountAlreadyExistsException;
import ru.lightstar.urlshort.exception.InvalidParametersException;
import ru.lightstar.urlshort.model.AccountWithOpenPassword;
import ru.lightstar.urlshort.service.ConfigService;
import ru.lightstar.urlshort.service.UtilService;

/**
 * Controller performing create account request.
 *
 * @author LightStar
 * @since 0.0.1
 */
@RestController
public class CreateAccountController extends ConfigController {

    /**
     * Constructs <code>CreateAccountController</code> object.
     *
     * @param configService injected configuration service bean.
     * @param utilService injected utility service bean.
     */
    @Autowired
    public CreateAccountController(final ConfigService configService, final UtilService utilService) {
        super(configService, utilService);
    }

    /**
     * Handler for create account request.
     *
     * @param request user request data object.
     * @return response data object.
     * @throws AccountAlreadyExistsException thrown if account with provided id already exists.
     */
    @RequestMapping(value = "/account", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public CreateAccountResponse createAccount(@RequestBody final CreateAccountRequest request)
            throws AccountAlreadyExistsException {
        this.checkCreateAccountRequest(request);

        final AccountWithOpenPassword accountWithOpenPassword =
                this.configService.createAccount(request.getAccountId());

        final CreateAccountResponse response = new CreateAccountResponse();
        response.setDescription("Your account is opened");
        response.setSuccess(true);
        response.setPassword(accountWithOpenPassword.getOpenPassword());

        return response;
    }

    /**
     * Check create account request data.
     *
     * @param request request data object.
     * @throws InvalidParametersException thrown if account id is empty.
     */
    private void checkCreateAccountRequest(final CreateAccountRequest request) {
        if (request.getAccountId().isEmpty()) {
            throw new InvalidParametersException();
        }
    }
}
