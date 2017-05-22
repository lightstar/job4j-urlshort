package ru.lightstar.urlshort.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.lightstar.urlshort.exception.AccessDeniedException;
import ru.lightstar.urlshort.exception.AccountNotFoundException;
import ru.lightstar.urlshort.service.ConfigService;
import ru.lightstar.urlshort.service.UtilService;

import java.util.Map;

/**
 * Class performing statistic request.
 *
 * @author LightStar
 * @since 0.0.1
 */
@RestController
public class StatisticController extends ConfigController {

    /**
     * Name of administrator's role.
     */
    private static final String ROLE_ADMIN = "ROLE_ADMIN";

    /**
     * Constructs <code>StatisticController</code> object.
     *
     * @param configService injected configuration service bean.
     * @param utilService injected utility service bean.
     */
    @Autowired
    public StatisticController(final ConfigService configService, final UtilService utilService) {
        super(configService, utilService);
    }

    /**
     * Handler for statistic request.
     * Only user with the same authenticated id or administrator have access to this.
     *
     * @param id account's id.
     * @return response data object.
     * @throws AccountNotFoundException thrown if account with provided id does not exists.
     * @throws AccessDeniedException thrown if user does not have access to account with provided id.
     */
    @RequestMapping(value = "/statistic/{id}", method = RequestMethod.GET)
    public Map<String, Integer> getStatistic(@PathVariable final String id)
            throws AccountNotFoundException, AccessDeniedException {
        this.checkAccessToAccount(id);
        return this.configService.getStatistic(this.configService.getAccountById(id));
    }

    /**
     * Check access of currently authenticated user to account with provided id.
     *
     * @param id account's id.
     * @throws AccessDeniedException thrown if authenticated user does not have access to this account.
     */
    private void checkAccessToAccount(final String id) throws AccessDeniedException {
        if (!this.utilService.getAuthName().equals(id) && !this.utilService.getAuthRole().equals(ROLE_ADMIN)) {
            throw new AccessDeniedException(String.format("Access to account with id '%s' is forbidden", id));
        }
    }
}
