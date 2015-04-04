package pl.kwojtas.cormenimpl;

import pl.kwojtas.cormenimpl.util.Array;
import pl.kwojtas.cormenimpl.util.List;
import pl.kwojtas.cormenimpl.util.Pair;
import pl.kwojtas.cormenimpl.util.Point2D;
import pl.kwojtas.cormenimpl.util.ZeroBasedIndexedArray;

import static java.lang.Math.max;
import static java.lang.Math.sqrt;
import static pl.kwojtas.cormenimpl.Chapter5.random;

/**
 * Implements algorithms from Chapter 8.
 */
public final class Chapter8 {

    private Chapter8() { }

    /**
     *
     * <p><span style="font-variant:small-caps;">Counting-Sort</span> from subchapter 8.2.</p>
     *
     * @param A
     * @param B
     * @param k
     */
    public static void countingSort(Array<Integer> A, Array<Integer> B, int k) {
        ZeroBasedIndexedArray<Integer> C = ZeroBasedIndexedArray.withLength(k + 1);
        for (int i = 0; i <= k; i++) {
            C.set(i, 0);
        }
        for (int j = 1; j <= A.length; j++) {
            C.set(A.at(j), C.at(A.at(j)) + 1);
        }
        for (int i = 1; i <= k; i++) {
            C.set(i, C.at(i) + C.at(i - 1));
        }
        for (int j = A.length; j >= 1; j--) {
            B.set(C.at(A.at(j)), A.at(j));
            C.set(A.at(j), C.at(A.at(j)) - 1);
        }
    }

    /**
     *
     * <p>Exercise 8.2-3.</p>
     *
     * @param A
     * @param B
     * @param k
     */
    public static void nonStableCountingSort(Array<Integer> A, Array<Integer> B, int k) {
        ZeroBasedIndexedArray<Integer> C = ZeroBasedIndexedArray.withLength(k + 1);
        for (int i = 0; i <= k; i++) {
            C.set(i, 0);
        }
        for (int j = 1; j <= A.length; j++) {
            C.set(A.at(j), C.at(A.at(j)) + 1);
        }
        for (int i = 1; i <= k; i++) {
            C.set(i, C.at(i) + C.at(i - 1));
        }
        for (int j = 1; j <= A.length; j++) {
            B.set(C.at(A.at(j)), A.at(j));
            C.set(A.at(j), C.at(A.at(j)) - 1);
        }
    }

    /**
     *
     * <p>Solution to exercise 8.2-4.</p>
     *
     * @param A
     * @param k
     * @param a
     * @param b
     * @return
     */
    public static int countNumbersInRange(Array<Integer> A, int k, int a, int b) {
        ZeroBasedIndexedArray<Integer> C = ZeroBasedIndexedArray.withLength(k + 1);
        for (int i = 0; i <= k; i++) {
            C.set(i, 0);
        }
        for (int j = 1; j <= A.length; j++) {
            C.set(A.at(j), C.at(A.at(j)) + 1);
        }
        for (int i = 1; i <= k; i++) {
            C.set(i, C.at(i) + C.at(i - 1));
        }
        if (0 < a && a <= b && b <= k) {
            return C.at(b) - C.at(a - 1);
        }
        if (0 < a && a <= k && k < b) {
            return C.at(k) - C.at(a - 1);
        }
        if (a <= 0 && 0 <= b && b <= k) {
            return C.at(b);
        }
        if (a <= 0 && 0 <= k && k < b) {
            return C.at(k);
        }
        return 0;
    }

    /**
     *
     * <p><span style="font-variant:small-caps;">Radix-Sort</span> from subchapter 8.3.</p>
     *
     * @param A
     * @param d
     */
    public static void radixSort(Array<Integer> A, int d) {
        for (int i = 1; i <= d; i++) {
            stableSortOnDigit(A, i, 10);
        }
    }

    private static void stableSortOnDigit(Array<Integer> A, int digit, int k) {
        ZeroBasedIndexedArray<Integer> C = ZeroBasedIndexedArray.withLength(k);
        for (int i = 0; i <= k - 1; i++) {
            C.set(i, 0);
        }
        for (int j = 1; j <= A.length; j++) {
            C.set(getDigit(A.at(j), digit, k), C.at(getDigit(A.at(j), digit, k)) + 1);
        }
        for (int i = 1; i <= k - 1; i++) {
            C.set(i, C.at(i) + C.at(i - 1));
        }
        Array<Integer> B = Array.withLength(A.length);
        for (int j = A.length; j >= 1; j--) {
            B.set(C.at(getDigit(A.at(j), digit, k)), A.at(j));
            C.set(getDigit(A.at(j), digit, k), C.at(getDigit(A.at(j), digit, k)) - 1);
        }
        for (int j = 1; j <= A.length; j++) {
            A.set(j, B.at(j));
        }
    }

