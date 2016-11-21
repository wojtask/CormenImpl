package pl.kwojtas.cormenimpl.datastructure;

import org.junit.Test;
import pl.kwojtas.cormenimpl.datastructure.BinaryTree.Node;

import static org.junit.Assert.assertEquals;
import static pl.kwojtas.cormenimpl.TestUtil.assertArrayEquals;

public class BinaryTreeTest {

    private BinaryTree<String> getExemplaryBinaryTree() {
        return new BinaryTree<>(
                new Node<>("4",
                        new Node<>("2",
                                new Node<>("1"),
                                new Node<>("3")
                        ),
                        null
                )
        );
    }

    @Test
    public void shouldReturnTreeSize() {
        BinaryTree<String> tree = getExemplaryBinaryTree();

        int actualSize = tree.getSize();

        assertEquals(4, actualSize);
    }

    @Test
    public void shouldTransformNonEmptyTreeToArray() {
        BinaryTree<String> tree = getExemplaryBinaryTree();

        Array<String> actualArray = tree.toArray();

        Array<String> expectedArray = Array.of("1", "2", "3", "4");
        assertArrayEquals(expectedArray, actualArray);
    }

    @Test
    public void shouldTransformEmptyTreeToArray() {
        BinaryTree<String> tree = BinaryTree.emptyTree();

        Array<String> actualArray = tree.toArray();

        assertArrayEquals(Array.emptyArray(), actualArray);
    }

}
