package hr.fer.zemris.apr.vector;

public class IncompatibleOperandException extends RuntimeException {

    public IncompatibleOperandException() {
        super();
    }

    public IncompatibleOperandException(String message){
        super(message);
    }

}
