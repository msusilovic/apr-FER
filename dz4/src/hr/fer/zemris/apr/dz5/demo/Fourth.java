package hr.fer.zemris.apr.dz5.demo;

import hr.fer.zemris.apr.dz5.algorithm.Solution;
import hr.fer.zemris.apr.dz5.algorithm.SteadyStateAlgorithm;
import hr.fer.zemris.apr.dz5.functions.ObjectiveFunction;
import hr.fer.zemris.apr.dz5.functions.impl.Function6;
import hr.fer.zemris.apr.dz5.operators.ArithmeticCrossover;
import hr.fer.zemris.apr.dz5.operators.ICrossover;
import hr.fer.zemris.apr.dz5.operators.IMutation;
import hr.fer.zemris.apr.dz5.operators.UniformMutationFloatingPoint;

import java.util.*;

public class Fourth {

    public static void main(String[] args) {

        ICrossover arithmetic = new ArithmeticCrossover();
        IMutation uniformMutationFloatingPoint = new UniformMutationFloatingPoint();

        double[][] mr = new double[4][10];
        double[] mutationRates = new double[]{0.1, 0.3, 0.6, 0.9};

        for (int i = 0; i < 10; i++) {
           // StringBuilder sb = new StringBuilder();
            for (int r = 0; r < mutationRates.length; r++) {
                ObjectiveFunction function6 = new Function6();
                SteadyStateAlgorithm ga1 = new SteadyStateAlgorithm(function6, 50, 2, -50, 150, 4, mutationRates[r], false, 1000, uniformMutationFloatingPoint, arithmetic, false);
                Solution solution1 = ga1.run();
                mr[r][i] = solution1.getFitness();
                //sb.append(String.format("%.4f", solution1.getFitness()) + (mutationRates[r] == 0.9 ? "" : ","));
            }
            //System.out.println(sb.toString());
        }

        for(int r = 0; r < mr.length; r++) {
            double[] fitness = mr[r];
            Arrays.sort(fitness);
            System.out.println("pm = " + mutationRates[r] + " mean = " + (fitness[4] + fitness[5]) / 2);
        }

        double[][] mp = new double[4][10];
        int[] popSizes = new int[]{30, 50, 100, 200};

        for (int i = 0; i < 10; i++) {
            //StringBuilder sb = new StringBuilder();
            for (int r = 0; r < popSizes.length; r++) {
                ObjectiveFunction function6 = new Function6();
                SteadyStateAlgorithm ga1 = new SteadyStateAlgorithm(function6, popSizes[r], 2, -50, 150, 4, 0.4, false, 1000, uniformMutationFloatingPoint, arithmetic, false);
                Solution solution1 = ga1.run();
                mp[r][i] = solution1.getFitness();
             //   sb.append(String.format("%.4f", solution1.getFitness()) + (popSizes[r] == 200 ? "" : ","));
            }
           // System.out.println(sb.toString());
        }

        System.out.println("\n");

        for(int r = 0; r < mp.length; r++) {
            double[] fitness = mp[r];
            Arrays.sort(fitness);
            System.out.println("popSize = " + popSizes[r] + " mean = " + (fitness[4] + fitness[5]) / 2);
        }
    }
}
