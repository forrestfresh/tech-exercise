package com.global.commtech.test.anagramfinder.consumers;

import java.util.List;
import java.util.StringJoiner;

import com.global.commtech.test.anagramfinder.api.Consumer;

/**
 * Simple join and print consumer, that joins the data with comma separation and writes out to standard out.
 */
public final class JoinAndPrintConsumer implements Consumer<List<String>> {

    @Override
    public void consume(List<String> data) {
        StringJoiner joiner = new StringJoiner(",");
        data.forEach(joiner::add);
        System.out.println(joiner);
    }

}
