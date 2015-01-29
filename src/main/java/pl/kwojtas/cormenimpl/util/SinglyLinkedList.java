package pl.kwojtas.cormenimpl.util;

public class SinglyLinkedList<T> {

    public class Node {
        public T key;
        public Node next;

        public Node(T key) {
            this.key = key;
        }
    }

    public Node head;

    @SafeVarargs
    public SinglyLinkedList(T... elements) {
        if (elements.length == 0) {
            return;
        }
        head = new Node(elements[0]);
        Node x = head;
        for (int i = 1; i < elements.length; i++) {
            Node y = new Node(elements[i]);
            x.next = y;
            x = y;
        }
    }

    public SinglyLinkedList(SinglyLinkedList<T> otherList) {
        if (otherList.head == null) {
            return;
        }
        head = new Node(otherList.head.key);
        Node x = head;
        Node z = otherList.head.next;
        while (z != null) {
            Node y = new Node(z.key);
            x.next = y;
            x = y;
            z = z.next;
        }
    }

    public int getLength() {
        int length = 0;
        Node x = head;
        while (x != null) {
            length++;
            x = x.next;
        }
        return length;
    }

    public Array<T> toArray() {
        Array<T> array = Array.withLength(getLength());
        Node x = head;
        int i = 1;
        while (x != null) {
            array.set(i, x.key);
            i++;
            x = x.next;
        }
        return array;
    }

}
