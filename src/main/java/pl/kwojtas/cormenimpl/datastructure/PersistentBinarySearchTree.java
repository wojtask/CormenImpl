package pl.kwojtas.cormenimpl.datastructure;

import pl.kwojtas.cormenimpl.Chapter10;

/**
 * Implements a persistent dynamic set as a binary search tree.
 *
 * @param <E> the type of elements in the tree
 */
public class PersistentBinarySearchTree<E> {

    /**
     * Implements a persistent binary search tree's node.
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
         * Creates a node with a given key.
         *
         * @param key the key of the new node
         */
        public Node(F key) {
            this.key = key;
        }

        /**
         * Creates a node with given key and children nodes.
         *
         * @param key   the key of the new node
         * @param left  the left child of the new node
         * @param right the right child of the new node
         */
        public Node(F key, Node<F> left, Node<F> right) {
            this.key = key;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * The list of roots of the tree from the oldest to the newest.
     */
    public SinglyLinkedListWithTail<Node<E>> roots;

    /**
     * Creates a binary tree from a root node.
     *
     * @param root the root of the new tree
     */
    public PersistentBinarySearchTree(Node<E> root) {
        this.roots = SinglyLinkedListWithTail.emptyList();
        Chapter10.singlyLinkedListEnqueue(this.roots, root);
    }

    /**
     * Creates an empty persistent binary search tree.
     */
    public static <E> PersistentBinarySearchTree<E> emptyTree() {
        return new PersistentBinarySearchTree<>(null);
    }

    /**
     * Transforms a subtree to an array.
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
