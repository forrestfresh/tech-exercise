package com.global.commtech.test.anagramfinder.transformers;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.global.commtech.test.anagramfinder.Consumer;
import com.global.commtech.test.anagramfinder.Transformer;

public final class ChunkTransformer implements Transformer<List<String>> {

    private final Function<String, String> chunkIdentifier;
    private final Consumer<List<String>> consumer;

    public ChunkTransformer(Function<String, String> chunkIdentifier, Consumer<List<String>> consumer) {
        this.chunkIdentifier = chunkIdentifier;
        this.consumer = consumer;
    }

    @Override
    public void transform(List<String> input) {
        Map<String, List<String>> chunks = input.stream()
                .collect(groupingBy(chunkIdentifier, toList()));
        chunks.values().forEach(consumer::consume);
    }

}
