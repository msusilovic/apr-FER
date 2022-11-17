package hr.fer.zemris.apr.functions.impl;

import hr.fer.zemris.apr.functions.ObjectiveFunction;
import hr.fer.zemris.apr.vector.Vector;

public class Function4 extends ObjectiveFunction {

    public Function4() {
        super(new Vector(true, new double[]{5.1, 1.1}), new Vector(new double[]{0, 0}));
    }

    @Override
    public double calculate(Vector x) {
        if (hashedValues.containsKey(x.toString())) {
            return (hashedValues.get(x.toString()));
        }
        callsCount++;
        double result = Math.abs((x.get(0) - x.get(1)) * (x.get(0) + x.get(1))) + Math.sqrt(x.get(0) * x.get(0) + x.get(1) * x.get(1));
        hashedValues.put(x.toString(), result);

        return result;
    }
}
