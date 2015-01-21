package pl.kwojtas.cormenimpl.util;

public class List<T> {

    public class Node {
        public T key;
        public Node prev;
        public Node next;
    }

    public Node head;

}
