package pl.kwojtas.cormenimpl.util;

public class BinaryTree<T> {

    public class Node {
        public T key;
        public Node p;
        public Node left;
        public Node right;

        public Node(T key) {
            this.key = key;
        }
    }

    public Node root;

    public int getSize() {
        return getSize(root);
    }

    private int getSize(Node x) {
        if (x == null) {
            return 0;
        }
        return 1 + getSize(x.left) + getSize(x.right);
    }

}
