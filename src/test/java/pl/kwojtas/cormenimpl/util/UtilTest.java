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
        String first = "aaa";
        String second = "bbb";

        boolean actualResult = Util.less(first, second);

        assertTrue(actualResult);
    }

    @Test
    public void shouldReturnFalseIfFirstValueIsNotLessThanSecondValue() {
        String first = "bbb";
        String second = "aaa";

        boolean actualResult = Util.less(first, second);

        assertFalse(actualResult);
    }

    @Test
    public void shouldReturnTrueIfFirstValueIsLessThanOrEqualToSecondValue() {
        String first = "aaa";
        String second = "bbb";

        boolean actualResult = Util.leq(first, second);

        assertTrue(actualResult);
    }

    @Test
    public void shouldReturnFalseIfFirstValueIsNotLessThanNorEqualToSecondValue() {
        String first = "bbb";
        String second = "aaa";

        boolean actualResult = Util.leq(first, second);

        assertFalse(actualResult);
    }

    @Test
    public void shouldReturnTrueIfFirstValueIsGreaterThanSecondValue() {
        String first = "bbb";
        String second = "aaa";

        boolean actualResult = Util.greater(first, second);

        assertTrue(actualResult);
    }

    @Test
    public void shouldReturnFalseIfFirstValueIsNotGreaterThanSecondValue() {
        String first = "aaa";
        String second = "bbb";

        boolean actualResult = Util.greater(first, second);

        assertFalse(actualResult);
    }

    @Test
    public void shouldReturnTrueIfFirstValueIsGreaterThanOrEqualToSecondValue() {
        String first = "bbb";
        String second = "aaa";

        boolean actualResult = Util.geq(first, second);

        assertTrue(actualResult);
    }

    @Test
    public void shouldReturnFalseIfFirstValueIsNotGreaterThanNorEqualToSecondValue() {
        String first = "aaa";
        String second = "bbb";

        boolean actualResult = Util.geq(first, second);

        assertFalse(actualResult);
    }

    @Test
    public void shouldReturnMinimumFromTwoValues() {
        String first = "aaa";
        String second = "bbb";

        String actualMinimum = Util.min(first, second);

        assertEquals(first, actualMinimum);
    }

    @Test
    public void shouldReturnMaximumFromTwoValues() {
        String first = "aaa";
        String second = "bbb";

        String actualMaximum = Util.max(first, second);

        assertEquals(second, actualMaximum);
    }

    @Test
    public void shouldReturnRandomBit() {

        int actualBit = Util.random();

        assertTrue(actualBit == 0 || actualBit == 1);
    }

    @Test
    public void shouldComputeCeilingOfQuotient() {
        int dividend = 45;
        int divisor = 4;

        int actualCeiling = Util.ceil(dividend, divisor);

        assertEquals(12, actualCeiling);
    }

}
