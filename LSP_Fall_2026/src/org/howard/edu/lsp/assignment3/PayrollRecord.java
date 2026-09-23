package org.howard.edu.lsp.assignment3;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * One fully transformed employee: the original data plus the values derived
 * from it.
 *
 * <p>A record derives its own pay level and employment status at construction
 * and knows how to render itself as an output CSV row, so the writer never has
 * to understand payroll rules.
 *
 * @author Arianna Stith
 */
public class PayrollRecord {

    /** Header row that matches {@link #toCsvRow()}. */
    public static final String CSV_HEADER =
            "EmployeeID,Name,Department,HoursWorked,HourlyRate,GrossPay,PayLevel,EmploymentStatus";

    /** Decimal places shown for every numeric column. */
    private static final int DISPLAY_SCALE = 2;

    /** The employee this record describes. */
    private final Employee employee;

    /** Final rounded gross pay. */
    private final BigDecimal grossPay;

    /** Pay classification derived from {@link #grossPay}. */
    private final PayLevel payLevel;

    /** Employment classification derived from the hours worked. */
    private final EmploymentStatus employmentStatus;

    /**
     * Builds a record and derives its classifications.
     *
     * @param employee validated employee
     * @param grossPay final rounded gross pay for that employee
     */
    public PayrollRecord(Employee employee, BigDecimal grossPay) {
        this.employee = employee;
        this.grossPay = grossPay;
        this.payLevel = PayLevel.forGrossPay(grossPay);
        this.employmentStatus = EmploymentStatus.forHoursWorked(employee.getHoursWorked());
    }

    /**
     * @return the employee this record describes
     */
    public Employee getEmployee() {
        return employee;
    }

    /**
     * @return the final rounded gross pay
     */
    public BigDecimal getGrossPay() {
        return grossPay;
    }

    /**
     * @return the pay level derived from the gross pay
     */
    public PayLevel getPayLevel() {
        return payLevel;
    }

    /**
     * @return the employment status derived from the hours worked
     */
    public EmploymentStatus getEmploymentStatus() {
        return employmentStatus;
    }

    /**
     * Renders this record as a line of the output file.
     *
     * @return comma-separated row matching {@link #CSV_HEADER}
     */
    public String toCsvRow() {
        return String.join(",",
                Integer.toString(employee.getEmployeeId()),
                employee.getName(),
                employee.getDepartment(),
                format(employee.getHoursWorked()),
                format(employee.getHourlyRate()),
                format(grossPay),
                payLevel.toString(),
                employmentStatus.toString());
    }

    /**
     * Formats a value with exactly two decimal places, independent of locale.
     *
     * @param value value to display
     * @return plain decimal string such as {@code 16.67}
     */
    private String format(BigDecimal value) {
        return value.setScale(DISPLAY_SCALE, RoundingMode.HALF_UP).toPlainString();
    }
}
