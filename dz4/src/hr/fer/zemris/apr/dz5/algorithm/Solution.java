package hr.fer.zemris.apr.dz5.algorithm;

public interface Solution extends Comparable<Solution> {

    double[] getChromosome();

    void setChromosome(double[] chromosome);

    public double getFitness();

    public void setFitness(double fitness);

    public double[] getValues();

    public int compareTo(Solution solution);
}
