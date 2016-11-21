package pl.kwojtas.cormenimpl.datastructure;

import pl.kwojtas.cormenimpl.Chapter12;

/**
 * Implements a binary tree.
 *
 * @param <E> the type of elements in the tree
 */
public class BinaryTree<E> {

    /**
     * Implements a binary tree's node.
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
         * Creates a node with a given key.
         *
         * @param key the key of the new node
         */
        public Node(F key) {
            this.key = key;
        }

        /**
         * Creates a node with a given key and children nodes.
         *
         * @param key   the key of the new node
         * @param left  the left child of the new node
         * @param right the right child of the new node
         */
        public Node(F key, Node<F> left, Node<F> right) {
            this.key = key;
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
     * Creates a binary tree from a root node.
     *
     * @param root the root of the new tree
     */
    public BinaryTree(Node<E> root) {
        this.root = root;
    }

    /**
     * Creates an empty binary tree.
     */
    public static <E> BinaryTree<E> emptyTree() {
        return new BinaryTree<>(null);
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
        if (x == null) {
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
        if (x == null) {
            return Array.emptyArray();
        }
        int n = getSize(x);
        Array<E> array = Array.withLength(n);
        BinaryTree.Node<E> y = Chapter12.treeMinimum(x);
        array.set(1, y.key);
        for (int i = 2; i <= n; i++) {
            y = Chapter12.treeSuccessor(y);
            array.set(i, y.key);
        }
        return array;
    }
}
