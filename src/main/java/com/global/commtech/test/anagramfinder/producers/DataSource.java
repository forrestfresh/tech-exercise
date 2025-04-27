package com.global.commtech.test.anagramfinder.producers;

import java.io.Closeable;
import java.io.IOException;

/**
 * Data source provides {@link Closeable} readers that deliver up data.
 */
public interface DataSource {

    /**
     * Gets a reader instance.
     *
     * @return the reader instance
     * @throws IOException should any IO errors occur
     */
    Reader getReader() throws IOException;

    /**
     * {@link Closeable} reader that provides data.
     */
    interface Reader extends Closeable {

        /**
         * Retrieves the next data line.
         *
         * @return the next data line
         * @throws IOException should any IO errors occur
         */
        String next() throws IOException;

        @Override
        default void close() throws IOException {
            // do nothing by default
        }

    }

}
