package demo.williamssonoma.codechallenge.exception;

/**
 * exception for invalid zip ranges
 */
public class InvalidZipRangeException extends RuntimeException {
    public InvalidZipRangeException() {
        super();
    }

    public InvalidZipRangeException(String message) {
        super(message);
    }

    public InvalidZipRangeException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidZipRangeException(Throwable cause) {
        super(cause);
    }

    public InvalidZipRangeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
