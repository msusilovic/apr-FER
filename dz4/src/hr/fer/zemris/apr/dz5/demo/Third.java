package hr.fer.zemris.apr.dz5.demo;

import hr.fer.zemris.apr.dz5.algorithm.Solution;
import hr.fer.zemris.apr.dz5.algorithm.SteadyStateAlgorithm;
import hr.fer.zemris.apr.dz5.functions.ObjectiveFunction;
import hr.fer.zemris.apr.dz5.functions.impl.Function6;
import hr.fer.zemris.apr.dz5.functions.impl.Function7;
import hr.fer.zemris.apr.dz5.operators.*;

import java.util.Arrays;

public class Third {

    public static void main(String[] args) {
        ICrossover arithmetic = new ArithmeticCrossover();
        IMutation uniformMutationFloatingPoint = new UniformMutationFloatingPoint();

        double[][] m6 = new double[4][10];
        double[][] m7 = new double[4][10];

        for (int i = 0; i < 10; i++) {

            //funkcija 6, 3, fp
            ObjectiveFunction function6 = new Function6();
            SteadyStateAlgorithm ga1 = new SteadyStateAlgorithm(function6, 50, 3, -50, 150,  2, 0.1, false, 1000, uniformMutationFloatingPoint, arithmetic, false);
            Solution solution1 = ga1.run();
            m6[0][i] = solution1.getFitness();

            //funkcija 6, 3, binary
            function6 = new Function6();
            SteadyStateAlgorithm ga2 = new SteadyStateAlgorithm(function6, 50, 3, -50, 150, 4, 0.1, true, 1000, new UniformMutationBinary(), new UniformCrossover(), false);
            Solution solution2 = ga2.run();
            m6[1][i] = solution2.getFitness();

            //funkcija 6
            function6 = new Function6();
            SteadyStateAlgorithm ga3 = new SteadyStateAlgorithm(function6, 50, 6, -50, 150, 2, 0.1, false, 1000, uniformMutationFloatingPoint, arithmetic, false);
            Solution solution3 = ga3.run();
            m6[2][i] = solution3.getFitness();

            function6 = new Function6();
            SteadyStateAlgorithm ga4 = new SteadyStateAlgorithm(function6, 50, 6, -50, 150,  4, 0.1, true, 1000, new UniformMutationBinary(), new UniformCrossover(), false);
            Solution solution4 = ga4.run();
            m6[3][i] = solution4.getFitness();

            //funkcija 7
            ObjectiveFunction function7 = new Function7();
            SteadyStateAlgorithm ga5 = new SteadyStateAlgorithm(function7, 50, 3, -50, 150, 3, 0.1, false, 100000, uniformMutationFloatingPoint, arithmetic, false);
            Solution solution5 = ga5.run();
            m7[0][i] = solution5.getFitness();

            function7 = new Function7();
            SteadyStateAlgorithm ga6 = new SteadyStateAlgorithm(function6, 50, 3, -50, 150,  4, 0.1, true, 100000, new UniformMutationBinary(), new UniformCrossover(), false);
            Solution solution6 = ga6.run();
            m7[1][i] = solution6.getFitness();

            //funkcija 7
            ObjectiveFunction function8 = new Function7();
            SteadyStateAlgorithm ga7 = new SteadyStateAlgorithm(function7, 50, 3, -50, 150,  3, 0.1, false, 100000, uniformMutationFloatingPoint, arithmetic, false);
            Solution solution7 = ga7.run();
            m7[2][i] = solution7.getFitness();

            function7 = new Function7();
            SteadyStateAlgorithm ga8 = new SteadyStateAlgorithm(function6, 50, 3, -50, 150,  4, 0.1, true, 100000, new UniformMutationBinary(), new UniformCrossover(), false);
            Solution solution8 = ga8.run();
            m7[3][i] = solution8.getFitness();
        }

        System.out.println("Funkcija 6:");
        String[] functions= new String[]{"FP_3", "BIN_3", "FP_6", "BIN_6" };
        for (int i = 0; i < m6.length; i++) {
            double[] m = m6[i];
            Arrays.sort(m);
            System.out.println(functions[i] + " mean = " + (m[4] + m[5]) / 2);
        }

        System.out.println("\nFunkcija 7:");
        for (int i = 0; i < m7.length; i++) {
            double[] m = m7[i];
            Arrays.sort(m);
            System.out.println(functions[i] + " mean = " + (m[4] + m[5]) / 2);
        }
    }
}
