package pl.kwojtas.cormenimpl.datastructure;

import pl.kwojtas.cormenimpl.Chapter13;

/**
 * Implements a red-black tree.
 *
 * @param <E> the type of elements in the tree
 */
public class RedBlackTree<E> {

    /**
     * Defines colors of red-black tree's nodes
     */
    public enum Color {
        RED, BLACK
    }

    /**
     * Implements a red-black tree's node.
     *
     * @param <F> the type of the node's key
     */
    public static class Node<F> {

        /**
         * The key.
         */
        public F key;

        /**
         * The parent node.
         */
        public Node<F> p;

        /**
         * The left child node.
         */
        public Node<F> left;

        /**
         * The right child node.
         */
        public Node<F> right;

        /**
         * The color.
         */
        public Color color;

        /**
         * Creates a node with a given key and a given color in a given tree.
         *
         * @param key   the key of the new node
         * @param color the color of the new node
         * @param tree  the tree where the new node belongs to
         */
        public Node(F key, Color color, RedBlackTree<F> tree) {
            this.key = key;
            this.color = color;
            this.left = this.right = this.p = tree.nil;
        }

        private Node() {
            this.color = Color.BLACK;
            this.left = this.right = this.p = this;
        }
    }

    /**
     * The root of the tree.
     */
    public Node<E> root;

    /**
     * The nil sentinel.
     */
    public Node<E> nil;

    /**
     * Creates an empty red-black tree.
     */
    public RedBlackTree() {
        root = nil = new Node<>();
    }

    /**
     * Returns the number of elements in the tree.
     *
     * @return the number of elements in the tree
     */
    public int getSize() {
        return getSize(root);
    }

    private int getSize(Node<E> x) {
        if (x == nil) {
            return 0;
        }
        return 1 + getSize(x.left) + getSize(x.right);
    }

    /**
     * Transforms the tree to an array.
     *
     * @return the sorted array containing all the elements in the tree
     */
    public Array<E> toArray() {
        return toArray(root);
    }

    /**
     * Transforms the subtree to an array.
     *
     * @param x the root of the subtree
     * @return the sorted array containing all the elements in the subtree rooted in {@code x}
     */
    public Array<E> toArray(Node<E> x) {
        if (x == nil) {
            return new Array<>();
        }
        int n = getSize(x);
        Array<E> array = Array.withLength(n);
        RedBlackTree.Node<E> y = Chapter13.rbTreeMinimum(this, x);
        array.set(1, y.key);
        for (int i = 2; i <= n; i++) {
            y = Chapter13.rbTreeSuccessor(this, y);
            array.set(i, y.key);
        }
        return array;
    }

}
