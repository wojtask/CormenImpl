package pl.kwojtas.cormenimpl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import pl.kwojtas.cormenimpl.datastructure.Array;
import pl.kwojtas.cormenimpl.datastructure.BinaryTree;
import pl.kwojtas.cormenimpl.datastructure.BinaryTree.Node;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static pl.kwojtas.cormenimpl.Fundamental.geq;
import static pl.kwojtas.cormenimpl.Fundamental.leq;
import static pl.kwojtas.cormenimpl.TestUtil.assertShuffled;
import static pl.kwojtas.cormenimpl.TestUtil.assertSorted;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Fundamental.class})
public class Chapter12Test {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUpOutStream() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void cleanupOutStream() {
        System.setOut(null);
    }

    private String[] splitOutContent() {
        return outContent.toString().split(System.getProperty("line.separator"));
    }

    private BinaryTree<Integer> getExemplaryBinaryTree() {
        return new BinaryTree<>(
                new Node<>(10,
                        new Node<>(4,
                                new Node<>(1),
                                null
                        ),
                        new Node<>(14,
                                new Node<>(11),
                                new Node<>(19,
                                        null,
                                        new Node<>(20)
                                )
                        )
                )
        );
    }

    @Test
    public void shouldHavePrivateConstructor() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<Chapter12> constructor = Chapter12.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    public void shouldPrintOutEmptyTreeInInorder() {

        Chapter12.inorderTreeWalk(null);

        assertEquals(0, outContent.size());
    }

    @Test
    public void shouldPrintOutNonemptyTreeInInorder() {
        BinaryTree<Integer> tree = getExemplaryBinaryTree();

        Chapter12.inorderTreeWalk(tree.root);

        String[] actualOutput = splitOutContent();
        String[] expectedOutput = new String[]{"1", "4", "10", "11", "14", "19", "20"};
        assertArrayEquals(expectedOutput, actualOutput);
    }

    @Test
    public void shouldPrintOutEmptyTreeInPreorder() {

        Chapter12.preorderTreeWalk(null);

        assertEquals(0, outContent.size());
    }

    @Test
    public void shouldPrintOutNonemptyTreeInPreorder() {
        BinaryTree<Integer> tree = getExemplaryBinaryTree();

        Chapter12.preorderTreeWalk(tree.root);

        String[] actualOutput = splitOutContent();
        String[] expectedOutput = new String[]{"10", "4", "1", "14", "11", "19", "20"};
        assertArrayEquals(expectedOutput, actualOutput);
    }

    @Test
    public void shouldPrintOutEmptyTreeInPostorder() {

        Chapter12.postorderTreeWalk(null);

        assertEquals(0, outContent.size());
    }

    @Test
    public void shouldPrintOutNonemptyTreeInPostorder() {
        BinaryTree<Integer> tree = getExemplaryBinaryTree();

        Chapter12.postorderTreeWalk(tree.root);

        String[] actualOutput = splitOutContent();
        String[] expectedOutput = new String[]{"1", "4", "11", "20", "19", "14", "10"};
        assertArrayEquals(expectedOutput, actualOutput);
    }

    @Test
    public void shouldFindNodeInTree() {
        BinaryTree<Integer> tree = getExemplaryBinaryTree();
        int key = 11;

        Node<Integer> actualFoundNode = Chapter12.treeSearch(tree.root, key);

        assertNotNull(actualFoundNode);
        assertEquals(Integer.valueOf(key), actualFoundNode.key);
    }

    @Test
    public void shouldNotFindNonexistentNodeInTree() {
        BinaryTree<Integer> tree = getExemplaryBinaryTree();
        int key = 17;

        Node<Integer> actualFoundNode = Chapter12.treeSearch(tree.root, key);

        assertNull(actualFoundNode);
    }

    @Test
    public void shouldFindNodeInTreeUsingIterativeTreeSearch() {
        BinaryTree<Integer> tree = getExemplaryBinaryTree();
        int key = 11;

        Node<Integer> actualFoundNode = Chapter12.iterativeTreeSearch(tree.root, key);

        assertNotNull(actualFoundNode);
        assertEquals(Integer.valueOf(key), actualFoundNode.key);
    }

