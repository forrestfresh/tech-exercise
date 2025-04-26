package com.global.commtech.test.anagramfinder.resolvers.tree;

import java.util.Iterator;
import java.util.function.Consumer;

public interface Tree {

    void add(char[] value);

    void walk(Consumer<Iterator<CharSequence>> consumer);

}
