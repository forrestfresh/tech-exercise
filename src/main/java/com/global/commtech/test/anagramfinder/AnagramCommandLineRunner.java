package com.global.commtech.test.anagramfinder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class AnagramCommandLineRunner implements CommandLineRunner {

    private final AnagramResolver resolver;

    public AnagramCommandLineRunner(@Qualifier("tree") AnagramResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public void run(final String... args) {
        Assert.isTrue(args.length == 1, "Please ensure that the input file is provided");

        Path path = Paths.get(args[0]);
        Assert.isTrue(Files.exists(path), String.format("%s Does not exist", path));

        try {
            resolver.resolve(path);
        } catch (IOException exception) {
            throw new RuntimeException("Application failed to run", exception);
        }
    }

}
