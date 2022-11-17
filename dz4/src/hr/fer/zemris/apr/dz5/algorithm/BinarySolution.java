package hr.fer.zemris.apr.dz5.algorithm;

import hr.fer.zemris.apr.dz5.functions.ObjectiveFunction;

import java.util.Arrays;
import java.util.Random;

public class BinarySolution implements Solution {

    private double[] chromosome;
    private int[] points;
    private double[] values;
    private double fitness;
    private double lowerBound;
    private double upperBound;
    private int n;
    private int N;

    public BinarySolution(double lowerBound, double upperBound, int p, int N, ObjectiveFunction function) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.N = N;
        n = (int) Math.ceil(Math.log(Math.floor(1 + (upperBound - lowerBound) * Math.pow(10, p))) / Math.log(2));
        chromosome = new double[n * N];
        //polje integera
        Random random = new Random();
        points = new int[N];
        for (int i = 0; i < N; i++) {
            points[i] = random.nextInt((int) Math.pow(2, n) - 1);
        }

        for (int i = 0; i < N; i++) {
            double[] bits = new double[n];
            String str = Integer.toBinaryString(points[i]);
            int indexBits = bits.length - 1;
            for (int j = str.length() - 1; j >= 0; j--) {
                bits[indexBits] = Double.parseDouble(str.substring(j, j + 1));
                indexBits--;
            }

            for (int j = 0; j < n; j++) {
                chromosome[i * n + j] = bits[j];
            }
        }
        setPoints();
        setValues();

        setFitness(function.calculate(values));
    }

    public BinarySolution(double[] chromosome, ObjectiveFunction function, double lowerBound, double upperBound, int N, int p) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.chromosome = chromosome;
        this.N = N;
        n = (int) Math.ceil(Math.log(Math.floor(1 + (upperBound - lowerBound) * Math.pow(10, p))) / Math.log(2));
        setPoints();
        setValues();
        setFitness(function.calculate(values));
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
        return this.values;
    }

    public double getLowerBound() {
        return lowerBound;
    }

    public double getUpperBound() {
        return upperBound;
    }

    public void setValues() {
        values = new double[N];
        for (int i = 0; i < N; i++) {
            values[i] = lowerBound + points[i] * (upperBound - lowerBound) / ((Math.pow(2, n)) - 1);
        }
    }

    public void setPoints() {

        points = new int[N];
        for (int i = 0; i < N; i++) {
            boolean[] bits = new boolean[n];
            for (int j = 0; j < n; j++) {
                bits[j] = chromosome[n * i + j] == 1 ? true : false;
            }
            int n = 0;
            for (boolean b : bits)
                n = (n << 1) | (b ? 1 : 0);
            points[i] = n;
        }
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
