package hr.fer.zemris.apr.linear;

public class IncompatibleOperandException extends RuntimeException {

    public IncompatibleOperandException() {
        super();
    }

    public IncompatibleOperandException(String message){
        super(message);
    }

}
