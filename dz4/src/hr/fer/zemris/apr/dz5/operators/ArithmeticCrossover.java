package hr.fer.zemris.apr.dz5.operators;

import java.util.Random;

public class ArithmeticCrossover implements ICrossover {
    @Override
    public double[] crossover(double[] parent1, double[] parent2) {
        double[] child = new double[parent1.length];

        Random random = new Random();

        for (int i = 0; i < parent1.length; i++) {
            double a = random.nextDouble();
            child[i] = a * parent1[i] + (1 - a) * parent2[i];
        }

        return child;
    }
}
