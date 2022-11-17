package hr.fer.zemris.apr.dz5.functions.impl;


import hr.fer.zemris.apr.dz5.functions.ObjectiveFunction;

import java.util.Arrays;

public class Function3 extends ObjectiveFunction {

    public Function3() {
        super(new double[]{1, 2});
    }

    @Override
    public double calculate(double[] x) {
        if (hashedValues.containsKey(Arrays.toString(x))) {
            return (hashedValues.get(Arrays.toString(x)));
        }
        callsCount++;
        double sum = 0;
        for (int i = 0; i < x.length; i++) {
            sum += Math.pow(x[i] - i - 1, 2);
        }
        hashedValues.put(Arrays.toString(x), sum);
        return sum;
    }
}
