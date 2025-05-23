package com.global.commtech.test.anagramfinder.api;

/**
 * Responsible for consuming and transforming a stream of data.
 * <p>
 * Given it consumes a stream of data, this is a type of {@link DataConsumer}, but in a similar way with the
 * {@link DataProducer} is expected to be used in conjunction with a downstream consumer, responsible for handling the
 * output.
 *
 * @param <T> the data type
 */
public interface DataTransformer<T> extends DataConsumer<T> {

    /**
     * Transforms the passed data.
     *
     * @param data the passed data
     * @throws ProcessingException in the case of error whilst producing data
     */
    void transform(T data) throws ProcessingException;

    @Override
    default void consume(T data) throws ProcessingException {
        transform(data);
    }

}
