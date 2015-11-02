package pl.kwojtas.cormenimpl;

import pl.kwojtas.cormenimpl.util.Array;
import pl.kwojtas.cormenimpl.util.Heap;
import pl.kwojtas.cormenimpl.util.Pair;
import pl.kwojtas.cormenimpl.util.PriorityQueueWithRanks;
import pl.kwojtas.cormenimpl.util.SinglyLinkedList;
import pl.kwojtas.cormenimpl.util.Young;

import static java.lang.Math.max;
import static java.lang.Math.sqrt;
import static pl.kwojtas.cormenimpl.util.Util.ceil;
import static pl.kwojtas.cormenimpl.util.Util.greater;
import static pl.kwojtas.cormenimpl.util.Util.less;

/**
 * Implements algorithms and data structures from Chapter 6.
 */
public final class Chapter6 {

    private Chapter6() {
    }

    /**
     * Returns the parent index in a heap.
     * <p><span style="font-variant:small-caps;">Parent</span> from subchapter 6.1.</p>
     *
     * @param i the index of a node
     * @return the parent index of the node of index {@code i}
     */
    static int parent(int i) {
        return i / 2;
    }

    /**
     * Returns the left child index in a heap.
     * <p><span style="font-variant:small-caps;">Left</span> from subchapter 6.1.</p>
     *
     * @param i the index of a node
     * @return the left child index of the node of index {@code i}
     */
    static int left(int i) {
        return 2 * i;
    }

    /**
     * Returns the right child index in a heap.
     * <p><span style="font-variant:small-caps;">Right</span> from subchapter 6.1.</p>
     *
     * @param i the index of a node
     * @return the right child index of the node of index {@code i}
     */
    static int right(int i) {
        return 2 * i + 1;
    }

    /**
     * Restores the max-heap property.
     * <p><span style="font-variant:small-caps;">Max-Heapify</span> from subchapter 6.2.</p>
     *
     * @param A   the heap with max-heap property violated at one node
     * @param i   the index of the node in {@code A} the property is violated at
     * @param <E> the type of elements in {@code A}
     */
    static <E extends Comparable<? super E>> void maxHeapify(Heap<E> A, int i) {
        int l = left(i);
        int r = right(i);
        int largest;
        if (l <= A.heapSize && greater(A.at(l), A.at(i))) {
            largest = l;
        } else {
            largest = i;
        }
        if (r <= A.heapSize && greater(A.at(r), A.at(largest))) {
            largest = r;
        }
        if (largest != i) {
            A.exch(i, largest);
            maxHeapify(A, largest);
        }
    }

    /**
     * Constructs a max-heap from an array of elements.
     * <p><span style="font-variant:small-caps;">Build-Max-Heap</span> from subchapter 6.3.</p>
     *
     * @param A   the array of elements
     * @param <E> the type of elements in {@code A}
     * @return a max-heap constructed from elements from {@code A}
     */
    static <E extends Comparable<? super E>> Heap<E> buildMaxHeap(Array<E> A) {
        Heap<E> H = new Heap<>(A);
        H.heapSize = H.length;
        for (int i = H.length / 2; i >= 1; i--) {
            maxHeapify(H, i);
        }
        return H;
    }

    /**
     * Sorts elements using heap sort.
     * <p><span style="font-variant:small-caps;">Heapsort</span> from subchapter 6.4.</p>
     *
     * @param A   the array of elements to sort
     * @param <E> the type of elements in {@code A}
     */
    public static <E extends Comparable<? super E>> void heapsort(Array<E> A) {
        Heap<E> H = buildMaxHeap(A);
        for (int i = H.length; i >= 2; i--) {
            H.exch(1, i);
            H.heapSize--;
            maxHeapify(H, 1);
        }
        A.set(H);
    }

