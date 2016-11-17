package pl.kwojtas.cormenimpl;

import org.junit.Test;
import pl.kwojtas.cormenimpl.datastructure.Array;
import pl.kwojtas.cormenimpl.datastructure.BinaryTree;
import pl.kwojtas.cormenimpl.datastructure.RedBlackTree;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static pl.kwojtas.cormenimpl.Fundamental.geq;
import static pl.kwojtas.cormenimpl.Fundamental.leq;
import static pl.kwojtas.cormenimpl.TestUtil.assertArrayEquals;
import static pl.kwojtas.cormenimpl.datastructure.RedBlackTree.Color.BLACK;
import static pl.kwojtas.cormenimpl.datastructure.RedBlackTree.Color.RED;

public class Chapter13Test {

    private RedBlackTree<Integer> getExemplaryRedBlackTree() {
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        RedBlackTree.Node<Integer> x1 = new RedBlackTree.Node<>(11, BLACK, tree);
        RedBlackTree.Node<Integer> x2 = new RedBlackTree.Node<>(2, RED, tree);
        RedBlackTree.Node<Integer> x3 = new RedBlackTree.Node<>(14, BLACK, tree);
        RedBlackTree.Node<Integer> x4 = new RedBlackTree.Node<>(1, BLACK, tree);
        RedBlackTree.Node<Integer> x5 = new RedBlackTree.Node<>(7, BLACK, tree);
        RedBlackTree.Node<Integer> x6 = new RedBlackTree.Node<>(15, RED, tree);
        RedBlackTree.Node<Integer> x7 = new RedBlackTree.Node<>(5, RED, tree);
        RedBlackTree.Node<Integer> x8 = new RedBlackTree.Node<>(9, RED, tree);
        tree.root = x1;
        x1.left = x2;  //         11 B
        x2.p = x1;     //        /   \
        x1.right = x3; //       /     \
        x3.p = x1;     //      2 R    14 B
        x2.left = x4;  //     / \       \
        x4.p = x2;     //    /   \       \
        x2.right = x5; //   1 B   7 B   15 R
        x5.p = x2;     //        / \
        x3.right = x6; //       /   \
        x6.p = x3;     //      5 R   9 R
        x5.left = x7;
        x7.p = x5;
        x5.right = x8;
        x8.p = x5;
        return tree;
    }

    private RedBlackTree<Integer> getExemplaryRedBlackTree2() {
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        RedBlackTree.Node<Integer> x1 = new RedBlackTree.Node<>(7, BLACK, tree);
        RedBlackTree.Node<Integer> x2 = new RedBlackTree.Node<>(3, BLACK, tree);
        RedBlackTree.Node<Integer> x3 = new RedBlackTree.Node<>(16, RED, tree);
        RedBlackTree.Node<Integer> x4 = new RedBlackTree.Node<>(1, RED, tree);
        RedBlackTree.Node<Integer> x5 = new RedBlackTree.Node<>(13, BLACK, tree);
        RedBlackTree.Node<Integer> x6 = new RedBlackTree.Node<>(18, BLACK, tree);
        RedBlackTree.Node<Integer> x7 = new RedBlackTree.Node<>(9, RED, tree);
        RedBlackTree.Node<Integer> x8 = new RedBlackTree.Node<>(14, RED, tree);
        tree.root = x1;
        x1.left = x2;  //         7 B
        x2.p = x1;     //        /   \
        x1.right = x3; //       /     \
        x3.p = x1;     //      3 B    16 R
        x2.left = x4;  //     /       / \
        x4.p = x2;     //    /       /   \
        x3.left = x5;  //   1 R    13 B  18 B
        x5.p = x3;     //          / \
        x3.right = x6; //         /   \
        x6.p = x3;     //        9 R   14 R
        x5.left = x7;
        x7.p = x5;
        x5.right = x8;
        x8.p = x5;
        return tree;
    }

    @Test
    public void shouldHavePrivateConstructor() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<Chapter13> constructor = Chapter13.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    public void shouldInsertNodeToEmptyRedBlackTree() {
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        RedBlackTree.Node<Integer> newNode = new RedBlackTree.Node<>(15, BLACK, tree);

        Chapter13.rbInsert(tree, newNode);

        assertRedBlackTree(tree);
        Array<Integer> expectedElements = Array.of(15);
        Array<Integer> actualElements = tree.toArray();
        assertArrayEquals(expectedElements, actualElements);
    }

    @Test
    public void shouldInsertNodeToRedBlackTreeLeft() {
        RedBlackTree<Integer> tree = getExemplaryRedBlackTree();
        RedBlackTree.Node<Integer> newNode = new RedBlackTree.Node<>(4, BLACK, tree);

        Chapter13.rbInsert(tree, newNode);

        assertRedBlackTree(tree);
        Array<Integer> expectedElements = Array.of(1, 2, 4, 5, 7, 9, 11, 14, 15);
        Array<Integer> actualElements = tree.toArray();
        assertArrayEquals(expectedElements, actualElements);
    }

    @Test
    public void shouldInsertNodeToRedBlackTree2() {
        RedBlackTree<Integer> tree = getExemplaryRedBlackTree2();
        RedBlackTree.Node<Integer> newNode = new RedBlackTree.Node<>(15, BLACK, tree);

        Chapter13.rbInsert(tree, newNode);

        assertRedBlackTree(tree);
        Array<Integer> expectedElements = Array.of(1, 3, 7, 9, 13, 14, 15, 16, 18);
        Array<Integer> actualElements = tree.toArray();
        assertArrayEquals(expectedElements, actualElements);
    }

