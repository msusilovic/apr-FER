package hr.fer.zemris.apr.functions.impl;

import hr.fer.zemris.apr.functions.ObjectiveFunction;
import hr.fer.zemris.apr.vector.Vector;

public class Function1 extends ObjectiveFunction {

    public Function1() {
        super(new Vector(true, new double[]{1.9, 2}), new Vector(true, new double[]{1, 1}));
    }

    @Override
    public double calculate(Vector x) {
        if (hashedValues.containsKey(x.toString())) {
            return (hashedValues.get(x.toString()));
        }
        callsCount++;
        double result = 100 * Math.pow(x.get(1) - x.get(0) * x.get(0), 2) + Math.pow(1 - x.get(0), 2);
        hashedValues.put(x.toString(), result);

        return result;
    }

}
