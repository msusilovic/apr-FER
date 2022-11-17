package hr.fer.zemris.apr.functions.impl;

import hr.fer.zemris.apr.functions.ObjectiveFunction;
import hr.fer.zemris.apr.vector.Vector;

public class Function2 extends ObjectiveFunction {

    public Function2() {
        super(new Vector(true, new double[]{0.1, 0.3}), new Vector(true, new double[]{4, 2}));
    }

    @Override
    public double calculate(Vector  x) {
        if (hashedValues.containsKey(x.toString())) {
            return (hashedValues.get(x.toString()));
        }
        callsCount++;
        double result = Math.pow(x.get(0) - 4, 2) + 4 * Math.pow(x.get(1) - 2, 2);
        hashedValues.put(x.toString(), result);

        return result;
    }
}
