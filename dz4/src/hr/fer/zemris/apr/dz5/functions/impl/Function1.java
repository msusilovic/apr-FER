package hr.fer.zemris.apr.dz5.functions.impl;


import hr.fer.zemris.apr.dz5.functions.ObjectiveFunction;

import java.util.Arrays;

public class Function1 extends ObjectiveFunction {

    public Function1() {
        super(new double[]{1, 1});
    }

    @Override
    public double calculate(double[] x) {
        if (hashedValues.containsKey(Arrays.toString(x))) {
            return (hashedValues.get(Arrays.toString(x)));
        }
        callsCount++;
        double result = 100 * Math.pow(x[1] - x[0] * x[0], 2) + Math.pow(1 - x[0], 2);
        hashedValues.put(Arrays.toString(x), result);

        return result;
    }

}
