package odes;

import odes.solvers.de.*;
import odes.systems.EDESystem;
import odes.visualization.Visualiser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    private static Map<String, String> options = new HashMap<>();

    public static void main(String[] args) throws IOException, InterruptedException {
        if (args.length == 0) {
            System.err.println("Configuration file expected, but not found!");
            return;
        }
        readOptions(args[0]);
        double sigma = Double.parseDouble(options.get("sigma"));
        double r = Double.parseDouble(options.get("r"));
        double b = Double.parseDouble(options.get("b"));
        double step = Double.parseDouble(options.get("step"));
        double x0 = Double.parseDouble(options.get("t0"));
        double[] y0 = new double[3];
        y0[0] = Double.parseDouble(options.get("x0"));
        y0[1] = Double.parseDouble(options.get("y0"));
        y0[2] = Double.parseDouble(options.get("z0"));
        LSystem system = new LSystem(sigma, r, b);
        DESolver solver = loadSolver(options.get("solver"), step, x0, y0, system);
        int steps = Integer.parseInt(options.get("steps_num"));
        double[] value = new double[3];
        double[] xv = new double[steps], yv = new double[steps], zv = new double[steps];
        xv[0] = y0[0];
        yv[0] = y0[1];
        zv[0] = y0[2];
        for (int i = 1; i < steps; i++) {
            solver.next(value);
            xv[i] = value[0];
            yv[i] = value[1];
            zv[i] = value[2];
        }
        Visualiser visualiser = new Visualiser(xv, yv, zv);
        visualiser.start();
    }

    private static DESolver loadSolver(String name, double step, double x0, double[] y0, EDESystem system) {
        switch (name) {
            case "E":
                return new EulerSolver(system, step, x0, y0);
            case "I":
                return new ImplicitEulerSolver(system, step, x0, y0);
            case "R":
                return new RKSolver(system, step, x0, y0);
            case "A":
                return new AdamsSolver(system, step, x0, y0, loadSolver(options.get("a_solver"), step, x0, y0, system));
            default:
                System.err.println("Wrong method name!");
                System.exit(0);
        }
        throw new AssertionError();
    }

    private static void readOptions(String file) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(file));
        while (sc.hasNext()) {
            String line = sc.nextLine();
            int eq = line.indexOf('=');
            options.put(line.substring(0, eq), line.substring(eq + 1));
        }
        sc.close();
    }
}