    /**
     * Restores the min-heap property.
     * <p><span style="font-variant:small-caps;">Min-Heapify</span> from solution to exercise 6.2-2.</p>
     *
     * @param A   the heap with min-heap property violated at one node
     * @param i   the index of the node in {@code A} the property is violated at
     * @param <E> the type of elements in {@code A}
     */
    public static <E extends Comparable<? super E>> void minHeapify(Heap<E> A, int i) {
        int l = left(i);
        int r = right(i);
        int smallest;
        if (l <= A.heapSize && less(A.at(l), A.at(i))) {
            smallest = l;
        } else {
            smallest = i;
        }
        if (r <= A.heapSize && less(A.at(r), A.at(smallest))) {
            smallest = r;
        }
        if (smallest != i) {
            A.exch(i, smallest);
            minHeapify(A, smallest);
        }
    }

    /**
     * Restores the max-heap property iteratively.
     * <p><span style="font-variant:small-caps;">Iterative-Max-Heapify</span> from solution to exercise 6.2-5.</p>
     *
     * @param A   the heap with max-heap property violated at one node
     * @param i   the index of the node in {@code A} the property is violated at
     * @param <E> the type of elements in {@code A}
     */
    public static <E extends Comparable<? super E>> void iterativeMaxHeapify(Heap<E> A, int i) {
        while (true) {
            int l = left(i);
            int r = right(i);
            int largest;
            if (l <= A.heapSize && greater(A.at(l), A.at(i))) {
                largest = l;
            } else {
                largest = i;
            }
            if (r <= A.heapSize && greater(A.at(r), A.at(largest))) {
                largest = r;
            }
            if (largest == i) {
                return;
            }
            A.exch(i, largest);
            i = largest;
        }
    }

    /**
     * Returns the largest element in a max-heap.
     * <p><span style="font-variant:small-caps;">Heap-Maximum</span> from subchapter 6.5.</p>
     *
     * @param A the max-heap
     * @return the largest element in {@code A}
     */
    public static int heapMaximum(Heap<Integer> A) {
        return A.at(1);
    }

    /**
     * Removes the largest element from a max-heap.
     * <p><span style="font-variant:small-caps;">Heap-Extract-Max</span> from subchapter 6.5.</p>
     *
     * @param A the max-heap
     * @return the largest element in {@code A}
     * @throws RuntimeException if {@code A} is empty
     */
    public static int heapExtractMax(Heap<Integer> A) {
        if (A.heapSize < 1) {
            throw new RuntimeException("heap underflow");
        }
        int max = A.at(1);
        A.set(1, A.at(A.heapSize));
        A.heapSize--;
        maxHeapify(A, 1);
        return max;
    }

    /**
     * Increases a key in a max-heap.
     * <p><span style="font-variant:small-caps;">Heap-Increase-Key</span> from subchapter 6.5.</p>
     *
     * @param A   the max-heap
     * @param i   the index of the key in {@code A} to be increased
     * @param key the new key
     * @throws RuntimeException if {@code key} is smaller than {@code A[i]}
     */
    public static void heapIncreaseKey(Heap<Integer> A, int i, int key) {
        if (key < A.at(i)) {
            throw new RuntimeException("new key is smaller than current key");
        }
        A.set(i, key);
        while (i > 1 && A.at(parent(i)) < A.at(i)) {
            A.exch(i, parent(i));
            i = parent(i);
        }
    }

    /**
     * Inserts a node into a max-heap.
     * <p><span style="font-variant:small-caps;">Max-Heap-Insert</span> from subchapter 6.5.</p>
     *
     * @param A   the max-heap
     * @param key the key of the new node
     */
    public static void maxHeapInsert(Heap<Integer> A, int key) {
        A.heapSize++;
        A.set(A.heapSize, Integer.MIN_VALUE);
        heapIncreaseKey(A, A.heapSize, key);
    }

    /**
     * Returns the smallest element in a min-heap.
     * <p><span style="font-variant:small-caps;">Heap-Minimum</span> from solution to exercise 6.5-3.</p>
     *
     * @param A the min-heap
     * @return the smallest element in {@code A}
     */
    public static int heapMinimum(Heap<Integer> A) {
        return A.at(1);
    }

