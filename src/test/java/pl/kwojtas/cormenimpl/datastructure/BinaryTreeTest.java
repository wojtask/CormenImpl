package pl.kwojtas.cormenimpl.datastructure;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static pl.kwojtas.cormenimpl.TestUtil.assertArrayEquals;

public class BinaryTreeTest {

    private BinaryTree<String> getExemplaryBinaryTree() {
        BinaryTree<String> tree = new BinaryTree<>();
        BinaryTree.Node<String> x1 = new BinaryTree.Node<>("4");
        BinaryTree.Node<String> x2 = new BinaryTree.Node<>("2");
        BinaryTree.Node<String> x3 = new BinaryTree.Node<>("1");
        BinaryTree.Node<String> x4 = new BinaryTree.Node<>("3");
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
        BinaryTree<String> tree = getExemplaryBinaryTree();

        int actualSize = tree.getSize();

        assertEquals(4, actualSize);
    }

    @Test
    public void shouldTransformNonEmptyTreeToArray() {
        BinaryTree<String> tree = getExemplaryBinaryTree();

        Array<String> actualArray = tree.toArray();

        Array<String> expectedArray = new Array<>("1", "2", "3", "4");
        assertArrayEquals(expectedArray, actualArray);
    }

    @Test
    public void shouldTransformEmptyTreeToArray() {
        BinaryTree<String> tree = new BinaryTree<>();

        Array<String> actualArray = tree.toArray();

        Array<String> expectedArray = new Array<>();
        assertArrayEquals(expectedArray, actualArray);
    }

}
