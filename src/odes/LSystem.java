package odes;

import odes.solvers.nonlinear.UniversalSolver;
import odes.systems.EDESystem;
import odes.systems.NLSystem;

public class LSystem implements EDESystem {
    public static final int SIZE = 3;
    public static final int X = 0;
    public static final int Y = 1;
    public static final int Z = 2;

    private double sigma, r, b;

    public LSystem(double sigma, double r, double b) {
        this.sigma = sigma;
        this.r = r;
        this.b = b;
    }

    @Override
    public void solve(double x, double[] y, double step, double[] result) {
        YSystem system = new YSystem(step, y);
        double[] res = UniversalSolver.solve(system);
        System.arraycopy(res, 0, result, 0, SIZE);
    }

    @Override
    public void evaluate(double x, double[] y, double[] result) {
        result[0] = sigma * (y[Y] - y[X]);
        result[1] = y[X] * (r - y[Z]) - y[Y];
        result[2] = y[X] * y[Y] - b * y[Z];
    }

    @Override
    public int getSize() {
        return SIZE;
    }

    private class YSystem implements NLSystem {
        public static final int SIZE = LSystem.SIZE;
        public static final int XN = 0;
        public static final int YN = 1;
        public static final int ZN = 2;

        private double step;
        private double[] y;

        public YSystem(double step, double[] y) {
            this.step = step;
            this.y = y;
        }

        @Override
        public void evaluate(double[] x, double[] result) {
            result[0] = y[XN] + step * sigma * (x[YN] - x[XN]) - x[XN];
            result[1] = y[YN] + step * (r * x[XN] - x[XN] * x[ZN] - x[YN]) - x[YN];
            result[2] = y[ZN] + step * (x[XN] * x[YN] - b * x[ZN]) - x[ZN];
        }

        @Override
        public void evaluateJacobian(double[] x, double[][] result) {
            result[0][0] = -step * sigma - 1;
            result[0][1] = step * sigma;
            result[0][2] = 0;
            result[1][0] = step * (r - x[ZN]);
            result[1][1] = -step - 1;
            result[1][2] = -step * x[XN];
            result[2][0] = step * x[YN];
            result[2][1] = step * x[XN];
            result[2][2] = -b * step - 1;
        }

        @Override
        public int getSize() {
            return SIZE;
        }
    }
}
