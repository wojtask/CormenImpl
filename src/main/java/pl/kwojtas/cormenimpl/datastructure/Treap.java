package pl.kwojtas.cormenimpl.datastructure;

/**
 * Implements a treap (binary search tree + min heap).
 *
 * @param <E> the type of elements in the treap
 */
public class Treap<E> {

    /**
     * Implements a treap's node.
     *
     * @param <F> the type of the node's key
     */
    public static class Node<F> {

        /**
         * The key.
         */
        public F key;

        /**
         * The priority
         */
        public int priority;

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
         * Creates a node with a given key and priority.
         *
         * @param key      the key of the new node
         * @param priority the priority of the new node
         */
        public Node(F key, int priority) {
            this.key = key;
            this.priority = priority;
        }

        /**
         * Creates a node with given key, priority and children nodes.
         *
         * @param key   the key of the new node
         * @param priority the priority of the new node
         * @param left  the left child of the new node
         * @param right the right child of the new node
         */
        public Node(F key, int priority, Node<F> left, Node<F> right) {
            this.key = key;
            this.priority = priority;
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
     * The root of the treap.
     */
    public Node<E> root;

    /**
     * Creates a treap from a root node.
     *
     * @param root the root of the new treap
     */
    public Treap(Node<E> root) {
        this.root = root;
    }

    /**
     * Creates an empty treap.
     */
    public static <E> Treap<E> emptyTreap() {
        return new Treap<>(null);
    }

    /**
     * Transforms the treap to an array.
     *
     * @return the sorted array containing all the elements in the treap
     */
    public Array<E> toArray() {
        return toArray(root);
    }

    /**
     * Transforms a subtreap to an array.
     *
     * @param x the root of the subtreap
     * @return the sorted array containing all the elements in the subtreap rooted at {@code x}
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
