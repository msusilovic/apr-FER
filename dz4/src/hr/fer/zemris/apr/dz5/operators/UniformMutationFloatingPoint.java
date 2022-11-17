package hr.fer.zemris.apr.dz5.operators;

import java.util.Random;

public class UniformMutationFloatingPoint implements IMutation {

    @Override
    public double[] mutate(double[] chromosome, double pm, double lowerBound, double upperBound) {
        Random random = new Random();

        for (int i = 0; i < chromosome.length; i++) {
            if (random.nextDouble() < pm) {
                chromosome[i] = lowerBound + random.nextDouble() * (upperBound - lowerBound);
            }
        }

        return chromosome;
    }

}
