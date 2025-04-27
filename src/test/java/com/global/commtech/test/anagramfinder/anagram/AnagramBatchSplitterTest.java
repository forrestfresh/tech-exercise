package com.global.commtech.test.anagramfinder.anagram;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AnagramBatchSplitterTest {

    @InjectMocks
    private AnagramBatchSplitter batchSplitter;

    @Test
    void shouldReturnTrueWhenWordLengthDiffer() {
        // when
        boolean newBatch = batchSplitter.test("first_word", "second_word");

        // then
        assertThat(newBatch).isTrue();
    }

    @Test
    void shouldReturnFalseWhenWordLengthIsEqual() {
        // when
        boolean newBatch = batchSplitter.test("one_word", "two_word");

        // then
        assertThat(newBatch).isFalse();
    }

}