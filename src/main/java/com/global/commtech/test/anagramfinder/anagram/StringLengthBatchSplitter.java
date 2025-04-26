package com.global.commtech.test.anagramfinder.anagram;

import java.util.function.BiPredicate;

public final class StringLengthBatchSplitter implements BiPredicate<String, String> {

    @Override
    public boolean test(String currentLine, String batchStartLine) {
        return currentLine.length() != batchStartLine.length();
    }

}
