package pl.kwojtas.cormenimpl.util;

public class ListWithSentinel<T> {

    public class Node {
        public T key;
        public Node prev;
        public Node next;

        public Node(T key) {
            this.key = key;
        }
    }

    public Node nil;

    @SafeVarargs
    public ListWithSentinel(T... elements) {
        nil = new Node(null);
        nil.prev = nil.next = nil;
        if (elements.length == 0) {
            return;
        }
        Node x = nil;
        for (int i = 0; i < elements.length; i++) {
            Node y = new Node(elements[i]);
            y.prev = x;
            x.next = y;
            y.next = nil;
            nil.prev = y;
            x = y;
        }
    }

    public ListWithSentinel(ListWithSentinel<T> otherList) {
        nil = new Node(null);
        nil.prev = nil.next = nil;
        Node x = nil;
        Node z = otherList.nil.next;
        while (z != otherList.nil) {
            Node y = new Node(z.key);
            y.prev = x;
            x.next = y;
            y.next = nil;
            nil.prev = y;
            x = y;
            z = z.next;
        }
    }

}
