package pl.kwojtas.cormenimpl;

import pl.kwojtas.cormenimpl.datastructure.AVLTree;
import pl.kwojtas.cormenimpl.datastructure.ParentlessRedBlackTree;
import pl.kwojtas.cormenimpl.datastructure.PersistentBinarySearchTree;
import pl.kwojtas.cormenimpl.datastructure.RedBlackTree;
import pl.kwojtas.cormenimpl.datastructure.RedBlackTree.Node;
import pl.kwojtas.cormenimpl.datastructure.Stack;

import static pl.kwojtas.cormenimpl.Fundamental.less;
import static pl.kwojtas.cormenimpl.Fundamental.max;
import static pl.kwojtas.cormenimpl.datastructure.RedBlackTree.Color.BLACK;
import static pl.kwojtas.cormenimpl.datastructure.RedBlackTree.Color.RED;

/**
 * Implements algorithms from Chapter 13.
 */
public final class Chapter13 {

    private Chapter13() {
    }

    /**
     * Performs a left rotation in a binary search tree with nil sentinel.
     * <p><span style="font-variant:small-caps;">Left-Rotate</span> from subchapter 13.2.</p>
     *
     * @param T   the binary search tree
     * @param x   the root of the subtree in {@code T} to rotate
     * @param <E> the type of keys in {@code T}
     */
    static <E> void leftRotate(RedBlackTree<E> T, Node<E> x) {
        Node<E> y = x.right;
        x.right = y.left;
        if (y.left != T.nil) {
            y.left.p = x;
        }
        y.p = x.p;
        if (x.p == T.nil) {
            T.root = y;
        } else {
            if (x == x.p.left) {
                x.p.left = y;
            } else {
                x.p.right = y;
            }
        }
        y.left = x;
        x.p = y;
    }

