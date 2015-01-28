package pl.kwojtas.cormenimpl.util;

public class SinglyLinkedListWithTail<T> extends SinglyLinkedList<T> {

    public SinglyLinkedListWithTail<T>.Node tail;

    @SafeVarargs
    public SinglyLinkedListWithTail(T... elements) {
        if (elements.length == 0) {
            return;
        }
        head = tail = new Node(elements[0]);
        Node x = head;
        for (int i = 1; i < elements.length; i++) {
            Node y = new Node(elements[i]);
            x.next = tail = y;
            x = y;
        }
    }

    public SinglyLinkedListWithTail(SinglyLinkedList<T> otherList) {
        if (otherList.head == null) {
            return;
        }
        head = tail = new Node(otherList.head.key);
        Node x = head;
        Node z = otherList.head.next;
        while (z != null) {
            Node y = new Node(z.key);
            x.next = tail = y;
            x = y;
            z = z.next;
        }
    }

    public int getLength() {
        int size = 0;
        Node x = head;
        while (x != null) {
            size++;
            x = x.next;
        }
        return size;
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
