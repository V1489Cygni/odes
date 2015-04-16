package odes.solvers.nonlinear;

import odes.solvers.linear.GaussSolver;
import odes.solvers.linear.LinearSolver;
import odes.systems.NLSystem;

public class NewtonSolver extends NonLinearSolver {
    @Override
    public double[] solve(NLSystem system) {
        int n = system.getSize();
        double[] x = getInitialApproximation(n);
        double[] step = new double[n];
        double[][] jacobian = new double[n][n];
        double[] value = new double[n];
        LinearSolver solver = new GaussSolver();
        for (int t = 0; t < iterationsNumber; t++) {
            system.evaluateJacobian(x, jacobian);
            system.evaluate(x, value);
            for (int i = 0; i < n; i++) {
                value[i] = -value[i];
            }
            solver.solve(jacobian, value, step);
            double dist = 0;
            for (int i = 0; i < n; i++) {
                x[i] += step[i];
                dist += Math.pow(step[i], 2);
            }
            if (Math.sqrt(dist) <= epsilon) {
                break;
            }
        }
        return x;
    }
}
