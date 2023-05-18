package com.aprz;

import java.lang.IllegalStateException;

public class RedBlackTree {

    // point to root
    private Node dummy = new Node();

    public RedBlackTree() {
        initRoot();
    }

    public Node root() {
        // dummy.leftChild == dummy.rightChild
        return dummy.getLeftChild();
    }

    private void initRoot() {
        dummy.setLeftChild(null);
        dummy.setRightChild(null);
    }

    public void setRoot(Node node) {
        Check.checkNotNull(node);

        dummy.setLeftChild(node);
        dummy.setRightChild(node);
    }

    public void insert(int key) {
        Node insertNode = new Node();
        insertNode.setKey(key);
        insert(insertNode);
    }

    private void insert(Node node) {
        Check.checkNull(node.getParent());

        if (isEmpty()) {
            dummy.setLeftChild(node);
            dummy.setRightChild(node);
            // 根节点一定要是黑色
            node.setColor(Color.BLACK);
        } else {
            // 插入肯定发生在叶子节点上面
            // 如果该节点是黑色节点，可以直接插入到左边或者右边
            // 如果该节点是红色节点，插入后就会出现两个红色节点，破坏了红黑树的性质。

            // 为了保持红黑树的性质，这个时候就要对树进行旋转
            // 我们找到需要插入的位置，然后找到其parent 和 grand parent
            // 对这3个节点进行旋转
            // 旋转其实就是将这3个节点的中序遍历结果的中间往上提
            // 具体看图
            // 旋转完成之后，重新着色
            // 着色的要求就是要保持树高不变
            // 着色完成后，会破坏 grand parent 的颜色约束，所以需要递归往上重新着色
            Node insertParent = findInsertLeaf(node);
            insertLeaf(node, insertParent);

            // check color if need rotate
            Node x = node;
            Node p = x.getParent();
            Node g = p.getParent();

            // 根节点是黑色，会终结循环
            while (p.isRed() && !isRoot(x)) {
                x = rotate(x, p, g);
                p = x.getParent();
                g = p.getParent();

                // make x color red
                recolor(x, x.getLeftChild(), x.getRightChild());
            }

            if (isRoot(x)) {
                x.setColor(Color.BLACK);
            }

        }

    }

    private boolean isRoot(Node node) {
        return dummy.getLeftChild() == node;
    }

    private void recolor(Node x, Node left, Node right) {
        x.setColor(Color.RED);
        left.setColor(Color.BLACK);
        right.setColor(Color.BLACK);
    }

    private Node rotate(Node x, Node p, Node g) {
        Check.checkNotNull(x);
        Check.checkNotNull(p);
        Check.checkNotNull(g);

        Check.checkEquals(x.getParent(), p);
        Check.checkEquals(p.getParent(), g);

        // Check.checkEquals(x.getColor(), Color.RED);
        // Check.checkEquals(p.getColor(), Color.RED);
        // Check.checkEquals(g.getColor(), Color.BLACK);

        boolean xIsLeft = p.getLeftChild() == x;
        boolean pIsLeft = g.getLeftChild() == p;

        Node pLeft = p.getLeftChild();
        Node pRight = p.getRightChild();

        Node xLeft = x.getLeftChild();
        Node xRight = x.getRightChild();

        // 旋转
        // 旋转有很多写法，反正只要达到目的即可
        if (xIsLeft && pIsLeft) {
            replaceNode(g, p);
            p.setRightChild(g);
            g.setLeftChild(pRight);

            return p;
        } else if (pIsLeft && !xIsLeft) {
            replaceNode(g, x);
            x.setLeftChild(p);
            x.setRightChild(g);
            p.setRightChild(xLeft);
            g.setLeftChild(xRight);

            return x;
        } else if (!pIsLeft && xIsLeft) {
            replaceNode(g, x);
            x.setLeftChild(g);
            x.setRightChild(p);
            g.setRightChild(xLeft);
            p.setLeftChild(xRight);

            return x;
        } else {
            replaceNode(g, p);
            p.setLeftChild(g);
            g.setRightChild(pLeft);

            return p;
        }
    }

    private void replaceNode(Node victim, Node target) {
        Check.checkNotNull(victim);
        // Check.checkNotNull(target);
        // Check.checkNotNull(victim.getParent());

        if (victim == root()) {
            setRoot(target);
        } else {
            Node iParent = victim.getParent();
            boolean iIsLeft = iParent.getLeftChild() == victim;
            if (iIsLeft) {
                iParent.setLeftChild(target);
            } else {
                iParent.setRightChild(target);
            }
        }
    }

    private void insertLeaf(Node node, Node parent) {
        Check.checkNotNull(node);
        Check.checkNotNull(parent);

        if (node.getKey() < parent.getKey()) {
            parent.setLeftChild(node);
        } else {
            parent.setRightChild(node);
        }
    }

    private Node findInsertLeaf(Node node) {
        Node root = dummy.getLeftChild();
        Node x = root;

        while (x != null) {
            if (node.getKey() < x.getKey()) {
                if (x.getLeftChild() == null) {
                    return x;
                }
                // 走左边
                x = x.getLeftChild();
            } else {
                if (x.getRightChild() == null) {
                    return x;
                }
                // 走右边
                x = x.getRightChild();
            }
        }

        // should not reach here
        throw new IllegalStateException("should not reach here, can not find insert position!!!");
    }

    public boolean isEmpty() {
        return dummy.leftChildEmpty() && dummy.rightChildEmpty();
    }

