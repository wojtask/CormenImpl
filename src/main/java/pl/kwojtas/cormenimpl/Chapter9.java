package pl.kwojtas.cormenimpl;

import pl.kwojtas.cormenimpl.util.Array;
import pl.kwojtas.cormenimpl.util.Pair;
import pl.kwojtas.cormenimpl.util.Point2D;

import java.util.HashSet;
import java.util.Set;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static pl.kwojtas.cormenimpl.Chapter7.randomizedPartition;
import static pl.kwojtas.cormenimpl.util.Util.ceil;
import static pl.kwojtas.cormenimpl.util.Util.less;

public class Chapter9 {

    private Chapter9() { }

    // subchapter 9.1
    public static int minimum(Array<Integer> A) {
        int min = A.at(1);
        for (int i = 2; i <= A.length; i++) {
            if (min > A.at(i)) {
                min = A.at(i);
            }
        }
        return min;
    }

    // subchapter 9.1
    public static Pair<Integer, Integer> minimumMaximum(Array<Integer> A) {
        int n = A.length;
        Pair<Integer, Integer> p;
        int i;
        if (n % 2 == 1) {
            p = new Pair<>(A.at(1), A.at(1));
            i = 2;
        } else {
            if (A.at(1) < A.at(2)) {
                p = new Pair<>(A.at(1), A.at(2));
            } else {
                p = new Pair<>(A.at(2), A.at(1));
            }
            i = 3;
        }
        while (i + 1 <= A.length) {
            if (A.at(i) < A.at(i + 1)) {
                if (A.at(i) < p.first) {
                    p.first = A.at(i);
                }
                if (A.at(i + 1) > p.second) {
                    p.second = A.at(i + 1);
                }
            } else {
                if (A.at(i + 1) < p.first) {
                    p.first = A.at(i + 1);
                }
                if (A.at(i) > p.second) {
                    p.second = A.at(i);
                }
            }
            i += 2;
        }
        return p;
    }

    // subchapter 9.2
    public static int randomizedSelect(Array<Integer> A, int p, int r, int i) {
        if (p == r) {
            return A.at(p);
        }
        int q = randomizedPartition(A, p, r);
        int k = q - p + 1;
        if (i == k) {
            return A.at(q);
        } else if (i < k) {
            return randomizedSelect(A, p, q - 1, i);
        } else {
            return randomizedSelect(A, q + 1, r, i - k);
        }
    }

    // solution of 9.2-3
    public static int iterativeRandomizedSelect(Array<Integer> A, int p, int r, int i) {
        while (p < r) {
            int q = randomizedPartition(A, p, r);
            int k = q - p + 1;
            if (i == k) {
                return A.at(q);
            } else if (i < k) {
                r = q - 1;
            } else {
                p = q + 1;
                i -= k;
            }
        }
        return A.at(p);
    }

    // subchapter 9.3
    public static <T extends Comparable> T select(Array<T> A, int p, int r, int i) {
        int n = r - p + 1;
        if (n == 1) {
            return A.at(p);
        }
        Array<T>[] groups = new Array[ceil(n, 5)];
        for (int j = 0; j < groups.length; j++) {
            groups[j] = new Array<>();
        }
        for (int j = p; j <= r; j++) {
            groups[(j - p) / 5].set((j - p) % 5 + 1, A.at(j));
        }
        Array<T> medians = new Array<>();
        for (int j = 0; j < groups.length; j++) {
            Chapter2.insertionSort(groups[j]);
            medians.set(j + 1, groups[j].at((groups[j].length + 1) / 2));
        }
        T x = select(medians, 1, medians.length, (medians.length + 1) / 2);
        int k = partitionAround(A, p, r, x) - p + 1;
        if (i == k) {
            return x;
        }
        if (i < k) {
            return select(A, p, p + k - 2, i);
        }
        return select(A, p + k, r, i - k);
    }

    // subchapter 9.3
    private static <T extends Comparable> int partitionAround(Array<T> A, int p, int r, T x) {
        int q = p;
        while (!A.at(q).equals(x)) {
            q++;
        }
        A.exch(q, r);
        int i = p - 1;
        for (int j = p; j <= r - 1; j++) {
            if (less(A.at(j), x)) {
                i++;
                A.exch(i, j);
            }
        }
        A.exch(i + 1, r);
        return i + 1;
    }

    // solution of 9.3-3
    public static <T extends Comparable> void bestCaseQuicksort(Array<T> A, int p, int r) {
        if (p < r) {
            int q = (p + r) / 2;
            select(A, p, r, q);
            bestCaseQuicksort(A, p, q - 1);
            bestCaseQuicksort(A, q + 1, r);
        }
    }

    // solution of 9.3-5
    public static int selectUsingMedianSubroutine(Array<Integer> A, int p, int r, int i) {
        if (p == r) {
            return A.at(p);
        }
        int q = (p + r) / 2;
        int x = select(A, p, r, q); // black-box median subroutine
        partitionAround(A, p, r, x); // we need to partition around the median because we could have used other black-box than select
        int k = q - p + 1;
        if (i == k) {
            return A.at(q);
        } else if (i < k) {
            return randomizedSelect(A, p, q - 1, i);
        } else {
            return randomizedSelect(A, q + 1, r, i - k);
        }
    }

