package demo.williamssonoma.codechallenge.exception;

/**
 * Thrown to indicate that some {@link demo.williamssonoma.codechallenge.model.ZipRange}s are invalid.<p/>
 *
 * @author  Micahael Sun
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
