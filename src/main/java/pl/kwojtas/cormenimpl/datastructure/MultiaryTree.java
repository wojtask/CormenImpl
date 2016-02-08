package pl.kwojtas.cormenimpl.datastructure;

/**
 * Implements an arbitrary rooted tree using the left-child, right-sibling representation.
 *
 * @param <E> the type of elements in the tree
 */
public class MultiaryTree<E> {

    /**
     * Implements an arbitrary rooted tree's node using the left-child, right-sibling representation.
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
         * The leftmost child node.
         */
        public Node<F> leftChild;

        /**
         * The next sibling node.
         */
        public Node<F> rightSibling;

        /**
         * Creates a node with a given key.
         *
         * @param key the key of the new node
         */
        public Node(F key) {
            this.key = key;
        }
    }

    /**
     * The root of the tree.
     */
    public Node<E> root;

}
