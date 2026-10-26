package org.howard.edu.lsp.midterm.inheritance;

/**
 * A full-time employee paid hourly, with overtime at 1.5 times the hourly rate
 * for hours worked beyond 40 in a week.
 *
 * @author Arianna Stith
 */
public class FullTimeEmployee extends Employee {

    private static final double OVERTIME_THRESHOLD = 40.0;
    private static final double OVERTIME_MULTIPLIER = 1.5;

    /**
     * Creates a full-time employee with the given hours and hourly rate.
     *
     * @param hoursWorked hours worked in the week; assumed nonnegative
     * @param hourlyRate hourly pay rate; assumed nonnegative
     */
    public FullTimeEmployee(double hoursWorked, double hourlyRate) {
        super(hoursWorked, hourlyRate);
    }

    /**
     * Returns straight-time pay for the first 40 hours and time-and-a-half for
     * any hours beyond 40.
     *
     * @return the week's pay
     */
    @Override
    public double calculatePay() {
        double hours = getHoursWorked();
        double rate = getHourlyRate();
        if (hours <= OVERTIME_THRESHOLD) {
            return hours * rate;
        }
        double overtimeHours = hours - OVERTIME_THRESHOLD;
        return (OVERTIME_THRESHOLD * rate) + (overtimeHours * rate * OVERTIME_MULTIPLIER);
    }
}
