package pl.kwojtas.cormenimpl;

import pl.kwojtas.cormenimpl.util.BinaryTree;

import static pl.kwojtas.cormenimpl.util.Util.less;

public final class Chapter12 {

    private Chapter12() { }

    // subchapter 12.1
    public static <T> void inorderTreeWalk(BinaryTree<T>.Node x) {
        if (x != null) {
            inorderTreeWalk(x.left);
            System.out.println(x.key);
            inorderTreeWalk(x.right);
        }
    }

    // solution of 12.1-4
    public static <T> void preorderTreeWalk(BinaryTree<T>.Node x) {
        if (x != null) {
            System.out.println(x.key);
            preorderTreeWalk(x.left);
            preorderTreeWalk(x.right);
        }
    }

    // solution of 12.1-4
    public static <T> void postorderTreeWalk(BinaryTree<T>.Node x) {
        if (x != null) {
            postorderTreeWalk(x.left);
            postorderTreeWalk(x.right);
            System.out.println(x.key);
        }
    }

    // subchapter 12.2
    public static <T extends Comparable> BinaryTree<T>.Node treeSearch(BinaryTree<T>.Node x, T k) {
        if (x == null || k.equals(x.key)) {
            return x;
        }
        if (less(k, x.key)) {
            return treeSearch(x.left, k);
        } else {
            return treeSearch(x.right, k);
        }
    }

    // subchapter 12.2
    public static <T extends Comparable> BinaryTree<T>.Node iterativeTreeSearch(BinaryTree<T>.Node x, T k) {
        while (x != null && !k.equals(x.key)) {
            if (less(k, x.key)) {
                x = x.left;
            } else {
                x = x.right;
            }
        }
        return x;
    }

    // subchapter 12.2
    public static <T> BinaryTree<T>.Node treeMinimum(BinaryTree<T>.Node x) {
        while (x.left != null) {
            x = x.left;
        }
        return x;
    }

    // subchapter 12.2
    public static <T> BinaryTree<T>.Node treeMaximum(BinaryTree<T>.Node x) {
        while (x.right != null) {
            x = x.right;
        }
        return x;
    }

    // subchapter 12.2
    public static <T extends Comparable> BinaryTree<T>.Node treeSuccessor(BinaryTree<T>.Node x) {
        if (x.right != null) {
            return treeMinimum(x.right);
        }
        BinaryTree<T>.Node y = x.p;
        while (y != null && x == y.right) {
            x = y;
            y = y.p;
        }
        return y;
    }

    // solution of 12.2-2
    public static <T extends Comparable> BinaryTree<T>.Node recursiveTreeMinimum(BinaryTree<T>.Node x) {
        if (x.left != null) {
            return recursiveTreeMinimum(x.left);
        }
        return x;
    }

    // solution of 12.2-2
    public static <T extends Comparable> BinaryTree<T>.Node recursiveTreeMaximum(BinaryTree<T>.Node x) {
        if (x.right != null) {
            return recursiveTreeMaximum(x.right);
        }
        return x;
    }

    // solution of 12.2-3
    public static <T extends Comparable> BinaryTree<T>.Node treePredecessor(BinaryTree<T>.Node x) {
        if (x.left != null) {
            return treeMaximum(x.left);
        }
        BinaryTree<T>.Node y = x.p;
        while (y != null && x == y.left) {
            x = y;
            y = y.p;
        }
        return y;
    }

    // exercise 12.2-7
    public static <T extends Comparable> void inorderTreeWalk_(BinaryTree<T> T) {
        if (T.root == null) {
            return;
        }
        int n = T.getSize();
        BinaryTree<T>.Node x = treeMinimum(T.root);
        System.out.println(x.key);
        for (int i = 1; i <= n - 1; i++) {
            x = treeSuccessor(x);
            System.out.println(x.key);
        }
    }

    // subchapter 12.3
    public static <T extends Comparable> void treeInsert(BinaryTree<T> T, BinaryTree<T>.Node z) {
        BinaryTree<T>.Node y = null;
        BinaryTree<T>.Node x = T.root;
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

    // subchapter 12.3
    public static <T extends Comparable> BinaryTree<T>.Node treeDelete(BinaryTree<T> T, BinaryTree<T>.Node z) {
        BinaryTree<T>.Node y;
        if (z.left == null && z.right == null) {
            y = z;
        } else {
            y = treeSuccessor(z);
        }
        BinaryTree<T>.Node x;
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

    // solution of 12.3-1
    public static <T extends Comparable> void recursiveTreeInsert(BinaryTree<T> T, BinaryTree<T>.Node x, BinaryTree<T>.Node z) {
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
