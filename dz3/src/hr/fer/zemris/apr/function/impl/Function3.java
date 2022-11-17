package hr.fer.zemris.apr.function.impl;

import hr.fer.zemris.apr.function.ObjectiveFunction;
import hr.fer.zemris.apr.linear.Matrix;
import hr.fer.zemris.apr.linear.Vector;

public class Function3 extends ObjectiveFunction {

    public Function3() {
        super(new Vector(true, new double[]{0, 0}), new Vector(true, new double[]{2, -3}));
    }

    @Override
    public double calculate(Vector x) {
        if (hashedResults.containsKey(x.toString())) {
            return (hashedResults.get(x.toString()));
        }
        resultCount++;
        double result = Math.pow(x.get(0) - 2, 2) + Math.pow(x.get(1) + 3, 2);
        hashedResults.put(x.toString(), result);

        return result;
    }

    @Override
    public Vector gradient(Vector x) {
        gradientCount++;

        return new Vector(true, new double[] {2 * x.get(0) - 4, 2 * x.get(1) + 6});
    }

    @Override
    public Matrix hessian(Vector x) {
        hessianCount++;

        return new Matrix(x.getDimension(), x.getDimension(), new double[][] {{2, 0}, {0, 2}}, true);
    }
}
