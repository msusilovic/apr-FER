package hr.fer.zemris.apr.dz5.numericalIntegration;

import hr.fer.zemris.apr.dz5.matrix.Matrix;

import java.io.FileOutputStream;
import java.io.IOException;

public class Euler extends NumericalIntegrationMethod {

    public Euler(double T, double tMax) {
        super(T, tMax);
    }

    @Override
    public Matrix run(Matrix A, Matrix B, Matrix xk, int tPower, int printIter) throws IOException {
        double t = 0;
        FileOutputStream outputStream = new FileOutputStream("stats/euler.csv", true);
        int i = 0;

        while (t < tMax) {
            xk = nextX(A, B, xk, t, tPower);

            String line = String.format("t = %.2f   x1 = %.4f   x2 = %.4f\n", t, xk.get(0, 0), xk.get(1, 0));
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
        Matrix dXk = A.nMultiply(xk);
        double[][] tElements = new double[2][1];
        if (B != null) {
            if (tPower == 0) {
                tElements = new double[][]{{1}, {1}};
            } else if (tPower == 1) {
                tElements = new double[][]{{t}, {t}};
            }
            Matrix tMatrix = new Matrix(xk.getRowsCount(), 1, tElements, true);
            dXk.add(B.nMultiply(tMatrix));
        }

        xk.add(dXk.scalarMultiply(T));

        return xk;
    }
}
