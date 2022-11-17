package hr.fer.zemris.apr.util;

public class Interval {

    private double xd;
    private double xg;

    public Interval(double xd, double xg) {
        this.xd = xd;
        this.xg = xg;
    }

    public double getXd() {
        return xd;
    }

    public void setXd(double xd) {
        this.xd = xd;
    }

    public double getXg() {
        return xg;
    }

    public void setXg(double xg) {
        this.xg = xg;
    }
}
