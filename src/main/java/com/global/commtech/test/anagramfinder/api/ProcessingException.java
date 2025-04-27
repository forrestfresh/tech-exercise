package com.global.commtech.test.anagramfinder.api;

/**
 * Exception thrown in the instance of an error whilst data is being processed.
 * <p>
 * Choice was to extend {@link RuntimeException} rather than being a checked exception, primarily in this instance to
 * keep the API and calling of the APIs clean. It is almost most likely that any data processing errors are
 * unrecoverable.
 */
public class ProcessingException extends RuntimeException {

    /**
     * Constructor to instantiate a new exception.
     *
     * @param message the error message
     * @param cause the underlying cause
     */
    public ProcessingException(String message, Throwable cause) {
        super(message, cause);
    }

}
