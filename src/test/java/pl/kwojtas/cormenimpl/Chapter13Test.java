package pl.kwojtas.cormenimpl;

import org.junit.Test;
import pl.kwojtas.cormenimpl.datastructure.Array;
import pl.kwojtas.cormenimpl.datastructure.RedBlackTree;
import pl.kwojtas.cormenimpl.datastructure.RedBlackTree.Node;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static pl.kwojtas.cormenimpl.Fundamental.geq;
import static pl.kwojtas.cormenimpl.Fundamental.leq;
import static pl.kwojtas.cormenimpl.TestUtil.assertArrayEquals;
import static pl.kwojtas.cormenimpl.datastructure.RedBlackTree.Color.BLACK;
import static pl.kwojtas.cormenimpl.datastructure.RedBlackTree.Color.RED;

public class Chapter13Test {

    private RedBlackTree<Integer> getExemplaryRedBlackTreeForAllLeftCasesInInsertFixup() {
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

    private RedBlackTree<Integer> getExemplaryRedBlackTreeForAllRightCasesInInsertFixup() {
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

    private RedBlackTree<Integer> getExemplaryRedBlackTreeForDeleting() {
        return new RedBlackTree<>(
                new Node<>(5, BLACK,
                        new Node<>(2, RED,
                                new Node<>(1, BLACK),
                                new Node<>(4, BLACK,
                                        new Node<>(3, RED),
                                        null
                                )
                        ),
                        new Node<>(7, BLACK,
                                new Node<>(6, RED),
                                null
                        )
                )
        );
    }

    private RedBlackTree<Integer> getExemplaryRedBlackTreeForAllLeftCasesInDeleteFixup() {
        return new RedBlackTree<>(
                new Node<>(8, BLACK,
                        new Node<>(4, BLACK,
                                new Node<>(2, BLACK,
                                        new Node<>(1, BLACK),
                                        new Node<>(3, BLACK)
                                ),
                                new Node<>(6, BLACK,
                                        new Node<>(5, BLACK),
                                        new Node<>(7, BLACK)
                                )
                        ),
                        new Node<>(20, RED,
                                new Node<>(16, BLACK,
                                        new Node<>(12, RED,
                                                new Node<>(10, BLACK,
                                                        new Node<>(9, BLACK),
                                                        new Node<>(11, BLACK)
                                                ),
                                                new Node<>(14, BLACK,
                                                        new Node<>(13, BLACK),
                                                        new Node<>(15, BLACK)
                                                )
                                        ),
                                        new Node<>(18, BLACK,
                                                new Node<>(17, BLACK),
                                                new Node<>(19, BLACK)
                                        )
                                ),
                                new Node<>(24, BLACK,
                                        new Node<>(22, BLACK,
                                                new Node<>(21, BLACK),
                                                new Node<>(23, BLACK)
                                        ),
                                        new Node<>(26, BLACK,
                                                new Node<>(25, BLACK),
                                                new Node<>(27, BLACK)
                                        )
                                )
                        )
                )
        );
    }

    private RedBlackTree<Integer> getExemplaryRedBlackTreeForAllRightCasesInDeleteFixup() {
        return new RedBlackTree<>(
                new Node<>(20, BLACK,
                        new Node<>(8, RED,
                                new Node<>(4, BLACK,
                                        new Node<>(2, BLACK,
                                                new Node<>(1, BLACK),
                                                new Node<>(3, BLACK)
                                        ),
                                        new Node<>(6, BLACK,
                                                new Node<>(5, BLACK),
                                                new Node<>(7, BLACK)
                                        )
                                ),
                                new Node<>(12, BLACK,
                                        new Node<>(10, BLACK,
                                                new Node<>(9, BLACK),
                                                new Node<>(11, BLACK)
                                        ),
                                        new Node<>(16, RED,
                                                new Node<>(14, BLACK,
                                                        new Node<>(13, BLACK),
                                                        new Node<>(15, BLACK)
                                                ),
                                                new Node<>(18, BLACK,
                                                        new Node<>(17, BLACK),
                                                        new Node<>(19, BLACK)
                                                )
                                        )
                                )
                        ),
                        new Node<>(24, BLACK,
                                new Node<>(22, BLACK,
                                        new Node<>(21, BLACK),
                                        new Node<>(23, BLACK)
                                ),
                                new Node<>(26, BLACK,
                                        new Node<>(25, BLACK),
                                        new Node<>(27, BLACK)
                                )
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
    public void shouldInsertNodeToRedBlackTreeAllLeftCasesInInsertFixup() {
        RedBlackTree<Integer> tree = getExemplaryRedBlackTreeForAllLeftCasesInInsertFixup();
        Node<Integer> newNode = new Node<>(4, BLACK);

        Chapter13.rbInsert(tree, newNode);

        assertRedBlackTree(tree);
        Array<Integer> expectedElements = Array.of(1, 2, 4, 5, 7, 9, 11, 14, 15);
        Array<Integer> actualElements = tree.toArray();
        assertArrayEquals(expectedElements, actualElements);
    }

    @Test
    public void shouldInsertNodeToRedBlackTreeAllRightCasesInInsertFixup() {
        RedBlackTree<Integer> tree = getExemplaryRedBlackTreeForAllRightCasesInInsertFixup();
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
        assertRedBlackProperty4(tree.root);
    }

    private <E> void assertRedBlackProperty4(Node<E> x) {
        if (x.color == RED) {
            assertEquals(BLACK, x.left.color);
            assertEquals(BLACK, x.right.color);
        }
        if (x.left != x) {
            assertRedBlackProperty4(x.left);
        }
        if (x.right != x) {
            assertRedBlackProperty4(x.right);
        }
    }

    private <E> void assertRedBlackProperty5(RedBlackTree<E> tree) {
        assertRedBlackProperty5(tree.root);
    }

    private <E> int assertRedBlackProperty5(Node<E> x) {
        int leftBlackHeight = 0;
        if (x.left != x) {
            leftBlackHeight = assertRedBlackProperty5(x.left) + (x.left.color == BLACK ? 1 : 0);
        }
        int rightBlackHeight = 0;
        if (x.right != x) {
            rightBlackHeight = assertRedBlackProperty5(x.right) + (x.right.color == BLACK ? 1 : 0);
        }
        assertEquals(leftBlackHeight, rightBlackHeight);
        return leftBlackHeight;
    }

    @Test
    public void shouldFindSuccessorInRedBlackTree() {
        RedBlackTree<Integer> tree = getExemplaryRedBlackTreeForDeleting();
        RedBlackTree.Node<Integer> node = tree.root.left.right;

        RedBlackTree.Node<Integer> actualSuccessor = Chapter13.rbTreeSuccessor(tree, node);

        assertNotNull(actualSuccessor);
        assertEquals(Integer.valueOf(5), actualSuccessor.key);
    }

    @Test
    public void shouldNotFindNonexistingSuccessorInRedBlackTree() {
        RedBlackTree<Integer> tree = getExemplaryRedBlackTreeForDeleting();
        RedBlackTree.Node<Integer> node = tree.root.right; // a node with maximum value in the tree

        RedBlackTree.Node<Integer> actualSuccessor = Chapter13.rbTreeSuccessor(tree, node);

        assertEquals(tree.nil, actualSuccessor);
    }

    @Test
    public void shouldDeleteLeafFromRedBlackTree() {
        RedBlackTree<Integer> tree = getExemplaryRedBlackTreeForDeleting();

        Node<Integer> actualDeletedNode = Chapter13.rbDelete(tree, tree.root.left.right.left); // a leaf

        assertRedBlackTree(tree);
        Array<Integer> expectedElements = Array.of(1, 2, 4, 5, 6, 7);
        Array<Integer> actualElements = tree.toArray();
        assertArrayEquals(expectedElements, actualElements);
        assertEquals(Integer.valueOf(3), actualDeletedNode.key);
    }

    @Test
    public void shouldDeleteNodeWithOneChildFromRedBlackTree() {
        RedBlackTree<Integer> tree = getExemplaryRedBlackTreeForDeleting();

        Node<Integer> actualDeletedNode = Chapter13.rbDelete(tree, tree.root.right); // a node with one child

        assertRedBlackTree(tree);
        Array<Integer> expectedElements = Array.of(1, 2, 3, 4, 5, 6);
        Array<Integer> actualElements = tree.toArray();
        assertArrayEquals(expectedElements, actualElements);
        assertEquals(Integer.valueOf(7), actualDeletedNode.key);
    }

    @Test
    public void shouldDeleteNodeWithTwoChildrenFromRedBlackTree() {
        RedBlackTree<Integer> tree = getExemplaryRedBlackTreeForDeleting();

        Node<Integer> actualDeletedNode = Chapter13.rbDelete(tree, tree.root.left); // a node with two children

        assertRedBlackTree(tree);
        Array<Integer> expectedElements = Array.of(1, 3, 4, 5, 6, 7);
        Array<Integer> actualElements = tree.toArray();
        assertArrayEquals(expectedElements, actualElements);
        assertEquals(Integer.valueOf(3), actualDeletedNode.key);
    }

    @Test
    public void shouldDeleteRootFromRedBlackTree() {
        RedBlackTree<Integer> tree = new RedBlackTree<>(new Node<>(10, BLACK));

        Node<Integer> actualDeletedNode = Chapter13.rbDelete(tree, tree.root);

        assertEquals(Integer.valueOf(10), actualDeletedNode.key);
        assertEquals(tree.nil, tree.root);
    }

    @Test
    public void shouldDeleteFromRedBlackTreeAllLeftCasesInDeleteFixup() {
        RedBlackTree<Integer> tree = getExemplaryRedBlackTreeForAllLeftCasesInDeleteFixup();

        // deletion will cause all left cases in rbDeleteFixup -- case 2 twice, case 1, case 3, and case 4 in this order
        Node<Integer> actualDeletedNode = Chapter13.rbDelete(tree, tree.root.left.left.left);

        assertRedBlackTree(tree);
        Array<Integer> expectedElements = Array.of(2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17,
                18, 19, 20, 21, 22, 23, 24, 25, 26, 27);
        Array<Integer> actualElements = tree.toArray();
        assertArrayEquals(expectedElements, actualElements);
        assertEquals(Integer.valueOf(1), actualDeletedNode.key);
    }

    @Test
    public void shouldDeleteFromRedBlackTreeAllRightCasesInDeleteFixup() {
        RedBlackTree<Integer> tree = getExemplaryRedBlackTreeForAllRightCasesInDeleteFixup();

        // deletion will cause all right cases in rbDeleteFixup -- case 2 twice, case 1, case 3, and case 4 in this order
        Node<Integer> actualDeletedNode = Chapter13.rbDelete(tree, tree.root.right.right.right);

        assertRedBlackTree(tree);
        Array<Integer> expectedElements = Array.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16,
                17, 18, 19, 20, 21, 22, 23, 24, 25, 26);
        Array<Integer> actualElements = tree.toArray();
        assertArrayEquals(expectedElements, actualElements);
        assertEquals(Integer.valueOf(27), actualDeletedNode.key);
    }

}