    /**
     * Performs a right rotation in a a binary search tree with nil sentinel.
     * <p><span style="font-variant:small-caps;">Right-Rotate</span> from solution to exercise 13.2-1.</p>
     *
     * @param T   the binary search tree
     * @param x   the root of the subtree in {@code T} to rotate
     * @param <E> the type of keys in {@code T}
     */
    static <E> void rightRotate(RedBlackTree<E> T, Node<E> x) {
        Node<E> y = x.left;
        x.left = y.right;
        if (y.right != T.nil) {
            y.right.p = x;
        }
        y.p = x.p;
        if (x.p == T.nil) {
            T.root = y;
        } else {
            if (x == x.p.right) {
                x.p.right = y;
            } else {
                x.p.left = y;
            }
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
     * @param T   the binary search tree with red-black properties violated
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
                        leftRotate(T, z);
                    }
                    z.p.color = BLACK;
                    z.p.p.color = RED;
                    rightRotate(T, z.p.p);
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
                        rightRotate(T, z);
                    }
                    z.p.color = BLACK;
                    z.p.p.color = RED;
                    leftRotate(T, z.p.p);
                }
            }
        }
        T.root.color = BLACK;
    }

    /**
     * Inserts a node into a red-black tree with no parent pointers in nodes.
     * <p>Solution to exercise 13.3-6.</p>
     *
     * @param T   the red-black tree
     * @param z   the node to insert
     * @param <E> the type of keys in {@code T}
     */
    public static <E extends Comparable<? super E>> void rbParentlessInsert(
            ParentlessRedBlackTree<E> T, ParentlessRedBlackTree.Node<E> z) {
        ParentlessRedBlackTree.Node<E> y = T.nil;
        ParentlessRedBlackTree.Node<E> x = T.root;
        int pathLength = getPathLengthFromRootToNewNode(T, z);
        Stack<ParentlessRedBlackTree.Node<E>> S = Stack.ofLength(pathLength + 1);
        Chapter10.push(S, T.nil);
        while (x != T.nil) {
            y = x;
            Chapter10.push(S, y);
            if (less(z.key, x.key)) {
                x = x.left;
            } else {
                x = x.right;
            }
        }
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
        rbParentlessInsertFixup(T, S, z);
    }

    private static <E extends Comparable<? super E>> int getPathLengthFromRootToNewNode(
            ParentlessRedBlackTree<E> T, ParentlessRedBlackTree.Node<E> z) {
        int pathLength = 0;
        ParentlessRedBlackTree.Node<E> x = T.root;
        while (x != T.nil) {
            pathLength++;
            if (less(z.key, x.key)) {
                x = x.left;
            } else {
                x = x.right;
            }
        }
        return pathLength;
    }

    /**
     * Restores the red-black properties after inserting a node into a red-black tree with no parent pointers in nodes.
     * <p><span style="font-variant:small-caps;">RB-Parentless-Insert-Fixup</span> from solution to exercise 13.3-6.</p>
     *
     * @param T   the binary search tree with red-black properties violated
     * @param z   the inserted node
     * @param <E> the type of keys in {@code T}
     */
    private static <E extends Comparable<? super E>> void rbParentlessInsertFixup(
            ParentlessRedBlackTree<E> T, Stack<ParentlessRedBlackTree.Node<E>> S, ParentlessRedBlackTree.Node<E> z) {
        ParentlessRedBlackTree.Node<E> p = Chapter10.pop(S);
        while (p.color == RED) {
            ParentlessRedBlackTree.Node<E> r = Chapter10.pop(S);
            if (p == r.left) {
                ParentlessRedBlackTree.Node<E> y = r.right;
                if (y.color == RED) {
                    y.color = p.color = BLACK;
                    r.color = RED;
                    z = r;
                    p = Chapter10.pop(S);
                } else {
                    if (z == p.right) {
                        ParentlessRedBlackTree.Node<E> tmp = z;
                        z = p;
                        p = tmp;
                        rbParentlessLeftRotate(T, z, r);
                    }
                    p.color = BLACK;
                    r.color = RED;
                    rbParentlessRightRotate(T, r, Chapter10.pop(S));
                }
            } else {
                ParentlessRedBlackTree.Node<E> y = r.left;
                if (y.color == RED) {
                    y.color = p.color = BLACK;
                    r.color = RED;
                    z = r;
                    p = Chapter10.pop(S);
                } else {
                    if (z == p.left) {
                        ParentlessRedBlackTree.Node<E> tmp = z;
                        z = p;
                        p = tmp;
                        rbParentlessRightRotate(T, z, r);
                    }
                    p.color = BLACK;
                    r.color = RED;
                    rbParentlessLeftRotate(T, r, Chapter10.pop(S));
                }
            }
        }
        T.root.color = BLACK;
    }

    /**
     * Performs a left rotation in a binary search tree with nil sentinel and with no parent pointers in nodes.
     * <p><span style="font-variant:small-caps;">RB-Parentless-Left-Rotate</span> from solution to exercise 13.3-6.</p>
     *
     * @param T   the binary search tree
     * @param x   the root of the subtree in {@code T} to rotate
     * @param p   the parent of {@code x} in {@code T}
     * @param <E> the type of keys in {@code T}
     */
    private static <E extends Comparable<? super E>> void rbParentlessLeftRotate(
            ParentlessRedBlackTree<E> T, ParentlessRedBlackTree.Node<E> x, ParentlessRedBlackTree.Node<E> p) {
        ParentlessRedBlackTree.Node<E> y = x.right;
        x.right = y.left;
        if (p == T.nil) {
            T.root = y;
        } else {
            if (x == p.left) {
                p.left = y;
            } else {
                p.right = y;
            }
        }
        y.left = x;
    }

    /**
     * Performs a right rotation in a binary search tree with nil sentinel and with no parent pointers in nodes.
     * <p><span style="font-variant:small-caps;">RB-Parentless-Right-Rotate</span> from solution to exercise 13.3-6.</p>
     *
     * @param T   the binary search tree
     * @param x   the root of the subtree in {@code T} to rotate
     * @param p   the parent of {@code x} in {@code T}
     * @param <E> the type of keys in {@code T}
     */
    private static <E extends Comparable<? super E>> void rbParentlessRightRotate(
            ParentlessRedBlackTree<E> T, ParentlessRedBlackTree.Node<E> x, ParentlessRedBlackTree.Node<E> p) {
        ParentlessRedBlackTree.Node<E> y = x.left;
        x.left = y.right;
        if (p == T.nil) {
            T.root = y;
        } else {
            if (x == p.right) {
                p.right = y;
            } else {
                p.left = y;
            }
        }
        y.right = x;
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
     * @param T   the binary search tree with red-black properties violated
     * @param x   the child of the deleted node that may violate the red-black properties
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
                    leftRotate(T, x.p);
                    w = x.p.right;
                }
                if (w.left.color == BLACK && w.right.color == BLACK) {
                    w.color = RED;
                    x = x.p;
                } else {
                    if (w.right.color == BLACK) {
                        w.left.color = BLACK;
                        w.color = RED;
                        rightRotate(T, w);
                        w = x.p.right;
                    }
                    w.color = x.p.color;
                    x.p.color = BLACK;
                    w.right.color = BLACK;
                    leftRotate(T, x.p);
                    x = T.root;
                }
            } else {
                w = x.p.left;
                if (w.color == RED) {
                    w.color = BLACK;
                    x.p.color = RED;
                    rightRotate(T, x.p);
                    w = x.p.left;
                }
                if (w.left.color == BLACK && w.right.color == BLACK) {
                    w.color = RED;
                    x = x.p;
                } else {
                    if (w.left.color == BLACK) {
                        w.right.color = BLACK;
                        w.color = RED;
                        leftRotate(T, w);
                        w = x.p.left;
                    }
                    w.color = x.p.color;
                    x.p.color = BLACK;
                    w.left.color = BLACK;
                    rightRotate(T, x.p);
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

    public static <E extends Comparable<? super E>> void persistentTreeInsert(PersistentBinarySearchTree<E> T, E k) {
        PersistentBinarySearchTree.Node<E> r = persistentTreeInsert(T.roots.tail.key, k);
        Chapter10.singlyLinkedListEnqueue(T.roots, r);
    }

    static <E extends Comparable<? super E>> PersistentBinarySearchTree.Node<E> persistentTreeInsert(PersistentBinarySearchTree.Node<E> x, E k) {
        PersistentBinarySearchTree.Node<E> z;
        if (x == null) {
            z = newNode(k);
        } else {
            z = copyNode(x);
            if (less(k, x.key)) {
                z.left = persistentTreeInsert(x.left, k);
            } else {
                z.right = persistentTreeInsert(x.right, k);
            }
        }
        return z;
    }

    static <E extends Comparable<? super E>> PersistentBinarySearchTree.Node<E> newNode(E k) {
        return new PersistentBinarySearchTree.Node<>(k);
    }

    static <E extends Comparable<? super E>> PersistentBinarySearchTree.Node<E> copyNode(PersistentBinarySearchTree.Node<E> x) {
        return new PersistentBinarySearchTree.Node<>(x.key, x.left, x.right);
    }

    /**
     * Returns the node's balance factor in a binary search tree with fields storing heights.
     * <p><span style="font-variant:small-caps;">Balance-Factor</span> from solution to problem 13-3(b).</p>
     *
     * @param x   the node of the tree
     * @param <E> the type of keys in the tree
     * @return the balance factor of {@code x}, defined as {@code x.left.h - x.right.h},
     * where balance factor of {@code null} is -1
     */
    static <E> int balanceFactor(AVLTree.Node<E> x) {
        int hl = -1;
        int hr = -1;
        if (x.left != null) {
            hl = x.left.h;
        }
        if (x.right != null) {
            hr = x.right.h;
        }
        return -hl + hr;
    }

    /**
     * Returns the node's actual height in an binary search tree with fields storing heights.
     * <p><span style="font-variant:small-caps;">Height</span> from solution to problem 13-3(b).</p>
     *
     * @param x   the node of the tree
     * @param <E> the type of keys in the tree
     * @return the actual height of {@code x}, based on heights of its children
     */
    static <E> int height(AVLTree.Node<E> x) {
        int hl = -1;
        int hr = -1;
        if (x.left != null) {
            hl = x.left.h;
        }
        if (x.right != null) {
            hr = x.right.h;
        }
        return max(hl, hr) + 1;
    }

    /**
     * Performs a left rotation in a binary search tree with fields storing heights.
     * <p><span style="font-variant:small-caps;">AVL-Left-Rotate</span> from solution to problem 13-3(b).</p>
     *
     * @param x   the root of the subtree in the tree to rotate
     * @param <E> the type of keys in the tree
     */
    static <E> void avlLeftRotate(AVLTree.Node<E> x) {
        AVLTree.Node<E> y = x.right;
        x.right = y.left;
        if (y.left != null) {
            y.left.p = x;
        }
        y.p = x.p;
        if (x.p != null) {
            if (x == x.p.left) {
                x.p.left = y;
            } else {
                x.p.right = y;
            }
        }
        y.left = x;
        x.p = y;
        x.h = height(x);
        y.h = height(y);
    }

    /**
     * Performs a right rotation in a binary search tree with fields storing heights.
     * <p><span style="font-variant:small-caps;">AVL-Right-Rotate</span> from solution to problem 13-3(b).</p>
     *
     * @param x   the root of the subtree in the tree to rotate
     * @param <E> the type of keys in the tree
     */
    static <E> void avlRightRotate(AVLTree.Node<E> x) {
        AVLTree.Node<E> y = x.left;
        x.left = y.right;
        if (y.right != null) {
            y.right.p = x;
        }
        y.p = x.p;
        if (x.p != null) {
            if (x == x.p.right) {
                x.p.right = y;
            } else {
                x.p.left = y;
            }
        }
        y.right = x;
        x.p = y;
        x.h = height(x);
        y.h = height(y);
    }

    /**
     * Restores the height balance for an unbalanced node in a binary search tree with fields storing heights.
     * Assumes that the balance factor of the unbalanced node is either -2 or 2, and the balance factor of all of its
     * descendants is either -1, 0 or 1.
     * <p><span style="font-variant:small-caps;">Balance</span> from solution to problem 13-3(b).</p>
     *
     * @param x   the unbalanced node in the tree
     * @param <E> the type of keys in the tree
     * @return the ancestor of {@code x} of the same depth in the tree as initially {@code x}
     */
    static <E> AVLTree.Node<E> balance(AVLTree.Node<E> x) {
        if (balanceFactor(x) == -2) {
            if (balanceFactor(x.left) == 1) {
                avlLeftRotate(x.left);
            }
            avlRightRotate(x);
            return x.p;
        } else if (balanceFactor(x) == 2) {
            if (balanceFactor(x.right) == -1) {
                avlRightRotate(x.right);
            }
            avlLeftRotate(x);
            return x.p;
        }
        return x;
    }

    /**
     * Inserts a node into an AVL tree.
     * <p><span style="font-variant:small-caps;">AVL-Insert</span> from solution to problem 13-3(c).</p>
     *
     * @param x   the root of the subtree to insert to
     * @param z   the node to insert
     * @param <E> the type of keys in the tree
     * @return {@code x} or {@code z} if {@code x = null}
     */
    static <E extends Comparable<? super E>> AVLTree.Node<E> avlInsert(AVLTree.Node<E> x, AVLTree.Node<E> z) {
        if (x == null) {
            return z;
        }
        if (less(z.key, x.key)) {
            x.left = avlInsert(x.left, z);
            x.left.p = x;
        } else {
            x.right = avlInsert(x.right, z);
            x.right.p = x;
        }
        x.h = height(x);
        return balance(x);
    }

    /**
     * Inserts a node into an AVL tree using <span style="font-variant:small-caps;">AVL-Insert</span>.
     * <p><span style="font-variant:small-caps;">AVL-Insert'</span> from solution to problem 13-3(c).</p>
     *
     * @param T   the AVL tree
     * @param z   the node to insert
     * @param <E> the type of keys in {@code T}
     */
    public static <E extends Comparable<? super E>> void avlInsert_(AVLTree<E> T, AVLTree.Node<E> z) {
        T.root = avlInsert(T.root, z);
    }

}
