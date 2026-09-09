package org.howard.edu.lsp.assignment2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Employee payroll ETL pipeline.
 *
 * <p>Extracts employee rows from {@code data/employees.csv}, applies the payroll
 * transformations required by CSCI 363 Assignment #2, and loads the results into
 * {@code data/transformed_employees.csv}. Both paths are relative to the project
 * root, which is the working directory the program is launched from.
 *
 * @author Arianna Stith
 */
public class ETLPipeline {

    /** Relative path of the input CSV file. */
    private static final String INPUT_PATH = "data/employees.csv";

    /** Relative path of the output CSV file. */
    private static final String OUTPUT_PATH = "data/transformed_employees.csv";

    /** Header written to the output file. */
    private static final String OUTPUT_HEADER =
            "EmployeeID,Name,Department,HoursWorked,HourlyRate,GrossPay,PayLevel,EmploymentStatus";

    /** Number of fields every valid input row must contain. */
    private static final int EXPECTED_FIELD_COUNT = 5;

    /** Hours worked beyond this threshold are paid at the overtime rate. */
    private static final BigDecimal OVERTIME_THRESHOLD = new BigDecimal("40.00");

    /** Multiplier applied to the hourly rate for overtime hours. */
    private static final BigDecimal OVERTIME_MULTIPLIER = new BigDecimal("1.5");

    /** Department that receives the bonus. */
    private static final String BONUS_DEPARTMENT = "IT";

    /** Multiplier that applies the 5% IT bonus. */
    private static final BigDecimal IT_BONUS_MULTIPLIER = new BigDecimal("1.05");

    /** Hours worked below this threshold make an employee part-time. */
    private static final BigDecimal FULL_TIME_THRESHOLD = new BigDecimal("30.00");

    /**
     * Runs the complete ETL pipeline and prints the run summary.
     *
     * @param args unused; the program requires no command-line arguments
     */
    public static void main(String[] args) {
        Path inputPath = Paths.get(INPUT_PATH);
        Path outputPath = Paths.get(OUTPUT_PATH);

        List<String> inputLines;
        try {
            inputLines = extract(inputPath);
        } catch (NoSuchFileException e) {
            System.out.println("Input file not found: " + INPUT_PATH);
            System.out.println("Looked in: " + inputPath.toAbsolutePath());
            return;
        } catch (IOException e) {
            System.out.println("Could not read input file: " + INPUT_PATH);
            System.out.println("Reason: " + e);
            return;
        }

        int rowsRead = 0;
        int rowsSkipped = 0;
        List<String> outputRows = new ArrayList<>();

        // The first line is the header and is neither counted nor transformed.
        for (int i = 1; i < inputLines.size(); i++) {
            rowsRead++;
            String transformedRow = transform(inputLines.get(i));
            if (transformedRow == null) {
                rowsSkipped++;
            } else {
                outputRows.add(transformedRow);
            }
        }

        try {
            load(outputPath, outputRows);
        } catch (IOException e) {
            System.out.println("Could not write output file: " + OUTPUT_PATH);
            System.out.println("Reason: " + e);
            return;
        }

        printSummary(rowsRead, outputRows.size(), rowsSkipped);
    }

    /**
     * Extract step: reads every line of the input file.
     *
     * @param inputPath relative path of the input CSV file
     * @return all lines of the file, including the header line
     * @throws IOException if the file is missing or cannot be read
     */
    private static List<String> extract(Path inputPath) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader =
                     Files.newBufferedReader(inputPath, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }

    /**
     * Transform step: turns one raw input row into one output row.
     *
     * <p>Transformations are applied in the order required by the specification:
     * normalize, validate, base/overtime pay, IT bonus, round the gross pay,
     * derive the pay level, then derive the employment status.
     *
     * @param line raw, untrimmed line from the input file
     * @return the formatted output row, or {@code null} if the row must be skipped
     */
    private static String transform(String line) {
        if (line == null || line.trim().isEmpty()) {
            return null;
        }

        // -1 keeps trailing empty fields so "1,2,3,4," is still five fields.
        String[] fields = line.split(",", -1);
        if (fields.length != EXPECTED_FIELD_COUNT) {
            return null;
        }

        // Step 1: normalize every field, and upper-case the name.
        String employeeId = fields[0].trim();
        String name = fields[1].trim().toUpperCase(Locale.ROOT);
        String department = fields[2].trim();
        String hoursText = fields[3].trim();
        String rateText = fields[4].trim();

        // Step 2: validate the numeric fields.
        if (!isInteger(employeeId)) {
            return null;
        }
        BigDecimal hoursWorked = parseDecimal(hoursText);
        BigDecimal hourlyRate = parseDecimal(rateText);
        if (hoursWorked == null || hourlyRate == null) {
            return null;
        }
        if (hoursWorked.signum() < 0 || hourlyRate.signum() < 0) {
            return null;
        }

        // Steps 3-5: pay is computed from the unrounded parsed values.
        BigDecimal grossPay = roundMoney(
                applyItBonus(calculatePay(hoursWorked, hourlyRate), department));

        // Steps 6-7.
        String payLevel = determinePayLevel(grossPay);
        String employmentStatus = determineEmploymentStatus(hoursWorked);

        return String.join(",",
                employeeId,
                name,
                department,
                formatTwoDecimals(hoursWorked),
                formatTwoDecimals(hourlyRate),
                formatTwoDecimals(grossPay),
                payLevel,
                employmentStatus);
    }

