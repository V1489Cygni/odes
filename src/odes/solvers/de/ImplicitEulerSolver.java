package odes.solvers.de;

import odes.systems.EDESystem;

public class ImplicitEulerSolver extends DESolver {
    private double lastX;
    private double[] lastY;

    public ImplicitEulerSolver(EDESystem system, double step, double initialX, double[] initialY) {
        super(system, step, initialX, initialY);
        int n = system.getSize();
        lastX = initialX;
        lastY = new double[n];
        System.arraycopy(initialY, 0, lastY, 0, n);
    }

    @Override
    public void next(double[] result) {
        int n = system.getSize();
        ((EDESystem) system).solve(lastX, lastY, step, result);
        lastX += step;
        for (int i = 0; i < n; i++) {
            lastY[i] = result[i];
        }
    }
}
