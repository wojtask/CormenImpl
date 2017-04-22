package pl.kwojtas.cormenimpl.datastructure;

import org.junit.Test;
import pl.kwojtas.cormenimpl.datastructure.RedBlackTree.Node;

import static pl.kwojtas.cormenimpl.TestUtil.assertArrayEquals;
import static pl.kwojtas.cormenimpl.datastructure.RedBlackTree.Color.BLACK;
import static pl.kwojtas.cormenimpl.datastructure.RedBlackTree.Color.RED;

public class RedBlackTreeTest {

    private RedBlackTree<String> getExemplaryRedBlackTree() {
        return new RedBlackTree<>(
                new Node<>("4", BLACK,
                        new Node<>("2", RED,
                                new Node<>("1", BLACK),
                                new Node<>("3", BLACK)
                                ),
                        null
                )
        );
    }

    @Test
    public void shouldTransformNonEmptyTreeToArray() {
        RedBlackTree<String> tree = getExemplaryRedBlackTree();

        Array<String> actualArray = tree.toArray();

        Array<String> expectedArray = Array.of("1", "2", "3", "4");
        assertArrayEquals(expectedArray, actualArray);
    }

    @Test
    public void shouldTransformEmptyTreeToArray() {
        RedBlackTree<String> tree = RedBlackTree.emptyTree();

        Array<String> actualArray = tree.toArray();

        assertArrayEquals(Array.emptyArray(), actualArray);
    }

}