    private static int getDigit(int number, int digit, int k) {
        while (digit > 1) {
            number /= k;
            digit--;
        }
        return number % k;
    }

    /**
     *
     * <p>Solution to exercise 8.3-4.</p>
     *
     * @param A
     */
    public static void sortNSquaredNumbers(Array<Integer> A) {
        int n = A.length;
        stableSortOnDigit(A, 1, n);
        stableSortOnDigit(A, 2, n);
    }

    /**
     *
     * <p><span style="font-variant:small-caps;">Bucket-Sort</span> from subchapter 8.4.</p>
     *
     * @param A
     */
    public static void bucketSort(Array<Double> A) {
        int n = A.length;
        ZeroBasedIndexedArray<List<Double>> B = ZeroBasedIndexedArray.withLength(n);
        for (int i = 0; i <= n - 1; i++) {
            B.set(i, new List<>());
        }
        for (int i = 1; i <= n; i++) {
            List.Node<Double> x = new List.Node<>(A.at(i));
            Chapter10.listInsert(B.at((int) (n * A.at(i))), x);
        }
        for (int i = 0; i <= n - 1; i++) {
            listInsertionSort(B.at(i));
        }
        concatenateListsToArray(B, A);
    }

    private static void listInsertionSort(List<Double> L) {
        if (L.head == null) {
            return;
        }
        List.Node<Double> x = L.head.next;
        while (x != null) {
            List.Node<Double> y = x.prev;
            while (y != null && y.key > x.key) {
                y = y.prev;
            }
            List.Node<Double> z = x.next;
            if (y != x.prev) {
                x.prev.next = z;
                if (z != null) {
                    z.prev = x.prev;
                }
                x.prev = y;
                if (y == null) {
                    x.next = L.head;
                    L.head.prev = x;
                    L.head = x;
                } else {
                    x.next = y.next;
                    y.next = x;
                }
            }
            x = z;
        }
    }

    private static void concatenateListsToArray(ZeroBasedIndexedArray<List<Double>> B, Array<Double> A) {
        int k = 1;
        for (int i = 0; i <= B.length - 1; i++) {
            List.Node<Double> x = B.at(i).head;
            while (x != null) {
                A.set(k, x.key);
                k++;
                x = x.next;
            }
        }
    }

    /**
     *
     * <p>Solution to exercise 8.4-4.</p>
     *
     * @param points
     */
    public static void sortUnitCirclePoints(Array<Point2D> points) {
        int n = points.length;
        ZeroBasedIndexedArray<List<Pair<Point2D, Double>>> B = ZeroBasedIndexedArray.withLength(n);
        for (int i = 0; i <= n - 1; i++) {
            B.set(i, new List<>());
        }
        for (int i = 1; i <= n; i++) {
            double distance = sqrt(points.at(i).x * points.at(i).x + points.at(i).y * points.at(i).y);
            int bucket = (int) (distance * distance * n);
            List.Node<Pair<Point2D, Double>> x = new List.Node<>(new Pair<>(points.at(i), distance));
            Chapter10.listInsert(B.at(bucket), x);
        }
        for (int i = 0; i <= n - 1; i++) {
            listInsertionSortUnitCirclePoints(B.at(i));
        }
        concatenateListsOfUnitCirclePoints(B, points);
    }

    private static void listInsertionSortUnitCirclePoints(List<Pair<Point2D, Double>> L) {
        if (L.head == null) {
            return;
        }
        List.Node<Pair<Point2D, Double>> x = L.head.next;
        while (x != null) {
            List.Node<Pair<Point2D, Double>> y = x.prev;
            while (y != null && y.key.second > x.key.second) {
                y = y.prev;
            }
            List.Node<Pair<Point2D, Double>> z = x.next;
            if (y != x.prev) {
                x.prev.next = z;
                if (z != null) {
                    z.prev = x.prev;
                }
                x.prev = y;
                if (y == null) {
                    x.next = L.head;
                    L.head.prev = x;
                    L.head = x;
                } else {
                    x.next = y.next;
                    y.next = x;
                }
            }
            x = z;
        }
    }

    private static void concatenateListsOfUnitCirclePoints(
            ZeroBasedIndexedArray<List<Pair<Point2D, Double>>> B, Array<Point2D> points) {
        int k = 1;
        for (int i = 0; i <= B.length - 1; i++) {
            List.Node<Pair<Point2D, Double>> x = B.at(i).head;
            while (x != null) {
                points.set(k, x.key.first);
                k++;
                x = x.next;
            }
        }
    }

