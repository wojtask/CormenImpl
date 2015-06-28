package pl.kwojtas.cormenimpl.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HugeArrayTest {

    @Test
    public void shouldCreateHugeArrayWithGivenSizeAndStackCapacity() {
        // given
        int size = 5;
        int stackCapacity = 3;

        // when
        HugeArray<String> hugeArray = new HugeArray<>(size, stackCapacity);

        // then
        assertEquals(size, hugeArray.T.length);
        assertEquals(stackCapacity, hugeArray.S.length);
    }

}
