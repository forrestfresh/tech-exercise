package com.global.commtech.test.anagramfinder.anagram;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AnagramIdentifierTest {

    @InjectMocks
    private AnagramIdentifier anagramIdentifier;

    @Test
    void shouldSortWordCharactersWhenCreatingIdentifier() {
        // when
        String identifier = anagramIdentifier.apply("word");

        // then
        assertThat(identifier).isEqualTo("dorw");
    }

    @Test
    void shouldCreateSameIdentifierForCollectionOfAnagrams() {
        // given
        Stream<String> anagrams = Stream.of("hello", "elloh", "leohl", "olleh");

        // when
        List<String> identifiers = anagrams.map(anagramIdentifier).distinct().toList();

        // then
        assertThat(identifiers).hasSize(1);
        assertThat(identifiers).first().isEqualTo("ehllo");
    }

}