    /**
     *
     * <p><span style="font-variant:small-caps;">Bitwise-Sort</span> from solution to problem 8-2(b).</p>
     *
     * @param A
     */
    public static void bitwiseSort(Array<Integer> A) {
        int n = A.length;
        int i = 1;
        int j = n;
        while (i < j) {
            A.exch(i, j);
            while (i <= n && A.at(i).equals(0)) {
                i++;
            }
            while (j >= 1 && A.at(j).equals(1)) {
                j--;
            }
        }

    }

    /**
     *
     * <p><span style="font-variant:small-caps;">Counting-Sort-In-Place</span> from solution to problem 8-2(e).</p>
     *
     * @param A
     * @param k
     */
    public static void countingSortInPlace(Array<Integer> A, int k) {
        ZeroBasedIndexedArray<Integer> C = ZeroBasedIndexedArray.withLength(k + 1);
        for (int i = 0; i <= k; i++) {
            C.set(i, 0);
        }
        for (int j = 1; j <= A.length; j++) {
            C.set(A.at(j), C.at(A.at(j)) + 1);
        }
        for (int i = 1; i <= k; i++) {
            C.set(i, C.at(i) + C.at(i - 1));
        }
        ZeroBasedIndexedArray<Integer> positions = new ZeroBasedIndexedArray<>(C);
        int i = A.length;
        while (i >= 1) {
            int key = A.at(i);
            if (positions.at(key - 1) < i && i <= positions.at(key)) {
                i--;
            } else {
                A.exch(i, C.at(key));
                C.set(key, C.at(key) - 1);
            }
        }
    }

    /**
     *
     * <p>Solution to problem 8-3(a).</p>
     *
     * @param A
     */
    public static void variousLengthNumbersSort(Array<Integer> A) {
        int j = 0, j_ = 0;
        for (int i = 1; i <= A.length; i++) {
            if (A.at(i) < 0) {
                j++;
            } else {
                j_++;
            }
        }
        Array<Integer> negative = Array.withLength(j);
        Array<Integer> nonNegative = Array.withLength(j_);
        j = 1;
        j_ = 1;
        for (int i = 1; i <= A.length; i++) {
            if (A.at(i) < 0) {
                negative.set(j, -A.at(i));
                j++;
            } else {
                nonNegative.set(j_, A.at(i));
                j_++;
            }
        }
        variousLengthNonNegativeNumbersSort(negative);
        variousLengthNonNegativeNumbersSort(nonNegative);
        j = 1;
        for (int i = negative.length; i >= 1; i--) {
            A.set(j, -negative.at(i));
            j++;
        }
        for (int i = 1; i <= nonNegative.length; i++) {
            A.set(j, nonNegative.at(i));
            j++;
        }
    }

    private static void variousLengthNonNegativeNumbersSort(Array<Integer> A) {
        if (A.length == 0) {
            return;
        }
        int maxLength = 0;
        for (int i = 1; i <= A.length; i++) {
            maxLength = max(maxLength, getNumberLength(A.at(i)));
        }
        variousLengthSortByLength(A, maxLength);
        Array<Integer> B = Array.withLength(A.length);
        B.set(1, A.at(1));
        int j = 1;
        for (int i = 2; i <= A.length; i++) {
            int previousLength = getNumberLength(A.at(i - 1));
            int currentLength = getNumberLength(A.at(i));
            if (previousLength == currentLength) {
                j++;
                B.set(j, A.at(i));
            } else {
                Array<Integer> sameLengthNumbers = Array.withLength(j);
                for (int l = 1; l <= j; l++) {
                    sameLengthNumbers.set(l, B.at(l));
                }
                radixSort(sameLengthNumbers, previousLength);
                int k = i - 1;
                while (j >= 1) {
                    A.set(k, sameLengthNumbers.at(j));
                    j--;
                    k--;
                }
                B.set(1, A.at(i));
                j = 1;
            }
        }
        Array<Integer> sameLengthNumbers = Array.withLength(j);
        for (int l = 1; l <= j; l++) {
            sameLengthNumbers.set(l, B.at(l));
        }
        radixSort(sameLengthNumbers, getNumberLength(A.at(A.length)));
        int k = A.length;
        while (j >= 1) {
            A.set(k, sameLengthNumbers.at(j));
            j--;
            k--;
        }
    }

    private static void variousLengthSortByLength(Array<Integer> A, int k) {
        ZeroBasedIndexedArray<Integer> C = ZeroBasedIndexedArray.withLength(k + 1);
        for (int i = 0; i <= k; i++) {
            C.set(i, 0);
        }
        for (int j = 1; j <= A.length; j++) {
            C.set(getNumberLength(A.at(j)), C.at(getNumberLength(A.at(j))) + 1);
        }
        for (int i = 1; i <= k; i++) {
            C.set(i, C.at(i) + C.at(i - 1));
        }
        Array<Integer> B = Array.withLength(A.length);
        for (int j = A.length; j >= 1; j--) {
            B.set(C.at(getNumberLength(A.at(j))), A.at(j));
            C.set(getNumberLength(A.at(j)), C.at(getNumberLength(A.at(j))) - 1);
        }
        A.set(B);
    }

