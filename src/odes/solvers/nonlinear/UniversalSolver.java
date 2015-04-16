package odes.solvers.nonlinear;

import odes.systems.NLSystem;

import java.util.Arrays;

public class UniversalSolver {
    public static final int IG = 10;
    public static final int IN = 100;
    public static final double EG = 0.001;
    public static final double EN = 0.001;

    public static double[] solve(NLSystem system) {
        NonLinearSolver grad = new GradientSolver();
        grad.setIterationsNumber(IG);
        grad.setEpsilon(EG);
        grad.setInitialApproximation(getInitialApproximation(system.getSize()));
        NonLinearSolver newt = new NewtonSolver();
        newt.setIterationsNumber(IN);
        newt.setEpsilon(EN);
        newt.setInitialApproximation(grad.solve(system));
        return newt.solve(system);
    }

    private static double[] getInitialApproximation(int dimension) {
        double[] initialApproximation = new double[dimension];
        Arrays.fill(initialApproximation, 0);
        return initialApproximation;
    }
}
