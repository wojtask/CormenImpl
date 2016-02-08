package pl.kwojtas.cormenimpl.datastructure;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class ZeroBasedIndexedArrayTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldCreateZeroBasedIndexedArrayWithInitialContents() {
        String[] contents = new String[]{"aaa", "bbb", "ccc"};

        ZeroBasedIndexedArray<String> zeroBasedIndexedArray = new ZeroBasedIndexedArray<>(contents);

        assertEquals(contents.length, zeroBasedIndexedArray.length);
        for (int i = 0; i <= zeroBasedIndexedArray.length - 1; i++) {
            assertEquals(contents[i], zeroBasedIndexedArray.at(i));
        }
    }

    @Test
    public void shouldCreateZeroBasedIndexedArrayOfGivenLength() {

        ZeroBasedIndexedArray<String> zeroBasedIndexedArray = ZeroBasedIndexedArray.withLength(3);

        assertEquals(3, zeroBasedIndexedArray.length);
    }

    @Test
    public void shouldCreateZeroBasedIndexedArrayByCopyingOtherZeroBasedIndexedArray() {
        ZeroBasedIndexedArray<String> otherZeroBasedIndexedArray = new ZeroBasedIndexedArray<>("aaa", "bbb", "ccc");

        ZeroBasedIndexedArray<String> zeroBasedIndexedArray = new ZeroBasedIndexedArray<>(otherZeroBasedIndexedArray);

        assertEquals(otherZeroBasedIndexedArray.length, zeroBasedIndexedArray.length);
        for (int i = 0; i <= zeroBasedIndexedArray.length - 1; i++) {
            assertEquals(otherZeroBasedIndexedArray.at(i), zeroBasedIndexedArray.at(i));
        }
    }

    @Test
    public void shouldReturnElementFromZeroBasedIndexedArray() {
        ZeroBasedIndexedArray<String> zeroBasedIndexedArray = new ZeroBasedIndexedArray<>("aaa", "bbb", "ccc");

        String actualElement = zeroBasedIndexedArray.at(2);

        assertEquals("ccc", actualElement);
    }

    @Test
    public void shouldThrowExceptionWhenAccessingInvalidPositionInZeroBasedIndexedArray() {
        ZeroBasedIndexedArray<String> zeroBasedIndexedArray = new ZeroBasedIndexedArray<>("aaa", "bbb", "ccc");

        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("Array index out of bound");
        zeroBasedIndexedArray.at(5);
    }

    @Test
    public void shouldSetElementInZeroBasedIndexedArray() {
        ZeroBasedIndexedArray<String> zeroBasedIndexedArray = new ZeroBasedIndexedArray<>("aaa", "bbb", "ccc");
        String newElement = "xyz";

        zeroBasedIndexedArray.set(0, newElement);

        assertEquals(newElement, zeroBasedIndexedArray.at(0));
    }

    @Test
    public void shouldThrowExceptionWhenSettingOnInvalidPositionInZeroBasedIndexedArray() {
        ZeroBasedIndexedArray<String> zeroBasedIndexedArray = new ZeroBasedIndexedArray<>("aaa", "bbb", "ccc");

        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("Array index out of bound");
        zeroBasedIndexedArray.set(5, "xyz");
    }

    @Test
    public void shouldSetZeroBasedIndexedArrayContentsByCopyingOtherZeroBasedIndexedArray() {
        ZeroBasedIndexedArray<String> otherZeroBasedIndexedArray = new ZeroBasedIndexedArray<>("aaa", "bbb", "ccc");
        ZeroBasedIndexedArray<String> zeroBasedIndexedArray = ZeroBasedIndexedArray.withLength(otherZeroBasedIndexedArray.length);

        zeroBasedIndexedArray.set(otherZeroBasedIndexedArray);

        assertEquals(otherZeroBasedIndexedArray.length, zeroBasedIndexedArray.length);
        for (int i = 0; i <= zeroBasedIndexedArray.length - 1; i++) {
            assertEquals(otherZeroBasedIndexedArray.at(i), zeroBasedIndexedArray.at(i));
        }
    }

    @Test
    public void shouldSetZeroBasedIndexedArrayContentsByCopyingItself() {
        String[] contents = new String[]{"aaa", "bbb", "ccc"};
        ZeroBasedIndexedArray<String> zeroBasedIndexedArray = new ZeroBasedIndexedArray<>(contents);

        zeroBasedIndexedArray.set(zeroBasedIndexedArray);

        assertEquals(contents.length, zeroBasedIndexedArray.length);
        for (int i = 0; i < zeroBasedIndexedArray.length - 1; i++) {
            assertEquals(contents[i], zeroBasedIndexedArray.at(i));
        }
    }

    @Test
    public void shouldExchangeTwoElementsInZeroBasedIndexedArray() {
        ZeroBasedIndexedArray<String> zeroBasedIndexedArray = new ZeroBasedIndexedArray<>("aaa", "bbb", "ccc");

        zeroBasedIndexedArray.exch(0, 2);

        assertEquals("ccc", zeroBasedIndexedArray.at(0));
        assertEquals("aaa", zeroBasedIndexedArray.at(2));
    }

}
