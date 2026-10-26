package org.howard.edu.lsp.midterm.inheritance;

/**
 * An employee whose weekly pay is computed from hours worked and an hourly
 * rate. Subclasses override {@link #calculatePay()} with the pay rule that
 * applies to that employment type.
 *
 * @author Arianna Stith
 */
public abstract class Employee {

    private final double hoursWorked;
    private final double hourlyRate;

    /**
     * Records the hours and rate used to compute this employee's pay.
     *
     * @param hoursWorked hours worked in the week; assumed nonnegative
     * @param hourlyRate hourly pay rate; assumed nonnegative
     */
    public Employee(double hoursWorked, double hourlyRate) {
        this.hoursWorked = hoursWorked;
        this.hourlyRate = hourlyRate;
    }

    /**
     * Returns the hours worked this week.
     *
     * @return hours worked
     */
    protected double getHoursWorked() {
        return hoursWorked;
    }

    /**
     * Returns this employee's hourly rate.
     *
     * @return hourly rate
     */
    protected double getHourlyRate() {
        return hourlyRate;
    }

    /**
     * Computes this employee's pay for the week.
     *
     * @return the pay amount
     */
    public abstract double calculatePay();
}
