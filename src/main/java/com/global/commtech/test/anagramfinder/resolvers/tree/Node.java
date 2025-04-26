package com.global.commtech.test.anagramfinder.resolvers.tree;

import java.util.Iterator;

class Node implements Iterator<CharSequence> {

    public static final Node ROOT = new Node('#');

    private static final int FACTOR = 10;

    final char character;

    Node child;
    Node sibling;

    private SimpleCharBuffer buffer;
    private int iteratorIndex;

    public Node(char character) {
        this.character = character;
    }

    public void addValue(char[] value) {
        if (buffer == null) {
            buffer = new SimpleCharBuffer(FACTOR, value.length * FACTOR);
        }

        buffer.add(value);
    }

    @Override
    public boolean hasNext() {
        return buffer != null && iteratorIndex < buffer.getSize();
    }

    @Override
    public CharSequence next() {
        if (buffer == null) {
            throw new IllegalStateException("No buffer");
        }

        return buffer.view(iteratorIndex++);
    }

}
