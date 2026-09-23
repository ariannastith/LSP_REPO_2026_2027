package org.howard.edu.lsp.assignment3;

import java.math.BigDecimal;

/**
 * A single validated employee from the input file.
 *
 * <p>An {@code Employee} only exists once every field has been normalized and
 * validated by {@link EmployeeRowParser}, so the rest of the pipeline can use
 * its values without re-checking them. Instances are immutable.
 *
 * @author Arianna Stith
 */
public class Employee {

    /** Identifier taken from the {@code EmployeeID} column. */
    private final int employeeId;

    /** Name, already trimmed and upper-cased. */
    private final String name;

    /** Department name, already trimmed. */
    private final String department;

    /** Hours worked, kept unrounded so pay is rounded only once. */
    private final BigDecimal hoursWorked;

    /** Hourly rate, kept unrounded so pay is rounded only once. */
    private final BigDecimal hourlyRate;

    /**
     * Creates an employee from values that have already been validated.
     *
     * @param employeeId  identifier from the input row
     * @param name        normalized name
     * @param department  normalized department
     * @param hoursWorked non-negative hours worked
     * @param hourlyRate  non-negative hourly rate
     */
    public Employee(int employeeId, String name, String department,
                    BigDecimal hoursWorked, BigDecimal hourlyRate) {
        this.employeeId = employeeId;
        this.name = name;
        this.department = department;
        this.hoursWorked = hoursWorked;
        this.hourlyRate = hourlyRate;
    }

    /**
     * @return the employee identifier
     */
    public int getEmployeeId() {
        return employeeId;
    }

    /**
     * @return the normalized name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the normalized department
     */
    public String getDepartment() {
        return department;
    }

    /**
     * @return the unrounded hours worked
     */
    public BigDecimal getHoursWorked() {
        return hoursWorked;
    }

    /**
     * @return the unrounded hourly rate
     */
    public BigDecimal getHourlyRate() {
        return hourlyRate;
    }

    /**
     * Reports whether this employee belongs to the given department.
     *
     * @param candidate department name to compare against, matched exactly
     * @return {@code true} if the departments are equal
     */
    public boolean worksIn(String candidate) {
        return department.equals(candidate);
    }
}
