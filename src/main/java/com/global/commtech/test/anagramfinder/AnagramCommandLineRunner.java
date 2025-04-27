package com.global.commtech.test.anagramfinder;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.global.commtech.test.anagramfinder.anagram.AnagramBatchSplitter;
import com.global.commtech.test.anagramfinder.anagram.AnagramIdentifier;
import com.global.commtech.test.anagramfinder.api.Consumer;
import com.global.commtech.test.anagramfinder.api.ProcessingException;
import com.global.commtech.test.anagramfinder.api.Producer;
import com.global.commtech.test.anagramfinder.api.Transformer;
import com.global.commtech.test.anagramfinder.consumers.JoinAndPrintConsumer;
import com.global.commtech.test.anagramfinder.producers.DataFileSource;
import com.global.commtech.test.anagramfinder.producers.FileReaderBatchProducer;
import com.global.commtech.test.anagramfinder.transformers.ChunkTransformer;
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

    private Producer<Path> createProducer(Path path) {
        Consumer<List<String>> printConsumer = new JoinAndPrintConsumer(System.out::println);
        Transformer<List<String>> chunkConsumer = new ChunkTransformer(printConsumer, new AnagramIdentifier());
        return new FileReaderBatchProducer(chunkConsumer, new DataFileSource(path), new AnagramBatchSplitter());
    }

}
