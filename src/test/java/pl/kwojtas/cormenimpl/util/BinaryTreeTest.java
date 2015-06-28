package pl.kwojtas.cormenimpl.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BinaryTreeTest {

    @Test
    public void shouldReturnTreeSize() {
        // given
        BinaryTree<String> tree = new BinaryTree<>();
        BinaryTree.Node<String> x1 = new BinaryTree.Node<>("1");
        BinaryTree.Node<String> x2 = new BinaryTree.Node<>("2");
        BinaryTree.Node<String> x3 = new BinaryTree.Node<>("3");
        BinaryTree.Node<String> x4 = new BinaryTree.Node<>("4");
        tree.root = x1;
        x1.left = x2;
        x2.p = x1;
        x2.left = x3;
        x3.p = x2;
        x2.right = x4;
        x4.p = x2;

        // when
        int actualSize = tree.getSize();

        // then
        assertEquals(4, actualSize);
    }

}
