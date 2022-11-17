package hr.fer.zemris.apr.linear;

public class Vector {

    private double[] elements;

    private int dimension;

    public Vector(double[] elements) {
        this.elements = elements;
        this.dimension = elements.length;
    }

    public Vector(boolean direct, double[] values) {

        if (direct) {
            this.elements = values;
        }else{
            for(int i = 0; i < values.length; i++) {
                this.set(i, values[i]);
            }
        }
        this.dimension = values.length;
    }

    public double get(int d) {
        return elements[d];
    }

    public Vector set(int position, double value) {
        elements[position] = value;

        return this;
    }

    public int getDimension() {
        return this.dimension;
    }

    public Vector copy() {
        Vector v = this.newInstance(this.getDimension());

        for (int i = 0; i < this.dimension; i++) {
            v.set(i, this.get(i));
        }

        return v;
    }

    public static Vector newInstance(int dimension) {
        return new Vector(new double[dimension]);
    }

    public static Vector parseSimple(String elements) {
        String[] strings = elements.split("\\s+");

        double [] values = new double[strings.length];

        for (int i = 0; i < strings.length; i++) {
            values[i] = Double.parseDouble(strings[i]);
        }

        return new Vector(values);
    }

    public Vector add(Vector other) throws IncompatibleOperandException {
        if(this.getDimension()!=other.getDimension()) {
            throw new IncompatibleOperandException();
        }

        for (int i = this.getDimension()-1; i >= 0; i--) {
            this.set(i, get(i)+ other.get(i));
        }

        return this;
    }

    public Vector nAdd(Vector other) throws IncompatibleOperandException {
        return this.copy().add(other);
    }

    public Vector sub(Vector other) {
        if(this.getDimension()!=other.getDimension()) {
            throw new IncompatibleOperandException();
        }

        for (int i = this.getDimension()-1; i >= 0; i--) {
            this.set(i, get(i) - other.get(i));
        }

        return this;
    }

    public Vector nSub(Vector other) {
        return this.copy().sub(other);
    }

    public Vector scalarMultiply(double scalar) {

        for (int i = this.getDimension()-1; i >= 0; i--) {
            this.set(i, scalar*get(i));
        }

        return this;
    }

    public Vector nScalarMultiply(double scalar) {
        return this.copy().scalarMultiply(scalar);
    }

    public double norm() {

        double sum = 0;

        for (int i = this.getDimension()-1; i >= 0; i--) {
            sum += this.get(i)*this.get(i);
        }

        return Math.sqrt(sum);
    }

    public Vector normalize() {

        double norm = this.norm();

        for(int i = this.getDimension()-1; i >= 0; i--) {
            this.set(i, this.get(i)/norm);
        }

        return this;
    }

    public Vector nNormalize() {
        return this.copy().normalize();
    }

    public double cosine(Vector other) {
        return this.scalarProduct(other) / (this.norm() + other.norm());
    }

    public double scalarProduct(Vector other) {

        if(this.getDimension()!=other.getDimension()) {
            throw new IncompatibleOperandException();
        }

        double sum = 0;

        for(int i = this.getDimension()-1; i >= 0; i--) {
            sum += this.get(i) * other.get(i);
        }

        return sum;
    }

    public Vector nVectorProduct(Vector other) {
        if (this.getDimension() !=3 || other.getDimension() != 3){
            throw new IncompatibleOperandException();
        }

        Vector vector = this.newInstance(3);

        vector.set(0, this.get(1) * other.get(2) - this.get(2) * other.get(1));
        vector.set(1, this.get(2) * other.get(0) - this.get(0) * other.get(2));
        vector.set(2, this.get(0) * other.get(1) - this.get(1) * other.get(0));

        return vector;

    }

    public Matrix toColumnMatrix() {
        Matrix matrix = new Matrix(this.getDimension(), 1);
        for(int i = 0; i < getDimension(); i++) {
            matrix.set(i, 0, get(i));
        }

        return matrix;

    }

    public double[] toArray() {

        double[] elements = new double[this.getDimension()];

        for (int i = 0; i < this.getDimension(); i++) {
            elements[i] = this.get(i);
        }
        return elements;
    }

    public String toString() {

      return toString(6);
    }

    public String toString(int precision) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(");

        for(int i = 0; i < this.getDimension(); i++) {
            stringBuilder.append(String.format("%." + precision + "f", this.get(i)));
            if(i < this.getDimension() - 1){
                stringBuilder.append(", ");
            }else{
                stringBuilder.append(")");
            }
        }

        return stringBuilder.toString();
    }
}
