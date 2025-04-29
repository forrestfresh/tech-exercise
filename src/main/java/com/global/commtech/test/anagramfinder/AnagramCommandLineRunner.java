package com.global.commtech.test.anagramfinder;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.global.commtech.test.anagramfinder.anagram.AnagramBatchSplitter;
import com.global.commtech.test.anagramfinder.anagram.AnagramIdentifier;
import com.global.commtech.test.anagramfinder.api.DataConsumer;
import com.global.commtech.test.anagramfinder.api.DataProducer;
import com.global.commtech.test.anagramfinder.api.DataTransformer;
import com.global.commtech.test.anagramfinder.api.ProcessingException;
import com.global.commtech.test.anagramfinder.consumers.JoinAndPrintDataConsumer;
import com.global.commtech.test.anagramfinder.producers.BatchDataProducer;
import com.global.commtech.test.anagramfinder.producers.DataFileSource;
import com.global.commtech.test.anagramfinder.transformers.ChunkDataTransformer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public final class AnagramCommandLineRunner implements CommandLineRunner {

    @Override
    public void run(final String... args) {
        Assert.isTrue(args.length == 1, "Please ensure that the input file is provided");

        Path path = Paths.get(args[0]);
        Assert.isTrue(Files.exists(path), String.format("%s Does not exist", path));

        try {
            createProducer(path).produce();
        } catch (ProcessingException pE) {
            throw new RuntimeException("Application failed to run", pE);
        }
    }

    private DataProducer createProducer(Path path) {
        DataConsumer<List<String>> printConsumer = new JoinAndPrintDataConsumer(System.out::println);
        DataTransformer<List<String>> chunkTransformer = new ChunkDataTransformer(printConsumer, new AnagramIdentifier());
        return new BatchDataProducer(chunkTransformer, new DataFileSource(path), new AnagramBatchSplitter());
    }

}
