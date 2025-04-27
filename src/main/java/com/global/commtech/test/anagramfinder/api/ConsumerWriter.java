package com.global.commtech.test.anagramfinder.api;

/**
 * Provides the means to write to a consumer.
 * <p>
 * To be extended by any implementation that is a writer of data to consumers, most notably {@link Producer} and
 * {@link Transformer}. Helps to remove the boilerplate code of making a consumer available.
 *
 * @param <T> the data type
 */
public abstract class ConsumerWriter<T> {

    private final Consumer<T> consumer;

    /**
     * Constructor for initialising consumer writers.
     *
     * @param consumer the consumer
     */
    protected ConsumerWriter(Consumer<T> consumer) {
        this.consumer = consumer;
    }

    /**
     * Writes data out to the consumer.
     *
     * @param data the data to be consumed
     * @throws ProcessingException in the case of error whilst producing data
     */
    protected void write(T data) throws ProcessingException {
        consumer.consume(data);
    }

}
