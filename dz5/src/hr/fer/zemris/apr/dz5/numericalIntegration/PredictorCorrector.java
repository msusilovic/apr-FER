package hr.fer.zemris.apr.dz5.numericalIntegration;

import hr.fer.zemris.apr.dz5.matrix.Matrix;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class PredictorCorrector extends NumericalIntegrationMethod {

    private NumericalIntegrationMethod predictor;
    private NumericalIntegrationMethod corrector;
    private int n;

    public PredictorCorrector(NumericalIntegrationMethod predictor, NumericalIntegrationMethod corrector, double T, double tMax, int n) {
        super(T, tMax);
        this.predictor = predictor;
        this.corrector = corrector;
        this.n = n;
    }

    @Override
    public Matrix run(Matrix A, Matrix B, Matrix xk, int tPower, int printIter) throws IOException {
        double t = 0;
        FileOutputStream outputStream = new FileOutputStream(n == 1 ? "stats/pece.csv" : "stats/pece2.csv", true);
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

        return xk;
    }

    @Override
    public Matrix nextX(Matrix A, Matrix B, Matrix xk, double t, int tPower) {
        Matrix temp = predictor.nextX(A, B, xk, t, tPower);

        for (int i = 0; i < n; i++) {
            temp = corrector.correction(A, B, xk, temp, t, tPower);
        }

        return temp;
    }

}
