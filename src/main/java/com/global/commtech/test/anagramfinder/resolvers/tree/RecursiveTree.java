package com.global.commtech.test.anagramfinder.resolvers.tree;

import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Consumer;

public class RecursiveTree implements Tree {

    private final Node root = Node.ROOT;

    @Override
    public void add(char[] value) {
        char[] index = Arrays.copyOf(value, value.length);
        Arrays.sort(index);

        NodeIndex furthestParent = findFurthestParent(root, 0, index);
        Node leaf = buildLeaf(furthestParent.parent, furthestParent.position, index);

        leaf.addValue(value);
    }

    private NodeIndex findFurthestParent(Node parent, int position, char[] index) {
        if (position == index.length) {
            return NodeIndex.of(parent, position);
        }

        Node child = searchChildren(parent.child, index[position]);

        if (child == null) {
            return NodeIndex.of(parent, position);
        }

        return findFurthestParent(child, position + 1, index);
    }

    private Node searchChildren(Node child, char character) {
        if (child == null) {
            return null;
        }

        if (child.character == character) {
            return child;
        }

        return searchChildren(child.sibling, character);
    }

    private Node buildLeaf(Node parent, int position, char[] index) {
        if (position == index.length) {
            return parent;
        }

        Node child = new Node(index[position]);
        addNewChild(parent, child);

        return buildLeaf(child, position + 1, index);
    }

    private void addNewChild(Node parent, Node child) {
        Node sibling = parent.child;

        if (sibling != null) {
            parent.child = child;
            child.sibling = sibling;
        } else {
            parent.child = child;
        }
    }

    @Override
    public void walk(Consumer<Iterator<CharSequence>> consumer) {
        walkInternal(root.child, consumer);
    }

    private void walkInternal(Node node, Consumer<Iterator<CharSequence>> consumer) {
        if (node == null) {
            return;
        }

        if (node.hasNext()) {
            consumer.accept(node);
        }

        walkInternal(node.child, consumer);
        walkInternal(node.sibling, consumer);
    }

    private record NodeIndex(Node parent, int position) {

        public static NodeIndex of(Node parent, int index) {
            return new NodeIndex(parent, index);
        }

    }

}
