package com.global.commtech.test.anagramfinder.transformers;

import static java.util.stream.Collectors.groupingByConcurrent;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.global.commtech.test.anagramfinder.api.ConsumerWriter;
import com.global.commtech.test.anagramfinder.api.DataConsumer;
import com.global.commtech.test.anagramfinder.api.DataTransformer;
import lombok.extern.slf4j.Slf4j;

/**
 * Responsible for chunking the data; creating smaller batches from the data set. The chunks are determined by a
 * chunk identifier {@link Function}. This function produces the common property that groups and identifiers a set of
 * data; a chunk.
 * <p>
 * Note processing of the data is done concurrently to aid performance with large data sets. Therefore, the order of
 * values within a chunk will not be deterministic.
 */
@Slf4j
public final class ChunkDataTransformer extends ConsumerWriter<List<String>> implements DataTransformer<List<String>> {

    private final Function<String, String> chunkIdentifier;

    /**
     * Constructor to instantiate a new chunk transformer.
     *
     * @param consumer consumer of the produced chunks
     * @param chunkIdentifier function to identify the common identifier that brings together a chunk
     */
    public ChunkDataTransformer(DataConsumer<List<String>> consumer, Function<String, String> chunkIdentifier) {
        super(consumer);
        this.chunkIdentifier = chunkIdentifier;
    }

    @Override
    public void transform(List<String> data) {
        Map<String, List<String>> chunks = data.parallelStream()
                .collect(groupingByConcurrent(chunkIdentifier, toList()));
        chunks.values().forEach(this::consumeChunk);
    }

    private void consumeChunk(List<String> chunk) {
        if (log.isDebugEnabled()) {
            log.debug("New chunk of size {}", chunk.size());
        }

        write(new ArrayList<>(chunk));
    }

}
