package hu.mora.tool.exception;

public class MoraException extends Exception {

    public MoraException(String message) {
        super(message);
    }

    public MoraException(String message, Throwable cause) {
        super(message, cause);
    }
}
