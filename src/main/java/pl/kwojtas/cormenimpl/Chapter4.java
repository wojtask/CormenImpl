package pl.kwojtas.cormenimpl;

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

}
