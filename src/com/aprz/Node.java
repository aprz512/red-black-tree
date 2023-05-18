package com.aprz;

enum Color {
    RED, BLACK
}

class Node {

    private Node parent;
    private Node leftChild;
    private Node rightChild;
    private Color color = Color.RED;
    private int key;
    private int value;

    public boolean isBlack() {
        return color == Color.BLACK;
    }

    public boolean isRed() {
        return color == Color.RED;
    }

    public void setLeftChild(Node leftChild) {
        this.leftChild = leftChild;
        if (leftChild != null) {
            leftChild.parent = this;
        }
    }

    public void setRightChild(Node rightChild) {
        this.rightChild = rightChild;
        if (rightChild != null) {
            rightChild.parent = this;
        }
    }

    public Node getLeftChild() {
        return leftChild;
    }

    public Node getRightChild() {
        return rightChild;
    }

    public Node getParent() {
        return parent;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public int getKey() {
        return key;
    }

    public int getValue() {
        return value;
    }

    public boolean leftChildEmpty() {
        return this.leftChild == null;
    }

    public boolean rightChildEmpty() {
        return this.rightChild == null;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void setKey(int key) {
        this.key = key;
    }

    @Override
    public String toString() {

        String lKey = null;
        String rKey = null;
        String pKey = null;

        if (leftChild != null) {
            lKey = leftChild.getKey() + "";
        }

        if (rightChild != null) {
            lKey = rightChild.getKey() + "";
        }

        if (parent != null) {
            pKey = parent.getKey() + "";
        }

        return "Node [parent=" + pKey + ", leftChild=" + lKey + ", rightChild=" + rKey
                + ", color=" + color
                + ", key=" + key + ", value=" + value + "]";
    }

}
