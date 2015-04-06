package pl.kwojtas.cormenimpl;

import pl.kwojtas.cormenimpl.util.BinaryTree;

import static pl.kwojtas.cormenimpl.util.Util.less;

/**
 * Implements algorithms from Chapter 12.
 */
public final class Chapter12 {

    private Chapter12() { }

    /**
     * Prints out the keys of a binary tree performing inorder tree walk.
     * <p><span style="font-variant:small-caps;">Inorder-Tree-Walk</span> from subchapter 12.1.</p>
     *
     * @param x the root of the tree to print out
     * @param <T> the type of keys in the tree
     */
    public static <T> void inorderTreeWalk(BinaryTree.Node<T> x) {
        if (x != null) {
            inorderTreeWalk(x.left);
            System.out.println(x.key);
            inorderTreeWalk(x.right);
        }
    }

    /**
     * Prints out the keys of a binary tree performing preorder tree walk.
     * <p><span style="font-variant:small-caps;">Preorder-Tree-Walk</span> from solution to exercise 12.1-4.</p>
     *
     * @param x the root of the tree to print out
     * @param <T> the type of keys in the tree
     */
    public static <T> void preorderTreeWalk(BinaryTree.Node<T> x) {
        if (x != null) {
            System.out.println(x.key);
            preorderTreeWalk(x.left);
            preorderTreeWalk(x.right);
        }
    }

    /**
     * Prints out the keys of a binary tree performing postorder tree walk.
     * <p><span style="font-variant:small-caps;">Postorder-Tree-Walk</span> from solution to exercise 12.1-4.</p>
     *
     * @param x the root of the tree to print out
     * @param <T> the type of keys in the tree
     */
    public static <T> void postorderTreeWalk(BinaryTree.Node<T> x) {
        if (x != null) {
            postorderTreeWalk(x.left);
            postorderTreeWalk(x.right);
            System.out.println(x.key);
        }
    }