    /**
     * Removes the smallest element from a min-heap.
     * <p><span style="font-variant:small-caps;">Heap-Extract-Min</span> from solution to exercise 6.5-3.</p>
     *
     * @param A the min-heap
     * @return the smallest element in {@code A}
     * @throws RuntimeException if {@code A} is empty
     */
    public static int heapExtractMin(Heap<Integer> A) {
        if (A.heapSize < 1) {
            throw new RuntimeException("heap underflow");
        }
        int min = A.at(1);
        A.set(1, A.at(A.heapSize));
        A.heapSize--;
        minHeapify(A, 1);
        return min;
    }

    /**
     * Decreases a key in a min-heap.
     * <p><span style="font-variant:small-caps;">Heap-Decrease-Key</span> from solution to exercise 6.5-3.</p>
     *
     * @param A   the min-heap
     * @param i   the index of the key in {@code A} to be decreased
     * @param key the new key
     * @throws RuntimeException if {@code key} is larger than {@code A[i]}
     */
    public static void heapDecreaseKey(Heap<Integer> A, int i, int key) {
        if (key > A.at(i)) {
            throw new RuntimeException("new key is larger than current key");
        }
        A.set(i, key);
        while (i > 1 && A.at(parent(i)) > A.at(i)) {
            A.exch(i, parent(i));
            i = parent(i);
        }
    }

    /**
     * Inserts a node into a min-heap.
     * <p><span style="font-variant:small-caps;">Min-Heap-Insert</span> from solution to exercise 6.5-3.</p>
     *
     * @param A   the min-heap
     * @param key the key of the new node
     */
    public static void minHeapInsert(Heap<Integer> A, int key) {
        A.heapSize++;
        A.set(A.heapSize, Integer.MAX_VALUE);
        heapDecreaseKey(A, A.heapSize, key);
    }

    /**
     * Implements the queue operation <span style="font-variant:small-caps;">Enqueue</span>
     * using min-priority queue with ranked elements.
     * <p>Solution to exercise 6.5-6.</p>
     *
     * @param priorityQueue the min-priority queue with ranked elements
     * @param key           the key to insert to the queue
     * @param <E>           the type of keys in {@code priorityQueue}
     */
    public static <E> void enqueueUsingPriorityQueue(PriorityQueueWithRanks<E> priorityQueue, E key) {
        int rank = priorityQueue.getCurrentRank();
        PriorityQueueWithRanks.KeyWithRank<E> keyWithRank = new PriorityQueueWithRanks.KeyWithRank<>(key, rank);
        minHeapInsertWithRanks(priorityQueue, keyWithRank);
    }

    private static <E> void minHeapInsertWithRanks(PriorityQueueWithRanks<E> priorityQueue, PriorityQueueWithRanks.KeyWithRank<E> keyWithRank) {
        priorityQueue.heapSize++;
        priorityQueue.set(priorityQueue.heapSize, keyWithRank);
    }

    /**
     * Implements the queue operation <span style="font-variant:small-caps;">Dequeue</span>
     * using min-priority queue with ranked elements.
     * <p>Solution to exercise 6.5-6.</p>
     *
     * @param priorityQueue the min-priority queue with ranked elements
     * @param <E>           the type of keys in {@code priorityQueue}
     * @return the key removed from the queue
     */
    public static <E> E dequeueUsingPriorityQueue(PriorityQueueWithRanks<E> priorityQueue) {
        return heapExtractMinWithRanks(priorityQueue).key;
    }

    private static <E> PriorityQueueWithRanks.KeyWithRank<E> heapExtractMinWithRanks(PriorityQueueWithRanks<E> priorityQueue) {
        PriorityQueueWithRanks.KeyWithRank<E> min = priorityQueue.at(1);
        priorityQueue.set(1, priorityQueue.at(priorityQueue.heapSize));
        priorityQueue.heapSize--;
        minHeapifyWithRanks(priorityQueue, 1);
        return min;
    }

