package com.global.commtech.test.anagramfinder.producers;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;

import com.global.commtech.test.anagramfinder.Consumer;
import com.global.commtech.test.anagramfinder.Producer;

public final class FileReaderBatchProducer implements Producer<Path> {

    private final BiPredicate<String, String> batchSplitter;
    private final Consumer<List<String>> consumer;

    public FileReaderBatchProducer(BiPredicate<String, String> batchSplitter, Consumer<List<String>> consumer) {
        this.batchSplitter = batchSplitter;
        this.consumer = consumer;
    }

    @Override
    public void produce(Path textFilePath) throws Exception {
        try (BufferedReader reader = Files.newBufferedReader(textFilePath)) {
            processInBatches(reader);
        }
    }

    private void processInBatches(BufferedReader reader) throws IOException {
        String currentLine, batchStartLine = null;
        List<String> batch = new ArrayList<>();

        while ((currentLine = reader.readLine()) != null) {

            if (batchStartLine == null || batchSplitter.test(currentLine, batchStartLine)) {
                batchStartLine = currentLine;
                consumeBatch(batch);
            }

            batch.add(currentLine);
        }

        consumeBatch(batch);
    }

    private void consumeBatch(List<String> batch) {
        if (!batch.isEmpty()) {
            // process the remaining batch
            consumer.consume(new ArrayList<>(batch));
            batch.clear();
        }
    }

}
