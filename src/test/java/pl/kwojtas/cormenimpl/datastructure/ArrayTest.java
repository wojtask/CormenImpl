package pl.kwojtas.cormenimpl.datastructure;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static pl.kwojtas.cormenimpl.TestUtil.assertArrayEquals;

public class ArrayTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldCreateArrayWithInitialContents() {
        String[] contents = new String[]{"aaa", "bbb", "ccc"};

        Array<String> array = Array.of(contents);

        assertEquals(contents.length, array.length);
        for (int i = 1; i <= array.length; i++) {
            assertEquals(contents[i - 1], array.at(i));
        }
    }

    @Test
    public void shouldCreateArrayOfGivenLength() {

        Array<String> array = Array.ofLength(3);

        assertEquals(3, array.length);
    }

    @Test
    public void shouldCreateArrayByCopyingOtherArray() {
        Array<String> otherArray = Array.of("aaa", "bbb", "ccc");

        Array<String> array = Array.copyOf(otherArray);

        assertArrayEquals(otherArray, array);
    }

    @Test
    public void shouldReturnElementFromArray() {
        Array<String> array = Array.of("aaa", "bbb", "ccc");

        String actualElement = array.at(2);

        assertEquals("bbb", actualElement);
    }

    @Test
    public void shouldThrowExceptionWhenAccessingInvalidPositionInArray() {
        Array<String> array = Array.of("aaa", "bbb", "ccc");

        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("Array index out of bound");
        array.at(5);
    }

    @Test
    public void shouldSetElementInArray() {
        Array<String> array = Array.of("aaa", "bbb", "ccc");
        String newElement = "xyz";

        array.set(3, newElement);

        assertEquals(newElement, array.at(3));
    }

    @Test
    public void shouldThrowExceptionWhenSettingOnInvalidPositionInArray() {
        Array<String> array = Array.of("aaa", "bbb", "ccc");

        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("Array index out of bound");
        array.set(5, "xyz");
    }

    @Test
    public void shouldSetArrayContentsByCopyingOtherArray() {
        Array<String> otherArray = Array.of("aaa", "bbb", "ccc");
        Array<String> array = Array.ofLength(otherArray.length);

        array.set(otherArray);

        assertArrayEquals(otherArray, array);
    }

    @Test
    public void shouldSetArrayContentsByCopyingItself() {
        String[] contents = new String[]{"aaa", "bbb", "ccc"};
        Array<String> array = Array.of(contents);

        array.set(array);

        assertEquals(contents.length, array.length);
        for (int i = 1; i <= array.length; i++) {
            assertEquals(contents[i - 1], array.at(i));
        }
    }

    @Test
    public void shouldExchangeTwoElementsInArray() {
        Array<String> array = Array.of("aaa", "bbb", "ccc");

        array.exch(1, 3);

        assertEquals("ccc", array.at(1));
        assertEquals("aaa", array.at(3));
    }

}