    private static <E> void minHeapifyWithRanks(PriorityQueueWithRanks<E> priorityQueue, int i) {
        int l = left(i);
        int r = right(i);
        int smallest;
        if (l <= priorityQueue.heapSize && priorityQueue.at(l).rank < priorityQueue.at(i).rank) {
            smallest = l;
        } else {
            smallest = i;
        }
        if (r <= priorityQueue.heapSize && priorityQueue.at(r).rank < priorityQueue.at(smallest).rank) {
            smallest = r;
        }
        if (smallest != i) {
            priorityQueue.exch(i, smallest);
            minHeapifyWithRanks(priorityQueue, smallest);
        }
    }

    /**
     * Implements the stack operation <span style="font-variant:small-caps;">Push</span>
     * using max-priority queue with ranked elements.
     * <p>Solution to exercise 6.5-6.</p>
     *
     * @param priorityQueue the max-priority queue with ranked elements
     * @param key           the key to insert to the stack
     * @param <E>           the type of keys in {@code priorityQueue}
     */
    public static <E> void pushUsingPriorityQueue(PriorityQueueWithRanks<E> priorityQueue, E key) {
        int rank = priorityQueue.getCurrentRank();
        PriorityQueueWithRanks.KeyWithRank<E> keyWithRank = new PriorityQueueWithRanks.KeyWithRank<>(key, rank);
        maxHeapInsertWithRanks(priorityQueue, keyWithRank);
    }

    private static <E> void maxHeapInsertWithRanks(PriorityQueueWithRanks<E> priorityQueue, PriorityQueueWithRanks.KeyWithRank<E> keyWithRank) {
        priorityQueue.heapSize++;
        priorityQueue.set(priorityQueue.heapSize, new PriorityQueueWithRanks.KeyWithRank<>(keyWithRank.key, Integer.MIN_VALUE));
        heapIncreaseRank(priorityQueue, priorityQueue.heapSize, keyWithRank.rank);
    }

    private static <E> void heapIncreaseRank(PriorityQueueWithRanks<E> priorityQueue, int i, int rank) {
        priorityQueue.at(i).rank = rank;
        while (i > 1 && priorityQueue.at(parent(i)).rank < priorityQueue.at(i).rank) {
            priorityQueue.exch(i, parent(i));
            i = parent(i);
        }
    }

    /**
     * Implements the stack operation <span style="font-variant:small-caps;">Pop</span>
     * using max-priority queue with ranked elements.
     * <p>Solution to exercise 6.5-6.</p>
     *
     * @param priorityQueue the max-priority queue with ranked elements
     * @param <E>           the type of keys in {@code priorityQueue}
     * @return the key removed from the stack
     */
    public static <E> E popUsingPriorityQueue(PriorityQueueWithRanks<E> priorityQueue) {
        return heapExtractMaxWithRanks(priorityQueue).key;
    }

    private static <E> PriorityQueueWithRanks.KeyWithRank<E> heapExtractMaxWithRanks(PriorityQueueWithRanks<E> priorityQueue) {
        PriorityQueueWithRanks.KeyWithRank<E> max = priorityQueue.at(1);
        priorityQueue.set(1, priorityQueue.at(priorityQueue.heapSize));
        priorityQueue.heapSize--;
        maxHeapifyWithRanks(priorityQueue, 1);
        return max;
    }

    private static <E> void maxHeapifyWithRanks(PriorityQueueWithRanks<E> priorityQueue, int i) {
        int l = left(i);
        int r = right(i);
        int largest;
        if (l <= priorityQueue.heapSize && priorityQueue.at(l).rank > priorityQueue.at(i).rank) {
            largest = l;
        } else {
            largest = i;
        }
        if (r <= priorityQueue.heapSize && priorityQueue.at(r).rank > priorityQueue.at(largest).rank) {
            largest = r;
        }
        if (largest != i) {
            priorityQueue.exch(i, largest);
            maxHeapifyWithRanks(priorityQueue, largest);
        }
    }

