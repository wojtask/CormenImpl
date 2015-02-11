package pl.kwojtas.cormenimpl.util;

public class List<T> {

    public static class Node<U> {
        public U key;
        public Node<U> prev;
        public Node<U> next;

        public Node(U key) {
            this.key = key;
        }
    }

    public Node<T> head;

    @SafeVarargs
    public List(T... elements) {
        if (elements.length == 0) {
            return;
        }
        head = new Node<>(elements[0]);
        Node<T> x = head;
        for (int i = 1; i < elements.length; i++) {
            Node<T> y = new Node<>(elements[i]);
            y.prev = x;
            x.next = y;
            x = y;
        }
    }

    public List(List<T> otherList) {
        if (otherList.head == null) {
            return;
        }
        head = new Node<>(otherList.head.key);
        Node<T> x = head;
        Node<T> z = otherList.head.next;
        while (z != null) {
            Node<T> y = new Node<>(z.key);
            y.prev = x;
            x.next = y;
            x = y;
            z = z.next;
        }
    }

    public int getLength() {
        int length = 0;
        Node<T> x = head;
        while (x != null) {
            length++;
            x = x.next;
        }
        return length;
    }

    public Array<T> toArray() {
        Array<T> array = Array.withLength(getLength());
        Node<T> x = head;
        int i = 1;
        while (x != null) {
            array.set(i, x.key);
            i++;
            x = x.next;
        }
        return array;
    }

}
