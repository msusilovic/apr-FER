package hr.fer.zemris.apr.dz5.algorithm;

import hr.fer.zemris.apr.dz5.functions.ObjectiveFunction;

import java.util.Random;

public class FloatingPointSolution implements Solution {

    private double[] chromosome;
    private double fitness;

    public FloatingPointSolution(double[] chromosome, double fitness) {
        this.chromosome = chromosome;
        this.fitness = fitness;
    }

    public FloatingPointSolution(double lowerBound, double upperBound, int n, ObjectiveFunction function) {
        Random random = new Random();
        this.chromosome = new double[n];
        for (int i = 0; i < n; i++) {
            this.chromosome[i] = lowerBound + random.nextDouble() * (upperBound - lowerBound);
        }
        this.fitness = function.calculate(chromosome);
    }

    @Override
    public double[] getChromosome() {
        return chromosome;
    }

    @Override
    public void setChromosome(double[] chromosome) {
        this.chromosome = chromosome;
    }

    @Override
    public double getFitness() {
        return this.fitness;
    }

    @Override
    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    @Override
    public double[] getValues() {
        return this.chromosome;
    }

    @Override
    public int compareTo(Solution solution) {
        if (this.fitness < solution.getFitness()) {
            return -1;
        }
        if (this.fitness > solution.getFitness()) {
            return 1;
        }
        return 0;
    }
}
