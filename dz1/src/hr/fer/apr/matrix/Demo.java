package hr.fer.apr.matrix;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Demo {
    public static void main(String[] args) throws IOException {
        prvi();
        drugi();
        treci();
        cetvrti();
        peti();
        sesti();
        sedmi();
        osmi();
        deveti();
        deseti();
    }

    private static void prvi() throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("1. ZADATAK\n");

        Matrix matrix = Matrix.parseFromFile("matrix1.txt");
        sb.append("Matrica 1:\n");
        sb.append(matrix);
        Matrix other = matrix.copy().scalarDivide(1.33 * (10e-10)).scalarMultiply(1.33 * (10e-10));
        sb.append("\nMatrica 2:\n");
        sb.append(other);
        sb.append(matrix.equals(other) ? "\nMatrice su jednake\n\n" : "\nMatrice nisu jednake\n\n");

        String rjesenje = sb.toString();
        System.out.println(rjesenje);
        Files.write(Path.of("rjesenja/prvi.txt"), rjesenje.getBytes());
    }

    private static void drugi() throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("2. ZADATAK\n");

        Matrix a = Matrix.parseFromFile("matrix2.txt");
        Matrix b = Matrix.parseFromFile("b2.txt");

        try {
            Matrix lu = a.copy().luDecomposition();
        } catch (RuntimeException exception) {
            sb.append(exception.getMessage());
        }

        Matrix p = new Matrix(a.getRowsCount(), a.getColsCount());
        for (int i = 0; i < a.getRowsCount(); i++) {
            p.set(i, i, 1);
        }
        Matrix lu = a.lupDecomposition(p);
        sb.append("\n\nLUP dekompozicija:\n");
        sb.append(lu);

        Matrix pb = p.nMultiply(b);
        Matrix y = lu.forwardSubstitution((Matrix) pb);
        Matrix x = lu.backwardSubstitution(y);
        sb.append("\nRješenje sustava:\n");
        sb.append(x);

        String rjesenje = sb.toString();
        Files.write(Path.of("rjesenja/drugi.txt"), rjesenje.getBytes());
        System.out.println(rjesenje);
    }

    private static void treci() throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("3. ZADATAK\n");

        Matrix matrix = Matrix.parseFromFile("matrix3.txt");
        Matrix b = Matrix.parseFromFile("b3.txt");

        Matrix lu = matrix.copy().luDecomposition();
        sb.append("LU dekompozicija:\n");
        sb.append(lu);

        try {
            sb.append(lu.backwardSubstitution(lu.forwardSubstitution(b)));
        } catch (RuntimeException exception) {
            sb.append(exception.getMessage() + "\n");
        }

        Matrix p = Matrix.getIdentityMatrix(matrix.getRowsCount());
        Matrix lup = matrix.lupDecomposition(p);
        sb.append("\nLUP dekompozicija:\n");
        sb.append(lup);
        try {
            sb.append(lup.backwardSubstitution(lu.forwardSubstitution(p.nMultiply(b))));
        } catch (RuntimeException exception) {
            sb.append(exception.getMessage() + "\n");
        }

        String rjesenje = sb.toString();
        Files.write(Path.of("rjesenja/treci.txt"), rjesenje.getBytes());
        System.out.println(rjesenje);
    }

    private static void cetvrti() throws IOException {
        StringBuilder sb = new StringBuilder();

        sb.append("4. ZADATAK\n");

        Matrix matrix = Matrix.parseFromFile("matrix4.txt");
        Matrix b = Matrix.parseFromFile("b4.txt");

        try {
            Matrix lu = matrix.copy().luDecomposition();
            sb.append("Rješenje sustava s LU dekompozicijom:\n");
            sb.append(lu.backwardSubstitution(lu.forwardSubstitution(b)));
        } catch (RuntimeException e) {
            sb.append(e.getMessage() + "\n");
        }

        Matrix p = Matrix.getIdentityMatrix(matrix.getRowsCount());
        Matrix lup = matrix.copy().lupDecomposition(p);
        sb.append("\nRješenje sustava s LUP dekompozicijom:\n");
        sb.append(lup.backwardSubstitution(lup.forwardSubstitution(p.nMultiply(b))));

        String rjesenje = sb.toString();
        System.out.println(rjesenje);
        Files.write(Path.of("rjesenja/cetvrti.txt"), rjesenje.getBytes());
    }

    private static void peti() throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("5. ZADATAK\n");

        Matrix a = Matrix.parseFromFile("matrix5.txt");
        Matrix b = Matrix.parseFromFile("b5.txt");
        try {
            Matrix lu = a.copy().luDecomposition();
            sb.append("Rješenje sustava s LU dekompozicijom:\n");
            sb.append(lu.backwardSubstitution(lu.forwardSubstitution(b)));
        } catch (RuntimeException e) {
            sb.append(e.getMessage() + "\n");
        }

        Matrix p = Matrix.getIdentityMatrix(a.getRowsCount());
        try {
            Matrix lup = a.copy().lupDecomposition(p);
            sb.append("\nRješenje sustava s LUP dekompozicijom:\n");
            sb.append(lup.backwardSubstitution(lup.forwardSubstitution(p.nMultiply(b))));
        } catch (RuntimeException e) {
           sb.append(e.getMessage());
        }

        String rjesenje = sb.toString();
        System.out.println(rjesenje);
        Files.write(Path.of("rjesenja/peti.txt"), rjesenje.getBytes());
    }

    private static void sesti() throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("6. ZADATAK\n");

        Matrix a = Matrix.parseFromFile("matrix6.txt");
        Matrix b = Matrix.parseFromFile("b6.txt");

        Matrix p = Matrix.getIdentityMatrix(a.getRowsCount());
        try {
            Matrix lup = a.copy().lupDecomposition(p);
            sb.append("\nRješenje sustava s LUP dekompozicijom:\n");
            sb.append(lup.backwardSubstitution(lup.forwardSubstitution(p.nMultiply(b))));
        } catch (RuntimeException e) {
            sb.append(e.getMessage() + "\n");
        }

        System.out.println(sb.toString());
        Files.write(Path.of("rjesenja/sesti.txt"), sb.toString().getBytes());
    }

    private static void sedmi() throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("7. ZADATAK\n");

        try {
            Matrix matrix = Matrix.parseFromFile("matrix3.txt");
            sb.append(matrix.inverse());
        } catch (RuntimeException exception) {
            sb.append(exception.getMessage() + "\n");
        }

        System.out.println(sb.toString());
        Files.write(Path.of("rjesenja/sedmi.txt"), sb.toString().getBytes());
    }

    private static void osmi() throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("8. ZADATAK\n");

        Matrix matrix = Matrix.parseFromFile("matrix8.txt");
        sb.append(matrix.inverse());

        System.out.println(sb.toString());
        Files.write(Path.of("rjesenja/osmi.txt"), sb.toString().getBytes());

    }

    private static void deveti() throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("9. ZADATAK\n");

        Matrix matrix = Matrix.parseFromFile("matrix8.txt");
        sb.append(matrix.determinant() + " \n");

        System.out.println(sb.toString());
        Files.write(Path.of("rjesenja/deveti.txt"), sb.toString().getBytes());
    }

    private static void deseti() throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("10. ZADATAK\n");

        Matrix matrix = Matrix.parseFromFile("matrix10.txt");
        sb.append(matrix.determinant());

        System.out.println(sb.toString());
        Files.write(Path.of("rjesenja/deseti.txt"), sb.toString().getBytes());
    }
}
