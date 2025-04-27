package com.global.commtech.test.anagramfinder.consumers;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

import java.util.List;

import com.global.commtech.test.anagramfinder.api.Consumer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class JoinAndPrintConsumerTest {

    @Mock
    private Consumer<String> mockPrinter;

    @InjectMocks
    private JoinAndPrintConsumer consumer;

    @Test
    void shouldWriteOutToPrinterWhenConsumingData() {
        // when
        consumer.consume(List.of("hello"));

        // then
        verify(mockPrinter).consume(eq("hello"));
    }

    @Test
    void shouldJoinDataUsingCommaWhenPassedMultipleValues() {
        // given
        List<String> data = List.of("abc", "def", "hij");

        // when
        consumer.consume(data);

        // then
        verify(mockPrinter).consume(eq("abc,def,hij"));
    }

}