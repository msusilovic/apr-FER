package hr.fer.zemris.apr.optimization;

import hr.fer.zemris.apr.functions.ObjectiveFunction;
import hr.fer.zemris.apr.vector.Vector;
import hr.fer.zemris.apr.util.Interval;

import java.util.Scanner;

public class GoldenRatio {

    private static final double K = 0.5 * (Math.sqrt(5) - 1);
    private static final double EPSILON = 10e-6;
    private static final double H = 0.1;

    private Interval unimodal(double x0, ObjectiveFunction function) {
        double left = x0 - H;
        double right = x0 + H;
        double m = x0;
        int step = 1;

        double fm = function.calculate(new Vector(true, new double[]{x0}));
        double fl = function.calculate(new Vector(true, new double[]{left}));
        double fr = function.calculate(new Vector(true, new double[]{right}));

        if (fm < fr && fm < fl) {
            return new Interval(left, right);
        } else if (fm > fr) {
            do {
                left = m;
                m = right;
                fm = fr;
                right = x0 + H * (step *= 2);
                fr = function.calculate(new Vector(true, new double[]{right}));
            } while (fm > fr);
        } else {
            do {
                right = m;
                m = left;
                fm = fl;
                left = x0 - H * (step *= 2);
                fl = function.calculate(new Vector(true, new double[]{left}));
            } while (fm > fl);
        }

        return new Interval(left, right);
    }

    public double goldenSection(ObjectiveFunction function, boolean verbose, double[] start) {
        return goldenSection(function, verbose, EPSILON, start);
    }
    public double goldenSection(ObjectiveFunction function, boolean verbose, double e, double[] start) {
        if (e == 0) e = EPSILON;

        Interval interval = null;
        if (start.length == 1) {
            interval = unimodal(start[0], function);
        } else {
            new Interval(start[0], start[1]);
        }
        double a = interval.getA();
        double b = interval.getB();
        double c = b - K * (b - a);
        double d = a + K * (b - a);
        double fc = function.calculate(new Vector(true, new double[]{c}));
        double fd = function.calculate(new Vector(true, new double[]{d}));

        if(verbose) {
            System.out.println("a = " + a + ", b = " + b +", c = " + c + ", d = " + d + "  \nf(c) = " + fc + ", f(d) = " + fd);
        }

        while ((b - a) > e) {
            if (fc < fd) {
                b = d;
                d = c;
                c = b - K * (b - a);
                fd = fc;
                fc = function.calculate(new Vector(true, new double[]{c}));
            } else {
                a = c;
                c = d;
                d = a + K * (b - a);
                fc = fd;
                fd = function.calculate(new Vector(true, new double[]{d}));
            }
            if(verbose) {
                System.out.println("a = " + a + ", b = " + b +", c = " + c + ", d = " + d + " \nf(c) = " + fc + ", f(d) = " + fd);
            }
        }

        return (a + b) / 2;
    }

    public double goldenSection(ObjectiveFunction f, boolean verbose) {
        double eps = 0;
        Scanner sc = new Scanner(System.in);
        System.out.println("Unesite vrijednost epsilon:");
        eps = Double.parseDouble(sc.nextLine().strip());
        System.out.println("Unesite početnu točku ili interval:");
        String[] start = sc.nextLine().strip().split("\\s+");
        if (start.length == 1) {
            return goldenSection(f, verbose, eps, new double[] {Double.parseDouble(start[0].strip())});
        } else if (start.length == 2) {
            return goldenSection(f, verbose, eps, new double[] {Double.parseDouble(start[0].strip()), Double.parseDouble(start[1].strip())});
        } else {
            throw new IllegalArgumentException();
        }
    }
}
