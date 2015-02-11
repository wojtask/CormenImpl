package pl.kwojtas.cormenimpl.util;

public class MultiaryTree<T> {

    public static class Node<U> {
        public U key;
        public Node<U> p;
        public Node<U> leftChild;
        public Node<U> rightSibling;

        public Node(U key) {
            this.key = key;
        }
    }

    public Node<T> root;

}
