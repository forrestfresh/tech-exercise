package com.global.commtech.test.anagramfinder.consumers;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.function.Consumer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class JoinAndPrintDataConsumerTest {

    @Mock
    private Consumer<String> mockPrinter;

    @InjectMocks
    private JoinAndPrintDataConsumer consumer;

    @Test
    void shouldWriteOutToPrinterWhenConsumingData() {
        // when
        consumer.consume(List.of("hello"));

        // then
        verify(mockPrinter).accept(eq("hello"));
    }

    @Test
    void shouldJoinDataUsingCommaWhenPassedMultipleValues() {
        // given
        List<String> data = List.of("abc", "def", "hij");

        // when
        consumer.consume(data);

        // then
        verify(mockPrinter).accept(eq("abc,def,hij"));
    }

}