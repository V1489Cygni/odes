package odes.systems;

public interface DESystem {
    void evaluate(double x, double[] y, double[] result);

    int getSize();
}