    // solution of 9.3-6
    public static Set<Integer> quantiles(Array<Integer> A, int p, int r, int k) {
        int n = r - p + 1;
        if (k == 1) {
            return new HashSet<>();
        }
        int q1 = (int)(p + (k / 2) * (1.0 * n / k));
        int q2 = (int)(p + ceil(k, 2) * (1.0 * n / k));
        select(A, p, r, q1 - p + 1);
        if (q1 != q2) {
            select(A, q1 + 1, r, q2 - q1);
        }
        Set<Integer> L = quantiles(A, p, q1 - 1, k / 2);
        Set<Integer> R = quantiles(A, q2 + 1, r, k / 2);
        L.add(A.at(q1));
        L.add(A.at(q2));
        L.addAll(R);
        return L;
    }

    // solution of 9.3-7
    public static Set<Integer> medianProximity(Array<Integer> S, int k) {
        int n = S.length;
        int x = select(S, 1, n, (n + 1) / 2);
        Array<Integer> dist = new Array<>();
        for (int i = 1; i <= n; i++) {
            dist.set(i, abs(S.at(i) - x));
        }
        int y = select(dist, 1, n, k);
        Set<Integer> M = new HashSet<>();
        for (int i = 1; i <= n; i++) {
            if (abs(S.at(i) - x) <= y) {
                M.add(S.at(i));
            }
        }
        if (M.size() == k + 1) {
            M.remove(x + y);
        }
        return M;
    }

    // solution of 9.3-8
    public static int twoArraysMedian(Array<Integer> X, int pX, int rX, Array<Integer> Y, int pY, int rY) {
        if (rX - pX <= 1) {
            return min(max(X.at(pX), Y.at(pY)), min(X.at(rX), Y.at(rY)));
        }
        int qX = (pX + rX) / 2;
        int qX_ = ceil(pX + rX, 2);
        int qY = (pY + rY) / 2;
        int qY_ = ceil(pY + rY, 2);
        if (X.at(qX).equals(Y.at(qY))) {
            return X.at(qX);
        }
        if (X.at(qX) < Y.at(qY)) {
            return twoArraysMedian(X, qX, rX, Y, pY, qY_);
        } else {
            return twoArraysMedian(X, pX, qX_, Y, qY, rY);
        }
    }

    // solution of 9-2(b)
    public static double weightedMedianUsingSorting(Array<Double> A, Array<Double> w) {
        sortWithWeights(A, w, 1, A.length);
        double weightSum = 0.0;
        int i = 1;
        while (i <= A.length && weightSum < 0.5) {
            weightSum += w.at(i);
            i++;
        }
        return A.at(i - 1);
    }

    // solution of 9-2(b)
    private static void sortWithWeights(Array<Double> A, Array<Double> w, int p, int r) {
        if (p < r) {
            int q = partitionWithWeights(A, w, p, r);
            sortWithWeights(A, w, p, q - 1);
            sortWithWeights(A, w, q + 1, r);
        }
    }

    // solution of 9-2(b)
    private static <T extends Comparable> int partitionWithWeights(Array<T> A, Array<Double> w, int p, int r) {
        T x = A.at(r);
        int i = p - 1;
        for (int j = p; j <= r - 1; j++) {
            if (less(A.at(j), x)) {
                i++;
                A.exch(i, j);
                w.exch(i, j);
            }
        }
        A.exch(i + 1, r);
        w.exch(i + 1, r);
        return i + 1;
    }

    // solution of 9-2(c)
    public static <T extends Comparable> T weightedMedian(Array<T> A, Array<Double> w, int p, int r) {
        if (r - p + 1 <= 2) {
            return w.at(p) >= w.at(r) ? A.at(p) : A.at(r);
        }
        partitionAroundMedianWithWeights(A, w, p, r);
        int q = (p + r) / 2;
        double WL = 0.0;
        for (int i = p; i <= q - 1; i++) {
            WL += w.at(i);
        }
        double WR = 1.0 - WL - w.at(q);
        if (WL < 0.5 && WR < 0.5) {
            return A.at(q);
        }
        if (WL >= 0.5) {
            w.set(q, w.at(q) + WR);
            return weightedMedian(A, w, p, q);
        } else {
            w.set(q, w.at(q) + WL);
            return weightedMedian(A, w, q, r);
        }
    }

    // solution of 9-2(c)
    private static <T extends Comparable> void partitionAroundMedianWithWeights(Array<T> A, Array<Double> w, int p, int r) {
        T x = select(new Array<>(A), p, r, (p + r) / 2);
        int q = p;
        while (!A.at(q).equals(x)) {
            q++;
        }
        A.exch(q, r);
        w.exch(q, r);
        partitionWithWeights(A, w, p, r);
    }

    // solution of 9-2(e)
    public static Point2D postOfficeLocation2D(Array<Point2D> A, Array<Double> w) {
        int n = A.length;
        Array<Double> X = new Array<>();
        Array<Double> Y = new Array<>();
        for (int i = 1; i <= n; i++) {
            X.set(i, A.at(i).x);
            Y.set(i, A.at(i).y);
        }
        double xp = weightedMedian(X, new Array<>(w), 1, n);
        double yp = weightedMedian(Y, new Array<>(w), 1, n);
        return new Point2D(xp, yp);
    }

}
