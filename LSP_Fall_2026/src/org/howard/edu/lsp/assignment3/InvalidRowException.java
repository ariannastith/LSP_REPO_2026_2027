package org.howard.edu.lsp.assignment3;

/**
 * Signals that an input row cannot become an {@link Employee} and must be
 * skipped.
 *
 * <p>Using an exception instead of a {@code null} return value lets
 * {@link EmployeeRowParser} say why a row was rejected while still forcing the
 * caller to handle the failure.
 *
 * @author Arianna Stith
 */
public class InvalidRowException extends Exception {

    /** Serialization identifier. */
    private static final long serialVersionUID = 1L;

    /**
     * Creates an exception describing why a row was rejected.
     *
     * @param message human-readable reason the row is invalid
     */
    public InvalidRowException(String message) {
        super(message);
    }
}
