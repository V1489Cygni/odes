package odes.solvers.linear;

public class GaussSolver extends LinearSolver {
    @Override
    public void solve(double[][] a, double[] b, double[] answer) {
        int n = a.length;
        if ((n != 0 && n != a[0].length) || n != b.length || n != answer.length) {
            throw new AssertionError("System elements have different size!");
        }
        for (int i = 0; i < n; i++) {
            if (a[i][i] == 0) {
                for (int j = i + 1; j < n; j++) {
                    if (a[j][i] != 0) {
                        double[] temp = a[j];
                        a[j] = a[i];
                        a[i] = temp;
                        double t = b[j];
                        b[j] = b[i];
                        b[i] = t;
                        break;
                    }
                }
            }
            for (int j = i + 1; j < n; j++) {
                double c = a[j][i] / a[i][i];
                for (int k = i; k < n; k++) {
                    a[j][k] -= c * a[i][k];
                }
                b[j] -= c * b[i];
            }
        }
        for (int i = n - 1; i >= 0; i--) {
            double sum = 0;
            for (int j = i + 1; j < n; j++) {
                sum += a[i][j] * answer[j];
            }
            answer[i] = (b[i] - sum) / a[i][i];
        }
    }
}
