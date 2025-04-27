package com.global.commtech.test.anagramfinder;

public abstract class ConsumerWriter<T> {

    private final Consumer<T> consumer;

    protected ConsumerWriter(Consumer<T> consumer) {
        this.consumer = consumer;
    }

    protected void write(T input) {
        consumer.consume(input);
    }

}
