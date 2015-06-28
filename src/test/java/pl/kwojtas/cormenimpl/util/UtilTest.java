package pl.kwojtas.cormenimpl.util;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UtilTest {

    @Test
    public void shouldHavePrivateConstructor() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<Util> constructor = Util.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    public void shouldReturnTrueIfFirstValueIsLessThanSecondValue() {
        // given
        String first = "aaa";
        String second = "bbb";

        // when
        boolean actualResult = Util.less(first, second);

        // then
        assertTrue(actualResult);
    }

    @Test
    public void shouldReturnFalseIfFirstValueIsNotLessThanSecondValue() {
        // given
        String first = "bbb";
        String second = "aaa";

        // when
        boolean actualResult = Util.less(first, second);

        // then
        assertFalse(actualResult);
    }

    @Test
    public void shouldReturnTrueIfFirstValueIsLessThanOrEqualToSecondValue() {
        // given
        String first = "aaa";
        String second = "bbb";

        // when
        boolean actualResult = Util.leq(first, second);

        // then
        assertTrue(actualResult);
    }

    @Test
    public void shouldReturnFalseIfFirstValueIsNotLessThanNorEqualToSecondValue() {
        // given
        String first = "bbb";
        String second = "aaa";

        // when
        boolean actualResult = Util.leq(first, second);

        // then
        assertFalse(actualResult);
    }

    @Test
    public void shouldReturnTrueIfFirstValueIsGreaterThanSecondValue() {
        // given
        String first = "bbb";
        String second = "aaa";

        // when
        boolean actualResult = Util.greater(first, second);

        // then
        assertTrue(actualResult);
    }

    @Test
    public void shouldReturnFalseIfFirstValueIsNotGreaterThanSecondValue() {
        // given
        String first = "aaa";
        String second = "bbb";

        // when
        boolean actualResult = Util.greater(first, second);

        // then
        assertFalse(actualResult);
    }

    @Test
    public void shouldReturnTrueIfFirstValueIsGreaterThanOrEqualToSecondValue() {
        // given
        String first = "bbb";
        String second = "aaa";

        // when
        boolean actualResult = Util.geq(first, second);

        // then
        assertTrue(actualResult);
    }

    @Test
    public void shouldReturnFalseIfFirstValueIsNotGreaterThanNorEqualToSecondValue() {
        // given
        String first = "aaa";
        String second = "bbb";

        // when
        boolean actualResult = Util.geq(first, second);

        // then
        assertFalse(actualResult);
    }

    @Test
    public void shouldReturnMinimumFromTwoValues() {
        // given
        String first = "aaa";
        String second = "bbb";

        // when
        String actualMinimum = Util.min(first, second);

        // then
        assertEquals(first, actualMinimum);
    }

    @Test
    public void shouldReturnMaximumFromTwoValues() {
        // given
        String first = "aaa";
        String second = "bbb";

        // when
        String actualMaximum = Util.max(first, second);

        // then
        assertEquals(second, actualMaximum);
    }

    @Test
    public void shouldReturnRandomBit() {
        // given

        // when
        int actualBit = Util.random();

        // then
        assertTrue(actualBit == 0 || actualBit == 1);
    }

    @Test
    public void shouldComputeCeilingOfQuotient() {
        // given
        int dividend = 45;
        int divisor = 4;

        // when
        int actualCeiling = Util.ceil(dividend, divisor);

        // then
        assertEquals(12, actualCeiling);
    }

}
