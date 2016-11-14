package pl.kwojtas.cormenimpl;

import org.junit.Test;
import pl.kwojtas.cormenimpl.datastructure.Array;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static pl.kwojtas.cormenimpl.TestUtil.assertShuffled;

public class Chapter5Test {

    @Test
    public void shouldHavePrivateConstructor() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<Chapter5> constructor = Chapter5.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    public void shouldPickNumberFromInterval() {
        int a = 10;
        int b = 100;

        int pickedNumber = Chapter5.random(a, b);

        assertTrue(a <= pickedNumber && pickedNumber <= b);
    }

    @Test
    public void shouldFlipACoinUsingUnbiasedRandom() {

        int pickedNumber = Chapter5.unbiasedRandom();

        assertTrue(pickedNumber == 0 || pickedNumber == 1);
    }

    @Test
    public void shouldPermuteArrayBySorting() {
        Array<Integer> array = Array.of(5, 7, 9, 2, 6, 8, 6, 6, 3, 1, 7, 8);
        Array<Integer> original = Array.copyOf(array);

        Chapter5.permuteBySorting(array);

        assertShuffled(original, array);
    }

    @Test
    public void shouldRandomizeArrayInPlace() {
        Array<Integer> array = Array.of(5, 7, 9, 2, 6, 8, 6, 6, 3, 1, 7, 8);
        Array<Integer> original = Array.copyOf(array);

        Chapter5.randomizeInPlace(array);

        assertShuffled(original, array);
    }

    @Test
    public void shouldRandomizeArrayInPlaceUsingAlternativeMethod() {
        Array<Integer> array = Array.of(5, 7, 9, 2, 6, 8, 6, 6, 3, 1, 7, 8);
        Array<Integer> original = Array.copyOf(array);

        Chapter5.randomizeInPlace_(array);

        assertShuffled(original, array);
    }

    @Test
    public void shouldPermuteArrayUniformlyBySorting() {
        Array<Integer> array = Array.of(5, 7, 9, 2, 6, 8, 6, 6, 3, 1, 7, 8);
        Array<Integer> original = Array.copyOf(array);

        Chapter5.permuteUniformlyBySorting(array);

        assertShuffled(original, array);
    }

    @Test
    public void shouldFindKeyUsingRandomSearch() {
        Array<Integer> array = Array.of(5, 7, 9, 2, 6, 8, 6, 6, 3, 1, 7, 8);
        Array<Integer> original = Array.copyOf(array);
        int key = 6;

        Integer actualIndex = Chapter5.randomSearch(array, key);

        assertNotNull(actualIndex);
        assertTrue(1 <= actualIndex && actualIndex <= original.length);
        assertEquals(Integer.valueOf(key), original.at(actualIndex));
    }

    @Test
    public void shouldNotFindNonexistentKeyUsingRandomSearch() {
        Array<Integer> array = Array.of(5, 7, 9, 2, 6, 8, 6, 6, 3, 1, 7, 8);
        int key = 4;

        Integer actualIndex = Chapter5.randomSearch(array, key);

        assertNull(actualIndex);
    }

}
