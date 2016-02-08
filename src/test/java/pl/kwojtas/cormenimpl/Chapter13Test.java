package pl.kwojtas.cormenimpl;

import org.junit.Test;
import pl.kwojtas.cormenimpl.util.RedBlackTree;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static pl.kwojtas.cormenimpl.util.RedBlackTree.Color.BLACK;
import static pl.kwojtas.cormenimpl.util.RedBlackTree.Color.RED;

public class Chapter13Test {

    private RedBlackTree<Integer> getExemplaryRedBlackTree() {
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        RedBlackTree.Node<Integer> x1 = new RedBlackTree.Node<>(7, BLACK, tree);
        RedBlackTree.Node<Integer> x2 = new RedBlackTree.Node<>(2, RED, tree);
        RedBlackTree.Node<Integer> x3 = new RedBlackTree.Node<>(11, RED, tree);
        RedBlackTree.Node<Integer> x4 = new RedBlackTree.Node<>(1, BLACK, tree);
        RedBlackTree.Node<Integer> x5 = new RedBlackTree.Node<>(5, BLACK, tree);
        RedBlackTree.Node<Integer> x6 = new RedBlackTree.Node<>(8, BLACK, tree);
        RedBlackTree.Node<Integer> x7 = new RedBlackTree.Node<>(14, BLACK, tree);
        RedBlackTree.Node<Integer> x8 = new RedBlackTree.Node<>(4, RED, tree);
        RedBlackTree.Node<Integer> x9 = new RedBlackTree.Node<>(15, RED, tree);
        tree.root = x1;
        x1.left = x2;  //              7 B
        x2.p = x1;     //            /   \
        x1.right = x3; //           /     \
        x3.p = x1;     //        R 2      11 R
        x2.left = x4;  //         / \     / \
        x4.p = x2;     //        /   \   /   \
        x2.right = x5; //     B 1   B 5 8 B  14 B
        x5.p = x2;     //            /         \
        x3.left = x6;  //           /           \
        x6.p = x3;     //          4 R          15 R
        x3.right = x7;
        x7.p = x3;
        x5.left = x8;
        x8.p = x5;
        x7.right = x9;
        x9.p = x7;
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
    public void shouldLeftRotateTree() {
        RedBlackTree<Integer> tree = getExemplaryRedBlackTree();
        int xKey = 11;
        int yKey = 14;

        Chapter13.leftRotate(tree, tree.root.right);

        assertEquals(Integer.valueOf(yKey), tree.root.right.key);
        assertEquals(Integer.valueOf(xKey), tree.root.right.left.key);
    }

    @Test
    public void shouldRightRotateTree() {
        RedBlackTree<Integer> tree = getExemplaryRedBlackTree();
        int xKey = 11;
        int yKey = 8;

        Chapter13.rightRotate(tree, tree.root.right);

        assertEquals(Integer.valueOf(yKey), tree.root.right.key);
        assertEquals(Integer.valueOf(xKey), tree.root.right.right.key);
    }

}
