package hr.fer.zemris.apr.optimization;

import hr.fer.zemris.apr.function.ObjectiveFunction;
import hr.fer.zemris.apr.linear.Vector;
import hr.fer.zemris.apr.util.Interval;
import hr.fer.zemris.apr.util.OrderedPair;

import java.util.*;

public class Box {

    private static final double EPSILON = 10E-6;
    private static final double ALPHA = 1.3;

    public Vector calculate(Vector x0, ObjectiveFunction function, List<Interval> explicitConstraints, List<ObjectiveFunction> implicitConstraints) {
        double bestSolution = function.calculate(x0);
        int iteration = 0;

        checkExplicitConstraints(x0, explicitConstraints);
        checkImplicitConstraints(x0, implicitConstraints);

        Vector xc = x0.copy();
        List<OrderedPair> points = generatePoints(xc, function, explicitConstraints, implicitConstraints);
        Collections.sort(points, Comparator.comparingDouble(OrderedPair::getY));

        do {
            int h = points.size() - 1;
            int h2 = points.size() - 2;

            xc = calculateCentroid(points);
            Vector xr = reflect(xc, points.get(h).getX());

            xr = fitToExplicitConstraints(xr, explicitConstraints);
            xr = fitToImplicitConstraints(xr, xc, implicitConstraints);
            if (function.calculate(xr) > points.get(h2).getY()) {
                xr = moveTowardsCentroid(xr, xc);
            }
            points.set(h, new OrderedPair(xr, function.calculate(xr)));
            Collections.sort(points, Comparator.comparingDouble(OrderedPair::getY));
            if (points.get(0).getY() < bestSolution) {
                bestSolution = points.get(0).getY();
                iteration = 0;
            } else {
                iteration++;
                if (iteration == 1000) {
                    System.out.println("Postupak divergira!");
                    break;
                }
            }
        } while (points.get(0).getX().nSub(points.get(points.size() - 1).getX()).norm() >= EPSILON);

        return points.get(0).getX();
    }

    private Vector fitToExplicitConstraints(Vector xr, List<Interval> explicitConstraints) {
        for (int i = 0; i < xr.getDimension(); i++) {
            if (xr.get(i) < explicitConstraints.get(i).getXd()) {
                xr.set(i, explicitConstraints.get(i).getXd());
            } else if (xr.get(i) > explicitConstraints.get(i).getXg()) {
                xr.set(i, explicitConstraints.get(i).getXg());
            }
        }

        return xr;
    }

    private Vector fitToImplicitConstraints(Vector xr, Vector xc, List<ObjectiveFunction> implicitConstraints) {
        while(!implicitConstraintsSatisfied(xr, implicitConstraints)) {
            xr = moveTowardsCentroid(xr, xc);
        }

        return xr;
    }

    private boolean implicitConstraintsSatisfied(Vector xr, List<ObjectiveFunction> implicitConstraints) {
        for(ObjectiveFunction function : implicitConstraints) {
            if (function.calculate(xr) < 0) {
                return false;
            }
        }
        return true;
    }


    private Vector reflect(Vector xc, Vector xh) {
        return xc.nScalarMultiply(1 + ALPHA).sub(xh.nScalarMultiply(ALPHA));
    }

    private List<OrderedPair> generatePoints(Vector xc, ObjectiveFunction function, List<Interval> explicit, List<ObjectiveFunction> implicit) {
        List<Vector> points = new ArrayList<>();

        for (int t = 0; t < 2 * xc.getDimension(); t++) {
            double[] x = new double[xc.getDimension()];
            for (int i = 0; i < xc.getDimension(); i++) {
                x[i] = explicit.get(i).getXd() + Math.random() * (explicit.get(i).getXg() - explicit.get(i).getXd());
            }
            Vector point = new Vector(x);
            point = fitToImplicitConstraints(point, xc, implicit);
            points.add(point);
            xc = calculateCurrentCentroid(points);
        }

        List<OrderedPair> pairs = new ArrayList<>();
        for(Vector v : points) {
            pairs.add(new OrderedPair(v, function.calculate(v)));
        }

        return pairs;
    }

    private Vector calculateCentroid(List<OrderedPair> points) {
        Vector centroid = points.get(0).getX().copy();
        for (int i = 1; i < points.size() - 1; i++) {
            centroid.add(points.get(i).getX());
        }

        return centroid.scalarMultiply(1 / ((double) points.size() - 1));
    }

    private Vector calculateCurrentCentroid(List<Vector> points) {
        Vector centroid = null;
        for (Vector p : points) {
            if(centroid == null) {
                centroid = p.copy();
            }else{
                centroid.add(p);
            }
        }
        return centroid.nScalarMultiply(1 / (double) points.size());
    }

    private Vector moveTowardsCentroid(Vector point, Vector xc) {
        return point.nAdd(xc).scalarMultiply(0.5);
    }

    private void checkExplicitConstraints(Vector x, List<Interval> explicitConstraints) {
        for (int i = 0; i < x.getDimension(); i++) {
            if (x.get(i) < explicitConstraints.get(i).getXd() || x.get(i) > explicitConstraints.get(i).getXg()) {
                throw new IllegalArgumentException("Početna točka mora zadovoljavati eksplicitna ograničenja!");
            }
        }
    }

    private void checkImplicitConstraints(Vector x, List<ObjectiveFunction> implicitConstraints) {
        if(!implicitConstraintsSatisfied(x, implicitConstraints)) {
                throw new IllegalArgumentException("Početna točka mora zadovoljavati implicitna ograničenja!");

        }
    }
}
