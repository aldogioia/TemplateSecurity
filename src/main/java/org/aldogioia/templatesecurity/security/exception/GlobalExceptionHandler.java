package org.aldogioia.templatesecurity.security.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.aldogioia.templatesecurity.data.dto.ErrorDto;
import org.aldogioia.templatesecurity.security.exception.customException.TokenException;
import org.aldogioia.templatesecurity.security.exception.customException.TokenExpiredException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

/**
 * Gestore globale delle eccezioni per l'applicazione.
 * <p>
 * Questa classe intercetta tutte le eccezioni non gestite dai controller
 * e restituisce una risposta strutturata con dettagli sull'errore.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDto onException(WebRequest req, Exception e) {
        return createErrorResponse(req, e.getMessage());
    }

    @ExceptionHandler(TokenException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDto onTokenException(WebRequest req, TokenException e) {
        return createErrorResponse(req, e.getMessage());
    }

    @ExceptionHandler(TokenExpiredException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorDto onTokenExpiredException(WebRequest req, TokenExpiredException e) {
        return createErrorResponse(req, e.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto onEntityNotFoundException(WebRequest req, EntityNotFoundException e) {
        return createErrorResponse(req, e.getMessage());
    }

    private ErrorDto createErrorResponse(WebRequest req, String message) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) req.resolveReference("request");
        assert httpServletRequest != null;
        return new ErrorDto(new Date(), httpServletRequest.getRequestURI(), message);
    }
}
