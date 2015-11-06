package pl.kwojtas.cormenimpl.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HugeArrayTest {

    @Test
    public void shouldCreateHugeArrayWithGivenSizeAndStackCapacity() {
        int size = 5;
        int stackCapacity = 3;

        HugeArray<String> hugeArray = new HugeArray<>(size, stackCapacity);

        assertEquals(size, hugeArray.T.length);
        assertEquals(stackCapacity, hugeArray.S.length);
    }

}
