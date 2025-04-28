package com.global.commtech.test.anagramfinder.producers;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;

import com.global.commtech.test.anagramfinder.api.DataConsumer;
import com.global.commtech.test.anagramfinder.api.ConsumerWriter;
import com.global.commtech.test.anagramfinder.api.ProcessingException;
import com.global.commtech.test.anagramfinder.api.DataProducer;
import lombok.extern.slf4j.Slf4j;

/**
 * Responsible for reading from a configured data file and batching the data to be consumed downstream.
 * <p>
 * Batches are determined by a {@link BiPredicate} batch splitter. It is expected that the passed file already exists;
 * will result in {@link FileReaderBatchDataProducer#produce()} throwing a {@link ProcessingException} if it does not.
 */
@Slf4j
public final class FileReaderBatchDataProducer extends ConsumerWriter<List<String>> implements DataProducer<Path> {

    private final DataSource source;
    private final BiPredicate<String, String> batchSplitter;

    /**
     * Constructor to initialise a new batch producer.
     *
     * @param consumer consumer of the produced data batches
     * @param source the data file that is expected to exist
     * @param batchSplitter batch splitter used to determine a new batch
     */
    public FileReaderBatchDataProducer(DataConsumer<List<String>> consumer, DataSource source,
            BiPredicate<String, String> batchSplitter) {
        super(consumer);
        this.source = source;
        this.batchSplitter = batchSplitter;
    }

    @Override
    public void produce() throws ProcessingException {
        try (DataSource.Reader reader = source.getReader()) {
            processInBatches(reader);
        } catch (IOException ioE) {
            throw new ProcessingException("Error whilst reading data from the data source", ioE);
        }
    }

    private void processInBatches(DataSource.Reader reader) throws IOException {
        String currentLine, firstBatchLine = null;
        List<String> batch = new ArrayList<>();

        while ((currentLine = reader.next()) != null) {
            if (firstBatchLine == null || batchSplitter.test(currentLine, firstBatchLine)) {
                firstBatchLine = currentLine;
                consumeBatch(batch);
            }

            batch.add(currentLine);
        }

        consumeBatch(batch);
    }

    private void consumeBatch(List<String> batch) {
        if (!batch.isEmpty()) {
            if (log.isDebugEnabled()) {
                log.debug("New batch of size {}", batch.size());
            }

            // process the remaining batch
            write(new ArrayList<>(batch));
            batch.clear();
        }
    }

}
