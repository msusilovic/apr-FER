package hr.fer.zemris.apr.dz5.numericalIntegration;

import hr.fer.zemris.apr.dz5.matrix.Matrix;

import java.io.FileOutputStream;
import java.io.IOException;

public class RungeKutta extends NumericalIntegrationMethod {

    public RungeKutta(double T, double tMax) {
        super(T, tMax);
    }

    @Override
    public Matrix run(Matrix A, Matrix B, Matrix xk, int tPower, int printIter) throws IOException {
        double t = 0;
        FileOutputStream outputStream = new FileOutputStream("stats/runge_kutta.csv", true);
        int i = 0;

        while (t < tMax) {

            xk = nextX(A, B, xk, t, tPower);

            String line = String.format("t = %.2f x1 = %.4f x2 = %.4f\n", t, xk.get(0, 0), xk.get(1, 0));
            outputStream.write(line.getBytes());
            if (i % printIter == 0) {
                System.out.print(line);
            }
            i++;
            t += T;

            x1.add(xk.get(0, 0));
            x2.add(xk.get(1, 0));
        }
        outputStream.close();

        return xk;
    }

    @Override
    public Matrix nextX(Matrix A, Matrix B, Matrix xk, double t, int tPower) {
        Matrix m1 = calculateF(A, B, xk, t, tPower);
        Matrix m2 = calculateF(A, B, xk.nAdd(m1.copy().scalarMultiply(T / 2)), t + T / 2, tPower);
        Matrix m3 = calculateF(A, B, xk.nAdd(m2.copy().scalarMultiply(T / 2)), t + T / 2, tPower);
        Matrix m4 = calculateF(A, B, xk.nAdd(m3.copy().scalarMultiply(T)), t + T, tPower);
        Matrix xkNext = xk.nAdd(m1.add(m2.scalarMultiply(2).add(m3.scalarMultiply(2).add(m4))).scalarMultiply(T / 6));

        return xkNext;
    }

    public Matrix calculateF(Matrix A, Matrix B, Matrix x, double t, int tPower) {
        Matrix tMatrix = null;
        if (tPower == 0) {
            tMatrix = new Matrix(x.getRowsCount(), 1, new double[][]{{1}, {1}}, true);
        } else if (tPower == 1) {
            tMatrix = new Matrix(x.getRowsCount(), 1, new double[][]{{t}, {t}}, true);
        }
        Matrix f = A.nMultiply(x);
        if (B != null) {
            f.add(B.nMultiply(tMatrix));
        }

        return f;
    }

}
