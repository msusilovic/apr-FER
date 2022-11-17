package hr.fer.zemris.apr.dz5.demo;

import hr.fer.zemris.apr.dz5.algorithm.Solution;
import hr.fer.zemris.apr.dz5.algorithm.SteadyStateAlgorithm;
import hr.fer.zemris.apr.dz5.functions.ObjectiveFunction;
import hr.fer.zemris.apr.dz5.functions.impl.Function1;
import hr.fer.zemris.apr.dz5.functions.impl.Function3;
import hr.fer.zemris.apr.dz5.functions.impl.Function6;
import hr.fer.zemris.apr.dz5.functions.impl.Function7;
import hr.fer.zemris.apr.dz5.operators.*;

import java.util.Arrays;

public class First {

    public static void main(String[] args) {

        ICrossover arithmetic = new ArithmeticCrossover();
        ICrossover uniformCrossover = new UniformCrossover();
        IMutation uniformMutationFloatingPoint = new UniformMutationFloatingPoint();
        IMutation uniformMutationBinary = new UniformMutationBinary();

        //funkcija 1
        System.out.println("Funkcija 1, prikaz s pomičnom točkom:");
        ObjectiveFunction function1 = new Function1();
        SteadyStateAlgorithm ga1 = new SteadyStateAlgorithm(function1, 50, 2, -50, 50, 2, 0.3, false, 1000000, uniformMutationFloatingPoint, arithmetic, false);
        Solution solution1 = ga1.run();
        System.out.println("x = " + Arrays.toString(solution1.getValues()) + ",\t f(x) = " + solution1.getFitness());

        System.out.println("\nFunkcija 1, binarni prikaz:");
        function1 = new Function1();
        SteadyStateAlgorithm ga2 = new SteadyStateAlgorithm(function1, 50, 2, -50, 50, 3, 0.2, true, 500000, uniformMutationBinary, uniformCrossover, false);
        Solution solution2 = ga2.run();
        System.out.println("x = " + Arrays.toString(solution2.getValues()) + ",\t f(x) = " + solution2.getFitness());

        //funkcija 3
        System.out.println("\nFunkcija 3, prikaz s pomičnom točkom:");
        ObjectiveFunction function3 = new Function3();
        SteadyStateAlgorithm ga3 = new SteadyStateAlgorithm(function3, 50, 5, -50, 50, 3, 0.4, false, 500000, uniformMutationFloatingPoint, arithmetic, false);
        Solution solution3 = ga3.run();
        System.out.println("x = " + Arrays.toString(solution3.getValues()) + ",\t f(x) = " + solution3.getFitness());

        System.out.println("\nFunkcija 3, binarni prikaz");
        function3 = new Function3();
        SteadyStateAlgorithm ga4 = new SteadyStateAlgorithm(function3, 50, 5, -50, 50, 3, 0.2, true, 500000, uniformMutationBinary, uniformCrossover, false);
        Solution solution4 = ga4.run();
        System.out.println("x = " + Arrays.toString(solution4.getValues()) + ",\t f(x) = " + solution4.getFitness());

        //funkcija 6
        System.out.println("\nFunkcija 6, prikaz s pomičnom točkom:");
        ObjectiveFunction function6 = new Function6();
        SteadyStateAlgorithm ga5 = new SteadyStateAlgorithm(function6, 50, 2, -50, 50, 2, 0.4, false, 200000, uniformMutationFloatingPoint, arithmetic, false);
        Solution solution5 = ga5.run();
        System.out.println("x = " + Arrays.toString(solution5.getValues()) + ",\t f(x) = " + solution5.getFitness());

        System.out.println("\nFunkcija 6, binarni prikaz:");
        function6 = new Function6();
        SteadyStateAlgorithm ga6 = new SteadyStateAlgorithm(function6, 50, 2, -50, 50, 3, 0.3, true, 100000, uniformMutationBinary, uniformCrossover, false);
        Solution solution6 = ga6.run();
        System.out.println("x = " + Arrays.toString(solution6.getValues()) + ",\t f(x) = " + solution6.getFitness());

        //funkcija 7
        System.out.println("\nFunkcija 7, prikaz s pomičnom točkom:");
        ObjectiveFunction function7 = new Function7();
        SteadyStateAlgorithm ga7 = new SteadyStateAlgorithm(function7, 100, 2, -50, 50, 3, 0.4, false, 200000, uniformMutationFloatingPoint, arithmetic, false);
        Solution solution7 = ga7.run();
        System.out.println("x = " + Arrays.toString(solution7.getValues()) + ",\t f(x) = " + solution7.getFitness());

        //funkcija 7
        System.out.println("\nFunkcija 7, binarni prikaz:");
        function7 = new Function7();
        SteadyStateAlgorithm ga8 = new SteadyStateAlgorithm(function7, 100, 2, -50, 50, 3, 0.4, true, 200000, uniformMutationBinary, uniformCrossover, false);
        Solution solution8 = ga8.run();
        System.out.println("x = " + Arrays.toString(solution8.getValues()) + ",\t f(x) = " + solution8.getFitness());
    }

}
