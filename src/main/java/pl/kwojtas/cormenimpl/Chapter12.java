package pl.kwojtas.cormenimpl;

import pl.kwojtas.cormenimpl.util.Array;
import pl.kwojtas.cormenimpl.util.BinaryTree;
import pl.kwojtas.cormenimpl.util.RadixTree;

import static pl.kwojtas.cormenimpl.util.Util.leq;
import static pl.kwojtas.cormenimpl.util.Util.less;
import static pl.kwojtas.cormenimpl.util.Util.random;

/**
 * Implements algorithms from Chapter 12.
 */
public final class Chapter12 {

    private Chapter12() {
    }

    /**
     * Prints out keys of a binary search tree performing inorder tree walk.
     * <p><span style="font-variant:small-caps;">Inorder-Tree-Walk</span> from subchapter 12.1.</p>
     *
     * @param x   the root of the tree to print out
     * @param <E> the type of keys in the tree
     */
    public static <E> void inorderTreeWalk(BinaryTree.Node<E> x) {
        if (x != null) {
            inorderTreeWalk(x.left);
            System.out.println(x.key);
            inorderTreeWalk(x.right);
        }
    }

    /**
     * Prints out keys of a binary search tree performing preorder tree walk.
     * <p><span style="font-variant:small-caps;">Preorder-Tree-Walk</span> from solution to exercise 12.1-4.</p>
     *
     * @param x   the root of the tree to print out
     * @param <E> the type of keys in the tree
     */
    public static <E> void preorderTreeWalk(BinaryTree.Node<E> x) {
        if (x != null) {
            System.out.println(x.key);
            preorderTreeWalk(x.left);
            preorderTreeWalk(x.right);
        }
    }

    /**
     * Prints out keys of a binary search tree performing postorder tree walk.
     * <p><span style="font-variant:small-caps;">Postorder-Tree-Walk</span> from solution to exercise 12.1-4.</p>
     *
     * @param x   the root of the tree to print out
     * @param <E> the type of keys in the tree
     */
    public static <E> void postorderTreeWalk(BinaryTree.Node<E> x) {
        if (x != null) {
            postorderTreeWalk(x.left);
            postorderTreeWalk(x.right);
            System.out.println(x.key);
        }
    }

    /**
     * Searches for a key in a binary search tree.
     * <p><span style="font-variant:small-caps;">Tree-Search</span> from subchapter 12.2.</p>
     *
     * @param x   the root of the tree
     * @param k   the key to find
     * @param <E> the type of keys in the tree
     * @return the node with key {@code k} in the tree, or {@code null} if the tree does not contain such node
     */
    public static <E extends Comparable<? super E>> BinaryTree.Node<E> treeSearch(BinaryTree.Node<E> x, E k) {
        if (x == null || k.equals(x.key)) {
            return x;
        }
        if (less(k, x.key)) {
            return treeSearch(x.left, k);
        } else {
            return treeSearch(x.right, k);
        }
    }

    /**
     * Iteratively searches for a key in a binary search tree.
     * <p><span style="font-variant:small-caps;">Iterative-Tree-Search</span> from subchapter 12.2.</p>
     *
     * @param x   the root of the tree
     * @param k   the key to find
     * @param <E> the type of keys in the tree
     * @return the node with key {@code k} in the tree, or {@code null} if the tree does not contain such node
     */
    public static <E extends Comparable<? super E>> BinaryTree.Node<E> iterativeTreeSearch(BinaryTree.Node<E> x, E k) {
        while (x != null && !k.equals(x.key)) {
            if (less(k, x.key)) {
                x = x.left;
            } else {
                x = x.right;
            }
        }
        return x;
    }

    /**
     * Returns the node with the smallest key in a non-empty binary search tree.
     * <p><span style="font-variant:small-caps;">Tree-Minimum</span> from subchapter 12.2.</p>
     *
     * @param x   the root of the tree
     * @param <E> the type of keys in the tree
     * @return the node with the smallest key in the tree
     */
    public static <E> BinaryTree.Node<E> treeMinimum(BinaryTree.Node<E> x) {
        while (x.left != null) {
            x = x.left;
        }
        return x;
    }

    /**
     * Returns the node with the largest key in a non-empty binary search tree.
     * <p><span style="font-variant:small-caps;">Tree-Maximum</span> from subchapter 12.2.</p>
     *
     * @param x   the root of the tree
     * @param <E> the type of keys in the tree
     * @return the node with the largest key in the tree
     */
    public static <E> BinaryTree.Node<E> treeMaximum(BinaryTree.Node<E> x) {
        while (x.right != null) {
            x = x.right;
        }
        return x;
    }

    /**
     * Returns the node's successor in a non-empty binary search tree.
     * <p><span style="font-variant:small-caps;">Tree-Successor</span> from subchapter 12.2.</p>
     *
     * @param x   the node of the tree
     * @param <E> the type of keys in the tree
     * @return the successor of {@code x} in the tree, or {@code null} if {@code x} has the largest key in the tree
     */
    public static <E> BinaryTree.Node<E> treeSuccessor(BinaryTree.Node<E> x) {
        if (x.right != null) {
            return treeMinimum(x.right);
        }
        BinaryTree.Node<E> y = x.p;
        while (y != null && x == y.right) {
            x = y;
            y = y.p;
        }
        return y;
    }

