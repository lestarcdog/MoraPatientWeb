package hu.mora.dbmigrate.exception;

import javax.ejb.ApplicationException;

@ApplicationException
public class MoraException extends RuntimeException {

    public MoraException(String message) {
        super(message);
    }

    public MoraException(String message, Throwable cause) {
        super(message, cause);
    }

    public MoraException(Throwable cause) {
        super(cause);
    }
}