    /**
     * Load step: writes the header and every transformed row to the output file.
     *
     * @param outputPath relative path of the output CSV file
     * @param rows       transformed rows, in input order
     * @throws IOException if the file cannot be created or written
     */
    private static void load(Path outputPath, List<String> rows) throws IOException {
        Path parent = outputPath.toAbsolutePath().getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        try (BufferedWriter writer =
                     Files.newBufferedWriter(outputPath, StandardCharsets.UTF_8)) {
            writer.write(OUTPUT_HEADER);
            writer.write("\n");
            for (String row : rows) {
                writer.write(row);
                writer.write("\n");
            }
        }
    }

    /**
     * Prints the run summary required by the specification.
     *
     * @param rowsRead        non-header lines encountered, including blank and malformed ones
     * @param rowsTransformed rows written to the output file
     * @param rowsSkipped     rows rejected by the skipping rules
     */
    private static void printSummary(int rowsRead, int rowsTransformed, int rowsSkipped) {
        System.out.println("Rows read: " + rowsRead);
        System.out.println("Rows transformed: " + rowsTransformed);
        System.out.println("Rows skipped: " + rowsSkipped);
        System.out.println("Output file: " + OUTPUT_PATH);
    }

    /**
     * Pays hours up to 40 at the normal rate and any hours above 40 at 1.5 times
     * the normal rate.
     *
     * @param hoursWorked validated hours worked
     * @param hourlyRate  validated hourly rate
     * @return pay before the IT bonus and before rounding
     */
    private static BigDecimal calculatePay(BigDecimal hoursWorked, BigDecimal hourlyRate) {
        if (hoursWorked.compareTo(OVERTIME_THRESHOLD) <= 0) {
            return hoursWorked.multiply(hourlyRate);
        }
        BigDecimal regularPay = OVERTIME_THRESHOLD.multiply(hourlyRate);
        BigDecimal overtimeHours = hoursWorked.subtract(OVERTIME_THRESHOLD);
        BigDecimal overtimePay = overtimeHours.multiply(hourlyRate).multiply(OVERTIME_MULTIPLIER);
        return regularPay.add(overtimePay);
    }

    /**
     * Adds the 5% bonus when the department is exactly {@code IT}.
     *
     * @param pay        pay after base and overtime hours
     * @param department trimmed department name
     * @return pay including the bonus when it applies
     */
    private static BigDecimal applyItBonus(BigDecimal pay, String department) {
        if (BONUS_DEPARTMENT.equals(department)) {
            return pay.multiply(IT_BONUS_MULTIPLIER);
        }
        return pay;
    }

    /**
     * Classifies the final rounded gross pay.
     *
     * @param grossPay rounded gross pay
     * @return {@code Low}, {@code Standard}, {@code High}, or {@code Executive}
     */
    private static String determinePayLevel(BigDecimal grossPay) {
        if (grossPay.compareTo(new BigDecimal("500.00")) < 0) {
            return "Low";
        }
        if (grossPay.compareTo(new BigDecimal("1000.00")) < 0) {
            return "Standard";
        }
        if (grossPay.compareTo(new BigDecimal("2000.00")) < 0) {
            return "High";
        }
        return "Executive";
    }

    /**
     * Classifies the employee by hours worked.
     *
     * @param hoursWorked validated hours worked
     * @return {@code Part-Time} below 30 hours, otherwise {@code Full-Time}
     */
    private static String determineEmploymentStatus(BigDecimal hoursWorked) {
        return hoursWorked.compareTo(FULL_TIME_THRESHOLD) < 0 ? "Part-Time" : "Full-Time";
    }

    /**
     * Rounds a monetary amount to two decimal places using round-half-up.
     *
     * @param amount unrounded amount
     * @return amount with a scale of exactly two
     */
    private static BigDecimal roundMoney(BigDecimal amount) {
        return amount.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Formats a value with exactly two decimal places, independent of locale.
     *
     * @param value value to format
     * @return plain decimal string such as {@code 16.67}
     */
    private static String formatTwoDecimals(BigDecimal value) {
        return roundMoney(value).toPlainString();
    }

    /**
     * Reports whether the text is a valid integer.
     *
     * @param text trimmed field text
     * @return {@code true} if the text parses as an {@code int}
     */
    private static boolean isInteger(String text) {
        try {
            Integer.parseInt(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Parses a decimal number without losing precision.
     *
     * @param text trimmed field text
     * @return the parsed value, or {@code null} if the text is not a decimal number
     */
    private static BigDecimal parseDecimal(String text) {
        try {
            return new BigDecimal(text);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
