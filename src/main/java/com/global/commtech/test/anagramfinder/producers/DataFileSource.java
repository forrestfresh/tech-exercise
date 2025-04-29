package com.global.commtech.test.anagramfinder.producers;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Data file source takes a file and providers a readers that read each line of the file.
 * <p>
 * Reader instances that are returned are closable, ensuring the underlying file is closed.
 */
public final class DataFileSource implements DataSource {

    private final Path dataFile;

    /**
     * Constructor to instantiate a new instance.
     *
     * @param dataFile the data file to read from
     */
    public DataFileSource(Path dataFile) {
        this.dataFile = dataFile;
    }

    @Override
    public Reader getReader() throws IOException {
        BufferedReader reader = Files.newBufferedReader(dataFile);
        return new FileReader(reader);
    }

    private record FileReader(BufferedReader bufferedReader) implements Reader {

        @Override
        public String next() throws IOException {
            return bufferedReader.readLine();
        }

        @Override
        public void close() throws IOException {
            bufferedReader.close();
        }

    }

}
