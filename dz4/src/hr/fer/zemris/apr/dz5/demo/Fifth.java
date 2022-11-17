package hr.fer.zemris.apr.dz5.demo;

import hr.fer.zemris.apr.dz5.algorithm.Solution;
import hr.fer.zemris.apr.dz5.algorithm.SteadyStateAlgorithm;
import hr.fer.zemris.apr.dz5.functions.ObjectiveFunction;
import hr.fer.zemris.apr.dz5.functions.impl.Function7;
import hr.fer.zemris.apr.dz5.operators.ArithmeticCrossover;
import hr.fer.zemris.apr.dz5.operators.ICrossover;
import hr.fer.zemris.apr.dz5.operators.IMutation;
import hr.fer.zemris.apr.dz5.operators.UniformMutationFloatingPoint;

import java.util.Arrays;

public class Fifth {
    public static void main(String[] args) {
        int[] ks = new int[]{3, 5, 10, 20};

        ICrossover crossover = new ArithmeticCrossover();
        IMutation mutation = new UniformMutationFloatingPoint();
        ObjectiveFunction function7;
        double[][] m = new double[4][10];

        for (int i = 0; i < 10; i++) {
            for (int r = 0; r < ks.length; r++) {
                function7 = new Function7();
                SteadyStateAlgorithm ga = new SteadyStateAlgorithm(function7, 100, 2, -50, 150, 4, 0.07, false, 100000, mutation, crossover, false, ks[r]);
                Solution solution = ga.run();
                m[r][i] = solution.getFitness();
            }
        }

        for (int r = 0; r < m.length; r++) {
            double[] fitness = m[r];
            Arrays.sort(fitness);
            System.out.println("k = " + ks[r] + " mean = " + (fitness[4] + fitness[5]) / 2);
        }
    }
}
