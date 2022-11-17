package hr.fer.zemris.apr.dz5.matrix;

public class IncompatibleOperandException extends RuntimeException {

    public IncompatibleOperandException() {
        super();
    }

    public IncompatibleOperandException(String message){
        super(message);
    }

}
