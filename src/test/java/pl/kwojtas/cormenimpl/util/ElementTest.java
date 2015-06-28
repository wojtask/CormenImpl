package pl.kwojtas.cormenimpl.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ElementTest {

    @Test
    public void shouldCreateElementFromGivenKeyAndSatelliteData() {
        // given
        int key = 4;
        String data = "aaa";

        // when
        Element<String> element = new Element<>(key, data);

        // then
        assertEquals(key, element.key);
        assertEquals(data, element.data);
    }

    @Test
    public void shouldCreateElementByCopyingExistingElement() {
        // given
        Element<String> otherElement = new Element<>(4, "aaa");

        // when
        Element<String> element = new Element<>(otherElement);

        // then
        assertEquals(otherElement.key, element.key);
        assertEquals(otherElement.data, element.data);
    }

}
