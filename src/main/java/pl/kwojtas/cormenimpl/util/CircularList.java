package pl.kwojtas.cormenimpl.util;

public class CircularList<T> extends SinglyLinkedList<T> {

    @SafeVarargs
    public CircularList(T... elements) {
        if (elements.length == 0) {
            return;
        }
        head = new Node(elements[0]);
        head.next = head;
        Node x = head;
        for (int i = 1; i < elements.length; i++) {
            Node y = new Node(elements[i]);
            y.next = head;
            x.next = y;
            x = y;
        }
    }

    public CircularList(CircularList<T> otherList) {
        if (otherList.head == null) {
            return;
        }
        head = new Node(otherList.head.key);
        head.next = head;
        Node x = head;
        Node z = otherList.head.next;
        while (z != otherList.head) {
            Node y = new Node(z.key);
            x.next = y;
            y.next = head;
            x = y;
            z = z.next;
        }
    }

}
