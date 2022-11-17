package hr.fer.zemris.apr.optimization;

import hr.fer.zemris.apr.functions.ObjectiveFunction;
import hr.fer.zemris.apr.vector.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Simplex {

    private static final double EPSILON = 10E-6;
    private static final double STEP = 1;
    private static final double ALPHA = 1;
    private static final double BETA = 0.5;
    private static final double GAMMA = 2;
    private static final double SIGMA = 0.5;

    public Vector simplex(ObjectiveFunction function, boolean verbose, Vector x) {
        return simplex(function, x, verbose, EPSILON, STEP, ALPHA, BETA, GAMMA, SIGMA);
    }

    public Vector simplex(ObjectiveFunction function, boolean verbose, Vector x, double step) {
        return simplex(function, x, verbose, EPSILON, step, ALPHA, BETA, GAMMA, SIGMA);
    };

    public Vector simplex(ObjectiveFunction function) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Unesite početnu točku:");
        String[] p = scanner.nextLine().split(" ");
        double[] point = new double[p.length];
        for (int i = 0; i < p.length; i++) {
            point[i] = Double.parseDouble(p[i].strip());
        }
        System.out.println("Unesite vrijednost epsilon:");
        double epsilon = Double.parseDouble(scanner.nextLine().strip());
        System.out.println("Unesite vrijednost pomaka:");
        double step = Double.parseDouble(scanner.nextLine().strip());
        System.out.println("Unesite vrijednost alfa:");
        double alpha = Double.parseDouble(scanner.nextLine().strip());
        System.out.println("Unesite vrijednost beta:");
        double beta = Double.parseDouble(scanner.nextLine().strip());
        System.out.println("Unesite vrijednost gama:");
        double gama = Double.parseDouble(scanner.nextLine().strip());
        System.out.println("Unesite vrijednost sigma:");
        double sigma = Double.parseDouble(scanner.nextLine().strip());

        return simplex(function, new Vector(true, point), false, epsilon, step, alpha, beta, gama, sigma);
    }

    public Vector simplex(ObjectiveFunction function, Vector x, boolean verbose, double epsilon, double step, double alpha, double beta, double gamma, double sigma) {
        List<Vector> X = getSimplexPoints(x, step);
        Vector xc;
        do {
            int h = getIndexH(function, X);
            int l = getIndexL(function, X);
            xc = centroid(X, h);
            double fc = function.calculate(xc);
            if (verbose) {
                System.out.println("xc = " + xc + ", f(xc) = " + fc);
            }
            Vector xr = reflection(xc, X.get(h), alpha);

            if (function.calculate(xr) < function.calculate(X.get(l))) {
                Vector xe = expansion(xc, xr, gamma);
                if (function.calculate(xe) < function.calculate(X.get(l))) {
                    X.set(h, xe);
                } else {
                    X.set(h, xr);
                }
            } else {
                if (checkCondition(X, xr, h, function)) {
                    if (function.calculate(xr) < function.calculate(X.get(h))) {
                        X.set(h, xr);
                    }
                    Vector xk = contraction(xc, X.get(h), beta);
                    if (function.calculate(xk) < function.calculate(X.get(h))) {
                        X.set(h, xk);
                    } else {
                        moveAllPoints(X, X.get(l).copy());
                    }
                } else {
                    X.set(h, xr);
                }
            }
        } while (!checkStopCriteria(X, function, xc, epsilon));

        return xc;
    }

    private boolean checkStopCriteria(List<Vector> X, ObjectiveFunction f, Vector xc, double epsilon) {
        double sum = 0;
        double fc = f.calculate(xc);
        for (int j = 0; j < X.size(); j++) {
            sum += Math.pow(f.calculate(X.get(j)) - fc, 2);
        }

        return (Math.sqrt(sum / X.size()) <= epsilon);
    }

    private void moveAllPoints(List<Vector> X, Vector xl) {
        for (int i = 0; i < X.size(); i++) {
            X.set(i, X.get(i).nAdd(xl).scalarMultiply(0.5));
        }
    }

    private Vector contraction(Vector xc, Vector xh, double beta) {
        return xc.nScalarMultiply(1 - beta).nAdd(xh.nScalarMultiply(beta));
    }

    private double[] addPoints(double[] simplexPoint, double[] simplexPoint1) {
        double[] solution = new double[simplexPoint.length];
        for (int i = 0; i < simplexPoint.length; i++) {
            solution[i] = 0.5 * (simplexPoint[i] + simplexPoint1[i]);
        }

        return solution;
    }

    private boolean checkCondition(List<Vector> simplexPoints, Vector xr, int h, ObjectiveFunction f) {
        for (int i = 0; i < simplexPoints.size(); i++) {
            if (i == h) continue;
            if (f.calculate(simplexPoints.get(i)) >= f.calculate(xr)) {
                return false;
            }
        }
        return true;
    }

    private Vector expansion(Vector xc, Vector xr, double gamma) {
        return xc.nScalarMultiply(1 - gamma).nSub(xr.nScalarMultiply(gamma));
    }

    private Vector reflection(Vector xc, Vector xh, double alpha) {
        return xc.nScalarMultiply(1 + alpha).nSub(xh.nScalarMultiply(alpha));
    }

    private Vector centroid(List<Vector> simplexPoints, int h) {
        Vector centroid = Vector.newInstance(simplexPoints.size() - 1);

        for (int i = 0; i < simplexPoints.size(); i++) {
            if (i == h) continue;
            centroid.add(simplexPoints.get(i));
        }
        centroid.scalarMultiply(1 / (double)(simplexPoints.size() - 1));

        return centroid;
    }

    private int getIndexL(ObjectiveFunction function, List<Vector> simplexPoints) {
        int index = -1;
        double min = Double.MAX_VALUE;
        for (int i = 0; i < simplexPoints.size(); i++) {
            double f = function.calculate(simplexPoints.get(i));
            if (f < min) {
                min = f;
                index = i;
            }
        }

        return index;
    }

    private int getIndexH(ObjectiveFunction function, List<Vector> simplexPoints) {
        int index = -1;
        double max = -Double.MAX_VALUE;
        for (int i = 0; i < simplexPoints.size(); i++) {
            double f = function.calculate(simplexPoints.get(i));
            if (f > max) {
                max = f;
                index = i;
            }
        }

        return index;
    }

    private List<Vector> getSimplexPoints(Vector x, double step) {
        List<Vector> points = new ArrayList<>();
        points.add(x);
        for (int i = 0; i < x.getDimension(); i++) {
            Vector newVector = x.copy();
            newVector.set(i, x.get(i) + step);
            points.add(newVector);
        }

        return points;
    }
}
