package pl.kwojtas.cormenimpl.datastructure;

import static pl.kwojtas.cormenimpl.datastructure.RedBlackTree.Color;

/**
 * Implements a red-black tree with no parent pointers in nodes.
 *
 * @param <E> the type of elements in the tree
 */
public class ParentlessRedBlackTree<E> {

    /**
     * Implements a parentless red-black tree's node.
     *
     * @param <F> the type of the node's key
     */
    public static class Node<F> {

        /**
         * The key.
         */
        public F key;

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
            this.left = left;
            this.right = right;
        }

        /**
         * Creates a node as a copy of a given node.
         *
         * @param x the node to copy
         */
        public Node(Node<F> x) {
            this.key = x.key;
            this.color = x.color;
            this.left = x.left;
            this.right = x.right;
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

    private ParentlessRedBlackTree() {
        this.root = this.nil = new Node<>(null, Color.BLACK);
        this.nil.left = this.nil.right = this.nil;
    }

    /**
     * Creates an empty parentless red-black tree.
     */
    public static <E> ParentlessRedBlackTree<E> emptyTree() {
        return new ParentlessRedBlackTree<>();
    }

    /**
     * Creates a parentless red-black tree from a root node.
     *
     * @param root the root of the new tree
     */
    public ParentlessRedBlackTree(Node<E> root) {
        this.root = root;
        this.nil = new Node<>(null, Color.BLACK);
        this.nil.left = this.nil.right = this.nil;
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
        if (x == nil) {
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
