package hr.fer.zemris.apr.functions.impl;

import hr.fer.zemris.apr.functions.ObjectiveFunction;
import hr.fer.zemris.apr.vector.Vector;

public class Function3 extends ObjectiveFunction {

    public Function3() {
        super(new Vector(true, new double[]{0, 0, 0, 0, 0}), new Vector(true, new double[]{1, 2, 3, 4, 5}));
    }

    @Override
    public double calculate(Vector x) {
        if (hashedValues.containsKey(x.toString())) {
            return (hashedValues.get(x.toString()));
        }
        callsCount++;
        double sum = 0;
        for(int i = 0; i < x.getDimension(); i++) {
            sum += Math.pow(x.get(i) - i - 1, 2);
        }
        hashedValues.put(x.toString(), sum);
        return sum;
    }
}
