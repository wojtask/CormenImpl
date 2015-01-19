package pl.kwojtas.cormenimpl;

import pl.kwojtas.cormenimpl.util.Array;
import pl.kwojtas.cormenimpl.util.Matrix;

public class Chapter4 {

    private Chapter4() { }

    // solution of 4-2
    public static Integer findMissingInteger(Array<Integer> A) {
        extendArrayWithExtraIntegers(A);
        int n = A.length;
        Integer missingInteger = 0;
        for (int j = 0; j <= getBitLength(n) - 1; j++) {
            int actualOnes = 0;
            for (int i = 1; i <= n; i++) {
                if (getBit(j, A, i)) {
                    actualOnes++;
                }
            }
            if (actualOnes < (n + 1) / 2) {
                missingInteger |= (1 << j);
            }
        }
        return missingInteger;
    }

    // solution of 4-2
    private static void extendArrayWithExtraIntegers(Array<Integer> A) {
        int n = A.length;
        while (!isPowerOf2Minus1(n)) {
            n++;
            A.set(n, n);
        }
    }

    // solution of 4-2
    private static boolean isPowerOf2Minus1(int n) {
        while (n > 0) {
            if (n % 2 == 0) {
                return false;
            }
            n /= 2;
        }
        return true;
    }

    // solution of 4-2
    private static int getBitLength(int n) {
        int bitLength = 0;
        while (n > 0) {
            bitLength++;
            n /= 2;
        }
        return bitLength;
    }

    // solution of 4-2
    private static boolean getBit(int j, Array<Integer> A, int i) {
        return (A.at(i) & (1 << j)) != 0;
    }

    // solution of 4-7(d)
    public static Array<Integer> mongeLeftmostMinimaIndexes(Matrix<Double> A) {
        int m = A.rows;
        int n = A.columns;
        Array<Integer> leftmostMinimaIndexes = new Array<>();
        if (m >= 2) {
            Array<Integer> leftmostMinimaIndexesOfEvenRows = mongeLeftmostMinimaIndexesOfEvenRows(A);
            for (int i = 2; i <= m; i += 2) {
                leftmostMinimaIndexes.set(i, leftmostMinimaIndexesOfEvenRows.at(i / 2));
            }
        }
        for (int i = 1; i <= m; i += 2) {
            int minimumIndexAbove = i > 1 ? leftmostMinimaIndexes.at(i - 1) : 1;
            int minimumIndexBelow = i < m ? leftmostMinimaIndexes.at(i + 1) : n;
            leftmostMinimaIndexes.set(i, mongeLeftmostMinimumIndex(A, i, minimumIndexAbove, minimumIndexBelow));
        }
        return leftmostMinimaIndexes;
    }

    // solution of 4-7(d)
    @SuppressWarnings("unchecked")
    private static Array<Integer> mongeLeftmostMinimaIndexesOfEvenRows(Matrix<Double> A) {
        int m = A.rows;
        Array<Double>[] oddRows = new Array[m / 2];
        for (int i = 2; i <= m; i += 2) {
            oddRows[i / 2 - 1] = A.row(i);
        }
        Matrix<Double> A_ = new Matrix<>(oddRows);
        return mongeLeftmostMinimaIndexes(A_);
    }

    // solution of 4-7(d)
    private static int mongeLeftmostMinimumIndex(Matrix<Double> A, int row, int minimumIndexAbove, int minimumIndexBelow) {
        int leftmostMinimumIndex = minimumIndexAbove;
        double leftmostMinimum = A.at(row, leftmostMinimumIndex);
        for (int column = minimumIndexAbove + 1; column <= minimumIndexBelow; column++) {
            if (A.at(row, column) < leftmostMinimum) {
                leftmostMinimum = A.at(row, column);
                leftmostMinimumIndex = column;
            }
        }
        return leftmostMinimumIndex;
    }

}
