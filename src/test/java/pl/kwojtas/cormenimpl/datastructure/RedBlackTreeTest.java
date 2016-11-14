package pl.kwojtas.cormenimpl.datastructure;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static pl.kwojtas.cormenimpl.TestUtil.assertArrayEquals;
import static pl.kwojtas.cormenimpl.datastructure.RedBlackTree.Color.BLACK;
import static pl.kwojtas.cormenimpl.datastructure.RedBlackTree.Color.RED;

public class RedBlackTreeTest {

    private RedBlackTree<String> getExemplaryRedBlackTree() {
        RedBlackTree<String> tree = new RedBlackTree<>();
        RedBlackTree.Node<String> x1 = new RedBlackTree.Node<>("4", BLACK, tree);
        RedBlackTree.Node<String> x2 = new RedBlackTree.Node<>("2", RED, tree);
        RedBlackTree.Node<String> x3 = new RedBlackTree.Node<>("1", BLACK, tree);
        RedBlackTree.Node<String> x4 = new RedBlackTree.Node<>("3", BLACK, tree);
        tree.root = x1;
        x1.left = x2;
        x2.p = x1;
        x2.left = x3;
        x3.p = x2;
        x2.right = x4;
        x4.p = x2;
        return tree;
    }

    @Test
    public void shouldReturnTreeSize() {
        RedBlackTree<String> tree = getExemplaryRedBlackTree();

        int actualSize = tree.getSize();

        assertEquals(4, actualSize);
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
        RedBlackTree<String> tree = new RedBlackTree<>();

        Array<String> actualArray = tree.toArray();

        assertArrayEquals(Array.emptyArray(), actualArray);
    }

}
