package hr.fer.zemris.apr.dz5.operators;

import java.util.Random;

public class UniformCrossover implements ICrossover {

    @Override
    public double[] crossover(double[] parent1, double[] parent2) {
        double[] child = new double[parent1.length];

        Random random = new Random();
        for (int i = 0; i < parent1.length; i++) {
            child[i] = random.nextDouble() < 0.5 ? parent1[i] : parent2[i];
        }

        return child;
    }
}
