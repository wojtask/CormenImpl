package pl.kwojtas.cormenimpl.util;

public class Young extends Matrix<Integer> {

    public Young(int rows, int columns) {
        super(matrixOfInfinities(rows, columns));
    }

    private static Array<Array<Integer>> matrixOfInfinities(int rows, int columns) {
        Array<Array<Integer>> infinities = Array.withLength(rows);
        for (int i = 1; i <= rows; i++) {
            infinities.set(i, Array.withLength(columns));
            for (int j = 1; j <= columns; j++) {
                infinities.at(i).set(j, Integer.MAX_VALUE);
            }
        }
        return infinities;
    }

    @SafeVarargs
    public Young(Array<Integer>... rows) {
        super(rows);
    }

}
