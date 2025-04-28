package com.global.commtech.test.anagramfinder.api;

/**
 * Responsible for producing a stream of data.
 * <p>
 * To be used in conjunction with {@link DataConsumer}, where the consumer is responsible for processing the incoming stream
 * of data.
 *
 * @param <T> the data type
 */
public interface DataProducer<T> {

    /**
     * Produces data.
     *
     * @throws ProcessingException in the case of error whilst producing data
     */
    void produce() throws ProcessingException;

}
