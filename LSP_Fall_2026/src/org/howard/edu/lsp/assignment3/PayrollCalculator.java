package org.howard.edu.lsp.assignment3;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Applies the payroll rules that turn an {@link Employee} into gross pay.
 *
 * <p>Base pay, overtime, and the department bonus are all computed from the
 * unrounded input values; the result is rounded exactly once, at the end.
 *
 * @author Arianna Stith
 */
public class PayrollCalculator {

    /** Hours worked beyond this threshold are paid at the overtime rate. */
    private static final BigDecimal OVERTIME_THRESHOLD = new BigDecimal("40.00");

    /** Multiplier applied to the hourly rate for overtime hours. */
    private static final BigDecimal OVERTIME_MULTIPLIER = new BigDecimal("1.5");

    /** Department that receives the bonus. */
    private static final String BONUS_DEPARTMENT = "IT";

    /** Multiplier that applies the 5% IT bonus. */
    private static final BigDecimal BONUS_MULTIPLIER = new BigDecimal("1.05");

    /** Decimal places kept for monetary amounts. */
    private static final int MONEY_SCALE = 2;

    /**
     * Computes the final gross pay for an employee.
     *
     * @param employee validated employee
     * @return gross pay including overtime and the department bonus, rounded to
     *         two decimal places using round-half-up
     */
    public BigDecimal calculateGrossPay(Employee employee) {
        BigDecimal pay = calculateBaseAndOvertimePay(employee);
        pay = applyDepartmentBonus(pay, employee);
        return round(pay);
    }

    /**
     * Rounds a monetary amount to two decimal places using round-half-up.
     *
     * @param amount unrounded amount
     * @return amount with a scale of exactly two
     */
    public BigDecimal round(BigDecimal amount) {
        return amount.setScale(MONEY_SCALE, RoundingMode.HALF_UP);
    }

    /**
     * Pays hours up to 40 at the normal rate and any hours above 40 at 1.5
     * times the normal rate.
     *
     * @param employee validated employee
     * @return pay before the bonus and before rounding
     */
    private BigDecimal calculateBaseAndOvertimePay(Employee employee) {
        BigDecimal hoursWorked = employee.getHoursWorked();
        BigDecimal hourlyRate = employee.getHourlyRate();

        if (hoursWorked.compareTo(OVERTIME_THRESHOLD) <= 0) {
            return hoursWorked.multiply(hourlyRate);
        }

        BigDecimal regularPay = OVERTIME_THRESHOLD.multiply(hourlyRate);
        BigDecimal overtimeHours = hoursWorked.subtract(OVERTIME_THRESHOLD);
        BigDecimal overtimePay = overtimeHours.multiply(hourlyRate).multiply(OVERTIME_MULTIPLIER);
        return regularPay.add(overtimePay);
    }

    /**
     * Adds the 5% bonus when the employee works in the bonus department.
     *
     * @param pay      pay after base and overtime hours
     * @param employee validated employee
     * @return pay including the bonus when it applies
     */
    private BigDecimal applyDepartmentBonus(BigDecimal pay, Employee employee) {
        if (employee.worksIn(BONUS_DEPARTMENT)) {
            return pay.multiply(BONUS_MULTIPLIER);
        }
        return pay;
    }
}
