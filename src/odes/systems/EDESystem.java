package odes.systems;

public interface EDESystem extends DESystem {
    void solve(double x, double[] y, double step, double[] result);
}
