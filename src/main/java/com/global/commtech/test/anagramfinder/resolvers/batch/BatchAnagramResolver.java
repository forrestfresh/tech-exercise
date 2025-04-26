package com.global.commtech.test.anagramfinder.resolvers.batch;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.global.commtech.test.anagramfinder.AnagramResolver;
import com.global.commtech.test.anagramfinder.common.printer.Printer;
import org.springframework.stereotype.Component;

@Component("batch")
class BatchAnagramResolver implements AnagramResolver {

    private final Printer printer;

    BatchAnagramResolver(Printer printer) {
        this.printer = printer;
    }

    @Override
    public void resolve(Path anagramFile) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(anagramFile)) {
            processInBatches(reader);
        }
    }

    private void processInBatches(BufferedReader reader) throws IOException {
        String anagram;
        int anagramLength = 0;
        List<String> anagramBatch = new ArrayList<>();

        while ((anagram = reader.readLine()) != null) {

            if (anagram.length() != anagramLength) {
                anagramLength = anagram.length();
                if (!anagramBatch.isEmpty()) {
                    processBatch(anagramBatch);
                    anagramBatch.clear();
                }
            }

            anagramBatch.add(anagram);
        }

        if (!anagramBatch.isEmpty()) {
            // process the remaining batch
            processBatch(anagramBatch);
        }
    }

    private void processBatch(List<String> anagramBatch) {
        Map<String, List<String>> anagramGroups = anagramBatch.stream()
                .collect(groupingBy(this::sortString, toList()));
        anagramGroups.values().forEach(printer::print);
    }

    private String sortString(String unsortedString) {
        char[] characters = unsortedString.trim()
                .toLowerCase()
                .toCharArray();

        Arrays.sort(characters);

        return String.valueOf(characters);
    }

}
