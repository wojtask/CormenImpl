package pl.kwojtas.cormenimpl;

import pl.kwojtas.cormenimpl.datastructure.RedBlackTree;

import static pl.kwojtas.cormenimpl.Fundamental.less;

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
     * @param <E> the type of keys in {@code T}
     */
    static <E> void leftRotate(RedBlackTree<E> T, RedBlackTree.Node<E> x) {
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

    /**
     * Performs a right rotation on a red-black tree.
     * <p><span style="font-variant:small-caps;">Right-Rotate</span> from solution to exercise 13.2-1.</p>
     *
     * @param T   the red-black tree
     * @param x   the root of the subtree in {@code T} to rotate
     * @param <E> the type of keys in {@code T}
     */
    static <E> void rightRotate(RedBlackTree<E> T, RedBlackTree.Node<E> x) {
        RedBlackTree.Node<E> y = x.left;
        x.left = y.right;
        y.right.p = x;
        y.p = x.p;
        if (x.p == T.nil) {
            T.root = y;
        } else if (x == x.p.left) {
            x.p.left = y;
        } else {
            x.p.right = y;
        }
        y.right = x;
        x.p = y;
    }

    /**
     * Inserts a node into a red-black tree.
     * <p><span style="font-variant:small-caps;">RB-Insert</span> from subchapter 13.3.</p>
     *
     * @param T   the red-black tree
     * @param z   the node to insert
     * @param <E> the type of keys in {@code T}
     */
    public static <E extends Comparable<? super E>> void rbInsert(RedBlackTree<E> T, RedBlackTree.Node<E> z) {
        RedBlackTree.Node<E> y = T.nil;
        RedBlackTree.Node<E> x = T.root;
        while (x != T.nil) {
            y = x;
            if (less(z.key, x.key)) {
                x = x.left;
            } else {
                x = x.right;
            }
        }
        z.p = y;
        if (y == T.nil) {
            T.root = z;
        } else {
            if (less(z.key, y.key)) {
                y.left = z;
            } else {
                y.right = z;
            }
        }
        z.left = T.nil;
        z.right = T.nil;
        z.color = RedBlackTree.Color.RED;
        rbInsertFixup(T, z);
    }

    /**
     * Restores the red-black properties after inserting a node into a red-black tree by
     * <span style="font-variant:small-caps;">RB-Insert</span>.
     * <p><span style="font-variant:small-caps;">RB-Insert-Fixup</span> from subchapter 13.3.</p>
     *
     * @param T   the red-black tree
     * @param z   the inserted node
     * @param <E> the type of keys in {@code T}
     */
    static <E extends Comparable<? super E>> void rbInsertFixup(RedBlackTree<E> T, RedBlackTree.Node<E> z) {
        while (z.p.color == RedBlackTree.Color.RED) {
            if (z.p == z.p.p.left) {
                RedBlackTree.Node<E> y = z.p.p.right;
                if (y.color == RedBlackTree.Color.RED) {
                    z.p.color = RedBlackTree.Color.BLACK;
                    y.color = RedBlackTree.Color.BLACK;
                    z.p.p.color = RedBlackTree.Color.RED;
                    z = z.p.p;
                } else {
                    if (z == z.p.right) {
                        z = z.p;
                        leftRotate(T, z);
                    }
                    z.p.color = RedBlackTree.Color.BLACK;
                    z.p.p.color = RedBlackTree.Color.RED;
                    rightRotate(T, z.p.p);
                }
            } else {
                RedBlackTree.Node<E> y = z.p.p.left;
                if (y.color == RedBlackTree.Color.RED) {
                    z.p.color = RedBlackTree.Color.BLACK;
                    y.color = RedBlackTree.Color.BLACK;
                    z.p.p.color = RedBlackTree.Color.RED;
                    z = z.p.p;
                } else {
                    if (z == z.p.left) {
                        z = z.p;
                        rightRotate(T, z);
                    }
                    z.p.color = RedBlackTree.Color.BLACK;
                    z.p.p.color = RedBlackTree.Color.RED;
                    leftRotate(T, z.p.p);
                }
            }
        }
        T.root.color = RedBlackTree.Color.BLACK;
    }

}
