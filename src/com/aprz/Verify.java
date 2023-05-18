package com.aprz;

/**
 * 校验红黑树
 */
public class Verify {

    // private static void printRbt(RedBlackTree tree) {
    //     Log.d("print tree, start....");
    //     printNode(tree.root());
    //     Log.d("print tree, end....");
    // }

    // private static void printNode(Node node) {
    //     if (node == null) {
    //         return;
    //     }
    //     printNode(node.getLeftChild());
    //     Log.d("key = " + node.getKey());
    //     printNode(node.getRightChild());
    // }

    public static void verify(RedBlackTree tree) {
        if (tree.root() == null) {
            return;
        }

        // printRbt(tree);

        Check.checkEquals(Color.BLACK, tree.root().getColor());
        verifyBlackHeight(tree.root(), 0);
    }

    private static int verifyBlackHeight(Node node, int blackHeight) {
        if (node == null) {
            return 0;
        }

        Node left = node.getLeftChild();
        Node right = node.getRightChild();

        int leftBlackHeight = verifyBlackHeight(left, blackHeight);
        int rightBlackHeight = verifyBlackHeight(right, blackHeight);

        Check.checkEquals(leftBlackHeight, rightBlackHeight);

        if (node.isRed()) {
            if (left != null) {
                Check.checkEquals(left.getColor(), Color.BLACK);
            }
            if (right != null) {
                Check.checkEquals(right.getColor(), Color.BLACK);
            }
            blackHeight = leftBlackHeight;
        } else if (node.isBlack()) {
            blackHeight = leftBlackHeight + 1;
        } else {
            Check.shouldNotReachHere("color is not red or black, color = " + node.getColor());
        }

        // 旋转之后，left child 可能与 node 相等
        if (left != null) {
            Check.checkLessEquals(left.getKey(), node.getKey());
        }

        if (right != null) {
            Check.checkLessEquals(node.getKey(), right.getKey());
        }

        return blackHeight;
    }

    public static void compareTree(RedBlackTree tree1, RedBlackTree tree2) {
        if (tree1.root() == null && tree2.root() == null) {
            return;
        }

        if (tree1.root() != null && tree2.root() != null) {
            Log.d("print tree:-------------------------");
            Log.d("tree1 : ");
            printTree(tree1.root(), 0);
            Log.d("tree2:");
            printTree(tree2.root(), 0);

            compareTreeInternal(tree1.root(), tree2.root(), 1);
            return;
        }

        Check.shouldNotReachHere("tree1 not equals to tree2!");
    }

    private static void compareTreeInternal(Node root1, Node root2, int level) {
        if (root1 == null && root2 == null) {
            return;
        } else if (root1 == null || root2 == null) {
            Check.shouldNotReachHere("tree not the same!");
        } else {
            Check.checkEquals(root1.getKey(), root2.getKey());

            compareTreeInternal(root1.getLeftChild(), root2.getLeftChild(), level + 1);
            compareTreeInternal(root1.getRightChild(), root2.getRightChild(), level + 1);
        }

    }

    public static void printTree(Node root, int level) {

        if(root == null) {
            return;
        }

        int space = level;
        StringBuilder sb = new StringBuilder();
        while (space != 0) {
            space--;
            sb.append("-");
        }

        if (root != null) {
            Log.d(sb.toString() + root.getKey() + "(" + root.getColor() + ")");
        }

        printTree(root.getLeftChild(), level + 1);
        printTree(root.getRightChild(), level + 1);
    }

}
