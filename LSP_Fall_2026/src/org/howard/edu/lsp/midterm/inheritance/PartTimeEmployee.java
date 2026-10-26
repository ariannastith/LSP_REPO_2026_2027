package org.howard.edu.lsp.midterm.inheritance;

/**
 * A part-time employee paid a straight hourly rate with no overtime premium.
 *
 * @author Arianna Stith
 */
public class PartTimeEmployee extends Employee {

    /**
     * Creates a part-time employee with the given hours and hourly rate.
     *
     * @param hoursWorked hours worked in the week; assumed nonnegative
     * @param hourlyRate hourly pay rate; assumed nonnegative
     */
    public PartTimeEmployee(double hoursWorked, double hourlyRate) {
        super(hoursWorked, hourlyRate);
    }

    /**
     * Returns hours worked multiplied by the hourly rate.
     *
     * @return the week's pay
     */
    @Override
    public double calculatePay() {
        return getHoursWorked() * getHourlyRate();
    }
}
