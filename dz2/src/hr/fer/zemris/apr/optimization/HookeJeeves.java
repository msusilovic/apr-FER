package hr.fer.zemris.apr.optimization;

import hr.fer.zemris.apr.functions.ObjectiveFunction;
import hr.fer.zemris.apr.vector.Vector;

import java.util.Scanner;

public class HookeJeeves {

    public static final double EPSILON = 10E-6;
    public static final double dx = 0.5;

    public Vector hookeJeeves(ObjectiveFunction function, Vector x0) {
        return hookeJeeves(function, false, x0, EPSILON);
    }

    public Vector hookeJeeves(ObjectiveFunction function, boolean verbose, Vector x0, double epsilon) {
        if (epsilon == 0) {
            epsilon = EPSILON;
        }

        double deltaX = dx;
        Vector xp = x0.copy();
        Vector xb = x0.copy();

        do {
            Vector xn = search(function, xp, deltaX);
            double fb = function.calculate(xb);
            double fp = function.calculate(xp);
            double fn = function.calculate(xn);
            if (verbose) {
                System.out.println("xb = " + xb + ", f(xb) = " + fb);
                System.out.println("xp = " + xp + ", f(xp) = " + fp);
                System.out.println("xn = " + xn + ", f(xn) = " + fn + "\n");
            }

            if (fn < fb) {
                xp = xn.nScalarMultiply(2).nSub(xb);
                xb = xn.copy();
            } else {
                deltaX /= 2;
                xp = xb.copy();
            }
        } while (deltaX >= epsilon);

        return xb;
    }

    private Vector search(ObjectiveFunction f, Vector xp, double dx) {
        Vector x = xp.copy();
        for (int i = 0; i < xp.getDimension(); i++) {
            double P = f.calculate(x);
            x.set(i, x.get(i) + dx);
            double N = f.calculate(x);
            if (N > P) {
                x.set(i, x.get(i) - 2 * dx);
                N = f.calculate(x);
                if (N > P)
                    x.set(i, x.get(i) + dx);
            }
        }

        return x;
    }

    public Vector hookeJeeves(ObjectiveFunction function) {
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

        return hookeJeeves(function, false, new Vector(true, point), eps);
    }
}
