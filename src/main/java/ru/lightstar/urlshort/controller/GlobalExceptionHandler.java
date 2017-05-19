package ru.lightstar.urlshort.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.lightstar.urlshort.controller.response.CreateAccountResponse;
import ru.lightstar.urlshort.controller.response.ErrorResponse;
import ru.lightstar.urlshort.exception.*;

/**
 * Handler for all previously unhandled exceptions in application.
 *
 * @author LightStar
 * @since 0.0.1
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handler for {@link AccountAlreadyExistsException}.
     *
     * @return response data object.
     */
    @ExceptionHandler(AccountAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public CreateAccountResponse accountAlreadyExistsExceptionHandler() {
        final CreateAccountResponse response = new CreateAccountResponse();
        response.setDescription("Account with that ID already exists");
        response.setSuccess(false);
        return response;
    }

    /**
     * Handler for {@link AccountNotFoundException}.
     *
     * @return response data object.
     */
    @ExceptionHandler(AccountNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse accountNotFoundExceptionHandler() {
        return this.exceptionHandler("Account not found");
    }

    /**
     * Handler for {@link LongUrlAlreadyExistsException}.
     *
     * @return response data object.
     */
    @ExceptionHandler(LongUrlAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse longUrlAlreadyExistsExceptionHandler() {
        return this.exceptionHandler("This url already registered in your account");
    }

    /**
     * Handler for {@link UrlNotFoundException}.
     *
     * @return response data object.
     */
    @ExceptionHandler(UrlNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse urlNotFoundExceptionHandler() {
        return this.exceptionHandler("Url not found");
    }

    /**
     * Handler for {@link AccessDeniedException}.
     *
     * @return response data object.
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse accessDeniedExceptionHandler() {
        return this.exceptionHandler("Access denied");
    }

    /**
     * Handler for exceptions related to parsing request data and validating parameters.
     *
     * @return response data object.
     */
    @ExceptionHandler({InvalidParametersException.class, HttpMessageConversionException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse invalidParametersExceptionHandler() {
        return this.exceptionHandler("Invalid parameters");
    }

    /**
     * Default exception handler.
     *
     * @return response data object.
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse defaultExceptionHandler() {
        return this.exceptionHandler("Unknown error");
    }

    /**
     * Generic exception handler that uses provided custom error message.
     *
     * @param errorMessage provided custom error message.
     * @return response data object.
     */
    private ErrorResponse exceptionHandler(final String errorMessage) {
        return new ErrorResponse(errorMessage);
    }
}