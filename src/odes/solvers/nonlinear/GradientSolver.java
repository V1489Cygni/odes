package odes.solvers.nonlinear;

import odes.systems.NLSystem;

import java.util.Arrays;

public class GradientSolver extends NonLinearSolver {
    private double lambda = 1;

    @Override
    public double[] solve(NLSystem system) {
        int n = system.getSize();
        double[] x = getInitialApproximation(n);
        double[] grad = new double[n];
        double[] value = new double[n];
        double[][] jacobian = new double[n][n];
        for (int t = 0; t < iterationsNumber; t++) {
            Arrays.fill(grad, 0);
            system.evaluate(x, value);
            system.evaluateJacobian(x, jacobian);
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    grad[j] += 2 * value[i] * jacobian[i][j];
                }
            }
            double dist = 0;
            for (int i = 0; i < n; i++) {
                x[i] -= lambda * grad[i];
                dist += Math.pow(lambda * grad[i], 2);
            }
            if (Math.sqrt(dist) <= epsilon) {
                break;
            }
            lambda /= 2;
        }
        return x;
    }

    public void setLambda(double lambda) {
        this.lambda = lambda;
    }
}
