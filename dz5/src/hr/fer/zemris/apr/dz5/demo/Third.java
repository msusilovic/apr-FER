package hr.fer.zemris.apr.dz5.demo;

import hr.fer.zemris.apr.dz5.matrix.Matrix;
import hr.fer.zemris.apr.dz5.numericalIntegration.*;

import java.io.IOException;

public class Third {

    public static void main(String[] args) throws IOException {
        Matrix A = Matrix.parseFromFile("matrices/A3.txt");
        Matrix B = Matrix.parseFromFile("matrices/B3.txt");
        Matrix x = Matrix.parseFromFile("matrices/x3.txt");

        System.out.println("EULER:");
        Euler euler = new Euler(0.01, 10);
        euler.run(A, B, x, 0, 20);

        System.out.println("\nOBRNUTI EULER:");
        BackwardEuler backwardEuler = new BackwardEuler(0.01, 10);
        backwardEuler.run(A, B, x, 0, 20);

        System.out.println("\nTRAPEZNI POSTUPAK:");
        Trapezoid trapezoid = new Trapezoid(0.01, 10);
        trapezoid.run(A, B, x, 0, 20);

        System.out.println("\nRUNGE-KUTTA:");
        RungeKutta rungeKutta = new RungeKutta(0.01, 10);
        rungeKutta.run(A, B, x, 0, 20);

        System.out.println("\nPECE (prediktor EULER, korektor TRAPEZNI):");
        euler = new Euler(0.01, 10);
        trapezoid = new Trapezoid(0.01, 10);
        PredictorCorrector pc = new PredictorCorrector(euler, trapezoid, 0.01, 10, 1);
        pc.run(A, B, x, 0, 20);

        System.out.println("\nPECE2 (prediktor EULER, korektor OBRNUTI EULER):");
        euler = new Euler(0.01, 10);
        backwardEuler = new BackwardEuler(0.01, 10);
        PredictorCorrector pc2 = new PredictorCorrector(euler, backwardEuler, 0.01, 10, 2);
        pc2.run(A, B, x, 0, 20);
    }
}
