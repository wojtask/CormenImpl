package pl.kwojtas.cormenimpl;

import pl.kwojtas.cormenimpl.util.Array;

/**
 * Implements algorithms from Chapter 4.
 */
public final class Chapter4 {

    private Chapter4() {
    }

    /**
     * Searches for the missing integer in an array.
     * <p>Solution to problem 4-2.</p>
     *
     * @param A the {@code n}-element array containing all integers from {@code 0..n} but one
     * @return the missing integer in {@code A}
     */
    public static Integer findMissingInteger(Array<Integer> A) {
        A = extendArrayWithExtraIntegers(A);
        int n = A.length;
        Array<Integer> positions = Array.withLength(n);
        for (int i = 1; i <= n; i++) {
            positions.set(i, i);
        }
        Integer missingInteger = 0;
        int j = 0;
        while (n > 0) {
            Array<Integer> positionsOfZeroBitNumbers = Array.withLength((n - 1) / 2);
            Array<Integer> positionsOfOneBitNumbers = Array.withLength((n - 1) / 2);
            int zerosFound = 0;
            int onesFound = 0;
            for (int i = 1; i <= n; i++) {
                if (getBit(j, A, positions.at(i)) == 0) {
                    zerosFound++;
                    // we don't care for more than (n - 1) / 2 numbers because in the next step
                    // we will be working on the other array which will have length = (n - 1) / 2
                    if (zerosFound <= positionsOfZeroBitNumbers.length) {
                        positionsOfZeroBitNumbers.set(zerosFound, positions.at(i));
                    }
                } else {
                    onesFound++;
                    if (onesFound <= positionsOfOneBitNumbers.length) {
                        positionsOfOneBitNumbers.set(onesFound, positions.at(i));
                    }
                }
            }
            if (zerosFound == (n - 1) / 2) {
                positions = positionsOfZeroBitNumbers;
            } else {
                positions = positionsOfOneBitNumbers;
                missingInteger |= (1 << j);
            }
            j++;
            n = (n - 1) / 2;
        }
        return missingInteger;
    }

    private static Array<Integer> extendArrayWithExtraIntegers(Array<Integer> A) {
        int n = nextPowerOf2Minus1(A.length);
        Array<Integer> extended = Array.withLength(n);
        for (int i = 1; i <= A.length; i++) {
            extended.set(i, A.at(i));
        }
        for (int i = A.length + 1; i <= n; i++) {
            extended.set(i, i);
        }
        return extended;
    }

    private static int nextPowerOf2Minus1(int n) {
        int result = 1;
        while (result <= n) {
            result <<= 1;
        }
        return result - 1;
    }

    private static int getBit(int j, Array<Integer> A, int i) {
        return A.at(i) & (1 << j);
    }

    /**
     * Returns the leftmost minimum indices in each row of a Monge array.
     * <p>Solution to problem 4-7(d).</p>
     *
     * @param A the Monge array
     * @return the array containing at the {@code i}-th position the leftmost minimum index in the {@code i}-th row of {@code A}
     */
    public static Array<Integer> mongeLeftmostMinimaIndices(Array<Array<Double>> A) {
        int m = A.length;
        int n = A.at(1).length;
        Array<Integer> leftmostMinimaIndices = Array.withLength(m);
        if (m >= 2) {
            Array<Integer> leftmostMinimaIndicesOfEvenRows = mongeLeftmostMinimaIndicesOfEvenRows(A);
            for (int i = 2; i <= m; i += 2) {
                leftmostMinimaIndices.set(i, leftmostMinimaIndicesOfEvenRows.at(i / 2));
            }
        }
        for (int i = 1; i <= m; i += 2) {
            int minimumIndexAbove = i > 1 ? leftmostMinimaIndices.at(i - 1) : 1;
            int minimumIndexBelow = i < m ? leftmostMinimaIndices.at(i + 1) : n;
            leftmostMinimaIndices.set(i, mongeLeftmostMinimumIndex(A, i, minimumIndexAbove, minimumIndexBelow));
        }
        return leftmostMinimaIndices;
    }

    private static Array<Integer> mongeLeftmostMinimaIndicesOfEvenRows(Array<Array<Double>> A) {
        int m = A.length;
        Array<Array<Double>> oddRows = Array.withLength(m / 2);
        for (int i = 2; i <= m; i += 2) {
            int n = A.at(i).length;
            oddRows.set(i / 2, Array.withLength(n));
            for (int j = 1; j <= n; j++) {
                oddRows.at(i / 2).set(j, A.at(i).at(j));
            }
        }
        Array<Array<Double>> A_ = new Array<>(oddRows);
        return mongeLeftmostMinimaIndices(A_);
    }

    private static int mongeLeftmostMinimumIndex(Array<Array<Double>> A, int row, int minimumIndexAbove, int minimumIndexBelow) {
        int leftmostMinimumIndex = minimumIndexAbove;
        double leftmostMinimum = A.at(row).at(leftmostMinimumIndex);
        for (int column = minimumIndexAbove + 1; column <= minimumIndexBelow; column++) {
            if (A.at(row).at(column) < leftmostMinimum) {
                leftmostMinimum = A.at(row).at(column);
                leftmostMinimumIndex = column;
            }
        }
        return leftmostMinimumIndex;
    }

}
