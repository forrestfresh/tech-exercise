package com.global.commtech.test.anagramfinder;

public interface Transformer<T> extends Consumer<T> {

    void transform(T input);

    @Override
    default void consume(T input) {
        transform(input);
    }

}