    /**
     * Returns the node with the smallest key in a non-empty binary search tree - a recursive version.
     * <p><span style="font-variant:small-caps;">Recursive-Tree-Minimum</span> from solution to exercise 12.2-2.</p>
     *
     * @param x   the root of the tree
     * @param <E> the type of keys in the tree
     * @return the node with the smallest key in the tree
     */
    public static <E> BinaryTree.Node<E> recursiveTreeMinimum(BinaryTree.Node<E> x) {
        if (x.left != null) {
            return recursiveTreeMinimum(x.left);
        }
        return x;
    }

    /**
     * Returns the node with the largest key in a non-empty binary search tree - a recursive version.
     * <p><span style="font-variant:small-caps;">Recursive-Tree-Maximum</span> from solution to exercise 12.2-2.</p>
     *
     * @param x   the root of the tree
     * @param <E> the type of keys in the tree
     * @return the node with the largest key in the tree
     */
    public static <E> BinaryTree.Node<E> recursiveTreeMaximum(BinaryTree.Node<E> x) {
        if (x.right != null) {
            return recursiveTreeMaximum(x.right);
        }
        return x;
    }

    /**
     * Returns the node's predecessor in a non-empty binary search tree.
     * <p><span style="font-variant:small-caps;">Tree-Predecessor</span> from solution to exercise 12.2-3.</p>
     *
     * @param x   the node of the tree
     * @param <E> the type of keys in the tree
     * @return the predecessor of {@code x} in the tree, or {@code null} if {@code x} has the smallest key in the tree
     */
    public static <E> BinaryTree.Node<E> treePredecessor(BinaryTree.Node<E> x) {
        if (x.left != null) {
            return treeMaximum(x.left);
        }
        BinaryTree.Node<E> y = x.p;
        while (y != null && x == y.left) {
            x = y;
            y = y.p;
        }
        return y;
    }

    /**
     * Prints out keys of a binary search tree performing inorder tree walk - an iterative version.
     * <p>Exercise 12.2-7.</p>
     *
     * @param T   the binary search tree
     * @param <E> the type of keys in {@code T}
     */
    public static <E> void inorderTreeWalk_(BinaryTree<E> T) {
        if (T.root == null) {
            return;
        }
        int n = T.getSize();
        BinaryTree.Node<E> x = treeMinimum(T.root);
        System.out.println(x.key);
        for (int i = 1; i <= n - 1; i++) {
            x = treeSuccessor(x);
            System.out.println(x.key);
        }
    }

