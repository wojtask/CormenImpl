package pl.kwojtas.cormenimpl.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PairTest {

    @Test
    public void shouldCreatePairFromGivenElements() {
        // given
        String first = "aaa";
        Integer second = 4;

        // when
        Pair<String, Integer> pair = new Pair<>(first, second);

        // then
        assertEquals(first, pair.first);
        assertEquals(second, pair.second);
    }
}