    @Test
    public void shouldNotFindNonexistentNodeInTreeUsingIterativeTreeSearch() {
        BinaryTree<Integer> tree = getExemplaryBinaryTree();
        int key = 17;

        Node<Integer> actualFoundNode = Chapter12.iterativeTreeSearch(tree.root, key);

        assertNull(actualFoundNode);
    }

    @Test
    public void shouldFindTreeMinimum() {
        BinaryTree<Integer> tree = getExemplaryBinaryTree();

        Node<Integer> actualMinimum = Chapter12.treeMinimum(tree.root);

        assertNotNull(actualMinimum);
        assertEquals(Integer.valueOf(1), actualMinimum.key);
    }

    @Test
    public void shouldFindTreeMaximum() {
        BinaryTree<Integer> tree = getExemplaryBinaryTree();

        Node<Integer> actualMaximum = Chapter12.treeMaximum(tree.root);

        assertNotNull(actualMaximum);
        assertEquals(Integer.valueOf(20), actualMaximum.key);
    }

    @Test
    public void shouldFindSuccessorInTree() {
        BinaryTree<Integer> tree = getExemplaryBinaryTree();

        Node<Integer> actualSuccessor = Chapter12.treeSuccessor(tree.root);

        assertNotNull(actualSuccessor);
        assertEquals(Integer.valueOf(11), actualSuccessor.key);
    }

    @Test
    public void shouldNotFindNonexistingSuccessorInTree() {
        BinaryTree<Integer> tree = getExemplaryBinaryTree();
        Node<Integer> node = tree.root.right.right.right; // a node with maximum value in the tree

        Node<Integer> actualSuccessor = Chapter12.treeSuccessor(node);

        assertNull(actualSuccessor);
    }

    @Test
    public void shouldFindTreeMinimumUsingRecursiveTreeMinimum() {
        BinaryTree<Integer> tree = getExemplaryBinaryTree();

        Node<Integer> actualMinimum = Chapter12.recursiveTreeMinimum(tree.root);

        assertNotNull(actualMinimum);
        assertEquals(Integer.valueOf(1), actualMinimum.key);
    }

    @Test
    public void shouldFindTreeMaximumUsingRecursiveTreeMaximum() {
        BinaryTree<Integer> tree = getExemplaryBinaryTree();

        Node<Integer> actualMaximum = Chapter12.recursiveTreeMaximum(tree.root);

        assertNotNull(actualMaximum);
        assertEquals(Integer.valueOf(20), actualMaximum.key);
    }

    @Test
    public void shouldFindPredecessorInTree() {
        BinaryTree<Integer> tree = getExemplaryBinaryTree();
        Node<Integer> node = tree.root.right.left; // node of key = 11

        Node<Integer> actualPredecessor = Chapter12.treePredecessor(node);

        assertNotNull(actualPredecessor);
        assertEquals(Integer.valueOf(10), actualPredecessor.key);
    }

    @Test
    public void shouldFindPredecessorInTreeForNodeWithLeftChild() {
        BinaryTree<Integer> tree = getExemplaryBinaryTree();

        Node<Integer> actualPredecessor = Chapter12.treePredecessor(tree.root);

        assertNotNull(actualPredecessor);
        assertEquals(Integer.valueOf(4), actualPredecessor.key);
    }

    @Test
    public void shouldPrintOutEmptyTreeUsingInorderTreeWalk_() {
        BinaryTree<Integer> tree = BinaryTree.emptyTree();

        Chapter12.inorderTreeWalk_(tree);

        assertEquals(0, outContent.size());
    }

    @Test
    public void shouldPrintOutNonemptyTreeUsingInorderTreeWalk_() {
        BinaryTree<Integer> tree = getExemplaryBinaryTree();

        Chapter12.inorderTreeWalk_(tree);

        String[] actualOutput = splitOutContent();
        String[] expectedOutput = new String[]{"1", "4", "10", "11", "14", "19", "20"};
        assertArrayEquals(expectedOutput, actualOutput);
    }

    @Test
    public void shouldInsertNodeToEmptyTree() {
        BinaryTree<Integer> tree = BinaryTree.emptyTree();
        Node<Integer> nodeToInsert = new Node<>(12);

        Chapter12.treeInsert(tree, nodeToInsert);

        Array<Integer> actualElements = tree.toArray();
        Array<Integer> expectedElements = Array.of(12);
        TestUtil.assertArrayEquals(actualElements, expectedElements);
    }