    public void delete(int key) {
        Node target = find(key);
        if (target == null) {
            Log.d("key = " + key + ", node not exist!");
            return;
        }
        delete(target);
    }

    private Node find(int key) {
        Node iterator = root();

        while (iterator != null) {
            if (iterator.getKey() == key) {
                return iterator;
            } else if (iterator.getKey() < key) {
                iterator = iterator.getRightChild();
            } else {
                iterator = iterator.getLeftChild();
            }
        }

        return null;
    }

    private void delete(Node node) {
        Check.checkNotNull(node);

        if (isEmpty()) {
            return;
        }

        Node db = null;
        Node p = deleteInternal(node);
        Node s = null;
        Node n = null;
        Node f = null;

        // 无双黑节点
        // 根节点不需处理
        if (p == dummy || p == null) {
            return;
        }

        Psnf psnf = null;

        while (db != root()) {
            psnf = getPsnf(db, p);

            Check.checkNotNull(psnf);
            p = psnf.getP();
            s = psnf.getS();
            n = psnf.getN();
            f = psnf.getF();

            Log.d("p node = " + p);
            Log.d("s node = " + s);

            // n f can be null
            int nColor = n == null ? Color.BLACK.ordinal() : n.getColor().ordinal();
            int fColor = f == null ? Color.BLACK.ordinal() : f.getColor().ordinal();
            int psfnColor = (p.getColor().ordinal() << 3)
                    | (s.getColor().ordinal() << 2)
                    | (nColor << 1)
                    | (fColor << 0);

            Log.d("psnf value = " + Integer.toHexString(psfnColor));

            Color pColor = p.getColor();

            switch (psfnColor) {
                case 0xf:
                    // float up
                    db = p;
                    s.setColor(Color.RED);
                    continue;
                case 0xb:
                    rotate(f, s, p);
                    s.setColor(Color.BLACK);
                    p.setColor(Color.RED);
                    continue;
                case 0x7:
                    p.setColor(Color.BLACK);
                    s.setColor(Color.RED);
                    break;
                case 0x4:
                case 0x5:
                case 0xc:
                case 0xd:
                    rotate(n, s, p);
                    p.setColor(Color.BLACK);
                    s.setColor(Color.BLACK);
                    n.setColor(pColor);
                    break;
                case 0x6:
                case 0xe:
                    rotate(f, s, p);
                    p.setColor(Color.BLACK);
                    s.setColor(pColor);
                    f.setColor(Color.BLACK);
                    break;

                default:
                    Check.shouldNotReachHere();
                    break;
            }
            break;
        }
    }

    private Psnf getPsnf(Node db, Node p) {
        // if (db == null && p == null) {
        // return new Psnf(p, null, null, null);
        // }

        if (db != null) {
            p = db.getParent();
        }

        boolean dbIsLeft = db == p.getLeftChild();
        Node s = null;
        Node n = null;
        Node f = null;

        if (dbIsLeft) {
            s = p.getRightChild();
        } else {
            s = p.getLeftChild();
        }

        Check.checkNotNull(s);

        if (dbIsLeft) {
            n = s.getLeftChild();
            f = s.getRightChild();
        } else {
            n = s.getRightChild();
            f = s.getLeftChild();
        }

        return new Psnf(p, s, n, f);
    }

    private Node deleteInternal(Node node) {
        Node x = node;
        Node doubleBlack = null;

        if (x.leftChildEmpty() && x.rightChildEmpty()) {
            // 要删除的是一个叶子节点
            // 黑色
            if (x.isBlack()) {
                doubleBlack = x.getParent();
            }

            replaceNode(x, null);

            return doubleBlack;
        } else if (x.leftChildEmpty() || x.rightChildEmpty()) {
            // 要删除的是只有一个孩子的节点
            // 根据红黑树的性质可以推断，该节点是黑节点，孩子是红节点
            // 直接删除该黑节点，让红色孩子节点补位，再把孩子染成黑色即可
            Node child = null;
            if (x.leftChildEmpty()) {
                child = x.getRightChild();
            } else {
                child = x.getLeftChild();
            }

            child.setColor(Color.BLACK);
            replaceNode(x, child);
            return null;
        } else {
            // 要删除的节点有两个孩子节点
            Node xRightLeftChild = x.getRightChild().getLeftChild();
            Node s = x.getRightChild();
            Node xLeft = x.getLeftChild();
            Node xRight = x.getRightChild();
            Node sRight = s.getRightChild();
            if (xRightLeftChild == null) {
                // 交换两个节点，交换颜色，就变成了第2中情况
                replaceNode(x, s);
                s.setLeftChild(xLeft);
                x.setRightChild(s.getRightChild());
                x.setLeftChild(null);
                s.setRightChild(x);
            } else {
                Node succussor = s;
                while (succussor != null && succussor.getLeftChild() != null) {
                    succussor = succussor.getLeftChild();
                }
                s = succussor;
                sRight = s.getRightChild();

                Node sParent = s.getParent();

                x.setRightChild(sRight);
                x.setLeftChild(null);
                s.setLeftChild(xLeft);
                s.setRightChild(xRight);

                replaceNode(x, s);
                sParent.setLeftChild(x);
            }

            Color xColor = x.getColor();
            x.setColor(s.getColor());
            s.setColor(xColor);

            // 变成了前面的第一种或者第二种情况
            return deleteInternal(x);
        }
    }

}
