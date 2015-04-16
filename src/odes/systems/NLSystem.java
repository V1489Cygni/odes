package odes.systems;

public abstract interface NLSystem {
    public abstract void evaluate(double[] x, double[] result);

    public abstract void evaluateJacobian(double[] x, double[][] result);

    public abstract int getSize();
}
