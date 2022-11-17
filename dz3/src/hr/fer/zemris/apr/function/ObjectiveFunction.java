package hr.fer.zemris.apr.function;

import hr.fer.zemris.apr.linear.Matrix;
import hr.fer.zemris.apr.linear.Vector;

import java.util.HashMap;
import java.util.Map;

public abstract class ObjectiveFunction {

    protected int resultCount = 0;
    protected int gradientCount = 0;
    protected int hessianCount = 0;

    private Vector x0;
    private Vector xMin;
    protected Map<String, Double> hashedResults = new HashMap<>();

    public ObjectiveFunction() {
    }

    public ObjectiveFunction(Vector x0, Vector xMin) {
        this.x0 = x0;
        this.xMin = xMin;
    }

    public int getResultCount() {
        return resultCount;
    }

    public int getGradientCount() {
        return gradientCount;
    }

    public int getHessianCount() {
        return hessianCount;
    }

    public abstract double calculate(Vector x);

    public Vector gradient(Vector x) {
        return null;
    }

    public Matrix hessian(Vector x) {
        return null;
    }

    public Vector getX0() {
        return x0;
    }

    public Vector getXMin() {
        return xMin;
    }

    public void resetMap() {
        this.hashedResults = new HashMap<>();
    }
}
