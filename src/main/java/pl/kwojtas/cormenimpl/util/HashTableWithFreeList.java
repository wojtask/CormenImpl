package pl.kwojtas.cormenimpl.util;

public class HashTableWithFreeList<T> extends ZeroBasedIndexedArray<HashTableWithFreeList<T>.Node> {

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

    public Integer free;
    public HashFunction h;

    private HashTableWithFreeList(int length, HashFunction h) {
        super(ZeroBasedIndexedArray.withLength(length));
        for (int i = 0; i <= length - 1; i++) {
            set(i, new Node());
        }
        free = 0;
        this.length = length;
        this.h = h;
    }

    public static <T> HashTableWithFreeList<T> withLengthAndHashFunction(int length, HashFunction h) {
        return new HashTableWithFreeList<>(length, h);
    }

}
