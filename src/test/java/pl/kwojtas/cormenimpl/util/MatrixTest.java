package pl.kwojtas.cormenimpl.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static pl.kwojtas.cormenimpl.TestUtil.assertArrayEquals;

public class MatrixTest {

    @Test
    public void shouldCreateMatrixFromGivenRows() {
        // given
        Array<String> row1 = new Array<>("aaa", "bbb", "ccc");
        Array<String> row2 = new Array<>("ddd", "eee", "fff");

        // when
        Matrix<String> matrix = new Matrix<>(row1, row2);

        // then
        assertEquals(2, matrix.rows);
        assertEquals(row1.length, matrix.columns);
        assertArrayEquals(row1, matrix.row(1));
        assertArrayEquals(row2, matrix.row(2));
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowExceptionWhenCreatingMatrixFromRowsOfDifferentSizes() {
        // given
        Array<String> row1 = new Array<>("aaa", "bbb", "ccc");
        Array<String> row2 = new Array<>("ddd", "eee");

        try {
            // when
            new Matrix<>(row1, row2);
        } catch (RuntimeException e) {
            // then
            assertEquals("Different sizes of rows", e.getMessage());
            throw e;
        }
    }

    @Test
    public void shouldCreateMatrixFromGivenArrayOfRows() {
        // given
        Array<Array<String>> rows = new Array<>(new Array<>("aaa", "bbb", "ccc"), new Array<>("ddd", "eee", "fff"));

        // when
        Matrix<String> matrix = new Matrix<>(rows);

        // then
        assertEquals(rows.length, matrix.rows);
        assertEquals(rows.at(1).length, matrix.columns);
        for (int i = 1; i <= rows.length; i++) {
            assertArrayEquals(rows.at(i), matrix.row(i));
        }
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowExceptionWhenCreatingMatrixFromArrayOfRowsOfDifferentSizes() {
        // given
        Array<Array<String>> rows = new Array<>(new Array<>("aaa", "bbb", "ccc"), new Array<>("ddd", "eee"));

        try {
            // when
            new Matrix<>(rows);
        } catch (RuntimeException e) {
            // then
            assertEquals("Different sizes of rows", e.getMessage());
            throw e;
        }
    }

    @Test
    public void shouldReturnRowFromMatrix() {
        // given
        Array<String> row1 = new Array<>("aaa", "bbb", "ccc");
        Array<String> row2 = new Array<>("ddd", "eee", "fff");
        Matrix<String> matrix = new Matrix<>(row1, row2);

        // when
        Array<String> actualRow = matrix.row(2);

        // then
        assertArrayEquals(row2, actualRow);
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowExceptionWhenAccessingInvalidRowFromMatrix() {
        // given
        Matrix<String> matrix = new Matrix<>(new Array<>("aaa", "bbb", "ccc"), new Array<>("ddd", "eee", "fff"));

        try {
            // when
            matrix.row(5);
        } catch (RuntimeException e) {
            // then
            assertEquals("Row index out of bound", e.getMessage());
            throw e;
        }
    }

    @Test
    public void shouldReturnElementFromMatrix() {
        // given
        Matrix<String> matrix = new Matrix<>(new Array<>("aaa", "bbb", "ccc"), new Array<>("ddd", "eee", "fff"));

        // when
        String actualElement = matrix.at(2, 1);

        // then
        assertEquals("ddd", actualElement);
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowExceptionWhenAccessingInvalidPositionInMatrix() {
        // given
        Matrix<String> matrix = new Matrix<>(new Array<>("aaa", "bbb", "ccc"), new Array<>("ddd", "eee", "fff"));

        try {
            // when
            matrix.at(1, 5);
        } catch (RuntimeException e) {
            // then
            assertEquals("Row index or column index out of bound", e.getMessage());
            throw e;
        }
    }

    @Test
    public void shouldSetElementInMatrix() {
        // given
        Matrix<String> matrix = new Matrix<>(new Array<>("aaa", "bbb", "ccc"), new Array<>("ddd", "eee", "fff"));
        String newElement = "xyz";

        // when
        matrix.set(1, 3, newElement);

        // then
        assertEquals(newElement, matrix.at(1, 3));
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowExceptionWhenSettingOnInvalidPositionInMatrix() {
        // given
        Matrix<String> matrix = new Matrix<>(new Array<>("aaa", "bbb", "ccc"), new Array<>("ddd", "eee", "fff"));

        try {
            // when
            matrix.set(1, 5, "xyz");
        } catch (RuntimeException e) {
            // then
            assertEquals("Row index or column index out of bound", e.getMessage());
            throw e;
        }
    }

    @Test
    public void shouldExchangeTwoElementsInMatrix() {
        // given
        Matrix<String> matrix = new Matrix<>(new Array<>("aaa", "bbb", "ccc"), new Array<>("ddd", "eee", "fff"));

        // when
        matrix.exch(2, 1, 1, 3);

        // then
        assertEquals("ccc", matrix.row(2).at(1));
        assertEquals("ddd", matrix.row(1).at(3));
    }

}
