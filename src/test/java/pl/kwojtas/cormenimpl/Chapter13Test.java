package pl.kwojtas.cormenimpl;

import org.junit.Test;
import pl.kwojtas.cormenimpl.datastructure.AVLTree;
import pl.kwojtas.cormenimpl.datastructure.Array;
import pl.kwojtas.cormenimpl.datastructure.ParentlessBinaryTree;
import pl.kwojtas.cormenimpl.datastructure.ParentlessRedBlackTree;
import pl.kwojtas.cormenimpl.datastructure.RedBlackTree;
import pl.kwojtas.cormenimpl.datastructure.RedBlackTree.Node;
import pl.kwojtas.cormenimpl.datastructure.Treap;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
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

    private AVLTree<Integer> getExemplaryAVLTree() {
        return new AVLTree<>(
                new AVLTree.Node<>(9,
                        new AVLTree.Node<>(6,
                                new AVLTree.Node<>(2,
                                        null,
                                        new AVLTree.Node<>(4)),
                                new AVLTree.Node<>(7)
                        ),
                        new AVLTree.Node<>(13,
                                new AVLTree.Node<>(11),
                                null
                        )
                )
        );
    }

    private ParentlessRedBlackTree<Integer> getExemplaryParentlessRedBlackTreeForAllLeftCasesInInsertFixup() {
        return new ParentlessRedBlackTree<>(
                new ParentlessRedBlackTree.Node<>(11, BLACK,
                        new ParentlessRedBlackTree.Node<>(2, RED,
                                new ParentlessRedBlackTree.Node<>(1, BLACK),
                                new ParentlessRedBlackTree.Node<>(7, BLACK,
                                        new ParentlessRedBlackTree.Node<>(5, RED),
                                        new ParentlessRedBlackTree.Node<>(9, RED)
                                )
                        ),
                        new ParentlessRedBlackTree.Node<>(14, BLACK,
                                null,
                                new ParentlessRedBlackTree.Node<>(15, RED)
                        )
                )
        );
    }

    private ParentlessRedBlackTree<Integer> getExemplaryParentlessRedBlackTreeForAllRightCasesInInsertFixup() {
        return new ParentlessRedBlackTree<>(
                new ParentlessRedBlackTree.Node<>(7, BLACK,
                        new ParentlessRedBlackTree.Node<>(3, BLACK,
                                new ParentlessRedBlackTree.Node<>(1, RED),
                                null
                        ),
                        new ParentlessRedBlackTree.Node<>(16, RED,
                                new ParentlessRedBlackTree.Node<>(13, BLACK,
                                        new ParentlessRedBlackTree.Node<>(9, RED),
                                        new ParentlessRedBlackTree.Node<>(14, RED)
                                ),
                                new ParentlessRedBlackTree.Node<>(18, BLACK)
                        )
                )
        );
    }

    private ParentlessBinaryTree<Integer> getExemplaryPersistentBinarySearchTree() {
        return new ParentlessBinaryTree<>(
                new ParentlessBinaryTree.Node<>(4,
                        new ParentlessBinaryTree.Node<>(3,
                                new ParentlessBinaryTree.Node<>(2),
                                null
                        ),
                        new ParentlessBinaryTree.Node<>(8,
                                new ParentlessBinaryTree.Node<>(7),
                                new ParentlessBinaryTree.Node<>(10)
                        )
                )
        );
    }

    private Treap<String> getExemplaryTreap() {
        return new Treap<>(
                new Treap.Node<>("G", 4,
                        new Treap.Node<>("B", 7,
                                new Treap.Node<>("A", 10),
                                new Treap.Node<>("E", 23)
                        ),
                        new Treap.Node<>("H", 5,
                                null,
                                new Treap.Node<>("K", 65,
                                        new Treap.Node<>("I", 73),
                                        null
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

    private <E extends Comparable<? super E>> void assertRedBlackTree(RedBlackTree<E> T) {
        assertBinarySearchTree(T);
        assertEquals(BLACK, T.root.color);
        assertEquals(BLACK, T.nil.color);
        assertRedBlackProperty4(T);
        assertRedBlackProperty5(T);
        assertParentPointersConsistent(T);
    }

    private <E extends Comparable<? super E>> void assertBinarySearchTree(RedBlackTree<E> T) {
        if (T.root != T.nil) {
            assertBinarySearchTree(T, T.root);
        }
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

    private <E> void assertRedBlackProperty4(RedBlackTree<E> T) {
        if (T.root != T.nil) {
            assertRedBlackProperty4(T.root);
        }
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

    private <E> void assertRedBlackProperty5(RedBlackTree<E> T) {
        if (T.root != T.nil) {
            assertRedBlackProperty5(T.root);
        }
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

    private <E extends Comparable<? super E>> void assertParentPointersConsistent(RedBlackTree<E> T) {
        if (T.root != T.nil) {
            assertEquals(T.nil, T.root.p);
            assertParentPointersConsistent(T, T.root);
        }
    }

    private <E extends Comparable<? super E>> void assertParentPointersConsistent(RedBlackTree<E> T, Node<E> x) {
        if (x.left != T.nil) {
            assertEquals(x, x.left.p);
            assertParentPointersConsistent(T, x.left);
        }
        if (x.right != T.nil) {
            assertEquals(x, x.right.p);
            assertParentPointersConsistent(T, x.right);
        }
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
    public void shouldInsertNodeToEmptyParentlessRedBlackTree() {
        ParentlessRedBlackTree<Integer> tree = ParentlessRedBlackTree.emptyTree();
        ParentlessRedBlackTree.Node<Integer> newNode = new ParentlessRedBlackTree.Node<>(15, BLACK);

        Chapter13.rbParentlessInsert(tree, newNode);

        assertParentlessRedBlackTree(tree);
        Array<Integer> expectedElements = Array.of(15);
        Array<Integer> actualElements = tree.toArray();
        assertArrayEquals(expectedElements, actualElements);
    }

    @Test
    public void shouldInsertNodeToParentlessRedBlackTreeAllLeftCasesInInsertFixup() {
        ParentlessRedBlackTree<Integer> tree = getExemplaryParentlessRedBlackTreeForAllLeftCasesInInsertFixup();
        ParentlessRedBlackTree.Node<Integer> newNode = new ParentlessRedBlackTree.Node<>(4, BLACK);

        Chapter13.rbParentlessInsert(tree, newNode);

        assertParentlessRedBlackTree(tree);
        Array<Integer> expectedElements = Array.of(1, 2, 4, 5, 7, 9, 11, 14, 15);
        Array<Integer> actualElements = tree.toArray();
        assertArrayEquals(expectedElements, actualElements);
    }

    @Test
    public void shouldInsertNodeToParentlessRedBlackTreeAllRightCasesInInsertFixup() {
        ParentlessRedBlackTree<Integer> tree = getExemplaryParentlessRedBlackTreeForAllRightCasesInInsertFixup();
        ParentlessRedBlackTree.Node<Integer> newNode = new ParentlessRedBlackTree.Node<>(15, BLACK);

        Chapter13.rbParentlessInsert(tree, newNode);

        assertParentlessRedBlackTree(tree);
        Array<Integer> expectedElements = Array.of(1, 3, 7, 9, 13, 14, 15, 16, 18);
        Array<Integer> actualElements = tree.toArray();
        assertArrayEquals(expectedElements, actualElements);
    }

    private <E extends Comparable<? super E>> void assertParentlessRedBlackTree(ParentlessRedBlackTree<E> T) {
        assertBinarySearchTree(T);
        assertEquals(BLACK, T.root.color);
        assertEquals(BLACK, T.nil.color);
        assertRedBlackProperty4(T);
        assertRedBlackProperty5(T);
    }

    private <E extends Comparable<? super E>> void assertBinarySearchTree(ParentlessRedBlackTree<E> T) {
        if (T.root != T.nil) {
            assertParentlessBinarySearchTree(T, T.root);
        }
    }

    private <E extends Comparable<? super E>> void assertParentlessBinarySearchTree(
            ParentlessRedBlackTree<E> T, ParentlessRedBlackTree.Node<E> x) {
        if (x.left != T.nil) {
            Array<E> leftElements = T.toArray(x.left);
            for (int i = 1; i <= leftElements.length; i++) {
                assertTrue(leq(leftElements.at(i), x.key));
            }
            assertParentlessBinarySearchTree(T, x.left);
        }
        if (x.right != T.nil) {
            Array<E> rightElements = T.toArray(x.right);
            for (int i = 1; i <= rightElements.length; i++) {
                assertTrue(geq(rightElements.at(i), x.key));
            }
            assertParentlessBinarySearchTree(T, x.right);
        }
    }

    private <E> void assertRedBlackProperty4(ParentlessRedBlackTree<E> T) {
        if (T.root != T.nil) {
            assertRedBlackProperty4(T.root);
        }
    }

    private <E> void assertRedBlackProperty4(ParentlessRedBlackTree.Node<E> x) {
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

    private <E> void assertRedBlackProperty5(ParentlessRedBlackTree<E> T) {
        if (T.root != T.nil) {
            assertRedBlackProperty5(T.root);
        }
    }

    private <E> int assertRedBlackProperty5(ParentlessRedBlackTree.Node<E> x) {
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
    public void shouldDeleteNodeWithTwoNilSonsFromRedBlackTree() {
        RedBlackTree<Integer> tree = getExemplaryRedBlackTreeForDeleting();

        Node<Integer> actualDeletedNode = Chapter13.rbDelete(tree, tree.root.left.right.left); // a node with two nil sons

        assertRedBlackTree(tree);
        Array<Integer> expectedElements = Array.of(1, 2, 4, 5, 6, 7);
        Array<Integer> actualElements = tree.toArray();
        assertArrayEquals(expectedElements, actualElements);
        assertEquals(Integer.valueOf(3), actualDeletedNode.key);
    }

    @Test
    public void shouldDeleteNodeWithOneNilSonFromRedBlackTree() {
        RedBlackTree<Integer> tree = getExemplaryRedBlackTreeForDeleting();

        Node<Integer> actualDeletedNode = Chapter13.rbDelete(tree, tree.root.right); // a node with one nil son

        assertRedBlackTree(tree);
        Array<Integer> expectedElements = Array.of(1, 2, 3, 4, 5, 6);
        Array<Integer> actualElements = tree.toArray();
        assertArrayEquals(expectedElements, actualElements);
        assertEquals(Integer.valueOf(7), actualDeletedNode.key);
    }

    @Test
    public void shouldDeleteNodeWithTwoNonnilSonsFromRedBlackTree() {
        RedBlackTree<Integer> tree = getExemplaryRedBlackTreeForDeleting();

        Node<Integer> actualDeletedNode = Chapter13.rbDelete(tree, tree.root.left); // a node with two non-nil sons

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

    @Test
    public void shouldInsertNodeToEmptyPersistentSetRepresentedByBinaryTree() {
        ParentlessBinaryTree<Integer> T = ParentlessBinaryTree.emptyTree();

        ParentlessBinaryTree<Integer> T_ = Chapter13.persistentTreeInsert(T, 15);

        assertNull(T.root);
        assertParentlessBinarySearchTree(T_);
        Array<Integer> expectedElements = Array.of(15);
        Array<Integer> actualElements = T_.toArray();
        assertArrayEquals(expectedElements, actualElements);
    }

    @Test
    public void shouldInsertNodeToNonemptyPersistentSetRepresentedByBinaryTree() {
        ParentlessBinaryTree<Integer> T = getExemplaryPersistentBinarySearchTree();

        ParentlessBinaryTree<Integer> T_ = Chapter13.persistentTreeInsert(T, 5);

        assertParentlessBinarySearchTree(T);
        assertParentlessBinarySearchTree(T_);
        Array<Integer> expectedElementsBeforeInsertion = Array.of(2, 3, 4, 7, 8, 10);
        Array<Integer> actualElementsBeforeInsertion = T.toArray();
        assertArrayEquals(expectedElementsBeforeInsertion, actualElementsBeforeInsertion);
        Array<Integer> expectedElementsAfterInsertion = Array.of(2, 3, 4, 5, 7, 8, 10);
        Array<Integer> actualElementsAfterInsertion = T_.toArray();
        assertArrayEquals(expectedElementsAfterInsertion, actualElementsAfterInsertion);
    }

    private <E extends Comparable<? super E>> void assertParentlessBinarySearchTree(ParentlessBinaryTree<E> T) {
        if (T.root != null) {
            assertParentlessBinarySearchTree(T, T.root);
        }
    }

    private <E extends Comparable<? super E>> void assertParentlessBinarySearchTree(ParentlessBinaryTree<E> T, ParentlessBinaryTree.Node<E> x) {
        if (x.left != null) {
            Array<E> leftElements = T.toArray(x.left);
            for (int i = 1; i <= leftElements.length; i++) {
                assertTrue(leq(leftElements.at(i), x.key));
            }
            assertParentlessBinarySearchTree(T, x.left);
        }
        if (x.right != null) {
            Array<E> rightElements = T.toArray(x.right);
            for (int i = 1; i <= rightElements.length; i++) {
                assertTrue(geq(rightElements.at(i), x.key));
            }
            assertParentlessBinarySearchTree(T, x.right);
        }
    }

    @Test
    public void shouldInsertNodeToEmptyPersistentSetRepresentedByRedBlackTree() {
        ParentlessRedBlackTree<Integer> T = ParentlessRedBlackTree.emptyTree();
        ParentlessRedBlackTree.Node<Integer> newNode = new ParentlessRedBlackTree.Node<>(15, BLACK);

        ParentlessRedBlackTree<Integer> T_ = Chapter13.persistentRbInsert(T, newNode);

        assertEquals(T.nil, T.root);
        assertParentlessRedBlackTree(T_);
        Array<Integer> expectedElements = Array.of(15);
        Array<Integer> actualElements = T_.toArray();
        assertArrayEquals(expectedElements, actualElements);
    }

    @Test
    public void shouldInsertNodeToNonemptyPersistentSetRepresentedByRedBlackTreeAllLeftCasesInInsertFixup() {
        ParentlessRedBlackTree<Integer> T = getExemplaryParentlessRedBlackTreeForAllLeftCasesInInsertFixup();
        ParentlessRedBlackTree.Node<Integer> newNode = new ParentlessRedBlackTree.Node<>(4, BLACK);

        ParentlessRedBlackTree<Integer> T_ = Chapter13.persistentRbInsert(T, newNode);

        assertParentlessRedBlackTree(T);
        assertParentlessRedBlackTree(T_);
        Array<Integer> expectedElementsBeforeInsertion = Array.of(1, 2, 5, 7, 9, 11, 14, 15);
        Array<Integer> actualElementsBeforeInsertion = T.toArray();
        assertArrayEquals(expectedElementsBeforeInsertion, actualElementsBeforeInsertion);
        Array<Integer> expectedElementsAfterInsertion = Array.of(1, 2, 4, 5, 7, 9, 11, 14, 15);
        Array<Integer> actualElementsAfterInsertion = T_.toArray();
        assertArrayEquals(expectedElementsAfterInsertion, actualElementsAfterInsertion);
    }

    @Test
    public void shouldInsertNodeToNonemptyPersistentSetRepresentedByRedBlackTreeAllRightCasesInInsertFixup() {
        ParentlessRedBlackTree<Integer> T = getExemplaryParentlessRedBlackTreeForAllRightCasesInInsertFixup();
        ParentlessRedBlackTree.Node<Integer> newNode = new ParentlessRedBlackTree.Node<>(15, BLACK);

        ParentlessRedBlackTree<Integer> T_ = Chapter13.persistentRbInsert(T, newNode);

        assertParentlessRedBlackTree(T);
        assertParentlessRedBlackTree(T_);
        Array<Integer> expectedElementsBeforeInsertion = Array.of(1, 3, 7, 9, 13, 14, 16, 18);
        Array<Integer> actualElementsBeforeInsertion = T.toArray();
        assertArrayEquals(expectedElementsBeforeInsertion, actualElementsBeforeInsertion);
        Array<Integer> expectedElementsAfterInsertion = Array.of(1, 3, 7, 9, 13, 14, 15, 16, 18);
        Array<Integer> actualElementsAfterInsertion = T_.toArray();
        assertArrayEquals(expectedElementsAfterInsertion, actualElementsAfterInsertion);
    }

    @Test
    public void shouldInsertNodeToEmptyAVLTree() {
        AVLTree<Integer> tree = AVLTree.emptyTree();
        AVLTree.Node<Integer> newNode = new AVLTree.Node<>(7);

        Chapter13.avlInsertWrapper(tree, newNode);

        assertAVLTree(tree);
        Array<Integer> expectedElements = Array.of(7);
        Array<Integer> actualElements = tree.toArray();
        assertArrayEquals(expectedElements, actualElements);
    }

    private void assertAVLTree(AVLTree<Integer> T) {
        assertBinarySearchTree(T);
        assertCorrectHeights(T);
        assertHeightBalanced(T);
    }

    private <E extends Comparable<? super E>> void assertBinarySearchTree(AVLTree<E> T) {
        if (T.root != null) {
            assertBinarySearchTree(T, T.root);
        }
    }

    private <E extends Comparable<? super E>> void assertBinarySearchTree(AVLTree<E> T, AVLTree.Node<E> x) {
        if (x.left != null) {
            Array<E> leftElements = T.toArray(x.left);
            for (int i = 1; i <= leftElements.length; i++) {
                assertTrue(leq(leftElements.at(i), x.key));
            }
            assertBinarySearchTree(T, x.left);
        }
        if (x.right != null) {
            Array<E> rightElements = T.toArray(x.right);
            for (int i = 1; i <= rightElements.length; i++) {
                assertTrue(geq(rightElements.at(i), x.key));
            }
            assertBinarySearchTree(T, x.right);
        }
    }

    private <E> void assertCorrectHeights(AVLTree<E> T) {
        assertCorrectHeights(T.root);
    }

    private <E> int assertCorrectHeights(AVLTree.Node<E> x) {
        if (x == null) {
            return -1;
        }
        int hl = assertCorrectHeights(x.left);
        int hr = assertCorrectHeights(x.right);
        assertEquals(x.h, Math.max(hl, hr) + 1);
        return x.h;
    }

    private <E> void assertHeightBalanced(AVLTree<E> T) {
        assertHeightBalanced(T.root);
    }

    private <E> int assertHeightBalanced(AVLTree.Node<E> x) {
        if (x == null) {
            return -1;
        }
        int hl = assertHeightBalanced(x.left);
        int hr = assertHeightBalanced(x.right);
        assertTrue(Math.abs(hr - hl) <= 1);
        return x.h;
    }

    @Test
    public void shouldInsertNodeToAVLTreeLeftLeftCase() {
        AVLTree<Integer> tree = getExemplaryAVLTree();
        AVLTree.Node<Integer> newNode = new AVLTree.Node<>(10);

        Chapter13.avlInsertWrapper(tree, newNode);

        assertAVLTree(tree);
        Array<Integer> expectedElements = Array.of(2, 4, 6, 7, 9, 10, 11, 13);
        Array<Integer> actualElements = tree.toArray();
        assertArrayEquals(expectedElements, actualElements);
    }

    @Test
    public void shouldInsertNodeToAVLTreeLeftRightCase() {
        AVLTree<Integer> tree = getExemplaryAVLTree();
        AVLTree.Node<Integer> newNode = new AVLTree.Node<>(12);

        Chapter13.avlInsertWrapper(tree, newNode);

        assertAVLTree(tree);
        Array<Integer> expectedElements = Array.of(2, 4, 6, 7, 9, 11, 12, 13);
        Array<Integer> actualElements = tree.toArray();
        assertArrayEquals(expectedElements, actualElements);
    }

    @Test
    public void shouldInsertNodeToAVLTreeRightRightCase() {
        AVLTree<Integer> tree = getExemplaryAVLTree();
        AVLTree.Node<Integer> newNode = new AVLTree.Node<>(5);

        Chapter13.avlInsertWrapper(tree, newNode);

        assertAVLTree(tree);
        Array<Integer> expectedElements = Array.of(2, 4, 5, 6, 7, 9, 11, 13);
        Array<Integer> actualElements = tree.toArray();
        assertArrayEquals(expectedElements, actualElements);
    }

    @Test
    public void shouldInsertNodeToAVLTreeRightLeftCase() {
        AVLTree<Integer> tree = getExemplaryAVLTree();
        AVLTree.Node<Integer> newNode = new AVLTree.Node<>(3);

        Chapter13.avlInsertWrapper(tree, newNode);

        assertAVLTree(tree);
        Array<Integer> expectedElements = Array.of(2, 3, 4, 6, 7, 9, 11, 13);
        Array<Integer> actualElements = tree.toArray();
        assertArrayEquals(expectedElements, actualElements);
    }

    @Test
    public void shouldInsertNodeIntoEmptyTreap() {
        Treap<String> treap = Treap.emptyTreap();
        Treap.Node<String> newNode = new Treap.Node<>("X", 10);

        Chapter13.treapInsert(treap, newNode);

        assertTreap(treap);
        Array<String> expectedElements = Array.of("X");
        Array<String> actualElements = treap.toArray();
        assertArrayEquals(expectedElements, actualElements);
    }

    @Test
    public void shouldInsertIntoEmptyTreap() {
        Treap<String> treap = getExemplaryTreap();
        Treap.Node<String> newNode1 = new Treap.Node<>("C", 25);
        Treap.Node<String> newNode2 = new Treap.Node<>("D", 9);
        Treap.Node<String> newNode3 = new Treap.Node<>("F", 2);

        Chapter13.treapInsert(treap, newNode1);
        Chapter13.treapInsert(treap, newNode2);
        Chapter13.treapInsert(treap, newNode3);

        assertTreap(treap);
        Array<String> expectedElements = Array.of("A", "B", "C", "D", "E", "F", "G", "H", "I", "K");
        Array<String> actualElements = treap.toArray();
        assertArrayEquals(expectedElements, actualElements);
    }

    private <E extends Comparable<? super E>> void assertTreap(Treap<E> T) {
        if (T.root != null) {
            assertTreap(T, T.root);
        }
    }

    private <E extends Comparable<? super E>> void assertTreap(Treap<E> T, Treap.Node<E> x) {
        if (x.left != null) {
            assertTrue(x.priority < x.left.priority);
            Array<E> leftElements = T.toArray(x.left);
            for (int i = 1; i <= leftElements.length; i++) {
                assertTrue(leq(leftElements.at(i), x.key));
            }
            assertTreap(T, x.left);
        }
        if (x.right != null) {
            assertTrue(x.priority < x.right.priority);
            Array<E> rightElements = T.toArray(x.right);
            for (int i = 1; i <= rightElements.length; i++) {
                assertTrue(geq(rightElements.at(i), x.key));
            }
            assertTreap(T, x.right);
        }
    }

}
