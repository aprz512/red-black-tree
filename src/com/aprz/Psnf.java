package com.aprz;


class Psnf {
    private Node p;
    private Node s;
    private Node n;
    private Node f;

    Psnf(Node p, Node s, Node n, Node f) {
        this.p = p;
        this.s = s;
        this.n = n;
        this.f = f;
    }

    // public void setF(Node f) {
    //     this.f = f;
    // }

    // public void setN(Node n) {
    //     this.n = n;
    // }

    // public void setP(Node p) {
    //     this.p = p;
    // }

    // public void setS(Node s) {
    //     this.s = s;
    // }

    public Node getS() {
        return s;
    }

    public Node getF() {
        return f;
    }

    public Node getN() {
        return n;
    }

    public Node getP() {
        return p;
    }
}