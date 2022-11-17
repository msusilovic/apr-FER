package hr.fer.zemris.apr.functions;

import hr.fer.zemris.apr.vector.Vector;

import java.util.HashMap;
import java.util.Map;

public abstract class ObjectiveFunction {

    protected int callsCount = 0;

    private Vector x0;
    private Vector xMin;
    protected Map<String, Double> hashedValues = new HashMap<>();

    public ObjectiveFunction() {

    };

    public ObjectiveFunction(Vector x0, Vector xMin) {
        this.x0 = x0;
        this.xMin = xMin;
    }

    public int getCallsCount() {
        return callsCount;
    }

    public void setCallsCount(int callsCount) {
        this.callsCount = callsCount;
    }

    public abstract double calculate(Vector x);

    public Vector getX0() {
        return x0;
    }

    public Vector getXMin() {
        return xMin;
    }

    public void resetMap() {
        this.hashedValues = new HashMap<>();
    }
}
