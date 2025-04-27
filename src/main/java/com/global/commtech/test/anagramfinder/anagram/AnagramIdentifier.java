package com.global.commtech.test.anagramfinder.anagram;

import java.util.Arrays;
import java.util.function.Function;

/**
 * Given a word produces a word identifier by sorting the characters, such that two or more words that are anagrams of
 * each other produces the same identifier.
 * <p>
 * Example using words [abc, acb, bac, bca, cab, cba] all produce the same identifier of [abc].
 */
public final class AnagramIdentifier implements Function<String, String> {

    @Override
    public String apply(String unsortedWord) {
        char[] characters = unsortedWord.trim()
                .toLowerCase()
                .toCharArray();

        Arrays.sort(characters);
        return String.valueOf(characters);
    }

}
