package ru.lightstar.urlshort.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import ru.lightstar.urlshort.exception.UrlNotFoundException;
import ru.lightstar.urlshort.model.Url;
import ru.lightstar.urlshort.service.RedirectService;

/**
 * Controller that processes redirection part of the application.
 *
 * @author LightStar
 * @since 0.0.1
 */
@Controller
public class RedirectController {

    /**
     * Redirect service bean used to perform redirection tasks.
     */
    private final RedirectService redirectService;

    /**
     * Constructs <code>RedirectController</code> object.
     *
     * @param redirectService injected redirect service bean.
     */
    @Autowired
    public RedirectController(final RedirectService redirectService) {
        this.redirectService = redirectService;
    }

    /**
     * Handler for short url request.
     *
     * @param shortUrl provided short url string.
     * @return redirection model and view object.
     * @throws UrlNotFoundException thrown if given short url does not registered in application.
     */
    @RequestMapping("/{shortUrl}")
    public ModelAndView redirect(@PathVariable final String shortUrl) throws UrlNotFoundException {
        final Url url = this.redirectService.getUrl(shortUrl);

        final RedirectView redirectView = new RedirectView(url.getLongUrl(), false);
        redirectView.setStatusCode(url.getRedirectType() == Url.REDIRECT_TYPE_PERMANENT ?
                HttpStatus.MOVED_PERMANENTLY : HttpStatus.FOUND);

        return new ModelAndView(redirectView);
    }
}