    private static int getNumberLength(int number) {
        if (number == 0) {
            return 1;
        }
        int length = 0;
        while (number > 0) {
            length++;
            number /= 10;
        }
        return length;
    }

    /**
     *
     * <p>Solution to problem 8-3(b).</p>
     *
     * @param A
     * @param position
     */
    public static void variousLengthStringsSort(Array<String> A, int position) {
        int n = A.length;
        countingSortByCharacter(A, position);
        int i = 1;
        while (i <= n) {
            int j = i;
            while (j <= n && A.at(j).charAt(position - 1) == A.at(i).charAt(position - 1)) {
                if (A.at(j).length() == position) {
                    A.exch(i, j);
                    i++;
                }
                j++;
            }
            Array<String> C = Array.withLength(j - i);
            for (int k = 1; k <= j - i; k++) {
                C.set(k, A.at(i + k - 1));
            }
            variousLengthStringsSort(C, position + 1);
            for (int k = 1; k <= j - i; k++) {
                A.set(i + k - 1, C.at(k));
            }
            i = j;
        }
    }

    private static void countingSortByCharacter(Array<String> A, int position) {
        ZeroBasedIndexedArray<Integer> C = ZeroBasedIndexedArray.withLength(128); // 128 = capacity for ASCII characters
        for (int i = 0; i <= 127; i++) {
            C.set(i, 0);
        }
        position--; // move to 0-based indexes
        for (int j = 1; j <= A.length; j++) {
            C.set(Character.getNumericValue(A.at(j).charAt(position)),
                    C.at(Character.getNumericValue(A.at(j).charAt(position))) + 1);
        }
        for (int i = 1; i <= 127; i++) {
            C.set(i, C.at(i) + C.at(i - 1));
        }
        Array<String> B = Array.withLength(A.length);
        for (int j = A.length; j >= 1; j--) {
            B.set(C.at(Character.getNumericValue(A.at(j).charAt(position))), A.at(j));
            C.set(Character.getNumericValue(A.at(j).charAt(position)),
                    C.at(Character.getNumericValue(A.at(j).charAt(position))) - 1);
        }
        A.set(B);
    }

    /**
     *
     * <p>Solution to problem 8-4(a).</p>
     *
     * @param R
     * @param B
     */
    public static void jugsGroup(Array<Double> R, Array<Double> B) {
        int n = R.length;
        for (int i = 1; i <= n - 1; i++) {
            int j = i;
            while (!R.at(i).equals(B.at(j))) {
                j++;
            }
            B.exch(i, j);
        }
    }

    /**
     *
     * <p><span style="font-variant:small-caps;">Jugs-Match</span> from solution to problem 8-4(c).</p>
     *
     * @param R
     * @param B
     * @param p
     * @param r
     */
    public static void jugsMatch(Array<Double> R, Array<Double> B, int p, int r) {
        if (p < r) {
            int q = jugsPartition(R, B, p, r);
            jugsMatch(R, B, p, q - 1);
            jugsMatch(R, B, q + 1, r);
        }
    }

    /**
     *
     * <p><span style="font-variant:small-caps;">Jugs-Partition</span> from solution to problem 8-4(c).</p>
     *
     * @param R
     * @param B
     * @param p
     * @param r
     * @return
     */
    private static int jugsPartition(Array<Double> R, Array<Double> B, int p, int r) {
        R.exch(r, random(p, r));
        double x = R.at(r);
        int i = p;
        while (!B.at(i).equals(x)) {
            i++;
        }
        B.exch(i, r);
        i = p - 1;
        for (int j = p; j <= r - 1; j++) {
            if (B.at(j) < x) {
                i++;
                B.exch(i, j);
            }
        }
        B.exch(i + 1, r);
        x = B.at(i + 1);
        i = p - 1;
        for (int j = p; j <= r - 1; j++) {
            if (R.at(j) < x) {
                i++;
                R.exch(i, j);
            }
        }
        R.exch(i + 1, r);
        return i + 1;
    }

    /**
     *
     * <p>Solution to problem 8-5(d).</p>
     *
     * @param A
     * @param k
     * @param p
     * @param r
     * @param <T>
     */
    public static <T extends Comparable> void kSort(Array<T> A, int k, int p, int r) {
        if (p + k - 1 < r) {
            int q = Chapter7.partition(A, p, r);
            kSort(A, k, p, q - 1);
            kSort(A, k, q + 1, r);
        }
    }

}
