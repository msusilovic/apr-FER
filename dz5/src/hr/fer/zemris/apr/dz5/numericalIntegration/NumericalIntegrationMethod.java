package hr.fer.zemris.apr.dz5.numericalIntegration;

import hr.fer.zemris.apr.dz5.matrix.Matrix;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class NumericalIntegrationMethod {

    protected double T;
    protected double tMax;
    protected List<Double> x1;
    protected List<Double> x2;

    public NumericalIntegrationMethod(double T, double tMax) {
        if (T > tMax) {
            throw new IllegalArgumentException();
        }
        this.T = T;
        this.tMax = tMax;

        this.x1 = new ArrayList<>();
        this.x2 = new ArrayList<>();
    }

    public abstract Matrix run(Matrix A, Matrix B, Matrix xk, int tPower, int iter) throws IOException;

    public abstract Matrix nextX(Matrix A, Matrix B, Matrix xk, double t, int tPower);

    public Matrix correction(Matrix A, Matrix B, Matrix xk, Matrix temp, double t, int tPower) {
        return null;
    }

    public List<Double> getX2() {
        return x2;
    }

    public List<Double> getX1() {
        return x1;
    }

    public void reset() {
        this.x1 = new ArrayList<>();
        this.x2 = new ArrayList<>();
    }
}
