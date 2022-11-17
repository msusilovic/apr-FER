package hr.fer.apr.matrix;

public class IncompatibleOperandException extends RuntimeException {

    public IncompatibleOperandException() {
        super();
    }

    public IncompatibleOperandException(String message){
        super(message);
    }

}
