package com.global.commtech.test.anagramfinder.anagram;

import java.util.Arrays;
import java.util.function.Function;

public final class AnagramIdentifier implements Function<String, String> {

    @Override
    public String apply(String unsortedString) {
        char[] characters = unsortedString.trim()
                .toLowerCase()
                .toCharArray();

        Arrays.sort(characters);

        return String.valueOf(characters);
    }

}
