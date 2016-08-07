package pl.kwojtas.cormenimpl.util;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static pl.kwojtas.cormenimpl.TestUtil.assertArrayEquals;

public class MatrixTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldCreateMatrixFromGivenRows() {
        Array<String> row1 = new Array<>("aaa", "bbb", "ccc");
        Array<String> row2 = new Array<>("ddd", "eee", "fff");

        Matrix<String> matrix = new Matrix<>(row1, row2);

        assertEquals(2, matrix.rows);
        assertEquals(row1.length, matrix.columns);
        assertArrayEquals(row1, matrix.row(1));
        assertArrayEquals(row2, matrix.row(2));
    }

    @Test
    public void shouldThrowExceptionWhenCreatingMatrixFromRowsOfDifferentSizes() {
        Array<String> row1 = new Array<>("aaa", "bbb", "ccc");
        Array<String> row2 = new Array<>("ddd", "eee");

        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("Different sizes of rows");
        new Matrix<>(row1, row2);
    }

    @Test
    public void shouldCreateMatrixFromGivenArrayOfRows() {
        Array<Array<String>> rows = new Array<>(new Array<>("aaa", "bbb", "ccc"), new Array<>("ddd", "eee", "fff"));

        Matrix<String> matrix = new Matrix<>(rows);

        assertEquals(rows.length, matrix.rows);
        assertEquals(rows.at(1).length, matrix.columns);
        for (int i = 1; i <= rows.length; i++) {
            assertArrayEquals(rows.at(i), matrix.row(i));
        }
    }

    @Test
    public void shouldThrowExceptionWhenCreatingMatrixFromArrayOfRowsOfDifferentSizes() {
        Array<Array<String>> rows = new Array<>(new Array<>("aaa", "bbb", "ccc"), new Array<>("ddd", "eee"));

        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("Different sizes of rows");
        new Matrix<>(rows);
    }

    @Test
    public void shouldReturnRowFromMatrix() {
        Array<String> row1 = new Array<>("aaa", "bbb", "ccc");
        Array<String> row2 = new Array<>("ddd", "eee", "fff");
        Matrix<String> matrix = new Matrix<>(row1, row2);

        Array<String> actualRow = matrix.row(2);

        assertArrayEquals(row2, actualRow);
    }

    @Test
    public void shouldThrowExceptionWhenAccessingInvalidRowFromMatrix() {
        Matrix<String> matrix = new Matrix<>(new Array<>("aaa", "bbb", "ccc"), new Array<>("ddd", "eee", "fff"));

        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("Row index out of bound");
        matrix.row(5);
    }

    @Test
    public void shouldReturnElementFromMatrix() {
        Matrix<String> matrix = new Matrix<>(new Array<>("aaa", "bbb", "ccc"), new Array<>("ddd", "eee", "fff"));

        String actualElement = matrix.at(2, 1);

        assertEquals("ddd", actualElement);
    }

    @Test
    public void shouldThrowExceptionWhenAccessingInvalidPositionInMatrix() {
        Matrix<String> matrix = new Matrix<>(new Array<>("aaa", "bbb", "ccc"), new Array<>("ddd", "eee", "fff"));

        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("Row index or column index out of bound");
        matrix.at(1, 5);
    }

    @Test
    public void shouldSetElementInMatrix() {
        Matrix<String> matrix = new Matrix<>(new Array<>("aaa", "bbb", "ccc"), new Array<>("ddd", "eee", "fff"));
        String newElement = "xyz";

        matrix.set(1, 3, newElement);

        assertEquals(newElement, matrix.at(1, 3));
    }

    @Test
    public void shouldThrowExceptionWhenSettingOnInvalidPositionInMatrix() {
        Matrix<String> matrix = new Matrix<>(new Array<>("aaa", "bbb", "ccc"), new Array<>("ddd", "eee", "fff"));

        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("Row index or column index out of bound");
        matrix.set(1, 5, "xyz");
    }

    @Test
    public void shouldExchangeTwoElementsInMatrix() {
        Matrix<String> matrix = new Matrix<>(new Array<>("aaa", "bbb", "ccc"), new Array<>("ddd", "eee", "fff"));

        matrix.exch(2, 1, 1, 3);

        assertEquals("ccc", matrix.row(2).at(1));
        assertEquals("ddd", matrix.row(1).at(3));
    }

}
