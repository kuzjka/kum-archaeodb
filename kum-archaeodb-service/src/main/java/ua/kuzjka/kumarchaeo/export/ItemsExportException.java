package ua.kuzjka.kumarchaeo.export;

/**
 * Represents an error during export to CSV.
 */
public class ItemsExportException extends RuntimeException {
    /**
     * Creates new export exception.
     * @param cause Exception cause
     */
    public ItemsExportException(Throwable cause) {
        super(cause);
    }
}
