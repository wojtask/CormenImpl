package pl.kwojtas.cormenimpl.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PairTest {

    @Test
    public void shouldCreatePairFromGivenElements() {
        String first = "aaa";
        Integer second = 4;

        Pair<String, Integer> pair = new Pair<>(first, second);

        assertEquals(first, pair.first);
        assertEquals(second, pair.second);
    }
}
