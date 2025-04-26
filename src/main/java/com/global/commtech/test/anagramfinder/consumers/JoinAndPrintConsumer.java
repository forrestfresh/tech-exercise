package com.global.commtech.test.anagramfinder.consumers;

import java.util.List;
import java.util.StringJoiner;

import com.global.commtech.test.anagramfinder.Consumer;

public final class JoinAndPrintConsumer implements Consumer<List<String>> {

    @Override
    public void consume(List<String> input) {
        StringJoiner joiner = new StringJoiner(",");
        input.forEach(joiner::add);
        System.out.println(joiner);
    }

}