    /**
     * Deletes an element from a max-heap.
     * <p><span style="font-variant:small-caps;">Max-Heap-Delete</span> from solution to exercise 6.5-7.</p>
     *
     * @param A   the max-heap
     * @param i   the index of the key in {@code A} to be deleted
     * @param <E> the type of keys in {@code A}
     * @return the key of the element deleted from {@code A}
     */
    public static <E extends Comparable<? super E>> E maxHeapDelete(Heap<E> A, int i) {
        A.exch(i, A.heapSize);
        A.heapSize--;
        maxHeapify(A, i);
        while (i > 1 && less(A.at(parent(i)), A.at(i))) {
            A.exch(i, parent(i));
            i = parent(i);
        }
        return A.at(A.heapSize + 1);
    }

    /**
     * Merges sorted lists into a single sorted list (a version for singly linked lists).
     * <p>Solution to exercise 6.5-8.</p>
     *
     * @param sortedLists an array of sorted lists
     * @return the merged sorted list
     */
    @SuppressWarnings("all")
    public static SinglyLinkedList<Integer> mergeSortedLists(Array<SinglyLinkedList<Integer>> sortedLists) {
        SinglyLinkedList<Integer> mergedList = new SinglyLinkedList<>();
        Heap<Pair<Integer, SinglyLinkedList<Integer>>> minPriorityQueue = Heap.withLength(sortedLists.length);
        for (int i = 1; i <= sortedLists.length; i++) {
            if (sortedLists.at(i).head != null) {
                minHeapInsertForMergingLists(minPriorityQueue, new Pair<>(sortedLists.at(i).head.key, sortedLists.at(i)));
                sortedLists.at(i).head = sortedLists.at(i).head.next;
            }
        }
        SinglyLinkedList.Node<Integer> tail = null;
        while (minPriorityQueue.heapSize > 0) {
            Pair<Integer, SinglyLinkedList<Integer>> min = heapExtractMinForMergingLists(minPriorityQueue);
            SinglyLinkedList.Node<Integer> newNode = new SinglyLinkedList.Node<>(min.first);
            if (mergedList.head == null) {
                mergedList.head = tail = newNode;
            } else {
                tail = tail.next = newNode;
            }
            if (min.second.head != null) {
                minHeapInsertForMergingLists(minPriorityQueue, new Pair<>(min.second.head.key, min.second));
                min.second.head = min.second.head.next;
            }
        }
        return mergedList;
    }

    private static void minHeapInsertForMergingLists(Heap<Pair<Integer, SinglyLinkedList<Integer>>> A, Pair<Integer, SinglyLinkedList<Integer>> key) {
        A.heapSize++;
        A.set(A.heapSize, new Pair<>(Integer.MAX_VALUE, key.second));
        heapDecreaseKeyForMergingLists(A, A.heapSize, key);
    }

    private static void heapDecreaseKeyForMergingLists(Heap<Pair<Integer, SinglyLinkedList<Integer>>> A, int i, Pair<Integer, SinglyLinkedList<Integer>> key) {
        A.set(i, key);
        while (i > 1 && A.at(parent(i)).first > A.at(i).first) {
            A.exch(i, parent(i));
            i = parent(i);
        }
    }

    private static Pair<Integer, SinglyLinkedList<Integer>> heapExtractMinForMergingLists(Heap<Pair<Integer, SinglyLinkedList<Integer>>> A) {
        Pair<Integer, SinglyLinkedList<Integer>> min = A.at(1);
        A.set(1, A.at(A.heapSize));
        A.heapSize--;
        minHeapifyForMergingLists(A, 1);
        return min;
    }

    private static void minHeapifyForMergingLists(Heap<Pair<Integer, SinglyLinkedList<Integer>>> A, int i) {
        int l = left(i);
        int r = right(i);
        int smallest;
        if (l <= A.heapSize && A.at(l).first < A.at(i).first) {
            smallest = l;
        } else {
            smallest = i;
        }
        if (r <= A.heapSize && A.at(r).first < A.at(smallest).first) {
            smallest = r;
        }
        if (smallest != i) {
            A.exch(i, smallest);
            minHeapifyForMergingLists(A, smallest);
        }
    }

