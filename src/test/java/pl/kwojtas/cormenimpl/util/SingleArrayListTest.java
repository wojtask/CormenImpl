package pl.kwojtas.cormenimpl.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static pl.kwojtas.cormenimpl.TestUtil.assertArrayEquals;

public class SingleArrayListTest {

    @Test
    public void shouldCreateSingleArrayList() {
        // given
        Array<Integer> A = new Array<>(100, 10, 4, 200, 1, null, 300, null, 13, 400, 13, 1, 500, 7, 10);
        Integer L = 4;

        // when
        SingleArrayList singleArrayList = new SingleArrayList(A, L, null);

        // then
        assertArrayEquals(A, singleArrayList);
        assertEquals(L, singleArrayList.L);
        assertNull(singleArrayList.free);
    }

}
