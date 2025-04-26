package com.global.commtech.test.anagramfinder.common.printer;

import java.util.StringJoiner;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component("simple")
@Primary
final class SimplePrinter implements Printer {

    @Override
    public <T extends CharSequence> void print(Iterable<T> anagrams) {
        StringJoiner joiner = new StringJoiner(",");
        anagrams.forEach(joiner::add);
        System.out.println(joiner);
    }

}
