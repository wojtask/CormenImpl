package pl.kwojtas.cormenimpl.util;

public class BinaryTree<T> {

    public class Node {
        public T key;
        public Node p;
        public Node left;
        public Node right;
    }

    public Node root;

    public int getSize() {
        return getSize(root);
    }

    private int getSize(Node x) {
        int treeSize = 1;
        if (x.left != null) {
            treeSize += getSize(x.left);
        }
        if (x.right != null) {
            treeSize += getSize(x.right);
        }
        return treeSize;
    }

}
