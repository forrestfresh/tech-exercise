package com.global.commtech.test.anagramfinder;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.global.commtech.test.anagramfinder.anagram.AnagramIdentifier;
import com.global.commtech.test.anagramfinder.anagram.StringLengthBatchSplitter;
import com.global.commtech.test.anagramfinder.consumers.JoinAndPrintConsumer;
import com.global.commtech.test.anagramfinder.producers.FileReaderBatchProducer;
import com.global.commtech.test.anagramfinder.transformers.ChunkTransformer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class AnagramCommandLineRunner implements CommandLineRunner {

    @Override
    public void run(final String... args) {
        Assert.isTrue(args.length == 1, "Please ensure that the input file is provided");

        Path path = Paths.get(args[0]);
        Assert.isTrue(Files.exists(path), String.format("%s Does not exist", path));

        try {
            Producer<Path> anagramProducer = createProducer();
            anagramProducer.produce(path);
        } catch (Exception exception) {
            throw new RuntimeException("Application failed to run", exception);
        }
    }

    private Producer<Path> createProducer() {
        Consumer<List<String>> printConsumer = new JoinAndPrintConsumer();
        Transformer<List<String>> chunkConsumer = new ChunkTransformer(new AnagramIdentifier(), printConsumer);
        return new FileReaderBatchProducer(new StringLengthBatchSplitter(), chunkConsumer);
    }

}
