package hr.fer.zemris.apr.function.impl;

import hr.fer.zemris.apr.function.ObjectiveFunction;
import hr.fer.zemris.apr.linear.Matrix;
import hr.fer.zemris.apr.linear.Vector;

import java.util.Arrays;

public class Function1 extends ObjectiveFunction {

    public Function1() {
        super(new Vector(true, new double[]{-1.9, 2}), new Vector(true, new double[]{1, 1}));
    }

    @Override
    public double calculate(Vector x) {
        if (hashedResults.containsKey(x.toString())) {
            return hashedResults.get(x.toString());
        }
        resultCount++;
        double result = 100 * Math.pow((x.get(1) - x.get(0) * x.get(0)), 2) + Math.pow((1 - x.get(0)), 2);
        hashedResults.put(x.toString(), result);

        return result;
    }

    @Override
    public Vector gradient(Vector x) {
        gradientCount++;

        return new Vector(true, new double[]{400 * Math.pow(x.get(0), 3) - 400 * x.get(0) * x.get(1) + 2 * x.get(0) - 2, 200 * x.get(1) - 200 * x.get(0) * x.get(0)});
    }

    @Override
    public Matrix hessian(Vector x) {
        hessianCount++;
        double[][] values = new double[][] {{-400 * x.get(1) + 1200 * x.get(0) * x.get(0) + 2, -400 * x.get(0)}, {-400 * x.get(0), 200}};

        return new Matrix(x.getDimension(), x.getDimension(), values, true);
    }
}
