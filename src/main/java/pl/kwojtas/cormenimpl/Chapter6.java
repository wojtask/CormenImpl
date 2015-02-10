package pl.kwojtas.cormenimpl;

import pl.kwojtas.cormenimpl.util.Array;
import pl.kwojtas.cormenimpl.util.Heap;
import pl.kwojtas.cormenimpl.util.List;
import pl.kwojtas.cormenimpl.util.Pair;
import pl.kwojtas.cormenimpl.util.Young;

import static java.lang.Math.max;
import static java.lang.Math.sqrt;
import static pl.kwojtas.cormenimpl.Chapter10.listDelete;
import static pl.kwojtas.cormenimpl.Chapter10.listInsert;
import static pl.kwojtas.cormenimpl.util.Util.ceil;
import static pl.kwojtas.cormenimpl.util.Util.greater;
import static pl.kwojtas.cormenimpl.util.Util.less;

public final class Chapter6 {

    private Chapter6() { }

    private static int parent(int i) {
        return i / 2;
    }

    private static int left(int i) {
        return 2 * i;
    }

    private static int right(int i) {
        return 2 * i + 1;
    }

    private static <T extends Comparable> void maxHeapify(Heap<T> A, int i) {
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

    private static <T extends Comparable> Heap<T> buildMaxHeap(Array<T> A) {
        Heap<T> H = new Heap<>(A);
        H.heapSize = H.length;
        for (int i = H.length / 2; i >= 1; i--) {
            maxHeapify(H, i);
        }
        return H;
    }

    // subchapter 6.4
    public static <T extends Comparable> void heapsort(Array<T> A) {
        Heap<T> H = buildMaxHeap(A);
        for (int i = H.length; i >= 2; i--) {
            H.exch(1, i);
            H.heapSize--;
            maxHeapify(H, 1);
        }
        A.set(H);
    }

    // solution of 6.2-2
    public static <T extends Comparable> void minHeapify(Heap<T> A, int i) {
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

    // solution of 6.2-5
    public static <T extends Comparable> void iterativeMaxHeapify(Heap<T> A, int i) {
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

    // subchapter 6.5
    public static int heapMaximum(Heap<Integer> A) {
        return A.at(1);
    }

    // subchapter 6.5
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

    // subchapter 6.5
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

    // subchapter 6.5
    public static void maxHeapInsert(Heap<Integer> A, int key) {
        A.heapSize++;
        A.set(A.heapSize, Integer.MIN_VALUE);
        heapIncreaseKey(A, A.heapSize, key);
    }

    // solution of 6.5-3
    public static int heapMinimum(Heap<Integer> A) {
        return A.at(1);
    }

    // solution of 6.5-3
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

    // solution of 6.5-3
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

    // solution of 6.5-3
    public static void minHeapInsert(Heap<Integer> A, int key) {
        A.heapSize++;
        A.set(A.heapSize, Integer.MAX_VALUE);
        heapDecreaseKey(A, A.heapSize, key);
    }

    public static class KeyWithRank<T> {
        public T key;
        public int rank;

        public KeyWithRank(T key, int rank) {
            this.key = key;
            this.rank = rank;
        }
    }

    public static class PriorityQueueWithRanks<T> extends Heap<KeyWithRank<T>> {
        private int currentRank;

        public PriorityQueueWithRanks(int initialLength) {
            super(new Array<>(), initialLength);
            currentRank = 1;
        }

        public int getCurrentRank() {
            return currentRank++;
        }
    }

    // solution of 6.5-6
    public static <T> void enqueueUsingPriorityQueue(PriorityQueueWithRanks<T> priorityQueue, T key) {
        int rank = priorityQueue.getCurrentRank();
        KeyWithRank<T> keyWithRank = new KeyWithRank<>(key, rank);
        minHeapInsertWithRanks(priorityQueue, keyWithRank);
    }

    private static <T> void minHeapInsertWithRanks(PriorityQueueWithRanks<T> priorityQueue, KeyWithRank<T> keyWithRank) {
        priorityQueue.heapSize++;
        priorityQueue.set(priorityQueue.heapSize, new KeyWithRank<>(keyWithRank.key, Integer.MAX_VALUE));
        heapDecreaseRank(priorityQueue, priorityQueue.heapSize, keyWithRank.rank);
    }

    private static <T> void heapDecreaseRank(PriorityQueueWithRanks<T> priorityQueue, int i, int rank) {
        priorityQueue.at(i).rank = rank;
        while (i > 1 && priorityQueue.at(parent(i)).rank > priorityQueue.at(i).rank) {
            priorityQueue.exch(i, parent(i));
            i = parent(i);
        }
    }

    // solution of 6.5-6
    public static <T> T dequeueUsingPriorityQueue(PriorityQueueWithRanks<T> priorityQueue) {
        return heapExtractMinWithRanks(priorityQueue).key;
    }

    private static <T> KeyWithRank<T> heapExtractMinWithRanks(PriorityQueueWithRanks<T> priorityQueue) {
        KeyWithRank<T> min = priorityQueue.at(1);
        priorityQueue.set(1, priorityQueue.at(priorityQueue.heapSize));
        priorityQueue.heapSize--;
        minHeapifyWithRanks(priorityQueue, 1);
        return min;
    }

    private static <T> void minHeapifyWithRanks(PriorityQueueWithRanks<T> priorityQueue, int i) {
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

    // solution of 6.5-6
    public static <T> void pushUsingPriorityQueue(PriorityQueueWithRanks<T> priorityQueue, T key) {
        int rank = priorityQueue.getCurrentRank();
        KeyWithRank<T> keyWithRank = new KeyWithRank<>(key, rank);
        maxHeapInsertWithRanks(priorityQueue, keyWithRank);
    }

    private static <T> void maxHeapInsertWithRanks(PriorityQueueWithRanks<T> priorityQueue, KeyWithRank<T> keyWithRank) {
        priorityQueue.heapSize++;
        priorityQueue.set(priorityQueue.heapSize, new KeyWithRank<>(keyWithRank.key, Integer.MIN_VALUE));
        heapIncreaseRank(priorityQueue, priorityQueue.heapSize, keyWithRank.rank);
    }

    private static <T> void heapIncreaseRank(PriorityQueueWithRanks<T> priorityQueue, int i, int rank) {
        priorityQueue.at(i).rank = rank;
        while (i > 1 && priorityQueue.at(parent(i)).rank < priorityQueue.at(i).rank) {
            priorityQueue.exch(i, parent(i));
            i = parent(i);
        }
    }

    // solution of 6.5-6
    public static <T> T popUsingPriorityQueue(PriorityQueueWithRanks<T> priorityQueue) {
        return heapExtractMaxWithRanks(priorityQueue).key;
    }

    private static <T> KeyWithRank<T> heapExtractMaxWithRanks(PriorityQueueWithRanks<T> priorityQueue) {
        KeyWithRank<T> max = priorityQueue.at(1);
        priorityQueue.set(1, priorityQueue.at(priorityQueue.heapSize));
        priorityQueue.heapSize--;
        maxHeapifyWithRanks(priorityQueue, 1);
        return max;
    }

    private static <T> void maxHeapifyWithRanks(PriorityQueueWithRanks<T> priorityQueue, int i) {
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

    // solution of 6.5-7
    public static <T extends Comparable> T maxHeapDelete(Heap<T> A, int i) {
        A.exch(i, A.heapSize);
        A.heapSize--;
        maxHeapify(A, i);
        while (i > 1 && less(A.at(parent(i)), A.at(i))) {
            A.exch(i, parent(i));
            i = parent(i);
        }
        return A.at(A.heapSize + 1);
    }

    // solution of 6.5-8
    public static List<Integer> mergeSortedLists(Array<List<Integer>> sortedLists) {
        List<Integer> reversedMergedList = new List<>();
        Heap<Pair<Integer, List<Integer>>> minPriorityQueue = Heap.withLength(sortedLists.length);
        for (int i = 1; i <= sortedLists.length; i++) {
            if (sortedLists.at(i).head != null) {
                minHeapInsertForMergingLists(minPriorityQueue, new Pair<>(sortedLists.at(i).head.key, sortedLists.at(i)));
                sortedLists.at(i).head = sortedLists.at(i).head.next;
            }
        }
        while (minPriorityQueue.heapSize > 0) {
            Pair<Integer, List<Integer>> min = heapExtractMinForMergingLists(minPriorityQueue);
            listInsert(reversedMergedList, reversedMergedList.new Node(min.first));
            if (min.second.head != null) {
                minHeapInsertForMergingLists(minPriorityQueue, new Pair<>(min.second.head.key, min.second));
                min.second.head = min.second.head.next;
            }
        }
        List<Integer> mergedList = new List<>();
        while (reversedMergedList.head != null) {
            List<Integer>.Node x = reversedMergedList.head;
            listDelete(reversedMergedList, reversedMergedList.head);
            listInsert(mergedList, x);
        }
        return mergedList;
    }

    private static void minHeapInsertForMergingLists(Heap<Pair<Integer, List<Integer>>> A, Pair<Integer, List<Integer>> key) {
        A.heapSize++;
        A.set(A.heapSize, new Pair<>(Integer.MAX_VALUE, key.second));
        heapDecreaseKeyForMergingLists(A, A.heapSize, key);
    }

    private static void heapDecreaseKeyForMergingLists(
            Heap<Pair<Integer, List<Integer>>> A, int i, Pair<Integer, List<Integer>> key) {
        if (key.first > A.at(i).first) {
            throw new RuntimeException("new key is larger than current key");
        }
        A.set(i, key);
        while (i > 1 && A.at(parent(i)).first > A.at(i).first) {
            A.exch(i, parent(i));
            i = parent(i);
        }
    }

    private static Pair<Integer, List<Integer>> heapExtractMinForMergingLists(Heap<Pair<Integer, List<Integer>>> A) {
        if (A.heapSize < 1) {
            throw new RuntimeException("heap underflow");
        }
        Pair<Integer, List<Integer>> min = A.at(1);
        A.set(1, A.at(A.heapSize));
        A.heapSize--;
        minHeapifyForMergingLists(A, 1);
        return min;
    }

    private static void minHeapifyForMergingLists(Heap<Pair<Integer, List<Integer>>> A, int i) {
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

    // problem 6-1
    public static Heap<Integer> buildMaxHeap_(Array<Integer> A) {
        Heap<Integer> H = new Heap<>(A);
        H.heapSize = 1;
        for (int i = 2; i <= H.length; i++) {
            maxHeapInsert(H, H.at(i));
        }
        return H;
    }

    private static int multiaryParent(int d, int i) {
        return ceil(i - 1, d);
    }

    private static int multiaryChild(int d, int k, int i) {
        return d * (i - 1) + k + 1;
    }

    // solution of 6-2(c)
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

    private static void multiaryMaxHeapify(Heap<Integer> A, int d, int i) {
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

    // solution of 6-2(d)
    public static void multiaryMaxHeapInsert(Heap<Integer> A, int d, int key) {
        A.heapSize++;
        A.set(A.heapSize, Integer.MIN_VALUE);
        multiaryHeapIncreaseKey(A, d, A.heapSize, key);
    }

    // solution of 6-2(e)
    public static void multiaryHeapIncreaseKey(Heap<Integer> A, int d, int i, int k) {
        A.set(i, max(A.at(i), k));
        while (i > 1 && A.at(multiaryParent(d, i)) < A.at(i)) {
            A.exch(i, multiaryParent(d, i));
            i = multiaryParent(d, i);
        }
    }

    private static int youngExtractMin(Young Y, int m, int n, int i, int j) {
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

    private static void youngify(Young Y, int i, int j) {
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

    private static void youngInsert(Young Y, int m, int n, int key) {
        Y.set(m, n, key);
        youngify(Y, m, n);
    }

    // solution of 6-3(e)
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

    // solution of 6-3(f)
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
