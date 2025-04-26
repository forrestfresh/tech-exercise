package com.global.commtech.test.anagramfinder.resolvers.tree;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.global.commtech.test.anagramfinder.AnagramResolver;
import com.global.commtech.test.anagramfinder.common.printer.Printer;
import org.springframework.stereotype.Component;

@Component("tree")
class TreeBasedAnagramResolver implements AnagramResolver {

    private final Printer printer;

    TreeBasedAnagramResolver(Printer printer) {
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

        Tree tree = new RecursiveTree();

        while ((anagram = reader.readLine()) != null) {

            if (anagram.length() != anagramLength) {
                anagramLength = anagram.length();
                processBatch(tree);
                tree = new RecursiveTree();
            }

            tree.add(anagram.trim().toCharArray());
        }

        // process the remaining batch
        processBatch(tree);
    }

    private void processBatch(Tree tree) {
        tree.walk((iterator) -> printer.print(() -> iterator));
    }

}
