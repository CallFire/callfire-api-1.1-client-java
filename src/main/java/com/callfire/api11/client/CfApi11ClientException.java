package com.callfire.api11.client;

/**
 * Thrown by client in case of invalid instantiation
 *
 * @since 1.0
 */
public class CfApi11ClientException extends RuntimeException {
    public CfApi11ClientException() {
    }

    public CfApi11ClientException(String message) {
        super(message);
    }

    public CfApi11ClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public CfApi11ClientException(Throwable cause) {
        super(cause);
    }
}
