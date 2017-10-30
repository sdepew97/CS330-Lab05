//Class from 206 Data Structures Class

package cs340.programming.project;

public class BinaryTree<E> {

    // Needs an inner class of nodes
    protected static class Node<E> {
        protected E data;
        protected Node<E> left, right;
        // Constructor
        public Node(E data) {
            this.data = data;
            left = right = null;
        }
        // Methods
        public String toString() {
            return data.toString();
        }
    } // Node<E> inner class

    public static class BinaryTreeException extends Exception {
        BinaryTreeException(String message) {
            super(message);
        }
    } // end of inner class BinaryTreeException

    // Data members/fields...just the root is needed
    protected Node<E> root;

    // Constructor(s)
    public BinaryTree() {
        root = null;
    } // BinaryTree()

    protected BinaryTree(Node<E> node) {
        this.root = node;
    } // BinaryTree(node)

    public BinaryTree(E data, BinaryTree<E> leftTree, BinaryTree<E> rightTree) {
        root = new Node<E>(data);
        if (leftTree != null) {
            root.left = leftTree.root;
        } else {
            root.left = null;
        }

        if (rightTree != null) {
            root.right = rightTree.root;
        } else {
            root.right = null;
        }
    } // BinaryTree(d, l, r)

    // Methods: Accessors

    public E getData() throws BinaryTreeException {
        if (root != null) {
            return root.data;
        } else {
            throw new BinaryTreeException("Trying to access data from empty tree.");
        }
    }

    public boolean isEmpty() {
        return root == null;
    }

    public void clear() {
        root = null;
    }

    public BinaryTree<E> getLeftSubtree() {
        if (root != null && root.left != null) {
            return new BinaryTree<E>(root.left);
        } else {
            return new BinaryTree<E>(null);
        }
    } // getLeftSubtree()

    public BinaryTree<E> getRightSubtree() {
        if (root != null && root.right != null) {
            return new BinaryTree<E>(root.right);
        } else {
            return new BinaryTree<E>(null);
        }
    } // getRightSubtree()

    public boolean isLeaf() {
        return root == null || (root.left == null && root.right == null);
    } // isLeaf()

    public int height() {
        // return the height of the tree
        if (root == null)
            return 0;
        else
            return height(root);
    }

    private int height(Node<E> node) {
        if (node == null)
            return 0;
        else
            return 1 + Math.max(height(node.left), height(node.right));
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        preOrderTraverse(root, 1, sb);
        return sb.toString();
    } // toString()

    // Traversals
    private void preOrderTraverse(Node<E> node, int depth, StringBuilder sb) {
        for (int i = 1; i < depth; i ++) {
            sb.append("   ");
        }

        if (node == null) {
            sb.append("null\n");
        } else {
            sb.append(node.toString());
            sb.append("\n");
            preOrderTraverse(node.left, depth+1, sb);
            preOrderTraverse(node.right, depth+1, sb);
        } // preOrderTraverse()
    }

} // BinaryTree<E> class

