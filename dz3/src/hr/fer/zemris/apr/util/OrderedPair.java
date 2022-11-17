package hr.fer.zemris.apr.util;

import hr.fer.zemris.apr.linear.Vector;

public class OrderedPair {

    private Vector x;
    private double y;

    public OrderedPair(Vector x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector getX() {
        return x;
    }

    public void setX(Vector x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
