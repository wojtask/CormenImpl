package pl.kwojtas.cormenimpl;

import pl.kwojtas.cormenimpl.util.Array;
import pl.kwojtas.cormenimpl.util.Matrix;

/**
 * Implements algorithms from Chapter 4.
 */
public final class Chapter4 {

    private Chapter4() { }

    /**
     * Looks for missing integer in an array.
     * <p>Solution to problem 4-2.</p>
     *
     * @param A the {@code n}-element {@link Array} containing all integers from {@code 0..n} but one
     * @return the missing integer in {@code A}
     */
    public static Integer findMissingInteger(Array<Integer> A) {
        A = extendArrayWithExtraIntegers(A);
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

    /**
     * Extends an array with consecutive integers up to the next power of 2 minus 1.
     *
     * @param A the {@code n}-element {@link Array} containing all integers from {@code 0..n} but one
     * @return the {@code 2^k-1}-element {@link Array} containing all integers from {@code 0..2^k-1} but one,
     * where {@code 2^k} is the least power of 2 greater than {@code n}
     */
    private static Array<Integer> extendArrayWithExtraIntegers(Array<Integer> A) {
        int n = A.length;
        while (!isPowerOf2Minus1(n)) {
            n++;
        }
        Array<Integer> extended = Array.withLength(n);
        for (int i = 1; i <= A.length; i++) {
            extended.set(i, A.at(i));
        }
        for (int i = A.length + 1; i <= n; i++) {
            extended.set(i, i);
        }
        return extended;
    }

    /**
     * Returns {@code true} if a number is one less than a power of 2.
     *
     * @param n the number
     * @return {@code true} if {@code n} is one less than a power of 2
     */
    private static boolean isPowerOf2Minus1(int n) {
        while (n > 0) {
            if (n % 2 == 0) {
                return false;
            }
            n /= 2;
        }
        return true;
    }

    /**
     * Returns the bit length of an number.
     *
     * @param n the number
     * @return the bit length of {@code n}
     */
    private static int getBitLength(int n) {
        int bitLength = 0;
        while (n > 0) {
            bitLength++;
            n /= 2;
        }
        return bitLength;
    }

    /**
     * Fetches the {@code j}-th bit of {@code A[i]}.
     *
     * @param j the bit number to get (0 - the least significant)
     * @param A the {@link Array} of numbers
     * @param i the index of number in {@code A} whose {@code j}-th bit needs to be fetched
     * @return the {@code j}-th bit of {@code A[i]}
     */
    private static boolean getBit(int j, Array<Integer> A, int i) {
        return (A.at(i) & (1 << j)) != 0;
    }

    /**
     * Returns the leftmost minimum indexes in each row of a Monge array.
     * <p>Solution to problem 4-7(d).</p>
     *
     * @param A the {@link Matrix} containing the Monge array
     * @return the {@link Array} in which the {@code i}-th position is
     * the leftmost minimum index in the {@code i}-th row of {@code A}
     */
    public static Array<Integer> mongeLeftmostMinimaIndexes(Matrix<Double> A) {
        int m = A.rows;
        int n = A.columns;
        Array<Integer> leftmostMinimaIndexes = Array.withLength(m);
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

    /**
     * Returns the leftmost minimum indexes in even rows of a Monge array.
     *
     * @param A the {@link Matrix} containing the Monge array
     * @return the {@link Array} in which the {@code i}-th position is
     * the leftmost minimum index in the {@code (2i)}-th row of {@code A}
     */
    private static Array<Integer> mongeLeftmostMinimaIndexesOfEvenRows(Matrix<Double> A) {
        int m = A.rows;
        Array<Array<Double>> oddRows = Array.withLength(m / 2);
        for (int i = 2; i <= m; i += 2) {
            int n = A.row(i).length;
            oddRows.set(i / 2, Array.withLength(n));
            for (int j = 1; j <= n; j++) {
                oddRows.at(i / 2).set(j, A.row(i).at(j));
            }
        }
        Matrix<Double> A_ = new Matrix<>(oddRows);
        return mongeLeftmostMinimaIndexes(A_);
    }

    /**
     * Returns the leftmost minimum index in a row of a Monge array.
     *
     * @param A the {@link Matrix} containing the Monge array
     * @param row the row of {@code A}
     * @param minimumIndexAbove the leftmost minimum index in a row above {@code row}
     * @param minimumIndexBelow the leftmost minimum index in a row below {@code row}
     * @return the leftmost minimum index in row {@code row} of {@code A}
     */
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
