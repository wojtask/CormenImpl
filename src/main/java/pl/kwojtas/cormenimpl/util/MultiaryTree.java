package pl.kwojtas.cormenimpl.util;

public class MultiaryTree<T> {

    public class Node {
        public T key;
        public Node p;
        public Node leftChild;
        public Node rightSibling;
    }

    public Node root;

}
