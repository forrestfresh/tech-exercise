package com.global.commtech.test.anagramfinder.transformers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.function.Function;

import com.global.commtech.test.anagramfinder.api.DataConsumer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ChunkDataTransformerTest {

    @Mock
    private DataConsumer<List<String>> consumer;
    @Mock
    private Function<String, String> chunkIdentifier;
    @Captor
    private ArgumentCaptor<List<String>> anagramCaptor;

    @InjectMocks
    private ChunkDataTransformer transformer;

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
        verify(consumer).consume(anagramCaptor.capture());
        assertThat(anagramCaptor.getAllValues()).hasSize(1);
        assertThat(anagramCaptor.getValue()).containsExactlyInAnyOrder("abc", "bca");
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
        verify(consumer, times(2)).consume(anagramCaptor.capture());
        assertThat(anagramCaptor.getAllValues()).hasSize(2);
        assertThat(anagramCaptor.getAllValues()).satisfiesExactlyInAnyOrder(
                list -> assertThat(list).containsExactlyInAnyOrder("abc", "bca"),
                list -> assertThat(list).containsExactly("xyz"));
    }

}