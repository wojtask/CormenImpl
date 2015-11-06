package pl.kwojtas.cormenimpl.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static pl.kwojtas.cormenimpl.TestUtil.assertArrayEquals;

public class ArrayTest {

    @Test
    public void shouldCreateArrayWithInitialContents() {
        String[] contents = new String[]{"aaa", "bbb", "ccc"};

        Array<String> array = new Array<>(contents);

        assertEquals(contents.length, array.length);
        for (int i = 1; i <= array.length; i++) {
            assertEquals(contents[i - 1], array.at(i));
        }
    }

    @Test
    public void shouldCreateArrayOfGivenLength() {

        Array<String> array = Array.withLength(3);

        assertEquals(3, array.length);
    }

    @Test
    public void shouldCreateArrayByCopyingOtherArray() {
        Array<String> otherArray = new Array<>("aaa", "bbb", "ccc");

        Array<String> array = new Array<>(otherArray);

        assertArrayEquals(otherArray, array);
    }

    @Test
    public void shouldReturnElementFromArray() {
        Array<String> array = new Array<>("aaa", "bbb", "ccc");

        String actualElement = array.at(2);

        assertEquals("bbb", actualElement);
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowExceptionWhenAccessingInvalidPositionInArray() {
        Array<String> array = new Array<>("aaa", "bbb", "ccc");

        try {
            array.at(5);
        } catch (RuntimeException e) {
            assertEquals("Array index out of bound", e.getMessage());
            throw e;
        }
    }

    @Test
    public void shouldSetElementInArray() {
        Array<String> array = new Array<>("aaa", "bbb", "ccc");
        String newElement = "xyz";

        array.set(3, newElement);

        assertEquals(newElement, array.at(3));
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowExceptionWhenSettingOnInvalidPositionInArray() {
        Array<String> array = new Array<>("aaa", "bbb", "ccc");

        try {
            array.set(5, "xyz");
        } catch (RuntimeException e) {
            assertEquals("Array index out of bound", e.getMessage());
            throw e;
        }
    }

    @Test
    public void shouldSetArrayContentsByCopyingOtherArray() {
        Array<String> otherArray = new Array<>("aaa", "bbb", "ccc");
        Array<String> array = Array.withLength(otherArray.length);

        array.set(otherArray);

        assertArrayEquals(otherArray, array);
    }

    @Test
    public void shouldSetArrayContentsByCopyingItself() {
        String[] contents = new String[]{"aaa", "bbb", "ccc"};
        Array<String> array = new Array<>(contents);

        array.set(array);

        assertEquals(contents.length, array.length);
        for (int i = 1; i <= array.length; i++) {
            assertEquals(contents[i - 1], array.at(i));
        }
    }

    @Test
    public void shouldExchangeTwoElementsInArray() {
        Array<String> array = new Array<>("aaa", "bbb", "ccc");

        array.exch(1, 3);

        assertEquals("ccc", array.at(1));
        assertEquals("aaa", array.at(3));
    }

}
