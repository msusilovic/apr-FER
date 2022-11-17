package hr.fer.zemris.apr.dz5.functions;

import java.util.HashMap;
import java.util.Map;

public abstract class ObjectiveFunction {

    protected int callsCount = 0;

    private double[] xMin;
    protected Map<String, Double> hashedValues = new HashMap<>();

    public ObjectiveFunction() {
    }

    public ObjectiveFunction(double[] xMin) {
        this.xMin = xMin;
    }

    public int getCallsCount() {
        return callsCount;
    }

    public void setCallsCount(int callsCount) {
        this.callsCount = callsCount;
    }

    public abstract double calculate(double[] x);

    public double[] getXMin() {
        return xMin;
    }

    public void resetMap() {
        this.hashedValues = new HashMap<>();
    }
}
