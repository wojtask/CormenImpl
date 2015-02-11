package pl.kwojtas.cormenimpl.util;

public class SinglyLinkedListWithTail<T> extends SinglyLinkedList<T> {

    public SinglyLinkedListWithTail.Node<T> tail;

    @SafeVarargs
    public SinglyLinkedListWithTail(T... elements) {
        if (elements.length == 0) {
            return;
        }
        head = tail = new Node<>(elements[0]);
        Node<T> x = head;
        for (int i = 1; i < elements.length; i++) {
            Node<T> y = new Node<>(elements[i]);
            x.next = tail = y;
            x = y;
        }
    }

    public SinglyLinkedListWithTail(SinglyLinkedList<T> otherList) {
        if (otherList.head == null) {
            return;
        }
        head = tail = new Node<>(otherList.head.key);
        Node<T> x = head;
        Node<T> z = otherList.head.next;
        while (z != null) {
            Node<T> y = new Node<>(z.key);
            x.next = tail = y;
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
