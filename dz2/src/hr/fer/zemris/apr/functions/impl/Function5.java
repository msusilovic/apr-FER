package hr.fer.zemris.apr.functions.impl;

import hr.fer.zemris.apr.functions.ObjectiveFunction;
import hr.fer.zemris.apr.vector.Vector;

public class Function5 extends ObjectiveFunction {

    public Function5() {
        super(new Vector(true, new double[]{0, 0}), new Vector(true, new double[]{0, 0}));
    }

    @Override
    public double calculate(Vector x) {
        if (hashedValues.containsKey(x.toString())) {
            return (hashedValues.get(x.toString()));
        }
        callsCount++;
        int sum = 0;
        for (int i = 0; i < x.getDimension(); i++) {
            sum += x.get(i) * x.get(i);
        }
        double result = 0.5 + (Math.pow(Math.sin(Math.sqrt(sum)), 2) - 0.5) / Math.pow(1 + 0.001 * sum, 2);
        hashedValues.put(x.toString(), result);

        return result;
    }
}
