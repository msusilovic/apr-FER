package hr.fer.zemris.apr.function.impl;

import hr.fer.zemris.apr.function.ObjectiveFunction;
import hr.fer.zemris.apr.linear.Matrix;
import hr.fer.zemris.apr.linear.Vector;

public class Function2 extends ObjectiveFunction {

    public Function2() {
        super(new Vector(true, new double[]{0.1, 0.3}), new Vector(true, new double[]{4, 2}));
    }

    @Override
    public double calculate(Vector x) {
        if (hashedResults.containsKey(x.toString())) {
            return (hashedResults.get(x.toString()));
        }
        resultCount++;
        double result = Math.pow(x.get(0) - 4, 2) + 4 * Math.pow(x.get(1) - 2, 2);
        hashedResults.put(x.toString(), result);

        return result;
    }

    @Override
    public Vector gradient(Vector x) {
        gradientCount++;

        return new Vector(true, new double[]{2 * x.get(0) - 8, 8 * x.get(1) - 16});
    }

    @Override
    public Matrix hessian(Vector x) {
        hessianCount++;

        return new Matrix(x.getDimension(), x.getDimension(), new double[][]{{2, 0}, {0, 8}}, true);
    }

}
