package pl.kwojtas.cormenimpl.util;

public class Matrix<T> {

    private T[][] data;
    public int rows;
    public int columns;

    @SafeVarargs
    public Matrix(Array<T>... rows) {
        this.data = (T[][]) new Object[rows.length][];
        for (int i = 0; i < rows.length; i++) {
            if (rows[i].length != rows[0].length) {
                throw new RuntimeException("Different sizes of rows");
            }
            this.data[i] = (T[]) new Object[rows[i].length];
            for (int j = 0; j < rows[i].length; j++) {
                this.data[i][j] = rows[i].at(j + 1);
            }
        }
        this.rows = rows.length;
        this.columns = rows.length > 0 ? rows[0].length : 0;
    }

    public Matrix(Array<Array<T>> rows) {
        this.data = (T[][]) new Object[rows.length][];
        for (int i = 0; i < rows.length; i++) {
            if (rows.at(i + 1).length != rows.at(1).length) {
                throw new RuntimeException("Different sizes of rows");
            }
            this.data[i] = (T[]) new Object[rows.at(i + 1).length];
            for (int j = 0; j < rows.at(i + 1).length; j++) {
                this.data[i][j] = rows.at(i + 1).at(j + 1);
            }
        }
        this.rows = rows.length;
        this.columns = rows.length > 0 ? rows.at(1).length : 0;
    }

    public Matrix(Matrix<T> otherMatrix) {
        if (this == otherMatrix) {
            return;
        }
        data = otherMatrix.data;
        rows = otherMatrix.rows;
        columns = otherMatrix.columns;
    }

    public Array<T> row(int row) {
        if (row < 1 || row > rows) {
            throw new RuntimeException("Matrix index out of bound");
        }
        return new Array<>(data[row - 1]);
    }

    public T at(int row, int column) {
        if (row < 1 || row > rows || column < 1 || column > columns) {
            throw new RuntimeException("Matrix index out of bound");
        }
        return data[row - 1][column - 1];
    }

    public void set(int row, int column, T element) {
        if (row < 1 || row > rows || column < 1 || column > columns) {
            throw new RuntimeException("Matrix index out of bound");
        }
        data[row - 1][column - 1] = element;
    }

    public void exch(int row1, int column1, int row2, int column2) {
        T swap = at(row1, column1);
        set(row1, column1, at(row2, column2));
        set(row2, column2, swap);
    }
}
