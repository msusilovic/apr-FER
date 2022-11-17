package hr.fer.zemris.apr.dz5.operators;

import java.util.Random;

public class SinglePointCrossover implements ICrossover {
    @Override
    public double[] crossover(double[] parent1, double[] parent2) {
        double[] child = new double[parent1.length];
        Random random = new Random();
        double crossoverPointIndex = random.nextInt(parent1.length);
        for (int i = 0; i < parent1.length; i++) {
            if (i <= crossoverPointIndex) {
                child[i] = parent1[i];
            } else {
                child[i] = parent2[i];
            }
        }

        return child;
    }
}
