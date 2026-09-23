package org.howard.edu.lsp.assignment3;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Employee payroll ETL pipeline.
 *
 * <p>Extracts employee rows from {@code data/employees.csv}, applies the payroll
 * transformations required by CSCI 363, and loads the results into
 * {@code data/transformed_employees.csv}. Both paths are relative to the project
 * root, which is the working directory the program is launched from.
 *
 * <p>This class coordinates the run only: reading, parsing, calculating,
 * writing, and counting each belong to a collaborating object.
 *
 * @author Arianna Stith
 */
public class ETLPipeline {

    /** Relative path of the input CSV file. */
    private static final String INPUT_PATH = "data/employees.csv";

    /** Relative path of the output CSV file. */
    private static final String OUTPUT_PATH = "data/transformed_employees.csv";

    /** Extract step. */
    private final EmployeeCsvReader reader;

    /** Parsing and validation of a single row. */
    private final EmployeeRowParser parser;

    /** Payroll rules. */
    private final PayrollCalculator calculator;

    /** Load step. */
    private final PayrollCsvWriter writer;

    /** Counters for the run summary. */
    private final PipelineSummary summary;

    /**
     * Creates a pipeline that reads and writes the given files.
     *
     * @param inputPath  path of the input CSV file
     * @param outputPath path of the output CSV file
     */
    public ETLPipeline(Path inputPath, Path outputPath) {
        this.reader = new EmployeeCsvReader(inputPath);
        this.parser = new EmployeeRowParser();
        this.calculator = new PayrollCalculator();
        this.writer = new PayrollCsvWriter(outputPath);
        this.summary = new PipelineSummary();
    }

    /**
     * Runs the complete ETL pipeline and prints the run summary.
     *
     * @param args unused; the program requires no command-line arguments
     */
    public static void main(String[] args) {
        ETLPipeline pipeline =
                new ETLPipeline(Paths.get(INPUT_PATH), Paths.get(OUTPUT_PATH));
        pipeline.run();
    }

    /**
     * Executes extract, transform, and load, then prints the summary.
     *
     * <p>A file-level failure ends the run with a message and no summary, which
     * matches the behavior of the Assignment #2 solution.
     */
    public void run() {
        List<String> dataRows;
        try {
            dataRows = reader.readDataRows();
        } catch (NoSuchFileException e) {
            System.out.println("Input file not found: " + INPUT_PATH);
            System.out.println("Looked in: " + reader.getSource().toAbsolutePath());
            return;
        } catch (IOException e) {
            System.out.println("Could not read input file: " + INPUT_PATH);
            System.out.println("Reason: " + e);
            return;
        }

        List<PayrollRecord> records = transform(dataRows);

        try {
            writer.write(records);
        } catch (IOException e) {
            System.out.println("Could not write output file: " + OUTPUT_PATH);
            System.out.println("Reason: " + e);
            return;
        }

        summary.print(OUTPUT_PATH);
    }

    /**
     * Transform step: converts every readable row into a payroll record and
     * counts the rows that had to be skipped.
     *
     * @param dataRows raw data rows from the input file
     * @return the transformed records, in input order
     */
    private List<PayrollRecord> transform(List<String> dataRows) {
        List<PayrollRecord> records = new ArrayList<>();
        for (String row : dataRows) {
            try {
                Employee employee = parser.parse(row);
                records.add(new PayrollRecord(employee, calculator.calculateGrossPay(employee)));
                summary.recordTransformed();
            } catch (InvalidRowException e) {
                summary.recordSkipped();
            }
        }
        return records;
    }

    /**
     * @return the counters collected by the most recent run
     */
    public PipelineSummary getSummary() {
        return summary;
    }
}
