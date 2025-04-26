package com.global.commtech.test.anagramfinder.resolvers.basic;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import com.global.commtech.test.anagramfinder.AnagramResolver;
import com.global.commtech.test.anagramfinder.common.printer.Printer;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component("basic")
@Primary
class BasicInMemoryAnagramResolver implements AnagramResolver {

    private final Printer printer;

    BasicInMemoryAnagramResolver(Printer printer) {
        this.printer = printer;
    }

    @Override
    public void resolve(Path anagramFile) throws IOException {
        Map<String, List<String>> anagramMatches = parseFile(anagramFile);
        anagramMatches.values().forEach(printer::print);
    }

    private Map<String, List<String>> parseFile(Path path) throws IOException {
        try (Stream<String> lines = Files.lines(path)) {
            return lines.collect(groupingBy(this::sortString, toList()));
        }
    }

    private String sortString(String unsortedString) {
        char[] characters = unsortedString.trim()
                .toLowerCase()
                .toCharArray();

        Arrays.sort(characters);

        return String.valueOf(characters);
    }

}
