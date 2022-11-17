package hr.fer.zemris.apr.functions.impl;

import hr.fer.zemris.apr.functions.ObjectiveFunction;
import hr.fer.zemris.apr.vector.Vector;

public class Function6 extends ObjectiveFunction {

    @Override
    public double calculate(Vector x) {
        if (hashedValues.containsKey(x.toString())) {
            return (hashedValues.get(x.toString()));
        }
        callsCount++;
        double result = Math.pow(x.get(0) - 3, 2);
        hashedValues.put(x.toString(), result);

        return result;
    }
}
