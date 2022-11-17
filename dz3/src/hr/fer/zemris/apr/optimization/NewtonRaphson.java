package hr.fer.zemris.apr.optimization;

import hr.fer.zemris.apr.function.ObjectiveFunction;
import hr.fer.zemris.apr.linear.Matrix;
import hr.fer.zemris.apr.linear.Vector;

public class NewtonRaphson {

    private static final double EPSILON = 10E-6;

    private GoldenRatio goldenRatio = new GoldenRatio();

    public Vector calculate(ObjectiveFunction function, Vector x0, boolean adaptive) {
        double bestSolution = Double.MAX_VALUE;
        int iterations = 0;
        Vector x = x0.copy();
        Vector deltaX;
        do {
            if (function.calculate(x) >= bestSolution) {
                iterations++;
                if (iterations == 100) {
                    System.out.println("Postupak divergira");
                    break;
                }
            } else {
                bestSolution = function.calculate(x);
                iterations = 0;
            }
            Matrix hessian = function.hessian(x);
            Matrix inv = hessian.inverse();
            deltaX = inv.nMultiply(function.gradient(x).toColumnMatrix()).toVector().scalarMultiply(-1);
            if (adaptive) {
                Vector dx = deltaX;
                ObjectiveFunction lambdaFunction = new ObjectiveFunction() {
                    @Override
                    public double calculate(Vector lambda) {
                        return function.calculate(x.nAdd(dx.nScalarMultiply(lambda.get(0))));
                    }
                };
                double lambda = goldenRatio.goldenSection(lambdaFunction, false, new double[]{1});
                Vector n = deltaX.nScalarMultiply(lambda);
                x.add(deltaX.nScalarMultiply(lambda));
            } else {
                x.add(deltaX);
            }
        } while (deltaX.norm() >= EPSILON);

        return x;
    }
}
