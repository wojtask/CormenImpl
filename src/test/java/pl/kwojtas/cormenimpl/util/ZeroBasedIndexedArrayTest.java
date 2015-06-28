package pl.kwojtas.cormenimpl.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ZeroBasedIndexedArrayTest {

    @Test
    public void shouldCreateZeroBasedIndexedArrayWithInitialContents() {
        // given
        String[] contents = new String[]{"aaa", "bbb", "ccc"};

        // when
        ZeroBasedIndexedArray<String> zeroBasedIndexedArray = new ZeroBasedIndexedArray<>(contents);

        // then
        assertEquals(contents.length, zeroBasedIndexedArray.length);
        for (int i = 0; i <= zeroBasedIndexedArray.length - 1; i++) {
            assertEquals(contents[i], zeroBasedIndexedArray.at(i));
        }
    }

    @Test
    public void shouldCreateZeroBasedIndexedArrayOfGivenLength() {
        // given

        // when
        ZeroBasedIndexedArray<String> zeroBasedIndexedArray = ZeroBasedIndexedArray.withLength(3);

        // then
        assertEquals(3, zeroBasedIndexedArray.length);
    }

    @Test
    public void shouldCreateZeroBasedIndexedArrayByCopyingOtherZeroBasedIndexedArray() {
        // given
        ZeroBasedIndexedArray<String> otherZeroBasedIndexedArray = new ZeroBasedIndexedArray<>("aaa", "bbb", "ccc");

        // when
        ZeroBasedIndexedArray<String> zeroBasedIndexedArray = new ZeroBasedIndexedArray<>(otherZeroBasedIndexedArray);

        // then
        assertEquals(otherZeroBasedIndexedArray.length, zeroBasedIndexedArray.length);
        for (int i = 0; i <= zeroBasedIndexedArray.length - 1; i++) {
            assertEquals(otherZeroBasedIndexedArray.at(i), zeroBasedIndexedArray.at(i));
        }
    }

    @Test
    public void shouldReturnElementFromZeroBasedIndexedArray() {
        // given
        ZeroBasedIndexedArray<String> zeroBasedIndexedArray = new ZeroBasedIndexedArray<>("aaa", "bbb", "ccc");

        // when
        String actualElement = zeroBasedIndexedArray.at(2);

        // then
        assertEquals("ccc", actualElement);
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowExceptionWhenAccessingInvalidPositionInZeroBasedIndexedArray() {
        // given
        ZeroBasedIndexedArray<String> zeroBasedIndexedArray = new ZeroBasedIndexedArray<>("aaa", "bbb", "ccc");

        try {
            // when
            zeroBasedIndexedArray.at(5);
        } catch (RuntimeException e) {
            // then
            assertEquals("Array index out of bound", e.getMessage());
            throw e;
        }
    }

    @Test
    public void shouldSetElementInZeroBasedIndexedArray() {
        // given
        ZeroBasedIndexedArray<String> zeroBasedIndexedArray = new ZeroBasedIndexedArray<>("aaa", "bbb", "ccc");
        String newElement = "xyz";

        // when
        zeroBasedIndexedArray.set(0, newElement);

        // then
        assertEquals(newElement, zeroBasedIndexedArray.at(0));
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowExceptionWhenSettingOnInvalidPositionInZeroBasedIndexedArray() {
        // given
        ZeroBasedIndexedArray<String> zeroBasedIndexedArray = new ZeroBasedIndexedArray<>("aaa", "bbb", "ccc");

        try {
            // when
            zeroBasedIndexedArray.set(5, "xyz");
        } catch (RuntimeException e) {
            // then
            assertEquals("Array index out of bound", e.getMessage());
            throw e;
        }
    }

    @Test
    public void shouldSetZeroBasedIndexedArrayContentsByCopyingOtherZeroBasedIndexedArray() {
        // given
        ZeroBasedIndexedArray<String> otherZeroBasedIndexedArray = new ZeroBasedIndexedArray<>("aaa", "bbb", "ccc");
        ZeroBasedIndexedArray<String> zeroBasedIndexedArray = ZeroBasedIndexedArray.withLength(otherZeroBasedIndexedArray.length);

        // when
        zeroBasedIndexedArray.set(otherZeroBasedIndexedArray);

        // then
        assertEquals(otherZeroBasedIndexedArray.length, zeroBasedIndexedArray.length);
        for (int i = 0; i <= zeroBasedIndexedArray.length - 1; i++) {
            assertEquals(otherZeroBasedIndexedArray.at(i), zeroBasedIndexedArray.at(i));
        }
    }

    @Test
    public void shouldSetZeroBasedIndexedArrayContentsByCopyingItself() {
        // given
        String[] contents = new String[]{"aaa", "bbb", "ccc"};
        ZeroBasedIndexedArray<String> zeroBasedIndexedArray = new ZeroBasedIndexedArray<>(contents);

        // when
        zeroBasedIndexedArray.set(zeroBasedIndexedArray);

        // then
        assertEquals(contents.length, zeroBasedIndexedArray.length);
        for (int i = 0; i < zeroBasedIndexedArray.length - 1; i++) {
            assertEquals(contents[i], zeroBasedIndexedArray.at(i));
        }
    }

    @Test
    public void shouldExchangeTwoElementsInZeroBasedIndexedArray() {
        // given
        ZeroBasedIndexedArray<String> zeroBasedIndexedArray = new ZeroBasedIndexedArray<>("aaa", "bbb", "ccc");

        // when
        zeroBasedIndexedArray.exch(0, 2);

        // then
        assertEquals("ccc", zeroBasedIndexedArray.at(0));
        assertEquals("aaa", zeroBasedIndexedArray.at(2));
    }

}
