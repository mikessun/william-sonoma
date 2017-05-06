package demo.williamssonoma.codechallenge.exception;

/**
 * exception during consolidating zip ranges
 */
public class ConsolidationException extends RuntimeException {
    public ConsolidationException() {
        super();
    }

    public ConsolidationException(String message) {
        super(message);
    }

    public ConsolidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConsolidationException(Throwable cause) {
        super(cause);
    }

    public ConsolidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
