package pl.kwojtas.cormenimpl.datastructure;

import pl.kwojtas.cormenimpl.Chapter13;

/**
 * Implements a red-black tree.
 *
 * @param <E> the type of elements in the tree
 */
public class RedBlackTree<E> {

    /**
     * Defines colors of red-black tree's nodes.
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
         */
        public Node(F key, Color color) {
            this.key = key;
            this.color = color;
        }

        /**
         * Creates a node with a given key, color and children nodes.
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
     * The nil sentinel.
     */
    public Node<E> nil;

    private RedBlackTree() {
        this.root = this.nil = new Node<>(null, Color.BLACK);
    }

    /**
     * Creates an empty red-black tree.
     */
    public static <E> RedBlackTree<E> emptyTree() {
        return new RedBlackTree<>();
    }

    /**
     * Creates a red-black tree from a root node.
     *
     * @param root the root of the new tree
     */
    public RedBlackTree(Node<E> root) {
        this.root = root;
        this.nil = root.p = new Node<>(null, Color.BLACK);
        assignSentinelToEmptyChildren(root);
    }

    private void assignSentinelToEmptyChildren(Node<E> node) {
        if (node.left == null) {
            node.left = nil;
        } else {
            assignSentinelToEmptyChildren(node.left);
        }
        if (node.right == null) {
            node.right = nil;
        } else {
            assignSentinelToEmptyChildren(node.right);
        }
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
            return Array.emptyArray();
        }
        int n = getSize(x);
        Array<E> array = Array.withLength(n);
        Node<E> y = Chapter13.rbTreeMinimum(this, x);
        array.set(1, y.key);
        for (int i = 2; i <= n; i++) {
            y = Chapter13.rbTreeSuccessor(this, y);
            array.set(i, y.key);
        }
        return array;
    }

}