    /**
     * Constructs a max-heap from an array of elements - alternative method.
     * <p><span style="font-variant:small-caps;">Build-Max-Heap'</span> from problem 6-1.</p>
     *
     * @param A the array of elements
     * @return a max-heap constructed from elements from {@code A}
     */
    public static Heap<Integer> buildMaxHeap_(Array<Integer> A) {
        Heap<Integer> H = new Heap<>(A);
        H.heapSize = 1;
        for (int i = 2; i <= H.length; i++) {
            maxHeapInsert(H, H.at(i));
        }
        return H;
    }

    /**
     * Returns the parent index in a multiary heap.
     * <p><span style="font-variant:small-caps;">Multiary-Parent</span> from solution to problem 6.1(a).</p>
     *
     * @param d the arity of the heap
     * @param i the index of a node
     * @return the parent index of the node of index {@code i}
     */
    static int multiaryParent(int d, int i) {
        return ceil(i - 1, d);
    }

    /**
     * Returns the {@code k}-th child index in a multiary heap.
     * <p><span style="font-variant:small-caps;">Multiary-Child</span> from solution to problem 6-1(a).</p>
     *
     * @param d the arity of the heap ({@code d >= 2})
     * @param k the child number ({@code 1 <= k <= d})
     * @param i the index of a node
     * @return the left child index of the node of index {@code i}
     */
    static int multiaryChild(int d, int k, int i) {
        return d * (i - 1) + k + 1;
    }

    /**
     * Removes the largest element from a multiary max-heap.
     * <p><span style="font-variant:small-caps;">Multiary-Heap-Extract-Max</span> from solution to problem 6-2(c).</p>
     *
     * @param A the multiary max-heap
     * @param d the arity of {@code A}
     * @return the largest element in {@code A}
     * @throws RuntimeException if {@code A} is empty
     */
    public static int multiaryHeapExtractMax(Heap<Integer> A, int d) {
        if (A.heapSize < 1) {
            throw new RuntimeException("heap underflow");
        }
        int max = A.at(1);
        A.set(1, A.at(A.heapSize));
        A.heapSize--;
        multiaryMaxHeapify(A, d, 1);
        return max;
    }

    /**
     * Restores the max-heap property in a multiary max-heap.
     * <p><span style="font-variant:small-caps;">Multiary-Max-Heapify</span> from solution to problem 6-2(c).</p>
     *
     * @param A the heap with max-heap property violated at one node
     * @param d the arity of the {@code A}
     * @param i the index of the node in {@code A} the property is violated at
     */
    static void multiaryMaxHeapify(Heap<Integer> A, int d, int i) {
        int largest = i;
        int k = 1;
        int child = multiaryChild(d, 1, i);
        while (k <= d && child <= A.heapSize) {
            if (A.at(child) > A.at(largest)) {
                largest = child;
            }
            k++;
            child = multiaryChild(d, k, i);
        }
        if (largest != i) {
            A.exch(i, largest);
            multiaryMaxHeapify(A, d, largest);
        }
    }

    /**
     * Inserts a node into a multiary max-heap.
     * <p><span style="font-variant:small-caps;">Multiary-Max-Heap-Insert</span> from solution to problem 6-2(d).</p>
     *
     * @param A   the multiary max-heap
     * @param d   the arity of {@code A}
     * @param key the key of the new node
     */
    public static void multiaryMaxHeapInsert(Heap<Integer> A, int d, int key) {
        A.heapSize++;
        A.set(A.heapSize, Integer.MIN_VALUE);
        multiaryHeapIncreaseKey(A, d, A.heapSize, key);
    }

