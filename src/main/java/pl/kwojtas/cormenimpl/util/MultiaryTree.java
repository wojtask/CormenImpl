package pl.kwojtas.cormenimpl.util;

/**
 * Implements an arbitrary rooted tree using the "left-child, right-sibling representation".
 *
 * @param <T> the type of elements in the arbitrary rooted tree
 */
public class MultiaryTree<T> {

    /**
     * Implements an arbitrary rooted tree's node using the "left-child, right-sibling representation".
     *
     * @param <U> the type of key in the node
     */
    public static class Node<U> {

        /**
         * The key.
         */
        public U key;

        /**
         * The parent node.
         */
        public Node<U> p;

        /**
         * The leftmost child node.
         */
        public Node<U> leftChild;

        /**
         * The next sibling node.
         */
        public Node<U> rightSibling;

        /**
         * Creates a node with a given key.
         *
         * @param key the key of the new node
         */
        public Node(U key) {
            this.key = key;
        }
    }

    /**
     * The root of the tree.
     */
    public Node<T> root;

}
