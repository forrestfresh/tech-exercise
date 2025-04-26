package com.global.commtech.test.anagramfinder.common.printer;

import org.springframework.stereotype.Component;

@Component("dumb")
final class DumbPrinter implements Printer {

    @Override
    public <T extends CharSequence> void print(Iterable<T> anagrams) {
        // Do nothing
    }

}
