package hr.fer.zemris.apr.dz5.demo;

import hr.fer.zemris.apr.dz5.algorithm.Solution;
import hr.fer.zemris.apr.dz5.algorithm.SteadyStateAlgorithm;
import hr.fer.zemris.apr.dz5.functions.ObjectiveFunction;
import hr.fer.zemris.apr.dz5.functions.impl.Function6;
import hr.fer.zemris.apr.dz5.functions.impl.Function7;
import hr.fer.zemris.apr.dz5.operators.*;

import java.util.Arrays;

public class Second {

    public static void main(String[] args) {

        ICrossover arithmetic = new ArithmeticCrossover();
        IMutation uniformMutationFloatingPoint = new UniformMutationFloatingPoint();

        int[] dimensions = new int[]{1, 3, 6, 10};

        for (int dimension : dimensions) {
            System.out.println("\nFunkcija 6, rješenje dimenzije " + dimension);
            ObjectiveFunction function6 = new Function6();
            SteadyStateAlgorithm ga5 = new SteadyStateAlgorithm(function6, 50, dimension, -20, 20, 2, 0.4, false, 200000, uniformMutationFloatingPoint, arithmetic, false);
            Solution solution5 = ga5.run();
            System.out.println("x = " + Arrays.toString(solution5.getValues()) + ",\t f(x) = " + solution5.getFitness());

            //funkcija 7
            System.out.println("Funkcija 7, rješenje dimenzije " + dimension);
            ObjectiveFunction function7 = new Function7();
            SteadyStateAlgorithm ga7 = new SteadyStateAlgorithm(function7, 50, dimension, -20, 20, 3, 0.4, false, 200000, uniformMutationFloatingPoint, arithmetic, false);
            Solution solution7 = ga7.run();
            System.out.println("x = " + Arrays.toString(solution7.getValues()) + ",\t f(x) = " + solution7.getFitness());
        }
    }
}