    /**
     * Increases a key in a multiary max-heap.
     * <p><span style="font-variant:small-caps;">Multiary-Heap-Increase-Key</span> from solution to problem 6-2(e).</p>
     *
     * @param A the multiary max-heap
     * @param d the arity of {@code A}
     * @param i the index of the key in {@code A} to be increased
     * @param k the new key
     * @throws RuntimeException if {@code k} is smaller than {@code A[i]}
     */
    public static void multiaryHeapIncreaseKey(Heap<Integer> A, int d, int i, int k) {
        A.set(i, max(A.at(i), k));
        while (i > 1 && A.at(multiaryParent(d, i)) < A.at(i)) {
            A.exch(i, multiaryParent(d, i));
            i = multiaryParent(d, i);
        }
    }

    /**
     * Removes the smallest element from a Young tableau.
     * <p><span style="font-variant:small-caps;">Young-Extract-Min</span> from solution to problem 6-3(c).</p>
     *
     * @param Y the Young tableau
     * @param m the number of rows in {@code Y}
     * @param n the number of columns in {@code Y}
     * @param i the row index of the current element
     * @param j the column index of the current element
     * @return the smallest element in {@code Y}
     */
    static int youngExtractMin(Young Y, int m, int n, int i, int j) {
        if (i == m && j == n) {
            int min = Y.at(i, j);
            Y.set(i, j, Integer.MAX_VALUE);
            return min;
        }
        int i_ = i, j_ = j + 1;
        if (i < m) {
            if (j == n || Y.at(i + 1, j) < Y.at(i, j + 1)) {
                i_ = i + 1;
                j_ = j;
            }
        }
        Y.exch(i, j, i_, j_);
        return youngExtractMin(Y, m, n, i_, j_);
    }

    /**
     * Restores the Young property in a Young tableau.
     * <p><span style="font-variant:small-caps;">Youngify</span> from solution to problem 6-3(d).</p>
     *
     * @param Y the Young tableau
     * @param i the row index of the current element
     * @param j the column index of the current element
     */
    static void youngify(Young Y, int i, int j) {
        int i_ = i, j_ = j;
        if (i > 1 && Y.at(i - 1, j) > Y.at(i_, j_)) {
            i_ = i - 1;
            j_ = j;
        }
        if (j > 1 && Y.at(i, j - 1) > Y.at(i_, j_)) {
            i_ = i;
            j_ = j - 1;
        }
        if (i_ != i || j_ != j) {
            Y.exch(i, j, i_, j_);
            youngify(Y, i_, j_);
        }
    }

    /**
     * Inserts a key into a Young tableau.
     * <p><span style="font-variant:small-caps;">Young-Insert</span> from solution to problem 6-3(d).</p>
     *
     * @param Y   the Young tableau
     * @param m   the number of rows in {@code Y}
     * @param n   the number of columns in {@code Y}
     * @param key the key to insert
     */
    static void youngInsert(Young Y, int m, int n, int key) {
        Y.set(m, n, key);
        youngify(Y, m, n);
    }

    /**
     * Sorts elements using a Young tableau.
     * <p><span style="font-variant:small-caps;">Young-Sort</span> from solution to problem 6-3(e).</p>
     *
     * @param A the array of elements to sort
     */
    public static void youngSort(Array<Integer> A) {
        int n = (int) sqrt(A.length);
        Young Y = new Young(n, n);
        for (int i = 1; i <= n * n; i++) {
            youngInsert(Y, n, n, A.at(i));
        }
        for (int i = 1; i <= n * n; i++) {
            A.set(i, youngExtractMin(Y, n, n, 1, 1));
        }
    }

    /**
     * Searches for a key in a Young tableau.
     * <p><span style="font-variant:small-caps;">Young-Search</span> from solution to problem 6-3(f).</p>
     *
     * @param Y the Young tableau
     * @param m the number of rows in {@code Y}
     * @param n the number of columns in {@code Y}
     * @param v the key to find
     * @return {@code true} if {@code Y} contains {@code v}, or {@code false} otherwise
     */
    public static boolean youngSearch(Young Y, int m, int n, int v) {
        int i = 1;
        int j = n;
        while (i <= m && j >= 1) {
            if (v == Y.at(i, j)) {
                return true;
            }
            if (v > Y.at(i, j)) {
                i++;
            } else {
                j--;
            }
        }
        return false;
    }

}
