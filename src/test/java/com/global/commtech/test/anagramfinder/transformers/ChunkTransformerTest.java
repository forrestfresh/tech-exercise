package com.global.commtech.test.anagramfinder.transformers;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.function.Function;

import com.global.commtech.test.anagramfinder.api.Consumer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ChunkTransformerTest {

    @Mock
    private Consumer<List<String>> consumer;
    @Mock
    private Function<String, String> chunkIdentifier;

    @InjectMocks
    private ChunkTransformer transformer;

    @Test
    void shouldNotCallConsumerWhenNoDataProvided() {
        // when
        transformer.transform(List.of());

        // then
        verifyNoInteractions(chunkIdentifier, consumer);
    }

    @Test
    void shouldCreateSingleGroupWhenProvideSingleValue() {
        // given
        when(chunkIdentifier.apply("abc")).thenReturn("abc");

        // when
        transformer.transform(List.of("abc"));

        // then
        verify(consumer).consume(eq(List.of("abc")));
    }

    @Test
    void shouldGroupDataWhenIdentifierIsSame() {
        // given
        when(chunkIdentifier.apply("abc")).thenReturn("abc");
        when(chunkIdentifier.apply("bca")).thenReturn("abc");

        // when
        transformer.transform(List.of("abc", "bca"));

        // then
        verify(consumer).consume(eq(List.of("abc", "bca")));
    }

    @Test
    void shouldChunkDataWhenIdentifierIsDifferent() {
        // given
        when(chunkIdentifier.apply("abc")).thenReturn("abc");
        when(chunkIdentifier.apply("bca")).thenReturn("abc");
        when(chunkIdentifier.apply("xyz")).thenReturn("xyz");

        // when
        transformer.transform(List.of("abc", "bca", "xyz"));

        // then
        verify(consumer).consume(eq(List.of("abc", "bca")));
        verify(consumer).consume(eq(List.of("xyz")));
    }

}