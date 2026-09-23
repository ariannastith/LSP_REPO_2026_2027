package org.howard.edu.lsp.assignment3;

import java.math.BigDecimal;

/**
 * Employment classification derived from the hours an employee worked.
 *
 * @author Arianna Stith
 */
public enum EmploymentStatus {

    /** Fewer than 30 hours worked. */
    PART_TIME("Part-Time"),

    /** 30 or more hours worked. */
    FULL_TIME("Full-Time");

    /** Fewest hours that still count as full-time. */
    private static final BigDecimal FULL_TIME_MINIMUM_HOURS = new BigDecimal("30.00");

    /** Text written to the output file. */
    private final String label;

    /**
     * @param label text written to the output file for this status
     */
    EmploymentStatus(String label) {
        this.label = label;
    }

    /**
     * Classifies an employee by hours worked.
     *
     * @param hoursWorked validated hours worked
     * @return {@link #PART_TIME} below 30 hours, otherwise {@link #FULL_TIME}
     */
    public static EmploymentStatus forHoursWorked(BigDecimal hoursWorked) {
        return hoursWorked.compareTo(FULL_TIME_MINIMUM_HOURS) < 0 ? PART_TIME : FULL_TIME;
    }

    /**
     * @return the label written to the output file
     */
    @Override
    public String toString() {
        return label;
    }
}
