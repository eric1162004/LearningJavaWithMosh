package exceptions;

// Checked -> Exception
// Unchecked (runtime) -> RuntimeException

public class InsufficientFundsException extends Exception{
    public InsufficientFundsException() {
        super("Insufficient fund in your account.");
    }

    public InsufficientFundsException(String message) {
        super(message);
    }
}
