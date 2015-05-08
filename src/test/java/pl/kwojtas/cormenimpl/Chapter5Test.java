package pl.kwojtas.cormenimpl;

import org.junit.Test;
import pl.kwojtas.cormenimpl.util.Array;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static pl.kwojtas.cormenimpl.TestUtil.assertShuffled;

public class Chapter5Test {

    @Test
    public void shouldPickNumberFromInterval() {
        // given
        int a = 10;
        int b = 100;

        // when
        int pickedNumber = Chapter5.random(a, b);

        // then
        assertTrue(a <= pickedNumber && pickedNumber <= b);
    }

    @Test
    public void shouldFlipACoinUsingUnbiasedRandom() {
        // given

        // when
        int pickedNumber = Chapter5.unbiasedRandom();

        // then
        assertTrue(pickedNumber == 0 || pickedNumber == 1);
    }

    @Test
    public void shouldPermuteArrayBySorting() {
        // given
        Array<Integer> array = new Array<>(5,7,9,2,6,8,6,6,3,1,7,8);
        Array<Integer> original = new Array<>(array);

        // when
        Chapter5.permuteBySorting(array);

        // then
        assertShuffled(original, array);
    }

    @Test
    public void shouldRandomizeArrayInPlace() {
        // given
        Array<Integer> array = new Array<>(5,7,9,2,6,8,6,6,3,1,7,8);
        Array<Integer> original = new Array<>(array);

        // when
        Chapter5.randomizeInPlace(array);

        // then
        assertShuffled(original, array);
    }

    @Test
    public void shouldRandomizeArrayInPlaceUsingAlternativeMethod() {
        // given
        Array<Integer> array = new Array<>(5,7,9,2,6,8,6,6,3,1,7,8);
        Array<Integer> original = new Array<>(array);

        // when
        Chapter5.randomizeInPlace_(array);

        // then
        assertShuffled(original, array);
    }

    @Test
    public void shouldPermuteArrayUniformlyBySorting() {
        // given
        Array<Integer> array = new Array<>(5,7,9,2,6,8,6,6,3,1,7,8);
        Array<Integer> original = new Array<>(array);

        // when
        Chapter5.permuteUniformlyBySorting(array);

        // then
        assertShuffled(original, array);
    }

    @Test
    public void shouldFindKeyUsingRandomSearch() {
        // given
        Array<Integer> array = new Array<>(5,7,9,2,6,8,6,6,3,1,7,8);
        Array<Integer> original = new Array<>(array);
        int key = 6;

        // when
        Integer actualIndex = Chapter5.randomSearch(array, key);

        // then
        assertNotNull(actualIndex);
        assertTrue(1 <= actualIndex && actualIndex <= original.length);
        assertEquals(Integer.valueOf(key), original.at(actualIndex));
    }

    @Test
    public void shouldNotFindNonexistentKeyUsingRandomSearch() {
        // given
        Array<Integer> array = new Array<>(5,7,9,2,6,8,6,6,3,1,7,8);
        int key = 4;

        // when
        Integer actualIndex = Chapter5.randomSearch(array, key);

        // then
        assertNull(actualIndex);
    }

}
