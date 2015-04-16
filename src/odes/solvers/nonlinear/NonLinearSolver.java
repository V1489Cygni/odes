package odes.solvers.nonlinear;

import odes.systems.NLSystem;

import java.util.Arrays;

public abstract class NonLinearSolver {
    protected double epsilon = 0;
    protected int iterationsNumber = Integer.MAX_VALUE;
    protected double[] initialApproximation;

    public abstract double[] solve(NLSystem system);

    public void setEpsilon(double epsilon) {
        this.epsilon = epsilon;
    }

    public void setIterationsNumber(int iterationsNumber) {
        this.iterationsNumber = iterationsNumber;
    }

    public void setInitialApproximation(double[] initialApproximation) {
        this.initialApproximation = initialApproximation;
    }

    protected double[] getInitialApproximation(int dimension) {
        double[] initialApproximation = new double[dimension];
        if (this.initialApproximation != null) {
            if (initialApproximation.length != this.initialApproximation.length) {
                throw new AssertionError("Initial approximation has invalid size!");
            }
            System.arraycopy(this.initialApproximation, 0, initialApproximation, 0, dimension);
        } else {
            Arrays.fill(initialApproximation, 0);
        }
        return initialApproximation;
    }
}
