package pl.kwojtas.cormenimpl.datastructure;

/**
 * Implements a radix tree for bit strings.
 */
public class RadixTree {

    /**
     * Implements a radix tree's node.
     */
    public static class Node {

        /**
         * The flag indicating that the node belongs to the tree ({@code true}),
         * or that it exists only to establish a path to other nodes ({@code false}).
         */
        public boolean inTree;

        /**
         * The left child node.
         */
        public Node left;

        /**
         * The right child node.
         */
        public Node right;
    }

    /**
     * The root of the tree.
     */
    public Node root;

}
