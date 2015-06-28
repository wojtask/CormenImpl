package pl.kwojtas.cormenimpl.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static pl.kwojtas.cormenimpl.TestUtil.assertArrayEquals;

public class MultipleArrayListTest {

    @Test
    public void shouldCreateMultipleArrayList() {
        // given
        Array<Integer> next = new Array<>(3, 1, null, 5, null);
        Array<String> key = new Array<>("aaa", "bbb", "ccc", "ddd", "eee");
        Array<Integer> prev = new Array<>(2, null, 1, null, null);
        Integer L = 2;
        Integer free = 4;

        // when
        MultipleArrayList<String> multipleArrayList = new MultipleArrayList<>(next, key, prev, L, free);

        // then
        assertArrayEquals(next, multipleArrayList.next);
        assertArrayEquals(key, multipleArrayList.key);
        assertArrayEquals(prev, multipleArrayList.prev);
        assertEquals(L, multipleArrayList.L);
        assertEquals(free, multipleArrayList.free);
    }

    @Test
    public void shouldCreateMultipleArrayListByCopyingExistingMultipleArrayList() {
        // given
        MultipleArrayList<String> otherMultipleArrayList = getExemplaryMultipleArrayList();

        // when
        MultipleArrayList<String> multipleArrayList = new MultipleArrayList<>(otherMultipleArrayList);

        // then
        assertArrayEquals(otherMultipleArrayList.next, multipleArrayList.next);
        assertArrayEquals(otherMultipleArrayList.key, multipleArrayList.key);
        assertArrayEquals(otherMultipleArrayList.prev, multipleArrayList.prev);
        assertEquals(otherMultipleArrayList.L, multipleArrayList.L);
        assertEquals(otherMultipleArrayList.free, multipleArrayList.free);
    }

    private MultipleArrayList<String> getExemplaryMultipleArrayList() {
        return new MultipleArrayList<>(
                new Array<>(3, 1, null, 5, null),
                new Array<>("aaa", "bbb", "ccc", "ddd", "eee"),
                new Array<>(2, null, 1, null, null),
                2,
                4
        );
    }

    @Test
    public void shouldGetListLength() {
        // given
        MultipleArrayList<String> multipleArrayList = getExemplaryMultipleArrayList();

        // when
        int actualLength = multipleArrayList.getLength();

        // then
        assertEquals(3, actualLength);
    }

    @Test
    public void shouldGetFreeListLength() {
        // given
        MultipleArrayList<String> multipleArrayList = getExemplaryMultipleArrayList();

        // when
        int actualLength = multipleArrayList.getFreeListLength();

        // then
        assertEquals(2, actualLength);
    }

    @Test
    public void shouldTransformListToArray() {
        // given
        MultipleArrayList<String> multipleArrayList = getExemplaryMultipleArrayList();
        Array<String> expectedArray = new Array<>("bbb", "aaa", "ccc");

        // when
        Array<String> actualArray = multipleArrayList.toArray();

        // then
        assertArrayEquals(expectedArray, actualArray);
    }

}
