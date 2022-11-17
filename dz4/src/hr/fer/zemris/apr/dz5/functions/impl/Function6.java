package hr.fer.zemris.apr.dz5.functions.impl;

import hr.fer.zemris.apr.dz5.functions.ObjectiveFunction;

import java.util.Arrays;

public class Function6 extends ObjectiveFunction {

    public Function6() {
        super(new double[] {0, 0});
    }

    @Override
    public double calculate(double[] x) {
        if (hashedValues.containsKey(Arrays.toString(x))) {
            return (hashedValues.get(Arrays.toString(x)));
        }
        callsCount++;
        double sum = 0;
        for(int i = 0; i < x.length; i++) {
            sum += Math.pow(x[i], 2);
        }

        double result = 0.5 + (Math.pow(Math.sin(Math.sqrt(sum)), 2) - 0.5) / Math.pow(1 + 0.001 * sum, 2);
        hashedValues.put(Arrays.toString(x), result);

        return result;
    }
}