    private <E extends Comparable<? super E>> void assertRedBlackTree(RedBlackTree<E> tree) {
        assertBinarySearchTree(tree);
        assertEquals(BLACK, tree.root.color);
        assertEquals(BLACK, tree.nil.color);
        assertRedBlackProperty4(tree);
        assertRedBlackProperty5(tree);
    }

    private <E extends Comparable<? super E>> void assertBinarySearchTree(RedBlackTree<E> T) {
        assertBinarySearchTree(T, T.root);
    }

    private <E extends Comparable<? super E>> void assertBinarySearchTree(RedBlackTree<E> T, RedBlackTree.Node<E> x) {
        if (x.left != T.nil) {
            Array<E> leftElements = T.toArray(x.left);
            for (int i = 1; i <= leftElements.length; i++) {
                assertTrue(leq(leftElements.at(i), x.key));
            }
            assertBinarySearchTree(T, x.left);
        }
        if (x.right != T.nil) {
            Array<E> rightElements = T.toArray(x.right);
            for (int i = 1; i <= rightElements.length; i++) {
                assertTrue(geq(rightElements.at(i), x.key));
            }
            assertBinarySearchTree(T, x.right);
        }
    }

    private <E> void assertRedBlackProperty4(RedBlackTree<E> tree) {
        assertRedBlackProperty4(tree.root, tree.nil);
    }

    private <E> void assertRedBlackProperty4(RedBlackTree.Node<E> x, RedBlackTree.Node<E> nil) {
        if (x.color == RED) {
            assertEquals(BLACK, x.left.color);
            assertEquals(BLACK, x.right.color);
        }
        if (x.left != nil) {
            assertRedBlackProperty4(x.left, nil);
        }
        if (x.right != nil) {
            assertRedBlackProperty4(x.right, nil);
        }
    }

    private <E> void assertRedBlackProperty5(RedBlackTree<E> tree) {
        // we build a binary tree of the same structure as the red-black tree that will store black heights
        assertRedBlackProperty5(tree.root, tree.nil, new BinaryTree.Node<>(0));
    }

    private <E> void assertRedBlackProperty5(RedBlackTree.Node<E> x, RedBlackTree.Node<E> nil, BinaryTree.Node<Integer> y) {
        if (x.left != nil) {
            y.left = new BinaryTree.Node<>(0);
            assertRedBlackProperty5(x.left, nil, y.left);
        }
        if (x.right != nil) {
            y.right = new BinaryTree.Node<>(0);
            assertRedBlackProperty5(x.right, nil, y.right);
        }
        int leftBlackHeight = (x.left != nil ? y.left.key : 0) + (x.left.color == BLACK ? 1 : 0);
        int rightBlackHeight = (x.right != nil ? y.right.key : 0) + (x.right.color == BLACK ? 1 : 0);
        assertEquals(leftBlackHeight, rightBlackHeight);
        y.key = leftBlackHeight;
    }

    @Test
    public void shouldDeleteLeafFromTree() {
        RedBlackTree<Integer> tree = getExemplaryRedBlackTree();

        RedBlackTree.Node<Integer> actualDeletedNode = Chapter13.rbDelete(tree, tree.root.left.right.left); // a leaf

        assertRedBlackTree(tree);
        Array<Integer> expectedElements = Array.of(1, 2, 7, 9, 11, 14, 15);
        Array<Integer> actualElements = tree.toArray();
        assertArrayEquals(expectedElements, actualElements);
        assertEquals(Integer.valueOf(5), actualDeletedNode.key);
    }

    @Test
    public void shouldDeleteNodeWithOneChildFromTree() {
        RedBlackTree<Integer> tree = getExemplaryRedBlackTree();

        RedBlackTree.Node<Integer> actualDeletedNode = Chapter13.rbDelete(tree, tree.root.right); // a node with one child

        assertRedBlackTree(tree);
        Array<Integer> expectedElements = Array.of(1, 2, 5, 7, 9, 11, 15);
        Array<Integer> actualElements = tree.toArray();
        assertArrayEquals(expectedElements, actualElements);
        assertEquals(Integer.valueOf(14), actualDeletedNode.key);
    }

    @Test
    public void shouldDeleteNodeWithTwoChildrenFromTree() {
        RedBlackTree<Integer> tree = getExemplaryRedBlackTree();

        RedBlackTree.Node<Integer> actualDeletedNode = Chapter13.rbDelete(tree, tree.root.left); // a node with two children (successor's key = 5)

        assertRedBlackTree(tree);
        Array<Integer> expectedElements = Array.of(1, 5, 7, 9, 11, 14, 15);
        Array<Integer> actualElements = tree.toArray();
        assertArrayEquals(expectedElements, actualElements);
        assertEquals(Integer.valueOf(5), actualDeletedNode.key);
    }

    @Test
    public void shouldDeleteNodeWithTwoChildrenFromTree2() {
        RedBlackTree<Integer> tree = getExemplaryRedBlackTree();

        RedBlackTree.Node<Integer> actualDeletedNode = Chapter13.rbDelete(tree, tree.root.left.right); // a node with two children (successor's key = 9)

        assertRedBlackTree(tree);
        Array<Integer> expectedElements = Array.of(1, 2, 5, 9, 11, 14, 15);
        Array<Integer> actualElements = tree.toArray();
        assertArrayEquals(expectedElements, actualElements);
        assertEquals(Integer.valueOf(9), actualDeletedNode.key);
    }

    @Test
    public void shouldDeleteRootFromTree() {
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        tree.root = new RedBlackTree.Node<>(10, BLACK, tree);

        RedBlackTree.Node<Integer> actualDeletedNode = Chapter13.rbDelete(tree, tree.root);

        assertEquals(Integer.valueOf(10), actualDeletedNode.key);
        assertEquals(tree.nil, tree.root);
    }

}
