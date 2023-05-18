package com.aprz;

import java.util.Stack;

/**
 * 构造红黑树
 * 
 * 使用字符串构造一个红黑树
 * 
 * # 字符表示空节点
 * (a,b,c) 表示一个a节点有两个孩子节点，左孩子节点是b，右孩子节点是c
 *
 */
public class Builder {

    public RedBlackTree build(String treeString, String colorString) {
        Node root = buildTree(treeString);
        if (root != null) {
            colorTree(root, colorString);
        }

        RedBlackTree rbt = new RedBlackTree();
        rbt.setRoot(root);

        Verify.verify(rbt);
        return rbt;

    }

    private void colorTree(Node tree, String coloString) {
        int count = colorTreeDfs(tree, coloString, 0);
        Check.checkEquals(count, coloString.length() - 1);
    }

    private int colorTreeDfs(Node tree, String colorString, int index) {
        if (tree == null) {
            Check.checkEquals('#', colorString.charAt(index));
            return index;
        }

        if (colorString.charAt(index) == 'R') {
            tree.setColor(Color.RED);
        } else if (colorString.charAt(index) == 'B') {
            tree.setColor(Color.BLACK);
        }

        index = colorTreeDfs(tree.getLeftChild(), colorString, index + 1);
        // index 传递给参数，累加
        index = colorTreeDfs(tree.getRightChild(), colorString, index + 1);

        return index;
    }

    private Node buildTree(String treeString) {
        Check.checkNotNull(treeString);

        Stack<Node> stack = new Stack<>();
        Node holder = new Node();
        int i = 0;
        char ch = 0;

        while (i < treeString.length()) {
            ch = treeString.charAt(i);
            if (ch == '(') {

                Node node = new Node();
                node.setLeftChild(holder);
                node.setRightChild(holder);

                // (key,
                int j = i + 1;
                while ('0' <= treeString.charAt(j) && treeString.charAt(j) <= '9') {
                    j++;
                }
                int key = Integer.parseInt(treeString.substring(i + 1, j));

                node.setKey(key);
                stack.push(node);

                i = j + 1;
                continue;
            } else if (ch == ')') {
                if (stack.isEmpty()) {
                    Check.shouldNotReachHere();
                }

                if (stack.size() == 1) {
                    Node root = stack.peek();
                    Node rootLeft = root.getLeftChild();
                    Node rootRight = root.getRightChild();
                    Check.checkNotEquals(rootLeft, holder);
                    Check.checkNotEquals(rootRight, holder);
                    return root;
                }

                Node top = stack.pop();
                Node parent = stack.peek();

                Node pLeft = parent.getLeftChild();
                Node pRight = parent.getRightChild();

                if (pLeft == holder) {
                    parent.setLeftChild(top);
                    i++;
                    continue;
                } else if (pRight == holder) {
                    parent.setRightChild(top);
                    i++;
                    continue;
                }

                Check.shouldNotReachHere("error when meet ')', tree string : " + treeString + ", ch = " + ch + ", i = " + i);

            } else if (ch == '#') {
                if (stack.isEmpty()) {
                    Check.shouldNotReachHere("tree string wrong NODE : " + treeString);
                }

                Node top = stack.peek();
                if (top.getLeftChild() == holder) {
                    top.setLeftChild(null);
                    i++;
                    continue;
                } else if (top.getRightChild() == holder) {
                    top.setRightChild(null);
                    i++;
                    continue;
                }

                Check.shouldNotReachHere("tree String wrong NODE : " + treeString);
            } else {
                i++;
                continue;
            }
        }

        return null;
    }

    public RedBlackTree delete(RedBlackTree tree, int key) {
        tree.delete(key);
        return tree;
    }

    public RedBlackTree insert(RedBlackTree tree, int key) {
        tree.insert(key);
        return tree;
    }
}