    /**
     * Inserts a node into a binary search tree.
     * <p><span style="font-variant:small-caps;">Tree-Insert</span> from subchapter 12.3.</p>
     *
     * @param T   the binary search tree
     * @param z   the node to insert
     * @param <E> the type of keys in {@code T}
     */
    public static <E extends Comparable<? super E>> void treeInsert(BinaryTree<E> T, BinaryTree.Node<E> z) {
        BinaryTree.Node<E> y = null;
        BinaryTree.Node<E> x = T.root;
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

    /**
     * Deletes a node from a binary search tree.
     * <p><span style="font-variant:small-caps;">Tree-Delete</span> from subchapter 12.3.</p>
     *
     * @param T   the binary search tree
     * @param z   the node to delete
     * @param <E> the type of keys in {@code T}
     * @return the node deleted from {@code T}
     */
    public static <E> BinaryTree.Node<E> treeDelete(BinaryTree<E> T, BinaryTree.Node<E> z) {
        BinaryTree.Node<E> y;
        if (z.left == null || z.right == null) {
            y = z;
        } else {
            y = treeSuccessor(z);
        }
        BinaryTree.Node<E> x;
        if (y.left != null) {
            x = y.left;
        } else {
            x = y.right;
        }
        if (x != null) {
            x.p = y.p;
        }
        if (y.p == null) {
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
        return y;
    }

    /**
     * Inserts a node into a binary search tree using a recursive version of Tree-Insert.
     * <p><span style="font-variant:small-caps;">Tree-Insert'</span> from solution to exercise 12.3-1.</p>
     *
     * @param T   the binary search tree
     * @param z   the node to insert
     * @param <E> the type of keys in {@code T}
     */
    public static <E extends Comparable<? super E>> void treeInsert_(BinaryTree<E> T, BinaryTree.Node<E> z) {
        if (T.root == null) {
            T.root = z;
        } else {
            recursiveTreeInsert(T.root, z);
        }
    }

    /**
     * Inserts a node into a binary search tree - a recursive version.
     * <p><span style="font-variant:small-caps;">Recursive-Tree-Insert</span> from solution to exercise 12.3-1.</p>
     *
     * @param x   the root of the subtree to insert to
     * @param z   the node to insert
     * @param <E> the type of keys in {@code T}
     */
    static <E extends Comparable<? super E>> void recursiveTreeInsert(BinaryTree.Node<E> x, BinaryTree.Node<E> z) {
        if (less(z.key, x.key)) {
            if (x.left != null) {
                recursiveTreeInsert(x.left, z);
            } else {
                x.left = z;
                z.p = x;
            }
        } else {
            if (x.right != null) {
                recursiveTreeInsert(x.right, z);
            } else {
                x.right = z;
                z.p = x;
            }
        }
    }

    /**
     * Sorts elements by inserting them into a binary search tree and performing inorder tree walk on it.
     * <p>Exercise 12.3-3.</p>
     *
     * @param A   the array of elements to sort
     * @param <E> the type of elements in {@code A}
     */
    public static <E extends Comparable<? super E>> void inorderSort(Array<E> A) {
        BinaryTree<E> T = new BinaryTree<>();
        for (int i = 1; i <= A.length; i++) {
            treeInsert(T, new BinaryTree.Node<>(A.at(i)));
        }
        inorderTreeWalk(T.root);
    }

    /**
     * Deletes a node from a binary search tree - a safe version.
     * <p>Solution to exercise 12.3-4.</p>
     *
     * @param T   the binary search tree
     * @param z   the node to delete
     * @param <E> the type of keys in {@code T}
     */
    public static <E> void safeTreeDelete(BinaryTree<E> T, BinaryTree.Node<E> z) {
        if (z.left == null || z.right == null) {
            treeDelete(T, z);
            return;
        }
        BinaryTree.Node<E> y = treeSuccessor(z);
        if (y.right != null) {
            y.right.p = y.p;
        }
        if (y == y.p.left) {
            y.p.left = y.right;
        } else {
            y.p.right = y.right;
        }
        z.left.p = z.right.p = y;
        if (z.p != null) {
            if (z == z.p.left) {
                z.p.left = y;
            } else {
                z.p.right = y;
            }
        }
        y.p = z.p;
        y.left = z.left;
        y.right = z.right;
    }

    /**
     * Deletes a node from a binary search tree - a fair version.
     * <p>Solution to exercise 12.3-6.</p>
     *
     * @param T   the binary search tree
     * @param z   the node to delete
     * @param <E> the type of keys in {@code T}
     * @return the node deleted from {@code T}
     */
    public static <E> BinaryTree.Node<E> fairTreeDelete(BinaryTree<E> T, BinaryTree.Node<E> z) {
        BinaryTree.Node<E> y;
        if (z.left == null || z.right == null) {
            y = z;
        } else {
            if (random() == 0) {
                y = treePredecessor(z);
            } else {
                y = treeSuccessor(z);
            }
        }
        BinaryTree.Node<E> x;
        if (y.left != null) {
            x = y.left;
        } else {
            x = y.right;
        }
        if (x != null) {
            x.p = y.p;
        }
        if (y.p == null) {
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
        return y;
    }

    /**
     * Sorts bit strings (strings consisted of '0's and '1's) using radix tree.
     * <p>Solution to problem 12-2.</p>
     *
     * @param S the array representing a set of bit strings to sort
     */
    public static void bitStringsSort(Array<String> S) {
        RadixTree T = new RadixTree();
        for (int i = 1; i <= S.length; i++) {
            radixTreeInsert(T, S.at(i));
        }
        preorderRadixTreeWalk(T.root, "");
    }

    private static void radixTreeInsert(RadixTree T, String key) {
        if (T.root == null) {
            T.root = new RadixTree.Node();
        }
        RadixTree.Node x = T.root;
        for (int i = 0; i < key.length(); i++) {
            if (key.charAt(i) == '0') {
                if (x.left == null) {
                    x.left = new RadixTree.Node();
                }
                x = x.left;
            } else {
                if (x.right == null) {
                    x.right = new RadixTree.Node();
                }
                x = x.right;
            }
        }
        x.inTree = true;
    }

    private static void preorderRadixTreeWalk(RadixTree.Node x, String key) {
        if (x != null) {
            if (x.inTree) {
                System.out.println(key);
            }
            preorderRadixTreeWalk(x.left, key + '0');
            preorderRadixTreeWalk(x.right, key + '1');
        }
    }

    /**
     * Sorts elements using quicksort that performs exactly the same comparisons
     * as those performed during inserting the elements to a binary search tree.
     * <p>Solution to problem 12-3(f).</p>
     *
     * @param A   the array of elements to sort
     * @param p   the index of the beginning of subarray in {@code A} being sorted
     * @param r   the index of the end of subarray in {@code A} being sorted
     * @param <E> the type of elements in {@code A}
     */
    public static <E extends Comparable<? super E>> void treeBuildingQuicksort(Array<E> A, int p, int r) {
        if (p < r) {
            int q = treeBuildingPartition(A, p, r);
            treeBuildingQuicksort(A, p, q - 1);
            treeBuildingQuicksort(A, q + 1, r);
        }
    }

    private static <E extends Comparable<? super E>> int treeBuildingPartition(Array<E> A, int p, int r) {
        E x = A.at(p);
        int i = p;
        for (int j = p + 1; j <= r; j++) {
            if (leq(A.at(j), x)) {
                i++;
                A.exch(i, j);
            }
        }
        A.exch(i, p);
        return i;
    }

}
