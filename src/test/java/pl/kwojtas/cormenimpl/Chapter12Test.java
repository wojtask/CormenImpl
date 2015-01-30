package pl.kwojtas.cormenimpl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pl.kwojtas.cormenimpl.util.BinaryTree;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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

    private BinaryTree<Integer> getExampleBinaryTree() {
        BinaryTree<Integer> tree = new BinaryTree<>();
        BinaryTree<Integer>.Node x1 = tree.new Node(10);
        BinaryTree<Integer>.Node x2 = tree.new Node(4);
        BinaryTree<Integer>.Node x3 = tree.new Node(14);
        BinaryTree<Integer>.Node x4 = tree.new Node(1);
        BinaryTree<Integer>.Node x5 = tree.new Node(11);
        BinaryTree<Integer>.Node x6 = tree.new Node(19);
        BinaryTree<Integer>.Node x7 = tree.new Node(20);
        tree.root = x1;
        x1.left = x2;
        x2.p = x1;
        x1.right = x3;
        x3.p = x1;
        x2.left = x4;
        x4.p = x2;
        x3.left = x5;
        x5.p = x3;
        x3.right = x6;
        x6.p = x3;
        x6.right = x7;
        x7.p = x6;
        return tree;
    }

    @Test
    public void shouldPrintOutEmptyTreeInInorder() {
        // given

        // when
        Chapter12.inorderTreeWalk(null);

        // then
        assertEquals(0, outContent.size());
    }

    @Test
    public void shouldPrintOutNonEmptyTreeInInorder() {
        // given
        BinaryTree<Integer> tree = getExampleBinaryTree();

        // when
        Chapter12.inorderTreeWalk(tree.root);

        // then
        String[] actualOutput = splitOutContent();
        String[] expectedOutput = new String[]{"1","4","10","11","14","19","20"};
        assertArrayEquals(expectedOutput, actualOutput);
    }

    @Test
     public void shouldPrintOutEmptyTreeInPreorder() {
        // given

        // when
        Chapter12.preorderTreeWalk(null);

        // then
        assertEquals(0, outContent.size());
    }

    @Test
    public void shouldPrintOutNonEmptyTreeInPreorder() {
        // given
        BinaryTree<Integer> tree = getExampleBinaryTree();

        // when
        Chapter12.preorderTreeWalk(tree.root);

        // then
        String[] actualOutput = splitOutContent();
        String[] expectedOutput = new String[]{"10","4","1","14","11","19","20"};
        assertArrayEquals(expectedOutput, actualOutput);
    }

    @Test
    public void shouldPrintOutEmptyTreeInPostorder() {
        // given

        // when
        Chapter12.postorderTreeWalk(null);

        // then
        assertEquals(0, outContent.size());
    }

    @Test
    public void shouldPrintOutNonEmptyTreeInPostorder() {
        // given
        BinaryTree<Integer> tree = getExampleBinaryTree();

        // when
        Chapter12.postorderTreeWalk(tree.root);

        // then
        String[] actualOutput = splitOutContent();
        String[] expectedOutput = new String[]{"1","4","11","20","19","14","10"};
        assertArrayEquals(expectedOutput, actualOutput);
    }

    @Test
    public void shouldFindNodeInTree() {
        // given
        BinaryTree<Integer> tree = getExampleBinaryTree();

        // when
        BinaryTree<Integer>.Node actualFoundNode = Chapter12.treeSearch(tree.root, 11);

        // then
        assertNotNull(actualFoundNode);
        assertEquals(new Integer(11), actualFoundNode.key);
        while (actualFoundNode != tree.root) {
            actualFoundNode = actualFoundNode.p;
        }
    }

    @Test
    public void shouldNotFindNonexistentNodeInTree() {
        // given
        BinaryTree<Integer> tree = getExampleBinaryTree();

        // when
        BinaryTree<Integer>.Node actualFoundNode = Chapter12.treeSearch(tree.root, 17);

        // then
        assertNull(actualFoundNode);
    }

    @Test
    public void shouldFindNodeInTreeUsingIterativeTreeSearch() {
        // given
        BinaryTree<Integer> tree = getExampleBinaryTree();

        // when
        BinaryTree<Integer>.Node actualFoundNode = Chapter12.iterativeTreeSearch(tree.root, 11);

        // then
        assertNotNull(actualFoundNode);
        assertEquals(new Integer(11), actualFoundNode.key);
        while (actualFoundNode != tree.root) {
            actualFoundNode = actualFoundNode.p;
        }
    }

    @Test
    public void shouldNotFindNonexistentNodeInTreeUsingIterativeTreeSearch() {
        // given
        BinaryTree<Integer> tree = getExampleBinaryTree();

        // when
        BinaryTree<Integer>.Node actualFoundNode = Chapter12.iterativeTreeSearch(tree.root, 17);

        // then
        assertNull(actualFoundNode);
    }

    @Test
    public void shouldFindTreeMinimum() {
        // given
        BinaryTree<Integer> tree = getExampleBinaryTree();

        // when
        BinaryTree<Integer>.Node actualMinimum = Chapter12.treeMinimum(tree.root);

        // then
        assertNotNull(actualMinimum);
        assertEquals(new Integer(1), actualMinimum.key);
        while (actualMinimum != tree.root) {
            actualMinimum = actualMinimum.p;
        }
    }

    @Test
    public void shouldFindTreeMaximum() {
        // given
        BinaryTree<Integer> tree = getExampleBinaryTree();

        // when
        BinaryTree<Integer>.Node actualMaximum = Chapter12.treeMaximum(tree.root);

        // then
        assertNotNull(actualMaximum);
        assertEquals(new Integer(20), actualMaximum.key);
        while (actualMaximum != tree.root) {
            actualMaximum = actualMaximum.p;
        }
    }

    @Test
    public void shouldFindSuccessorInTree() {
        // given
        BinaryTree<Integer> tree = getExampleBinaryTree();

        // when
        BinaryTree<Integer>.Node actualSuccessor = Chapter12.treeSuccessor(tree.root);

        // then
        assertNotNull(actualSuccessor);
        assertEquals(new Integer(11), actualSuccessor.key);
        while (actualSuccessor != tree.root) {
            actualSuccessor = actualSuccessor.p;
        }
    }

    @Test
    public void shouldFindTreeMinimumUsingRecursiveTreeMinimum() {
        // given
        BinaryTree<Integer> tree = getExampleBinaryTree();

        // when
        BinaryTree<Integer>.Node actualMinimum = Chapter12.recursiveTreeMinimum(tree.root);

        // then
        assertNotNull(actualMinimum);
        assertEquals(new Integer(1), actualMinimum.key);
        while (actualMinimum != tree.root) {
            actualMinimum = actualMinimum.p;
        }
    }

    @Test
    public void shouldFindTreeMaximumUsingRecursiveTreeMaximum() {
        // given
        BinaryTree<Integer> tree = getExampleBinaryTree();

        // when
        BinaryTree<Integer>.Node actualMaximum = Chapter12.recursiveTreeMaximum(tree.root);

        // then
        assertNotNull(actualMaximum);
        assertEquals(new Integer(20), actualMaximum.key);
        while (actualMaximum != tree.root) {
            actualMaximum = actualMaximum.p;
        }
    }

    @Test
    public void shouldFindPredecessorInTree() {
        // given
        BinaryTree<Integer> tree = getExampleBinaryTree();

        // when
        BinaryTree<Integer>.Node actualPredecessor = Chapter12.treePredecessor(tree.root.right.left); // node of key = 11

        // then
        assertNotNull(actualPredecessor);
        assertEquals(new Integer(10), actualPredecessor.key);
        while (actualPredecessor != tree.root) {
            actualPredecessor = actualPredecessor.p;
        }
    }

    @Test
    public void shouldPrintOutEmptyTreeUsingInorderTreeWalk_() {
        // given

        // when
        Chapter12.inorderTreeWalk_(new BinaryTree<>());

        // then
        assertEquals(0, outContent.size());
    }

    @Test
    public void shouldPrintOutNonEmptyTreeUsingInorderTreeWalk_() {
        // given
        BinaryTree<Integer> tree = getExampleBinaryTree();

        // when
        Chapter12.inorderTreeWalk_(tree);

        // then
        String[] actualOutput = splitOutContent();
        String[] expectedOutput = new String[]{"1","4","10","11","14","19","20"};
        assertArrayEquals(expectedOutput, actualOutput);
    }

    @Test
    public void shouldInsertNodeToEmptyTree() {
        // given
        BinaryTree<Integer> tree = new BinaryTree<>();
        BinaryTree<Integer>.Node nodeToInsert = tree.new Node(12);

        // when
        Chapter12.treeInsert(tree, nodeToInsert);

        // then
        assertEquals(new Integer(12), nodeToInsert.key);
        assertNull(nodeToInsert.left);
        assertNull(nodeToInsert.right);
        assertNull(nodeToInsert.p);
        assertEquals(tree.root, nodeToInsert);
    }

    @Test
    public void shouldInsertNodeToNonEmptyTree() {
        // given
        BinaryTree<Integer> tree = getExampleBinaryTree();
        BinaryTree<Integer>.Node nodeToInsert = tree.new Node(12);

        // when
        Chapter12.treeInsert(tree, nodeToInsert);

        // then
        assertEquals(new Integer(12), nodeToInsert.key);
        assertNull(nodeToInsert.left);
        assertNull(nodeToInsert.right);
        assertEquals(nodeToInsert, nodeToInsert.p.right);
        assertEquals(new Integer(11), nodeToInsert.p.key);
        while (nodeToInsert != tree.root) {
            nodeToInsert = nodeToInsert.p;
        }
    }

    @Test
    public void shouldDeleteLeafFromTree() {
        // given
        BinaryTree<Integer> tree = getExampleBinaryTree();
        int exampleTreeSize = tree.getSize();

        // when
        Chapter12.treeDelete(tree, tree.root.right.left);

        // then
        assertNull(tree.root.right.left);
        assertEquals(exampleTreeSize - 1, tree.getSize());
    }

    @Test
    public void shouldDeleteNodeWithOneChildFromTree() {
        // given
        BinaryTree<Integer> tree = getExampleBinaryTree();
        int exampleTreeSize = tree.getSize();

        // when
        Chapter12.treeDelete(tree, tree.root.right.right);

        // then
        assertNotNull(tree.root.right.right);
        assertEquals(new Integer(20), tree.root.right.right.key);
        assertEquals(exampleTreeSize - 1, tree.getSize());
    }

    @Test
    public void shouldDeleteNodeWithTwoChildrenFromTree() {
        // given
        BinaryTree<Integer> tree = getExampleBinaryTree();
        int exampleTreeSize = tree.getSize();

        // when
        Chapter12.treeDelete(tree, tree.root.right);

        // then
        assertNotNull(tree.root.right);
        assertEquals(new Integer(19), tree.root.right.key);
        assertEquals(exampleTreeSize - 1, tree.getSize());
    }

    @Test
    public void shouldInsertNodeToEmptyTreeUsingRecursiveTreeInsert() {
        // given
        BinaryTree<Integer> tree = new BinaryTree<>();
        BinaryTree<Integer>.Node nodeToInsert = tree.new Node(12);

        // when
        Chapter12.recursiveTreeInsert(tree, tree.root, nodeToInsert);

        // then
        assertEquals(new Integer(12), nodeToInsert.key);
        assertNull(nodeToInsert.left);
        assertNull(nodeToInsert.right);
        assertNull(nodeToInsert.p);
        assertEquals(tree.root, nodeToInsert);
    }

    @Test
    public void shouldInsertNodeToNonEmptyTreeUsingRecursiveTreeInsert() {
        // given
        BinaryTree<Integer> tree = getExampleBinaryTree();
        BinaryTree<Integer>.Node nodeToInsert = tree.new Node(12);

        // when
        Chapter12.recursiveTreeInsert(tree, tree.root, nodeToInsert);

        // then
        assertEquals(new Integer(12), nodeToInsert.key);
        assertNull(nodeToInsert.left);
        assertNull(nodeToInsert.right);
        assertEquals(nodeToInsert, nodeToInsert.p.right);
        assertEquals(new Integer(11), nodeToInsert.p.key);
        while (nodeToInsert != tree.root) {
            nodeToInsert = nodeToInsert.p;
        }
    }

}