package com.global.commtech.test.anagramfinder.resolvers.tree;

import java.nio.CharBuffer;

import org.springframework.util.Assert;

class SimpleCharBuffer {

    private char[] buffer;
    private int[] offsets;
    private int[] lengths;
    private int currentPosition = 0;
    private int currentIndex = 0;

    public SimpleCharBuffer(int capacity, int totalCharCapacity) {
        this.buffer = new char[totalCharCapacity];
        this.offsets = new int[capacity];
        this.lengths = new int[capacity];
    }

    public void add(char[] value) {
        int len = value.length;

        ensureBufferCapacity(len);
        ensureIndexCapacity();

        System.arraycopy(value, 0, buffer, currentPosition, len);
        offsets[currentIndex] = currentPosition;
        lengths[currentIndex] = len;
        currentPosition += len;
        currentIndex++;
    }

    public CharSequence view(int index) {
        Assert.isTrue(index < currentIndex, "Index is out of bounds");
        return CharBuffer.wrap(buffer, offsets[index], lengths[index]);
    }

    public int getSize() {
        return currentIndex;
    }

    private void ensureBufferCapacity(int additionalCapacity) {
        int required = currentPosition + additionalCapacity;
        if (required > buffer.length) {
            int newSize = Math.max(buffer.length * 2, required);
            char[] newArray = new char[newSize];
            System.arraycopy(buffer, 0, newArray, 0, buffer.length);
            buffer = newArray;
        }
    }

    private void ensureIndexCapacity() {
        int required = currentIndex + 1;
        if (required > offsets.length) {
            offsets = resize(offsets);
            lengths = resize(lengths);
        }
    }

    private int[] resize(int[] existing) {
        int newSize = existing.length * 2;
        int[] newArray = new int[newSize];
        System.arraycopy(existing, 0, newArray, 0, existing.length);
        return newArray;
    }

}
