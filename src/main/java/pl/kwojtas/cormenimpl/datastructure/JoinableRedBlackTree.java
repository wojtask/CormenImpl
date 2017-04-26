package pl.kwojtas.cormenimpl.datastructure;

import pl.kwojtas.cormenimpl.datastructure.RedBlackTree.Color;

/**
 * Implements a red-black tree.
 *
 * @param <E> the type of elements in the tree
 */
public class JoinableRedBlackTree<E> {

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
         * Creates a node with given key and color.
         *
         * @param key   the key of the new node
         * @param color the color of the new node
         */
        public Node(F key, Color color) {
            this.key = key;
            this.color = color;
        }

        /**
         * Creates a node with given key, color and children nodes.
         *
         * @param key   the key of the new node
         * @param color the color of the new node
         * @param left  the left child of the new node
         * @param right the right child of the new node
         */
        public Node(F key, Color color, Node<F> left, Node<F> right) {
            this.key = key;
            this.color = color;
            if (left != null) {
                this.left = left;
                left.p = this;
            }
            if (right != null) {
                this.right = right;
                right.p = this;
            }
        }

    }

    /**
     * The root of the tree.
     */
    public Node<E> root;

    /**
     * The black height.
     */
    public int bh;

    private JoinableRedBlackTree() {
    }

    /**
     * Creates an empty red-black tree.
     */
    public static <E> JoinableRedBlackTree<E> emptyTree() {
        return new JoinableRedBlackTree<>();
    }

    /**
     * Creates a red-black tree from a root node.
     *
     * @param root the root of the new tree
     */
    public JoinableRedBlackTree(Node<E> root) {
        this.root = root;
        this.bh = calculateBlackHeight(root);
    }

    private int calculateBlackHeight(Node<E> node) {
        int blackHeight = 0;
        while (node != null) {
            if (node.color == Color.BLACK) {
                blackHeight++;
            }
            // it doesn't matter which path we choose as long as the tree fulfills property 5
            node = node.left;
        }
        return blackHeight;
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
     * @return the sorted array containing all the elements in the subtree rooted at {@code x}
     */
    public Array<E> toArray(Node<E> x) {
        if (x == null) {
            return Array.emptyArray();
        }
        Array<E> leftArray = toArray(x.left);
        Array<E> rightArray = toArray(x.right);
        Array<E> array = Array.ofLength(leftArray.length + 1 + rightArray.length);
        for (int i = 1; i <= leftArray.length; i++) {
            array.set(i, leftArray.at(i));
        }
        array.set(leftArray.length + 1, x.key);
        for (int i = 1; i <= rightArray.length; i++) {
            array.set(leftArray.length + 1 + i, rightArray.at(i));
        }
        return array;
    }

}
