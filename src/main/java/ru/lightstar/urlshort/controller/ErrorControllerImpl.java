package ru.lightstar.urlshort.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;
import ru.lightstar.urlshort.controller.response.ErrorResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Controller that replaces spring boot's default error controller implementation.
 *
 * @author LightStar
 * @since 0.0.1
 */
@RestController
public class ErrorControllerImpl implements ErrorController {

    /**
     * Mapped path of the error page.
     */
    private static final String ERROR_PATH = "/error";

    /**
     * Object used to retrieve error details.
     */
    private final ErrorAttributes errorAttributes;

    /**
     * Constructs <code>ErrorControllerImpl</code> object.
     *
     * @param errorAttributes injected <code>ErrorAttributes</code> bean.
     */
    @Autowired
    public ErrorControllerImpl(final ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    /**
     * Error handler that extracts error information and returns JSON data with error message.
     *
     * @param request servlet's request.
     * @return response data.
     */
    @RequestMapping(value = ERROR_PATH)
    public ErrorResponse error(final HttpServletRequest request) {
        final RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        final Map<String, Object> errorAttributes = this.errorAttributes.getErrorAttributes(requestAttributes,
                false);
        return new ErrorResponse((String) errorAttributes.get("message"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}