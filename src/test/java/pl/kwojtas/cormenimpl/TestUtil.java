package pl.kwojtas.cormenimpl;

import static org.junit.Assert.assertEquals;

public class TestUtil {

    public static <T> void assertArrayEquals(Array<T> expected, Array<T> actual) {
        assertEquals(expected.length, actual.length);
        for (int i = 1; i <= expected.length; i++) {
            assertEquals(expected.at(i), actual.at(i));
        }
    }

}
