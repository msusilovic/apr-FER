package hr.fer.zemris.apr.optimization;

import hr.fer.zemris.apr.function.ObjectiveFunction;
import hr.fer.zemris.apr.linear.Vector;

public class GradientDescent {

    private static final double EPSILON = 10E-6;

    private GoldenRatio goldenRatio = new GoldenRatio();

    public Vector calculate(ObjectiveFunction function, Vector x0, boolean adaptive) {
        double bestSolution = Double.MAX_VALUE;
        int iterations = 0;
        Vector x = x0.copy();
        Vector xPrev;
        do {
            xPrev = x.copy();
            if (function.calculate(x) >= bestSolution) {
                iterations++;
                if (iterations == 100) {
                    System.out.println("Postupak divergira.");
                    break;
                }
            } else {
                bestSolution = function.calculate(x);
                iterations = 0;
            }
            Vector F = function.gradient(x);
            Vector v = F.nNormalize().nScalarMultiply(-1);
            if (adaptive) {
                ObjectiveFunction lambdaFunction = new ObjectiveFunction() {
                    @Override
                    public double calculate(Vector lambda) {
                        return function.calculate(x.nAdd(v.nScalarMultiply(lambda.get(0))));
                    }
                };
                double lambda = goldenRatio.goldenSection(lambdaFunction, false, new double[]{1});
                x.add(v.scalarMultiply(lambda));
            } else {
                x.add(v);
            }

        } while (x.nSub(xPrev).norm() >= EPSILON);

        return x;
    }

}
