package org.howard.edu.lsp.assignment3;

/**
 * Counts what happened during a run and prints the required summary.
 *
 * <p>Every data row is reported to this object exactly once, either as
 * transformed or as skipped, which keeps the three counters consistent with one
 * another.
 *
 * @author Arianna Stith
 */
public class PipelineSummary {

    /** Data rows written to the output file. */
    private int rowsTransformed;

    /** Data rows rejected by the skipping rules. */
    private int rowsSkipped;

    /**
     * Records a row that was successfully transformed.
     */
    public void recordTransformed() {
        rowsTransformed++;
    }

    /**
     * Records a row that had to be skipped.
     */
    public void recordSkipped() {
        rowsSkipped++;
    }

    /**
     * @return non-header rows encountered, including blank and malformed ones
     */
    public int getRowsRead() {
        return rowsTransformed + rowsSkipped;
    }

    /**
     * @return rows written to the output file
     */
    public int getRowsTransformed() {
        return rowsTransformed;
    }

    /**
     * @return rows rejected by the skipping rules
     */
    public int getRowsSkipped() {
        return rowsSkipped;
    }

    /**
     * Prints the run summary required by the specification.
     *
     * @param outputPath path shown on the last line of the summary
     */
    public void print(String outputPath) {
        System.out.println("Rows read: " + getRowsRead());
        System.out.println("Rows transformed: " + getRowsTransformed());
        System.out.println("Rows skipped: " + getRowsSkipped());
        System.out.println("Output file: " + outputPath);
    }
}
