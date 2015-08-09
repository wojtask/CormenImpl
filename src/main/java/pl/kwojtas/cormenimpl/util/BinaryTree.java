package pl.kwojtas.cormenimpl.util;

/**
 * Implements a binary tree.
 *
 * @param <E> the type of elements in the binary tree
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
    }

    /**
     * The root of the tree.
     */
    public Node<E> root;

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

}
