package com.global.commtech.test.anagramfinder.resolvers.tree;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;


public class SimpleCharBufferTest {

    @Test
    void testAddAndViewSingleValue() {
        SimpleCharBuffer values = new SimpleCharBuffer(10, 100);
        values.add("hello".toCharArray());

        assertEquals(1, values.getSize());
        assertEquals("hello", values.view(0).toString());
    }

    @Test
    void testAddMultipleValues() {
        SimpleCharBuffer values = new SimpleCharBuffer(2, 20);

        values.add("one".toCharArray());
        values.add("two".toCharArray());
        values.add("three".toCharArray());

        assertEquals(3, values.getSize());
        assertEquals("one", values.view(0).toString());
        assertEquals("two", values.view(1).toString());
        assertEquals("three", values.view(2).toString());
    }

    @Test
    void testDynamicResizingBuffer() {
        SimpleCharBuffer values = new SimpleCharBuffer(2, 5); // small buffer to force resize
        values.add("abc".toCharArray());
        values.add("defg".toCharArray());
        values.add("hijkl".toCharArray()); // should trigger resize

        assertEquals("abc", values.view(0).toString());
        assertEquals("defg", values.view(1).toString());
        assertEquals("hijkl", values.view(2).toString());
    }

    @Test
    void testDynamicResizingIndexArrays() {
        SimpleCharBuffer values = new SimpleCharBuffer(1, 50); // small index capacity to force resize
        values.add("a".toCharArray());
        values.add("b".toCharArray());
        values.add("c".toCharArray());

        assertEquals(3, values.getSize());
        assertEquals("a", values.view(0).toString());
        assertEquals("b", values.view(1).toString());
        assertEquals("c", values.view(2).toString());
    }

}
