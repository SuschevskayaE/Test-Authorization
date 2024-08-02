package ru.test.authorization.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.test.authorization.exception.GatewayTimeoutException;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.GATEWAY_TIMEOUT)
    public ErrorResponse handleDataNotFoundException(final GatewayTimeoutException e) {
        return new ErrorResponse(
                e.getMessage()
        );
    }
}
