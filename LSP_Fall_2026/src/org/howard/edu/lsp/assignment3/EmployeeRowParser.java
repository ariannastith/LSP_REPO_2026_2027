package org.howard.edu.lsp.assignment3;

import java.math.BigDecimal;
import java.util.Locale;

/**
 * Turns one raw line of the input file into a validated {@link Employee}.
 *
 * <p>This class owns every rule about the shape of the input: how many fields a
 * row must have, which fields are normalized, and which values are rejected.
 * Rows that break a rule are reported with an {@link InvalidRowException} so the
 * pipeline can count them as skipped.
 *
 * @author Arianna Stith
 */
public class EmployeeRowParser {

    /** Number of fields every valid input row must contain. */
    private static final int EXPECTED_FIELD_COUNT = 5;

    /** Column index of the employee identifier. */
    private static final int ID_INDEX = 0;

    /** Column index of the employee name. */
    private static final int NAME_INDEX = 1;

    /** Column index of the department. */
    private static final int DEPARTMENT_INDEX = 2;

    /** Column index of the hours worked. */
    private static final int HOURS_INDEX = 3;

    /** Column index of the hourly rate. */
    private static final int RATE_INDEX = 4;

    /**
     * Parses and validates one input row.
     *
     * @param line raw, untrimmed line from the input file
     * @return the employee described by the row
     * @throws InvalidRowException if the row is blank, has the wrong number of
     *                             fields, or holds a value that is not valid
     */
    public Employee parse(String line) throws InvalidRowException {
        if (line == null || line.trim().isEmpty()) {
            throw new InvalidRowException("Row is blank");
        }

        // -1 keeps trailing empty fields so "1,2,3,4," is still five fields.
        String[] fields = line.split(",", -1);
        if (fields.length != EXPECTED_FIELD_COUNT) {
            throw new InvalidRowException(
                    "Expected " + EXPECTED_FIELD_COUNT + " fields but found " + fields.length);
        }

        int employeeId = parseId(fields[ID_INDEX].trim());
        String name = fields[NAME_INDEX].trim().toUpperCase(Locale.ROOT);
        String department = fields[DEPARTMENT_INDEX].trim();
        BigDecimal hoursWorked = parseAmount(fields[HOURS_INDEX].trim(), "HoursWorked");
        BigDecimal hourlyRate = parseAmount(fields[RATE_INDEX].trim(), "HourlyRate");

        return new Employee(employeeId, name, department, hoursWorked, hourlyRate);
    }

    /**
     * Parses the employee identifier.
     *
     * @param text trimmed field text
     * @return the identifier
     * @throws InvalidRowException if the text is not an integer
     */
    private int parseId(String text) throws InvalidRowException {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            throw new InvalidRowException("EmployeeID is not an integer: " + text);
        }
    }

    /**
     * Parses a non-negative decimal field without losing precision.
     *
     * @param text      trimmed field text
     * @param fieldName column name, used in the failure message
     * @return the parsed value
     * @throws InvalidRowException if the text is not a number or is negative
     */
    private BigDecimal parseAmount(String text, String fieldName) throws InvalidRowException {
        BigDecimal value;
        try {
            value = new BigDecimal(text);
        } catch (NumberFormatException e) {
            throw new InvalidRowException(fieldName + " is not a number: " + text);
        }
        if (value.signum() < 0) {
            throw new InvalidRowException(fieldName + " is negative: " + text);
        }
        return value;
    }
}
