package com.global.commtech.test.anagramfinder.api;

/**
 * Responsible for consuming a stream of data.
 *
 * @param <T> the data type
 */
public interface Consumer<T> {

    /**
     * Consumes the passed data.
     *
     * @param data the data to be consumed
     * @throws ProcessingException in the case of error whilst producing data
     */
    void consume(T data) throws ProcessingException;

}
