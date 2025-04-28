package com.global.commtech.test.anagramfinder.producers;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.List;
import java.util.function.BiPredicate;

import com.global.commtech.test.anagramfinder.api.DataConsumer;
import com.global.commtech.test.anagramfinder.api.ProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FileReaderBatchDataProducerTest {

    @Mock
    private DataConsumer<List<String>> consumer;
    @Mock
    private DataSource dataSource;
    @Mock
    private DataSource.Reader reader;
    @Mock(lenient = true)
    private BiPredicate<String, String> splitter;

    @InjectMocks
    private FileReaderBatchDataProducer producer;

    @BeforeEach
    void setUp() throws IOException {
        when(dataSource.getReader()).thenReturn(reader);
    }

    @Test
    void shouldNotCallConsumerWhenDataSourceProvidesNoData() {
        // when
        producer.produce();

        // then
        verifyNoInteractions(splitter, consumer);
    }

    @Test
    void shouldCallConsumerWhenDataSourceProvidesData() throws IOException {
        // given
        when(reader.next()).thenReturn("abc").thenReturn(null);

        // when
        producer.produce();

        // then
        verify(consumer).consume(eq(List.of("abc")));
    }

    @Test
    void shouldCallConsumerTwiceWhenBatchSplitterCreatesNewBatch() throws IOException {
        // given
        when(reader.next()).thenReturn("abc").thenReturn("def").thenReturn("lmno").thenReturn(null);
        when(splitter.test(eq("lmno"), any())).thenReturn(true);

        // when
        producer.produce();

        // then
        verify(consumer).consume(eq(List.of("abc", "def")));
        verify(consumer).consume(eq(List.of("lmno")));
    }

    @Test
    void shouldPropagateExceptionWhenThrownDuringRetrievalOfReader() throws IOException {
        // given
        when(dataSource.getReader()).thenThrow(new IOException());

        // when, then
        assertThrows(ProcessingException.class, () -> producer.produce());
    }

    @Test
    void shouldPropagateExceptionWhenThrownDuringReadingData() throws IOException {
        // given
        when(reader.next()).thenThrow(new IOException());

        // when, then
        assertThrows(ProcessingException.class, () -> producer.produce());
    }

}