package com.global.commtech.test.anagramfinder.anagram;

import java.util.function.BiPredicate;

/**
 * Words that are anagrams will always be of the same length. Therefore, this batch splitter defines a new batch when
 * two words are no longer the same length. This batch slitter assumes that the data being processed is ordered by
 * word length.
 */
public final class AnagramBatchSplitter implements BiPredicate<String, String> {

    @Override
    public boolean test(String currentWord, String firstBatchWord) {
        return currentWord.length() != firstBatchWord.length();
    }

}
