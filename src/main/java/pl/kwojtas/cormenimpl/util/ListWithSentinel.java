package pl.kwojtas.cormenimpl.util;

public class ListWithSentinel<T> {

    public static class Node<U> {
        public U key;
        public Node<U> prev;
        public Node<U> next;

        public Node(U key) {
            this.key = key;
        }
    }

    public Node<T> nil;

    @SafeVarargs
    public ListWithSentinel(T... elements) {
        nil = new Node<>(null);
        nil.prev = nil.next = nil;
        if (elements.length == 0) {
            return;
        }
        Node<T> x = nil;
        for (T element : elements) {
            Node<T> y = new Node<>(element);
            y.prev = x;
            x.next = y;
            y.next = nil;
            nil.prev = y;
            x = y;
        }
    }

    public ListWithSentinel(ListWithSentinel<T> otherList) {
        nil = new Node<>(null);
        nil.prev = nil.next = nil;
        Node<T> x = nil;
        Node<T> z = otherList.nil.next;
        while (z != otherList.nil) {
            Node<T> y = new Node<>(z.key);
            y.prev = x;
            x.next = y;
            y.next = nil;
            nil.prev = y;
            x = y;
            z = z.next;
        }
    }

    public int getLength() {
        int length = 0;
        Node<T> x = nil.next;
        while (x != nil) {
            length++;
            x = x.next;
        }
        return length;
    }

    public Array<T> toArray() {
        Array<T> array = Array.withLength(getLength());
        Node<T> x = nil.next;
        int i = 1;
        while (x != nil) {
            array.set(i, x.key);
            i++;
            x = x.next;
        }
        return array;
    }

}
