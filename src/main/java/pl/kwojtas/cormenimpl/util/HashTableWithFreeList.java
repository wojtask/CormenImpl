package pl.kwojtas.cormenimpl.util;

public class HashTableWithFreeList<T> {

    public class Node {
        public Element<T> element;
        public Integer next;
        public Integer prev;

        public Node(Element<T> element, Integer next, Integer prev) {
            this.element = element;
            this.next = next;
            this.prev = prev;
        }

        public Node() { }
    }

    public ZeroBasedIndexedArray<HashTableWithFreeList.Node> T;
    public Integer free;
    public int length;

    private HashTableWithFreeList(int length) {
        T = ZeroBasedIndexedArray.withLength(length);
        for (int i = 0; i <= length - 1; i++) {
            T.set(i, new Node());
        }
        free = 0;
        this.length = length;
    }

    public static <T> HashTableWithFreeList<T> withLength(int length) {
        return new HashTableWithFreeList<>(length);
    }

    public Node at(int position) {
        return T.at(position);
    }

}
