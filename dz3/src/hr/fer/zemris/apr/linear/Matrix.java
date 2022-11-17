package hr.fer.zemris.apr.linear;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Matrix {

    private static final double EPSILON = 10e-6;

    private double[][] elements;
    private int rows;
    private int cols;
    private int switchCount = 0;

    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;

        this.elements = new double[rows][cols];
    }

    public Matrix(int rows, int cols, double[][] elems, boolean direct) {

        this.rows = rows;
        this.cols = cols;

        if (direct) {
            this.elements = elems;
        } else {
            this.elements = new double[rows][cols];
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    this.set(i, j, elems[i][j]);
                }
            }
        }
    }

    public int getSwitchCount() {
        return switchCount;
    }

    public double[] getRowValues(int i) {
        return elements[i];
    }

    public Matrix getColumnMatrix(int column) {
        Matrix columnMatrix = new Matrix(this.getRowsCount(), 1);
        for (int i = 0; i < this.getRowsCount(); i++) {
            columnMatrix.set(i, 0, get(i, column));
        }

        return columnMatrix;
    }

    public void setRow(int i, double[] row) {
        elements[i] = row;
    }

    public Matrix transpose() {
        Matrix matrix = new Matrix(this.getColsCount(), this.getRowsCount());
        for (int i = 0; i < this.getRowsCount(); i++) {
            for (int j = 0; i < this.getColsCount(); j++) {
                matrix.set(j, i, this.get(i, j));
            }
        }

        return matrix;
    }

    public int getRowsCount() {
        return this.rows;
    }

    public int getColsCount() {
        return this.cols;
    }

    public double[][] getElements() {
        return elements;
    }

    public double get(int row, int col) {
        return elements[row][col];
    }

    public Matrix set(int row, int col, double value) {
        elements[row][col] = value;

        return this;
    }

    public Matrix copy() {
        Matrix matrix = newInstance(this.rows, this.cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix.set(i, j, elements[i][j]);
            }
        }

        return matrix;
    }

    public Matrix newInstance(int row, int col) {
        return new Matrix(row, col);
    }

    public Matrix scalarMultiply(double scalar) {
        for (int i = 0; i < this.getRowsCount(); i++) {
            for (int j = 0; j < this.getColsCount(); j++) {
                this.set(i, j, scalar * this.get(i, j));
            }
        }

        return this;
    }

    public Matrix scalarDivide(double scalar) {
        return scalarMultiply(1 / scalar);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Matrix matrix = (Matrix) o;
        if (rows != matrix.rows || cols != matrix.cols) return false;
        for (int i = 0; i < matrix.getRowsCount(); i++) {
            for (int j = 0; j < matrix.getColsCount(); j++) {
                if (Math.abs(this.get(i, j) - matrix.get(i, j)) > EPSILON) return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(rows, cols);
        result = 31 * result + Arrays.hashCode(elements);
        return result;
    }

    public static Matrix parseFromFile(String stringPath) throws IOException {
        List<String> lines = Files.readAllLines(Path.of(stringPath));
        int cols = lines.get(0).split(" ").length;
        Matrix matrix = new Matrix(lines.size(), cols);

        for (int i = 0; i < lines.size(); i++) {
            String[] line = lines.get(i).split(" ");
            for (int j = 0; j < line.length; j++) {
                matrix.set(i, j, Double.parseDouble(line[j]));
            }
        }

        return matrix;
    }

    public static void writeToFile(Matrix matrix, String path) throws IOException {
        Files.write(Path.of(path), matrix.toString().getBytes());
    }

    public Matrix forwardSubstitution(Matrix b) {
        if (b.getRowsCount() != getRowsCount() || getRowsCount() != getColsCount()) {
            throw new IncompatibleOperandException();
        }
        Matrix matrix = new Matrix(b.getRowsCount(), 1, b.getElements(), false);
        for (int i = 0; i < this.getRowsCount() - 1; i++) {
            for (int j = i + 1; j < this.getColsCount(); j++) {
                matrix.set(j, 0, matrix.get(j, 0) - (this.get(j, i) * matrix.get(i, 0)));
            }
        }

        return matrix;
    }

    public Matrix backwardSubstitution(Matrix b) {
        if (b.getRowsCount() != getRowsCount() || getRowsCount() != getColsCount()) {
            throw new IncompatibleOperandException();
        }
        Matrix matrix = new Matrix(b.getRowsCount(), 1, b.getElements(), false);
        for (int i = this.getRowsCount() - 1; i >= 0; i--) {
            if (Math.abs(this.get(i, i)) < EPSILON) {
                throw new RuntimeException("Matrica je singularna. Sustav nema rješenja.");
            }

            matrix.set(i, 0, matrix.get(i, 0) / this.get(i, i));
            for (int j = 0; j < i; j++) {
                matrix.set(j, 0, matrix.get(j, 0) - this.get(j, i) * matrix.get(i, 0));
            }
        }

        return matrix;
    }

    public Matrix luDecomposition() {
        if (this.getRowsCount() != this.getColsCount()) {
            throw new IncompatibleOperandException();
        }
        for (int i = 0; i < this.getRowsCount() - 1; i++) {
            double value = Math.abs(get(i, i));
            if (Math.abs(get(i, i)) < EPSILON) {
                throw new RuntimeException("Matricu nije moguce rastaviti LU dekompozicijom.");
            }
            for (int j = i + 1; j < this.getColsCount(); j++) {
                set(j, i, get(j, i) / get(i, i));
                for (int k = i + 1; k < getColsCount(); k++) {
                    set(j, k, get(j, k) - get(j, i) * get(i, k));
                }
            }
        }

        return this;
    }

    public Matrix lupDecomposition(Matrix p) {
        int count = 0;
        if (this.getRowsCount() != this.getColsCount()) {
            throw new IncompatibleOperandException();
        }
        for (int i = 0; i < getRowsCount() - 1; i++) {
            int r = findPivotRow(i);
            if (Math.abs(get(r, i)) < EPSILON) {
                throw new RuntimeException("Sustav je nerješiv.");
            }
            if (i != r) {
                switchCount++;
                switchRows(r, i, this);
                switchRows(r, i, p);
            }
            for (int j = i + 1; j < getColsCount(); j++) {
                set(j, i, get(j, i) / get(i, i));
                for (int k = i + 1; k < getColsCount(); k++) {
                    set(j, k, get(j, k) - get(j, i) * get(i, k));
                }
            }
        }

        return this;
    }

    private int findPivotRow(int i) {
        double max = Math.abs(get(i, i));
        int r = i;
        for (int j = i + 1; j < getRowsCount(); j++) {
            if (Math.abs(get(j, i)) > max) {
                max = Math.abs(get(j, i));
                r = j;
            }
        }

        return r;
    }

    private void switchRows(int r, int i, Matrix matrix) {
        double[] tempRow = matrix.getRowValues(i);
        matrix.setRow(i, matrix.getRowValues(r));
        matrix.setRow(r, tempRow);
    }

    public Matrix inverse() {
        if (this.getRowsCount() != this.getColsCount()) {
            throw new RuntimeException("Nemoguće računati inverz matrice koja nije kvadratna");
        }
        Matrix p = getIdentityMatrix(this.getColsCount());
        lupDecomposition(p);
        Matrix x = new Matrix(this.getRowsCount(), this.getColsCount());
        Matrix identity = Matrix.getIdentityMatrix(this.getRowsCount());
        for (int i = 0; i < this.getColsCount(); i++) {
            Matrix yi = forwardSubstitution((Matrix) p.nMultiply(identity.getColumnMatrix(i)));
            Matrix xi = backwardSubstitution(yi);
            for (int j = 0; j < this.getRowsCount(); j++) {
                x.set(j, i, xi.get(j, 0));
            }
        }

        return x;
    }

    public static Matrix getIdentityMatrix(int rows) {
        Matrix e = new Matrix(rows, rows);
        for (int i = 0; i < rows; i++) {
            e.set(i, i, 1);
        }

        return e;
    }

    public double determinant() {
        Matrix p = getIdentityMatrix(this.getRowsCount());
        Matrix lup = ((Matrix) this.copy()).lupDecomposition(p);
        double determinant = Math.pow(-1, lup.getSwitchCount());
        for (int i = 0; i < lup.getRowsCount(); i++) {
            determinant *= lup.get(i, i);
        }

        return determinant;
    }

    @Override
    public String toString() {
        return print(3);
    }

    public String print(int precision) {

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < this.getRowsCount(); i++) {
            for (int j = 0; j < this.getColsCount(); j++) {
                sb.append(String.format("%." + precision + "f", this.get(i, j)));
                if (j != this.getColsCount() - 1) {
                    sb.append(" ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public Matrix nAdd(Matrix other) {

        return this.copy().add(other);
    }

    public Matrix add(Matrix other) {

        checkDimensions(other);

        for (int i = 0; i < this.getRowsCount(); i++) {
            for (int j = 0; j < this.getColsCount(); j++) {
                this.set(i, j, this.get(i, j) + other.get(i, j));
            }
        }

        return this;
    }

    public Matrix sub(Matrix other) {
        checkDimensions(other);
        for (int i = 0; i < this.getRowsCount(); i++) {
            for (int j = 0; j < this.getColsCount(); j++) {
                this.set(i, j, this.get(i, j) - other.get(i, j));
            }
        }

        return this;
    }

    public Matrix nSub(Matrix other) {
        return this.copy().sub(other);
    }

    public Matrix nMultiply(Matrix other) {
        if (this.getColsCount() != other.getRowsCount()) {
            throw new IncompatibleOperandException();
        }
        int dimMutual = this.getColsCount();
        int dimR = this.getRowsCount();
        int dimC = other.getColsCount();

        Matrix retVal = new Matrix(dimR, dimC);
        for (int i = 0; i < dimR; i++){
            for (int j = 0; j < dimC; j++){
                double val = 0;
                for (int k = 0; k < dimMutual; k++){
                    val += this.get(i, k) * other.get(k, j);
                }
                retVal.set(i, j, val);
            }
        }
        return retVal;
    }

    public Vector toVector() {
        if (getColsCount() != 1) {
            throw new IncompatibleOperandException();
        }
        double[] values = new double[getRowsCount()];
        for (int i = 0; i < this.getRowsCount(); i++) {
            values[i] = get(i, 0);
        }

        return new Vector(values);
    }

    private void checkDimensions(Matrix other) {

        if (this.getColsCount() != other.getColsCount() || this.getRowsCount() != other.getRowsCount()) {
            throw new IncompatibleOperandException();
        }
    }
}
