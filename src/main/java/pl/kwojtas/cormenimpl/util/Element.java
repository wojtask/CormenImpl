package pl.kwojtas.cormenimpl.util;

/**
 * Implements a generic element - a structure with a key and satellite data.
 *
 * @param <T> the type of satellite data
 */
public class Element<T> {

    /**
     * The key.
     */
    public int key;

    /**
     * The satellite data.
     */
    public T data;

    /**
     * Creates an element from a given key and satellite data.
     *
     * @param key  the key of the new element
     * @param data the satellite data of the new element
     */
    public Element(int key, T data) {
        this.key = key;
        this.data = data;
    }

    /**
     * Creates an element by copying an existing element.
     *
     * @param otherElement the element to be copied
     */
    public Element(Element<T> otherElement) {
        this.key = otherElement.key;
        this.data = otherElement.data;
    }

}
