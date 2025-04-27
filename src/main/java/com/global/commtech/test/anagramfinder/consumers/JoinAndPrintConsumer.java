package com.global.commtech.test.anagramfinder.consumers;

import java.util.List;
import java.util.StringJoiner;

import com.global.commtech.test.anagramfinder.api.Consumer;

/**
 * Simple join and print consumer, that joins the data with comma separation and writes out to the printer consumer.
 */
public final class JoinAndPrintConsumer implements Consumer<List<String>> {

    private final Consumer<String> printer;

    /**
     * Constructor to instantiate a new join and print consumer.
     *
     * @param printer the printer
     */
    public JoinAndPrintConsumer(Consumer<String> printer) {
        this.printer = printer;
    }

    @Override
    public void consume(List<String> data) {
        StringJoiner joiner = new StringJoiner(",");
        data.forEach(joiner::add);
        printer.consume(joiner.toString());
    }

}
