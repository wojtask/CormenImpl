package pl.kwojtas.cormenimpl.util;

import java.util.Arrays;

public class Young extends Matrix<Integer> {

    public Young(int rows, int columns) {
        super(matrixOfInfinities(rows, columns));
    }

    @SafeVarargs
    public Young(Array<Integer>... rows) {
        super(rows);
    }

    private static Integer[][] matrixOfInfinities(int rows, int columns) {
        Integer infinities[][] = new Integer[rows][];
        for (int i = 0; i < rows; i++) {
            infinities[i] = new Integer[columns];
            Arrays.fill(infinities[i], Integer.MAX_VALUE);
        }
        return infinities;
    }

}
