package hr.fer.zemris.apr.dz5.demo;

import hr.fer.zemris.apr.dz5.matrix.Matrix;
import hr.fer.zemris.apr.dz5.numericalIntegration.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class First {

    public static void main(String[] args) throws IOException {
        Matrix A = Matrix.parseFromFile("matrices/A1.txt");
        Matrix x = Matrix.parseFromFile("matrices/x1.txt");

        List<Double> x1 = new ArrayList<>();
        List<Double> x2 = new ArrayList<>();
        double t = 0;
        double T = 0.01;
        double tMax = 10;

        double x1first = x.get(0, 0);
        double x2first = x.get(1, 0);

        while (t < tMax) {
            x1.add(x1first * Math.cos(t) + x2first * Math.sin(t));
            x2.add(x2first * Math.cos(t) - x1first * Math.sin(t));

            t += T;
        }

        System.out.println("EULER:");
        Euler euler = new Euler(0.01, 10);
        euler.run(A, null, x, -1, 50);
        printError(x1, x2, euler, "Eulerov postupak");

        System.out.println("\nOBRNUTI EULER:");
        BackwardEuler backwardEuler = new BackwardEuler(0.01, 10);
        backwardEuler.run(A, null, x, -1, 50);
        printError(x1, x2, backwardEuler, "obrnuti Eulerov postupak");

        System.out.println("\nTRAPEZNI POSTUPAK:");
        Trapezoid trapezoid = new Trapezoid(0.01, 10);
        trapezoid.run(A, null, x, -1, 50);
        printError(x1, x2, trapezoid, "trapezni postupak");

        System.out.println("\nRUNGE-KUTTA:");
        RungeKutta rungeKutta = new RungeKutta(0.01, 10);
        rungeKutta.run(A, null, x, -1, 50);
        printError(x1, x2, rungeKutta, "Runge-Kutta postupak");


        System.out.println("\nPECE (prediktor EULER, korektor TRAPEZNI):");
        euler = new Euler(0.01, 10);
        trapezoid = new Trapezoid(0.01, 10);
        PredictorCorrector pece = new PredictorCorrector(euler, trapezoid, 0.01, 10, 1);
        pece.run(A, null, x, -1, 50);
        printError(x1, x2, backwardEuler, "PECE postupak");

        System.out.println("\nPECE2 (prediktor EULER, korektor OBRNUTI EULER):");
        euler = new Euler(0.01, 10);
        backwardEuler = new BackwardEuler(0.01, 10);
        PredictorCorrector pece2 = new PredictorCorrector(euler, backwardEuler, 0.01, 10, 2);
        pece2.run(A, null, x, -1, 50);
        printError(x1, x2, pece2, "PE(CE)2");

    }

    private static void printError(List<Double> x1, List<Double> x2, NumericalIntegrationMethod method, String name) {
        double sum1 = 0;
        double sum2 = 0;

        for (int i = 0; i < x1.size(); i++) {
            sum1 += Math.abs(x1.get(i) - method.getX1().get(i));
            sum2 += Math.abs(x2.get(i) - method.getX2().get(i));
        }

        System.out.println("\nPogreška x1 za " + name + ": " + sum1);
        System.out.println("Pogreška x2 za " + name + ": " + sum2 + "\n");
    }
}
