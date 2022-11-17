package hr.fer.zemris.apr.dz5.operators;

public interface IMutation {

    double[] mutate(double[] chromosome, double pm, double lowerBound, double upperBound);

}
