package hr.fer.zemris.apr.optimization;

import hr.fer.zemris.apr.functions.ObjectiveFunction;
import hr.fer.zemris.apr.vector.Vector;

import java.util.Scanner;

public class CoordinateSearch {

    public static final double EPSILON = 10E-6;

    public Vector coordinateSearch(ObjectiveFunction f, Vector x0) {
        return coordinateSearch(f, EPSILON, x0);
    }

    public Vector coordinateSearch(ObjectiveFunction function, double epsilon, Vector x0) {
        Vector x = x0.copy();
        Vector xs;
        GoldenRatio gr = new GoldenRatio();
        do {
            xs = x.copy();
            for (int i = 0; i < x0.getDimension(); i++) {
                ObjectiveFunction g = new WrapFunction(x, function, i);
                double lambdaMin = gr.goldenSection(g, false, 0, new double[]{x.get(i)});
                x.set(i, x.get(i) + lambdaMin);
            }
        } while (x.nSub(xs).norm() <= EPSILON);

        return x;
    }

    private class WrapFunction extends ObjectiveFunction {

        private Vector x;
        private ObjectiveFunction f;
        private int index;

        public WrapFunction(Vector x, ObjectiveFunction f, int index) {
            this.x = x;
            this.f = f;
            this.index = index;
        }

        @Override
        public double calculate(Vector v) {
            Vector newVector = x.copy();
            newVector.set(index, newVector.get(index) + v.get(0));
            return f.calculate(newVector);
        }
    }
    public Vector coordinateSearch(ObjectiveFunction function) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Unesite vrijednost epsilon:");
        double eps = Double.parseDouble(sc.nextLine().strip());
        System.out.println("Unesite početnu točku ili interval:");
        String[] start = sc.nextLine().strip().split("\\s+");
        double[] point = new double[start.length];
        double[] d = new double[start.length];
        for (int i = 0; i < start.length; i++) {
            point[i] = Double.parseDouble(start[i]);
        }

        return coordinateSearch(function, eps, new Vector(true, point));
    }
}