    @Test
    public void shouldInsertNodeToNonemptyTree() {
        BinaryTree<Integer> tree = getExemplaryBinaryTree();
        Node<Integer> nodeToInsert = new Node<>(12);

        Chapter12.treeInsert(tree, nodeToInsert);

        Array<Integer> actualElements = tree.toArray();
        Array<Integer> expectedElements = Array.of(1, 4, 10, 11, 12, 14, 19, 20);
        TestUtil.assertArrayEquals(actualElements, expectedElements);
        assertBinarySearchTree(tree);
    }

    private <E extends Comparable<? super E>> void assertBinarySearchTree(BinaryTree<E> T) {
        assertBinarySearchTree(T, T.root);
    }

    private <E extends Comparable<? super E>> void assertBinarySearchTree(BinaryTree<E> T, Node<E> x) {
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

    @Test
    public void shouldInsertNodeToNonemptyTree2() {
        BinaryTree<Integer> tree = getExemplaryBinaryTree();
        Node<Integer> nodeToInsert = new Node<>(18);

        Chapter12.treeInsert(tree, nodeToInsert);

        Array<Integer> actualElements = tree.toArray();
        Array<Integer> expectedElements = Array.of(1, 4, 10, 11, 14, 18, 19, 20);
        TestUtil.assertArrayEquals(actualElements, expectedElements);
        assertBinarySearchTree(tree);
    }

    @Test
    public void shouldDeleteLeafFromTree() {
        BinaryTree<Integer> tree = getExemplaryBinaryTree();

        Node<Integer> actualDeletedNode = Chapter12.treeDelete(tree, tree.root.right.left); // a leaf

        assertEquals(Integer.valueOf(11), actualDeletedNode.key);
        Array<Integer> actualElements = tree.toArray();
        Array<Integer> expectedElements = Array.of(1, 4, 10, 14, 19, 20);
        TestUtil.assertArrayEquals(actualElements, expectedElements);
        assertBinarySearchTree(tree);
    }

    @Test
    public void shouldDeleteNodeWithOneChildFromTree() {
        BinaryTree<Integer> tree = getExemplaryBinaryTree();

        Node<Integer> actualDeletedNode = Chapter12.treeDelete(tree, tree.root.left); // a node with one child

        assertEquals(Integer.valueOf(4), actualDeletedNode.key);
        Array<Integer> actualElements = tree.toArray();
        Array<Integer> expectedElements = Array.of(1, 10, 11, 14, 19, 20);
        TestUtil.assertArrayEquals(actualElements, expectedElements);
        assertBinarySearchTree(tree);
    }

    @Test
    public void shouldDeleteNodeWithTwoChildrenFromTree() {
        BinaryTree<Integer> tree = getExemplaryBinaryTree();

        Node<Integer> actualDeletedNode = Chapter12.treeDelete(tree, tree.root.right); // a node with two children (successor's key = 19)

        assertEquals(Integer.valueOf(19), actualDeletedNode.key);
        Array<Integer> actualElements = tree.toArray();
        Array<Integer> expectedElements = Array.of(1, 4, 10, 11, 19, 20);
        TestUtil.assertArrayEquals(actualElements, expectedElements);
        assertBinarySearchTree(tree);
    }

    @Test
    public void shouldDeleteRootFromTree() {
        BinaryTree<Integer> tree = new BinaryTree<>(new Node<>(10));

        Node<Integer> actualDeletedNode = Chapter12.treeDelete(tree, tree.root);

        assertEquals(Integer.valueOf(10), actualDeletedNode.key);
        assertNull(tree.root);
    }

    @Test
    public void shouldInsertNodeToEmptyTreeUsingRecursiveTreeInsert() {
        BinaryTree<Integer> tree = BinaryTree.emptyTree();
        Node<Integer> nodeToInsert = new Node<>(12);

        Chapter12.recursiveTreeInsertWrapper(tree, nodeToInsert);

        Array<Integer> actualElements = tree.toArray();
        Array<Integer> expectedElements = Array.of(12);
        TestUtil.assertArrayEquals(actualElements, expectedElements);
    }

    @Test
    public void shouldInsertNodeToNonemptyTreeUsingRecursiveTreeInsert() {
        BinaryTree<Integer> tree = getExemplaryBinaryTree();
        Node<Integer> nodeToInsert = new Node<>(12);

        Chapter12.recursiveTreeInsertWrapper(tree, nodeToInsert);

        Array<Integer> actualElements = tree.toArray();
        Array<Integer> expectedElements = Array.of(1, 4, 10, 11, 12, 14, 19, 20);
        TestUtil.assertArrayEquals(actualElements, expectedElements);
        assertBinarySearchTree(tree);
    }

    @Test
    public void shouldInsertNodeToNonemptyTreeUsingRecursiveTreeInsert2() {
        BinaryTree<Integer> tree = getExemplaryBinaryTree();
        Node<Integer> nodeToInsert = new Node<>(18);

        Chapter12.recursiveTreeInsertWrapper(tree, nodeToInsert);

        Array<Integer> actualElements = tree.toArray();
        Array<Integer> expectedElements = Array.of(1, 4, 10, 11, 14, 18, 19, 20);
        TestUtil.assertArrayEquals(actualElements, expectedElements);
        assertBinarySearchTree(tree);
    }

    @Test
    public void shouldSortArrayUsingInorderSort() {
        Array<Integer> array = Array.of(5, 7, 9, 2, 6, 8, 6, 6, 3, 1, 7, 8);

        Chapter12.inorderSort(array);

        String[] actualOutput = splitOutContent();
        String[] expectedOutput = new String[]{"1", "2", "3", "5", "6", "6", "6", "7", "7", "8", "8", "9"};
        assertArrayEquals(expectedOutput, actualOutput);
    }

    @Test
    public void shouldDeleteLeafFromTreeUsingSafeTreeDelete() {
        BinaryTree<Integer> tree = getExemplaryBinaryTree();

        Chapter12.safeTreeDelete(tree, tree.root.right.left); // a leaf

        Array<Integer> actualElements = tree.toArray();
        Array<Integer> expectedElements = Array.of(1, 4, 10, 14, 19, 20);
        TestUtil.assertArrayEquals(actualElements, expectedElements);
        assertBinarySearchTree(tree);
    }

    @Test
    public void shouldDeleteNodeWithTwoChildrenFromTreeUsingSafeTreeDelete() {
        BinaryTree<Integer> tree = getExemplaryBinaryTree();

        Chapter12.safeTreeDelete(tree, tree.root.right); // a node with two children (successor's key = 19)

        Array<Integer> actualElements = tree.toArray();
        Array<Integer> expectedElements = Array.of(1, 4, 10, 11, 19, 20);
        TestUtil.assertArrayEquals(actualElements, expectedElements);
        assertBinarySearchTree(tree);
    }

    @Test
    public void shouldDeleteNodeWithTwoChildrenFromTreeUsingSafeTreeDelete2() {
        BinaryTree<Integer> tree = getExemplaryBinaryTreeForSafeDelete();

        Chapter12.safeTreeDelete(tree, tree.root.left); // a node with two children (successor's key = 6)

        Array<Integer> actualElements = tree.toArray();
        Array<Integer> expectedElements = Array.of(2, 6, 7, 8, 9, 10);
        TestUtil.assertArrayEquals(actualElements, expectedElements);
        assertBinarySearchTree(tree);
    }

    private BinaryTree<Integer> getExemplaryBinaryTreeForSafeDelete() {
        return new BinaryTree<>(
                new Node<>(9,
                        new Node<>(5,
                                new Node<>(2),
                                new Node<>(7,
                                        new Node<>(6),
                                        new Node<>(8)
                                )
                        ),
                        new Node<>(10)
                )
        );
    }

    @Test
    public void shouldDeleteLeafFromTreeUsingFairTreeDelete() {
        BinaryTree<Integer> tree = getExemplaryBinaryTree();

        Node<Integer> actualDeletedNode = Chapter12.fairTreeDelete(tree, tree.root.right.left); // a leaf

        assertEquals(Integer.valueOf(11), actualDeletedNode.key);
        Array<Integer> actualElements = tree.toArray();
        Array<Integer> expectedElements = Array.of(1, 4, 10, 14, 19, 20);
        TestUtil.assertArrayEquals(actualElements, expectedElements);
        assertBinarySearchTree(tree);
    }

    @Test
    public void shouldDeleteNodeWithOneChildFromTreeUsingFairTreeDelete() {
        BinaryTree<Integer> tree = getExemplaryBinaryTree();

        Node<Integer> actualDeletedNode = Chapter12.fairTreeDelete(tree, tree.root.left); // a node with one child

        assertEquals(Integer.valueOf(4), actualDeletedNode.key);
        Array<Integer> actualElements = tree.toArray();
        Array<Integer> expectedElements = Array.of(1, 10, 11, 14, 19, 20);
        TestUtil.assertArrayEquals(actualElements, expectedElements);
        assertBinarySearchTree(tree);
    }

    @Test
    public void shouldDeleteNodeWithTwoChildrenFromTreeUsingFairTreeDeleteBySplicingOutItsPredecessor() {
        BinaryTree<Integer> tree = getExemplaryBinaryTree();
        mockStatic(Fundamental.class);
        when(Fundamental.random()).thenReturn(0);
        when(Fundamental.leq(1, 10)).thenReturn(true);
        when(Fundamental.leq(4, 10)).thenReturn(true);
        when(Fundamental.leq(1, 4)).thenReturn(true);
        when(Fundamental.geq(11, 10)).thenReturn(true);
        when(Fundamental.geq(19, 10)).thenReturn(true);
        when(Fundamental.geq(20, 10)).thenReturn(true);
        when(Fundamental.geq(19, 11)).thenReturn(true);
        when(Fundamental.geq(20, 11)).thenReturn(true);
        when(Fundamental.geq(20, 19)).thenReturn(true);

        Node<Integer> actualDeletedNode = Chapter12.fairTreeDelete(tree, tree.root.right); // a node with two children (predecessor's key = 11)

        assertEquals(Integer.valueOf(11), actualDeletedNode.key);
        Array<Integer> actualElements = tree.toArray();
        Array<Integer> expectedElements = Array.of(1, 4, 10, 11, 19, 20);
        TestUtil.assertArrayEquals(actualElements, expectedElements);
        assertBinarySearchTree(tree);
    }

    @Test
    public void shouldDeleteNodeWithTwoChildrenFromTreeUsingFairTreeDeleteBySplicingOutItsSuccessor() {
        BinaryTree<Integer> tree = getExemplaryBinaryTree();
        mockStatic(Fundamental.class);
        when(Fundamental.random()).thenReturn(1);
        when(Fundamental.leq(1, 10)).thenReturn(true);
        when(Fundamental.leq(4, 10)).thenReturn(true);
        when(Fundamental.leq(1, 4)).thenReturn(true);
        when(Fundamental.leq(11, 19)).thenReturn(true);
        when(Fundamental.geq(11, 10)).thenReturn(true);
        when(Fundamental.geq(19, 10)).thenReturn(true);
        when(Fundamental.geq(20, 10)).thenReturn(true);
        when(Fundamental.geq(20, 19)).thenReturn(true);

        Node<Integer> actualDeletedNode = Chapter12.fairTreeDelete(tree, tree.root.right); // a node with two children (successor's key = 19)

        assertEquals(Integer.valueOf(19), actualDeletedNode.key);
        Array<Integer> actualElements = tree.toArray();
        Array<Integer> expectedElements = Array.of(1, 4, 10, 11, 19, 20);
        TestUtil.assertArrayEquals(actualElements, expectedElements);
        assertBinarySearchTree(tree);
    }

    @Test
    public void shouldDeleteRootFromTreeUsingFairTreeDelete() {
        BinaryTree<Integer> tree = new BinaryTree<>(new Node<>(10));

        Node<Integer> actualDeletedNode = Chapter12.fairTreeDelete(tree, tree.root);

        assertEquals(Integer.valueOf(10), actualDeletedNode.key);
        assertNull(tree.root);
    }

    @Test
    public void shouldSortBitStringsUsingRadixTree() {
        Array<String> array = Array.of("1011", "10", "011", "100", "0");

        Chapter12.bitStringsSort(array);

        String[] actualOutput = splitOutContent();
        String[] expectedOutput = new String[]{"0", "011", "10", "100", "1011"};
        assertArrayEquals(expectedOutput, actualOutput);
    }

    @Test
    public void shouldSortArrayUsingTreeBuildingQuicksort() {
        Array<Integer> array = Array.of(5, 7, 9, 2, 6, 8, 6, 6, 3, 1, 7, 8);
        Array<Integer> original = Array.copyOf(array);

        Chapter12.treeBuildingQuicksort(array, 1, array.length);

        assertShuffled(original, array);
        assertSorted(array);
    }

}
