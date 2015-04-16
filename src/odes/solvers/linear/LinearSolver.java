package odes.solvers.linear;

public abstract class LinearSolver {
    public abstract void solve(double[][] a, double[] b, double[] answer);
}
