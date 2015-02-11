package pl.kwojtas.cormenimpl;

import pl.kwojtas.cormenimpl.util.BinaryTree;

import static pl.kwojtas.cormenimpl.util.Util.less;

public final class Chapter12 {

    private Chapter12() { }

    // subchapter 12.1
    public static <T> void inorderTreeWalk(BinaryTree.Node<T> x) {
        if (x != null) {
            inorderTreeWalk(x.left);
            System.out.println(x.key);
            inorderTreeWalk(x.right);
        }
    }

    // solution of 12.1-4
    public static <T> void preorderTreeWalk(BinaryTree.Node<T> x) {
        if (x != null) {
            System.out.println(x.key);
            preorderTreeWalk(x.left);
            preorderTreeWalk(x.right);
        }
    }

    // solution of 12.1-4
    public static <T> void postorderTreeWalk(BinaryTree.Node<T> x) {
        if (x != null) {
            postorderTreeWalk(x.left);
            postorderTreeWalk(x.right);
            System.out.println(x.key);
        }
    }

    // subchapter 12.2
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

    // subchapter 12.2
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

    // subchapter 12.2
    public static <T> BinaryTree.Node<T> treeMinimum(BinaryTree.Node<T> x) {
        while (x.left != null) {
            x = x.left;
        }
        return x;
    }

    // subchapter 12.2
    public static <T> BinaryTree.Node<T> treeMaximum(BinaryTree.Node<T> x) {
        while (x.right != null) {
            x = x.right;
        }
        return x;
    }

    // subchapter 12.2
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

    // solution of 12.2-2
    public static <T extends Comparable> BinaryTree.Node<T> recursiveTreeMinimum(BinaryTree.Node<T> x) {
        if (x.left != null) {
            return recursiveTreeMinimum(x.left);
        }
        return x;
    }

    // solution of 12.2-2
    public static <T extends Comparable> BinaryTree.Node<T> recursiveTreeMaximum(BinaryTree.Node<T> x) {
        if (x.right != null) {
            return recursiveTreeMaximum(x.right);
        }
        return x;
    }

    // solution of 12.2-3
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

    // exercise 12.2-7
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

    // subchapter 12.3
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

    // subchapter 12.3
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

    // solution of 12.3-1
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
