package hr.fer.zemris.apr.dz5.numericalIntegration;

import hr.fer.zemris.apr.dz5.matrix.Matrix;

import java.io.FileOutputStream;
import java.io.IOException;

public class Trapezoid extends NumericalIntegrationMethod {

    public Trapezoid(double T, double tMax) {
        super(T, tMax);
    }

    @Override
    public Matrix run(Matrix A, Matrix B, Matrix xk, int tPower, int printIter) throws IOException {
        double t = 0;
        FileOutputStream outputStream = new FileOutputStream("stats/trapezoid.csv", true);
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
        Matrix U = Matrix.getIdentityMatrix(A.getRowsCount());
        Matrix R = U.nSub(A.copy().scalarMultiply(T / 2)).inverse().nMultiply(U.nAdd(A.copy().scalarMultiply(T / 2)));
        xk = R.nMultiply(xk);

        if (B != null) {
            Matrix S = U.nSub(A.copy().scalarMultiply(T / 2)).inverse().scalarMultiply(T / 2).nMultiply(B);
            Matrix tMatrix = null;
            double[][] tElements = new double[2][1];
            double[][] tNextElements = new double[2][1];
            if (tPower == 0) {
                tElements = new double[][]{{1}, {1}};
                tNextElements = new double[][]{{1}, {1}};
            } else if (tPower == 1) {
                tElements = new double[][]{{t}, {t}};
                tNextElements = new double[][]{{t + T}, {t + T}};
            }
            tMatrix = new Matrix(xk.getRowsCount(), 1, tElements, true).add(new Matrix(xk.getRowsCount(), 1, tNextElements, true));
            xk.add(S.nMultiply(tMatrix));
        }

        return xk;
    }

    @Override
    public Matrix correction(Matrix A, Matrix B, Matrix xk, Matrix temp, double t, int tPower) {
        Matrix m1 = A.nMultiply(xk);
        Matrix m2 = A.nMultiply(temp);

        if (B != null) {
            double[][] tElements = new double[2][1];
            if (tPower == 0) {
                tElements = new double[][]{{1}, {1}};
            } else if (tPower == 1) {
                tElements = new double[][]{{t}, {t}};
            }
            Matrix tMatrix = new Matrix(xk.getRowsCount(), 1, tElements, true);
            m1.add(B.nMultiply(tMatrix));
            m2.add(B.nMultiply(tMatrix));
        }

        return xk.nAdd(m1.add(m2).scalarMultiply(T / 2));
    }
}
