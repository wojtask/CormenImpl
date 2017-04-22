package pl.kwojtas.cormenimpl.datastructure;

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
        this.nil.left = this.nil.right = this.nil.p = this.nil;
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
        this.nil.left = this.nil.right = this.nil.p = this.nil;
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
