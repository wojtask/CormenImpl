package pl.kwojtas.cormenimpl.util;

public class Element<T> {
    public int key;
    public T data;

    public Element(int key, T data) {
        this.key = key;
        this.data = data;
    }

    public Element(Element<T> otherElement) {
        this.key = otherElement.key;
        this.data = otherElement.data;
    }
}
