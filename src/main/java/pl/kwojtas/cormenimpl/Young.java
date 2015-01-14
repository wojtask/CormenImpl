package pl.kwojtas.cormenimpl;

public class Young {

    private Matrix<Integer> data;

    public Young(int rows, int columns) {
        Integer infinities[][] = new Integer[rows][];
        for (int i = 0; i < rows; i++) {
            infinities[i] = new Integer[columns];
            for (int j = 0; j < columns; j++) {
                infinities[i][j] = Integer.MAX_VALUE;
            }
        }
        this.data = new Matrix<>(infinities);
    }

    public int at(int row, int column) {
        return data.at(row, column);
    }

    public void set(int row, int column, int key) {
        data.set(row, column, key);
    }

    public void exch(int row1, int column1, int row2, int column2) {
        data.exch(row1, column1, row2, column2);
    }

}
