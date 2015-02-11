package pl.kwojtas.cormenimpl.util;

public class BinaryTree<T> {

    public static class Node<U> {
        public U key;
        public Node<U> p;
        public Node<U> left;
        public Node<U> right;

        public Node(U key) {
            this.key = key;
        }
    }

    public Node<T> root;

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
