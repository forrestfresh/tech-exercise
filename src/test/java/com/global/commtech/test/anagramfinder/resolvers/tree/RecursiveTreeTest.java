package com.global.commtech.test.anagramfinder.resolvers.tree;

import java.util.Iterator;
import java.util.StringJoiner;

import org.junit.jupiter.api.Test;

class RecursiveTreeTest {

    @Test
    void test() {
        RecursiveTree tree = new RecursiveTree();
        tree.add("hello".toCharArray());
        tree.add("help".toCharArray());
        tree.add("pelh".toCharArray());
        tree.add("cheese".toCharArray());
        tree.add("c".toCharArray());
        tree.add("lpeh".toCharArray());
        tree.add("ess".toCharArray());
        tree.add("chesee".toCharArray());
        tree.add("c".toCharArray());

        tree.walk(this::print);
    }

    private void print(Iterator<CharSequence> iterator) {
        StringJoiner joiner = new StringJoiner(",");
        while (iterator.hasNext()) {
            joiner.add(iterator.next());
        }
        System.out.println(joiner);
    }

}