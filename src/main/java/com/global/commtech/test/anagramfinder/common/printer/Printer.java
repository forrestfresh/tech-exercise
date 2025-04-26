package com.global.commtech.test.anagramfinder.common.printer;

import java.util.List;

public interface Printer {

    <T extends CharSequence> void print(Iterable<T> anagrams);

}
