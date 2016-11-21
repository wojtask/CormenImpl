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

        /**
         * Creates a node with a given key and children nodes.
         *
         * @param key      the key of the new node
         * @param children the children of the new node
         */
        @SafeVarargs
        public Node(F key, Node<F>... children) {
            this.key = key;
            this.leftChild = children[0];
            children[0].p = this;
            for (int i = 1; i < children.length; i++) {
                children[i].p = this;
                children[i - 1].rightSibling = children[i];
            }
        }
    }

    /**
     * The root of the tree.
     */
    public Node<E> root;

    /**
     * Creates an arbitrary rooted tree from a root node.
     *
     * @param root the root of the new tree
     */
    public MultiaryTree(Node<E> root) {
        this.root = root;
    }

}
