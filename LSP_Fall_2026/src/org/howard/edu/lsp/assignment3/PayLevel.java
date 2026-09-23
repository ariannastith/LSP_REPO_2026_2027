package org.howard.edu.lsp.assignment3;

import java.math.BigDecimal;

/**
 * Pay classification derived from an employee's final gross pay.
 *
 * <p>The enum owns both the labels written to the output file and the
 * thresholds that separate them, so the classification rule lives in exactly
 * one place.
 *
 * @author Arianna Stith
 */
public enum PayLevel {

    /** Gross pay below $500. */
    LOW("Low"),

    /** Gross pay from $500 up to but not including $1000. */
    STANDARD("Standard"),

    /** Gross pay from $1000 up to but not including $2000. */
    HIGH("High"),

    /** Gross pay of $2000 or more. */
    EXECUTIVE("Executive");

    /** Lowest gross pay that is no longer {@link #LOW}. */
    private static final BigDecimal STANDARD_MINIMUM = new BigDecimal("500.00");

    /** Lowest gross pay that is no longer {@link #STANDARD}. */
    private static final BigDecimal HIGH_MINIMUM = new BigDecimal("1000.00");

    /** Lowest gross pay that is no longer {@link #HIGH}. */
    private static final BigDecimal EXECUTIVE_MINIMUM = new BigDecimal("2000.00");

    /** Text written to the output file. */
    private final String label;

    /**
     * @param label text written to the output file for this level
     */
    PayLevel(String label) {
        this.label = label;
    }

    /**
     * Classifies a final, already rounded gross pay amount.
     *
     * @param grossPay rounded gross pay
     * @return the matching pay level
     */
    public static PayLevel forGrossPay(BigDecimal grossPay) {
        if (grossPay.compareTo(STANDARD_MINIMUM) < 0) {
            return LOW;
        }
        if (grossPay.compareTo(HIGH_MINIMUM) < 0) {
            return STANDARD;
        }
        if (grossPay.compareTo(EXECUTIVE_MINIMUM) < 0) {
            return HIGH;
        }
        return EXECUTIVE;
    }

    /**
     * @return the label written to the output file
     */
    @Override
    public String toString() {
        return label;
    }
}
