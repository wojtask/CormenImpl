package pl.kwojtas.cormenimpl.util;

public class XorList<T> {

    public static class Node<U> {
        public U key;
        public byte np;
    }

    public Node<T> head;
    public Node<T> tail;

}
