package demo.williamssonoma.codechallenge.exception;

/**
 * Thrown to indicate that some input {@link demo.williamssonoma.codechallenge.model.ZipRange}s are invalid.<p/>
 *
 * @author  Micahael Sun
 */
public class InvalidInputException extends RuntimeException {
    public InvalidInputException() {
        super();
    }

    public InvalidInputException(String message) {
        super(message);
    }

    public InvalidInputException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidInputException(Throwable cause) {
        super(cause);
    }

    public InvalidInputException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
