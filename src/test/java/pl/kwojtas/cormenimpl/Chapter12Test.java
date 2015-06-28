package pl.kwojtas.cormenimpl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pl.kwojtas.cormenimpl.util.BinaryTree;

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
    public void shouldHavePrivateConstructor() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<Chapter12> constructor = Chapter12.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
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
    public void shouldPrintOutNonemptyTreeInInorder() {
        // given
        BinaryTree<Integer> tree = getExemplaryBinaryTree();

        // when
        Chapter12.inorderTreeWalk(tree.root);

        // then
        String[] actualOutput = splitOutContent();
        String[] expectedOutput = new String[]{"1", "4", "10", "11", "14", "19", "20"};
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
    public void shouldPrintOutNonemptyTreeInPreorder() {
        // given
        BinaryTree<Integer> tree = getExemplaryBinaryTree();

        // when
        Chapter12.preorderTreeWalk(tree.root);

        // then
        String[] actualOutput = splitOutContent();
        String[] expectedOutput = new String[]{"10", "4", "1", "14", "11", "19", "20"};
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
    public void shouldPrintOutNonemptyTreeInPostorder() {
        // given
        BinaryTree<Integer> tree = getExemplaryBinaryTree();

        // when
        Chapter12.postorderTreeWalk(tree.root);

        // then
        String[] actualOutput = splitOutContent();
        String[] expectedOutput = new String[]{"1", "4", "11", "20", "19", "14", "10"};
        assertArrayEquals(expectedOutput, actualOutput);
    }

    @Test
    public void shouldFindNodeInTree() {
        // given
        BinaryTree<Integer> tree = getExemplaryBinaryTree();
        int key = 11;

        // when
        BinaryTree.Node<Integer> actualFoundNode = Chapter12.treeSearch(tree.root, key);

        // then
        assertNotNull(actualFoundNode);
        assertEquals(Integer.valueOf(key), actualFoundNode.key);
        while (actualFoundNode != tree.root) {
            actualFoundNode = actualFoundNode.p;
        }
    }

    @Test
    public void shouldNotFindNonexistentNodeInTree() {
        // given
        BinaryTree<Integer> tree = getExemplaryBinaryTree();
        int key = 17;

        // when
        BinaryTree.Node<Integer> actualFoundNode = Chapter12.treeSearch(tree.root, key);

        // then
        assertNull(actualFoundNode);
    }

    @Test
    public void shouldFindNodeInTreeUsingIterativeTreeSearch() {
        // given
        BinaryTree<Integer> tree = getExemplaryBinaryTree();
        int key = 11;

        // when
        BinaryTree.Node<Integer> actualFoundNode = Chapter12.iterativeTreeSearch(tree.root, key);

        // then
        assertNotNull(actualFoundNode);
        assertEquals(Integer.valueOf(key), actualFoundNode.key);
        while (actualFoundNode != tree.root) {
            actualFoundNode = actualFoundNode.p;
        }
    }

    @Test
    public void shouldNotFindNonexistentNodeInTreeUsingIterativeTreeSearch() {
        // given
        BinaryTree<Integer> tree = getExemplaryBinaryTree();
        int key = 17;

        // when
        BinaryTree.Node<Integer> actualFoundNode = Chapter12.iterativeTreeSearch(tree.root, key);

        // then
        assertNull(actualFoundNode);
    }

    @Test
    public void shouldFindTreeMinimum() {
        // given
        BinaryTree<Integer> tree = getExemplaryBinaryTree();

        // when
        BinaryTree.Node<Integer> actualMinimum = Chapter12.treeMinimum(tree.root);

        // then
        assertNotNull(actualMinimum);
        assertEquals(Integer.valueOf(1), actualMinimum.key);
        while (actualMinimum != tree.root) {
            actualMinimum = actualMinimum.p;
        }
    }

    @Test
    public void shouldFindTreeMaximum() {
        // given
        BinaryTree<Integer> tree = getExemplaryBinaryTree();

        // when
        BinaryTree.Node<Integer> actualMaximum = Chapter12.treeMaximum(tree.root);

        // then
        assertNotNull(actualMaximum);
        assertEquals(Integer.valueOf(20), actualMaximum.key);
        while (actualMaximum != tree.root) {
            actualMaximum = actualMaximum.p;
        }
    }

    @Test
    public void shouldFindSuccessorInTree() {
        // given
        BinaryTree<Integer> tree = getExemplaryBinaryTree();

        // when
        BinaryTree.Node<Integer> actualSuccessor = Chapter12.treeSuccessor(tree.root);

        // then
        assertNotNull(actualSuccessor);
        assertEquals(Integer.valueOf(11), actualSuccessor.key);
        while (actualSuccessor != tree.root) {
            actualSuccessor = actualSuccessor.p;
        }
    }

    @Test
    public void shouldNotFindNonexistingSuccessorInTree() {
        // given
        BinaryTree<Integer> tree = getExemplaryBinaryTree();
        BinaryTree.Node<Integer> node = tree.root.right.right.right; // a node with maximum value in the tree

        // when
        BinaryTree.Node<Integer> actualSuccessor = Chapter12.treeSuccessor(node);

        // then
        assertNull(actualSuccessor);
    }

    @Test
    public void shouldFindTreeMinimumUsingRecursiveTreeMinimum() {
        // given
        BinaryTree<Integer> tree = getExemplaryBinaryTree();

        // when
        BinaryTree.Node<Integer> actualMinimum = Chapter12.recursiveTreeMinimum(tree.root);

        // then
        assertNotNull(actualMinimum);
        assertEquals(Integer.valueOf(1), actualMinimum.key);
        while (actualMinimum != tree.root) {
            actualMinimum = actualMinimum.p;
        }
    }

    @Test
    public void shouldFindTreeMaximumUsingRecursiveTreeMaximum() {
        // given
        BinaryTree<Integer> tree = getExemplaryBinaryTree();

        // when
        BinaryTree.Node<Integer> actualMaximum = Chapter12.recursiveTreeMaximum(tree.root);

        // then
        assertNotNull(actualMaximum);
        assertEquals(Integer.valueOf(20), actualMaximum.key);
        while (actualMaximum != tree.root) {
            actualMaximum = actualMaximum.p;
        }
    }

    @Test
    public void shouldFindPredecessorInTree() {
        // given
        BinaryTree<Integer> tree = getExemplaryBinaryTree();
        BinaryTree.Node<Integer> node = tree.root.right.left; // node of key = 11

        // when
        BinaryTree.Node<Integer> actualPredecessor = Chapter12.treePredecessor(node);

        // then
        assertNotNull(actualPredecessor);
        assertEquals(Integer.valueOf(10), actualPredecessor.key);
        while (actualPredecessor != tree.root) {
            actualPredecessor = actualPredecessor.p;
        }
    }

    @Test
    public void shouldFindPredecessorInTreeForNodeWithLeftChild() {
        // given
        BinaryTree<Integer> tree = getExemplaryBinaryTree();

        // when
        BinaryTree.Node<Integer> actualPredecessor = Chapter12.treePredecessor(tree.root);

        // then
        assertNotNull(actualPredecessor);
        assertEquals(Integer.valueOf(4), actualPredecessor.key);
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
    public void shouldPrintOutNonemptyTreeUsingInorderTreeWalk_() {
        // given
        BinaryTree<Integer> tree = getExemplaryBinaryTree();

        // when
        Chapter12.inorderTreeWalk_(tree);

        // then
        String[] actualOutput = splitOutContent();
        String[] expectedOutput = new String[]{"1", "4", "10", "11", "14", "19", "20"};
        assertArrayEquals(expectedOutput, actualOutput);
    }

    @Test
    public void shouldInsertNodeToEmptyTree() {
        // given
        BinaryTree<Integer> tree = new BinaryTree<>();
        BinaryTree.Node<Integer> nodeToInsert = new BinaryTree.Node<>(12);

        // when
        Chapter12.treeInsert(tree, nodeToInsert);

        // then
        assertEquals(Integer.valueOf(12), nodeToInsert.key);
        assertNull(nodeToInsert.left);
        assertNull(nodeToInsert.right);
        assertNull(nodeToInsert.p);
        assertEquals(tree.root, nodeToInsert);
    }

    @Test
    public void shouldInsertNodeToNonemptyTree() {
        // given
        BinaryTree<Integer> tree = getExemplaryBinaryTree();
        BinaryTree.Node<Integer> nodeToInsert = new BinaryTree.Node<>(12);

        // when
        Chapter12.treeInsert(tree, nodeToInsert);

        // then
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
        // given
        BinaryTree<Integer> tree = getExemplaryBinaryTree();
        BinaryTree.Node<Integer> nodeToInsert = new BinaryTree.Node<>(18);

        // when
        Chapter12.treeInsert(tree, nodeToInsert);

        // then
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
        // given
        BinaryTree<Integer> tree = getExemplaryBinaryTree();
        int exampleTreeSize = tree.getSize();

        // when
        Chapter12.treeDelete(tree, tree.root.right.left); // a leaf

        // then
        assertNull(tree.root.right.left);
        assertEquals(exampleTreeSize - 1, tree.getSize());
    }

    @Test
    public void shouldDeleteNodeWithOneChildFromTree() {
        // given
        BinaryTree<Integer> tree = getExemplaryBinaryTree();
        int exampleTreeSize = tree.getSize();

        // when
        Chapter12.treeDelete(tree, tree.root.left); // a node with one child

        // then
        assertNotNull(tree.root.left);
        assertEquals(Integer.valueOf(1), tree.root.left.key);
        assertEquals(exampleTreeSize - 1, tree.getSize());
    }

    @Test
    public void shouldDeleteNodeWithTwoChildrenFromTree() {
        // given
        BinaryTree<Integer> tree = getExemplaryBinaryTree();
        int exampleTreeSize = tree.getSize();

        // when
        Chapter12.treeDelete(tree, tree.root.right); // a node with two children (successor's key = 19)

        // then
        assertNotNull(tree.root.right);
        assertEquals(Integer.valueOf(19), tree.root.right.key);
        assertEquals(exampleTreeSize - 1, tree.getSize());
    }

    @Test
    public void shouldDeleteRootFromTree() {
        // given
        BinaryTree<Integer> tree = new BinaryTree<>();
        tree.root = new BinaryTree.Node<>(10);

        // when
        Chapter12.treeDelete(tree, tree.root);

        // then
        assertNull(tree.root);
    }

    @Test
    public void shouldInsertNodeToEmptyTreeUsingRecursiveTreeInsert() {
        // given
        BinaryTree<Integer> tree = new BinaryTree<>();
        BinaryTree.Node<Integer> nodeToInsert = new BinaryTree.Node<>(12);

        // when
        Chapter12.recursiveTreeInsert(tree, tree.root, nodeToInsert);

        // then
        assertEquals(Integer.valueOf(12), nodeToInsert.key);
        assertNull(nodeToInsert.left);
        assertNull(nodeToInsert.right);
        assertNull(nodeToInsert.p);
        assertEquals(tree.root, nodeToInsert);
    }

    @Test
    public void shouldInsertNodeToNonemptyTreeUsingRecursiveTreeInsert() {
        // given
        BinaryTree<Integer> tree = getExemplaryBinaryTree();
        BinaryTree.Node<Integer> nodeToInsert = new BinaryTree.Node<>(12);

        // when
        Chapter12.recursiveTreeInsert(tree, tree.root, nodeToInsert);

        // then
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
        // given
        BinaryTree<Integer> tree = getExemplaryBinaryTree();
        BinaryTree.Node<Integer> nodeToInsert = new BinaryTree.Node<>(18);

        // when
        Chapter12.recursiveTreeInsert(tree, tree.root, nodeToInsert);

        // then
        assertEquals(Integer.valueOf(18), nodeToInsert.key);
        assertNull(nodeToInsert.left);
        assertNull(nodeToInsert.right);
        assertEquals(nodeToInsert, nodeToInsert.p.left); // for the particular tree the new node will be left son of its parent
        assertEquals(Integer.valueOf(19), nodeToInsert.p.key); // and its parent will be the node of key = 19
        while (nodeToInsert != tree.root) {
            nodeToInsert = nodeToInsert.p;
        }
    }

}
