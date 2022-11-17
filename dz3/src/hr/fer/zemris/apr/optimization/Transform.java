package hr.fer.zemris.apr.optimization;

import hr.fer.zemris.apr.function.ObjectiveFunction;
import hr.fer.zemris.apr.linear.Vector;

import java.util.List;

public class Transform {

    private static final double EPSILON = 10E-6;
    HookeJeeves hookeJeeves = new HookeJeeves();
    private double t = 1;

    public Vector transformAndCalculate(Vector x0, ObjectiveFunction f, List<ObjectiveFunction> g, List<ObjectiveFunction> h) {

        Vector x = inequalityConstraintsSatisfied(x0, g) ? x0.copy() : findInnerPoint(x0, g);
        Vector xPrev;

        do {
            xPrev = x.copy();
            ObjectiveFunction mixed = new ObjectiveFunction() {
                @Override
                public double calculate(Vector v) {
                    double gSum = 0;
                    double hSum = 0;

                    for(ObjectiveFunction gi : g) {
                        double y = gi.calculate(v);
                        if(y < 0)  {
                            gSum = Double.NEGATIVE_INFINITY;
                            break;
                        }else{
                            gSum += Math.log(y);
                        }
                    }
                    for(ObjectiveFunction hi : h) {
                        hSum += Math.pow(hi.calculate(v), 2);
                    }

                    return f.calculate(v) - (1 / (double) t) * gSum + t * hSum;
                }
            };

            x = hookeJeeves.hookeJeeves(mixed, x);
            t *= 10;
        }while(!checkEndCondition(xPrev, x));

        return x;
    }

    private Vector findInnerPoint(Vector x0, List<ObjectiveFunction> g) {
        ObjectiveFunction G = new ObjectiveFunction() {
            @Override
            public double calculate(Vector v) {
                double sum = 0;
                for(ObjectiveFunction function : g) {
                    double y = function.calculate(v);
                    if(y < 0) {
                        sum -= y;
                    }
                }

                return sum;
            }
        };

        HookeJeeves hj = new HookeJeeves();
        return hj.hookeJeeves(G, x0);
    }

    private boolean checkEndCondition(Vector xPrev, Vector x) {
        Vector c = xPrev.nSub(x);
        for(int i = 0; i < c.getDimension(); i++) {
            if(c.get(i) >= EPSILON) return false;
        }

        return true;
    }

    private boolean inequalityConstraintsSatisfied(Vector x0, List<ObjectiveFunction> inequalityConstraints) {
        for (ObjectiveFunction function : inequalityConstraints) {
            double rez = function.calculate(x0);
            if (function.calculate(x0) < 0) {
                return false;
            }
        }

        return true;
    }
}
