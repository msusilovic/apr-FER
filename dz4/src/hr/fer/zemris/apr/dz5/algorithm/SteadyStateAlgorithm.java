package hr.fer.zemris.apr.dz5.algorithm;

import hr.fer.zemris.apr.dz5.functions.ObjectiveFunction;
import hr.fer.zemris.apr.dz5.operators.ICrossover;
import hr.fer.zemris.apr.dz5.operators.IMutation;

import java.nio.file.attribute.PosixFilePermission;
import java.util.*;

public class SteadyStateAlgorithm {

    private ObjectiveFunction function;
    private List<Solution> population = new ArrayList<>();
    private boolean isBinaryRepresentation;
    private int numberOfEvaluations;
    private IMutation mutation;
    private ICrossover crossover;
    protected Solution bestSolution;
    private double mutationRate;
    private double low;
    private double high;
    private boolean trace;
    private int N;
    private int p;
    private int k = 3;

    public SteadyStateAlgorithm(ObjectiveFunction function, int populationSize, int N, double low, double high, int p, double mutationRate, boolean isBinaryRepresentation, int numberOfEvaluations, IMutation mutation, ICrossover crossover, boolean trace) {
        this.function = function;
        this.isBinaryRepresentation = isBinaryRepresentation;
        this.population = generatePopulation(populationSize, p, N, low, high);
        this.mutation = mutation;
        this.crossover = crossover;
        this.numberOfEvaluations = numberOfEvaluations;
        this.low = low;
        this.high = high;
        this.mutationRate = mutationRate;
        this.trace = trace;
        this.N = N;
        this.p = p;
    }

    public SteadyStateAlgorithm(ObjectiveFunction function, int populationSize, int N, double low, double high, int p, double mutationRate, boolean isBinaryRepresentation, int numberOfEvaluations, IMutation mutation, ICrossover crossover, boolean trace, int k) {
        this(function, populationSize, N, low, high, p, mutationRate, isBinaryRepresentation, numberOfEvaluations, mutation, crossover, trace);
        this.k = k;
    }

    public Solution run() {

        while (bestSolution.getFitness() > 10E-6 && function.getCallsCount() < numberOfEvaluations) {
            Collections.sort(population, Comparator.naturalOrder());

            List<Integer> indices = getRandomIndices(population.size());

            Solution parent1 = population.get(indices.get(0));
            Solution parent2 = population.get(indices.get(1));

            double[] chromosome = crossover.crossover(parent1.getChromosome(), parent2.getChromosome());
            chromosome = mutation.mutate(chromosome, mutationRate, low, high);
            Solution solution = null;
            if (isBinaryRepresentation) {
                solution = new BinarySolution(chromosome, function, low, high, N, p);
            } else {
                solution = new FloatingPointSolution(chromosome, function.calculate(chromosome));
            }

            if (solution.getFitness() < population.get(indices.get(k - 1)).getFitness()) {
                population.set(indices.get(k - 1), solution);
            }
            if (solution.getFitness() < bestSolution.getFitness()) {
                bestSolution = solution;
                if (trace) {
                    printBestSolution(function);
                }
            }
        }

        return bestSolution;
    }

    private void printBestSolution(ObjectiveFunction function) {
        System.out.println("Broj evaluacija: " + function.getCallsCount() + ", Kromosom: " + Arrays.toString(bestSolution.getChromosome()) + ", Vrijednost funkcije: " + bestSolution.getFitness());

    }

    private List<Solution> getParents(List<Integer> indices) {
        double max = -Double.MAX_VALUE;
        Solution worstSolution = null;
        List<Solution> parents = new LinkedList<>();
        for (Integer i : indices) {
            Solution s = population.get(i);
            parents.add(s);
            if (s.getFitness() > max) {
                max = s.getFitness();
                worstSolution = s;
            }
        }
        indices = new ArrayList<>();
        indices.add(population.indexOf(worstSolution));
        parents.remove(worstSolution);

        return parents;
    }

    private List<Integer> getRandomIndices(int size) {
        Random random = new Random();
        List<Integer> indices = new ArrayList<>();
        while (indices.size() < k) {
            int index = random.nextInt(size);
            if (!indices.contains(index)) {
                indices.add(index);
            }
        }

        Collections.sort(indices);
        return indices;
    }

    private List<Solution> generatePopulation(int populationSize, int p, int N, double lowerBound, double upperBound) {
        List<Solution> population = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            if (isBinaryRepresentation) {
                population.add(new BinarySolution(lowerBound, upperBound, p, N, function));
            } else {
                population.add(new FloatingPointSolution(lowerBound, upperBound, N, function));
            }
        }

        bestSolution = population.get(0);
        for (int i = 1; i < populationSize; i++) {
            if (population.get(i).getFitness() < bestSolution.getFitness()) {
                bestSolution = population.get(i);
            }
        }

        return population;
    }
}
