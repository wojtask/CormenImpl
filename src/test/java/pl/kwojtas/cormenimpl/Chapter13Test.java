package pl.kwojtas.cormenimpl;

import org.junit.Test;
import pl.kwojtas.cormenimpl.datastructure.Array;
import pl.kwojtas.cormenimpl.datastructure.BinaryTree;
import pl.kwojtas.cormenimpl.datastructure.RedBlackTree;
import pl.kwojtas.cormenimpl.datastructure.RedBlackTree.Node;

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
        return new RedBlackTree<>(
                new Node<>(11, BLACK,
                        new Node<>(2, RED,
                                new Node<>(1, BLACK),
                                new Node<>(7, BLACK,
                                        new Node<>(5, RED),
                                        new Node<>(9, RED)
                                )
                        ),
                        new Node<>(14, BLACK,
                                null,
                                new Node<>(15, RED)
                        )
                )
        );
    }

    private RedBlackTree<Integer> getExemplaryRedBlackTree2() {
        return new RedBlackTree<>(
                new Node<>(7, BLACK,
                        new Node<>(3, BLACK,
                                new Node<>(1, RED),
                                null
                        ),
                        new Node<>(16, RED,
                                new Node<>(13, BLACK,
                                        new Node<>(9, RED),
                                        new Node<>(14, RED)
                                ),
                                new Node<>(18, BLACK)
                        )
                )
        );
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
        RedBlackTree<Integer> tree = RedBlackTree.emptyTree();
        Node<Integer> newNode = new Node<>(15, BLACK);

        Chapter13.rbInsert(tree, newNode);

        assertRedBlackTree(tree);
        Array<Integer> expectedElements = Array.of(15);
        Array<Integer> actualElements = tree.toArray();
        assertArrayEquals(expectedElements, actualElements);
    }

    @Test
    public void shouldInsertNodeToRedBlackTreeLeft() {
        RedBlackTree<Integer> tree = getExemplaryRedBlackTree();
        Node<Integer> newNode = new Node<>(4, BLACK);

        Chapter13.rbInsert(tree, newNode);

        assertRedBlackTree(tree);
        Array<Integer> expectedElements = Array.of(1, 2, 4, 5, 7, 9, 11, 14, 15);
        Array<Integer> actualElements = tree.toArray();
        assertArrayEquals(expectedElements, actualElements);
    }

    @Test
    public void shouldInsertNodeToRedBlackTree2() {
        RedBlackTree<Integer> tree = getExemplaryRedBlackTree2();
        Node<Integer> newNode = new Node<>(15, BLACK);

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

    private <E extends Comparable<? super E>> void assertBinarySearchTree(RedBlackTree<E> T, Node<E> x) {
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

    private <E> void assertRedBlackProperty4(Node<E> x, Node<E> nil) {
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

    private <E> void assertRedBlackProperty5(Node<E> x, Node<E> nil, BinaryTree.Node<Integer> y) {
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
    public void shouldDeleteLeafFromRedBlackTree() {
        RedBlackTree<Integer> tree = getExemplaryRedBlackTree();

        Node<Integer> actualDeletedNode = Chapter13.rbDelete(tree, tree.root.left.right.left); // a leaf

        assertRedBlackTree(tree);
        Array<Integer> expectedElements = Array.of(1, 2, 7, 9, 11, 14, 15);
        Array<Integer> actualElements = tree.toArray();
        assertArrayEquals(expectedElements, actualElements);
        assertEquals(Integer.valueOf(5), actualDeletedNode.key);
    }

    @Test
    public void shouldDeleteNodeWithOneChildFromRedBlackTree() {
        RedBlackTree<Integer> tree = getExemplaryRedBlackTree();

        Node<Integer> actualDeletedNode = Chapter13.rbDelete(tree, tree.root.right); // a node with one child

        assertRedBlackTree(tree);
        Array<Integer> expectedElements = Array.of(1, 2, 5, 7, 9, 11, 15);
        Array<Integer> actualElements = tree.toArray();
        assertArrayEquals(expectedElements, actualElements);
        assertEquals(Integer.valueOf(14), actualDeletedNode.key);
    }

    @Test
    public void shouldDeleteNodeWithTwoChildrenFromRedBlackTree() {
        RedBlackTree<Integer> tree = getExemplaryRedBlackTree();

        Node<Integer> actualDeletedNode = Chapter13.rbDelete(tree, tree.root.left); // a node with two children (successor's key = 5)

        assertRedBlackTree(tree);
        Array<Integer> expectedElements = Array.of(1, 5, 7, 9, 11, 14, 15);
        Array<Integer> actualElements = tree.toArray();
        assertArrayEquals(expectedElements, actualElements);
        assertEquals(Integer.valueOf(5), actualDeletedNode.key);
    }

    @Test
    public void shouldDeleteNodeWithTwoChildrenFromRedBlackTree2() {
        RedBlackTree<Integer> tree = getExemplaryRedBlackTree();

        Node<Integer> actualDeletedNode = Chapter13.rbDelete(tree, tree.root.left.right); // a node with two children (successor's key = 9)

        assertRedBlackTree(tree);
        Array<Integer> expectedElements = Array.of(1, 2, 5, 9, 11, 14, 15);
        Array<Integer> actualElements = tree.toArray();
        assertArrayEquals(expectedElements, actualElements);
        assertEquals(Integer.valueOf(9), actualDeletedNode.key);
    }

    @Test
    public void shouldDeleteRootFromRedBlackTree() {
        RedBlackTree<Integer> tree = new RedBlackTree<>(new Node<>(10, BLACK));

        Node<Integer> actualDeletedNode = Chapter13.rbDelete(tree, tree.root);

        assertEquals(Integer.valueOf(10), actualDeletedNode.key);
        assertEquals(tree.nil, tree.root);
    }

}
