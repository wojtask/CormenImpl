package pl.kwojtas.cormenimpl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import pl.kwojtas.cormenimpl.util.Array;
import pl.kwojtas.cormenimpl.util.BinaryTree;
import pl.kwojtas.cormenimpl.util.Util;

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
import static pl.kwojtas.cormenimpl.TestUtil.assertShuffled;
import static pl.kwojtas.cormenimpl.TestUtil.assertSorted;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Util.class})
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
        BinaryTree<Integer> tree = new BinaryTree<>();
        BinaryTree.Node<Integer> x1 = new BinaryTree.Node<>(10);
        BinaryTree.Node<Integer> x2 = new BinaryTree.Node<>(4);
        BinaryTree.Node<Integer> x3 = new BinaryTree.Node<>(14);
        BinaryTree.Node<Integer> x4 = new BinaryTree.Node<>(1);
        BinaryTree.Node<Integer> x5 = new BinaryTree.Node<>(11);
        BinaryTree.Node<Integer> x6 = new BinaryTree.Node<>(19);
        BinaryTree.Node<Integer> x7 = new BinaryTree.Node<>(20);
        tree.root = x1;
        x1.left = x2;  //             10
        x2.p = x1;     //            /  \
        x1.right = x3; //           /    \
        x3.p = x1;     //          4     14
        x2.left = x4;  //         /      / \
        x4.p = x2;     //        /      /   \
        x3.left = x5;  //       2      11   19
        x5.p = x3;     //                     \
        x3.right = x6; //                      \
        x6.p = x3;     //                      20
        x6.right = x7;
        x7.p = x6;
        return tree;
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

        BinaryTree.Node<Integer> actualFoundNode = Chapter12.treeSearch(tree.root, key);

        assertNotNull(actualFoundNode);
        assertEquals(Integer.valueOf(key), actualFoundNode.key);
        while (actualFoundNode != tree.root) {
            actualFoundNode = actualFoundNode.p;
        }
    }

    @Test
    public void shouldNotFindNonexistentNodeInTree() {
        BinaryTree<Integer> tree = getExemplaryBinaryTree();
        int key = 17;

        BinaryTree.Node<Integer> actualFoundNode = Chapter12.treeSearch(tree.root, key);

        assertNull(actualFoundNode);
    }

    @Test
    public void shouldFindNodeInTreeUsingIterativeTreeSearch() {
        BinaryTree<Integer> tree = getExemplaryBinaryTree();
        int key = 11;

        BinaryTree.Node<Integer> actualFoundNode = Chapter12.iterativeTreeSearch(tree.root, key);

        assertNotNull(actualFoundNode);
        assertEquals(Integer.valueOf(key), actualFoundNode.key);
        while (actualFoundNode != tree.root) {
            actualFoundNode = actualFoundNode.p;
        }
    }

    @Test
    public void shouldNotFindNonexistentNodeInTreeUsingIterativeTreeSearch() {
        BinaryTree<Integer> tree = getExemplaryBinaryTree();
        int key = 17;

        BinaryTree.Node<Integer> actualFoundNode = Chapter12.iterativeTreeSearch(tree.root, key);

        assertNull(actualFoundNode);
    }

    @Test
    public void shouldFindTreeMinimum() {
        BinaryTree<Integer> tree = getExemplaryBinaryTree();

        BinaryTree.Node<Integer> actualMinimum = Chapter12.treeMinimum(tree.root);

        assertNotNull(actualMinimum);
        assertEquals(Integer.valueOf(1), actualMinimum.key);
        while (actualMinimum != tree.root) {
            actualMinimum = actualMinimum.p;
        }
    }

    @Test
    public void shouldFindTreeMaximum() {
        BinaryTree<Integer> tree = getExemplaryBinaryTree();

        BinaryTree.Node<Integer> actualMaximum = Chapter12.treeMaximum(tree.root);

        assertNotNull(actualMaximum);
        assertEquals(Integer.valueOf(20), actualMaximum.key);
        while (actualMaximum != tree.root) {
            actualMaximum = actualMaximum.p;
        }
    }

    @Test
    public void shouldFindSuccessorInTree() {
        BinaryTree<Integer> tree = getExemplaryBinaryTree();

        BinaryTree.Node<Integer> actualSuccessor = Chapter12.treeSuccessor(tree.root);

        assertNotNull(actualSuccessor);
        assertEquals(Integer.valueOf(11), actualSuccessor.key);
        while (actualSuccessor != tree.root) {
            actualSuccessor = actualSuccessor.p;
        }
    }

    @Test
    public void shouldNotFindNonexistingSuccessorInTree() {
        BinaryTree<Integer> tree = getExemplaryBinaryTree();
        BinaryTree.Node<Integer> node = tree.root.right.right.right; // a node with maximum value in the tree

        BinaryTree.Node<Integer> actualSuccessor = Chapter12.treeSuccessor(node);

        assertNull(actualSuccessor);
    }

    @Test
    public void shouldFindTreeMinimumUsingRecursiveTreeMinimum() {
        BinaryTree<Integer> tree = getExemplaryBinaryTree();

        BinaryTree.Node<Integer> actualMinimum = Chapter12.recursiveTreeMinimum(tree.root);

        assertNotNull(actualMinimum);
        assertEquals(Integer.valueOf(1), actualMinimum.key);
        while (actualMinimum != tree.root) {
            actualMinimum = actualMinimum.p;
        }
    }

    @Test
    public void shouldFindTreeMaximumUsingRecursiveTreeMaximum() {
        BinaryTree<Integer> tree = getExemplaryBinaryTree();

        BinaryTree.Node<Integer> actualMaximum = Chapter12.recursiveTreeMaximum(tree.root);

        assertNotNull(actualMaximum);
        assertEquals(Integer.valueOf(20), actualMaximum.key);
        while (actualMaximum != tree.root) {
            actualMaximum = actualMaximum.p;
        }
    }

    @Test
    public void shouldFindPredecessorInTree() {
        BinaryTree<Integer> tree = getExemplaryBinaryTree();
        BinaryTree.Node<Integer> node = tree.root.right.left; // node of key = 11

        BinaryTree.Node<Integer> actualPredecessor = Chapter12.treePredecessor(node);

        assertNotNull(actualPredecessor);
        assertEquals(Integer.valueOf(10), actualPredecessor.key);
        while (actualPredecessor != tree.root) {
            actualPredecessor = actualPredecessor.p;
        }
    }

    @Test
    public void shouldFindPredecessorInTreeForNodeWithLeftChild() {
        BinaryTree<Integer> tree = getExemplaryBinaryTree();

        BinaryTree.Node<Integer> actualPredecessor = Chapter12.treePredecessor(tree.root);

        assertNotNull(actualPredecessor);
        assertEquals(Integer.valueOf(4), actualPredecessor.key);
        while (actualPredecessor != tree.root) {
            actualPredecessor = actualPredecessor.p;
        }
    }

    @Test
    public void shouldPrintOutEmptyTreeUsingInorderTreeWalk_() {
        BinaryTree<Integer> tree = new BinaryTree<>();

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
        BinaryTree<Integer> tree = new BinaryTree<>();
        BinaryTree.Node<Integer> nodeToInsert = new BinaryTree.Node<>(12);

        Chapter12.treeInsert(tree, nodeToInsert);

        assertEquals(Integer.valueOf(12), nodeToInsert.key);
        assertNull(nodeToInsert.left);
        assertNull(nodeToInsert.right);
        assertNull(nodeToInsert.p);
        assertEquals(tree.root, nodeToInsert);
    }

    @Test
    public void shouldInsertNodeToNonemptyTree() {
        BinaryTree<Integer> tree = getExemplaryBinaryTree();
        BinaryTree.Node<Integer> nodeToInsert = new BinaryTree.Node<>(12);

        Chapter12.treeInsert(tree, nodeToInsert);

        assertEquals(Integer.valueOf(12), nodeToInsert.key);
        assertNull(nodeToInsert.left);
        assertNull(nodeToInsert.right);
        assertEquals(nodeToInsert, nodeToInsert.p.right); // for the particular tree the new node will be right son of its parent
        assertEquals(Integer.valueOf(11), nodeToInsert.p.key); // and its parent will be the node of key = 11
        while (nodeToInsert != tree.root) {
            nodeToInsert = nodeToInsert.p;
        }
    }

    @Test
    public void shouldInsertNodeToNonemptyTree2() {
        BinaryTree<Integer> tree = getExemplaryBinaryTree();
        BinaryTree.Node<Integer> nodeToInsert = new BinaryTree.Node<>(18);

        Chapter12.treeInsert(tree, nodeToInsert);

        assertEquals(Integer.valueOf(18), nodeToInsert.key);
        assertNull(nodeToInsert.left);
        assertNull(nodeToInsert.right);
        assertEquals(nodeToInsert, nodeToInsert.p.left); // for the particular tree the new node will be left son of its parent
        assertEquals(Integer.valueOf(19), nodeToInsert.p.key); // and its parent will be the node of key = 11
        while (nodeToInsert != tree.root) {
            nodeToInsert = nodeToInsert.p;
        }
    }

    @Test
    public void shouldDeleteLeafFromTree() {
        BinaryTree<Integer> tree = getExemplaryBinaryTree();
        int exemplaryTreeSize = tree.getSize();

        BinaryTree.Node<Integer> actualDeletedNode = Chapter12.treeDelete(tree, tree.root.right.left); // a leaf

        assertEquals(Integer.valueOf(11), actualDeletedNode.key);
        assertNull(tree.root.right.left);
        assertEquals(exemplaryTreeSize - 1, tree.getSize());
    }

    @Test
    public void shouldDeleteNodeWithOneChildFromTree() {
        BinaryTree<Integer> tree = getExemplaryBinaryTree();
        int exemplaryTreeSize = tree.getSize();

        BinaryTree.Node<Integer> actualDeletedNode = Chapter12.treeDelete(tree, tree.root.left); // a node with one child

        assertEquals(Integer.valueOf(4), actualDeletedNode.key);
        assertNotNull(tree.root.left);
        assertEquals(Integer.valueOf(1), tree.root.left.key);
        assertEquals(exemplaryTreeSize - 1, tree.getSize());
    }

    @Test
    public void shouldDeleteNodeWithTwoChildrenFromTree() {
        BinaryTree<Integer> tree = getExemplaryBinaryTree();
        int exemplaryTreeSize = tree.getSize();

        BinaryTree.Node<Integer> actualDeletedNode = Chapter12.treeDelete(tree, tree.root.right); // a node with two children (successor's key = 19)

        assertEquals(Integer.valueOf(19), actualDeletedNode.key);
        assertNotNull(tree.root.right);
        assertEquals(Integer.valueOf(19), tree.root.right.key);
        assertEquals(exemplaryTreeSize - 1, tree.getSize());
    }

    @Test
    public void shouldDeleteRootFromTree() {
        BinaryTree<Integer> tree = new BinaryTree<>();
        tree.root = new BinaryTree.Node<>(10);

        BinaryTree.Node<Integer> actualDeletedNode = Chapter12.treeDelete(tree, tree.root);

        assertEquals(Integer.valueOf(10), actualDeletedNode.key);
        assertNull(tree.root);
    }

    @Test
    public void shouldInsertNodeToEmptyTreeUsingRecursiveTreeInsert() {
        BinaryTree<Integer> tree = new BinaryTree<>();
        BinaryTree.Node<Integer> nodeToInsert = new BinaryTree.Node<>(12);

        Chapter12.recursiveTreeInsert(tree, tree.root, nodeToInsert);

        assertEquals(Integer.valueOf(12), nodeToInsert.key);
        assertNull(nodeToInsert.left);
        assertNull(nodeToInsert.right);
        assertNull(nodeToInsert.p);
        assertEquals(tree.root, nodeToInsert);
    }

    @Test
    public void shouldInsertNodeToNonemptyTreeUsingRecursiveTreeInsert() {
        BinaryTree<Integer> tree = getExemplaryBinaryTree();
        BinaryTree.Node<Integer> nodeToInsert = new BinaryTree.Node<>(12);

        Chapter12.recursiveTreeInsert(tree, tree.root, nodeToInsert);

        assertEquals(Integer.valueOf(12), nodeToInsert.key);
        assertNull(nodeToInsert.left);
        assertNull(nodeToInsert.right);
        assertEquals(nodeToInsert, nodeToInsert.p.right); // for the particular tree the new node will be right son of its parent
        assertEquals(Integer.valueOf(11), nodeToInsert.p.key); // and its parent will be the node of key = 11
        while (nodeToInsert != tree.root) {
            nodeToInsert = nodeToInsert.p;
        }
    }

    @Test
    public void shouldInsertNodeToNonemptyTreeUsingRecursiveTreeInsert2() {
        BinaryTree<Integer> tree = getExemplaryBinaryTree();
        BinaryTree.Node<Integer> nodeToInsert = new BinaryTree.Node<>(18);

        Chapter12.recursiveTreeInsert(tree, tree.root, nodeToInsert);

        assertEquals(Integer.valueOf(18), nodeToInsert.key);
        assertNull(nodeToInsert.left);
        assertNull(nodeToInsert.right);
        assertEquals(nodeToInsert, nodeToInsert.p.left); // for the particular tree the new node will be left son of its parent
        assertEquals(Integer.valueOf(19), nodeToInsert.p.key); // and its parent will be the node of key = 19
        while (nodeToInsert != tree.root) {
            nodeToInsert = nodeToInsert.p;
        }
    }

    @Test
    public void shouldSortArrayUsingInorderSort() {
        Array<Integer> array = new Array<>(5, 7, 9, 2, 6, 8, 6, 6, 3, 1, 7, 8);

        Chapter12.inorderSort(array);

        String[] actualOutput = splitOutContent();
        String[] expectedOutput = new String[]{"1", "2", "3", "5", "6", "6", "6", "7", "7", "8", "8", "9"};
        assertArrayEquals(expectedOutput, actualOutput);
    }

    @Test
    public void shouldDeleteLeafFromTreeUsingSafeTreeDelete() {
        BinaryTree<Integer> tree = getExemplaryBinaryTree();
        int exemplaryTreeSize = tree.getSize();

        Chapter12.safeTreeDelete(tree, tree.root.right.left); // a leaf

        assertNull(tree.root.right.left);
        assertEquals(exemplaryTreeSize - 1, tree.getSize());
    }

    @Test
    public void shouldDeleteNodeWithTwoChildrenFromTreeUsingSafeTreeDelete() {
        BinaryTree<Integer> tree = getExemplaryBinaryTree();
        int exemplaryTreeSize = tree.getSize();

        Chapter12.safeTreeDelete(tree, tree.root.right); // a node with two children (successor's key = 19)

        assertNotNull(tree.root.right);
        assertEquals(Integer.valueOf(19), tree.root.right.key);
        assertEquals(exemplaryTreeSize - 1, tree.getSize());
    }

    @Test
    public void shouldDeleteNodeWithTwoChildrenFromTreeUsingSafeTreeDelete2() {
        BinaryTree<Integer> tree = getExemplaryBinaryTreeForSafeDelete();
        int exemplaryTreeSize = tree.getSize();

        Chapter12.safeTreeDelete(tree, tree.root.left); // a node with two children (successor's key = 6)

        assertNotNull(tree.root.left);
        assertEquals(Integer.valueOf(6), tree.root.left.key);
        assertEquals(exemplaryTreeSize - 1, tree.getSize());
    }

    private BinaryTree<Integer> getExemplaryBinaryTreeForSafeDelete() {
        BinaryTree<Integer> tree = new BinaryTree<>();
        BinaryTree.Node<Integer> x1 = new BinaryTree.Node<>(9);
        BinaryTree.Node<Integer> x2 = new BinaryTree.Node<>(5);
        BinaryTree.Node<Integer> x3 = new BinaryTree.Node<>(10);
        BinaryTree.Node<Integer> x4 = new BinaryTree.Node<>(2);
        BinaryTree.Node<Integer> x5 = new BinaryTree.Node<>(7);
        BinaryTree.Node<Integer> x6 = new BinaryTree.Node<>(6);
        BinaryTree.Node<Integer> x7 = new BinaryTree.Node<>(8);
        tree.root = x1;
        x1.left = x2;  //             9
        x2.p = x1;     //            / \
        x1.right = x3; //           /   \
        x3.p = x1;     //          5    10
        x2.left = x4;  //         / \
        x4.p = x2;     //        /   \
        x2.right = x5; //       2     7
        x5.p = x2;     //            / \
        x5.left = x6;  //           /   \
        x6.p = x5;     //          6     8
        x5.right = x7;
        x7.p = x5;
        return tree;
    }

    @Test
    public void shouldDeleteLeafFromTreeUsingFairTreeDelete() {
        BinaryTree<Integer> tree = getExemplaryBinaryTree();
        int exemplaryTreeSize = tree.getSize();

        BinaryTree.Node<Integer> actualDeletedNode = Chapter12.fairTreeDelete(tree, tree.root.right.left); // a leaf

        assertEquals(Integer.valueOf(11), actualDeletedNode.key);
        assertNull(tree.root.right.left);
        assertEquals(exemplaryTreeSize - 1, tree.getSize());
    }

    @Test
    public void shouldDeleteNodeWithOneChildFromTreeUsingFairTreeDelete() {
        BinaryTree<Integer> tree = getExemplaryBinaryTree();
        int exemplaryTreeSize = tree.getSize();

        BinaryTree.Node<Integer> actualDeletedNode = Chapter12.fairTreeDelete(tree, tree.root.left); // a node with one child

        assertEquals(Integer.valueOf(4), actualDeletedNode.key);
        assertNotNull(tree.root.left);
        assertEquals(Integer.valueOf(1), tree.root.left.key);
        assertEquals(exemplaryTreeSize - 1, tree.getSize());
    }

    @Test
    public void shouldDeleteNodeWithTwoChildrenFromTreeUsingFairTreeDeleteBySplicingOutItsPredecessor() {
        BinaryTree<Integer> tree = getExemplaryBinaryTree();
        int exemplaryTreeSize = tree.getSize();
        mockStatic(Util.class);
        when(Util.random()).thenReturn(0);

        BinaryTree.Node<Integer> actualDeletedNode = Chapter12.fairTreeDelete(tree, tree.root.right); // a node with two children (predecessor's key = 11)

        assertEquals(Integer.valueOf(11), actualDeletedNode.key);
        assertNotNull(tree.root.right);
        assertEquals(Integer.valueOf(11), tree.root.right.key);
        assertEquals(exemplaryTreeSize - 1, tree.getSize());
    }

    @Test
    public void shouldDeleteNodeWithTwoChildrenFromTreeUsingFairTreeDeleteBySplicingOutItsSuccessor() {
        BinaryTree<Integer> tree = getExemplaryBinaryTree();
        int exemplaryTreeSize = tree.getSize();
        mockStatic(Util.class);
        when(Util.random()).thenReturn(1);

        BinaryTree.Node<Integer> actualDeletedNode = Chapter12.fairTreeDelete(tree, tree.root.right); // a node with two children (successor's key = 19)

        assertEquals(Integer.valueOf(19), actualDeletedNode.key);
        assertNotNull(tree.root.right);
        assertEquals(Integer.valueOf(19), tree.root.right.key);
        assertEquals(exemplaryTreeSize - 1, tree.getSize());
    }

    @Test
    public void shouldDeleteRootFromTreeUsingFairTreeDelete() {
        BinaryTree<Integer> tree = new BinaryTree<>();
        tree.root = new BinaryTree.Node<>(10);

        BinaryTree.Node<Integer> actualDeletedNode = Chapter12.fairTreeDelete(tree, tree.root);

        assertEquals(Integer.valueOf(10), actualDeletedNode.key);
        assertNull(tree.root);
    }

    @Test
    public void shouldSortBitStringsUsingRadixTree() {
        Array<String> array = new Array<>("1011", "10", "011", "100", "0");

        Chapter12.bitStringsSort(array);

        String[] actualOutput = splitOutContent();
        String[] expectedOutput = new String[]{"0", "011", "10", "100", "1011"};
        assertArrayEquals(expectedOutput, actualOutput);
    }

    @Test
    public void shouldSortArrayUsingTreeBuildingQuicksort() {
        Array<Integer> array = new Array<>(5, 7, 9, 2, 6, 8, 6, 6, 3, 1, 7, 8);
        Array<Integer> original = new Array<>(array);

        Chapter12.treeBuildingQuicksort(array, 1, array.length);

        assertShuffled(original, array);
        assertSorted(array);
    }

}
