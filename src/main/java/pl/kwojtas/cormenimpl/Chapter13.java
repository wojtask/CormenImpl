package pl.kwojtas.cormenimpl;

import pl.kwojtas.cormenimpl.util.RedBlackTree;

/**
 * Implements algorithms from Chapter 13.
 */
public final class Chapter13 {

    private Chapter13() {
    }

    /**
     * Performs a left rotation on a red-black tree.
     * <p><span style="font-variant:small-caps;">Left-Rotate</span> from subchapter 13.2.</p>
     *
     * @param T   the red-black tree
     * @param x   the root of the subtree in {@code T} to rotate
     * @param <E> the type of keys in the tree
     */
    public static <E> void leftRotate(RedBlackTree<E> T, RedBlackTree.Node<E> x) {
        RedBlackTree.Node<E> y = x.right;
        x.right = y.left;
        y.left.p = x;
        y.p = x.p;
        if (x.p == T.nil) {
            T.root = y;
        } else if (x == x.p.left) {
            x.p.left = y;
        } else {
            x.p.right = y;
        }
        y.left = x;
        x.p = y;
    }

}