    /**
     * Searches for a key in a binary tree.
     * <p><span style="font-variant:small-caps;">Tree-Search</span> from subchapter 12.2.</p>
     *
     * @param x the root of the tree
     * @param k the key to find
     * @param <T> the type of keys in the tree
     * @return the node of key {@code k} in the tree, or {@code null} if the tree does not contain such node
     */
    public static <T extends Comparable> BinaryTree.Node<T> treeSearch(BinaryTree.Node<T> x, T k) {
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
     * Iteratively searches for a key in a binary tree.
     * <p><span style="font-variant:small-caps;">Iterative-Tree-Search</span> from subchapter 12.2.</p>
     *
     * @param x the root of the tree
     * @param k the key to find
     * @param <T> the type of keys in the tree
     * @return the node of key {@code k} in the tree, or {@code null} if the tree does not contain such node
     */
    public static <T extends Comparable> BinaryTree.Node<T> iterativeTreeSearch(BinaryTree.Node<T> x, T k) {
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
     * Returns the node with the smallest key in a non-empty binary tree.
     * <p><span style="font-variant:small-caps;">Tree-Minimum</span> from subchapter 12.2.</p>
     *
     * @param x the root of the tree
     * @param <T> the type of keys in the tree
     * @return the node with the smallest key in a binary tree
     */
    public static <T> BinaryTree.Node<T> treeMinimum(BinaryTree.Node<T> x) {
        while (x.left != null) {
            x = x.left;
        }
        return x;
    }

    /**
     * Returns the node with the largest key in a non-empty binary tree.
     * <p><span style="font-variant:small-caps;">Tree-Maximum</span> from subchapter 12.2.</p>
     *
     * @param x the root of the tree
     * @param <T> the type of keys in the tree
     * @return the node with the largest key in a binary tree
     */
    public static <T> BinaryTree.Node<T> treeMaximum(BinaryTree.Node<T> x) {
        while (x.right != null) {
            x = x.right;
        }
        return x;
    }

    /**
     * Returns the node's successor in a non-empty binary tree.
     * <p><span style="font-variant:small-caps;">Tree-Successor</span> from subchapter 12.2.</p>
     *
     * @param x the node of the tree
     * @param <T> the type of keys in the tree
     * @return the successor of {@code x} in the tree, or {@code null} if {@code x} has the largest key in the tree
     */
    public static <T extends Comparable> BinaryTree.Node<T> treeSuccessor(BinaryTree.Node<T> x) {
        if (x.right != null) {
            return treeMinimum(x.right);
        }
        BinaryTree.Node<T> y = x.p;
        while (y != null && x == y.right) {
            x = y;
            y = y.p;
        }
        return y;
    }

    /**
     * Returns the node with the smallest key in a non-empty binary tree - recursive version.
     * <p><span style="font-variant:small-caps;">Recursive-Tree-Minimum</span> from solution to exercise 12.2-2.</p>
     *
     * @param x the root of the tree
     * @param <T> the type of keys in the tree
     * @return the node with the smallest key in a binary tree
     */
    public static <T extends Comparable> BinaryTree.Node<T> recursiveTreeMinimum(BinaryTree.Node<T> x) {
        if (x.left != null) {
            return recursiveTreeMinimum(x.left);
        }
        return x;
    }

    /**
     * Returns the node with the largest key in a non-empty binary tree - recursive version.
     * <p><span style="font-variant:small-caps;">Recursive-Tree-Maximum</span> from solution to exercise 12.2-2.</p>
     *
     * @param x the root of the tree
     * @param <T> the type of keys in the tree
     * @return the node with the largest key in a binary tree
     */
    public static <T extends Comparable> BinaryTree.Node<T> recursiveTreeMaximum(BinaryTree.Node<T> x) {
        if (x.right != null) {
            return recursiveTreeMaximum(x.right);
        }
        return x;
    }

    /**
     * Returns the node's predecessor in a non-empty binary tree.
     * <p><span style="font-variant:small-caps;">Tree-Predecessor</span> from solution to exercise 12.2-3.</p>
     *
     * @param x the node of the tree
     * @param <T> the type of keys in the tree
     * @return the predecessor of {@code x} in the tree, or {@code null} if {@code x} has the smallest key in the tree
     */
    public static <T extends Comparable> BinaryTree.Node<T> treePredecessor(BinaryTree.Node<T> x) {
        if (x.left != null) {
            return treeMaximum(x.left);
        }
        BinaryTree.Node<T> y = x.p;
        while (y != null && x == y.left) {
            x = y;
            y = y.p;
        }
        return y;
    }

    /**
     * Prints out the keys of a binary tree performing inorder tree walk - iterative version.
     * <p>Exercise 12.2-7.</p>
     *
     * @param T the binary tree
     * @param <T> the type of keys in the tree
     */
    public static <T extends Comparable> void inorderTreeWalk_(BinaryTree<T> T) {
        if (T.root == null) {
            return;
        }
        int n = T.getSize();
        BinaryTree.Node<T> x = treeMinimum(T.root);
        System.out.println(x.key);
        for (int i = 1; i <= n - 1; i++) {
            x = treeSuccessor(x);
            System.out.println(x.key);
        }
    }

    /**
     * Inserts a node into a binary tree.
     * <p><span style="font-variant:small-caps;">Tree-Insert</span> from subchapter 12.3.</p>
     *
     * @param T the binary tree
     * @param z the node to insert
     * @param <T> the type of keys in the tree
     */
    public static <T extends Comparable> void treeInsert(BinaryTree<T> T, BinaryTree.Node<T> z) {
        BinaryTree.Node<T> y = null;
        BinaryTree.Node<T> x = T.root;
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
     * Deletes a node from a binary tree
     * <p><span style="font-variant:small-caps;">Tree-Delete</span> from subchapter 12.3.</p>
     *
     * @param T the binary tree
     * @param z the node to delete
     * @param <T> the type of keys in the tree
     * @return the node deleted from {@code T}
     */
    public static <T extends Comparable> BinaryTree.Node<T> treeDelete(BinaryTree<T> T, BinaryTree.Node<T> z) {
        BinaryTree.Node<T> y;
        if (z.left == null && z.right == null) {
            y = z;
        } else {
            y = treeSuccessor(z);
        }
        BinaryTree.Node<T> x;
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
     * Inserts a node into a binary tree - recursive version.
     * <p><span style="font-variant:small-caps;">Recursive-Tree-Insert</span> from solution to exercise 12.3-1.</p>
     *
     * @param T the binary tree
     * @param x the root of the tree
     * @param z the node to insert
     * @param <T> the type of keys in the tree
     */
    public static <T extends Comparable> void recursiveTreeInsert(BinaryTree<T> T, BinaryTree.Node<T> x, BinaryTree.Node<T> z) {
        if (x == null) {
            T.root = z;
            return;
        }
        if (less(z.key, x.key)) {
            if (x.left != null) {
                recursiveTreeInsert(T, x.left, z);
            } else {
                x.left = z;
                z.p = x;
            }
        } else {
            if (x.right != null) {
                recursiveTreeInsert(T, x.right, z);
            } else {
                x.right = z;
                z.p = x;
            }
        }
    }

}
