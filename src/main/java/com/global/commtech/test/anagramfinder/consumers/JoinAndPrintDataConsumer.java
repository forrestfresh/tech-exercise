package com.global.commtech.test.anagramfinder.consumers;

import java.util.List;
import java.util.StringJoiner;
import java.util.function.Consumer;

import com.global.commtech.test.anagramfinder.api.DataConsumer;

/**
 * Simple join and print consumer, that joins the data with comma separation and writes out to the printer consumer.
 */
public final class JoinAndPrintDataConsumer implements DataConsumer<List<String>> {

    private final Consumer<String> printer;

    /**
     * Constructor to instantiate a new join and print consumer.
     *
     * @param printer the printer
     */
    public JoinAndPrintDataConsumer(Consumer<String> printer) {
        this.printer = printer;
    }

    @Override
    public void consume(List<String> data) {
        StringJoiner joiner = new StringJoiner(",");
        data.stream()
                .sorted() // order so output is deterministic
                .forEach(joiner::add);
        printer.accept(joiner.toString());
    }

}
