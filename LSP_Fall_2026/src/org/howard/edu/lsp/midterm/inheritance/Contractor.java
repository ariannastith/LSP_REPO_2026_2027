package org.howard.edu.lsp.midterm.inheritance;

/**
 * A contractor paid an hourly rate with a 15 percent premium on all hours
 * worked.
 *
 * @author Arianna Stith
 */
public class Contractor extends Employee {

    private static final double CONTRACTOR_PREMIUM = 1.15;

    /**
     * Creates a contractor with the given hours and hourly rate.
     *
     * @param hoursWorked hours worked in the week; assumed nonnegative
     * @param hourlyRate hourly pay rate; assumed nonnegative
     */
    public Contractor(double hoursWorked, double hourlyRate) {
        super(hoursWorked, hourlyRate);
    }

    /**
     * Returns hours worked multiplied by the hourly rate, increased by 15
     * percent.
     *
     * @return the week's pay
     */
    @Override
    public double calculatePay() {
        return getHoursWorked() * getHourlyRate() * CONTRACTOR_PREMIUM;
    }
}
