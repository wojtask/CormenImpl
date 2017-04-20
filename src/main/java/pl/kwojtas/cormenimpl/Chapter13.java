package pl.kwojtas.cormenimpl;

import pl.kwojtas.cormenimpl.datastructure.RedBlackTree;
import pl.kwojtas.cormenimpl.datastructure.RedBlackTree.Node;

import static pl.kwojtas.cormenimpl.Fundamental.less;
import static pl.kwojtas.cormenimpl.datastructure.RedBlackTree.Color.BLACK;
import static pl.kwojtas.cormenimpl.datastructure.RedBlackTree.Color.RED;

/**
 * Implements algorithms from Chapter 13.
 */
public final class Chapter13 {

    private Chapter13() {
    }

    /**
     * Performs a left rotation on a red-black tree.
     * <p><span style="font-variant:small-caps;">RB-Left-Rotate</span> from subchapter 13.2
     * (we use <span style="font-variant:small-caps;">Left-Rotate</span> as a name of the left rotation operation
     * on a binary search tree.)</p>
     *
     * @param T   the red-black tree
     * @param x   the root of the subtree in {@code T} to rotate
     * @param <E> the type of keys in {@code T}
     */
    static <E> void rbLeftRotate(RedBlackTree<E> T, Node<E> x) {
        Node<E> y = x.right;
        x.right = y.left;
        if (y.left.p != T.nil) {
            y.left.p = x;
        }
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
     * <p><span style="font-variant:small-caps;">RB-Right-Rotate</span> from solution to exercise 13.2-1.</p>
     *
     * @param T   the red-black tree
     * @param x   the root of the subtree in {@code T} to rotate
     * @param <E> the type of keys in {@code T}
     */
    static <E> void rbRightRotate(RedBlackTree<E> T, Node<E> x) {
        Node<E> y = x.left;
        x.left = y.right;
        if (y.right.p != T.nil) {
            y.right.p = x;
        }
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
    public static <E extends Comparable<? super E>> void rbInsert(RedBlackTree<E> T, Node<E> z) {
        Node<E> y = T.nil;
        Node<E> x = T.root;
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
        z.color = RED;
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
    static <E extends Comparable<? super E>> void rbInsertFixup(RedBlackTree<E> T, Node<E> z) {
        while (z.p.color == RED) {
            if (z.p == z.p.p.left) {
                Node<E> y = z.p.p.right;
                if (y.color == RED) {
                    z.p.color = BLACK;
                    y.color = BLACK;
                    z.p.p.color = RED;
                    z = z.p.p;
                } else {
                    if (z == z.p.right) {
                        z = z.p;
                        rbLeftRotate(T, z);
                    }
                    z.p.color = BLACK;
                    z.p.p.color = RED;
                    rbRightRotate(T, z.p.p);
                }
            } else {
                Node<E> y = z.p.p.left;
                if (y.color == RED) {
                    z.p.color = BLACK;
                    y.color = BLACK;
                    z.p.p.color = RED;
                    z = z.p.p;
                } else {
                    if (z == z.p.left) {
                        z = z.p;
                        rbRightRotate(T, z);
                    }
                    z.p.color = BLACK;
                    z.p.p.color = RED;
                    rbLeftRotate(T, z.p.p);
                }
            }
        }
        T.root.color = BLACK;
    }

    /**
     * Deletes a node from a red-black tree.
     * <p><span style="font-variant:small-caps;">RB-Delete</span> from subchapter 13.4.</p>
     *
     * @param T   the red-black tree
     * @param z   the node to delete
     * @param <E> the type of keys in {@code T}
     * @return the node deleted from {@code T}
     */
    public static <E> Node<E> rbDelete(RedBlackTree<E> T, Node<E> z) {
        Node<E> y;
        if (z.left == T.nil || z.right == T.nil) {
            y = z;
        } else {
            y = rbTreeSuccessor(T, z);
        }
        Node<E> x;
        if (y.left != T.nil) {
            x = y.left;
        } else {
            x = y.right;
        }
        x.p = y.p;
        if (y.p == T.nil) {
            T.root = x;
        } else {
            if (y == y.p.left) {
                y.p.left = x;
            } else {
                y.p.right = x;
            }
        }
        if (y != z) {
            z.key = y.key;
        }
        if (y.color == BLACK) {
            rbDeleteFixup(T, x);
        }
        return y;
    }

    /**
     * Restores the red-black properties after deleting a node from a red-black tree by
     * <span style="font-variant:small-caps;">RB-Delete</span>.
     * <p><span style="font-variant:small-caps;">RB-Delete-Fixup</span> from subchapter 13.4.</p>
     *
     * @param T   the red-black tree
     * @param x   the node that may violate the red-black properties
     * @param <E> the type of keys in {@code T}
     */
    static <E> void rbDeleteFixup(RedBlackTree<E> T, Node<E> x) {
        while (x != T.root && x.color == BLACK) {
            Node<E> w;
            if (x == x.p.left) {
                w = x.p.right;
                if (w.color == RED) {
                    w.color = BLACK;
                    x.p.color = RED;
                    rbLeftRotate(T, x.p);
                    w = x.p.right;
                }
                if (w.left.color == BLACK && w.right.color == BLACK) {
                    w.color = RED;
                    x = x.p;
                } else {
                    if (w.right.color == BLACK) {
                        w.left.color = BLACK;
                        w.color = RED;
                        rbRightRotate(T, w);
                        w = x.p.right;
                    }
                    w.color = x.p.color;
                    x.p.color = BLACK;
                    w.right.color = BLACK;
                    rbLeftRotate(T, x.p);
                    x = T.root;
                }
            } else {
                w = x.p.left;
                if (w.color == RED) {
                    w.color = BLACK;
                    x.p.color = RED;
                    rbRightRotate(T, x.p);
                    w = x.p.left;
                }
                if (w.left.color == BLACK && w.right.color == BLACK) {
                    w.color = RED;
                    x = x.p;
                } else {
                    if (w.left.color == BLACK) {
                        w.right.color = BLACK;
                        w.color = RED;
                        rbLeftRotate(T, w);
                        w = x.p.left;
                    }
                    w.color = x.p.color;
                    x.p.color = BLACK;
                    w.left.color = BLACK;
                    rbRightRotate(T, x.p);
                    x = T.root;
                }
            }
        }
        x.color = BLACK;
    }

    /**
     * Returns the node with the smallest key in a non-empty red-black tree.
     * <p>Chapter 13.</p>
     *
     * @param T   the red-black tree
     * @param x   the root of {@code T}
     * @param <E> the type of keys in {@code T}
     * @return the node with the smallest key in {@code T}
     */
    public static <E> Node<E> rbTreeMinimum(RedBlackTree<E> T, Node<E> x) {
        while (x.left != T.nil) {
            x = x.left;
        }
        return x;
    }

    /**
     * Returns the node's successor in a non-empty red-black tree.
     * <p>Chapter 13.</p>
     *
     * @param T   the red-black tree
     * @param x   the node of the {@code T}
     * @param <E> the type of keys in {@code T}
     * @return the successor of {@code x} in {@code T}, or {@code null} if {@code x} has the largest key in {@code T}
     */
    public static <E> Node<E> rbTreeSuccessor(RedBlackTree<E> T, Node<E> x) {
        if (x.right != T.nil) {
            return rbTreeMinimum(T, x.right);
        }
        Node<E> y = x.p;
        while (y != T.nil && x == y.right) {
            x = y;
            y = y.p;
        }
        return y;
    }

}
