package ru.test.authorization.exception;

public class GatewayTimeoutException extends RuntimeException {

    public GatewayTimeoutException(final String message) {
        super(message);
    }
}
