package pl.kwojtas.cormenimpl;

import static java.lang.Math.max;
import static java.lang.Math.sqrt;
import static pl.kwojtas.cormenimpl.Util.ceil;
import static pl.kwojtas.cormenimpl.Util.greater;
import static pl.kwojtas.cormenimpl.Util.less;

public class Chapter6 {

    private Chapter6() { }

    // subchapter 6.1
    private static int parent(int i) {
        return i / 2;
    }

    // subchapter 6.1
    private static int left(int i) {
        return 2 * i;
    }

    // subchapter 6.1
    private static int right(int i) {
        return 2 * i + 1;
    }

    // subchapter 6.2
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

    // solution of 6.2-2
    private static <T extends Comparable> void minHeapify(Heap<T> A, int i) {
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

    // subchapter 6.3
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

    // for testing minHeapify
    private static <T extends Comparable> Heap<T> buildMinHeap(Array<T> A) {
        Heap<T> H = new Heap<>(A);
        H.heapSize = H.length;
        for (int i = H.length / 2; i >= 1; i--) {
            minHeapify(H, i);
        }
        return H;
    }

    // for testing minHeapify
    public static <T extends Comparable> void nonincreasingHeapsort(Array<T> A) {
        Heap<T> H = buildMinHeap(A);
        for (int i = H.length; i >= 2; i--) {
            H.exch(1, i);
            H.heapSize--;
            minHeapify(H, 1);
        }
        A.set(H);
    }

    // solution of 6.2-5
    private static <T extends Comparable> void iterativeMaxHeapify(Heap<T> A, int i) {
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

    // for testing iterativeMaxHeapify
    private static <T extends Comparable> Heap<T> buildMaxHeapUsingIterativeMaxHeapify(Array<T> A) {
        Heap<T> H = new Heap<>(A);
        H.heapSize = H.length;
        for (int i = H.length / 2; i >= 1; i--) {
            iterativeMaxHeapify(H, i);
        }
        return H;
    }

    // for testing iterativeMaxHeapify
    public static <T extends Comparable> void heapsortUsingIterativeMaxHeapify(Array<T> A) {
        Heap<T> H = buildMaxHeapUsingIterativeMaxHeapify(A);
        for (int i = H.length; i >= 2; i--) {
            H.exch(1, i);
            H.heapSize--;
            iterativeMaxHeapify(H, 1);
        }
        A.set(H);
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
    private static void heapIncreaseKey(Heap<Integer> A, int i, int key) {
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
    private static void heapDecreaseKey(Heap<Integer> A, int i, int key) {
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

    // problem 6-1
    private static Heap<Integer> buildMaxHeap_(Array<Integer> A) {
        Heap<Integer> H = new Heap<>(A);
        H.heapSize = 1;
        for (int i = 2; i <= H.length; i++) {
            maxHeapInsert(H, H.at(i));
        }
        return H;
    }

    // for testing buildMaxHeap_
    public static void heapsort_(Array<Integer> A) {
        Heap<Integer> H = buildMaxHeap_(A);
        for (int i = H.length; i >= 2; i--) {
            H.exch(1, i);
            H.heapSize--;
            maxHeapify(H, 1);
        }
        A.set(H);
    }

    // solution of 6-2(a)
    private static int dAryParent(int d, int i) {
        return ceil(i - 1, d);
    }

    // solution of 6-2(a)
    private static int dAryChild(int d, int k, int i) {
        return d * (i - 1) + k + 1;
    }

    // solution of 6-2(c)
    private static void dAryMaxHeapify(Heap<Integer> A, int d, int i) {
        int largest = i;
        int k = 1;
        int child = dAryChild(d, 1, i);
        while (k <= d && child <= A.heapSize) {
            if (A.at(child) > A.at(largest)) {
                largest = child;
            }
            k++;
            child = dAryChild(d, k, i);
        }
        if (largest != i) {
            A.exch(i, largest);
            dAryMaxHeapify(A, d, largest);
        }
    }

    // solution of 6-2(d)
    private static void dAryMaxHeapInsert(Heap<Integer> A, int d, int key) {
        A.heapSize++;
        A.set(A.heapSize, Integer.MIN_VALUE);
        dAryHeapIncreaseKey(A, d, A.heapSize, key);
    }

    // solution of 6-2(e)
    private static void dAryHeapIncreaseKey(Heap<Integer> A, int d, int i, int k) {
        A.set(i, max(A.at(i), k));
        while (i > 1 && A.at(dAryParent(d, i)) < A.at(i)) {
            A.exch(i, dAryParent(d, i));
            i = dAryParent(d, i);
        }
    }

    // solution of 6-3(c)
    private static int youngExractMin(Young Y, int m, int n, int i, int j) {
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
        return youngExractMin(Y, m, n, i_, j_);
    }

    // solution of 6-3(d)
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

    // solution of 6-3(d)
    private static void youngInsert(Young Y, int m, int n, int key) {
        Y.set(m, n, key);
        youngify(Y, m, n);
    }

    // solution of 6-3(e)
    private static void youngSort(Array<Integer> A) {
        int n = (int) sqrt(A.length);
        Young Y = new Young(n, n);
        for (int i = 1; i <= n * n; i++) {
            youngInsert(Y, n, n, A.at(i));
        }
        for (int i = 1; i <= n * n; i++) {
            A.set(i, youngExractMin(Y, n, n, 1, 1));
        }
    }

    // solution of 6-3(f)
    private static boolean youngSearch(Matrix<Integer> Y, int m, int n, double v) {
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
