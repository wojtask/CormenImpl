package pl.kwojtas.cormenimpl;

import pl.kwojtas.cormenimpl.datastructure.AVLTree;
import pl.kwojtas.cormenimpl.datastructure.ParentlessBinaryTree;
import pl.kwojtas.cormenimpl.datastructure.ParentlessRedBlackTree;
import pl.kwojtas.cormenimpl.datastructure.RedBlackTree;
import pl.kwojtas.cormenimpl.datastructure.Stack;
import pl.kwojtas.cormenimpl.datastructure.Treap;

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
    static <E> void leftRotate(RedBlackTree<E> T, RedBlackTree.Node<E> x) {
        RedBlackTree.Node<E> y = x.right;
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
    static <E> void rightRotate(RedBlackTree<E> T, RedBlackTree.Node<E> x) {
        RedBlackTree.Node<E> y = x.left;
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
    static <E extends Comparable<? super E>> void rbInsertFixup(RedBlackTree<E> T, RedBlackTree.Node<E> z) {
        while (z.p.color == RED) {
            if (z.p == z.p.p.left) {
                RedBlackTree.Node<E> y = z.p.p.right;
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
                RedBlackTree.Node<E> y = z.p.p.left;
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
        int pathLength = getPathLengthFromRoot(T, z);
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

    private static <E extends Comparable<? super E>> int getPathLengthFromRoot(
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
    public static <E> RedBlackTree.Node<E> rbDelete(RedBlackTree<E> T, RedBlackTree.Node<E> z) {
        RedBlackTree.Node<E> y;
        if (z.left == T.nil || z.right == T.nil) {
            y = z;
        } else {
            y = rbTreeSuccessor(T, z);
        }
        RedBlackTree.Node<E> x;
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
    static <E> void rbDeleteFixup(RedBlackTree<E> T, RedBlackTree.Node<E> x) {
        while (x != T.root && x.color == BLACK) {
            RedBlackTree.Node<E> w;
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
    public static <E> RedBlackTree.Node<E> rbTreeMinimum(RedBlackTree<E> T, RedBlackTree.Node<E> x) {
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
    public static <E> RedBlackTree.Node<E> rbTreeSuccessor(RedBlackTree<E> T, RedBlackTree.Node<E> x) {
        if (x.right != T.nil) {
            return rbTreeMinimum(T, x.right);
        }
        RedBlackTree.Node<E> y = x.p;
        while (y != T.nil && x == y.right) {
            x = y;
            y = y.p;
        }
        return y;
    }

    /**
     * Creates a new node for parentless binary search tree whose field {@code key} is {@code k},
     * and fields {@code left} and {@code right} are {@code null}.
     *
     * @param k   the key of the new node
     * @param <E> the type of the key
     * @return the newly created node
     */
    static <E extends Comparable<? super E>> ParentlessBinaryTree.Node<E> newNode(E k) {
        return new ParentlessBinaryTree.Node<>(k);
    }

    /**
     * Creates a new node for parentless binary search tree whose fields {@code key}, {@code left} and {@code right}
     * are set to the values of the same fields of node {@code x}.
     *
     * @param x   the node to copy
     * @param <E> the type of the key in {@code x}
     * @return the newly created node
     */
    static <E extends Comparable<? super E>> ParentlessBinaryTree.Node<E> copyNode(ParentlessBinaryTree.Node<E> x) {
        return new ParentlessBinaryTree.Node<>(x.key, x.left, x.right);
    }

    /**
     * Inserts a key into a persistent set represented by a parentless binary search tree.
     * <p><span style="font-variant:small-caps;">Persistent-Subtree-Insert</span> from solution to problem 13-1(b).</p>
     *
     * @param x   the root of the subtree to insert to
     * @param k   the key to insert
     * @param <E> the type of keys in {@code T}
     * @return the root of the new tree with inserted key
     */
    static <E extends Comparable<? super E>> ParentlessBinaryTree.Node<E> persistentSubtreeInsert(ParentlessBinaryTree.Node<E> x, E k) {
        ParentlessBinaryTree.Node<E> z;
        if (x == null) {
            z = newNode(k);
        } else {
            z = copyNode(x);
            if (less(k, x.key)) {
                z.left = persistentSubtreeInsert(x.left, k);
            } else {
                z.right = persistentSubtreeInsert(x.right, k);
            }
        }
        return z;
    }

    /**
     * Inserts a key into a persistent set represented by a parentless binary search tree.
     * <p><span style="font-variant:small-caps;">Persistent-Tree-Insert</span> from solution to problem 13-1(b).</p>
     *
     * @param T   the parentless binary search tree representing the persistent set
     * @param k   the key to insert
     * @param <E> the type of keys in {@code T}
     * @return the binary search tree with new key inserted
     */
    public static <E extends Comparable<? super E>> ParentlessBinaryTree<E> persistentTreeInsert(ParentlessBinaryTree<E> T, E k) {
        ParentlessBinaryTree<E> T_ = ParentlessBinaryTree.emptyTree();
        T_.root = persistentSubtreeInsert(T.root, k);
        return T_;
    }

    /**
     * Inserts a node into a persistent set represented by a parentless red-black tree.
     * <p>Solution to problem 13-1(e).</p>
     *
     * @param T   the parentless red-black tree representing the persistent set
     * @param z   the node to insert
     * @param <E> the type of keys in {@code T}
     * @return the red-black tree with new node inserted
     */
    public static <E extends Comparable<? super E>> ParentlessRedBlackTree<E> persistentRbInsert(
            ParentlessRedBlackTree<E> T, ParentlessRedBlackTree.Node<E> z) {
        ParentlessRedBlackTree.Node<E> y = T.nil;
        ParentlessRedBlackTree.Node<E> x = T.root;
        ParentlessRedBlackTree<E> T_ = ParentlessRedBlackTree.emptyTree();
        T_.nil = T.nil;
        ParentlessRedBlackTree.Node<E> y_ = T_.nil;
        ParentlessRedBlackTree.Node<E> x_;
        int pathLength = getPathLengthFromRoot(T, z);
        Stack<ParentlessRedBlackTree.Node<E>> S = Stack.ofLength(pathLength + 1);
        Chapter10.push(S, T.nil);
        while (x != T.nil) {
            y = x;
            x_ = new ParentlessRedBlackTree.Node<>(x.key, x.color, x.left, x.right);
            if (y_ != T.nil) {
                if (x == y_.left) {
                    y_.left = x_;
                } else {
                    y_.right = x_;
                }
            }
            y_ = x_;
            Chapter10.push(S, y_);
            if (less(z.key, x.key)) {
                x = x.left;
            } else {
                x = x.right;
            }
        }
        if (y == T.nil) {
            T_.root = z;
        } else {
            if (less(z.key, y.key)) {
                y_.left = z;
            } else {
                y_.right = z;
            }
        }
        z.left = z.right = T.nil;
        z.color = RED;
        persistentRbInsertFixup(T_, S, z);
        return T_;
    }

    /**
     * Restores the red-black properties after inserting a node into a persistent set represented by a parentless red-black tree.
     * <p>Solution to problem 13-1(e).</p>
     *
     * @param T   the parentless red-black tree with red-black properties violated representing the persistent set
     * @param z   the inserted node
     * @param <E> the type of keys in {@code T}
     */
    static <E extends Comparable<? super E>> void persistentRbInsertFixup(
            ParentlessRedBlackTree<E> T, Stack<ParentlessRedBlackTree.Node<E>> S, ParentlessRedBlackTree.Node<E> z) {
        ParentlessRedBlackTree.Node<E> p = Chapter10.pop(S);
        while (p.color == RED) {
            ParentlessRedBlackTree.Node<E> r = Chapter10.pop(S);
            if (p == r.left) {
                ParentlessRedBlackTree.Node<E> y = r.right;
                if (y.color == RED) {
                    r.right = new ParentlessRedBlackTree.Node<>(y.key, BLACK, y.left, y.right);
                    p.color = BLACK;
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
                    r.left = new ParentlessRedBlackTree.Node<>(y.key, BLACK, y.left, y.right);
                    p.color = BLACK;
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
     * <p><span style="font-variant:small-caps;">AVL-Insert-Wrapper</span> from solution to problem 13-3(c).</p>
     *
     * @param T   the AVL tree
     * @param z   the node to insert
     * @param <E> the type of keys in {@code T}
     */
    public static <E extends Comparable<? super E>> void avlInsertWrapper(AVLTree<E> T, AVLTree.Node<E> z) {
        T.root = avlInsert(T.root, z);
    }

    /**
     * Inserts a node into a treap.
     * <p><span style="font-variant:small-caps;">Treap-Insert</span> from solution to problem 13-4(c).</p>
     *
     * @param T   the treap
     * @param x   the node to insert
     * @param <E> the type of keys in {@code T}
     */
    public static <E extends Comparable<? super E>> void treapInsert(Treap<E> T, Treap.Node<E> x) {
        treeInsert(T, x);
        while (x != T.root && x.priority < x.p.priority) {
            if (x == x.p.left) {
                rightRotate(T, x.p);
            } else {
                leftRotate(T, x.p);
            }
        }
    }

    private static <E extends Comparable<? super E>> void treeInsert(Treap<E> T, Treap.Node<E> z) {
        Treap.Node<E> y = null;
        Treap.Node<E> x = T.root;
        while (x != null) {
            y = x;
            if (less(z.key, x.key)) {
                x = x.left;
            } else {
                x = x.right;
            }
        }
        z.p = y;
        if (y == null) {
            T.root = z;
        } else {
            if (less(z.key, y.key)) {
                y.left = z;
            } else {
                y.right = z;
            }
        }
    }

    private static <E extends Comparable<? super E>> void leftRotate(Treap<E> T, Treap.Node<E> x) {
        Treap.Node<E> y = x.right;
        x.right = y.left;
        if (y.left != null) {
            y.left.p = x;
        }
        y.p = x.p;
        if (x.p == null) {
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

    private static <E extends Comparable<? super E>> void rightRotate(Treap<E> T, Treap.Node<E> x) {
        Treap.Node<E> y = x.left;
        x.left = y.right;
        if (y.right != null) {
            y.right.p = x;
        }
        y.p = x.p;
        if (x.p == null) {
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

}
