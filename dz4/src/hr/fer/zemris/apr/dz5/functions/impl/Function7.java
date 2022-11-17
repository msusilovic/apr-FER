package hr.fer.zemris.apr.dz5.functions.impl;

import hr.fer.zemris.apr.dz5.functions.ObjectiveFunction;

import java.util.Arrays;

public class Function7 extends ObjectiveFunction {

    public Function7() {
        super(new double[]{0, 0});
    }

    @Override
    public double calculate(double[] x) {
        if (hashedValues.containsKey(Arrays.toString(x))) {
            return (hashedValues.get(Arrays.toString(x)));
        }
        callsCount++;
        double sum = 0;
        for (int i = 0; i < x.length; i++) {
            sum += Math.pow(x[i], 2);
        }

        double result = Math.pow(sum, 0.25) * (1 + Math.pow(Math.sin(50 * Math.pow(sum, 0.1)), 2));
        hashedValues.put(Arrays.toString(x), result);

        return result;
    }
}
