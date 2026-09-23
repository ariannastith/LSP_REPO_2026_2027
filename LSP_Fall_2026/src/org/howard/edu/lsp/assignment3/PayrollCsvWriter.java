package org.howard.edu.lsp.assignment3;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Load step: writes transformed records to the output CSV file.
 *
 * <p>The writer creates the output directory when it is missing and asks each
 * {@link PayrollRecord} to render itself, so it never needs to know how a row
 * is laid out.
 *
 * @author Arianna Stith
 */
public class PayrollCsvWriter {

    /** File this writer writes to. */
    private final Path destination;

    /**
     * @param destination path of the output CSV file
     */
    public PayrollCsvWriter(Path destination) {
        this.destination = destination;
    }

    /**
     * Writes the header and every record, in order.
     *
     * @param records transformed records to write
     * @throws IOException if the file cannot be created or written
     */
    public void write(List<PayrollRecord> records) throws IOException {
        createParentDirectory();
        try (BufferedWriter writer =
                     Files.newBufferedWriter(destination, StandardCharsets.UTF_8)) {
            writer.write(PayrollRecord.CSV_HEADER);
            writer.write("\n");
            for (PayrollRecord record : records) {
                writer.write(record.toCsvRow());
                writer.write("\n");
            }
        }
    }

    /**
     * Creates the directory that holds the output file when it does not exist.
     *
     * @throws IOException if the directory cannot be created
     */
    private void createParentDirectory() throws IOException {
        Path parent = destination.toAbsolutePath().getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
    }

    /**
     * @return the path this writer writes to
     */
    public Path getDestination() {
        return destination;
    }
}
