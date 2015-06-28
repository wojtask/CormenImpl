package pl.kwojtas.cormenimpl.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static pl.kwojtas.cormenimpl.TestUtil.assertArrayEquals;

public class ArrayTest {

    @Test
    public void shouldCreateArrayWithInitialContents() {
        // given
        String[] contents = new String[]{"aaa", "bbb", "ccc"};

        // when
        Array<String> array = new Array<>(contents);

        // then
        assertEquals(contents.length, array.length);
        for (int i = 1; i <= array.length; i++) {
            assertEquals(contents[i - 1], array.at(i));
        }
    }

    @Test
    public void shouldCreateArrayOfGivenLength() {
        // given

        // when
        Array<String> array = Array.withLength(3);

        // then
        assertEquals(3, array.length);
    }

    @Test
    public void shouldCreateArrayByCopyingOtherArray() {
        // given
        Array<String> otherArray = new Array<>("aaa", "bbb", "ccc");

        // when
        Array<String> array = new Array<>(otherArray);

        // then
        assertArrayEquals(otherArray, array);
    }

    @Test
    public void shouldReturnElementFromArray() {
        // given
        Array<String> array = new Array<>("aaa", "bbb", "ccc");

        // when
        String actualElement = array.at(2);

        // then
        assertEquals("bbb", actualElement);
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowExceptionWhenAccessingInvalidPositionInArray() {
        // given
        Array<String> array = new Array<>("aaa", "bbb", "ccc");

        try {
            // when
            array.at(5);
        } catch (RuntimeException e) {
            // then
            assertEquals("Array index out of bound", e.getMessage());
            throw e;
        }
    }

    @Test
    public void shouldSetElementInArray() {
        // given
        Array<String> array = new Array<>("aaa", "bbb", "ccc");
        String newElement = "xyz";

        // when
        array.set(3, newElement);

        // then
        assertEquals(newElement, array.at(3));
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowExceptionWhenSettingOnInvalidPositionInArray() {
        // given
        Array<String> array = new Array<>("aaa", "bbb", "ccc");

        try {
            // when
            array.set(5, "xyz");
        } catch (RuntimeException e) {
            // then
            assertEquals("Array index out of bound", e.getMessage());
            throw e;
        }
    }

    @Test
    public void shouldSetArrayContentsByCopyingOtherArray() {
        // given
        Array<String> otherArray = new Array<>("aaa", "bbb", "ccc");
        Array<String> array = Array.withLength(otherArray.length);

        // when
        array.set(otherArray);

        // then
        assertArrayEquals(otherArray, array);
    }

    @Test
    public void shouldSetArrayContentsByCopyingItself() {
        // given
        String[] contents = new String[]{"aaa", "bbb", "ccc"};
        Array<String> array = new Array<>(contents);

        // when
        array.set(array);

        // then
        assertEquals(contents.length, array.length);
        for (int i = 1; i <= array.length; i++) {
            assertEquals(contents[i - 1], array.at(i));
        }
    }

    @Test
    public void shouldExchangeTwoElementsInArray() {
        // given
        Array<String> array = new Array<>("aaa", "bbb", "ccc");

        // when
        array.exch(1, 3);

        // then
        assertEquals("ccc", array.at(1));
        assertEquals("aaa", array.at(3));
    }

}
