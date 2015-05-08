package pl.kwojtas.cormenimpl.util;

/**
 * Implements a binary tree.
 *
 * @param <T> the type of elements in the binary tree
 */
public class BinaryTree<T> {

    /**
     * Implements a binary tree's node.
     *
     * @param <U> the type of the node's key
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
         * The left child node.
         */
        public Node<U> left;

        /**
         * The right child node.
         */
        public Node<U> right;

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

    /**
     * Returns the number of elements in the tree.
     *
     * @return the number of elements in the tree
     */
    public int getSize() {
        return getSize(root);
    }

    private int getSize(Node<T> x) {
        if (x == null) {
            return 0;
        }
        return 1 + getSize(x.left) + getSize(x.right);
    }

}
